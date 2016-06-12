/* test the ciphers and hashes using their built-in self-tests */

#include <tomcrypt_test.h>

int cipher_hash_test(void)
{
   int           x;
#ifdef MTK_TEE_SUPPORT
   #define BUFSIZE (4096)
   unsigned char *buf = NULL;
#else
   unsigned char buf[4096];
#endif
   unsigned long n;
   prng_state    *pnprng;
#ifndef MTK_TEE_SUPPORT
   prng_state    nprng;
#endif

   /* test ciphers */
   for (x = 0; cipher_descriptor[x].name != NULL; x++) {
      DO(cipher_descriptor[x].test());
   }

   /* test hashes */
   for (x = 0; hash_descriptor[x].name != NULL; x++) {
      DO(hash_descriptor[x].test());
   }

#ifdef MTK_TEE_SUPPORT
    pnprng = (prng_state*)malloc(sizeof(prng_state));
    n = BUFSIZE;
    buf = malloc(BUFSIZE);
#else
    pnprng = &nprng;
    n = sizeof(buf);
#endif

   /* test prngs (test, import/export */
   for (x = 0; prng_descriptor[x].name != NULL; x++) {
      DO(prng_descriptor[x].test());
      DO(prng_descriptor[x].start(pnprng));
      DO(prng_descriptor[x].add_entropy((unsigned char *)"helloworld12", 12, pnprng));
      DO(prng_descriptor[x].ready(pnprng));
      DO(prng_descriptor[x].pexport(buf, &n, pnprng));
      prng_descriptor[x].done(pnprng);
      DO(prng_descriptor[x].pimport(buf, n, pnprng));
      DO(prng_descriptor[x].ready(pnprng));
      if (prng_descriptor[x].read(buf, 100, pnprng) != 100) {
         fprintf(stderr, "Error reading from imported PRNG!\n");
#ifdef MTK_TEE_SUPPORT
         free(buf);
         free(pnprng);
         return 1;
#else
         exit(EXIT_FAILURE);
#endif
      }
      prng_descriptor[x].done(pnprng);
   }
#ifdef MTK_TEE_SUPPORT
   free(buf);
   free(pnprng);
#endif

   return 0;
}

/* $Source: /cvs/libtom/libtomcrypt/testprof/cipher_hash_test.c,v $ */
/* $Revision: 1.3 $ */
/* $Date: 2005/05/05 14:35:59 $ */
