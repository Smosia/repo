/*
 * Copyright (c) 2015 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

#include "tlStd.h"
#include "TlApi/TlApi.h"

#include "aes-common.h"
#include "aes-core.h"

/*
 * Implementation notes:
 * This implementation assumes it runs over 32-bit processors.
 * It uses 4 tables of 1K.
 * It'd be possible to get down to
 * 2 tables of 256 bytes, but this is a time/memory compromise.
*/

/*
 * Lookup table for encryption
 * This table gathers the first three transformations of a round,
 * i.e : SubBytes, ShiftRows and MixColumns
 * This optimization is suggested in
 * http://csrc.nist.gov/encryption/aes/rijndael/Rijndael.pdf
 * section 5.2.1
 * The original Sbox can be found in FIPS 197 section 5.1.1
 *
 * TE[a] = Sbox[a].[02, 01, 01, 03]
 * Multiplication by (02) corresponds to a left shift and XOR 1b.
 * (see FIPS 197 section 4.2.1)
 * Multiplication by (03) corresponds to a mult by 2 and an addition.
 * 256 entries of 32-bit words.
 */
static const uint32_t TE[256] = {
    0xc66363a5UL /* (63).[02, 01, 01, 03] */ ,
    0xf87c7c84UL /* (7c).[02, 01, 01, 03] */ ,
    0xee777799UL /* (77).[02, 01, 01, 03] */ ,
    0xf67b7b8dUL /* etc */ ,
    0xfff2f20dUL, 0xd66b6bbdUL, 0xde6f6fb1UL, 0x91c5c554UL,
    0x60303050UL, 0x02010103UL, 0xce6767a9UL, 0x562b2b7dUL,
    0xe7fefe19UL, 0xb5d7d762UL, 0x4dababe6UL, 0xec76769aUL,
    0x8fcaca45UL, 0x1f82829dUL, 0x89c9c940UL, 0xfa7d7d87UL,
    0xeffafa15UL, 0xb25959ebUL, 0x8e4747c9UL, 0xfbf0f00bUL,
    0x41adadecUL, 0xb3d4d467UL, 0x5fa2a2fdUL, 0x45afafeaUL,
    0x239c9cbfUL, 0x53a4a4f7UL, 0xe4727296UL, 0x9bc0c05bUL,
    0x75b7b7c2UL, 0xe1fdfd1cUL, 0x3d9393aeUL, 0x4c26266aUL,
    0x6c36365aUL, 0x7e3f3f41UL, 0xf5f7f702UL, 0x83cccc4fUL,
    0x6834345cUL, 0x51a5a5f4UL, 0xd1e5e534UL, 0xf9f1f108UL,
    0xe2717193UL, 0xabd8d873UL, 0x62313153UL, 0x2a15153fUL,
    0x0804040cUL, 0x95c7c752UL, 0x46232365UL, 0x9dc3c35eUL,
    0x30181828UL, 0x379696a1UL, 0x0a05050fUL, 0x2f9a9ab5UL,
    0x0e070709UL, 0x24121236UL, 0x1b80809bUL, 0xdfe2e23dUL,
    0xcdebeb26UL, 0x4e272769UL, 0x7fb2b2cdUL, 0xea75759fUL,
    0x1209091bUL, 0x1d83839eUL, 0x582c2c74UL, 0x341a1a2eUL,
    0x361b1b2dUL, 0xdc6e6eb2UL, 0xb45a5aeeUL, 0x5ba0a0fbUL,
    0xa45252f6UL, 0x763b3b4dUL, 0xb7d6d661UL, 0x7db3b3ceUL,
    0x5229297bUL, 0xdde3e33eUL, 0x5e2f2f71UL, 0x13848497UL,
    0xa65353f5UL, 0xb9d1d168UL, 0x00000000UL, 0xc1eded2cUL,
    0x40202060UL, 0xe3fcfc1fUL, 0x79b1b1c8UL, 0xb65b5bedUL,
    0xd46a6abeUL, 0x8dcbcb46UL, 0x67bebed9UL, 0x7239394bUL,
    0x944a4adeUL, 0x984c4cd4UL, 0xb05858e8UL, 0x85cfcf4aUL,
    0xbbd0d06bUL, 0xc5efef2aUL, 0x4faaaae5UL, 0xedfbfb16UL,
    0x864343c5UL, 0x9a4d4dd7UL, 0x66333355UL, 0x11858594UL,
    0x8a4545cfUL, 0xe9f9f910UL, 0x04020206UL, 0xfe7f7f81UL,
    0xa05050f0UL, 0x783c3c44UL, 0x259f9fbaUL, 0x4ba8a8e3UL,
    0xa25151f3UL, 0x5da3a3feUL, 0x804040c0UL, 0x058f8f8aUL,
    0x3f9292adUL, 0x219d9dbcUL, 0x70383848UL, 0xf1f5f504UL,
    0x63bcbcdfUL, 0x77b6b6c1UL, 0xafdada75UL, 0x42212163UL,
    0x20101030UL, 0xe5ffff1aUL, 0xfdf3f30eUL, 0xbfd2d26dUL,
    0x81cdcd4cUL, 0x180c0c14UL, 0x26131335UL, 0xc3ecec2fUL,
    0xbe5f5fe1UL, 0x359797a2UL, 0x884444ccUL, 0x2e171739UL,
    0x93c4c457UL, 0x55a7a7f2UL, 0xfc7e7e82UL, 0x7a3d3d47UL,
    0xc86464acUL, 0xba5d5de7UL, 0x3219192bUL, 0xe6737395UL,
    0xc06060a0UL, 0x19818198UL, 0x9e4f4fd1UL, 0xa3dcdc7fUL,
    0x44222266UL, 0x542a2a7eUL, 0x3b9090abUL, 0x0b888883UL,
    0x8c4646caUL, 0xc7eeee29UL, 0x6bb8b8d3UL, 0x2814143cUL,
    0xa7dede79UL, 0xbc5e5ee2UL, 0x160b0b1dUL, 0xaddbdb76UL,
    0xdbe0e03bUL, 0x64323256UL, 0x743a3a4eUL, 0x140a0a1eUL,
    0x924949dbUL, 0x0c06060aUL, 0x4824246cUL, 0xb85c5ce4UL,
    0x9fc2c25dUL, 0xbdd3d36eUL, 0x43acacefUL, 0xc46262a6UL,
    0x399191a8UL, 0x319595a4UL, 0xd3e4e437UL, 0xf279798bUL,
    0xd5e7e732UL, 0x8bc8c843UL, 0x6e373759UL, 0xda6d6db7UL,
    0x018d8d8cUL, 0xb1d5d564UL, 0x9c4e4ed2UL, 0x49a9a9e0UL,
    0xd86c6cb4UL, 0xac5656faUL, 0xf3f4f407UL, 0xcfeaea25UL,
    0xca6565afUL, 0xf47a7a8eUL, 0x47aeaee9UL, 0x10080818UL,
    0x6fbabad5UL, 0xf0787888UL, 0x4a25256fUL, 0x5c2e2e72UL,
    0x381c1c24UL, 0x57a6a6f1UL, 0x73b4b4c7UL, 0x97c6c651UL,
    0xcbe8e823UL, 0xa1dddd7cUL, 0xe874749cUL, 0x3e1f1f21UL,
    0x964b4bddUL, 0x61bdbddcUL, 0x0d8b8b86UL, 0x0f8a8a85UL,
    0xe0707090UL, 0x7c3e3e42UL, 0x71b5b5c4UL, 0xcc6666aaUL,
    0x904848d8UL, 0x06030305UL, 0xf7f6f601UL, 0x1c0e0e12UL,
    0xc26161a3UL, 0x6a35355fUL, 0xae5757f9UL, 0x69b9b9d0UL,
    0x17868691UL, 0x99c1c158UL, 0x3a1d1d27UL, 0x279e9eb9UL,
    0xd9e1e138UL, 0xebf8f813UL, 0x2b9898b3UL, 0x22111133UL,
    0xd26969bbUL, 0xa9d9d970UL, 0x078e8e89UL, 0x339494a7UL,
    0x2d9b9bb6UL, 0x3c1e1e22UL, 0x15878792UL, 0xc9e9e920UL,
    0x87cece49UL, 0xaa5555ffUL, 0x50282878UL, 0xa5dfdf7aUL,
    0x038c8c8fUL, 0x59a1a1f8UL, 0x09898980UL, 0x1a0d0d17UL,
    0x65bfbfdaUL, 0xd7e6e631UL, 0x844242c6UL, 0xd06868b8UL,
    0x824141c3UL, 0x299999b0UL, 0x5a2d2d77UL, 0x1e0f0f11UL,
    0x7bb0b0cbUL, 0xa85454fcUL, 0x6dbbbbd6UL, 0x2c16163aUL,
};

