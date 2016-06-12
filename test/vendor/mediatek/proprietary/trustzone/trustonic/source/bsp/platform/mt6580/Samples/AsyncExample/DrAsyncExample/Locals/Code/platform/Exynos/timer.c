/*
 * Copyright (c) 2013 TRUSTONIC LIMITED
 * All rights reserved
 *
 * The present software is the confidential and proprietary information of
 * TRUSTONIC LIMITED. You shall not disclose the present software and shall
 * use it only in accordance with the terms of the license agreement you
 * entered into with TRUSTONIC LIMITED. This software may be subject to
 * export or import laws in certain countries.
 */

/**
 * HW Timer handling code
 *
 */
#include "drStd.h"
#include "DrApi/DrApi.h"
#include "timer.h"

#ifdef __EXYNOS_5250__
#define TIMER_DRV_IRQ           (76)
/* Addresses and offsets */
#define TIMER_BASE_PHYS         0x101E0000
#elif defined(__EXYNOS_4X12__)
#define TIMER_DRV_IRQ           (77)
#define TIMER_BASE_PHYS         0x10070000
#elif defined(__EXYNOS_5433__)
#define TIMER_BASE_PHYS         0x10590000
#define TIMER_DRV_IRQ           (418)
#elif defined(__EXYNOS_3475__)
#define TIMER_BASE_PHYS         0x101E0000
#define TIMER_DRV_IRQ           (125)
#endif

#define S3C2410_RTCREG(x)       (virt_timer_base + x)
#define S3C2410_INTP            S3C2410_RTCREG(0x30)
#define S3C2410_INTP_ALM        (1 << 1)
#define S3C2410_INTP_TIC        (1 << 0)

#define S3C2410_RTCCON          S3C2410_RTCREG(0x40)
#define S3C2410_RTCCON_RTCEN    (1<<0)
#define S3C2410_RTCCON_CLKSEL   (1<<1)
#define S3C2410_RTCCON_CNTSEL   (1<<2)
#define S3C2410_RTCCON_CLKRST   (1<<3)
#define S3C64XX_RTCCON_TICEN    (1<<8)

#define S3C64XX_RTCCON_TICMSK   (0xF<<7)
#define S3C64XX_RTCCON_TICSHT   (7)

#define S3C2410_TICNT           S3C2410_RTCREG(0x44)
#define S3C2410_CURTICNT        S3C2410_RTCREG(0x90)

#define S3C2410_RTCALM          S3C2410_RTCREG(0x50)
#define S3C2410_RTCALM_ALMEN    (1<<6)
#define S3C2410_RTCALM_YEAREN   (1<<5)
#define S3C2410_RTCALM_MONEN    (1<<4)
#define S3C2410_RTCALM_DAYEN    (1<<3)
#define S3C2410_RTCALM_HOUREN   (1<<2)
#define S3C2410_RTCALM_MINEN    (1<<1)
#define S3C2410_RTCALM_SECEN    (1<<0)

#define S3C2410_RTCALM_ALL \
  S3C2410_RTCALM_ALMEN | S3C2410_RTCALM_YEAREN | S3C2410_RTCALM_MONEN |\
  S3C2410_RTCALM_DAYEN | S3C2410_RTCALM_HOUREN | S3C2410_RTCALM_MINEN |\
  S3C2410_RTCALM_SECEN

#define S3C2410_ALMSEC          S3C2410_RTCREG(0x54)
#define S3C2410_ALMMIN          S3C2410_RTCREG(0x58)
#define S3C2410_ALMHOUR         S3C2410_RTCREG(0x5c)

#define S3C2410_ALMDATE         S3C2410_RTCREG(0x60)
#define S3C2410_ALMMON          S3C2410_RTCREG(0x64)
#define S3C2410_ALMYEAR         S3C2410_RTCREG(0x68)

#define S3C2410_RTCRST          S3C2410_RTCREG(0x6c)

#define S3C2410_RTCSEC          S3C2410_RTCREG(0x70)
#define S3C2410_RTCMIN          S3C2410_RTCREG(0x74)
#define S3C2410_RTCHOUR         S3C2410_RTCREG(0x78)
#define S3C2410_RTCDATE         S3C2410_RTCREG(0x7c)
#define S3C2410_RTCDAY          S3C2410_RTCREG(0x80)
#define S3C2410_RTCMON          S3C2410_RTCREG(0x84)
#define S3C2410_RTCYEAR         S3C2410_RTCREG(0x88)


uint32_t  virt_timer_base;

void plat_get_info(void);

uint32_t plat_getTimerBase(void)
{
   return  TIMER_BASE_PHYS;
}

uint32_t plat_getIrqNumber(void)
{
    return TIMER_DRV_IRQ;
}

void plat_timerInit(uint32_t virt_timer_base_ext)
{
    virt_timer_base=virt_timer_base_ext;

    word_t rtccon = 0x0;

    plat_get_info();

    rtccon = readl(S3C2410_RTCCON);
    if (rtccon&S3C64XX_RTCCON_TICEN){
    //RTC has already been setup by Linux Kernel
    //We read current setup and set S3C2410_TICNT that
    //TIMER_LOAD_VALUE=1000000 aprox. corresponds to 1s
        word_t n= TIMER_LOAD_VALUE>>(20-(15-((readl(S3C2410_RTCCON)>>4)&0xF)));
        drDbgPrintLnf("[Driver DrAsyncExample] n = %d", n);
        writel(n, S3C2410_TICNT);
    }
    else{
    //We perform full setup

        /* Reset RTC */
        writel(rtccon, S3C2410_RTCCON);
        /* Reset INTP */
        writel(0x0, S3C2410_INTP);

        /* Enable RTC */
        rtccon |= S3C2410_RTCCON_RTCEN;

        /* Enable tick count */
        rtccon |= S3C64XX_RTCCON_TICEN;

        /* Tick clock select 1100->8HZ */
        rtccon |= 0x9 << 4;

        writel(((TIMER_LOAD_VALUE/15625)-1), S3C2410_TICNT);

        writel(rtccon, S3C2410_RTCCON);
    }

    plat_get_info();
}

void plat_timerShutdown(void)
{
    plat_get_info();
    /* Reset RTC */
    writel(0x0, S3C2410_RTCCON);
}

void plat_clearInterrupt(void)
{
    word_t rtccon = 0x0;

    plat_get_info();

    /* reset current counter */

    rtccon = readl(S3C2410_RTCCON);
    rtccon&=(~S3C64XX_RTCCON_TICEN);
    writel(rtccon, S3C2410_RTCCON);
    rtccon|= S3C64XX_RTCCON_TICEN;
    writel(rtccon, S3C2410_RTCCON);


    /* Ack both flags*/
    writel(S3C2410_INTP_TIC | S3C2410_INTP_ALM, S3C2410_INTP);

    plat_get_info();

}

void plat_get_info(void)
{
    drDbgPrintLnf("[Driver DrAsyncExample] plat_clearInterrupt() current interrupt flag: 0x%08X", readl(S3C2410_INTP));
    drDbgPrintLnf("[Driver DrAsyncExample] plat_clearInterrupt() current tick: 0x%08X", readl(S3C2410_CURTICNT));
    drDbgPrintLnf("[Driver DrAsyncExample] plat_clearInterrupt() setup tick: 0x%08X", readl(S3C2410_TICNT));
    drDbgPrintLnf("[Driver DrAsyncExample] plat_timerInit() control 0x%08X",readl(S3C2410_RTCCON));
}

