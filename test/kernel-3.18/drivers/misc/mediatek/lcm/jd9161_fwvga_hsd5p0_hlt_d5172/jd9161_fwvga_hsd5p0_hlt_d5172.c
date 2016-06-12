/*****************************************************************************
*  Copyright Statement:
*  --------------------
*  This software is protected by Copyright and the information contained
*  herein is confidential. The software may not be copied and the information
*  contained herein may not be used or disclosed except with the written
*  permission of MediaTek Inc. (C) 2008
*
*  BY OPENING THIS FILE, BUYER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
*  THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
*  RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO BUYER ON
*  AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
*  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
*  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
*  NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
*  SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
*  SUPPLIED WITH THE MEDIATEK SOFTWARE, AND BUYER AGREES TO LOOK ONLY TO SUCH
*  THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. MEDIATEK SHALL ALSO
*  NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE RELEASES MADE TO BUYER'S
*  SPECIFICATION OR TO CONFORM TO A PARTICULAR STANDARD OR OPEN FORUM.
*
*  BUYER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND CUMULATIVE
*  LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
*  AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
*  OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY BUYER TO
*  MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
*
*  THE TRANSACTION CONTEMPLATED HEREUNDER SHALL BE CONSTRUED IN ACCORDANCE
*  WITH THE LAWS OF THE STATE OF CALIFORNIA, USA, EXCLUDING ITS CONFLICT OF
*  LAWS PRINCIPLES.  ANY DISPUTES, CONTROVERSIES OR CLAIMS ARISING THEREOF AND
*  RELATED THERETO SHALL BE SETTLED BY ARBITRATION IN SAN FRANCISCO, CA, UNDER
*  THE RULES OF THE INTERNATIONAL CHAMBER OF COMMERCE (ICC).
*
*****************************************************************************/


#ifndef BUILD_LK
#include <linux/string.h>
#endif
#include "lcm_drv.h"

#ifdef BUILD_LK
	#include <platform/mt_gpio.h>
	#include <string.h>
#elif defined(BUILD_UBOOT)
	#include <asm/arch/mt_gpio.h>
#else
//	#include <mach/mt_gpio.h>
	#include <mt-plat/mt_gpio.h>
#endif


// ---------------------------------------------------------------------------
//  Local Constants
// ---------------------------------------------------------------------------

#define FRAME_WIDTH  										(480)
#define FRAME_HEIGHT 										(854)

#define REGFLAG_DELAY             							0xAB
#define REGFLAG_END_OF_TABLE      							0x100  // END OF REGISTERS MARKER

// ---------------------------------------------------------------------------
//  Local Variables
// ---------------------------------------------------------------------------

static LCM_UTIL_FUNCS lcm_util = {0};

#define SET_RESET_PIN(v)    (lcm_util.set_reset_pin((v)))

#define UDELAY(n) (lcm_util.udelay(n))
#define MDELAY(n) (lcm_util.mdelay(n))

#define LCM_ID       (0x9161)

// ---------------------------------------------------------------------------
//  Local Functions
// ---------------------------------------------------------------------------

#define dsi_set_cmdq_V2(cmd, count, ppara, force_update)	lcm_util.dsi_set_cmdq_V2(cmd, count, ppara, force_update)
#define dsi_set_cmdq(pdata, queue_size, force_update)		lcm_util.dsi_set_cmdq(pdata, queue_size, force_update)
#define wrtie_cmd(cmd)										lcm_util.dsi_write_cmd(cmd)
#define write_regs(addr, pdata, byte_nums)					lcm_util.dsi_write_regs(addr, pdata, byte_nums)
#define read_reg(cmd)											lcm_util.dsi_dcs_read_lcm_reg(cmd)
#define read_reg_v2(cmd, buffer, buffer_size)   				lcm_util.dsi_dcs_read_lcm_reg_v2(cmd, buffer, buffer_size)    
/*
#ifndef BUILD_LK
	extern atomic_t ESDCheck_byCPU;
#endif
*/
struct LCM_setting_table {
    unsigned char cmd;
    unsigned char count;
    unsigned char para_list[64];
};


static struct LCM_setting_table lcm_sleep_out_setting[] = {
    // Sleep Out
    {0x11, 1, {0x00}},
    {REGFLAG_DELAY, 120, {}},
    // Display ON
    {0x29, 1, {0x00}},
    {REGFLAG_DELAY, 10, {}},
    {REGFLAG_END_OF_TABLE, 0x00, {}}
};