/* table to use for the last round of encryption
*/
static const uint8_t SBOX[256] = {
    0x63, 0x7c, 0x77, 0x7b,
    0xf2, 0x6b, 0x6f, 0xc5,
    0x30, 0x01, 0x67, 0x2b,
    0xfe, 0xd7, 0xab, 0x76,
    0xca, 0x82, 0xc9, 0x7d,
    0xfa, 0x59, 0x47, 0xf0,
    0xad, 0xd4, 0xa2, 0xaf,
    0x9c, 0xa4, 0x72, 0xc0,
    0xb7, 0xfd, 0x93, 0x26,
    0x36, 0x3f, 0xf7, 0xcc,
    0x34, 0xa5, 0xe5, 0xf1,
    0x71, 0xd8, 0x31, 0x15,
    0x04, 0xc7, 0x23, 0xc3,
    0x18, 0x96, 0x05, 0x9a,
    0x07, 0x12, 0x80, 0xe2,
    0xeb, 0x27, 0xb2, 0x75,
    0x09, 0x83, 0x2c, 0x1a,
    0x1b, 0x6e, 0x5a, 0xa0,
    0x52, 0x3b, 0xd6, 0xb3,
    0x29, 0xe3, 0x2f, 0x84,
    0x53, 0xd1, 0x00, 0xed,
    0x20, 0xfc, 0xb1, 0x5b,
    0x6a, 0xcb, 0xbe, 0x39,
    0x4a, 0x4c, 0x58, 0xcf,
    0xd0, 0xef, 0xaa, 0xfb,
    0x43, 0x4d, 0x33, 0x85,
    0x45, 0xf9, 0x02, 0x7f,
    0x50, 0x3c, 0x9f, 0xa8,
    0x51, 0xa3, 0x40, 0x8f,
    0x92, 0x9d, 0x38, 0xf5,
    0xbc, 0xb6, 0xda, 0x21,
    0x10, 0xff, 0xf3, 0xd2,
    0xcd, 0x0c, 0x13, 0xec,
    0x5f, 0x97, 0x44, 0x17,
    0xc4, 0xa7, 0x7e, 0x3d,
    0x64, 0x5d, 0x19, 0x73,
    0x60, 0x81, 0x4f, 0xdc,
    0x22, 0x2a, 0x90, 0x88,
    0x46, 0xee, 0xb8, 0x14,
    0xde, 0x5e, 0x0b, 0xdb,
    0xe0, 0x32, 0x3a, 0x0a,
    0x49, 0x06, 0x24, 0x5c,
    0xc2, 0xd3, 0xac, 0x62,
    0x91, 0x95, 0xe4, 0x79,
    0xe7, 0xc8, 0x37, 0x6d,
    0x8d, 0xd5, 0x4e, 0xa9,
    0x6c, 0x56, 0xf4, 0xea,
    0x65, 0x7a, 0xae, 0x08,
    0xba, 0x78, 0x25, 0x2e,
    0x1c, 0xa6, 0xb4, 0xc6,
    0xe8, 0xdd, 0x74, 0x1f,
    0x4b, 0xbd, 0x8b, 0x8a,
    0x70, 0x3e, 0xb5, 0x66,
    0x48, 0x03, 0xf6, 0x0e,
    0x61, 0x35, 0x57, 0xb9,
    0x86, 0xc1, 0x1d, 0x9e,
    0xe1, 0xf8, 0x98, 0x11,
    0x69, 0xd9, 0x8e, 0x94,
    0x9b, 0x1e, 0x87, 0xe9,
    0xce, 0x55, 0x28, 0xdf,
    0x8c, 0xa1, 0x89, 0x0d,
    0xbf, 0xe6, 0x42, 0x68,
    0x41, 0x99, 0x2d, 0x0f,
    0xb0, 0x54, 0xbb, 0x16,
};

