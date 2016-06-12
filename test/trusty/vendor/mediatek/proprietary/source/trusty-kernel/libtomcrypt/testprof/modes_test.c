/* test CFB/OFB/CBC modes */
#include <tomcrypt_test.h>

int modes_test(void)
{
   unsigned char pt[64], ct[64], tmp[64], key[16], iv[16], iv2[16];
   int cipher_idx;
#ifdef LTC_CBC_MODE
   symmetric_CBC *pcbc;
#ifndef MTK_TEE_SUPPORT
   symmetric_CBC cbc;
#endif
#endif
#ifdef LTC_CFB_MODE
   symmetric_CFB *pcfb;
#ifndef MTK_TEE_SUPPORT
   symmetric_CFB cfb;
#endif
#endif
#ifdef LTC_OFB_MODE
   symmetric_OFB *pofb;
#ifndef MTK_TEE_SUPPORT
   symmetric_OFB ofb;
#endif
#endif
   unsigned long l;

   /* make a random pt, key and iv */
   yarrow_read(pt,  64, &yarrow_prng);
   yarrow_read(key, 16, &yarrow_prng);
   yarrow_read(iv,  16, &yarrow_prng);

   /* get idx of AES handy */
   cipher_idx = find_cipher("aes");
   if (cipher_idx == -1) {
      fprintf(stderr, "test requires AES");
      return 1;
   }

#ifdef LTC_F8_MODE
   DO(f8_test_mode());
#endif

#ifdef LTC_LRW_MODE
   DO(lrw_test());
#endif

#ifdef LTC_CBC_MODE
#ifdef MTK_TEE_SUPPORT
   pcbc = (symmetric_CBC *)malloc(sizeof(symmetric_CBC));
#else
   pcbc = &cbc;
#endif
   /* test CBC mode */
   /* encode the block */
   DO(cbc_start(cipher_idx, iv, key, 16, 0, pcbc));
   l = sizeof(iv2);
   DO(cbc_getiv(iv2, &l, pcbc));
   if (l != 16 || memcmp(iv2, iv, 16)) {
      fprintf(stderr, "cbc_getiv failed");
#ifdef MTK_TEE_SUPPORT
      free(pcbc);
#endif
      return 1;
   }
   DO(cbc_encrypt(pt, ct, 64, pcbc));

   /* decode the block */
   DO(cbc_setiv(iv2, l, pcbc));
   zeromem(tmp, sizeof(tmp));
   DO(cbc_decrypt(ct, tmp, 64, pcbc));
   if (memcmp(tmp, pt, 64) != 0) {
      fprintf(stderr, "CBC failed");
#ifdef MTK_TEE_SUPPORT
      free(pcbc);
#endif
      return 1;
   }
#ifdef MTK_TEE_SUPPORT
   free(pcbc);
#endif
#endif

#ifdef LTC_CFB_MODE
#ifdef MTK_TEE_SUPPORT
   pcfb = (symmetric_CFB *)malloc(sizeof(symmetric_CFB));
#else
   pcfb = &cfb;
#endif
   /* test CFB mode */
   /* encode the block */
   DO(cfb_start(cipher_idx, iv, key, 16, 0, pcfb));
   l = sizeof(iv2);
   DO(cfb_getiv(iv2, &l, pcfb));
   /* note we don't memcmp iv2/iv since cfb_start processes the IV for the first block */
   if (l != 16) {
      fprintf(stderr, "cfb_getiv failed");
#ifdef MTK_TEE_SUPPORT
      free(pcfb);
#endif
      return 1;
   }
   DO(cfb_encrypt(pt, ct, 64, pcfb));

   /* decode the block */
   DO(cfb_setiv(iv, l, pcfb));
   zeromem(tmp, sizeof(tmp));
   DO(cfb_decrypt(ct, tmp, 64, pcfb));
   if (memcmp(tmp, pt, 64) != 0) {
      fprintf(stderr, "CFB failed");
#ifdef MTK_TEE_SUPPORT
      free(pcfb);
#endif
      return 1;
   }
#ifdef MTK_TEE_SUPPORT
   free(pcfb);
#endif
#endif

#ifdef LTC_OFB_MODE
#ifdef MTK_TEE_SUPPORT
   pofb = (symmetric_OFB *)malloc(sizeof(symmetric_OFB));
#else
   pofb = &ofb;
#endif
   /* test OFB mode */
   /* encode the block */
   DO(ofb_start(cipher_idx, iv, key, 16, 0, pofb));
   l = sizeof(iv2);
   DO(ofb_getiv(iv2, &l, pofb));
   if (l != 16 || memcmp(iv2, iv, 16)) {
      fprintf(stderr, "ofb_getiv failed");
#ifdef MTK_TEE_SUPPORT
      free(pofb);
#endif
      return 1;
   }
   DO(ofb_encrypt(pt, ct, 64, pofb));

   /* decode the block */
   DO(ofb_setiv(iv2, l, pofb));
   zeromem(tmp, sizeof(tmp));
   DO(ofb_decrypt(ct, tmp, 64, pofb));
   if (memcmp(tmp, pt, 64) != 0) {
      fprintf(stderr, "OFB failed");
#ifdef MTK_TEE_SUPPORT
      free(pofb);
#endif
      return 1;
   }
#ifdef MTK_TEE_SUPPORT
   free(pofb);
#endif
#endif

#ifdef LTC_CTR_MODE
   DO(ctr_test());
#endif

#ifdef LTC_XTS_MODE
   DO(xts_test());
#endif

   return 0;
}

/* $Source: /cvs/libtom/libtomcrypt/testprof/modes_test.c,v $ */
/* $Revision: 1.15 $ */
/* $Date: 2007/02/16 16:36:25 $ */
