/* A Bison parser, made by GNU Bison 2.7.  */

/* Skeleton implementation for Bison GLR parsers in C
   
      Copyright (C) 2002-2012 Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* C GLR parser skeleton written by Paul Hilfinger.  */

/* Identify Bison output.  */
#define YYBISON 1

/* Bison version.  */
#define YYBISON_VERSION "2.7"

/* Skeleton name.  */
#define YYSKELETON_NAME "glr.cc"

/* Pure parsers.  */
#define YYPURE 1






/* Copy the first part of user declarations.  */
/* Line 207 of glr.c  */
#line 10 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"

/* C/C++ Declarations */
#include "mcld/Script/ScriptReader.h"
#include "mcld/Script/ScriptScanner.h"
#include "mcld/Script/Operand.h"
#include "mcld/Script/Operator.h"
#include "mcld/Script/Assignment.h"
#include "mcld/Script/RpnExpr.h"
#include "mcld/Script/FileToken.h"
#include "mcld/Script/NameSpec.h"
#include "mcld/Script/WildcardPattern.h"
#include "mcld/Support/MsgHandling.h"
using namespace mcld;

#undef yylex
#define yylex m_ScriptScanner.lex

/* Line 207 of glr.c  */
#line 74 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"

# ifndef YY_NULL
#  if defined __cplusplus && 201103L <= __cplusplus
#   define YY_NULL nullptr
#  else
#   define YY_NULL 0
#  endif
# endif

#include "ScriptParser.hpp"

/* Enabling verbose error messages.  */
#ifdef YYERROR_VERBOSE
# undef YYERROR_VERBOSE
# define YYERROR_VERBOSE 1
#else
# define YYERROR_VERBOSE 1
#endif

/* Default (constant) value used for initialization for null
   right-hand sides.  Unlike the standard yacc.c template, here we set
   the default value of $$ to a zeroed-out value.  Since the default
   value is undefined, this behavior is technically correct.  */
static YYSTYPE yyval_default;
static YYLTYPE yyloc_default
# if defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL
  = { 1, 1, 1, 1 }
# endif
;

/* Copy the second part of user declarations.  */
/* Line 230 of glr.c  */
#line 107 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"
/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

# ifndef YYLLOC_DEFAULT
#  define YYLLOC_DEFAULT(Current, Rhs, N)                               \
    do                                                                  \
      if (N)                                                            \
        {                                                               \
          (Current).begin  = YYRHSLOC (Rhs, 1).begin;                   \
          (Current).end    = YYRHSLOC (Rhs, N).end;                     \
        }                                                               \
      else                                                              \
        {                                                               \
          (Current).begin = (Current).end = YYRHSLOC (Rhs, 0).end;      \
        }                                                               \
    while (/*CONSTCOND*/ false)
# endif

#define YYRHSLOC(Rhs, K) ((Rhs)[K].yystate.yyloc)
static void yyerror (const mcld::ScriptParser::location_type *yylocationp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader, const char* msg);
/* Line 230 of glr.c  */
#line 130 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifndef YY_
# if defined YYENABLE_NLS && YYENABLE_NLS
#  if ENABLE_NLS
#   include <libintl.h> /* INFRINGES ON USER NAME SPACE */
#   define YY_(Msgid) dgettext ("bison-runtime", Msgid)
#  endif
# endif
# ifndef YY_
#  define YY_(Msgid) Msgid
# endif
#endif

/* Suppress unused-variable warnings by "using" E.  */
#if ! defined lint || defined __GNUC__
# define YYUSE(E) ((void) (E))
#else
# define YYUSE(E) /* empty */
#endif

/* Identity function, used to suppress warnings about constant conditions.  */
#ifndef lint
# define YYID(N) (N)
#else
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static int
YYID (int i)
#else
static int
YYID (i)
    int i;
#endif
{
  return i;
}
#endif

#ifndef YYFREE
# define YYFREE free
#endif
#ifndef YYMALLOC
# define YYMALLOC malloc
#endif
#ifndef YYREALLOC
# define YYREALLOC realloc
#endif

#define YYSIZEMAX ((size_t) -1)

#ifdef __cplusplus
   typedef bool yybool;
#else
   typedef unsigned char yybool;
#endif
#define yytrue 1
#define yyfalse 0

#ifndef YYSETJMP
# include <setjmp.h>
# define YYJMP_BUF jmp_buf
# define YYSETJMP(Env) setjmp (Env)
/* Pacify clang.  */
# define YYLONGJMP(Env, Val) (longjmp (Env, Val), YYASSERT (0))
#endif

/*-----------------.
| GCC extensions.  |
`-----------------*/

#ifndef __attribute__
/* This feature is available in gcc versions 2.5 and later.  */
# if (! defined __GNUC__ || __GNUC__ < 2 \
      || (__GNUC__ == 2 && __GNUC_MINOR__ < 5))
#  define __attribute__(Spec) /* empty */
# endif
#endif

#ifndef YYASSERT
# define YYASSERT(Condition) ((void) ((Condition) || (abort (), 0)))
#endif

/* YYFINAL -- State number of the termination state.  */
#define YYFINAL  4
/* YYLAST -- Last index in YYTABLE.  */
#define YYLAST   1303

/* YYNTOKENS -- Number of terminals.  */
#define YYNTOKENS  120
/* YYNNTS -- Number of nonterminals.  */
#define YYNNTS  67
/* YYNRULES -- Number of rules.  */
#define YYNRULES  189
/* YYNRULES -- Number of states.  */
#define YYNSTATES  446
/* YYMAXRHS -- Maximum number of symbols on right-hand side of rule.  */
#define YYMAXRHS 8
/* YYMAXLEFT -- Maximum number of symbols to the left of a handle
   accessed by $0, $-1, etc., in any rule.  */
#define YYMAXLEFT 0

/* YYTRANSLATE(X) -- Bison symbol number corresponding to X.  */
#define YYUNDEFTOK  2
#define YYMAXUTOK   352

#define YYTRANSLATE(YYX)                                                \
  ((unsigned int) (YYX) <= YYMAXUTOK ? yytranslate[YYX] : YYUNDEFTOK)

/* YYTRANSLATE[YYLEX] -- Bison symbol number corresponding to YYLEX.  */
static const unsigned char yytranslate[] =
{
       0,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,   110,   119,     2,     2,   109,    96,     2,
     115,   116,   107,   105,    80,   106,     2,   108,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,    91,   114,
      99,    81,   100,    90,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,    95,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,   117,    94,   118,   111,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     2,     2,     2,     2,
       2,     2,     2,     2,     2,     2,     1,     2,     3,     4,
       5,     6,     7,     8,     9,    10,    11,    12,    13,    14,
      15,    16,    17,    18,    19,    20,    21,    22,    23,    24,
      25,    26,    27,    28,    29,    30,    31,    32,    33,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,    53,    54,
      55,    56,    57,    58,    59,    60,    61,    62,    63,    64,
      65,    66,    67,    68,    69,    70,    71,    72,    73,    74,
      75,    76,    77,    78,    79,    82,    83,    84,    85,    86,
      87,    88,    89,    92,    93,    97,    98,   101,   102,   103,
     104,   112,   113
};

#if YYDEBUG
/* YYPRHS[YYN] -- Index of the first RHS symbol of rule number YYN in
   YYRHS.  */
static const unsigned short int yyprhs[] =
{
       0,     0,     3,     4,     8,    11,    12,    14,    16,    18,
      20,    22,    24,    26,    28,    30,    32,    34,    39,    44,
      53,    58,    63,    68,    73,    78,    85,    86,    89,    91,
      94,    98,    99,   105,   106,   113,   114,   122,   124,   126,
     127,   133,   136,   137,   139,   141,   143,   144,   152,   153,
     154,   163,   166,   167,   172,   175,   177,   181,   184,   185,
     187,   189,   191,   193,   195,   200,   201,   206,   207,   212,
     213,   215,   217,   218,   221,   222,   226,   227,   228,   231,
     235,   236,   238,   241,   242,   244,   246,   248,   250,   252,
     254,   259,   261,   267,   269,   274,   276,   278,   280,   281,
     287,   288,   291,   293,   294,   297,   300,   302,   304,   309,
     314,   319,   327,   335,   343,   351,   356,   361,   366,   371,
     376,   381,   383,   385,   390,   395,   400,   405,   410,   415,
     420,   425,   430,   435,   443,   451,   459,   460,   463,   467,
     470,   473,   476,   479,   483,   487,   491,   495,   499,   503,
     507,   511,   515,   519,   523,   527,   531,   535,   539,   543,
     547,   551,   557,   562,   567,   572,   579,   584,   589,   590,
     598,   603,   610,   615,   620,   625,   632,   639,   644,   649,
     650,   658,   663,   665,   670,   675,   677,   679,   681,   683
};

/* YYRHS -- A `-1'-separated list of the rules' RHS.  */
static const short int yyrhs[] =
{
     121,     0,    -1,    -1,     6,   122,   123,    -1,   123,   124,
      -1,    -1,   125,    -1,   126,    -1,   127,    -1,   128,    -1,
     130,    -1,   129,    -1,   131,    -1,   132,    -1,   179,    -1,
     140,    -1,   114,    -1,    10,   115,     3,   116,    -1,    18,
     115,     3,   116,    -1,    18,   115,     3,    80,     3,    80,
       3,   116,    -1,    13,   115,   133,   116,    -1,    12,   115,
     133,   116,    -1,    16,   115,     3,   116,    -1,    15,   115,
       3,   116,    -1,    26,   115,     3,   116,    -1,    20,   115,
     180,    80,   186,   116,    -1,    -1,   134,   135,    -1,   139,
      -1,   135,   139,    -1,   135,    80,   139,    -1,    -1,    14,
     115,   136,   135,   116,    -1,    -1,   135,    14,   115,   137,
     135,   116,    -1,    -1,   135,    80,    14,   115,   138,   135,
     116,    -1,   186,    -1,     4,    -1,    -1,    31,   141,   117,
     142,   118,    -1,   142,   143,    -1,    -1,   125,    -1,   179,
      -1,   144,    -1,    -1,   186,   146,   145,   117,   149,   118,
     150,    -1,    -1,    -1,   147,   151,   148,    91,   154,   155,
     156,   157,    -1,   149,   165,    -1,    -1,   158,   159,   160,
     164,    -1,   182,   152,    -1,   152,    -1,   115,   153,   116,
      -1,   115,   116,    -1,    -1,    71,    -1,    72,    -1,    73,
      -1,    74,    -1,    75,    -1,    76,   115,   180,   116,    -1,
      -1,    36,   115,   180,   116,    -1,    -1,    77,   115,   180,
     116,    -1,    -1,    78,    -1,    79,    -1,    -1,   100,   186,
      -1,    -1,    76,   100,   186,    -1,    -1,    -1,   161,   162,
      -1,   162,    91,   163,    -1,    -1,   186,    -1,    81,   180,
      -1,    -1,   179,    -1,   166,    -1,   177,    -1,   178,    -1,
     114,    -1,   167,    -1,    57,   115,   167,   116,    -1,   186,
      -1,   168,   115,   170,   173,   116,    -1,   169,    -1,    58,
     115,   169,   116,    -1,   186,    -1,   107,    -1,    90,    -1,
      -1,    55,   115,   171,   172,   116,    -1,    -1,   172,   169,
      -1,   169,    -1,    -1,   174,   175,    -1,   175,   176,    -1,
     176,    -1,   169,    -1,    60,   115,   169,   116,    -1,    58,
     115,   169,   116,    -1,    59,   115,   169,   116,    -1,    58,
     115,    59,   115,   169,   116,   116,    -1,    59,   115,    58,
     115,   169,   116,   116,    -1,    58,   115,    58,   115,   169,
     116,   116,    -1,    59,   115,    59,   115,   169,   116,   116,
      -1,    61,   115,   169,   116,    -1,    62,   115,   180,   116,
      -1,    63,   115,   180,   116,    -1,    64,   115,   180,   116,
      -1,    65,   115,   180,   116,    -1,    66,   115,   180,   116,
      -1,    69,    -1,    70,    -1,    58,   115,    70,   116,    -1,
     185,    81,   180,   114,    -1,   185,    89,   182,   114,    -1,
     185,    88,   182,   114,    -1,   185,    87,   182,   114,    -1,
     185,    86,   182,   114,    -1,   185,    85,   182,   114,    -1,
     185,    84,   182,   114,    -1,   185,    83,   182,   114,    -1,
     185,    82,   182,   114,    -1,    28,   115,   185,    81,   180,
     116,   114,    -1,    29,   115,   185,    81,   180,   116,   114,
      -1,    30,   115,   185,    81,   180,   116,   114,    -1,    -1,
     181,   182,    -1,   115,   182,   116,    -1,   105,   182,    -1,
     106,   182,    -1,   110,   182,    -1,   111,   182,    -1,   182,
     107,   182,    -1,   182,   108,   182,    -1,   182,   109,   182,
      -1,   182,   105,   182,    -1,   182,   106,   182,    -1,   182,
     104,   182,    -1,   182,   103,   182,    -1,   182,    99,   182,
      -1,   182,   102,   182,    -1,   182,   100,   182,    -1,   182,
     101,   182,    -1,   182,    98,   182,    -1,   182,    97,   182,
      -1,   182,    96,   182,    -1,   182,    95,   182,    -1,   182,
      94,   182,    -1,   182,    93,   182,    -1,   182,    92,   182,
      -1,   182,    90,   182,    91,   182,    -1,    34,   115,   182,
     116,    -1,    35,   115,   186,   116,    -1,    36,   115,   182,
     116,    -1,    36,   115,   182,    80,   182,   116,    -1,    37,
     115,   186,   116,    -1,    38,   115,   182,   116,    -1,    -1,
      39,   183,   115,   182,    80,   182,   116,    -1,    40,   115,
     182,   116,    -1,    41,   115,   182,    80,   182,   116,    -1,
      42,   115,   185,   116,    -1,    43,   115,   186,   116,    -1,
      44,   115,   186,   116,    -1,    45,   115,   182,    80,   182,
     116,    -1,    46,   115,   182,    80,   182,   116,    -1,    47,
     115,   182,   116,    -1,    48,   115,   186,   116,    -1,    -1,
      49,   115,   186,   184,    80,   182,   116,    -1,    50,   115,
     186,   116,    -1,    51,    -1,    52,   115,    53,   116,    -1,
      52,   115,    54,   116,    -1,     5,    -1,   185,    -1,     3,
      -1,     3,    -1,   119,     3,   119,    -1
};

/* YYRLINE[YYN] -- source line where rule number YYN was defined.  */
static const unsigned short int yyrline[] =
{
       0,   209,   209,   208,   214,   215,   218,   219,   220,   221,
     222,   223,   224,   225,   226,   227,   228,   231,   235,   237,
     241,   245,   252,   256,   260,   264,   268,   268,   273,   275,
     277,   280,   279,   284,   283,   288,   287,   293,   295,   308,
     307,   313,   314,   325,   326,   327,   345,   344,   352,   358,
     352,   371,   372,   375,   385,   390,   397,   399,   402,   405,
     407,   409,   411,   413,   417,   420,   424,   427,   431,   434,
     437,   439,   442,   445,   448,   451,   454,   457,   457,   462,
     464,   467,   471,   474,   485,   486,   487,   488,   489,   492,
     494,   498,   505,   513,   515,   519,   521,   523,   528,   527,
     532,   535,   540,   547,   547,   552,   556,   562,   564,   566,
     568,   570,   572,   574,   576,   578,   582,   583,   584,   585,
     586,   589,   590,   591,   594,   596,   597,   598,   599,   600,
     601,   602,   603,   604,   609,   614,   621,   621,   632,   636,
     642,   648,   654,   660,   666,   672,   678,   684,   690,   696,
     702,   708,   714,   720,   726,   732,   738,   744,   750,   756,
     762,   768,   774,   780,   787,   796,   802,   809,   819,   818,
     828,   834,   840,   847,   851,   858,   864,   870,   876,   881,
     880,   890,   897,   903,   909,   915,   920,   927,   931,   933
};
#endif

#if YYDEBUG || YYERROR_VERBOSE || 1
/* YYTNAME[SYMBOL-NUM] -- String name of the symbol SYMBOL-NUM.
   First, the terminals, then, starting at YYNTOKENS, nonterminals.  */
static const char *const yytname[] =
{
  "END", "error", "$undefined", "STRING", "LNAMESPEC", "INTEGER",
  "LINKER_SCRIPT", "DEFSYM", "VERSION_SCRIPT", "DYNAMIC_LIST", "ENTRY",
  "INCLUDE", "INPUT", "GROUP", "AS_NEEDED", "OUTPUT", "SEARCH_DIR",
  "STARTUP", "OUTPUT_FORMAT", "TARGET", "ASSERT", "EXTERN",
  "FORCE_COMMON_ALLOCATION", "INHIBIT_COMMON_ALLOCATION", "INSERT",
  "NOCROSSREFS", "OUTPUT_ARCH", "LD_FEATURE", "HIDDEN", "PROVIDE",
  "PROVIDE_HIDDEN", "SECTIONS", "MEMORY", "PHDRS", "ABSOLUTE", "ADDR",
  "ALIGN", "ALIGNOF", "BLOCK", "DATA_SEGMENT_ALIGN", "DATA_SEGMENT_END",
  "DATA_SEGMENT_RELRO_END", "DEFINED", "LENGTH", "LOADADDR", "MAX", "MIN",
  "NEXT", "ORIGIN", "SEGMENT_START", "SIZEOF", "SIZEOF_HEADERS",
  "CONSTANT", "MAXPAGESIZE", "COMMONPAGESIZE", "EXCLUDE_FILE", "COMMON",
  "KEEP", "SORT_BY_NAME", "SORT_BY_ALIGNMENT", "SORT_NONE",
  "SORT_BY_INIT_PRIORITY", "BYTE", "SHORT", "LONG", "QUAD", "SQUAD",
  "FILL", "DISCARD", "CREATE_OBJECT_SYMBOLS", "CONSTRUCTORS", "NOLOAD",
  "DSECT", "COPY", "INFO", "OVERLAY", "AT", "SUBALIGN", "ONLY_IF_RO",
  "ONLY_IF_RW", "','", "'='", "RS_ASSIGN", "LS_ASSIGN", "OR_ASSIGN",
  "AND_ASSIGN", "DIV_ASSIGN", "MUL_ASSIGN", "SUB_ASSIGN", "ADD_ASSIGN",
  "'?'", "':'", "LOGICAL_OR", "LOGICAL_AND", "'|'", "'^'", "'&'", "NE",
  "EQ", "'<'", "'>'", "GE", "LE", "RSHIFT", "LSHIFT", "'+'", "'-'", "'*'",
  "'/'", "'%'", "'!'", "'~'", "UNARY_MINUS", "UNARY_PLUS", "';'", "'('",
  "')'", "'{'", "'}'", "'\"'", "$accept", "script_file", "$@1",
  "linker_script", "script_command", "entry_command",
  "output_format_command", "group_command", "input_command",
  "search_dir_command", "output_command", "output_arch_command",
  "assert_command", "input_list", "$@2", "inputs", "$@3", "$@4", "$@5",
  "input", "sections_command", "$@6", "sect_commands", "sect_cmd",
  "output_sect_desc", "$@7", "output_desc_prolog", "$@8", "$@9",
  "output_sect_commands", "output_desc_epilog", "opt_vma_and_type",
  "opt_type", "type", "opt_lma", "opt_align", "opt_subalign",
  "opt_constraint", "opt_region", "opt_lma_region", "opt_phdr", "$@10",
  "phdrs", "phdr", "opt_fill", "output_sect_cmd", "input_sect_desc",
  "input_sect_spec", "wildcard_file", "wildcard_pattern",
  "opt_exclude_files", "$@11", "exclude_files",
  "input_sect_wildcard_patterns", "$@12", "wildcard_sections",
  "wildcard_section", "output_sect_data", "output_sect_keyword",
  "symbol_assignment", "script_exp", "$@13", "exp", "$@14", "$@15",
  "symbol", "string", YY_NULL
};
#endif

