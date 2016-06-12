
/** 
   @file tomcrypt_mtk_tee_pal.c
   mtk tee platform adaptation layer
*/
#include "tomcrypt_mtk_tee_pal.h"
#include "tomcrypt.h"

#include <lk/init.h>
#include <mt_trng.h>

#include "assert.h"

/* random number generator of mtk tee platform */
unsigned long rng_mtk_tee(unsigned char *buf, unsigned long len,
                          void (*callback)(void))
{
    unsigned long i;
    int sz;
    if( NULL == buf )
    {
        return 0;
    }

    for(i=0;i<len;i+=16)
    {
        sz = ((i+16)>len) ? (len-i) : 16 ;
        plat_gen_rng_data((uint8_t *)buf + i, sz);
    }
    return len;

#if 0
    //return 0;
    unsigned long i, temp;
    int *pi4buf;

    temp = len&3;

    if (temp != 0)    {
        for (i = 0; i < temp; i++)  {
            *buf++ = rand() & 0xFF;
        }
    }
    len -= temp;
    pi4buf = (int *)buf;
    for (i = 0; i < len/4; i++)  {
        *pi4buf++ = rand();
    }

    return len;
#endif
}

#if defined(LTC_MECC) && defined(LTC_MECC_FP)
LTC_MUTEX_PROTO(ltc_ecc_fp_lock);
#endif

/* global initialization for libtomcrypt */
static void LibTomCryptInit(int level)
{
    LTC_MUTEX_INIT(&ltc_cipher_mutex);

    LTC_MUTEX_INIT(&ltc_hash_mutex);

    LTC_MUTEX_INIT(&ltc_prng_mutex);

#if defined(LTC_MECC) && defined(LTC_MECC_FP)
    LTC_MUTEX_INIT(&ltc_ecc_fp_lock);
#endif

   /* crypt initialization for math library */
   /* select the math descriptor */
#ifdef LTM_DESC
   ltc_mp = ltm_desc;
#elif defined(TFM_DESC)
   ltc_mp = tfm_desc;
#else
   #error no math library defined !!!
#endif

    return;
}

/* initialization function for libtomcrypt */
LK_INIT_HOOK(LibTomCryptInit, &LibTomCryptInit, LK_INIT_LEVEL_TARGET+32);