static struct LCM_setting_table lcm_deep_sleep_mode_in_setting[] = {
    // Display off sequence
    {0x28, 1, {0x00}},
    {REGFLAG_DELAY, 10, {}},   
    // Sleep Mode On
    {0x10, 1, {0x00}},
    {REGFLAG_DELAY, 120, {}},
    {REGFLAG_END_OF_TABLE, 0x00, {}}
};

static struct LCM_setting_table lcm_initialization_setting[] = {

	
{0xBF,3,{0x91,0x61,0xF2}},
 
{0xB3,2,{0x00,0x7B}},
 
 
{0xB4,2,{0x00,0x7B}},
 
 
{0xB8,6,{0x00,0xBF,0x01,0x00,0xBF, 0x01}}, 
 
 
{0xBA,3,{0x3E, 0x23, 0x00}}, 
 
 
{0xC3,1,{0x04}}, 
 
 
{0xC4,2,{0x30,0x6A}},
 

{0xC7,9, {0x00,0x01,0x31,0x05, 0x65, 0x2B, 0x12, 0xA5, 0xA5}}, 
 
 
{0xC8,38,{0x7E, 0x70, 0x64, 0x5A, 0x59, 0x4A, 0x4F, 0x37, 0x4E, 0x4B, 0x48, 0x64, 0x4E,0x53, 0x43, 0x3F, 0x2C, 0x17, 0x08, 0x7E, 0x70, 0x64, 0x5A, 0x59, 0x4A, 0x4F, 0x37, 0x4E, 0x4B, 0x48, 0x64, 0x4E, 0x53, 0x43,  0x3F, 0x2C, 0x17,  0x08}}, 
 
 
{0xD4,16,{0x1E,0x1F,0x17,0x37,0x06,0x04,0x0A ,0x08 ,0x00 ,0x02, 0x1F, 0x1F, 0x1F, 0x1F, 0x1F, 0x1F}},
 
 
{0xD5,16,{0x1E ,0x1F, 0x17, 0x37, 0x07, 0x05, 0x0B, 0x09, 0x01, 0x03, 0x1F, 0x1F, 0x1F, 0x1F, 0x1F, 0x1F}},
 
 
{0xD6,16,{ 0x1F, 0x1E, 0x17, 0x17, 0x07, 0x09, 0x0B, 0x05, 0x03, 0x01, 0x1F, 0x1F, 0x1F, 0x1F, 0x1F,0x1F}},
 
 
{0xD7,16,{0x1F ,0x1E ,0x17 ,0x17 ,0x06 ,0x08 ,0x0A ,0x04 ,0x02 ,0x00 ,0x1F ,0x1F ,0x1F ,0x1F ,0x1F ,0x1F}},
 
 
{0xD8,20,{0x20 ,0x00 ,0x00 ,0x30 ,0x03 ,0x30 ,0x01 ,0x02 ,0x00 ,0x01 ,0x02 ,0x06 ,0x70 ,0x00 ,0x00 ,0x73 ,0x07 ,0x06 ,0x70 ,0x08}},
 
 
{0xD9,19,{ 0x00 ,0x0A ,0x0A ,0x80 ,0x00 ,0x00 ,0x06 ,0x7b ,0x00 ,0x80 ,0x00 ,0x33 ,0x6A ,0x1F ,0x00 ,0x00 ,0x00 ,0x03 ,0x7b}},

{0x35,0,{}},

{0xBE,1,{0x01}},

{0xCC,10,{0x34 ,0x20 ,0x38 ,0x60 ,0x11 ,0x91 ,0x00 ,0x40 ,0x00 ,0x00}},

{0xBE ,1,{0x00}},
           
{0x11,0,{}},
{REGFLAG_DELAY, 120, {}},
{0x29,0,{}},
{REGFLAG_DELAY, 20, {}},
{REGFLAG_END_OF_TABLE, 0x00, {}}

};

static void push_table(struct LCM_setting_table *table, unsigned int count, unsigned char force_update)
{
    unsigned int i;
    
    for(i = 0; i < count; i++) {
        unsigned cmd;
        cmd = table[i].cmd;
        
        switch (cmd) {
            case REGFLAG_DELAY :
                MDELAY(table[i].count);
                break;
            
            case REGFLAG_END_OF_TABLE :
                break;
            
            default:
                dsi_set_cmdq_V2(cmd, table[i].count, table[i].para_list, force_update);				
        }
    }
}


// ---------------------------------------------------------------------------
//  LCM Driver Implementations
// ---------------------------------------------------------------------------
static void lcm_set_util_funcs(const LCM_UTIL_FUNCS *util)
{
    memcpy(&lcm_util, util, sizeof(LCM_UTIL_FUNCS));
}