/* YYR1[YYN] -- Symbol number of symbol that rule YYN derives.  */
static const unsigned char yyr1[] =
{
       0,   120,   122,   121,   123,   123,   124,   124,   124,   124,
     124,   124,   124,   124,   124,   124,   124,   125,   126,   126,
     127,   128,   129,   130,   131,   132,   134,   133,   135,   135,
     135,   136,   135,   137,   135,   138,   135,   139,   139,   141,
     140,   142,   142,   143,   143,   143,   145,   144,   147,   148,
     146,   149,   149,   150,   151,   151,   152,   152,   152,   153,
     153,   153,   153,   153,   154,   154,   155,   155,   156,   156,
     157,   157,   157,   158,   158,   159,   159,   161,   160,   162,
     162,   163,   164,   164,   165,   165,   165,   165,   165,   166,
     166,   167,   167,   168,   168,   169,   169,   169,   171,   170,
     170,   172,   172,   174,   173,   175,   175,   176,   176,   176,
     176,   176,   176,   176,   176,   176,   177,   177,   177,   177,
     177,   178,   178,   178,   179,   179,   179,   179,   179,   179,
     179,   179,   179,   179,   179,   179,   181,   180,   182,   182,
     182,   182,   182,   182,   182,   182,   182,   182,   182,   182,
     182,   182,   182,   182,   182,   182,   182,   182,   182,   182,
     182,   182,   182,   182,   182,   182,   182,   182,   183,   182,
     182,   182,   182,   182,   182,   182,   182,   182,   182,   184,
     182,   182,   182,   182,   182,   182,   182,   185,   186,   186
};

/* YYR2[YYN] -- Number of symbols composing right hand side of rule YYN.  */
static const unsigned char yyr2[] =
{
       0,     2,     0,     3,     2,     0,     1,     1,     1,     1,
       1,     1,     1,     1,     1,     1,     1,     4,     4,     8,
       4,     4,     4,     4,     4,     6,     0,     2,     1,     2,
       3,     0,     5,     0,     6,     0,     7,     1,     1,     0,
       5,     2,     0,     1,     1,     1,     0,     7,     0,     0,
       8,     2,     0,     4,     2,     1,     3,     2,     0,     1,
       1,     1,     1,     1,     4,     0,     4,     0,     4,     0,
       1,     1,     0,     2,     0,     3,     0,     0,     2,     3,
       0,     1,     2,     0,     1,     1,     1,     1,     1,     1,
       4,     1,     5,     1,     4,     1,     1,     1,     0,     5,
       0,     2,     1,     0,     2,     2,     1,     1,     4,     4,
       4,     7,     7,     7,     7,     4,     4,     4,     4,     4,
       4,     1,     1,     4,     4,     4,     4,     4,     4,     4,
       4,     4,     4,     7,     7,     7,     0,     2,     3,     2,
       2,     2,     2,     3,     3,     3,     3,     3,     3,     3,
       3,     3,     3,     3,     3,     3,     3,     3,     3,     3,
       3,     5,     4,     4,     4,     6,     4,     4,     0,     7,
       4,     6,     4,     4,     4,     6,     6,     4,     4,     0,
       7,     4,     1,     4,     4,     1,     1,     1,     1,     3
};

/* YYDPREC[RULE-NUM] -- Dynamic precedence of rule #RULE-NUM (0 if none).  */
static const unsigned char yydprec[] =
{
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0
};

/* YYMERGER[RULE-NUM] -- Index of merging function for rule #RULE-NUM.  */
static const unsigned char yymerger[] =
{
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0
};

/* YYDEFACT[S] -- default reduction number in state S.  Performed when
   YYTABLE doesn't specify something else to do.  Zero means the default
   is an error.  */
static const unsigned char yydefact[] =
{
       0,     2,     0,     5,     1,     3,   187,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,    39,    16,
       4,     6,     7,     8,     9,    11,    10,    12,    13,    15,
      14,     0,     0,    26,    26,     0,     0,     0,   136,     0,
       0,     0,     0,     0,   136,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,    42,     0,   185,     0,
       0,     0,     0,     0,   168,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   182,     0,     0,     0,
       0,     0,     0,     0,   186,     0,     0,     0,     0,     0,
       0,     0,    17,    21,   188,    38,     0,     0,    27,    28,
      37,    20,    23,    22,     0,    18,     0,   137,    24,   136,
     136,   136,     0,   124,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,   139,   140,   141,   142,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   132,   131,   130,   129,
     128,   127,   126,   125,    31,     0,     0,     0,    29,     0,
       0,     0,     0,     0,   188,    40,    43,    41,    45,    44,
      48,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,   179,     0,     0,     0,
     138,     0,   160,   159,   158,   157,   156,   155,   154,   150,
     152,   153,   151,   149,   148,   146,   147,   143,   144,   145,
       0,   189,    33,     0,    30,     0,    25,     0,     0,     0,
      46,    58,   162,   163,     0,   164,   166,   167,     0,   170,
       0,   172,   173,   174,     0,     0,   177,   178,     0,   181,
     183,   184,     0,     0,     0,    35,     0,   133,   134,   135,
       0,     0,    49,    55,    58,     0,     0,     0,     0,     0,
       0,   161,    32,     0,     0,    19,    52,    59,    60,    61,
      62,    63,    57,     0,     0,     0,    54,   165,     0,   171,
     175,   176,     0,    34,     0,     0,    56,    65,   169,   180,
      36,     0,     0,     0,     0,     0,     0,     0,   121,   122,
      97,    96,    88,    74,    51,    85,    89,     0,    93,    86,
      87,    84,    91,     0,    67,     0,     0,   136,   136,   136,
     136,   136,     0,    47,    76,   100,   136,     0,    69,     0,
       0,     0,     0,    95,     0,     0,     0,     0,     0,    73,
       0,    77,     0,   103,     0,   136,     0,    72,     0,    90,
     123,    94,   116,   117,   118,   119,   120,     0,    83,    80,
      98,     0,     0,    64,     0,   136,    70,    71,    50,    75,
     136,    53,    78,     0,    92,     0,     0,     0,     0,   107,
     104,   106,    66,     0,    82,     0,   102,     0,     0,     0,
       0,     0,   105,    68,    79,    81,    99,   101,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,   109,     0,
       0,   110,   108,   115,     0,     0,     0,     0,     0,     0,
       0,     0,   113,   111,   112,   114
};

/* YYPDEFGOTO[NTERM-NUM].  */
static const short int yydefgoto[] =
{
      -1,     2,     3,     5,    20,    21,    22,    23,    24,    25,
      26,    27,    28,    54,    55,   108,   230,   264,   284,   109,
      29,    43,   122,   187,   188,   270,   240,   241,   294,   305,
     343,   272,   273,   293,   334,   348,   367,   388,   344,   361,
     378,   379,   392,   414,   391,   324,   325,   326,   327,   328,
     363,   393,   407,   381,   382,   400,   401,   329,   330,    30,
      60,    61,   146,   129,   258,    94,   353
};

/* YYPACT[STATE-NUM] -- Index in YYTABLE of the portion describing
   STATE-NUM.  */
#define YYPACT_NINF -216
static const short int yypact[] =
{
      11,  -216,    31,  -216,  -216,   303,  -216,  -111,  -105,   -80,
     -78,   -70,   -67,   -59,   -47,   -44,   -42,   -41,  -216,  -216,
    -216,  -216,  -216,  -216,  -216,  -216,  -216,  -216,  -216,  -216,
    -216,   207,    57,  -216,  -216,    94,   102,   103,  -216,   106,
     109,   109,   109,    -6,  -216,   406,   406,   406,   406,   406,
     406,   406,   406,     0,     5,     4,    12,    24,    26,   -64,
      33,   406,    36,    34,    65,    83,  -216,    51,  -216,    23,
      52,    53,    62,    63,  -216,    64,    67,    68,    70,    73,
      74,    75,    76,    77,    80,    85,  -216,    88,   406,   406,
     406,   406,   406,   959,  -216,   982,  1005,  1028,  1051,  1074,
    1097,  1120,  -216,  -216,  -216,  -216,    91,   163,    30,  -216,
    -216,  -216,  -216,  -216,   177,  -216,    20,  1165,  -216,  -216,
    -216,  -216,    -1,  -216,   406,    20,   406,    20,   406,    92,
     406,   406,   109,    20,    20,   406,   406,   406,    20,    20,
      20,    -3,  -216,  -216,  -216,  -216,   660,   406,   406,   406,
     406,   406,   406,   406,   406,   406,   406,   406,   406,   406,
     406,   406,   406,   406,   406,   406,  -216,  -216,  -216,  -216,
    -216,  -216,  -216,  -216,  -216,    82,    95,    22,  -216,   132,
      98,   101,   105,   110,   337,  -216,  -216,  -216,  -216,  -216,
    -216,   685,   111,   520,   114,   710,   406,   735,   550,   115,
     117,   121,   580,   610,   760,   124,  -216,   125,   126,   130,
    -216,  1145,   380,   400,  1180,  1194,   274,   236,   236,   329,
     329,   329,   329,   -43,   -43,   -66,   -66,  -216,  -216,  -216,
       4,  -216,  -216,   107,  -216,   216,  -216,   134,   137,   138,
    -216,   488,  -216,  -216,   406,  -216,  -216,  -216,   640,  -216,
     406,  -216,  -216,  -216,   406,   406,  -216,  -216,   140,  -216,
    -216,  -216,   406,     8,     4,  -216,   158,  -216,  -216,  -216,
     136,   356,  -216,  -216,   935,   785,   406,   810,   835,   860,
     406,  1165,  -216,    10,     4,  -216,  -216,  -216,  -216,  -216,
    -216,  -216,  -216,   159,   133,    99,  -216,  -216,   885,  -216,
    -216,  -216,   910,  -216,    16,    29,  -216,   149,  -216,  -216,
    -216,   139,   161,   162,   164,   166,   167,   168,  -216,  -216,
    -216,  -216,  -216,   184,  -216,  -216,  -216,   170,  -216,  -216,
    -216,  -216,   171,   182,   242,   128,    44,  -216,  -216,  -216,
    -216,  -216,    20,  -216,   229,   253,  -216,   194,   233,   196,
     198,   201,   204,  -216,   208,   209,   211,   212,   214,  -216,
     247,  -216,   197,  -216,   232,  -216,   238,   -24,   104,  -216,
    -216,  -216,  -216,  -216,  -216,  -216,  -216,    20,   273,  -216,
    -216,   239,    43,  -216,   240,  -216,  -216,  -216,  -216,  -216,
    -216,  -216,   266,   104,  -216,   245,   249,   250,   251,  -216,
      43,  -216,  -216,   252,  -216,    20,  -216,    97,    18,    86,
     104,   104,  -216,  -216,  -216,  -216,  -216,  -216,   254,   270,
     271,   295,   297,   298,   299,   300,   104,   104,  -216,   104,
     104,  -216,  -216,  -216,   323,   343,   344,   347,   348,   349,
     352,   353,  -216,  -216,  -216,  -216
};

/* YYPGOTO[NTERM-NUM].  */
static const short int yypgoto[] =
{
    -216,  -216,  -216,  -216,  -216,   264,  -216,  -216,  -216,  -216,
    -216,  -216,  -216,   333,  -216,  -215,  -216,  -216,  -216,  -102,
    -216,  -216,  -216,  -216,  -216,  -216,  -216,  -216,  -216,  -216,
    -216,  -216,   218,  -216,  -216,  -216,  -216,  -216,  -216,  -216,
    -216,  -216,  -216,  -216,  -216,  -216,  -216,    54,  -216,   178,
    -216,  -216,  -216,  -216,  -216,  -216,    13,  -216,  -216,  -121,
     -39,  -216,   108,  -216,  -216,    -2,   -55
};

/* YYTABLE[YYPACT[STATE-NUM]].  What to do in state STATE-NUM.  If
   positive, shift that token.  If negative, reduce the rule which
   number is the opposite.  If YYTABLE_NINF, syntax error.  */
#define YYTABLE_NINF -188
static const short int yytable[] =
{
     110,   189,   184,    31,    32,    67,   178,   104,   105,     7,
      33,   104,   105,   104,   105,   263,   114,     1,   106,   104,
     105,   104,   176,   104,   176,   104,   105,    15,    16,    17,
     176,     4,   184,   104,   105,    34,   233,    35,    63,    64,
      65,   163,   164,   165,   176,    36,   104,   104,    37,   283,
     208,   209,   115,   110,   386,   387,    38,    15,    16,    17,
      53,   180,   161,   162,   163,   164,   165,   190,    39,   304,
     192,    40,   194,    41,    42,   234,   418,   419,   200,   201,
     181,   182,   183,   205,   206,   207,   311,   312,   177,   104,
     177,   313,   314,   315,   316,   317,   177,    57,   318,   319,
     104,   395,   396,   397,   398,    58,    59,   104,   320,    62,
     177,    66,     6,   116,   351,   119,   102,   185,   107,   320,
      31,   103,   110,   107,   282,   321,   303,   107,   111,   107,
     199,   104,   310,   320,   320,   107,   321,   107,   124,   107,
     112,   107,   113,   322,   421,   422,   120,   323,   107,   107,
     321,   321,   118,    93,    95,    96,    97,    98,    99,   100,
     101,   178,   107,   107,   121,   123,   175,   125,   126,   117,
     287,   288,   289,   290,   291,   110,   320,   127,   128,   130,
     179,   178,   131,   132,   331,   133,   349,   320,   134,   135,
     136,   137,   138,   321,   320,   139,   142,   143,   144,   145,
     140,   231,   178,   141,   321,   107,   174,   196,   110,   110,
     232,   321,   235,   416,   236,   292,   107,   237,   320,   266,
     280,   238,   265,   107,   307,   333,   239,   243,   110,   110,
     246,   251,   191,   252,   193,   321,   195,   253,   197,   198,
     257,   259,   260,   202,   203,   204,   261,   107,   267,   110,
     332,   268,   269,   286,   335,   211,   212,   213,   214,   215,
     216,   217,   218,   219,   220,   221,   222,   223,   224,   225,
     226,   227,   228,   229,   285,   306,   336,   337,   347,   338,
     332,   339,   340,   341,   342,   345,   -95,   359,    44,    45,
      46,    47,    48,    49,    50,    51,    52,   346,   354,   355,
     356,   357,   358,    31,   248,   360,     6,   364,   362,   365,
     366,   368,   380,     7,   369,     8,     9,   370,    10,    11,
     371,    12,   389,    13,   372,   373,   384,   374,   375,    14,
     376,    15,    16,    17,    18,   155,   156,   157,   158,   159,
     160,   161,   162,   163,   164,   165,   403,   377,   383,   274,
     415,   404,   275,   385,   390,   394,   402,   405,   277,     6,
     408,    68,   278,   279,   409,   410,   411,    56,   413,   426,
     281,   153,   154,   155,   156,   157,   158,   159,   160,   161,
     162,   163,   164,   165,   298,   427,   186,   428,   302,   350,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    86,    87,     6,
     429,    68,   430,   412,   431,   432,   433,    19,  -187,  -187,
    -187,  -187,  -187,  -187,  -187,  -187,  -187,   287,   288,   289,
     290,   291,   159,   160,   161,   162,   163,   164,   165,   438,
      69,    70,    71,    72,    73,    74,    75,    76,    77,    78,
      79,    80,    81,    82,    83,    84,    85,    86,    87,   439,
     440,    88,    89,   441,   442,   443,    90,    91,   444,   445,
       0,    92,   292,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     6,   296,    68,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,    88,    89,     0,   352,     0,    90,    91,     0,     0,
       0,    92,    69,    70,    71,    72,    73,    74,    75,    76,
      77,    78,    79,    80,    81,    82,    83,    84,    85,    86,
      87,     0,     0,     0,     0,     0,   352,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     399,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,   406,     0,     0,     0,     0,     0,     0,   399,     0,
       0,     0,     0,     0,     0,   417,   420,   423,   424,   425,
       0,     0,     0,    88,    89,     0,     0,     0,    90,    91,
     244,     0,     0,   271,   434,   435,     0,   436,   437,     0,
     147,     0,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     250,     0,     0,     0,     0,     0,   245,     0,     0,     0,
     147,     0,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     254,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     147,     0,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     255,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     147,     0,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     276,     0,     0,     0,     0,     0,     0,     0,     0,     0,
     147,     0,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     147,     0,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     0,     0,     0,     0,   147,   210,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,     0,     0,     0,     0,     0,
     147,   242,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     0,     0,     0,     0,   147,   247,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,     0,     0,     0,     0,     0,
     147,   249,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     0,     0,     0,     0,   147,   256,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,     0,     0,     0,     0,     0,
     147,   297,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     0,     0,     0,     0,   147,   299,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,     0,     0,     0,     0,     0,
     147,   300,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     0,     0,     0,     0,   147,   301,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,     0,     0,     0,     0,     0,
     147,   308,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     0,     0,     0,     0,   147,   309,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,     0,     0,     0,     0,   147,
     295,   148,   149,   150,   151,   152,   153,   154,   155,   156,
     157,   158,   159,   160,   161,   162,   163,   164,   165,     0,
       0,     0,   147,   166,   148,   149,   150,   151,   152,   153,
     154,   155,   156,   157,   158,   159,   160,   161,   162,   163,
     164,   165,     0,     0,     0,   147,   167,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,     0,     0,     0,   147,   168,
     148,   149,   150,   151,   152,   153,   154,   155,   156,   157,
     158,   159,   160,   161,   162,   163,   164,   165,     0,     0,
       0,   147,   169,   148,   149,   150,   151,   152,   153,   154,
     155,   156,   157,   158,   159,   160,   161,   162,   163,   164,
     165,     0,     0,     0,   147,   170,   148,   149,   150,   151,
     152,   153,   154,   155,   156,   157,   158,   159,   160,   161,
     162,   163,   164,   165,     0,     0,     0,   147,   171,   148,
     149,   150,   151,   152,   153,   154,   155,   156,   157,   158,
     159,   160,   161,   162,   163,   164,   165,     0,     0,     0,
     147,   172,   148,   149,   150,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
       0,     0,     0,     0,   173,   147,   262,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,   147,     0,   148,   149,   150,
     151,   152,   153,   154,   155,   156,   157,   158,   159,   160,
     161,   162,   163,   164,   165,   151,   152,   153,   154,   155,
     156,   157,   158,   159,   160,   161,   162,   163,   164,   165,
     152,   153,   154,   155,   156,   157,   158,   159,   160,   161,
     162,   163,   164,   165
};

/* YYCONFLP[YYPACT[STATE-NUM]] -- Pointer into YYCONFL of start of
   list of conflicting reductions corresponding to action entry for
   state STATE-NUM in yytable.  0 means no conflicts.  The list in
   yyconfl is terminated by a rule number of 0.  */
static const unsigned char yyconflp[] =
{
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0,     0,     0,     0,     0,     0,     0,
       0,     0,     0,     0
};

/* YYCONFL[I] -- lists of conflicting rule numbers, each terminated by
   0, pointed into by YYCONFLP.  */
static const short int yyconfl[] =
{
       0
};

