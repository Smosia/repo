/*
* Copyright (C) 2014 MediaTek Inc.
* Modification based on code covered by the mentioned copyright
* and/or permission notice(s).
*/
/*
 *
 * Copyright 2006, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// Old implementation for phone_number_compare(), which has used in cupcake, but once replaced with
// the new, more strict version, and reverted again.

#include <string.h>
//#include <utils/Log.h>
#include <android/log.h>
#ifdef MTK_CTA_DFO_SUPPORT
#include <cutils/properties.h>
#endif

//add JWYYL-74 songqingming 20141219 (start)
#include <stdlib.h>
//add JWYYL-74 songqingming 20141219 (end)

// Simple logging macros.
#define LOG_TAG "OldPhoneNumberUtils"
#define LOGV(...) __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

namespace android {

//add JWYYL-74 songqingming 20141219 (start)
#ifdef MTK_CTA_DFO_SUPPORT
static int getMinMatchFromProperties()
{
    char min_match[PROPERTY_VALUE_MAX] = {0};
    int n = 0;
    property_get("ro.rgk_number_min_match", min_match, "7");
    n = atoi(min_match);
    LOGD("getMinMatchFromProperties: +++ min_match=%d", n);
    return n;
}
#endif
//add JWYYL-74 songqingming 20141219 (end)

static int getMinMatch() 
{
#if defined(MTK_LONG_MIN_MATCH) || defined(MTK_OP09_SUPPORT)
    return 11;
#elif defined(MTK_CTA_DFO_SUPPORT)
    char cta[PROPERTY_VALUE_MAX] = {0};
    property_get("ro.mtk_cta_support", cta, "0");
    if (0 == strcmp(cta, "1"))
        return 11;
    else
        //modify JWYYL-74 songqingming 20141219 (start)
        //return 7;
        return getMinMatchFromProperties();
        //modify JWYYL-74 songqingming 20141219 (end)
#endif
    return 7;
}

/** True if c is ISO-LATIN characters 0-9 */
static bool isISODigit (char c)
{
    return c >= '0' && c <= '9';
}

/** True if c is ISO-LATIN characters 0-9, *, # , +  */
static bool isNonSeparator(char c)
{
    return (c >= '0' && c <= '9') || c == '*' || c == '#' || c == '+';
}

/**
 * Phone numbers are stored in "lookup" form in the database
 * as reversed strings to allow for caller ID lookup
 *
 * This method takes a phone number and makes a valid SQL "LIKE"
 * string that will match the lookup form
 *
 */
/** all of a up to len must be an international prefix or
 *  separators/non-dialing digits
 */
static bool matchIntlPrefix(const char* a, int len)
{
    /* '([^0-9*#+]\+[^0-9*#+] | [^0-9*#+]0(0|11)[^0-9*#+] )$' */
    /*        0    1                     2 3 45               */

    int state = 0;
    for (int i = 0 ; i < len ; i++) {
        char c = a[i];

        switch (state) {
            case 0:
                if      (c == '+') state = 1;
                else if (c == '0') state = 2;
                else if (isNonSeparator(c)) return false;
            break;

            case 2:
                if      (c == '0') state = 3;
                else if (c == '1') state = 4;
                else if (isNonSeparator(c)) return false;
            break;

            case 4:
                if      (c == '1') state = 5;
                else if (isNonSeparator(c)) return false;
            break;

            default:
                if (isNonSeparator(c)) return false;
            break;

        }
    }

    return state == 1 || state == 3 || state == 5;
}

/** all of 'a' up to len must match non-US trunk prefix ('0') */
static bool matchTrunkPrefix(const char* a, int len)
{
    bool found;

    found = false;

    for (int i = 0 ; i < len ; i++) {
        char c = a[i];

        if (c == '0' && !found) {
            found = true;
        } else if (isNonSeparator(c)) {
            return false;
        }
    }

    return found;
}

/** all of 'a' up to len must be a (+|00|011)country code)
 *  We're fast and loose with the country code. Any \d{1,3} matches */
static bool matchIntlPrefixAndCC(const char* a, int len)
{
    /*  [^0-9*#+]*(\+|0(0|11)\d\d?\d? [^0-9*#+] $ */
    /*      0       1 2 3 45  6 7  8              */

    int state = 0;
    for (int i = 0 ; i < len ; i++ ) {
        char c = a[i];

        switch (state) {
            case 0:
                if      (c == '+') state = 1;
                else if (c == '0') state = 2;
                else if (isNonSeparator(c)) return false;
            break;

            case 2:
                if      (c == '0') state = 3;
                else if (c == '1') state = 4;
                else if (isNonSeparator(c)) return false;
            break;

            case 4:
                if      (c == '1') state = 5;
                else if (isNonSeparator(c)) return false;
            break;

            case 1:
            case 3:
            case 5:
                if      (isISODigit(c)) state = 6;
                else if (isNonSeparator(c)) return false;
            break;

            case 6:
            case 7:
                if      (isISODigit(c)) state++;
                else if (isNonSeparator(c)) return false;
            break;

            default:
                if (isNonSeparator(c)) return false;
        }
    }

    return state == 6 || state == 7 || state == 8;
}

/** or -1 if both are negative */
static int minPositive(int a, int b)
{
    if (a >= 0 && b >= 0) {
        return (a < b) ? a : b;
    } else if (a >= 0) { /* && b < 0 */
        return a;
    } else if (b >= 0) { /* && a < 0 */
        return b;
    } else { /* a < 0 && b < 0 */
        return -1;
    }
}