static void lcm_get_params(LCM_PARAMS *params)
{
  	
		memset(params, 0, sizeof(LCM_PARAMS));
	
		params->type   = LCM_TYPE_DSI;

		params->width  = FRAME_WIDTH;
		params->height = FRAME_HEIGHT;

		// enable tearing-free
		params->dbi.te_mode 			= LCM_DBI_TE_MODE_VSYNC_ONLY;
		params->dbi.te_edge_polarity		= LCM_POLARITY_RISING;

#if (LCM_DSI_CMD_MODE)
		params->dsi.mode   = CMD_MODE;
#else
		params->dsi.mode   = SYNC_PULSE_VDO_MODE;
#endif
	
		// DSI
		/* Command mode setting */
		params->dsi.LANE_NUM		    = LCM_TWO_LANE;
		//The following defined the fomat for data coming from LCD engine.
		params->dsi.data_format.color_order = LCM_COLOR_ORDER_RGB;
		params->dsi.data_format.trans_seq   = LCM_DSI_TRANS_SEQ_MSB_FIRST;
		params->dsi.data_format.padding     = LCM_DSI_PADDING_ON_LSB;
		params->dsi.data_format.format      = LCM_DSI_FORMAT_RGB888;

		params->dsi.word_count =480*3;
		params->dsi.vertical_active_line = 800;
		params->dsi.compatibility_for_nvk = 0;

		// Highly depends on LCD driver capability.
		// Not support in MT6573
		params->dsi.packet_size=256;

		// Video mode setting		
		params->dsi.intermediat_buffer_num = 2;

		params->dsi.PS=LCM_PACKED_PS_24BIT_RGB888;

		params->dsi.vertical_sync_active				= 2;
		params->dsi.vertical_backporch					= 12;//12,6
		params->dsi.vertical_frontporch					= 6;//5		
		params->dsi.vertical_active_line				= FRAME_HEIGHT; 
	
		params->dsi.horizontal_sync_active				= 8;//5
		params->dsi.horizontal_backporch				= 8;
		params->dsi.horizontal_frontporch				= 8;
		params->dsi.horizontal_active_pixel				= FRAME_WIDTH;
      
    params->dsi.PLL_CLOCK=159;


////////huihuang.shi
    params->dsi.esd_check_enable = 0;
////////
		params->dsi.customization_esd_check_enable = 1;
		params->dsi.lcm_esd_check_table[0].cmd			= 0x0a;
		params->dsi.lcm_esd_check_table[0].count		= 1;
		params->dsi.lcm_esd_check_table[0].para_list[0] = 0x9c;

}

/*
static void lcm_init(void)
{
	  lcm_util.set_gpio_out(GPIO_LCD_BIAS_ENP_PIN, GPIO_OUT_ONE);
	MDELAY(10);
    lcm_util.set_gpio_out(GPIO_LCD_BIAS_ENN_PIN, GPIO_OUT_ONE);
	MDELAY(10);
    SET_RESET_PIN(1);
    MDELAY(10);
    SET_RESET_PIN(0);
    MDELAY(10);
    SET_RESET_PIN(1);
    MDELAY(120);
push_table(lcm_initialization_setting, sizeof(lcm_initialization_setting) / sizeof(struct LCM_setting_table), 1);

}


static void lcm_suspend(void)
{
	push_table(lcm_deep_sleep_mode_in_setting, sizeof(lcm_deep_sleep_mode_in_setting) / sizeof(struct LCM_setting_table), 1);
	
	SET_RESET_PIN(1);
	MDELAY(1); 
	SET_RESET_PIN(0);
	MDELAY(10);
	SET_RESET_PIN(1);	
	MDELAY(120);
    lcm_util.set_gpio_out(GPIO_LCD_BIAS_ENP_PIN, GPIO_OUT_ZERO);
	MDELAY(10);
    lcm_util.set_gpio_out(GPIO_LCD_BIAS_ENN_PIN, GPIO_OUT_ZERO);
	MDELAY(10);

}
*/



static void lcm_init(void)
{

	#ifdef BUILD_LK
		printf("sym  lcm_init start\n");
	#else
		printk("sym  lcm_init  start\n");
	#endif

	SET_RESET_PIN(1);
	MDELAY(10);
	SET_RESET_PIN(0);
	MDELAY(20);
	SET_RESET_PIN(1);
	MDELAY(120);

	push_table(lcm_initialization_setting, sizeof(lcm_initialization_setting) / sizeof(struct LCM_setting_table), 1);
	#ifdef BUILD_LK
		printf("sym  lcm_init end\n");
	#else
		printk("sym  lcm_init end\n");
	#endif
}