static const short int yycheck[] =
{
      55,   122,     3,     5,   115,    44,   108,     3,     4,    10,
     115,     3,     4,     3,     4,   230,    80,     6,    14,     3,
       4,     3,    14,     3,    14,     3,     4,    28,    29,    30,
      14,     0,     3,     3,     4,   115,    14,   115,    40,    41,
      42,   107,   108,   109,    14,   115,     3,     3,   115,   264,
      53,    54,   116,   108,    78,    79,   115,    28,    29,    30,
       3,   116,   105,   106,   107,   108,   109,   122,   115,   284,
     125,   115,   127,   115,   115,   177,    58,    59,   133,   134,
     119,   120,   121,   138,   139,   140,    57,    58,    80,     3,
      80,    62,    63,    64,    65,    66,    80,     3,    69,    70,
       3,    58,    59,    60,    61,     3,     3,     3,    90,     3,
      80,   117,     3,    80,    70,    81,   116,   118,   119,    90,
     122,   116,   177,   119,   116,   107,   116,   119,   116,   119,
     132,     3,   116,    90,    90,   119,   107,   119,   115,   119,
     116,   119,   116,   114,    58,    59,    81,   118,   119,   119,
     107,   107,   116,    45,    46,    47,    48,    49,    50,    51,
      52,   263,   119,   119,    81,   114,     3,   115,   115,    61,
      71,    72,    73,    74,    75,   230,    90,   115,   115,   115,
       3,   283,   115,   115,   305,   115,    58,    90,   115,   115,
     115,   115,   115,   107,    90,   115,    88,    89,    90,    91,
     115,   119,   304,   115,   107,   119,   115,   115,   263,   264,
     115,   107,    80,   116,   116,   116,   119,   116,    90,     3,
      80,   116,   115,   119,    91,    76,   116,   116,   283,   284,
     116,   116,   124,   116,   126,   107,   128,   116,   130,   131,
     116,   116,   116,   135,   136,   137,   116,   119,   114,   304,
     305,   114,   114,   117,   115,   147,   148,   149,   150,   151,
     152,   153,   154,   155,   156,   157,   158,   159,   160,   161,
     162,   163,   164,   165,   116,   116,   115,   115,    36,   115,
     335,   115,   115,   115,   100,   115,   115,   342,    81,    82,
      83,    84,    85,    86,    87,    88,    89,   115,   337,   338,
     339,   340,   341,   305,   196,    76,     3,   346,    55,   115,
      77,   115,   115,    10,   116,    12,    13,   116,    15,    16,
     116,    18,   377,    20,   116,   116,   365,   116,   116,    26,
     116,    28,    29,    30,    31,    99,   100,   101,   102,   103,
     104,   105,   106,   107,   108,   109,   385,   100,   116,   241,
     405,   390,   244,   115,    81,   116,   116,    91,   250,     3,
     115,     5,   254,   255,   115,   115,   115,    34,   116,   115,
     262,    97,    98,    99,   100,   101,   102,   103,   104,   105,
     106,   107,   108,   109,   276,   115,   122,   116,   280,   335,
      34,    35,    36,    37,    38,    39,    40,    41,    42,    43,
      44,    45,    46,    47,    48,    49,    50,    51,    52,     3,
     115,     5,   115,   400,   116,   116,   116,   114,    81,    82,
      83,    84,    85,    86,    87,    88,    89,    71,    72,    73,
      74,    75,   103,   104,   105,   106,   107,   108,   109,   116,
      34,    35,    36,    37,    38,    39,    40,    41,    42,    43,
      44,    45,    46,    47,    48,    49,    50,    51,    52,   116,
     116,   105,   106,   116,   116,   116,   110,   111,   116,   116,
      -1,   115,   116,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,     3,   274,     5,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,   105,   106,    -1,   336,    -1,   110,   111,    -1,    -1,
      -1,   115,    34,    35,    36,    37,    38,    39,    40,    41,
      42,    43,    44,    45,    46,    47,    48,    49,    50,    51,
      52,    -1,    -1,    -1,    -1,    -1,   368,    -1,    -1,    -1,
      -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
     382,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      -1,   393,    -1,    -1,    -1,    -1,    -1,    -1,   400,    -1,
      -1,    -1,    -1,    -1,    -1,   407,   408,   409,   410,   411,
      -1,    -1,    -1,   105,   106,    -1,    -1,    -1,   110,   111,
      80,    -1,    -1,   115,   426,   427,    -1,   429,   430,    -1,
      90,    -1,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      80,    -1,    -1,    -1,    -1,    -1,   116,    -1,    -1,    -1,
      90,    -1,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      80,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    -1,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      80,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    -1,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      80,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,    -1,
      90,    -1,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      90,    -1,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,    -1,    -1,    -1,    -1,    90,   116,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    -1,    -1,    -1,    -1,    -1,
      90,   116,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,    -1,    -1,    -1,    -1,    90,   116,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    -1,    -1,    -1,    -1,    -1,
      90,   116,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,    -1,    -1,    -1,    -1,    90,   116,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    -1,    -1,    -1,    -1,    -1,
      90,   116,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,    -1,    -1,    -1,    -1,    90,   116,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    -1,    -1,    -1,    -1,    -1,
      90,   116,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,    -1,    -1,    -1,    -1,    90,   116,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    -1,    -1,    -1,    -1,    -1,
      90,   116,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,    -1,    -1,    -1,    -1,    90,   116,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    -1,    -1,    -1,    -1,    90,
     115,    92,    93,    94,    95,    96,    97,    98,    99,   100,
     101,   102,   103,   104,   105,   106,   107,   108,   109,    -1,
      -1,    -1,    90,   114,    92,    93,    94,    95,    96,    97,
      98,    99,   100,   101,   102,   103,   104,   105,   106,   107,
     108,   109,    -1,    -1,    -1,    90,   114,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    -1,    -1,    -1,    90,   114,
      92,    93,    94,    95,    96,    97,    98,    99,   100,   101,
     102,   103,   104,   105,   106,   107,   108,   109,    -1,    -1,
      -1,    90,   114,    92,    93,    94,    95,    96,    97,    98,
      99,   100,   101,   102,   103,   104,   105,   106,   107,   108,
     109,    -1,    -1,    -1,    90,   114,    92,    93,    94,    95,
      96,    97,    98,    99,   100,   101,   102,   103,   104,   105,
     106,   107,   108,   109,    -1,    -1,    -1,    90,   114,    92,
      93,    94,    95,    96,    97,    98,    99,   100,   101,   102,
     103,   104,   105,   106,   107,   108,   109,    -1,    -1,    -1,
      90,   114,    92,    93,    94,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      -1,    -1,    -1,    -1,   114,    90,    91,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    90,    -1,    92,    93,    94,
      95,    96,    97,    98,    99,   100,   101,   102,   103,   104,
     105,   106,   107,   108,   109,    95,    96,    97,    98,    99,
     100,   101,   102,   103,   104,   105,   106,   107,   108,   109,
      96,    97,    98,    99,   100,   101,   102,   103,   104,   105,
     106,   107,   108,   109
};

/* YYSTOS[STATE-NUM] -- The (internal number of the) accessing
   symbol of state STATE-NUM.  */
static const unsigned char yystos[] =
{
       0,     6,   121,   122,     0,   123,     3,    10,    12,    13,
      15,    16,    18,    20,    26,    28,    29,    30,    31,   114,
     124,   125,   126,   127,   128,   129,   130,   131,   132,   140,
     179,   185,   115,   115,   115,   115,   115,   115,   115,   115,
     115,   115,   115,   141,    81,    82,    83,    84,    85,    86,
      87,    88,    89,     3,   133,   134,   133,     3,     3,     3,
     180,   181,     3,   185,   185,   185,   117,   180,     5,    34,
      35,    36,    37,    38,    39,    40,    41,    42,    43,    44,
      45,    46,    47,    48,    49,    50,    51,    52,   105,   106,
     110,   111,   115,   182,   185,   182,   182,   182,   182,   182,
     182,   182,   116,   116,     3,     4,    14,   119,   135,   139,
     186,   116,   116,   116,    80,   116,    80,   182,   116,    81,
      81,    81,   142,   114,   115,   115,   115,   115,   115,   183,
     115,   115,   115,   115,   115,   115,   115,   115,   115,   115,
     115,   115,   182,   182,   182,   182,   182,    90,    92,    93,
      94,    95,    96,    97,    98,    99,   100,   101,   102,   103,
     104,   105,   106,   107,   108,   109,   114,   114,   114,   114,
     114,   114,   114,   114,   115,     3,    14,    80,   139,     3,
     186,   180,   180,   180,     3,   118,   125,   143,   144,   179,
     186,   182,   186,   182,   186,   182,   115,   182,   182,   185,
     186,   186,   182,   182,   182,   186,   186,   186,    53,    54,
     116,   182,   182,   182,   182,   182,   182,   182,   182,   182,
     182,   182,   182,   182,   182,   182,   182,   182,   182,   182,
     136,   119,   115,    14,   139,    80,   116,   116,   116,   116,
     146,   147,   116,   116,    80,   116,   116,   116,   182,   116,
      80,   116,   116,   116,    80,    80,   116,   116,   184,   116,
     116,   116,    91,   135,   137,   115,     3,   114,   114,   114,
     145,   115,   151,   152,   182,   182,    80,   182,   182,   182,
      80,   182,   116,   135,   138,   116,   117,    71,    72,    73,
      74,    75,   116,   153,   148,   115,   152,   116,   182,   116,
     116,   116,   182,   116,   135,   149,   116,    91,   116,   116,
     116,    57,    58,    62,    63,    64,    65,    66,    69,    70,
      90,   107,   114,   118,   165,   166,   167,   168,   169,   177,
     178,   179,   186,    76,   154,   115,   115,   115,   115,   115,
     115,   115,   100,   150,   158,   115,   115,    36,   155,    58,
     167,    70,   169,   186,   180,   180,   180,   180,   180,   186,
      76,   159,    55,   170,   180,   115,    77,   156,   115,   116,
     116,   116,   116,   116,   116,   116,   116,   100,   160,   161,
     115,   173,   174,   116,   180,   115,    78,    79,   157,   186,
      81,   164,   162,   171,   116,    58,    59,    60,    61,   169,
     175,   176,   116,   180,   180,    91,   169,   172,   115,   115,
     115,   115,   176,   116,   163,   186,   116,   169,    58,    59,
     169,    58,    59,   169,   169,   169,   115,   115,   116,   115,
     115,   116,   116,   116,   169,   169,   169,   169,   116,   116,
     116,   116,   116,   116,   116,   116
};

/* Error token number */
#define YYTERROR 1


/* YYLLOC_DEFAULT -- Set CURRENT to span from RHS[1] to RHS[N].
   If N is 0, then set CURRENT to the empty location which ends
   the previous symbol: RHS[0] (always defined).  */

# ifndef YYLLOC_DEFAULT
#  define YYLLOC_DEFAULT(Current, Rhs, N)                               \
    do                                                                  \
      if (N)                                                            \
        {                                                               \
          (Current).begin  = YYRHSLOC (Rhs, 1).begin;                   \
          (Current).end    = YYRHSLOC (Rhs, N).end;                     \
        }                                                               \
      else                                                              \
        {                                                               \
          (Current).begin = (Current).end = YYRHSLOC (Rhs, 0).end;      \
        }                                                               \
    while (/*CONSTCOND*/ false)
# endif

# define YYRHSLOC(Rhs, K) ((Rhs)[K].yystate.yyloc)


/* YY_LOCATION_PRINT -- Print the location on the stream.
   This macro was not mandated originally: define only if we know
   we won't break user code: when these are the locations we know.  */

#ifndef YY_LOCATION_PRINT
# if defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL

/* Print *YYLOCP on YYO.  Private, do not rely on its existence. */

__attribute__((__unused__))
#if (defined __STDC__ || defined __C99__FUNC__ \
     || defined __cplusplus || defined _MSC_VER)
static unsigned
yy_location_print_ (FILE *yyo, YYLTYPE const * const yylocp)
#else
static unsigned
yy_location_print_ (yyo, yylocp)
    FILE *yyo;
    YYLTYPE const * const yylocp;
#endif
{
  unsigned res = 0;
  int end_col = 0 != yylocp->last_column ? yylocp->last_column - 1 : 0;
  if (0 <= yylocp->first_line)
    {
      res += fprintf (yyo, "%d", yylocp->first_line);
      if (0 <= yylocp->first_column)
        res += fprintf (yyo, ".%d", yylocp->first_column);
    }
  if (0 <= yylocp->last_line)
    {
      if (yylocp->first_line < yylocp->last_line)
        {
          res += fprintf (yyo, "-%d", yylocp->last_line);
          if (0 <= end_col)
            res += fprintf (yyo, ".%d", end_col);
        }
      else if (0 <= end_col && yylocp->first_column < end_col)
        res += fprintf (yyo, "-%d", end_col);
    }
  return res;
 }

#  define YY_LOCATION_PRINT(File, Loc)          \
  yy_location_print_ (File, &(Loc))

# else
#  define YY_LOCATION_PRINT(File, Loc) ((void) 0)
# endif
#endif


/* YYLEX -- calling `yylex' with the right arguments.  */
#define YYLEX yylex (&yylval, &yylloc, m_ScriptFile)


#undef yynerrs
#define yynerrs (yystackp->yyerrcnt)
#undef yychar
#define yychar (yystackp->yyrawchar)
#undef yylval
#define yylval (yystackp->yyval)
#undef yylloc
#define yylloc (yystackp->yyloc)


static const int YYEOF = 0;
static const int YYEMPTY = -2;

typedef enum { yyok, yyaccept, yyabort, yyerr } YYRESULTTAG;

#define YYCHK(YYE)                                                           \
   do { YYRESULTTAG yyflag = YYE; if (yyflag != yyok) return yyflag; }       \
   while (YYID (0))

#if YYDEBUG

# ifndef YYFPRINTF
#  define YYFPRINTF fprintf
# endif

# define YYDPRINTF(Args)                        \
do {                                            \
  if (yydebug)                                  \
    YYFPRINTF Args;                             \
} while (YYID (0))


/*--------------------.
| Print this symbol.  |
`--------------------*/

static void
yy_symbol_print (FILE *, int yytype, const mcld::ScriptParser::semantic_type *yyvaluep, const mcld::ScriptParser::location_type *yylocationp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  YYUSE (yyparser);
  YYUSE (m_LDConfig);
  YYUSE (m_ScriptFile);
  YYUSE (m_ScriptScanner);
  YYUSE (m_ObjectReader);
  YYUSE (m_ArchiveReader);
  YYUSE (m_DynObjReader);
  YYUSE (m_GroupReader);
  yyparser.yy_symbol_print_ (yytype, yyvaluep, yylocationp);
}


# define YY_SYMBOL_PRINT(Title, Type, Value, Location)          \
do {                                                            \
  if (yydebug)                                                  \
    {                                                           \
      YYFPRINTF (stderr, "%s ", Title);                         \
      yy_symbol_print (stderr, Type, Value, Location, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);        \
      YYFPRINTF (stderr, "\n");                                 \
    }                                                           \
} while (YYID (0))

/* Nonzero means print parse trace.  It is left uninitialized so that
   multiple parsers can coexist.  */
int yydebug;

#else /* !YYDEBUG */

# define YYDPRINTF(Args)
# define YY_SYMBOL_PRINT(Title, Type, Value, Location)

#endif /* !YYDEBUG */

/* YYINITDEPTH -- initial size of the parser's stacks.  */
#ifndef YYINITDEPTH
# define YYINITDEPTH 200
#endif

/* YYMAXDEPTH -- maximum size the stacks can grow to (effective only
   if the built-in stack extension method is used).

   Do not make this value too large; the results are undefined if
   SIZE_MAX < YYMAXDEPTH * sizeof (GLRStackItem)
   evaluated with infinite-precision integer arithmetic.  */

#ifndef YYMAXDEPTH
# define YYMAXDEPTH 10000
#endif

/* Minimum number of free items on the stack allowed after an
   allocation.  This is to allow allocation and initialization
   to be completed by functions that call yyexpandGLRStack before the
   stack is expanded, thus insuring that all necessary pointers get
   properly redirected to new data.  */
#define YYHEADROOM 2

#ifndef YYSTACKEXPANDABLE
# if (! defined __cplusplus \
      || (defined YYLTYPE_IS_TRIVIAL && YYLTYPE_IS_TRIVIAL \
          && defined YYSTYPE_IS_TRIVIAL && YYSTYPE_IS_TRIVIAL))
#  define YYSTACKEXPANDABLE 1
# else
#  define YYSTACKEXPANDABLE 0
# endif
#endif

#if YYSTACKEXPANDABLE
# define YY_RESERVE_GLRSTACK(Yystack)                   \
  do {                                                  \
    if (Yystack->yyspaceLeft < YYHEADROOM)              \
      yyexpandGLRStack (Yystack);                       \
  } while (YYID (0))
#else
# define YY_RESERVE_GLRSTACK(Yystack)                   \
  do {                                                  \
    if (Yystack->yyspaceLeft < YYHEADROOM)              \
      yyMemoryExhausted (Yystack);                      \
  } while (YYID (0))
#endif


#if YYERROR_VERBOSE

# ifndef yystpcpy
#  if defined __GLIBC__ && defined _STRING_H && defined _GNU_SOURCE
#   define yystpcpy stpcpy
#  else
/* Copy YYSRC to YYDEST, returning the address of the terminating '\0' in
   YYDEST.  */
static char *
yystpcpy (char *yydest, const char *yysrc)
{
  char *yyd = yydest;
  const char *yys = yysrc;

  while ((*yyd++ = *yys++) != '\0')
    continue;

  return yyd - 1;
}
#  endif
# endif

# ifndef yytnamerr
/* Copy to YYRES the contents of YYSTR after stripping away unnecessary
   quotes and backslashes, so that it's suitable for yyerror.  The
   heuristic is that double-quoting is unnecessary unless the string
   contains an apostrophe, a comma, or backslash (other than
   backslash-backslash).  YYSTR is taken from yytname.  If YYRES is
   null, do not copy; instead, return the length of what the result
   would have been.  */
static size_t
yytnamerr (char *yyres, const char *yystr)
{
  if (*yystr == '"')
    {
      size_t yyn = 0;
      char const *yyp = yystr;

      for (;;)
        switch (*++yyp)
          {
          case '\'':
          case ',':
            goto do_not_strip_quotes;

          case '\\':
            if (*++yyp != '\\')
              goto do_not_strip_quotes;
            /* Fall through.  */
          default:
            if (yyres)
              yyres[yyn] = *yyp;
            yyn++;
            break;

          case '"':
            if (yyres)
              yyres[yyn] = '\0';
            return yyn;
          }
    do_not_strip_quotes: ;
    }

  if (! yyres)
    return strlen (yystr);

  return yystpcpy (yyres, yystr) - yyres;
}
# endif

#endif /* !YYERROR_VERBOSE */

/** State numbers, as in LALR(1) machine */
typedef int yyStateNum;

/** Rule numbers, as in LALR(1) machine */
typedef int yyRuleNum;

/** Grammar symbol */
typedef short int yySymbol;

/** Item references, as in LALR(1) machine */
typedef short int yyItemNum;

typedef struct yyGLRState yyGLRState;
typedef struct yyGLRStateSet yyGLRStateSet;
typedef struct yySemanticOption yySemanticOption;
typedef union yyGLRStackItem yyGLRStackItem;
typedef struct yyGLRStack yyGLRStack;