/*
 * Lookup table for decryption
 * TD[x] = SboxInv[x].[0e, 09, 0d, 0b];
 */
static const uint32_t TD[256] = {
    0x51f4a750UL /* (52).[0e, 09, 0d, 0b] */ , 0x7e416553UL, 0x1a17a4c3UL,
    0x3a275e96UL,
    0x3bab6bcbUL, 0x1f9d45f1UL, 0xacfa58abUL, 0x4be30393UL,
    0x2030fa55UL, 0xad766df6UL, 0x88cc7691UL, 0xf5024c25UL,
    0x4fe5d7fcUL, 0xc52acbd7UL, 0x26354480UL, 0xb562a38fUL,
    0xdeb15a49UL, 0x25ba1b67UL, 0x45ea0e98UL, 0x5dfec0e1UL,
    0xc32f7502UL, 0x814cf012UL, 0x8d4697a3UL, 0x6bd3f9c6UL,
    0x038f5fe7UL, 0x15929c95UL, 0xbf6d7aebUL, 0x955259daUL,
    0xd4be832dUL, 0x587421d3UL, 0x49e06929UL, 0x8ec9c844UL,
    0x75c2896aUL, 0xf48e7978UL, 0x99583e6bUL, 0x27b971ddUL,
    0xbee14fb6UL, 0xf088ad17UL, 0xc920ac66UL, 0x7dce3ab4UL,
    0x63df4a18UL, 0xe51a3182UL, 0x97513360UL, 0x62537f45UL,
    0xb16477e0UL, 0xbb6bae84UL, 0xfe81a01cUL, 0xf9082b94UL,
    0x70486858UL, 0x8f45fd19UL, 0x94de6c87UL, 0x527bf8b7UL,
    0xab73d323UL, 0x724b02e2UL, 0xe31f8f57UL, 0x6655ab2aUL,
    0xb2eb2807UL, 0x2fb5c203UL, 0x86c57b9aUL, 0xd33708a5UL,
    0x302887f2UL, 0x23bfa5b2UL, 0x02036abaUL, 0xed16825cUL,
    0x8acf1c2bUL, 0xa779b492UL, 0xf307f2f0UL, 0x4e69e2a1UL,
    0x65daf4cdUL, 0x0605bed5UL, 0xd134621fUL, 0xc4a6fe8aUL,
    0x342e539dUL, 0xa2f355a0UL, 0x058ae132UL, 0xa4f6eb75UL,
    0x0b83ec39UL, 0x4060efaaUL, 0x5e719f06UL, 0xbd6e1051UL,
    0x3e218af9UL, 0x96dd063dUL, 0xdd3e05aeUL, 0x4de6bd46UL,
    0x91548db5UL, 0x71c45d05UL, 0x0406d46fUL, 0x605015ffUL,
    0x1998fb24UL, 0xd6bde997UL, 0x894043ccUL, 0x67d99e77UL,
    0xb0e842bdUL, 0x07898b88UL, 0xe7195b38UL, 0x79c8eedbUL,
    0xa17c0a47UL, 0x7c420fe9UL, 0xf8841ec9UL, 0x00000000UL,
    0x09808683UL, 0x322bed48UL, 0x1e1170acUL, 0x6c5a724eUL,
    0xfd0efffbUL, 0x0f853856UL, 0x3daed51eUL, 0x362d3927UL,
    0x0a0fd964UL, 0x685ca621UL, 0x9b5b54d1UL, 0x24362e3aUL,
    0x0c0a67b1UL, 0x9357e70fUL, 0xb4ee96d2UL, 0x1b9b919eUL,
    0x80c0c54fUL, 0x61dc20a2UL, 0x5a774b69UL, 0x1c121a16UL,
    0xe293ba0aUL, 0xc0a02ae5UL, 0x3c22e043UL, 0x121b171dUL,
    0x0e090d0bUL, 0xf28bc7adUL, 0x2db6a8b9UL, 0x141ea9c8UL,
    0x57f11985UL, 0xaf75074cUL, 0xee99ddbbUL, 0xa37f60fdUL,
    0xf701269fUL, 0x5c72f5bcUL, 0x44663bc5UL, 0x5bfb7e34UL,
    0x8b432976UL, 0xcb23c6dcUL, 0xb6edfc68UL, 0xb8e4f163UL,
    0xd731dccaUL, 0x42638510UL, 0x13972240UL, 0x84c61120UL,
    0x854a247dUL, 0xd2bb3df8UL, 0xaef93211UL, 0xc729a16dUL,
    0x1d9e2f4bUL, 0xdcb230f3UL, 0x0d8652ecUL, 0x77c1e3d0UL,
    0x2bb3166cUL, 0xa970b999UL, 0x119448faUL, 0x47e96422UL,
    0xa8fc8cc4UL, 0xa0f03f1aUL, 0x567d2cd8UL, 0x223390efUL,
    0x87494ec7UL, 0xd938d1c1UL, 0x8ccaa2feUL, 0x98d40b36UL,
    0xa6f581cfUL, 0xa57ade28UL, 0xdab78e26UL, 0x3fadbfa4UL,
    0x2c3a9de4UL, 0x5078920dUL, 0x6a5fcc9bUL, 0x547e4662UL,
    0xf68d13c2UL, 0x90d8b8e8UL, 0x2e39f75eUL, 0x82c3aff5UL,
    0x9f5d80beUL, 0x69d0937cUL, 0x6fd52da9UL, 0xcf2512b3UL,
    0xc8ac993bUL, 0x10187da7UL, 0xe89c636eUL, 0xdb3bbb7bUL,
    0xcd267809UL, 0x6e5918f4UL, 0xec9ab701UL, 0x834f9aa8UL,
    0xe6956e65UL, 0xaaffe67eUL, 0x21bccf08UL, 0xef15e8e6UL,
    0xbae79bd9UL, 0x4a6f36ceUL, 0xea9f09d4UL, 0x29b07cd6UL,
    0x31a4b2afUL, 0x2a3f2331UL, 0xc6a59430UL, 0x35a266c0UL,
    0x744ebc37UL, 0xfc82caa6UL, 0xe090d0b0UL, 0x33a7d815UL,
    0xf104984aUL, 0x41ecdaf7UL, 0x7fcd500eUL, 0x1791f62fUL,
    0x764dd68dUL, 0x43efb04dUL, 0xccaa4d54UL, 0xe49604dfUL,
    0x9ed1b5e3UL, 0x4c6a881bUL, 0xc12c1fb8UL, 0x4665517fUL,
    0x9d5eea04UL, 0x018c355dUL, 0xfa877473UL, 0xfb0b412eUL,
    0xb3671d5aUL, 0x92dbd252UL, 0xe9105633UL, 0x6dd64713UL,
    0x9ad7618cUL, 0x37a10c7aUL, 0x59f8148eUL, 0xeb133c89UL,
    0xcea927eeUL, 0xb761c935UL, 0xe11ce5edUL, 0x7a47b13cUL,
    0x9cd2df59UL, 0x55f2733fUL, 0x1814ce79UL, 0x73c737bfUL,
    0x53f7cdeaUL, 0x5ffdaa5bUL, 0xdf3d6f14UL, 0x7844db86UL,
    0xcaaff381UL, 0xb968c43eUL, 0x3824342cUL, 0xc2a3405fUL,
    0x161dc372UL, 0xbce2250cUL, 0x283c498bUL, 0xff0d9541UL,
    0x39a80171UL, 0x080cb3deUL, 0xd8b4e49cUL, 0x6456c190UL,
    0x7bcb8461UL, 0xd532b670UL, 0x486c5c74UL, 0xd0b85742UL,
};

