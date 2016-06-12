#include <tomcrypt_test.h>

void exit(int ret)
{
    printf("[tomcrypt_test] exit(ret=%x\n)", ret);
    while(1);
}


prng_state yarrow_prng;

void reg_algs(void)
{
  int err;
#ifdef LTC_RIJNDAEL
  register_cipher (&aes_desc);
#endif
#ifdef LTC_BLOWFISH
  register_cipher (&blowfish_desc);
#endif
#ifdef LTC_XTEA
  register_cipher (&xtea_desc);
#endif
#ifdef LTC_RC5
  register_cipher (&rc5_desc);
#endif
#ifdef LTC_RC6
  register_cipher (&rc6_desc);
#endif
#ifdef LTC_SAFERP
  register_cipher (&saferp_desc);
#endif
#ifdef LTC_TWOFISH
  register_cipher (&twofish_desc);
#endif
#ifdef LTC_SAFER
  register_cipher (&safer_k64_desc);
  register_cipher (&safer_sk64_desc);
  register_cipher (&safer_k128_desc);
  register_cipher (&safer_sk128_desc);
#endif
#ifdef LTC_RC2
  register_cipher (&rc2_desc);
#endif
#ifdef LTC_DES
  register_cipher (&des_desc);
  register_cipher (&des3_desc);
#endif
#ifdef LTC_CAST5
  register_cipher (&cast5_desc);
#endif
#ifdef LTC_NOEKEON
  register_cipher (&noekeon_desc);
#endif
#ifdef LTC_SKIPJACK
  register_cipher (&skipjack_desc);
#endif
#ifdef LTC_KHAZAD
  register_cipher (&khazad_desc);
#endif
#ifdef LTC_ANUBIS
  register_cipher (&anubis_desc);
#endif
#ifdef LTC_KSEED
  register_cipher (&kseed_desc);
#endif
#ifdef LTC_KASUMI
  register_cipher (&kasumi_desc);
#endif
#ifdef LTC_MULTI2
  register_cipher (&multi2_desc);
#endif
#ifdef LTC_CAMELLIA
  register_cipher (&camellia_desc);
#endif

#ifdef LTC_TIGER
  register_hash (&tiger_desc);
#endif
#ifdef LTC_MD2
  register_hash (&md2_desc);
#endif
#ifdef LTC_MD4
  register_hash (&md4_desc);
#endif
#ifdef LTC_MD5
  register_hash (&md5_desc);
#endif
#ifdef LTC_SHA1
  register_hash (&sha1_desc);
#endif
#ifdef LTC_SHA224
  register_hash (&sha224_desc);
#endif
#ifdef LTC_SHA256
  register_hash (&sha256_desc);
#endif
#ifdef LTC_SHA384
  register_hash (&sha384_desc);
#endif
#ifdef LTC_SHA512
  register_hash (&sha512_desc);
#endif
#ifdef LTC_RIPEMD128
  register_hash (&rmd128_desc);
#endif
#ifdef LTC_RIPEMD160
  register_hash (&rmd160_desc);
#endif
#ifdef LTC_RIPEMD256
  register_hash (&rmd256_desc);
#endif
#ifdef LTC_RIPEMD320
  register_hash (&rmd320_desc);
#endif
#ifdef LTC_WHIRLPOOL
  register_hash (&whirlpool_desc);
#endif
#ifdef LTC_CHC_HASH
  register_hash(&chc_desc);
  if ((err = chc_register(register_cipher(&aes_desc))) != CRYPT_OK) {
     fprintf(stderr, "chc_register error: %s\n", error_to_string(err));
     exit(EXIT_FAILURE);
  }
#endif


#ifndef LTC_YARROW
   #error This demo requires Yarrow.
#endif
register_prng(&yarrow_desc);
#ifdef LTC_FORTUNA
register_prng(&fortuna_desc);
#endif
#ifdef LTC_RC4
register_prng(&rc4_desc);
#endif
#ifdef LTC_SOBER128
register_prng(&sober128_desc);
#endif

   if ((err = rng_make_prng(128, find_prng("yarrow"), &yarrow_prng, NULL)) != CRYPT_OK) {
      fprintf(stderr, "rng_make_prng failed: %s\n", error_to_string(err));
      exit(EXIT_FAILURE);
   }
}

void tomcrypt_test_main(void)
{
    int x;
    static int tomcrypt_test_main_inited = 0;
    if(tomcrypt_test_main_inited == 0)
    {
        reg_algs();
        tomcrypt_test_main_inited = 1;
    }

    printf("\ndh_test......"); x = dh_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\nkatja_test......"); x = katja_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\npkcs_1_test......"); x = pkcs_1_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\ncipher_hash_test......"); x = cipher_hash_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\ndsa_test......"); x = dsa_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\nmac_test......"); x = mac_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\nrsa_test......"); x = rsa_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\nder_tests......"); x = der_tests(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\necc_test......"); x = ecc_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\nmodes_test......"); x = modes_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
    printf("\nstore_test......"); x = store_test(); printf(x ? "failed" : "passed");if (x) exit(EXIT_FAILURE);
}