struct yyGLRState {
  /** Type tag: always true.  */
  yybool yyisState;
  /** Type tag for yysemantics.  If true, yysval applies, otherwise
   *  yyfirstVal applies.  */
  yybool yyresolved;
  /** Number of corresponding LALR(1) machine state.  */
  yyStateNum yylrState;
  /** Preceding state in this stack */
  yyGLRState* yypred;
  /** Source position of the first token produced by my symbol */
  size_t yyposn;
  union {
    /** First in a chain of alternative reductions producing the
     *  non-terminal corresponding to this state, threaded through
     *  yynext.  */
    yySemanticOption* yyfirstVal;
    /** Semantic value for this state.  */
    YYSTYPE yysval;
  } yysemantics;
  /** Source location for this state.  */
  YYLTYPE yyloc;
};

struct yyGLRStateSet {
  yyGLRState** yystates;
  /** During nondeterministic operation, yylookaheadNeeds tracks which
   *  stacks have actually needed the current lookahead.  During deterministic
   *  operation, yylookaheadNeeds[0] is not maintained since it would merely
   *  duplicate yychar != YYEMPTY.  */
  yybool* yylookaheadNeeds;
  size_t yysize, yycapacity;
};

struct yySemanticOption {
  /** Type tag: always false.  */
  yybool yyisState;
  /** Rule number for this reduction */
  yyRuleNum yyrule;
  /** The last RHS state in the list of states to be reduced.  */
  yyGLRState* yystate;
  /** The lookahead for this reduction.  */
  int yyrawchar;
  YYSTYPE yyval;
  YYLTYPE yyloc;
  /** Next sibling in chain of options.  To facilitate merging,
   *  options are chained in decreasing order by address.  */
  yySemanticOption* yynext;
};

/** Type of the items in the GLR stack.  The yyisState field
 *  indicates which item of the union is valid.  */
union yyGLRStackItem {
  yyGLRState yystate;
  yySemanticOption yyoption;
};

struct yyGLRStack {
  int yyerrState;
  /* To compute the location of the error token.  */
  yyGLRStackItem yyerror_range[3];

  int yyerrcnt;
  int yyrawchar;
  YYSTYPE yyval;
  YYLTYPE yyloc;

  YYJMP_BUF yyexception_buffer;
  yyGLRStackItem* yyitems;
  yyGLRStackItem* yynextFree;
  size_t yyspaceLeft;
  yyGLRState* yysplitPoint;
  yyGLRState* yylastDeleted;
  yyGLRStateSet yytops;
};

#if YYSTACKEXPANDABLE
static void yyexpandGLRStack (yyGLRStack* yystackp);
#endif

static void yyFail (yyGLRStack* yystackp, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader, const char* yymsg)
  __attribute__ ((__noreturn__));
static void
yyFail (yyGLRStack* yystackp, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader, const char* yymsg)
{
  if (yymsg != YY_NULL)
    yyerror (yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, yymsg);
  YYLONGJMP (yystackp->yyexception_buffer, 1);
}

static void yyMemoryExhausted (yyGLRStack* yystackp)
  __attribute__ ((__noreturn__));
static void
yyMemoryExhausted (yyGLRStack* yystackp)
{
  YYLONGJMP (yystackp->yyexception_buffer, 2);
}

#if YYDEBUG || YYERROR_VERBOSE
/** A printable representation of TOKEN.  */
static inline const char*
yytokenName (yySymbol yytoken)
{
  if (yytoken == YYEMPTY)
    return "";

  return yytname[yytoken];
}
#endif

/** Fill in YYVSP[YYLOW1 .. YYLOW0-1] from the chain of states starting
 *  at YYVSP[YYLOW0].yystate.yypred.  Leaves YYVSP[YYLOW1].yystate.yypred
 *  containing the pointer to the next state in the chain.  */
static void yyfillin (yyGLRStackItem *, int, int) __attribute__ ((__unused__));
static void
yyfillin (yyGLRStackItem *yyvsp, int yylow0, int yylow1)
{
  int i;
  yyGLRState *s = yyvsp[yylow0].yystate.yypred;
  for (i = yylow0-1; i >= yylow1; i -= 1)
    {
      YYASSERT (s->yyresolved);
      yyvsp[i].yystate.yyresolved = yytrue;
      yyvsp[i].yystate.yysemantics.yysval = s->yysemantics.yysval;
      yyvsp[i].yystate.yyloc = s->yyloc;
      s = yyvsp[i].yystate.yypred = s->yypred;
    }
}

/* Do nothing if YYNORMAL or if *YYLOW <= YYLOW1.  Otherwise, fill in
 * YYVSP[YYLOW1 .. *YYLOW-1] as in yyfillin and set *YYLOW = YYLOW1.
 * For convenience, always return YYLOW1.  */
static inline int yyfill (yyGLRStackItem *, int *, int, yybool)
     __attribute__ ((__unused__));
static inline int
yyfill (yyGLRStackItem *yyvsp, int *yylow, int yylow1, yybool yynormal)
{
  if (!yynormal && yylow1 < *yylow)
    {
      yyfillin (yyvsp, *yylow, yylow1);
      *yylow = yylow1;
    }
  return yylow1;
}

/** Perform user action for rule number YYN, with RHS length YYRHSLEN,
 *  and top stack item YYVSP.  YYLVALP points to place to put semantic
 *  value ($$), and yylocp points to place for location information
 *  (@$).  Returns yyok for normal return, yyaccept for YYACCEPT,
 *  yyerr for YYERROR, yyabort for YYABORT.  */
/*ARGSUSED*/ static YYRESULTTAG
yyuserAction (yyRuleNum yyn, int yyrhslen, yyGLRStackItem* yyvsp,
              yyGLRStack* yystackp,
              YYSTYPE* yyvalp, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  yybool yynormal __attribute__ ((__unused__)) =
    (yystackp->yysplitPoint == YY_NULL);
  int yylow;
  YYUSE (yyparser);
  YYUSE (m_LDConfig);
  YYUSE (m_ScriptFile);
  YYUSE (m_ScriptScanner);
  YYUSE (m_ObjectReader);
  YYUSE (m_ArchiveReader);
  YYUSE (m_DynObjReader);
  YYUSE (m_GroupReader);
# undef yyerrok
# define yyerrok (yystackp->yyerrState = 0)
# undef YYACCEPT
# define YYACCEPT return yyaccept
# undef YYABORT
# define YYABORT return yyabort
# undef YYERROR
# define YYERROR return yyerrok, yyerr
# undef YYRECOVERING
# define YYRECOVERING() (yystackp->yyerrState != 0)
# undef yyclearin
# define yyclearin (yychar = YYEMPTY)
# undef YYFILL
# define YYFILL(N) yyfill (yyvsp, &yylow, N, yynormal)
# undef YYBACKUP
# define YYBACKUP(Token, Value)                                              \
  return yyerror (yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_("syntax error: cannot back up")),     \
         yyerrok, yyerr

  yylow = 1;
  if (yyrhslen == 0)
    *yyvalp = yyval_default;
  else
    *yyvalp = yyvsp[YYFILL (1-yyrhslen)].yystate.yysemantics.yysval;
  YYLLOC_DEFAULT ((*yylocp), (yyvsp - yyrhslen), yyrhslen);
  yystackp->yyerror_range[1].yystate.yyloc = *yylocp;

  switch (yyn)
    {
        case 2:
/* Line 868 of glr.c  */
#line 209 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptScanner.setLexState(ScriptFile::LDScript); }
    break;

  case 3:
/* Line 868 of glr.c  */
#line 211 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptScanner.popLexState(); }
    break;

  case 17:
/* Line 868 of glr.c  */
#line 232 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addEntryPoint(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)); }
    break;

  case 18:
/* Line 868 of glr.c  */
#line 236 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addOutputFormatCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)); }
    break;

  case 19:
/* Line 868 of glr.c  */
#line 238 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addOutputFormatCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (8))].yystate.yysemantics.yysval.string), *(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (8))].yystate.yysemantics.yysval.string), *(((yyGLRStackItem const *)yyvsp)[YYFILL ((7) - (8))].yystate.yysemantics.yysval.string)); }
    break;

  case 20:
/* Line 868 of glr.c  */
#line 242 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addGroupCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.str_tokens), m_GroupReader, m_LDConfig); }
    break;

  case 21:
/* Line 868 of glr.c  */
#line 246 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                  m_ScriptFile.addInputCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.str_tokens), m_ObjectReader, m_ArchiveReader,
                                           m_DynObjReader, m_LDConfig);
                }
    break;

  case 22:
/* Line 868 of glr.c  */
#line 253 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addSearchDirCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)); }
    break;

  case 23:
/* Line 868 of glr.c  */
#line 257 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addOutputCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)); }
    break;

  case 24:
/* Line 868 of glr.c  */
#line 261 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addOutputArchCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)); }
    break;

  case 25:
/* Line 868 of glr.c  */
#line 265 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addAssertCmd(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (6))].yystate.yysemantics.yysval.rpn_expr), *(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (6))].yystate.yysemantics.yysval.string)); }
    break;

  case 26:
/* Line 868 of glr.c  */
#line 268 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.createStringList(); }
    break;

  case 27:
/* Line 868 of glr.c  */
#line 270 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_tokens) = m_ScriptFile.getCurrentStringList(); }
    break;

  case 28:
/* Line 868 of glr.c  */
#line 274 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.getCurrentStringList()->push_back((((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.str_token)); }
    break;

  case 29:
/* Line 868 of glr.c  */
#line 276 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.getCurrentStringList()->push_back((((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.str_token)); }
    break;

  case 30:
/* Line 868 of glr.c  */
#line 278 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.getCurrentStringList()->push_back((((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.str_token)); }
    break;

  case 31:
/* Line 868 of glr.c  */
#line 280 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.setAsNeeded(true); }
    break;

  case 32:
/* Line 868 of glr.c  */
#line 282 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.setAsNeeded(false); }
    break;

  case 33:
/* Line 868 of glr.c  */
#line 284 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.setAsNeeded(true); }
    break;

  case 34:
/* Line 868 of glr.c  */
#line 286 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.setAsNeeded(false); }
    break;

  case 35:
/* Line 868 of glr.c  */
#line 288 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.setAsNeeded(true); }
    break;

  case 36:
/* Line 868 of glr.c  */
#line 290 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.setAsNeeded(false); }
    break;

  case 37:
/* Line 868 of glr.c  */
#line 294 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_token) = FileToken::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string), m_ScriptFile.asNeeded()); }
    break;

  case 38:
/* Line 868 of glr.c  */
#line 296 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_token) = NameSpec::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string), m_ScriptFile.asNeeded()); }
    break;

  case 39:
/* Line 868 of glr.c  */
#line 308 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.enterSectionsCmd(); }
    break;

  case 40:
/* Line 868 of glr.c  */
#line 310 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.leaveSectionsCmd(); }
    break;

  case 46:
/* Line 868 of glr.c  */
#line 345 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.enterOutputSectDesc(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (2))].yystate.yysemantics.yysval.string), (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.output_prolog)); }
    break;

  case 47:
/* Line 868 of glr.c  */
#line 349 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.leaveOutputSectDesc((((yyGLRStackItem const *)yyvsp)[YYFILL ((7) - (7))].yystate.yysemantics.yysval.output_epilog)); }
    break;

  case 48:
/* Line 868 of glr.c  */
#line 352 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                       m_ScriptScanner.setLexState(ScriptFile::Expression);
                       /* create exp for vma */
                       m_ScriptFile.createRpnExpr();
                     }
    break;

  case 49:
/* Line 868 of glr.c  */
#line 358 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptScanner.popLexState(); }
    break;

  case 50:
/* Line 868 of glr.c  */
#line 361 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                       ((*yyvalp).output_prolog).m_pVMA       = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (8))].yystate.yysemantics.yysval.output_prolog).m_pVMA;
                       ((*yyvalp).output_prolog).m_Type       = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (8))].yystate.yysemantics.yysval.output_prolog).m_Type;
                       ((*yyvalp).output_prolog).m_pLMA       = (((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (8))].yystate.yysemantics.yysval.rpn_expr);
                       ((*yyvalp).output_prolog).m_pAlign     = (((yyGLRStackItem const *)yyvsp)[YYFILL ((6) - (8))].yystate.yysemantics.yysval.rpn_expr);
                       ((*yyvalp).output_prolog).m_pSubAlign  = (((yyGLRStackItem const *)yyvsp)[YYFILL ((7) - (8))].yystate.yysemantics.yysval.rpn_expr);
                       ((*yyvalp).output_prolog).m_Constraint = (((yyGLRStackItem const *)yyvsp)[YYFILL ((8) - (8))].yystate.yysemantics.yysval.output_constraint);
                     }
    break;

  case 53:
/* Line 868 of glr.c  */
#line 376 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                        ((*yyvalp).output_epilog).m_pRegion    = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (4))].yystate.yysemantics.yysval.string);
                        ((*yyvalp).output_epilog).m_pLMARegion = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (4))].yystate.yysemantics.yysval.string);
                        ((*yyvalp).output_epilog).m_pPhdrs     = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.str_tokens);
                        ((*yyvalp).output_epilog).m_pFillExp   = (((yyGLRStackItem const *)yyvsp)[YYFILL ((4) - (4))].yystate.yysemantics.yysval.rpn_expr);
                     }
    break;

  case 54:
/* Line 868 of glr.c  */
#line 386 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                     ((*yyvalp).output_prolog).m_pVMA = m_ScriptFile.getCurrentRpnExpr();
                     ((*yyvalp).output_prolog).m_Type = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.output_type);
                   }
    break;

  case 55:
/* Line 868 of glr.c  */
#line 391 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                     ((*yyvalp).output_prolog).m_pVMA = NULL;
                     ((*yyvalp).output_prolog).m_Type = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.output_type);
                   }
    break;

  case 56:
/* Line 868 of glr.c  */
#line 398 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (3))].yystate.yysemantics.yysval.output_type); }
    break;

  case 57:
/* Line 868 of glr.c  */
#line 400 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = OutputSectDesc::LOAD; }
    break;

  case 58:
/* Line 868 of glr.c  */
#line 402 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = OutputSectDesc::LOAD; }
    break;

  case 59:
/* Line 868 of glr.c  */
#line 406 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = OutputSectDesc::NOLOAD; }
    break;

  case 60:
/* Line 868 of glr.c  */
#line 408 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = OutputSectDesc::DSECT; }
    break;

  case 61:
/* Line 868 of glr.c  */
#line 410 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = OutputSectDesc::COPY; }
    break;

  case 62:
/* Line 868 of glr.c  */
#line 412 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = OutputSectDesc::INFO; }
    break;

  case 63:
/* Line 868 of glr.c  */
#line 414 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_type) = OutputSectDesc::OVERLAY; }
    break;

  case 64:
/* Line 868 of glr.c  */
#line 418 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.rpn_expr); }
    break;

  case 65:
/* Line 868 of glr.c  */
#line 420 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = NULL; }
    break;

  case 66:
/* Line 868 of glr.c  */
#line 425 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.rpn_expr); }
    break;

  case 67:
/* Line 868 of glr.c  */
#line 427 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = NULL; }
    break;

  case 68:
/* Line 868 of glr.c  */
#line 432 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.rpn_expr); }
    break;

  case 69:
/* Line 868 of glr.c  */
#line 434 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = NULL; }
    break;

  case 70:
/* Line 868 of glr.c  */
#line 438 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_constraint) = OutputSectDesc::ONLY_IF_RO; }
    break;

  case 71:
/* Line 868 of glr.c  */
#line 440 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_constraint) = OutputSectDesc::ONLY_IF_RW; }
    break;

  case 72:
/* Line 868 of glr.c  */
#line 442 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).output_constraint) = OutputSectDesc::NO_CONSTRAINT; }
    break;

  case 73:
/* Line 868 of glr.c  */
#line 446 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.string); }
    break;

  case 74:
/* Line 868 of glr.c  */
#line 448 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = NULL; }
    break;

  case 75:
/* Line 868 of glr.c  */
#line 452 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.string); }
    break;

  case 76:
/* Line 868 of glr.c  */
#line 454 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = NULL; }
    break;

  case 77:
/* Line 868 of glr.c  */
#line 457 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.createStringList(); }
    break;

  case 78:
/* Line 868 of glr.c  */
#line 459 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_tokens) = m_ScriptFile.getCurrentStringList(); }
    break;

  case 79:
/* Line 868 of glr.c  */
#line 463 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.getCurrentStringList()->push_back((((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.str_token)); }
    break;

  case 81:
/* Line 868 of glr.c  */
#line 468 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_token) = StrToken::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string)); }
    break;

  case 82:
/* Line 868 of glr.c  */
#line 472 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.rpn_expr); }
    break;

  case 83:
/* Line 868 of glr.c  */
#line 474 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).rpn_expr) = NULL; }
    break;

  case 89:
/* Line 868 of glr.c  */
#line 493 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addInputSectDesc(InputSectDesc::NoKeep, (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.input_spec)); }
    break;

  case 90:
/* Line 868 of glr.c  */
#line 495 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addInputSectDesc(InputSectDesc::Keep, (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.input_spec)); }
    break;

  case 91:
/* Line 868 of glr.c  */
#line 499 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                    ((*yyvalp).input_spec).m_pWildcardFile =
                      WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_NONE);
                    ((*yyvalp).input_spec).m_pExcludeFiles = NULL;
                    ((*yyvalp).input_spec).m_pWildcardSections = NULL;
                  }
    break;

  case 92:
/* Line 868 of glr.c  */
#line 506 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                    ((*yyvalp).input_spec).m_pWildcardFile = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (5))].yystate.yysemantics.yysval.wildcard);
                    ((*yyvalp).input_spec).m_pExcludeFiles = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (5))].yystate.yysemantics.yysval.str_tokens);
                    ((*yyvalp).input_spec).m_pWildcardSections = (((yyGLRStackItem const *)yyvsp)[YYFILL ((4) - (5))].yystate.yysemantics.yysval.str_tokens);
                  }
    break;

  case 93:
/* Line 868 of glr.c  */
#line 514 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_NONE); }
    break;

  case 94:
/* Line 868 of glr.c  */
#line 516 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_NAME); }
    break;

  case 95:
/* Line 868 of glr.c  */
#line 520 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string); }
    break;

  case 96:
/* Line 868 of glr.c  */
#line 522 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = &m_ScriptFile.createParserStr("*", 1); }
    break;

  case 97:
/* Line 868 of glr.c  */
#line 524 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = &m_ScriptFile.createParserStr("?", 1); }
    break;

  case 98:
/* Line 868 of glr.c  */
#line 528 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.createStringList(); }
    break;

  case 99:
/* Line 868 of glr.c  */
#line 530 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_tokens) = m_ScriptFile.getCurrentStringList(); }
    break;

  case 100:
/* Line 868 of glr.c  */
#line 532 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_tokens) = NULL; }
    break;

  case 101:
/* Line 868 of glr.c  */
#line 536 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                  m_ScriptFile.getCurrentStringList()->push_back(
                    WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_NONE));
                }
    break;

  case 102:
/* Line 868 of glr.c  */
#line 541 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                  m_ScriptFile.getCurrentStringList()->push_back(
                    WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_NONE));
                }
    break;

  case 103:
/* Line 868 of glr.c  */
#line 547 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.createStringList(); }
    break;

  case 104:
/* Line 868 of glr.c  */
#line 549 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).str_tokens) = m_ScriptFile.getCurrentStringList(); }
    break;

  case 105:
/* Line 868 of glr.c  */
#line 553 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                      m_ScriptFile.getCurrentStringList()->push_back((((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.wildcard));
                    }
    break;

  case 106:
/* Line 868 of glr.c  */
#line 557 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                      m_ScriptFile.getCurrentStringList()->push_back((((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.wildcard));
                    }
    break;

  case 107:
/* Line 868 of glr.c  */
#line 563 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_NONE); }
    break;

  case 108:
/* Line 868 of glr.c  */
#line 565 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_NONE); }
    break;

  case 109:
/* Line 868 of glr.c  */
#line 567 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_NAME); }
    break;

  case 110:
/* Line 868 of glr.c  */
#line 569 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_ALIGNMENT); }
    break;

  case 111:
/* Line 868 of glr.c  */
#line 571 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (7))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_NAME_ALIGNMENT); }
    break;

  case 112:
/* Line 868 of glr.c  */
#line 573 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (7))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_ALIGNMENT_NAME); }
    break;

  case 113:
/* Line 868 of glr.c  */
#line 575 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (7))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_NAME); }
    break;

  case 114:
/* Line 868 of glr.c  */
#line 577 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (7))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_ALIGNMENT); }
    break;

  case 115:
/* Line 868 of glr.c  */
#line 579 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).wildcard) = WildcardPattern::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string), WildcardPattern::SORT_BY_INIT_PRIORITY); }
    break;

  case 124:
/* Line 868 of glr.c  */
#line 595 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { m_ScriptFile.addAssignment(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (4))].yystate.yysemantics.yysval.string), *(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.rpn_expr)); }
    break;

  case 133:
/* Line 868 of glr.c  */
#line 605 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                      m_ScriptFile.addAssignment(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (7))].yystate.yysemantics.yysval.string), *(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (7))].yystate.yysemantics.yysval.rpn_expr),
                                                 Assignment::HIDDEN);
                    }
    break;

  case 134:
/* Line 868 of glr.c  */
#line 610 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                      m_ScriptFile.addAssignment(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (7))].yystate.yysemantics.yysval.string), *(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (7))].yystate.yysemantics.yysval.rpn_expr),
                                                 Assignment::PROVIDE);
                    }
    break;

  case 135:
/* Line 868 of glr.c  */
#line 615 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
                      m_ScriptFile.addAssignment(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (7))].yystate.yysemantics.yysval.string), *(((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (7))].yystate.yysemantics.yysval.rpn_expr),
                                                 Assignment::PROVIDE_HIDDEN);
                    }
    break;

  case 136:
/* Line 868 of glr.c  */
#line 621 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
               m_ScriptScanner.setLexState(ScriptFile::Expression);
               m_ScriptFile.createRpnExpr();
             }
    break;

  case 137:
/* Line 868 of glr.c  */
#line 626 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
               m_ScriptScanner.popLexState();
               ((*yyvalp).rpn_expr) = m_ScriptFile.getCurrentRpnExpr();
             }
    break;

  case 138:
/* Line 868 of glr.c  */
#line 633 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (3))].yystate.yysemantics.yysval.integer);
      }
    break;

  case 139:
/* Line 868 of glr.c  */
#line 637 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::UNARY_PLUS>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 140:
/* Line 868 of glr.c  */
#line 643 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::UNARY_MINUS>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 141:
/* Line 868 of glr.c  */
#line 649 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::LOGICAL_NOT>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 142:
/* Line 868 of glr.c  */
#line 655 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::BITWISE_NOT>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (2))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 143:
/* Line 868 of glr.c  */
#line 661 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::MUL>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 144:
/* Line 868 of glr.c  */
#line 667 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::DIV>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 145:
/* Line 868 of glr.c  */
#line 673 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::MOD>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 146:
/* Line 868 of glr.c  */
#line 679 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::ADD>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 147:
/* Line 868 of glr.c  */
#line 685 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::SUB>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 148:
/* Line 868 of glr.c  */
#line 691 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::LSHIFT>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 149:
/* Line 868 of glr.c  */
#line 697 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::RSHIFT>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 150:
/* Line 868 of glr.c  */
#line 703 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::LT>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 151:
/* Line 868 of glr.c  */
#line 709 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::LE>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 152:
/* Line 868 of glr.c  */
#line 715 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::GT>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 153:
/* Line 868 of glr.c  */
#line 721 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::GE>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 154:
/* Line 868 of glr.c  */
#line 727 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::EQ>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 155:
/* Line 868 of glr.c  */
#line 733 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::NE>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 156:
/* Line 868 of glr.c  */
#line 739 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::BITWISE_AND>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 157:
/* Line 868 of glr.c  */
#line 745 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::BITWISE_XOR>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 158:
/* Line 868 of glr.c  */
#line 751 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::BITWISE_OR>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 159:
/* Line 868 of glr.c  */
#line 757 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::LOGICAL_AND>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 160:
/* Line 868 of glr.c  */
#line 763 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::LOGICAL_OR>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (3))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 161:
/* Line 868 of glr.c  */
#line 769 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::TERNARY_IF>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (5))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (5))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (5))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 162:
/* Line 868 of glr.c  */
#line 775 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::ABSOLUTE>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 163:
/* Line 868 of glr.c  */
#line 781 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SectOperand::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)));
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::ADDR>());
        ((*yyvalp).integer) = 2;
      }
    break;

  case 164:
/* Line 868 of glr.c  */
#line 788 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        RpnExpr::iterator pos = m_ScriptFile.getCurrentRpnExpr()->begin() +
                                m_ScriptFile.getCurrentRpnExpr()->size() - (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.integer);
        m_ScriptFile.getCurrentRpnExpr()->insert(pos, SymOperand::create("."));
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::ALIGN>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.integer) + 2;
      }
    break;

  case 165:
/* Line 868 of glr.c  */
#line 797 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::ALIGN>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (6))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (6))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 166:
/* Line 868 of glr.c  */
#line 803 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SectOperand::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)));
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::ALIGNOF>());
        ((*yyvalp).integer) = 2;
      }
    break;

  case 167:
/* Line 868 of glr.c  */
#line 810 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        RpnExpr::iterator pos = m_ScriptFile.getCurrentRpnExpr()->begin() +
                                m_ScriptFile.getCurrentRpnExpr()->size() - (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.integer);
        m_ScriptFile.getCurrentRpnExpr()->insert(pos, SymOperand::create("."));
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::ALIGN>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.integer) + 2;
      }
    break;

  case 168:
/* Line 868 of glr.c  */
#line 819 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SymOperand::create("."));
      }
    break;

  case 169:
/* Line 868 of glr.c  */
#line 823 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::DATA_SEGMENT_ALIGN>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((4) - (7))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((6) - (7))].yystate.yysemantics.yysval.integer) + 2;
      }
    break;

  case 170:
/* Line 868 of glr.c  */
#line 829 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::DATA_SEGMENT_END>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 171:
/* Line 868 of glr.c  */
#line 835 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::DATA_SEGMENT_RELRO_END>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (6))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (6))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 172:
/* Line 868 of glr.c  */
#line 841 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SymOperand::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)));
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::DEFINED>());
        ((*yyvalp).integer) = 2;
      }
    break;

  case 173:
/* Line 868 of glr.c  */
#line 848 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        /* TODO */
      }
    break;

  case 174:
/* Line 868 of glr.c  */
#line 852 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SectOperand::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)));
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::LOADADDR>());
        ((*yyvalp).integer) = 2;
      }
    break;

  case 175:
/* Line 868 of glr.c  */
#line 859 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::MAX>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (6))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (6))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 176:
/* Line 868 of glr.c  */
#line 865 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::MIN>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (6))].yystate.yysemantics.yysval.integer) + (((yyGLRStackItem const *)yyvsp)[YYFILL ((5) - (6))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 177:
/* Line 868 of glr.c  */
#line 871 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::NEXT>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.integer) + 1;
      }
    break;

  case 178:
/* Line 868 of glr.c  */
#line 877 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        /* TODO */
      }
    break;

  case 179:
/* Line 868 of glr.c  */
#line 881 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SectOperand::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (3))].yystate.yysemantics.yysval.string)));
      }
    break;

  case 180:
/* Line 868 of glr.c  */
#line 885 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::SEGMENT_START>());
        ((*yyvalp).integer) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((6) - (7))].yystate.yysemantics.yysval.integer) + 2;
      }
    break;

  case 181:
/* Line 868 of glr.c  */
#line 891 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SectOperand::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((3) - (4))].yystate.yysemantics.yysval.string)));
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::SIZEOF>());
        ((*yyvalp).integer) = 2;
      }
    break;

  case 182:
/* Line 868 of glr.c  */
#line 898 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::SIZEOF_HEADERS>());
        ((*yyvalp).integer) = 1;
      }
    break;

  case 183:
/* Line 868 of glr.c  */
#line 904 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::MAXPAGESIZE>());
        ((*yyvalp).integer) = 1;
      }
    break;

  case 184:
/* Line 868 of glr.c  */
#line 910 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(
          &Operator::create<Operator::COMMONPAGESIZE>());
        ((*yyvalp).integer) = 1;
      }
    break;

  case 185:
/* Line 868 of glr.c  */
#line 916 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(IntOperand::create((((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.integer)));
        ((*yyvalp).integer) = 1;
      }
    break;

  case 186:
/* Line 868 of glr.c  */
#line 921 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    {
        m_ScriptFile.getCurrentRpnExpr()->push_back(SymOperand::create(*(((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string)));
        ((*yyvalp).integer) = 1;
      }
    break;

  case 187:
/* Line 868 of glr.c  */
#line 928 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string); }
    break;

  case 188:
/* Line 868 of glr.c  */
#line 932 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((1) - (1))].yystate.yysemantics.yysval.string); }
    break;

  case 189:
/* Line 868 of glr.c  */
#line 934 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
    { ((*yyvalp).string) = (((yyGLRStackItem const *)yyvsp)[YYFILL ((2) - (3))].yystate.yysemantics.yysval.string); }
    break;


/* Line 868 of glr.c  */
#line 2768 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"
      default: break;
    }

  return yyok;
# undef yyerrok
# undef YYABORT
# undef YYACCEPT
# undef YYERROR
# undef YYBACKUP
# undef yyclearin
# undef YYRECOVERING
}


/*ARGSUSED*/ static void
yyuserMerge (int yyn, YYSTYPE* yy0, YYSTYPE* yy1)
{
  YYUSE (yy0);
  YYUSE (yy1);

  switch (yyn)
    {
      
      default: break;
    }
}

                              /* Bison grammar-table manipulation.  */

/*-----------------------------------------------.
| Release the memory associated to this symbol.  |
`-----------------------------------------------*/

/*ARGSUSED*/
static void
yydestruct (const char *yymsg, int yytype, YYSTYPE *yyvaluep, YYLTYPE *yylocationp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  YYUSE (yyvaluep);
  YYUSE (yylocationp);
  YYUSE (yyparser);
  YYUSE (m_LDConfig);
  YYUSE (m_ScriptFile);
  YYUSE (m_ScriptScanner);
  YYUSE (m_ObjectReader);
  YYUSE (m_ArchiveReader);
  YYUSE (m_DynObjReader);
  YYUSE (m_GroupReader);

  if (!yymsg)
    yymsg = "Deleting";
  YY_SYMBOL_PRINT (yymsg, yytype, yyvaluep, yylocationp);

  switch (yytype)
    {

      default:
        break;
    }
}

/** Number of symbols composing the right hand side of rule #RULE.  */
static inline int
yyrhsLength (yyRuleNum yyrule)
{
  return yyr2[yyrule];
}