/* table to use for the last round of decryption */
static const uint8_t SINVBOX[256] = {
    0x52, 0x09, 0x6a, 0xd5,
    0x30, 0x36, 0xa5, 0x38,
    0xbf, 0x40, 0xa3, 0x9e,
    0x81, 0xf3, 0xd7, 0xfb,
    0x7c, 0xe3, 0x39, 0x82,
    0x9b, 0x2f, 0xff, 0x87,
    0x34, 0x8e, 0x43, 0x44,
    0xc4, 0xde, 0xe9, 0xcb,
    0x54, 0x7b, 0x94, 0x32,
    0xa6, 0xc2, 0x23, 0x3d,
    0xee, 0x4c, 0x95, 0x0b,
    0x42, 0xfa, 0xc3, 0x4e,
    0x08, 0x2e, 0xa1, 0x66,
    0x28, 0xd9, 0x24, 0xb2,
    0x76, 0x5b, 0xa2, 0x49,
    0x6d, 0x8b, 0xd1, 0x25,
    0x72, 0xf8, 0xf6, 0x64,
    0x86, 0x68, 0x98, 0x16,
    0xd4, 0xa4, 0x5c, 0xcc,
    0x5d, 0x65, 0xb6, 0x92,
    0x6c, 0x70, 0x48, 0x50,
    0xfd, 0xed, 0xb9, 0xda,
    0x5e, 0x15, 0x46, 0x57,
    0xa7, 0x8d, 0x9d, 0x84,
    0x90, 0xd8, 0xab, 0x00,
    0x8c, 0xbc, 0xd3, 0x0a,
    0xf7, 0xe4, 0x58, 0x05,
    0xb8, 0xb3, 0x45, 0x06,
    0xd0, 0x2c, 0x1e, 0x8f,
    0xca, 0x3f, 0x0f, 0x02,
    0xc1, 0xaf, 0xbd, 0x03,
    0x01, 0x13, 0x8a, 0x6b,
    0x3a, 0x91, 0x11, 0x41,
    0x4f, 0x67, 0xdc, 0xea,
    0x97, 0xf2, 0xcf, 0xce,
    0xf0, 0xb4, 0xe6, 0x73,
    0x96, 0xac, 0x74, 0x22,
    0xe7, 0xad, 0x35, 0x85,
    0xe2, 0xf9, 0x37, 0xe8,
    0x1c, 0x75, 0xdf, 0x6e,
    0x47, 0xf1, 0x1a, 0x71,
    0x1d, 0x29, 0xc5, 0x89,
    0x6f, 0xb7, 0x62, 0x0e,
    0xaa, 0x18, 0xbe, 0x1b,
    0xfc, 0x56, 0x3e, 0x4b,
    0xc6, 0xd2, 0x79, 0x20,
    0x9a, 0xdb, 0xc0, 0xfe,
    0x78, 0xcd, 0x5a, 0xf4,
    0x1f, 0xdd, 0xa8, 0x33,
    0x88, 0x07, 0xc7, 0x31,
    0xb1, 0x12, 0x10, 0x59,
    0x27, 0x80, 0xec, 0x5f,
    0x60, 0x51, 0x7f, 0xa9,
    0x19, 0xb5, 0x4a, 0x0d,
    0x2d, 0xe5, 0x7a, 0x9f,
    0x93, 0xc9, 0x9c, 0xef,
    0xa0, 0xe0, 0x3b, 0x4d,
    0xae, 0x2a, 0xf5, 0xb0,
    0xc8, 0xeb, 0xbb, 0x3c,
    0x83, 0x53, 0x99, 0x61,
    0x17, 0x2b, 0x04, 0x7e,
    0xba, 0x77, 0xd6, 0x26,
    0xe1, 0x69, 0x14, 0x63,
    0x55, 0x21, 0x0c, 0x7d,
};