static void lcm_suspend(void)
{	
	push_table(lcm_deep_sleep_mode_in_setting, sizeof(lcm_deep_sleep_mode_in_setting) / sizeof(struct LCM_setting_table), 1);
	
	SET_RESET_PIN(1);
	MDELAY(1); 
	SET_RESET_PIN(0);
	MDELAY(10);
	SET_RESET_PIN(1);	
	MDELAY(120);
}


static void lcm_resume(void)
{
	lcm_init();
}
         
#if 0 
static void lcm_update(unsigned int x, unsigned int y,
                       unsigned int width, unsigned int height)
{
    unsigned int x0 = x;
    unsigned int y0 = y;
    unsigned int x1 = x0 + width - 1;
    unsigned int y1 = y0 + height - 1;
    
    unsigned char x0_MSB = ((x0>>8)&0xFF);
    unsigned char x0_LSB = (x0&0xFF);
    unsigned char x1_MSB = ((x1>>8)&0xFF);
    unsigned char x1_LSB = (x1&0xFF);
    unsigned char y0_MSB = ((y0>>8)&0xFF);
    unsigned char y0_LSB = (y0&0xFF);
    unsigned char y1_MSB = ((y1>>8)&0xFF);
    unsigned char y1_LSB = (y1&0xFF);
    
    unsigned int data_array[16];
    

    data_array[0]= 0x00053902;
    data_array[1]= (x1_MSB<<24)|(x0_LSB<<16)|(x0_MSB<<8)|0x2a;
    data_array[2]= (x1_LSB);
    dsi_set_cmdq(data_array, 3, 1);

    data_array[0]= 0x00053902;
    data_array[1]= (y1_MSB<<24)|(y0_LSB<<16)|(y0_MSB<<8)|0x2b;
    data_array[2]= (y1_LSB);
    dsi_set_cmdq(data_array, 3, 1);
    
    data_array[0]= 0x002c3909;
    dsi_set_cmdq(data_array, 1, 0);
}
#endif
static unsigned int lcm_compare_id(void)
{
	unsigned int id1 = 0, id2 = 0,id3 = 0;
	unsigned char buffer[4];
	unsigned int data_array[16];
	
	SET_RESET_PIN(1);  //NOTE:should reset LCM firstly
	MDELAY(10);
	SET_RESET_PIN(0);
	MDELAY(10);
	SET_RESET_PIN(1);
	MDELAY(120);	
	

	
	data_array[0] = 0x00023700;// read id return two byte,version and id
	dsi_set_cmdq(data_array, 1, 1);
	MDELAY(10); 
	
	read_reg_v2(0xDF, buffer, 2);
	id1 = buffer[0]; //0x55
	id2 = buffer[1]; //0x17
	id3 = (id1<<8)|(id2);
	
#ifdef BUILD_LK
	printf("%s, LK nt35517 debug: nt35516 id1 = 0x%08x  id2 = 0x%08x  id3 = 0x%08x\n", __func__, id1,id2,id3);
#else
	printk("%s, LK nt35517 debug: nt35516 id1 = 0x%08x  id2 = 0x%08x  id3 = 0x%08x\n", __func__, id1,id2,id3);
#endif    

	return (LCM_ID == id3)?1:0;
   
}

static unsigned int lcm_ata_check(unsigned char *buffer)
{
	return 1;
	#ifndef BUILD_LK
		unsigned int  data_array[2];
		unsigned char buf[2];
		unsigned int id_high;
		unsigned int id_low;
		unsigned int id;
		
	/*atomic_set(&ESDCheck_byCPU,1);*/
		data_array[0] = 0x00023700;// read id return two byte,version and id
		dsi_set_cmdq(data_array, 1, 1);
		MDELAY(10); 
	
		read_reg_v2(0xDF, buf, 2);
		id_high = buf[0];
		id_low = buf[1];
		id = buf[0] << 8 | buf[1];
	/*atomic_set(&ESDCheck_byCPU,0);*/
		printk("1111111111111111111111=0x%x\n",id);
		return (LCM_ID == id)?1:0;
	#else
		return 0;
	#endif
}

// ---------------------------------------------------------------------------
//  Get LCM Driver Hooks
// ---------------------------------------------------------------------------
LCM_DRIVER jd9161_fwvga_hsd5p0_hlt_d5172_lcm_drv = 
{
    .name			= "jd9161_fwvga_hsd5p0_hlt_d5172",
    .set_util_funcs = lcm_set_util_funcs,
    .get_params     = lcm_get_params,
    .init           = lcm_init,
    .suspend        = lcm_suspend,
    .resume         = lcm_resume,
    .compare_id    = lcm_compare_id,
    .ata_check			=	lcm_ata_check,
};