static void
yydestroyGLRState (char const *yymsg, yyGLRState *yys, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  if (yys->yyresolved)
    yydestruct (yymsg, yystos[yys->yylrState],
                &yys->yysemantics.yysval, &yys->yyloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
  else
    {
#if YYDEBUG
      if (yydebug)
        {
          if (yys->yysemantics.yyfirstVal)
            YYFPRINTF (stderr, "%s unresolved ", yymsg);
          else
            YYFPRINTF (stderr, "%s incomplete ", yymsg);
          yy_symbol_print (stderr, yystos[yys->yylrState],
                           YY_NULL, &yys->yyloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
          YYFPRINTF (stderr, "\n");
        }
#endif

      if (yys->yysemantics.yyfirstVal)
        {
          yySemanticOption *yyoption = yys->yysemantics.yyfirstVal;
          yyGLRState *yyrh;
          int yyn;
          for (yyrh = yyoption->yystate, yyn = yyrhsLength (yyoption->yyrule);
               yyn > 0;
               yyrh = yyrh->yypred, yyn -= 1)
            yydestroyGLRState (yymsg, yyrh, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
        }
    }
}

/** Left-hand-side symbol for rule #RULE.  */
static inline yySymbol
yylhsNonterm (yyRuleNum yyrule)
{
  return yyr1[yyrule];
}

#define yypact_value_is_default(Yystate) \
  (!!((Yystate) == (-216)))

/** True iff LR state STATE has only a default reduction (regardless
 *  of token).  */
static inline yybool
yyisDefaultedState (yyStateNum yystate)
{
  return yypact_value_is_default (yypact[yystate]);
}

/** The default reduction for STATE, assuming it has one.  */
static inline yyRuleNum
yydefaultAction (yyStateNum yystate)
{
  return yydefact[yystate];
}

#define yytable_value_is_error(Yytable_value) \
  YYID (0)

/** Set *YYACTION to the action to take in YYSTATE on seeing YYTOKEN.
 *  Result R means
 *    R < 0:  Reduce on rule -R.
 *    R = 0:  Error.
 *    R > 0:  Shift to state R.
 *  Set *CONFLICTS to a pointer into yyconfl to 0-terminated list of
 *  conflicting reductions.
 */
static inline void
yygetLRActions (yyStateNum yystate, int yytoken,
                int* yyaction, const short int** yyconflicts)
{
  int yyindex = yypact[yystate] + yytoken;
  if (yypact_value_is_default (yypact[yystate])
      || yyindex < 0 || YYLAST < yyindex || yycheck[yyindex] != yytoken)
    {
      *yyaction = -yydefact[yystate];
      *yyconflicts = yyconfl;
    }
  else if (! yytable_value_is_error (yytable[yyindex]))
    {
      *yyaction = yytable[yyindex];
      *yyconflicts = yyconfl + yyconflp[yyindex];
    }
  else
    {
      *yyaction = 0;
      *yyconflicts = yyconfl + yyconflp[yyindex];
    }
}

static inline yyStateNum
yyLRgotoState (yyStateNum yystate, yySymbol yylhs)
{
  int yyr;
  yyr = yypgoto[yylhs - YYNTOKENS] + yystate;
  if (0 <= yyr && yyr <= YYLAST && yycheck[yyr] == yystate)
    return yytable[yyr];
  else
    return yydefgoto[yylhs - YYNTOKENS];
}

static inline yybool
yyisShiftAction (int yyaction)
{
  return 0 < yyaction;
}

static inline yybool
yyisErrorAction (int yyaction)
{
  return yyaction == 0;
}

                                /* GLRStates */

/** Return a fresh GLRStackItem.  Callers should call
 * YY_RESERVE_GLRSTACK afterwards to make sure there is sufficient
 * headroom.  */

static inline yyGLRStackItem*
yynewGLRStackItem (yyGLRStack* yystackp, yybool yyisState)
{
  yyGLRStackItem* yynewItem = yystackp->yynextFree;
  yystackp->yyspaceLeft -= 1;
  yystackp->yynextFree += 1;
  yynewItem->yystate.yyisState = yyisState;
  return yynewItem;
}

/** Add a new semantic action that will execute the action for rule
 *  RULENUM on the semantic values in RHS to the list of
 *  alternative actions for STATE.  Assumes that RHS comes from
 *  stack #K of *STACKP. */
static void
yyaddDeferredAction (yyGLRStack* yystackp, size_t yyk, yyGLRState* yystate,
                     yyGLRState* rhs, yyRuleNum yyrule)
{
  yySemanticOption* yynewOption =
    &yynewGLRStackItem (yystackp, yyfalse)->yyoption;
  yynewOption->yystate = rhs;
  yynewOption->yyrule = yyrule;
  if (yystackp->yytops.yylookaheadNeeds[yyk])
    {
      yynewOption->yyrawchar = yychar;
      yynewOption->yyval = yylval;
      yynewOption->yyloc = yylloc;
    }
  else
    yynewOption->yyrawchar = YYEMPTY;
  yynewOption->yynext = yystate->yysemantics.yyfirstVal;
  yystate->yysemantics.yyfirstVal = yynewOption;

  YY_RESERVE_GLRSTACK (yystackp);
}

                                /* GLRStacks */

/** Initialize SET to a singleton set containing an empty stack.  */
static yybool
yyinitStateSet (yyGLRStateSet* yyset)
{
  yyset->yysize = 1;
  yyset->yycapacity = 16;
  yyset->yystates = (yyGLRState**) YYMALLOC (16 * sizeof yyset->yystates[0]);
  if (! yyset->yystates)
    return yyfalse;
  yyset->yystates[0] = YY_NULL;
  yyset->yylookaheadNeeds =
    (yybool*) YYMALLOC (16 * sizeof yyset->yylookaheadNeeds[0]);
  if (! yyset->yylookaheadNeeds)
    {
      YYFREE (yyset->yystates);
      return yyfalse;
    }
  return yytrue;
}

static void yyfreeStateSet (yyGLRStateSet* yyset)
{
  YYFREE (yyset->yystates);
  YYFREE (yyset->yylookaheadNeeds);
}

/** Initialize STACK to a single empty stack, with total maximum
 *  capacity for all stacks of SIZE.  */
static yybool
yyinitGLRStack (yyGLRStack* yystackp, size_t yysize)
{
  yystackp->yyerrState = 0;
  yynerrs = 0;
  yystackp->yyspaceLeft = yysize;
  yystackp->yyitems =
    (yyGLRStackItem*) YYMALLOC (yysize * sizeof yystackp->yynextFree[0]);
  if (!yystackp->yyitems)
    return yyfalse;
  yystackp->yynextFree = yystackp->yyitems;
  yystackp->yysplitPoint = YY_NULL;
  yystackp->yylastDeleted = YY_NULL;
  return yyinitStateSet (&yystackp->yytops);
}


#if YYSTACKEXPANDABLE
# define YYRELOC(YYFROMITEMS,YYTOITEMS,YYX,YYTYPE) \
  &((YYTOITEMS) - ((YYFROMITEMS) - (yyGLRStackItem*) (YYX)))->YYTYPE

/** If STACK is expandable, extend it.  WARNING: Pointers into the
    stack from outside should be considered invalid after this call.
    We always expand when there are 1 or fewer items left AFTER an
    allocation, so that we can avoid having external pointers exist
    across an allocation.  */
static void
yyexpandGLRStack (yyGLRStack* yystackp)
{
  yyGLRStackItem* yynewItems;
  yyGLRStackItem* yyp0, *yyp1;
  size_t yynewSize;
  size_t yyn;
  size_t yysize = yystackp->yynextFree - yystackp->yyitems;
  if (YYMAXDEPTH - YYHEADROOM < yysize)
    yyMemoryExhausted (yystackp);
  yynewSize = 2*yysize;
  if (YYMAXDEPTH < yynewSize)
    yynewSize = YYMAXDEPTH;
  yynewItems = (yyGLRStackItem*) YYMALLOC (yynewSize * sizeof yynewItems[0]);
  if (! yynewItems)
    yyMemoryExhausted (yystackp);
  for (yyp0 = yystackp->yyitems, yyp1 = yynewItems, yyn = yysize;
       0 < yyn;
       yyn -= 1, yyp0 += 1, yyp1 += 1)
    {
      *yyp1 = *yyp0;
      if (*(yybool *) yyp0)
        {
          yyGLRState* yys0 = &yyp0->yystate;
          yyGLRState* yys1 = &yyp1->yystate;
          if (yys0->yypred != YY_NULL)
            yys1->yypred =
              YYRELOC (yyp0, yyp1, yys0->yypred, yystate);
          if (! yys0->yyresolved && yys0->yysemantics.yyfirstVal != YY_NULL)
            yys1->yysemantics.yyfirstVal =
              YYRELOC (yyp0, yyp1, yys0->yysemantics.yyfirstVal, yyoption);
        }
      else
        {
          yySemanticOption* yyv0 = &yyp0->yyoption;
          yySemanticOption* yyv1 = &yyp1->yyoption;
          if (yyv0->yystate != YY_NULL)
            yyv1->yystate = YYRELOC (yyp0, yyp1, yyv0->yystate, yystate);
          if (yyv0->yynext != YY_NULL)
            yyv1->yynext = YYRELOC (yyp0, yyp1, yyv0->yynext, yyoption);
        }
    }
  if (yystackp->yysplitPoint != YY_NULL)
    yystackp->yysplitPoint = YYRELOC (yystackp->yyitems, yynewItems,
                                 yystackp->yysplitPoint, yystate);

  for (yyn = 0; yyn < yystackp->yytops.yysize; yyn += 1)
    if (yystackp->yytops.yystates[yyn] != YY_NULL)
      yystackp->yytops.yystates[yyn] =
        YYRELOC (yystackp->yyitems, yynewItems,
                 yystackp->yytops.yystates[yyn], yystate);
  YYFREE (yystackp->yyitems);
  yystackp->yyitems = yynewItems;
  yystackp->yynextFree = yynewItems + yysize;
  yystackp->yyspaceLeft = yynewSize - yysize;
}
#endif

static void
yyfreeGLRStack (yyGLRStack* yystackp)
{
  YYFREE (yystackp->yyitems);
  yyfreeStateSet (&yystackp->yytops);
}

/** Assuming that S is a GLRState somewhere on STACK, update the
 *  splitpoint of STACK, if needed, so that it is at least as deep as
 *  S.  */
static inline void
yyupdateSplit (yyGLRStack* yystackp, yyGLRState* yys)
{
  if (yystackp->yysplitPoint != YY_NULL && yystackp->yysplitPoint > yys)
    yystackp->yysplitPoint = yys;
}

/** Invalidate stack #K in STACK.  */
static inline void
yymarkStackDeleted (yyGLRStack* yystackp, size_t yyk)
{
  if (yystackp->yytops.yystates[yyk] != YY_NULL)
    yystackp->yylastDeleted = yystackp->yytops.yystates[yyk];
  yystackp->yytops.yystates[yyk] = YY_NULL;
}

/** Undelete the last stack that was marked as deleted.  Can only be
    done once after a deletion, and only when all other stacks have
    been deleted.  */
static void
yyundeleteLastStack (yyGLRStack* yystackp)
{
  if (yystackp->yylastDeleted == YY_NULL || yystackp->yytops.yysize != 0)
    return;
  yystackp->yytops.yystates[0] = yystackp->yylastDeleted;
  yystackp->yytops.yysize = 1;
  YYDPRINTF ((stderr, "Restoring last deleted stack as stack #0.\n"));
  yystackp->yylastDeleted = YY_NULL;
}

static inline void
yyremoveDeletes (yyGLRStack* yystackp)
{
  size_t yyi, yyj;
  yyi = yyj = 0;
  while (yyj < yystackp->yytops.yysize)
    {
      if (yystackp->yytops.yystates[yyi] == YY_NULL)
        {
          if (yyi == yyj)
            {
              YYDPRINTF ((stderr, "Removing dead stacks.\n"));
            }
          yystackp->yytops.yysize -= 1;
        }
      else
        {
          yystackp->yytops.yystates[yyj] = yystackp->yytops.yystates[yyi];
          /* In the current implementation, it's unnecessary to copy
             yystackp->yytops.yylookaheadNeeds[yyi] since, after
             yyremoveDeletes returns, the parser immediately either enters
             deterministic operation or shifts a token.  However, it doesn't
             hurt, and the code might evolve to need it.  */
          yystackp->yytops.yylookaheadNeeds[yyj] =
            yystackp->yytops.yylookaheadNeeds[yyi];
          if (yyj != yyi)
            {
              YYDPRINTF ((stderr, "Rename stack %lu -> %lu.\n",
                          (unsigned long int) yyi, (unsigned long int) yyj));
            }
          yyj += 1;
        }
      yyi += 1;
    }
}

/** Shift to a new state on stack #K of STACK, corresponding to LR state
 * LRSTATE, at input position POSN, with (resolved) semantic value SVAL.  */
static inline void
yyglrShift (yyGLRStack* yystackp, size_t yyk, yyStateNum yylrState,
            size_t yyposn,
            YYSTYPE* yyvalp, YYLTYPE* yylocp)
{
  yyGLRState* yynewState = &yynewGLRStackItem (yystackp, yytrue)->yystate;

  yynewState->yylrState = yylrState;
  yynewState->yyposn = yyposn;
  yynewState->yyresolved = yytrue;
  yynewState->yypred = yystackp->yytops.yystates[yyk];
  yynewState->yysemantics.yysval = *yyvalp;
  yynewState->yyloc = *yylocp;
  yystackp->yytops.yystates[yyk] = yynewState;

  YY_RESERVE_GLRSTACK (yystackp);
}

/** Shift stack #K of YYSTACK, to a new state corresponding to LR
 *  state YYLRSTATE, at input position YYPOSN, with the (unresolved)
 *  semantic value of YYRHS under the action for YYRULE.  */
static inline void
yyglrShiftDefer (yyGLRStack* yystackp, size_t yyk, yyStateNum yylrState,
                 size_t yyposn, yyGLRState* rhs, yyRuleNum yyrule)
{
  yyGLRState* yynewState = &yynewGLRStackItem (yystackp, yytrue)->yystate;

  yynewState->yylrState = yylrState;
  yynewState->yyposn = yyposn;
  yynewState->yyresolved = yyfalse;
  yynewState->yypred = yystackp->yytops.yystates[yyk];
  yynewState->yysemantics.yyfirstVal = YY_NULL;
  yystackp->yytops.yystates[yyk] = yynewState;

  /* Invokes YY_RESERVE_GLRSTACK.  */
  yyaddDeferredAction (yystackp, yyk, yynewState, rhs, yyrule);
}

/** Pop the symbols consumed by reduction #RULE from the top of stack
 *  #K of STACK, and perform the appropriate semantic action on their
 *  semantic values.  Assumes that all ambiguities in semantic values
 *  have been previously resolved.  Set *VALP to the resulting value,
 *  and *LOCP to the computed location (if any).  Return value is as
 *  for userAction.  */
static inline YYRESULTTAG
yydoAction (yyGLRStack* yystackp, size_t yyk, yyRuleNum yyrule,
            YYSTYPE* yyvalp, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  int yynrhs = yyrhsLength (yyrule);

  if (yystackp->yysplitPoint == YY_NULL)
    {
      /* Standard special case: single stack.  */
      yyGLRStackItem* rhs = (yyGLRStackItem*) yystackp->yytops.yystates[yyk];
      YYASSERT (yyk == 0);
      yystackp->yynextFree -= yynrhs;
      yystackp->yyspaceLeft += yynrhs;
      yystackp->yytops.yystates[0] = & yystackp->yynextFree[-1].yystate;
      return yyuserAction (yyrule, yynrhs, rhs, yystackp,
                           yyvalp, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
    }
  else
    {
      /* At present, doAction is never called in nondeterministic
       * mode, so this branch is never taken.  It is here in
       * anticipation of a future feature that will allow immediate
       * evaluation of selected actions in nondeterministic mode.  */
      int yyi;
      yyGLRState* yys;
      yyGLRStackItem yyrhsVals[YYMAXRHS + YYMAXLEFT + 1];
      yys = yyrhsVals[YYMAXRHS + YYMAXLEFT].yystate.yypred
        = yystackp->yytops.yystates[yyk];
      if (yynrhs == 0)
        /* Set default location.  */
        yyrhsVals[YYMAXRHS + YYMAXLEFT - 1].yystate.yyloc = yys->yyloc;
      for (yyi = 0; yyi < yynrhs; yyi += 1)
        {
          yys = yys->yypred;
          YYASSERT (yys);
        }
      yyupdateSplit (yystackp, yys);
      yystackp->yytops.yystates[yyk] = yys;
      return yyuserAction (yyrule, yynrhs, yyrhsVals + YYMAXRHS + YYMAXLEFT - 1,
                           yystackp, yyvalp, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
    }
}

#if !YYDEBUG
# define YY_REDUCE_PRINT(Args)
#else
# define YY_REDUCE_PRINT(Args)          \
do {                                    \
  if (yydebug)                          \
    yy_reduce_print Args;               \
} while (YYID (0))

/*----------------------------------------------------------.
| Report that the RULE is going to be reduced on stack #K.  |
`----------------------------------------------------------*/

/*ARGSUSED*/ static inline void
yy_reduce_print (yyGLRStack* yystackp, size_t yyk, yyRuleNum yyrule,
                 YYSTYPE* yyvalp, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  int yynrhs = yyrhsLength (yyrule);
  yybool yynormal __attribute__ ((__unused__)) =
    (yystackp->yysplitPoint == YY_NULL);
  yyGLRStackItem* yyvsp = (yyGLRStackItem*) yystackp->yytops.yystates[yyk];
  int yylow = 1;
  int yyi;
  YYUSE (yyvalp);
  YYUSE (yylocp);
  YYUSE (yyparser);
  YYUSE (m_LDConfig);
  YYUSE (m_ScriptFile);
  YYUSE (m_ScriptScanner);
  YYUSE (m_ObjectReader);
  YYUSE (m_ArchiveReader);
  YYUSE (m_DynObjReader);
  YYUSE (m_GroupReader);
  YYFPRINTF (stderr, "Reducing stack %lu by rule %d (line %lu):\n",
             (unsigned long int) yyk, yyrule - 1,
             (unsigned long int) yyrline[yyrule]);
  /* The symbols being reduced.  */
  for (yyi = 0; yyi < yynrhs; yyi++)
    {
      YYFPRINTF (stderr, "   $%d = ", yyi + 1);
      yy_symbol_print (stderr, yyrhs[yyprhs[yyrule] + yyi],
                       &(((yyGLRStackItem const *)yyvsp)[YYFILL ((yyi + 1) - (yynrhs))].yystate.yysemantics.yysval)
                       , &(((yyGLRStackItem const *)yyvsp)[YYFILL ((yyi + 1) - (yynrhs))].yystate.yyloc)                       , yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
      YYFPRINTF (stderr, "\n");
    }
}
#endif

/** Pop items off stack #K of STACK according to grammar rule RULE,
 *  and push back on the resulting nonterminal symbol.  Perform the
 *  semantic action associated with RULE and store its value with the
 *  newly pushed state, if FORCEEVAL or if STACK is currently
 *  unambiguous.  Otherwise, store the deferred semantic action with
 *  the new state.  If the new state would have an identical input
 *  position, LR state, and predecessor to an existing state on the stack,
 *  it is identified with that existing state, eliminating stack #K from
 *  the STACK.  In this case, the (necessarily deferred) semantic value is
 *  added to the options for the existing state's semantic value.
 */
static inline YYRESULTTAG
yyglrReduce (yyGLRStack* yystackp, size_t yyk, yyRuleNum yyrule,
             yybool yyforceEval, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  size_t yyposn = yystackp->yytops.yystates[yyk]->yyposn;

  if (yyforceEval || yystackp->yysplitPoint == YY_NULL)
    {
      YYSTYPE yysval;
      YYLTYPE yyloc;

      YY_REDUCE_PRINT ((yystackp, yyk, yyrule, &yysval, &yyloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
      YYCHK (yydoAction (yystackp, yyk, yyrule, &yysval, &yyloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
      YY_SYMBOL_PRINT ("-> $$ =", yyr1[yyrule], &yysval, &yyloc);
      yyglrShift (yystackp, yyk,
                  yyLRgotoState (yystackp->yytops.yystates[yyk]->yylrState,
                                 yylhsNonterm (yyrule)),
                  yyposn, &yysval, &yyloc);
    }
  else
    {
      size_t yyi;
      int yyn;
      yyGLRState* yys, *yys0 = yystackp->yytops.yystates[yyk];
      yyStateNum yynewLRState;

      for (yys = yystackp->yytops.yystates[yyk], yyn = yyrhsLength (yyrule);
           0 < yyn; yyn -= 1)
        {
          yys = yys->yypred;
          YYASSERT (yys);
        }
      yyupdateSplit (yystackp, yys);
      yynewLRState = yyLRgotoState (yys->yylrState, yylhsNonterm (yyrule));
      YYDPRINTF ((stderr,
                  "Reduced stack %lu by rule #%d; action deferred.  Now in state %d.\n",
                  (unsigned long int) yyk, yyrule - 1, yynewLRState));
      for (yyi = 0; yyi < yystackp->yytops.yysize; yyi += 1)
        if (yyi != yyk && yystackp->yytops.yystates[yyi] != YY_NULL)
          {
            yyGLRState *yysplit = yystackp->yysplitPoint;
            yyGLRState *yyp = yystackp->yytops.yystates[yyi];
            while (yyp != yys && yyp != yysplit && yyp->yyposn >= yyposn)
              {
                if (yyp->yylrState == yynewLRState && yyp->yypred == yys)
                  {
                    yyaddDeferredAction (yystackp, yyk, yyp, yys0, yyrule);
                    yymarkStackDeleted (yystackp, yyk);
                    YYDPRINTF ((stderr, "Merging stack %lu into stack %lu.\n",
                                (unsigned long int) yyk,
                                (unsigned long int) yyi));
                    return yyok;
                  }
                yyp = yyp->yypred;
              }
          }
      yystackp->yytops.yystates[yyk] = yys;
      yyglrShiftDefer (yystackp, yyk, yynewLRState, yyposn, yys0, yyrule);
    }
  return yyok;
}

static size_t
yysplitStack (yyGLRStack* yystackp, size_t yyk)
{
  if (yystackp->yysplitPoint == YY_NULL)
    {
      YYASSERT (yyk == 0);
      yystackp->yysplitPoint = yystackp->yytops.yystates[yyk];
    }
  if (yystackp->yytops.yysize >= yystackp->yytops.yycapacity)
    {
      yyGLRState** yynewStates;
      yybool* yynewLookaheadNeeds;

      yynewStates = YY_NULL;

      if (yystackp->yytops.yycapacity
          > (YYSIZEMAX / (2 * sizeof yynewStates[0])))
        yyMemoryExhausted (yystackp);
      yystackp->yytops.yycapacity *= 2;

      yynewStates =
        (yyGLRState**) YYREALLOC (yystackp->yytops.yystates,
                                  (yystackp->yytops.yycapacity
                                   * sizeof yynewStates[0]));
      if (yynewStates == YY_NULL)
        yyMemoryExhausted (yystackp);
      yystackp->yytops.yystates = yynewStates;

      yynewLookaheadNeeds =
        (yybool*) YYREALLOC (yystackp->yytops.yylookaheadNeeds,
                             (yystackp->yytops.yycapacity
                              * sizeof yynewLookaheadNeeds[0]));
      if (yynewLookaheadNeeds == YY_NULL)
        yyMemoryExhausted (yystackp);
      yystackp->yytops.yylookaheadNeeds = yynewLookaheadNeeds;
    }
  yystackp->yytops.yystates[yystackp->yytops.yysize]
    = yystackp->yytops.yystates[yyk];
  yystackp->yytops.yylookaheadNeeds[yystackp->yytops.yysize]
    = yystackp->yytops.yylookaheadNeeds[yyk];
  yystackp->yytops.yysize += 1;
  return yystackp->yytops.yysize-1;
}

/** True iff Y0 and Y1 represent identical options at the top level.
 *  That is, they represent the same rule applied to RHS symbols
 *  that produce the same terminal symbols.  */
static yybool
yyidenticalOptions (yySemanticOption* yyy0, yySemanticOption* yyy1)
{
  if (yyy0->yyrule == yyy1->yyrule)
    {
      yyGLRState *yys0, *yys1;
      int yyn;
      for (yys0 = yyy0->yystate, yys1 = yyy1->yystate,
           yyn = yyrhsLength (yyy0->yyrule);
           yyn > 0;
           yys0 = yys0->yypred, yys1 = yys1->yypred, yyn -= 1)
        if (yys0->yyposn != yys1->yyposn)
          return yyfalse;
      return yytrue;
    }
  else
    return yyfalse;
}

/** Assuming identicalOptions (Y0,Y1), destructively merge the
 *  alternative semantic values for the RHS-symbols of Y1 and Y0.  */
static void
yymergeOptionSets (yySemanticOption* yyy0, yySemanticOption* yyy1)
{
  yyGLRState *yys0, *yys1;
  int yyn;
  for (yys0 = yyy0->yystate, yys1 = yyy1->yystate,
       yyn = yyrhsLength (yyy0->yyrule);
       yyn > 0;
       yys0 = yys0->yypred, yys1 = yys1->yypred, yyn -= 1)
    {
      if (yys0 == yys1)
        break;
      else if (yys0->yyresolved)
        {
          yys1->yyresolved = yytrue;
          yys1->yysemantics.yysval = yys0->yysemantics.yysval;
        }
      else if (yys1->yyresolved)
        {
          yys0->yyresolved = yytrue;
          yys0->yysemantics.yysval = yys1->yysemantics.yysval;
        }
      else
        {
          yySemanticOption** yyz0p = &yys0->yysemantics.yyfirstVal;
          yySemanticOption* yyz1 = yys1->yysemantics.yyfirstVal;
          while (YYID (yytrue))
            {
              if (yyz1 == *yyz0p || yyz1 == YY_NULL)
                break;
              else if (*yyz0p == YY_NULL)
                {
                  *yyz0p = yyz1;
                  break;
                }
              else if (*yyz0p < yyz1)
                {
                  yySemanticOption* yyz = *yyz0p;
                  *yyz0p = yyz1;
                  yyz1 = yyz1->yynext;
                  (*yyz0p)->yynext = yyz;
                }
              yyz0p = &(*yyz0p)->yynext;
            }
          yys1->yysemantics.yyfirstVal = yys0->yysemantics.yyfirstVal;
        }
    }
}

/** Y0 and Y1 represent two possible actions to take in a given
 *  parsing state; return 0 if no combination is possible,
 *  1 if user-mergeable, 2 if Y0 is preferred, 3 if Y1 is preferred.  */
static int
yypreference (yySemanticOption* y0, yySemanticOption* y1)
{
  yyRuleNum r0 = y0->yyrule, r1 = y1->yyrule;
  int p0 = yydprec[r0], p1 = yydprec[r1];

  if (p0 == p1)
    {
      if (yymerger[r0] == 0 || yymerger[r0] != yymerger[r1])
        return 0;
      else
        return 1;
    }
  if (p0 == 0 || p1 == 0)
    return 0;
  if (p0 < p1)
    return 3;
  if (p1 < p0)
    return 2;
  return 0;
}

static YYRESULTTAG yyresolveValue (yyGLRState* yys,
                                   yyGLRStack* yystackp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader);


/** Resolve the previous N states starting at and including state S.  If result
 *  != yyok, some states may have been left unresolved possibly with empty
 *  semantic option chains.  Regardless of whether result = yyok, each state
 *  has been left with consistent data so that yydestroyGLRState can be invoked
 *  if necessary.  */
static YYRESULTTAG
yyresolveStates (yyGLRState* yys, int yyn,
                 yyGLRStack* yystackp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  if (0 < yyn)
    {
      YYASSERT (yys->yypred);
      YYCHK (yyresolveStates (yys->yypred, yyn-1, yystackp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
      if (! yys->yyresolved)
        YYCHK (yyresolveValue (yys, yystackp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
    }
  return yyok;
}

/** Resolve the states for the RHS of OPT, perform its user action, and return
 *  the semantic value and location.  Regardless of whether result = yyok, all
 *  RHS states have been destroyed (assuming the user action destroys all RHS
 *  semantic values if invoked).  */
static YYRESULTTAG
yyresolveAction (yySemanticOption* yyopt, yyGLRStack* yystackp,
                 YYSTYPE* yyvalp, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  yyGLRStackItem yyrhsVals[YYMAXRHS + YYMAXLEFT + 1];
  int yynrhs = yyrhsLength (yyopt->yyrule);
  YYRESULTTAG yyflag =
    yyresolveStates (yyopt->yystate, yynrhs, yystackp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
  if (yyflag != yyok)
    {
      yyGLRState *yys;
      for (yys = yyopt->yystate; yynrhs > 0; yys = yys->yypred, yynrhs -= 1)
        yydestroyGLRState ("Cleanup: popping", yys, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
      return yyflag;
    }

  yyrhsVals[YYMAXRHS + YYMAXLEFT].yystate.yypred = yyopt->yystate;
  if (yynrhs == 0)
    /* Set default location.  */
    yyrhsVals[YYMAXRHS + YYMAXLEFT - 1].yystate.yyloc = yyopt->yystate->yyloc;
  {
    int yychar_current = yychar;
    YYSTYPE yylval_current = yylval;
    YYLTYPE yylloc_current = yylloc;
    yychar = yyopt->yyrawchar;
    yylval = yyopt->yyval;
    yylloc = yyopt->yyloc;
    yyflag = yyuserAction (yyopt->yyrule, yynrhs,
                           yyrhsVals + YYMAXRHS + YYMAXLEFT - 1,
                           yystackp, yyvalp, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
    yychar = yychar_current;
    yylval = yylval_current;
    yylloc = yylloc_current;
  }
  return yyflag;
}

#if YYDEBUG
static void
yyreportTree (yySemanticOption* yyx, int yyindent)
{
  int yynrhs = yyrhsLength (yyx->yyrule);
  int yyi;
  yyGLRState* yys;
  yyGLRState* yystates[1 + YYMAXRHS];
  yyGLRState yyleftmost_state;

  for (yyi = yynrhs, yys = yyx->yystate; 0 < yyi; yyi -= 1, yys = yys->yypred)
    yystates[yyi] = yys;
  if (yys == YY_NULL)
    {
      yyleftmost_state.yyposn = 0;
      yystates[0] = &yyleftmost_state;
    }
  else
    yystates[0] = yys;

  if (yyx->yystate->yyposn < yys->yyposn + 1)
    YYFPRINTF (stderr, "%*s%s -> <Rule %d, empty>\n",
               yyindent, "", yytokenName (yylhsNonterm (yyx->yyrule)),
               yyx->yyrule - 1);
  else
    YYFPRINTF (stderr, "%*s%s -> <Rule %d, tokens %lu .. %lu>\n",
               yyindent, "", yytokenName (yylhsNonterm (yyx->yyrule)),
               yyx->yyrule - 1, (unsigned long int) (yys->yyposn + 1),
               (unsigned long int) yyx->yystate->yyposn);
  for (yyi = 1; yyi <= yynrhs; yyi += 1)
    {
      if (yystates[yyi]->yyresolved)
        {
          if (yystates[yyi-1]->yyposn+1 > yystates[yyi]->yyposn)
            YYFPRINTF (stderr, "%*s%s <empty>\n", yyindent+2, "",
                       yytokenName (yyrhs[yyprhs[yyx->yyrule]+yyi-1]));
          else
            YYFPRINTF (stderr, "%*s%s <tokens %lu .. %lu>\n", yyindent+2, "",
                       yytokenName (yyrhs[yyprhs[yyx->yyrule]+yyi-1]),
                       (unsigned long int) (yystates[yyi - 1]->yyposn + 1),
                       (unsigned long int) yystates[yyi]->yyposn);
        }
      else
        yyreportTree (yystates[yyi]->yysemantics.yyfirstVal, yyindent+2);
    }
}
#endif

/*ARGSUSED*/ static YYRESULTTAG
yyreportAmbiguity (yySemanticOption* yyx0,
                   yySemanticOption* yyx1, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  YYUSE (yyx0);
  YYUSE (yyx1);

#if YYDEBUG
  YYFPRINTF (stderr, "Ambiguity detected.\n");
  YYFPRINTF (stderr, "Option 1,\n");
  yyreportTree (yyx0, 2);
  YYFPRINTF (stderr, "\nOption 2,\n");
  yyreportTree (yyx1, 2);
  YYFPRINTF (stderr, "\n");
#endif

  yyerror (yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_("syntax is ambiguous"));
  return yyabort;
}

/** Starting at and including state S1, resolve the location for each of the
 *  previous N1 states that is unresolved.  The first semantic option of a state
 *  is always chosen.  */
static void
yyresolveLocations (yyGLRState* yys1, int yyn1,
                    yyGLRStack *yystackp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  if (0 < yyn1)
    {
      yyresolveLocations (yys1->yypred, yyn1 - 1, yystackp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
      if (!yys1->yyresolved)
        {
          yyGLRStackItem yyrhsloc[1 + YYMAXRHS];
          int yynrhs;
          yySemanticOption *yyoption = yys1->yysemantics.yyfirstVal;
          YYASSERT (yyoption != YY_NULL);
          yynrhs = yyrhsLength (yyoption->yyrule);
          if (yynrhs > 0)
            {
              yyGLRState *yys;
              int yyn;
              yyresolveLocations (yyoption->yystate, yynrhs,
                                  yystackp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
              for (yys = yyoption->yystate, yyn = yynrhs;
                   yyn > 0;
                   yys = yys->yypred, yyn -= 1)
                yyrhsloc[yyn].yystate.yyloc = yys->yyloc;
            }
          else
            {
              /* Both yyresolveAction and yyresolveLocations traverse the GSS
                 in reverse rightmost order.  It is only necessary to invoke
                 yyresolveLocations on a subforest for which yyresolveAction
                 would have been invoked next had an ambiguity not been
                 detected.  Thus the location of the previous state (but not
                 necessarily the previous state itself) is guaranteed to be
                 resolved already.  */
              yyGLRState *yyprevious = yyoption->yystate;
              yyrhsloc[0].yystate.yyloc = yyprevious->yyloc;
            }
          {
            int yychar_current = yychar;
            YYSTYPE yylval_current = yylval;
            YYLTYPE yylloc_current = yylloc;
            yychar = yyoption->yyrawchar;
            yylval = yyoption->yyval;
            yylloc = yyoption->yyloc;
            YYLLOC_DEFAULT ((yys1->yyloc), yyrhsloc, yynrhs);
            yychar = yychar_current;
            yylval = yylval_current;
            yylloc = yylloc_current;
          }
        }
    }
}

/** Resolve the ambiguity represented in state S, perform the indicated
 *  actions, and set the semantic value of S.  If result != yyok, the chain of
 *  semantic options in S has been cleared instead or it has been left
 *  unmodified except that redundant options may have been removed.  Regardless
 *  of whether result = yyok, S has been left with consistent data so that
 *  yydestroyGLRState can be invoked if necessary.  */
static YYRESULTTAG
yyresolveValue (yyGLRState* yys, yyGLRStack* yystackp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  yySemanticOption* yyoptionList = yys->yysemantics.yyfirstVal;
  yySemanticOption* yybest = yyoptionList;
  yySemanticOption** yypp;
  yybool yymerge = yyfalse;
  YYSTYPE yysval;
  YYRESULTTAG yyflag;
  YYLTYPE *yylocp = &yys->yyloc;

  for (yypp = &yyoptionList->yynext; *yypp != YY_NULL; )
    {
      yySemanticOption* yyp = *yypp;

      if (yyidenticalOptions (yybest, yyp))
        {
          yymergeOptionSets (yybest, yyp);
          *yypp = yyp->yynext;
        }
      else
        {
          switch (yypreference (yybest, yyp))
            {
            case 0:
              yyresolveLocations (yys, 1, yystackp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
              return yyreportAmbiguity (yybest, yyp, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
              break;
            case 1:
              yymerge = yytrue;
              break;
            case 2:
              break;
            case 3:
              yybest = yyp;
              yymerge = yyfalse;
              break;
            default:
              /* This cannot happen so it is not worth a YYASSERT (yyfalse),
                 but some compilers complain if the default case is
                 omitted.  */
              break;
            }
          yypp = &yyp->yynext;
        }
    }

  if (yymerge)
    {
      yySemanticOption* yyp;
      int yyprec = yydprec[yybest->yyrule];
      yyflag = yyresolveAction (yybest, yystackp, &yysval, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
      if (yyflag == yyok)
        for (yyp = yybest->yynext; yyp != YY_NULL; yyp = yyp->yynext)
          {
            if (yyprec == yydprec[yyp->yyrule])
              {
                YYSTYPE yysval_other;
                YYLTYPE yydummy;
                yyflag = yyresolveAction (yyp, yystackp, &yysval_other, &yydummy, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
                if (yyflag != yyok)
                  {
                    yydestruct ("Cleanup: discarding incompletely merged value for",
                                yystos[yys->yylrState],
                                &yysval, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
                    break;
                  }
                yyuserMerge (yymerger[yyp->yyrule], &yysval, &yysval_other);
              }
          }
    }
  else
    yyflag = yyresolveAction (yybest, yystackp, &yysval, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);

  if (yyflag == yyok)
    {
      yys->yyresolved = yytrue;
      yys->yysemantics.yysval = yysval;
    }
  else
    yys->yysemantics.yyfirstVal = YY_NULL;
  return yyflag;
}

static YYRESULTTAG
yyresolveStack (yyGLRStack* yystackp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  if (yystackp->yysplitPoint != YY_NULL)
    {
      yyGLRState* yys;
      int yyn;

      for (yyn = 0, yys = yystackp->yytops.yystates[0];
           yys != yystackp->yysplitPoint;
           yys = yys->yypred, yyn += 1)
        continue;
      YYCHK (yyresolveStates (yystackp->yytops.yystates[0], yyn, yystackp
                             , yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
    }
  return yyok;
}

static void
yycompressStack (yyGLRStack* yystackp)
{
  yyGLRState* yyp, *yyq, *yyr;

  if (yystackp->yytops.yysize != 1 || yystackp->yysplitPoint == YY_NULL)
    return;

  for (yyp = yystackp->yytops.yystates[0], yyq = yyp->yypred, yyr = YY_NULL;
       yyp != yystackp->yysplitPoint;
       yyr = yyp, yyp = yyq, yyq = yyp->yypred)
    yyp->yypred = yyr;

  yystackp->yyspaceLeft += yystackp->yynextFree - yystackp->yyitems;
  yystackp->yynextFree = ((yyGLRStackItem*) yystackp->yysplitPoint) + 1;
  yystackp->yyspaceLeft -= yystackp->yynextFree - yystackp->yyitems;
  yystackp->yysplitPoint = YY_NULL;
  yystackp->yylastDeleted = YY_NULL;

  while (yyr != YY_NULL)
    {
      yystackp->yynextFree->yystate = *yyr;
      yyr = yyr->yypred;
      yystackp->yynextFree->yystate.yypred = &yystackp->yynextFree[-1].yystate;
      yystackp->yytops.yystates[0] = &yystackp->yynextFree->yystate;
      yystackp->yynextFree += 1;
      yystackp->yyspaceLeft -= 1;
    }
}

static YYRESULTTAG
yyprocessOneStack (yyGLRStack* yystackp, size_t yyk,
                   size_t yyposn, YYLTYPE *yylocp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  int yyaction;
  const short int* yyconflicts;
  yyRuleNum yyrule;

  while (yystackp->yytops.yystates[yyk] != YY_NULL)
    {
      yyStateNum yystate = yystackp->yytops.yystates[yyk]->yylrState;
      YYDPRINTF ((stderr, "Stack %lu Entering state %d\n",
                  (unsigned long int) yyk, yystate));

      YYASSERT (yystate != YYFINAL);

      if (yyisDefaultedState (yystate))
        {
          yyrule = yydefaultAction (yystate);
          if (yyrule == 0)
            {
              YYDPRINTF ((stderr, "Stack %lu dies.\n",
                          (unsigned long int) yyk));
              yymarkStackDeleted (yystackp, yyk);
              return yyok;
            }
          YYCHK (yyglrReduce (yystackp, yyk, yyrule, yyfalse, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
        }
      else
        {
          yySymbol yytoken;
          yystackp->yytops.yylookaheadNeeds[yyk] = yytrue;
          if (yychar == YYEMPTY)
            {
              YYDPRINTF ((stderr, "Reading a token: "));
              yychar = YYLEX;
            }

          if (yychar <= YYEOF)
            {
              yychar = yytoken = YYEOF;
              YYDPRINTF ((stderr, "Now at end of input.\n"));
            }
          else
            {
              yytoken = YYTRANSLATE (yychar);
              YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
            }

          yygetLRActions (yystate, yytoken, &yyaction, &yyconflicts);

          while (*yyconflicts != 0)
            {
              size_t yynewStack = yysplitStack (yystackp, yyk);
              YYDPRINTF ((stderr, "Splitting off stack %lu from %lu.\n",
                          (unsigned long int) yynewStack,
                          (unsigned long int) yyk));
              YYCHK (yyglrReduce (yystackp, yynewStack,
                                  *yyconflicts, yyfalse, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
              YYCHK (yyprocessOneStack (yystackp, yynewStack,
                                        yyposn, yylocp, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
              yyconflicts += 1;
            }

          if (yyisShiftAction (yyaction))
            break;
          else if (yyisErrorAction (yyaction))
            {
              YYDPRINTF ((stderr, "Stack %lu dies.\n",
                          (unsigned long int) yyk));
              yymarkStackDeleted (yystackp, yyk);
              break;
            }
          else
            YYCHK (yyglrReduce (yystackp, yyk, -yyaction,
                                yyfalse, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
        }
    }
  return yyok;
}

/*ARGSUSED*/ static void
yyreportSyntaxError (yyGLRStack* yystackp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  if (yystackp->yyerrState != 0)
    return;
#if ! YYERROR_VERBOSE
  yyerror (&yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_("syntax error"));
#else
  {
  yySymbol yytoken = yychar == YYEMPTY ? YYEMPTY : YYTRANSLATE (yychar);
  size_t yysize0 = yytnamerr (YY_NULL, yytokenName (yytoken));
  size_t yysize = yysize0;
  yybool yysize_overflow = yyfalse;
  char* yymsg = YY_NULL;
  enum { YYERROR_VERBOSE_ARGS_MAXIMUM = 5 };
  /* Internationalized format string. */
  const char *yyformat = YY_NULL;
  /* Arguments of yyformat. */
  char const *yyarg[YYERROR_VERBOSE_ARGS_MAXIMUM];
  /* Number of reported tokens (one for the "unexpected", one per
     "expected").  */
  int yycount = 0;

  /* There are many possibilities here to consider:
     - If this state is a consistent state with a default action, then
       the only way this function was invoked is if the default action
       is an error action.  In that case, don't check for expected
       tokens because there are none.
     - The only way there can be no lookahead present (in yychar) is if
       this state is a consistent state with a default action.  Thus,
       detecting the absence of a lookahead is sufficient to determine
       that there is no unexpected or expected token to report.  In that
       case, just report a simple "syntax error".
     - Don't assume there isn't a lookahead just because this state is a
       consistent state with a default action.  There might have been a
       previous inconsistent state, consistent state with a non-default
       action, or user semantic action that manipulated yychar.
     - Of course, the expected token list depends on states to have
       correct lookahead information, and it depends on the parser not
       to perform extra reductions after fetching a lookahead from the
       scanner and before detecting a syntax error.  Thus, state merging
       (from LALR or IELR) and default reductions corrupt the expected
       token list.  However, the list is correct for canonical LR with
       one exception: it will still contain any token that will not be
       accepted due to an error action in a later state.
  */
  if (yytoken != YYEMPTY)
    {
      int yyn = yypact[yystackp->yytops.yystates[0]->yylrState];
      yyarg[yycount++] = yytokenName (yytoken);
      if (!yypact_value_is_default (yyn))
        {
          /* Start YYX at -YYN if negative to avoid negative indexes in
             YYCHECK.  In other words, skip the first -YYN actions for this
             state because they are default actions.  */
          int yyxbegin = yyn < 0 ? -yyn : 0;
          /* Stay within bounds of both yycheck and yytname.  */
          int yychecklim = YYLAST - yyn + 1;
          int yyxend = yychecklim < YYNTOKENS ? yychecklim : YYNTOKENS;
          int yyx;
          for (yyx = yyxbegin; yyx < yyxend; ++yyx)
            if (yycheck[yyx + yyn] == yyx && yyx != YYTERROR
                && !yytable_value_is_error (yytable[yyx + yyn]))
              {
                if (yycount == YYERROR_VERBOSE_ARGS_MAXIMUM)
                  {
                    yycount = 1;
                    yysize = yysize0;
                    break;
                  }
                yyarg[yycount++] = yytokenName (yyx);
                {
                  size_t yysz = yysize + yytnamerr (YY_NULL, yytokenName (yyx));
                  yysize_overflow |= yysz < yysize;
                  yysize = yysz;
                }
              }
        }
    }

  switch (yycount)
    {
#define YYCASE_(N, S)                   \
      case N:                           \
        yyformat = S;                   \
      break
      YYCASE_(0, YY_("syntax error"));
      YYCASE_(1, YY_("syntax error, unexpected %s"));
      YYCASE_(2, YY_("syntax error, unexpected %s, expecting %s"));
      YYCASE_(3, YY_("syntax error, unexpected %s, expecting %s or %s"));
      YYCASE_(4, YY_("syntax error, unexpected %s, expecting %s or %s or %s"));
      YYCASE_(5, YY_("syntax error, unexpected %s, expecting %s or %s or %s or %s"));
#undef YYCASE_
    }

  {
    size_t yysz = yysize + strlen (yyformat);
    yysize_overflow |= yysz < yysize;
    yysize = yysz;
  }

  if (!yysize_overflow)
    yymsg = (char *) YYMALLOC (yysize);

  if (yymsg)
    {
      char *yyp = yymsg;
      int yyi = 0;
      while ((*yyp = *yyformat))
        {
          if (*yyp == '%' && yyformat[1] == 's' && yyi < yycount)
            {
              yyp += yytnamerr (yyp, yyarg[yyi++]);
              yyformat += 2;
            }
          else
            {
              yyp++;
              yyformat++;
            }
        }
      yyerror (&yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, yymsg);
      YYFREE (yymsg);
    }
  else
    {
      yyerror (&yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_("syntax error"));
      yyMemoryExhausted (yystackp);
    }
  }
#endif /* YYERROR_VERBOSE */
  yynerrs += 1;
}

/* Recover from a syntax error on *YYSTACKP, assuming that *YYSTACKP->YYTOKENP,
   yylval, and yylloc are the syntactic category, semantic value, and location
   of the lookahead.  */
/*ARGSUSED*/ static void
yyrecoverSyntaxError (yyGLRStack* yystackp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  size_t yyk;
  int yyj;

  if (yystackp->yyerrState == 3)
    /* We just shifted the error token and (perhaps) took some
       reductions.  Skip tokens until we can proceed.  */
    while (YYID (yytrue))
      {
        yySymbol yytoken;
        if (yychar == YYEOF)
          yyFail (yystackp, &yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_NULL);
        if (yychar != YYEMPTY)
          {
            /* We throw away the lookahead, but the error range
               of the shifted error token must take it into account.  */
            yyGLRState *yys = yystackp->yytops.yystates[0];
            yyGLRStackItem yyerror_range[3];
            yyerror_range[1].yystate.yyloc = yys->yyloc;
            yyerror_range[2].yystate.yyloc = yylloc;
            YYLLOC_DEFAULT ((yys->yyloc), yyerror_range, 2);
            yytoken = YYTRANSLATE (yychar);
            yydestruct ("Error: discarding",
                        yytoken, &yylval, &yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
          }
        YYDPRINTF ((stderr, "Reading a token: "));
        yychar = YYLEX;
        if (yychar <= YYEOF)
          {
            yychar = yytoken = YYEOF;
            YYDPRINTF ((stderr, "Now at end of input.\n"));
          }
        else
          {
            yytoken = YYTRANSLATE (yychar);
            YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
          }
        yyj = yypact[yystackp->yytops.yystates[0]->yylrState];
        if (yypact_value_is_default (yyj))
          return;
        yyj += yytoken;
        if (yyj < 0 || YYLAST < yyj || yycheck[yyj] != yytoken)
          {
            if (yydefact[yystackp->yytops.yystates[0]->yylrState] != 0)
              return;
          }
        else if (! yytable_value_is_error (yytable[yyj]))
          return;
      }

  /* Reduce to one stack.  */
  for (yyk = 0; yyk < yystackp->yytops.yysize; yyk += 1)
    if (yystackp->yytops.yystates[yyk] != YY_NULL)
      break;
  if (yyk >= yystackp->yytops.yysize)
    yyFail (yystackp, &yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_NULL);
  for (yyk += 1; yyk < yystackp->yytops.yysize; yyk += 1)
    yymarkStackDeleted (yystackp, yyk);
  yyremoveDeletes (yystackp);
  yycompressStack (yystackp);

  /* Now pop stack until we find a state that shifts the error token.  */
  yystackp->yyerrState = 3;
  while (yystackp->yytops.yystates[0] != YY_NULL)
    {
      yyGLRState *yys = yystackp->yytops.yystates[0];
      yyj = yypact[yys->yylrState];
      if (! yypact_value_is_default (yyj))
        {
          yyj += YYTERROR;
          if (0 <= yyj && yyj <= YYLAST && yycheck[yyj] == YYTERROR
              && yyisShiftAction (yytable[yyj]))
            {
              /* Shift the error token.  */
              /* First adjust its location.*/
              YYLTYPE yyerrloc;
              yystackp->yyerror_range[2].yystate.yyloc = yylloc;
              YYLLOC_DEFAULT (yyerrloc, (yystackp->yyerror_range), 2);
              YY_SYMBOL_PRINT ("Shifting", yystos[yytable[yyj]],
                               &yylval, &yyerrloc);
              yyglrShift (yystackp, 0, yytable[yyj],
                          yys->yyposn, &yylval, &yyerrloc);
              yys = yystackp->yytops.yystates[0];
              break;
            }
        }
      yystackp->yyerror_range[1].yystate.yyloc = yys->yyloc;
      if (yys->yypred != YY_NULL)
        yydestroyGLRState ("Error: popping", yys, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
      yystackp->yytops.yystates[0] = yys->yypred;
      yystackp->yynextFree -= 1;
      yystackp->yyspaceLeft += 1;
    }
  if (yystackp->yytops.yystates[0] == YY_NULL)
    yyFail (yystackp, &yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_NULL);
}

#define YYCHK1(YYE)                                                          \
  do {                                                                       \
    switch (YYE) {                                                           \
    case yyok:                                                               \
      break;                                                                 \
    case yyabort:                                                            \
      goto yyabortlab;                                                       \
    case yyaccept:                                                           \
      goto yyacceptlab;                                                      \
    case yyerr:                                                              \
      goto yyuser_error;                                                     \
    default:                                                                 \
      goto yybuglab;                                                         \
    }                                                                        \
  } while (YYID (0))


/*----------.
| yyparse.  |
`----------*/

int
yyparse (mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader)
{
  int yyresult;
  yyGLRStack yystack;
  yyGLRStack* const yystackp = &yystack;
  size_t yyposn;

  YYDPRINTF ((stderr, "Starting parse\n"));

  yychar = YYEMPTY;
  yylval = yyval_default;
  yylloc = yyloc_default;

/* User initialization code.  */
yylloc.initialize ();
/* Line 2277 of glr.c  */
#line 61 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
{
  /* Initialize the initial location. */
  yylloc.begin.filename = yylloc.end.filename = &(m_ScriptFile.name());
}
/* Line 2277 of glr.c  */
#line 4223 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"

  if (! yyinitGLRStack (yystackp, YYINITDEPTH))
    goto yyexhaustedlab;
  switch (YYSETJMP (yystack.yyexception_buffer))
    {
    case 0: break;
    case 1: goto yyabortlab;
    case 2: goto yyexhaustedlab;
    default: goto yybuglab;
    }
  yyglrShift (&yystack, 0, 0, 0, &yylval, &yylloc);
  yyposn = 0;

  while (YYID (yytrue))
    {
      /* For efficiency, we have two loops, the first of which is
         specialized to deterministic operation (single stack, no
         potential ambiguity).  */
      /* Standard mode */
      while (YYID (yytrue))
        {
          yyRuleNum yyrule;
          int yyaction;
          const short int* yyconflicts;

          yyStateNum yystate = yystack.yytops.yystates[0]->yylrState;
          YYDPRINTF ((stderr, "Entering state %d\n", yystate));
          if (yystate == YYFINAL)
            goto yyacceptlab;
          if (yyisDefaultedState (yystate))
            {
              yyrule = yydefaultAction (yystate);
              if (yyrule == 0)
                {
               yystack.yyerror_range[1].yystate.yyloc = yylloc;
                  yyreportSyntaxError (&yystack, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
                  goto yyuser_error;
                }
              YYCHK1 (yyglrReduce (&yystack, 0, yyrule, yytrue, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
            }
          else
            {
              yySymbol yytoken;
              if (yychar == YYEMPTY)
                {
                  YYDPRINTF ((stderr, "Reading a token: "));
                  yychar = YYLEX;
                }

              if (yychar <= YYEOF)
                {
                  yychar = yytoken = YYEOF;
                  YYDPRINTF ((stderr, "Now at end of input.\n"));
                }
              else
                {
                  yytoken = YYTRANSLATE (yychar);
                  YY_SYMBOL_PRINT ("Next token is", yytoken, &yylval, &yylloc);
                }

              yygetLRActions (yystate, yytoken, &yyaction, &yyconflicts);
              if (*yyconflicts != 0)
                break;
              if (yyisShiftAction (yyaction))
                {
                  YY_SYMBOL_PRINT ("Shifting", yytoken, &yylval, &yylloc);
                  yychar = YYEMPTY;
                  yyposn += 1;
                  yyglrShift (&yystack, 0, yyaction, yyposn, &yylval, &yylloc);
                  if (0 < yystack.yyerrState)
                    yystack.yyerrState -= 1;
                }
              else if (yyisErrorAction (yyaction))
                {
               yystack.yyerror_range[1].yystate.yyloc = yylloc;
                  yyreportSyntaxError (&yystack, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
                  goto yyuser_error;
                }
              else
                YYCHK1 (yyglrReduce (&yystack, 0, -yyaction, yytrue, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
            }
        }

      while (YYID (yytrue))
        {
          yySymbol yytoken_to_shift;
          size_t yys;

          for (yys = 0; yys < yystack.yytops.yysize; yys += 1)
            yystackp->yytops.yylookaheadNeeds[yys] = yychar != YYEMPTY;

          /* yyprocessOneStack returns one of three things:

              - An error flag.  If the caller is yyprocessOneStack, it
                immediately returns as well.  When the caller is finally
                yyparse, it jumps to an error label via YYCHK1.

              - yyok, but yyprocessOneStack has invoked yymarkStackDeleted
                (&yystack, yys), which sets the top state of yys to NULL.  Thus,
                yyparse's following invocation of yyremoveDeletes will remove
                the stack.

              - yyok, when ready to shift a token.

             Except in the first case, yyparse will invoke yyremoveDeletes and
             then shift the next token onto all remaining stacks.  This
             synchronization of the shift (that is, after all preceding
             reductions on all stacks) helps prevent double destructor calls
             on yylval in the event of memory exhaustion.  */

          for (yys = 0; yys < yystack.yytops.yysize; yys += 1)
            YYCHK1 (yyprocessOneStack (&yystack, yys, yyposn, &yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
          yyremoveDeletes (&yystack);
          if (yystack.yytops.yysize == 0)
            {
              yyundeleteLastStack (&yystack);
              if (yystack.yytops.yysize == 0)
                yyFail (&yystack, &yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_("syntax error"));
              YYCHK1 (yyresolveStack (&yystack, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
              YYDPRINTF ((stderr, "Returning to deterministic operation.\n"));
           yystack.yyerror_range[1].yystate.yyloc = yylloc;
              yyreportSyntaxError (&yystack, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
              goto yyuser_error;
            }

          /* If any yyglrShift call fails, it will fail after shifting.  Thus,
             a copy of yylval will already be on stack 0 in the event of a
             failure in the following loop.  Thus, yychar is set to YYEMPTY
             before the loop to make sure the user destructor for yylval isn't
             called twice.  */
          yytoken_to_shift = YYTRANSLATE (yychar);
          yychar = YYEMPTY;
          yyposn += 1;
          for (yys = 0; yys < yystack.yytops.yysize; yys += 1)
            {
              int yyaction;
              const short int* yyconflicts;
              yyStateNum yystate = yystack.yytops.yystates[yys]->yylrState;
              yygetLRActions (yystate, yytoken_to_shift, &yyaction,
                              &yyconflicts);
              /* Note that yyconflicts were handled by yyprocessOneStack.  */
              YYDPRINTF ((stderr, "On stack %lu, ", (unsigned long int) yys));
              YY_SYMBOL_PRINT ("shifting", yytoken_to_shift, &yylval, &yylloc);
              yyglrShift (&yystack, yys, yyaction, yyposn,
                          &yylval, &yylloc);
              YYDPRINTF ((stderr, "Stack %lu now in state #%d\n",
                          (unsigned long int) yys,
                          yystack.yytops.yystates[yys]->yylrState));
            }

          if (yystack.yytops.yysize == 1)
            {
              YYCHK1 (yyresolveStack (&yystack, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader));
              YYDPRINTF ((stderr, "Returning to deterministic operation.\n"));
              yycompressStack (&yystack);
              break;
            }
        }
      continue;
    yyuser_error:
      yyrecoverSyntaxError (&yystack, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
      yyposn = yystack.yytops.yystates[0]->yyposn;
    }

 yyacceptlab:
  yyresult = 0;
  goto yyreturn;

 yybuglab:
  YYASSERT (yyfalse);
  goto yyabortlab;

 yyabortlab:
  yyresult = 1;
  goto yyreturn;

 yyexhaustedlab:
  yyerror (&yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader, YY_("memory exhausted"));
  yyresult = 2;
  goto yyreturn;

 yyreturn:
  if (yychar != YYEMPTY)
    yydestruct ("Cleanup: discarding lookahead",
                YYTRANSLATE (yychar), &yylval, &yylloc, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);

  /* If the stack is well-formed, pop the stack until it is empty,
     destroying its entries as we go.  But free the stack regardless
     of whether it is well-formed.  */
  if (yystack.yyitems)
    {
      yyGLRState** yystates = yystack.yytops.yystates;
      if (yystates)
        {
          size_t yysize = yystack.yytops.yysize;
          size_t yyk;
          for (yyk = 0; yyk < yysize; yyk += 1)
            if (yystates[yyk])
              {
                while (yystates[yyk])
                  {
                    yyGLRState *yys = yystates[yyk];
                 yystack.yyerror_range[1].yystate.yyloc = yys->yyloc;
                  if (yys->yypred != YY_NULL)
                      yydestroyGLRState ("Cleanup: popping", yys, yyparser, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
                    yystates[yyk] = yys->yypred;
                    yystack.yynextFree -= 1;
                    yystack.yyspaceLeft += 1;
                  }
                break;
              }
        }
      yyfreeGLRStack (&yystack);
    }

  /* Make sure YYID is used.  */
  return YYID (yyresult);
}

/* DEBUGGING ONLY */
#if YYDEBUG
static void yypstack (yyGLRStack* yystackp, size_t yyk)
  __attribute__ ((__unused__));
static void yypdumpstack (yyGLRStack* yystackp) __attribute__ ((__unused__));

static void
yy_yypstack (yyGLRState* yys)
{
  if (yys->yypred)
    {
      yy_yypstack (yys->yypred);
      YYFPRINTF (stderr, " -> ");
    }
  YYFPRINTF (stderr, "%d@%lu", yys->yylrState,
             (unsigned long int) yys->yyposn);
}

static void
yypstates (yyGLRState* yyst)
{
  if (yyst == YY_NULL)
    YYFPRINTF (stderr, "<null>");
  else
    yy_yypstack (yyst);
  YYFPRINTF (stderr, "\n");
}

static void
yypstack (yyGLRStack* yystackp, size_t yyk)
{
  yypstates (yystackp->yytops.yystates[yyk]);
}

#define YYINDEX(YYX)                                                         \
    ((YYX) == YY_NULL ? -1 : (yyGLRStackItem*) (YYX) - yystackp->yyitems)


static void
yypdumpstack (yyGLRStack* yystackp)
{
  yyGLRStackItem* yyp;
  size_t yyi;
  for (yyp = yystackp->yyitems; yyp < yystackp->yynextFree; yyp += 1)
    {
      YYFPRINTF (stderr, "%3lu. ",
                 (unsigned long int) (yyp - yystackp->yyitems));
      if (*(yybool *) yyp)
        {
          YYFPRINTF (stderr, "Res: %d, LR State: %d, posn: %lu, pred: %ld",
                     yyp->yystate.yyresolved, yyp->yystate.yylrState,
                     (unsigned long int) yyp->yystate.yyposn,
                     (long int) YYINDEX (yyp->yystate.yypred));
          if (! yyp->yystate.yyresolved)
            YYFPRINTF (stderr, ", firstVal: %ld",
                       (long int) YYINDEX (yyp->yystate
                                             .yysemantics.yyfirstVal));
        }
      else
        {
          YYFPRINTF (stderr, "Option. rule: %d, state: %ld, next: %ld",
                     yyp->yyoption.yyrule - 1,
                     (long int) YYINDEX (yyp->yyoption.yystate),
                     (long int) YYINDEX (yyp->yyoption.yynext));
        }
      YYFPRINTF (stderr, "\n");
    }
  YYFPRINTF (stderr, "Tops:");
  for (yyi = 0; yyi < yystackp->yytops.yysize; yyi += 1)
    YYFPRINTF (stderr, "%lu: %ld; ", (unsigned long int) yyi,
               (long int) YYINDEX (yystackp->yytops.yystates[yyi]));
  YYFPRINTF (stderr, "\n");
}
#endif
/* Line 2575 of glr.c  */
#line 937 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"


void mcld::ScriptParser::error(const mcld::ScriptParser::location_type& pLoc,
                               const std::string &pMsg)
{
  position last = pLoc.end - 1;
  std::string filename = "NaN";
  if (last.filename != NULL)
    filename = *last.filename;

  mcld::error(diag::err_syntax_error)
    << filename << last.line << last.column << pMsg;
}

/* Line 2575 of glr.c  */
#line 4534 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"
/*------------------.
| Report an error.  |
`------------------*/

static void
yyerror (const mcld::ScriptParser::location_type *yylocationp, mcld::ScriptParser& yyparser, const class LinkerConfig& m_LDConfig, class ScriptFile& m_ScriptFile, class ScriptScanner& m_ScriptScanner, class ObjectReader& m_ObjectReader, class ArchiveReader& m_ArchiveReader, class DynObjReader& m_DynObjReader, class GroupReader& m_GroupReader, const char* msg)
{
  YYUSE (yyparser);
  YYUSE (m_LDConfig);
  YYUSE (m_ScriptFile);
  YYUSE (m_ScriptScanner);
  YYUSE (m_ObjectReader);
  YYUSE (m_ArchiveReader);
  YYUSE (m_DynObjReader);
  YYUSE (m_GroupReader);
  yyparser.error (*yylocationp, msg);
}


/* Line 2575 of glr.c  */
#line 48 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
namespace mcld {
/* Line 2575 of glr.c  */
#line 4558 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"
  /// Build a parser object.
  ScriptParser::ScriptParser (const class LinkerConfig& m_LDConfig_yyarg, class ScriptFile& m_ScriptFile_yyarg, class ScriptScanner& m_ScriptScanner_yyarg, class ObjectReader& m_ObjectReader_yyarg, class ArchiveReader& m_ArchiveReader_yyarg, class DynObjReader& m_DynObjReader_yyarg, class GroupReader& m_GroupReader_yyarg)
    :
#if YYDEBUG
      
      yycdebug_ (&std::cerr),
#endif
      m_LDConfig (m_LDConfig_yyarg),
      m_ScriptFile (m_ScriptFile_yyarg),
      m_ScriptScanner (m_ScriptScanner_yyarg),
      m_ObjectReader (m_ObjectReader_yyarg),
      m_ArchiveReader (m_ArchiveReader_yyarg),
      m_DynObjReader (m_DynObjReader_yyarg),
      m_GroupReader (m_GroupReader_yyarg)
  {
  }

  ScriptParser::~ScriptParser ()
  {
  }

  int
  ScriptParser::parse ()
  {
    return ::yyparse (*this, m_LDConfig, m_ScriptFile, m_ScriptScanner, m_ObjectReader, m_ArchiveReader, m_DynObjReader, m_GroupReader);
  }

#if YYDEBUG
  /*--------------------.
  | Print this symbol.  |
  `--------------------*/

  inline void
  ScriptParser::yy_symbol_value_print_ (int yytype,
                           const semantic_type* yyvaluep,
                           const location_type* yylocationp)
  {
    YYUSE (yylocationp);
    YYUSE (yyvaluep);
    std::ostream& yyoutput = debug_stream ();
    std::ostream& yyo = yyoutput;
    YYUSE (yyo);
    switch (yytype)
      {
          default:
          break;
      }
  }


  void
  ScriptParser::yy_symbol_print_ (int yytype,
                           const semantic_type* yyvaluep,
                           const location_type* yylocationp)
  {
    *yycdebug_ << (yytype < YYNTOKENS ? "token" : "nterm")
               << ' ' << yytname[yytype] << " ("
               << *yylocationp << ": ";
    yy_symbol_value_print_ (yytype, yyvaluep, yylocationp);
    *yycdebug_ << ')';
  }

  std::ostream&
  ScriptParser::debug_stream () const
  {
    return *yycdebug_;
  }

  void
  ScriptParser::set_debug_stream (std::ostream& o)
  {
    yycdebug_ = &o;
  }


  ScriptParser::debug_level_type
  ScriptParser::debug_level () const
  {
    return yydebug;
  }

  void
  ScriptParser::set_debug_level (debug_level_type l)
  {
    // Actually, it is yydebug which is really used.
    yydebug = l;
  }

#endif
/* Line 2575 of glr.c  */
#line 48 "frameworks/compile/mclinker/lib/Script/ScriptParser.yy"
} // mcld
/* Line 2575 of glr.c  */
#line 4652 "out/host/linux-x86/obj/STATIC_LIBRARIES/libmcldScript_intermediates/ScriptParser.cpp"