/* round key constants */
static const uint32_t RCON[] = {
    0x01000000 /* x^0, 0, 0, 0 */ ,
    0x02000000 /* x^1, 0, 0, 0 */ ,
    0x04000000,
    0x08000000,
    0x10000000,
    0x20000000,
    0x40000000,
    0x80000000,
    0x1B000000,            /* x^8, 0, 0, 0 mod x^8 + x^4 + x^3 + x + 1 */
    0x36000000,
};

/* ---------------------------------------------------- */
/**
 * AES encryption function with
 * Nb = 4, where Nb is the block length / 32
 *
 * @param keyCtx The expanded key
 *
 * @param in the input (cleartext). Must be one block length.
 *
 * @param out the output (encrypted text). Must be one block length.
 *
 */
void aesImplAesCipher(
    const aesKeyCtx_t *keyCtx,
    const uint8_t in[16 /* 4 * Nb */ ],
    uint8_t out[16 /* 4 * Nb */ ])
{

    /* the state is a one-dimensional array of 32 bit words,
     * each word being a column (see FIPS 197, section 3.5)
     * Avoid table indexes and use one variable per column
     */
    uint32_t state0, state1, state2, state3;
    uint32_t temp0, temp1, temp2, temp3;
    const uint32_t *roundKey;
    uint8_t round;

    /* state = in */
    AES_LOAD32B(state0, in);
    AES_LOAD32B(state1, in + 4);
    AES_LOAD32B(state2, in + 8);
    AES_LOAD32B(state3, in + 12);

    const uint32_t *keySchedule = keyCtx->keySchedule;
    const uint8_t Nr = keyCtx->nRounds;

    /* add round key 0 */
    state0 ^= keySchedule[0];
    state1 ^= keySchedule[1];
    state2 ^= keySchedule[2];
    state3 ^= keySchedule[3];

    /* all rounds are similar, except the last one */
    for (round = 1, roundKey = &keySchedule[4]; round < Nr; round++) {
        /* the different steps of the round transformation
         * can be combined in a single set of table lookups
         * For 16 byte blocks, using a single table, we have:
         * ej = kj XOR T0[a0,j]
         *         XOR Rotbyte(T0[a1,j+1 mod 4]
         *         XOR Rotbyte(T0[a2,j+2 mod 4]
         *         XOR Rotbyte(T0[a3,j+3 mod 4])))
         *
         * which is equivalent to
         *
         * ej = kj XOR T0[a0,j]
         *         XOR rotation of one byte(T0[a1,j+1 mod 4])
         *         XOR rotation of two bytes(T0[a2,j+2 mod 4])
         *         XOR rotation of three bytes(T0[a3,j+3 mod 4])
         *
         * NB. This works for 32-bit processors
         *
         * See "The Rijndael Block Cipher" AES Proposal
         * by Joan Daemen and Vincent Rijmen
         * http://csrc.nist.gov/encryption/aes/rijndael/Rijndael.pdf
         * sections 5.2.1
         * and FIPS 197 section 4.2.2 and 5.1
         */
        temp0 = roundKey[0] ^ TE[(state0 >> 24) & 0xff]
                            ^ AES_ROR(TE[(state1 >> 16) & 0xff], 8)
                            ^ AES_ROR(TE[(state2 >> 8) & 0xff], 16)
                            ^ AES_ROR(TE[(state3) & 0xff], 24);

        temp1 = roundKey[1] ^ TE[(state1 >> 24) & 0xff]
                            ^ AES_ROR(TE[(state2 >> 16) & 0xff], 8)
                            ^ AES_ROR(TE[(state3 >> 8) & 0xff], 16)
                            ^ AES_ROR(TE[(state0) & 0xff], 24);

        temp2 = roundKey[2] ^ TE[(state2 >> 24) & 0xff]
                            ^ AES_ROR(TE[(state3 >> 16) & 0xff], 8)
                            ^ AES_ROR(TE[(state0 >> 8) & 0xff], 16)
                            ^ AES_ROR(TE[(state1) & 0xff], 24);

        temp3 = roundKey[3] ^ TE[(state3 >> 24) & 0xff]
                            ^ AES_ROR(TE[(state0 >> 16) & 0xff], 8)
                            ^ AES_ROR(TE[(state1 >> 8) & 0xff], 16)
                            ^ AES_ROR(TE[(state2) & 0xff], 24);

        state0 = temp0;
        state1 = temp1;
        state2 = temp2;
        state3 = temp3;

        roundKey = &(roundKey[4]);
    }

    /*
     * In the last round, there is no MixColumn transformation,
     * This means we must use the Sbox instead of the transformation
     * tables :
     * ej = kj XOR Sbox[a0,j].[1, 1, 1, 1]
     *         XOR Sbox[a1,j+1 mod 4]   .[1, 1, 1, 1]
     *         XOR Sbox[a2,j+2 mod 4]   .[1, 1, 1, 1]
     *         XOR Sbox[a3,j+3 mod 4]   .[1, 1, 1, 1]
     */
    temp0 = roundKey[0] ^ (SBOX[(state0 >> 24)] << 24 /* 1st byte */ )
                        ^ (SBOX[(state1 >> 16) & 0xff] << 16 /* 2nd byte */ )
                        ^ (SBOX[(state2 >> 8) & 0xff] << 8 /* 3rd byte */ )
                        ^ (SBOX[(state3) & 0xff] /* 4th byte */ );

    temp1 = roundKey[1] ^ (SBOX[(state1 >> 24) & 0xff] << 24)
                        ^ (SBOX[(state2 >> 16) & 0xff] << 16)
                        ^ (SBOX[(state3 >> 8) & 0xff] << 8)
                        ^ (SBOX[(state0) & 0xff]);

    temp2 = roundKey[2] ^ (SBOX[(state2 >> 24) & 0xff] << 24)
                        ^ (SBOX[(state3 >> 16) & 0xff] << 16)
                        ^ (SBOX[(state0 >> 8) & 0xff] << 8)
                        ^ (SBOX[(state1) & 0xff]);

    temp3 = roundKey[3] ^ (SBOX[(state3 >> 24) & 0xff] << 24)
                        ^ (SBOX[(state0 >> 16) & 0xff] << 16)
                        ^ (SBOX[(state1 >> 8) & 0xff] << 8)
                        ^ (SBOX[(state2) & 0xff]);

    AES_STORE32B(temp0, out);
    AES_STORE32B(temp1, out + 4);
    AES_STORE32B(temp2, out + 8);
    AES_STORE32B(temp3, out + 12);
}