/**
 * Return the offset into a of the first appearance of b, or -1 if there
 * is no such character in a.
 */
static int indexOf(const char *a, char b) {
    const char *ix = strchr(a, b);

    if (ix == NULL)
        return -1;
    else
        return ix - a;
}

//add JWLYB-1496 songqingming 20150318 (start)
/** all of 'a' up to len must match prefix ('03') and prefix ('92') */
static bool matchInternationalCodeForCustom(const char* a, const char* b)
{
    int findCount1 = 0;
    char c = a[0];
    if (c == '0') {
        findCount1++;
    }
    c = a[1];
    if (c == '3') {
        findCount1++;
    }

    int findCount2 = 0;
    c = b[0];
    if (c == '9') {
        findCount2++;
    }
    c = b[1];
    if (c == '2') {
        findCount2++;
    }

    if (findCount1 == 2 && findCount2 == 2) {
        return true;
    }
    return false;
}
//add JWLYB-1496 songqingming 20150318 (end)

/**
 * Compare phone numbers a and b, return true if they're identical
 * enough for caller ID purposes.
 *
 * - Compares from right to left
 * - requires MIN_MATCH (7) characters to match
 * - handles common trunk prefixes and international prefixes
 *   (basically, everything except the Russian trunk prefix)
 *
 * Tolerates nulls
 */
bool phone_number_compare_loose(const char* a, const char* b)
{
    int ia, ib;
    int matched;
    int numSeparatorCharsInA = 0;
    int numSeparatorCharsInB = 0;

    if (a == NULL || b == NULL) {
        return false;
    }

    //LOGD("phone_number_compare_loose: a=%s, b=%s", a, b);

    ia = strlen(a);
    ib = strlen(b);
    if (ia == 0 || ib == 0) {
        return false;
    }

    // Compare from right to left
    ia--;
    ib--;

    matched = 0;

    while (ia >= 0 && ib >=0) {
        char ca, cb;
        bool skipCmp = false;

        ca = a[ia];

        if (!isNonSeparator(ca)) {
            ia--;
            skipCmp = true;
            numSeparatorCharsInA++;
        }

        cb = b[ib];

        if (!isNonSeparator(cb)) {
            ib--;
            skipCmp = true;
            numSeparatorCharsInB++;
        }

        if (!skipCmp) {
            if (cb != ca) {
                break;
            }
            ia--; ib--; matched++;
        }
    }

    //LOGD("phone_number_compare_loose: a=%s, b=%s, matched=%d, MIN_MATCH=%d", a, b, matched, getMinMatch());

    if (matched < getMinMatch()) {
        const int effectiveALen = strlen(a) - numSeparatorCharsInA;
        const int effectiveBLen = strlen(b) - numSeparatorCharsInB;

        //LOGD("phone_number_compare_loose: eALen=%d, eBLen=%d", effectiveALen, effectiveBLen);

        // if the number of dialable chars in a and b match, but the matched chars < MIN_MATCH,
        // treat them as equal (i.e. 404-04 and 40404)
        if (effectiveALen == effectiveBLen && effectiveALen == matched) {
            return true;
        }

        return false;
    }

    // At least one string has matched completely;
    if (matched >= getMinMatch()  && (ia < 0 || ib < 0)) {
        return true;
    }

    /*
     * Now, what remains must be one of the following for a
     * match:
     *
     *  - a '+' on one and a '00' or a '011' on the other
     *  - a '0' on one and a (+,00)<country code> on the other
     *     (for this, a '0' and a '00' prefix would have succeeded above)
     */

    if (matchIntlPrefix(a, ia + 1) && matchIntlPrefix(b, ib +1)) {
        return true;
    }

    if (matchTrunkPrefix(a, ia + 1) && matchIntlPrefixAndCC(b, ib +1)) {
        return true;
    }

    if (matchTrunkPrefix(b, ib + 1) && matchIntlPrefixAndCC(a, ia +1)) {
        return true;
    }

    /*
     * Last resort: if the number of unmatched characters on both sides is less than or equal
     * to the length of the longest country code and only one number starts with a + accept
     * the match. This is because some countries like France and Russia have an extra prefix
     * digit that is used when dialing locally in country that does not show up when you dial
     * the number using the country code. In France this prefix digit is used to determine
     * which land line carrier to route the call over.
     */
    /*bool aPlusFirst = (*a == '+');
    bool bPlusFirst = (*b == '+');
    if (ia < 4 && ib < 4 && (aPlusFirst || bPlusFirst) && !(aPlusFirst && bPlusFirst)) {
        return true;
    }*/

    //add JWLYB-1496 songqingming 20150318 (start)
    bool aPlusFirst = (*a == '+');
    bool bPlusFirst = (*b == '+');
    if (ia < 4 && ib < 4 && (aPlusFirst || bPlusFirst) && !(aPlusFirst && bPlusFirst)) {
        return true;
    }

    #ifdef MTK_CTA_DFO_SUPPORT
    char qmobile[PROPERTY_VALUE_MAX] = {0};
    property_get("ro.rgk_qmobile_number_match", qmobile, "0");
    if (0 == strcmp(qmobile, "1")) {
        if (ia == 1 && ib == 1
                && (matchInternationalCodeForCustom(a, b) || matchInternationalCodeForCustom(b, a)) ) {
            LOGD("phone_number_compare_loose: custom match : a=%s, b=%s", a, b);
            return true;
        }
    }
    #endif
    //add JWLYB-1496 songqingming 20150318 (end)
	
    //LOGD("phone_number_compare_loose: return false");

    return false;
}

}  // namespace android