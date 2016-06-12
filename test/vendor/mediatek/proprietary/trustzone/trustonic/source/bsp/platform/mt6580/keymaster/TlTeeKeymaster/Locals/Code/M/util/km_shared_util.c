/*
 * Copyright (c) 2013-2015 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

#include "keymaster_ta_defs.h"
#include "km_shared_util.h"

uint32_t get_u32(const uint8_t *pos) {
    uint32_t a = 0;
    for (int i = 0; i < 4; i++) {
        a |= ((uint32_t)pos[i] << (8*i));
    }
    return a;
}
uint64_t get_u64(const uint8_t *pos) {
    uint64_t a = 0;
    for (int i = 0; i < 8; i++) {
        a |= ((uint64_t)pos[i] << (8*i));
    }
    return a;
}
void set_u32(uint8_t *pos, uint32_t val) {
    for (int i = 0; i < 4; i++) {
        pos[i] = (val >> (8*i)) & 0xFF;
    }
}
void set_u64(uint8_t *pos, uint64_t val) {
    for (int i = 0; i < 8; i++) {
        pos[i] = (val >> (8*i)) & 0xFF;
    }
}
void set_u32_increment_pos(uint8_t **pos, uint32_t val) {
    set_u32(*pos, val);
    *pos += 4;
}
void set_u64_increment_pos(uint8_t **pos, uint64_t val) {
    set_u64(*pos, val);
    *pos += 8;
}
void set_data_increment_pos(uint8_t **pos, const uint8_t *src, uint32_t len) {
    memcpy(*pos, src, len);
    *pos += len;
}
void set_ptr_increment_src(uint8_t **ptr, uint8_t **src, uint32_t len) {
    *ptr = *src;
    *src += len;
}

bool check_algorithm_purpose(
    keymaster_algorithm_t algorithm,
    keymaster_purpose_t purpose)
{
    switch (algorithm) {
        case KM_ALGORITHM_AES:
            return ((purpose == KM_PURPOSE_ENCRYPT) ||
                    (purpose == KM_PURPOSE_DECRYPT));
        case KM_ALGORITHM_HMAC:
        case KM_ALGORITHM_EC:
            return ((purpose == KM_PURPOSE_SIGN) ||
                    (purpose == KM_PURPOSE_VERIFY));
        case KM_ALGORITHM_RSA:
            return ((purpose == KM_PURPOSE_ENCRYPT) ||
                    (purpose == KM_PURPOSE_DECRYPT) ||
                    (purpose == KM_PURPOSE_SIGN) ||
                    (purpose == KM_PURPOSE_VERIFY));
        default:
            return false;
    }
}