/* ---------------------------------------------------- */
/**
 * This is the implementation of the Equivalent Inverse
 * Cipher (FIPS 197, section 5.3.5). It uses
 * the same sequence of operations as for the Cipher
 * but must use a modified key schedule.
 *
 * @param keyCtx The expanded key.
 *
 * @param in the encrypted text. Its size must be a block
 * size.
 *
 * @param out the cleartext (result). A block's size.
 */
void aesImplAesInvCipher(
    const aesKeyCtx_t *keyCtx,
    const uint8_t in[16 /* 4 * Nb */ ],    /* encrypted text */
    uint8_t out[16 /* 4 * Nb */ ])
{
    uint32_t state0, state1, state2, state3;
    uint32_t temp0, temp1, temp2, temp3;
    const uint32_t *roundKey;
    uint8_t round;

    /* state = in */
    AES_LOAD32B(state0, in);
    AES_LOAD32B(state1, in + 4);
    AES_LOAD32B(state2, in + 8);
    AES_LOAD32B(state3, in + 12);

    const uint32_t *keySchedule = keyCtx->keySchedule;
    const uint8_t Nr = keyCtx->nRounds;

    /* add last round key */
    state0 ^= keySchedule[Nr * 4];
    state1 ^= keySchedule[(Nr * 4) + 1];
    state2 ^= keySchedule[(Nr * 4) + 2];
    state3 ^= keySchedule[(Nr * 4) + 3];

    /* all rounds are similar, except the last one */
    for (round = (Nr - 1); round > 0; round--) {
        roundKey = &keySchedule[round * 4];

        /* ej = kj XOR T'0[a0,j]
         *         XOR rotation of one byte(T'0[a1,j-1 mod 4])
         *         XOR rotation of two bytes(T'0[a2,j-2 mod 4])
         *         XOR rotation of three bytes(T'0[a3,j-3 mod 4])
         */
        temp0 = roundKey[0] ^ TD[(state0 >> 24) & 0xff]
                            ^ AES_ROR(TD[(state3 >> 16) & 0xff], 8)
                            ^ AES_ROR(TD[(state2 >> 8) & 0xff], 16)
                            ^ AES_ROR(TD[(state1) & 0xff], 24);

        temp1 = roundKey[1] ^ TD[(state1 >> 24) & 0xff]
                            ^ AES_ROR(TD[(state0 >> 16) & 0xff], 8)
                            ^ AES_ROR(TD[(state3 >> 8) & 0xff], 16)
                            ^ AES_ROR(TD[(state2) & 0xff], 24);

        temp2 = roundKey[2] ^ TD[(state2 >> 24) & 0xff]
                            ^ AES_ROR(TD[(state1 >> 16) & 0xff], 8)
                            ^ AES_ROR(TD[(state0 >> 8) & 0xff], 16)
                            ^ AES_ROR(TD[(state3) & 0xff], 24);

        temp3 = roundKey[3] ^ TD[(state3 >> 24) & 0xff]
                            ^ AES_ROR(TD[(state2 >> 16) & 0xff], 8)
                            ^ AES_ROR(TD[(state1 >> 8) & 0xff], 16)
                            ^ AES_ROR(TD[(state0) & 0xff], 24);

        state0 = temp0;
        state1 = temp1;
        state2 = temp2;
        state3 = temp3;
    }

    /* last round - uses round key 0
     * ej = kj XOR InvSbox[a0,j].[1, 1, 1, 1]
     *         XOR InvSbox[a1,j-1 mod 4]   .[1, 1, 1, 1]
     *         XOR InvSbox[a2,j-2 mod 4]   .[1, 1, 1, 1]
     *         XOR InvSbox[a3,j-3 mod 4]   .[1, 1, 1, 1]
     */
    temp0 = keySchedule[0] ^ (SINVBOX[(state0 >> 24) & 0xff] << 24)
                           ^ (SINVBOX[(state3 >> 16) & 0xff] << 16)
                           ^ (SINVBOX[(state2 >> 8) & 0xff] << 8)
                           ^ (SINVBOX[(state1) & 0xff]);

    temp1 = keySchedule[1] ^ (SINVBOX[(state1 >> 24) & 0xff] << 24)
                           ^ (SINVBOX[(state0 >> 16) & 0xff] << 16)
                           ^ (SINVBOX[(state3 >> 8) & 0xff] << 8)
                           ^ (SINVBOX[(state2) & 0xff]);

    temp2 = keySchedule[2] ^ (SINVBOX[(state2 >> 24) & 0xff] << 24)
                           ^ (SINVBOX[(state1 >> 16) & 0xff] << 16)
                           ^ (SINVBOX[(state0 >> 8) & 0xff] << 8)
                           ^ (SINVBOX[(state3) & 0xff]);

    temp3 = keySchedule[3] ^ (SINVBOX[(state3 >> 24) & 0xff] << 24)
                           ^ (SINVBOX[(state2 >> 16) & 0xff] << 16)
                           ^ (SINVBOX[(state1 >> 8) & 0xff] << 8)
                           ^ (SINVBOX[(state0) & 0xff]);

    AES_STORE32B(temp0, out);
    AES_STORE32B(temp1, out + 4);
    AES_STORE32B(temp2, out + 8);
    AES_STORE32B(temp3, out + 12);
}

/**
 * Set up key schedule for AES forward cipher.
 */
void aesImplAesExpandKey(
    const tlApiSymKey_t *key,
    aesKeyCtx_t *keyCtx)
{
    keyCtx->nRounds = AES_N_ROUNDS(key->len); /* (10, 12 or 14) */
    uint8_t Nk = key->len / 4; /* key length in words (4, 6 or 8) */
    uint8_t i, r, rNk;

    /* the first Nk words are filled with the cipher key */
    for (i = 0; i < Nk; i++) {
        AES_LOAD32B(keyCtx->keySchedule[i], key->key + 4*i);
    }

    i = Nk; r = 1; rNk = Nk;
    while (i < (4 * (keyCtx->nRounds + 1))) { /* i < 60 */
        uint32_t temp = keyCtx->keySchedule[i - 1];
        if (i == rNk) {
            /* SubWord(RotWord(temp)) XOR Rcon[i/Nk]
             * where SubWord applies the Sbox
             * and RotWord performs a cyclic permutation to the left
             */
            temp = (SBOX[(temp >> 16 /* get second byte */ ) & 0xff] << 24)
                 ^ (SBOX[(temp >> 8 /* get third byte */ ) & 0xff] << 16)
                 ^ (SBOX[temp & 0xff /* get fourth byte */ ] << 8)
                 ^ (SBOX[(temp >> 24 /* get first byte */ ) & 0xff])
                 ^ RCON[r - 1];
            r++; rNk += Nk;
        } else if ((Nk == 8) && ((i & 7) == 4)) {
            /* SubWord(temp) */
            temp = (SBOX[(temp >> 24) & 0xff] << 24)
                 ^ (SBOX[(temp >> 16) & 0xff] << 16)
                 ^ (SBOX[(temp >> 8) & 0xff] << 8)
                 ^ (SBOX[temp & 0xff]);
        }
        keyCtx->keySchedule[i] = keyCtx->keySchedule[i - Nk] ^ temp;
        i++;
    }
}

/**
 * Set up key schedule for AES inverse cipher.
 */
void aesImplAesInvExpandKey(
    const tlApiSymKey_t *key,
    aesKeyCtx_t *keyCtx)
{
    aesImplAesExpandKey(key, keyCtx);

    uint8_t i;

    for (i = 4; i < 4 * keyCtx->nRounds; i++) {
        /* InvMixColumns all keys except first and last one
         * Instead of coding .[0e, 09, 0d, 0b]
         * we rather use existing tables:
         * SboxInv(Sbox(a)).[0e, 09, 0d, 0b]
         */
        keyCtx->keySchedule[i] = TD[SBOX[(keyCtx->keySchedule[i] >> 24)]]
                               ^ AES_ROR(TD[SBOX[(keyCtx->keySchedule[i] >> 16) & 0xff]], 8)
                               ^ AES_ROR(TD[SBOX[(keyCtx->keySchedule[i] >> 8) & 0xff]], 16)
                               ^ AES_ROR(TD[SBOX[(keyCtx->keySchedule[i]) & 0xff]], 24);
    }
}

/* ---------------------------------------------------- */

static const uint8_t AES_WRAP_IV[] = {
    0xA6, 0xA6, 0xA6, 0xA6, 0xA6, 0xA6, 0xA6, 0xA6
};

tlApiResult_t aesAESWrapKeyRFC3394(
   const tlApiSymKey_t *wrappingKey,
   const tlApiSymKey_t *sourceKey,
   tlApiSymKey_t *wrappedKey)
{
    if (!AES_IS_VALID_KEY(wrappingKey) ||
        !AES_IS_VALID_KEY(sourceKey) ||
        (wrappedKey == NULL) ||
        (wrappedKey->key == NULL) ||
        (sourceKey->len + 8 != wrappedKey->len))
    {
        return E_TLAPI_INVALID_PARAMETER;
    }

    aesKeyCtx_t keyCtx = {0};
    struct Register64bit {
        uint8_t reg[8];
    };
    struct Register64bit B[2];
    struct Register64bit R[4];

    aesImplAesExpandKey(wrappingKey, &keyCtx);

    uint32_t n = sourceKey->len / 8; /* number of 64-bit key data blocks (2,3 or 4) */

    /* Set A0 to an initial value */
    memmove(&B[0], AES_WRAP_IV, sizeof(AES_WRAP_IV));

    memmove(R, sourceKey->key, 8*n);

    for (uint32_t j = 0; j < 6; j++) {
        for (uint32_t i = 0; i < n; i++) {
            memmove(&B[1], &R[i], 8);

            aesImplAesCipher(&keyCtx, (const uint8_t*)B, (uint8_t*)B);

            uint32_t t = n*j + i + 1;
            B[0].reg[7] ^= 0xFF & (t);
            B[0].reg[6] ^= 0xFF & (t >> 8);
            B[0].reg[5] ^= 0xFF & (t >> 16);
            B[0].reg[4] ^= 0xFF & (t >> 24);
            memmove(&R[i], &B[1], 8);
        }
    }

    memmove(wrappedKey->key, &B[0], 8);
    memmove(wrappedKey->key + 8, R, 8*n);

    return TLAPI_OK;
}

/* ---------------------------------------------------- */

tlApiResult_t aesAESUnwrapKeyRFC3394(
    const tlApiSymKey_t *unwrappingKey,
    tlApiSymKey_t *targetKey,
    const tlApiSymKey_t *wrappedKey)
{
    if (!AES_IS_VALID_KEY(unwrappingKey) ||
        !AES_IS_VALID_KEY(targetKey) ||
        (wrappedKey == NULL) ||
        (wrappedKey->key == NULL) ||
        (targetKey->len + 8 != wrappedKey->len))
    {
        return E_TLAPI_INVALID_PARAMETER;
    }

    tlApiResult_t sret = TLAPI_OK;
    aesKeyCtx_t keyCtx = {0};
    struct Register64bit {
        uint8_t reg[8];
    };
    struct Register64bit B[2];
    struct Register64bit R[4];

    aesImplAesInvExpandKey(unwrappingKey, &keyCtx);

    uint32_t n = targetKey->len / 8; /* number of 64-bit key data blocks (2,3 or 4) */

    /* Set A0 to an initial value */
    memmove(&B[0], wrappedKey->key, 8);

    memmove(R, wrappedKey->key + 8, 8*n);

    for (uint32_t j = 0; j < 6; j++) {
        for (uint32_t i = 0; i < n; i++) {
            uint32_t t = n*(6-j) - i;
            B[0].reg[7] ^= 0xFF & (t);
            B[0].reg[6] ^= 0xFF & (t >> 8);
            B[0].reg[5] ^= 0xFF & (t >> 16);
            B[0].reg[4] ^= 0xFF & (t >> 24);

            memmove(&B[1], &R[n-1-i], 8);

            aesImplAesInvCipher(&keyCtx, (const uint8_t*)B, (uint8_t*)B);

            memmove(&R[n-1-i], &B[1], 8);
        }
    }

    if (memcmp(&B[0], AES_WRAP_IV, sizeof(AES_WRAP_IV)) != 0) {
        sret = E_TLAPI_INVALID_PARAMETER;
    } else {
        memmove(targetKey->key, R, 8*n);
        sret = TLAPI_OK;
    }

    return sret;
}
