#ifdef BUILD_LK
#else
#include <linux/string.h>
#endif

#include "lcm_drv.h"
#ifdef BUILD_LK
   // #include <platform/mt_gpio.h>
#elif defined(BUILD_UBOOT)
    #include <asm/arch/mt_gpio.h>
#else
  //  #include <mach/mt_gpio.h>
#endif
/*
#include <cust_adc.h>    	
#define MIN_VOLTAGE (1200)	//++++rgk bug-id:no modify by yangjuwei 20140401
#define MAX_VOLTAGE (2000)	//++++rgk bug-id:no modify by yangjuwei 20140401
*/
#define FRAME_WIDTH                                          (480)
#define FRAME_HEIGHT                                         (800)

#define REGFLAG_DELAY                                         0XFFE
#define REGFLAG_END_OF_TABLE                      			 0x1FF   // END OF REGISTERS MARKER

#define LCM_DSI_CMD_MODE                                    0

//static unsigned int lcm_esd_test = FALSE;      ///only for ESD test

static LCM_UTIL_FUNCS lcm_util = {0};

#define SET_RESET_PIN(v)    			(lcm_util.set_reset_pin((v)))

#define UDELAY(n) 				(lcm_util.udelay(n))
#define MDELAY(n) 				(lcm_util.mdelay(n))


// ---------------------------------------------------------------------------
//  Local Functions
// ---------------------------------------------------------------------------
#define dsi_set_cmdq_V2(cmd, count, ppara, force_update)		lcm_util.dsi_set_cmdq_V2(cmd, count, ppara, force_update)
#define dsi_set_cmdq(pdata, queue_size, force_update)			lcm_util.dsi_set_cmdq(pdata, queue_size, force_update)
#define wrtie_cmd(cmd)							lcm_util.dsi_write_cmd(cmd)
#define write_regs(addr, pdata, byte_nums)				lcm_util.dsi_write_regs(addr, pdata, byte_nums)
#define read_reg                                            lcm_util.dsi_read_reg()
#define read_reg_v2(cmd, buffer, buffer_size)				lcm_util.dsi_dcs_read_lcm_reg_v2(cmd, buffer, buffer_size)

extern int IMM_GetOneChannelValue(int dwChannel, int data[4], int* rawdata);

static struct LCM_setting_table {
    unsigned cmd;
    unsigned char count;
    unsigned char para_list[64];
};
#ifndef BUILD_LK
extern atomic_t ESDCheck_byCPU;
#endif

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


#if (LCM_DSI_CMD_MODE)
	params->dsi.mode   = CMD_MODE;
#else
	params->dsi.mode   = SYNC_PULSE_VDO_MODE; //SYNC_PULSE_VDO_MODE;//BURST_VDO_MODE; 
#endif
	
	// DSI
	/* Command mode setting */
	//1 Three lane or Four lane
		params->dsi.LANE_NUM				= LCM_TWO_LANE;
	//The following defined the fomat for data coming from LCD engine.
	params->dsi.data_format.format      = LCM_DSI_FORMAT_RGB888;

	// Video mode setting		
	params->dsi.PS=LCM_PACKED_PS_24BIT_RGB888;
		
		params->dsi.vertical_sync_active				= 6;// 3    2
		params->dsi.vertical_backporch					= 14;// 20   1
		params->dsi.vertical_frontporch					= 20; // 1  12
		params->dsi.vertical_active_line				= FRAME_HEIGHT; 

		params->dsi.horizontal_sync_active				= 10;// 50  2
		params->dsi.horizontal_backporch				= 60;//90
		params->dsi.horizontal_frontporch				= 60;//90
		params->dsi.horizontal_active_pixel				= FRAME_WIDTH;
		params->dsi.ssc_disable                         = 1;
		params->dsi.HS_TRAIL                             = 15;

		params->dsi.PLL_CLOCK=208;//208//270

		params->dsi.esd_check_enable = 1;
		params->dsi.customization_esd_check_enable = 1;
		params->dsi.lcm_esd_check_table[0].cmd			= 0x0a;
		params->dsi.lcm_esd_check_table[0].count		= 1;
		params->dsi.lcm_esd_check_table[0].para_list[0] = 0x9c;

}

//static unsigned int vcom = 0x30;
static void push_table(struct LCM_setting_table *table, unsigned int count, unsigned char force_update)
{
	unsigned int i;
	
	for(i = 0; i < count; i++) 
	{   
		unsigned cmd;
		cmd = table[i].cmd;

		switch (cmd)
		{
			case REGFLAG_DELAY :
				MDELAY(table[i].count);
				break;
			case REGFLAG_END_OF_TABLE :
				break;
			/*case 0xd9 :
				table[i].para_list[0] = vcom;
				vcom +=2;
				dsi_set_cmdq_V2(cmd, table[i].count, table[i].para_list, force_update);
				break;*/
			default:
				dsi_set_cmdq_V2(cmd, table[i].count, table[i].para_list, force_update);
		}
	}
}

static struct LCM_setting_table lcm_initialization_setting[] = {
 
{0xB9,3,{0xF1,0x08,0x01}},			
	     
{0xB1,7,{0x21,0x1E,0x1E,0x87,0x30,0x01,0x8B}},			
	      
{0xB2,1,{0x23}},			
	      
{0xB3,8,{0x00,0x00,0x06,0x06,0x20,0x20,0x30,0x30}},			
	     
{0xBA,0x11,{0x31,0x00,0x44,0x25,0x91,0x0A,0x00,0x00,0xC1,0x00,0x00,0x00,0x0D,0x02,0x1D,0xB9,0xEE}},			
	      
{0xE3,5,{0x04,0x04,0x01,0x01,0x00}}, 			
	      
{0xB4,1,{0x00}},				
	   
{0xB5,2,{0x05,0x05}}, 				
       
{0xB6,2,{0x8C,0x54}},			
	
{0xB8,2,{0x64,0x20}}, 				

{0xCC,1,{0x00}},			
	
{0xBC,1,{0x47}}, 		
	    
{0xE9,0x33,{0x00,0x00,0x07,0x00,0x00,0x81,0x89,0x12,0x31,0x23,0x23,0x08,0x81,0x80,0x23,0x00,0x00,0x10,0x00,0x00,0x00,0x0F,0x89,0x13,0x18,0x88,0x88,0x88,0x88,0x88,0x88,0x89,0x02,0x08,0x88,0x88,0x88,0x88,0x88,0x88,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00}},
	         
{0xEA,0x16,{0x90,0x00,0x00,0x00,0x88,0x20,0x09,0x88,0x88,0x88,0x88,0x88,0x88,0x88,0x31,0x19,0x88,0x88,0x88,0x88,0x88,0x88}},
	   
{0xE0,0x22,{0x00,0x00,0x00,0x18,0x1C,0x29,0x2A,0x3D,0x02,0x0D,0x13,0x18,0x1A,0x17,0x17,0x14,0x18,0x00,0x00,0x00,0x18,0x1C,0x29,0x2A,0x3D,0x02,0x0D,0x13,0x18,0x1A,0x17,0x17,0x14,0x18}},  
    
{0x11,0,{}},

{REGFLAG_DELAY,120,{}},
						
{0x29,0,{}},		
{REGFLAG_DELAY,10,{}}			

};


static void lcm_init(void)
{

	SET_RESET_PIN(1);
	MDELAY(20); 
	SET_RESET_PIN(0);
	MDELAY(20);
	SET_RESET_PIN(1);
	MDELAY(100);
    push_table(lcm_initialization_setting, sizeof(lcm_initialization_setting) / sizeof(struct LCM_setting_table), 1);

}

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
    dsi_set_cmdq(&data_array, 3, 1);
    
    data_array[0]= 0x00053902;
    data_array[1]= (y1_MSB<<24)|(y0_LSB<<16)|(y0_MSB<<8)|0x2b;
    data_array[2]= (y1_LSB);
    dsi_set_cmdq(&data_array, 3, 1);
    
    data_array[0]= 0x00290508;
    dsi_set_cmdq(&data_array, 1, 1);
    
    data_array[0]= 0x002c3909;
    dsi_set_cmdq(&data_array, 1, 0);


}

static struct LCM_setting_table lcm_deep_sleep_mode_in_setting[] = {
{0x28, 0, {}},
{REGFLAG_DELAY, 20, {}},
{0x10, 0, {}},
{REGFLAG_DELAY, 120, {}},
{REGFLAG_END_OF_TABLE, 0x00, {}}
};

static void lcm_suspend(void)
{
	push_table(lcm_deep_sleep_mode_in_setting, sizeof(lcm_deep_sleep_mode_in_setting) / sizeof(struct LCM_setting_table), 1);
 SET_RESET_PIN(1);
	MDELAY(20); 
	SET_RESET_PIN(0);
	MDELAY(20);
	SET_RESET_PIN(1);
	MDELAY(100);
}

static void lcm_resume(void)
{
    lcm_init();
}

/*
static unsigned int lcm_compare_id(void)
{
	
	int array[4];
	char buffer[3];
	char id_high=0;
	char id_low=0;
	int id=0;

	SET_RESET_PIN(1);
	MDELAY(20); 
	SET_RESET_PIN(0);
	MDELAY(20);
	SET_RESET_PIN(1);
	MDELAY(100);

	//{0x39, 0xFF, 5, { 0xFF,0x98,0x06,0x04,0x01}}, // Change to Page 1 CMD
	array[0] = 0x00043902;
	array[1] = 0x018198FF;
	dsi_set_cmdq(array, 2, 1);

	array[0] = 0x00013700;
	dsi_set_cmdq(array, 1, 1);
	read_reg_v2(0x00, &buffer[0], 1);  //0x98

	id = buffer[0];

	return (0x98 == id)?1:0;

}

// zhoulidong  add for lcm detect (start)
static unsigned int rgk_lcm_compare_id(void)
{
    int data[4] = {0,0,0,0};
    int res = 0;
    int rawdata = 0;
    int lcm_vol = 0;

#ifdef AUXADC_LCM_VOLTAGE_CHANNEL
    res = IMM_GetOneChannelValue(AUXADC_LCM_VOLTAGE_CHANNEL,data,&rawdata);
    if(res < 0)
    { 
	#ifdef BUILD_LK
	printf("[adc_uboot]: jinmin get data error\n");
	#endif
	return 0;
		   
    }
#endif

    lcm_vol = data[0]*1000+data[1]*10;

    #ifdef BUILD_LK
    printf("[adc_uboot]: jinmin lcm_vol= %d\n",lcm_vol);
    #endif
	
    if (lcm_vol>=MIN_VOLTAGE && lcm_vol <= MAX_VOLTAGE)// && lcm_compare_id())
    {
	return 1;
    }

    return 0;

}

#if 1//temp solution start
//add by yangjuwei 
static unsigned int lcm_ata_check(unsigned char *buffer)
{
#ifndef BUILD_LK
			int array[4];
			char buf[3];
			char id_high=0;
			char id_low=0;
			int id=0;
		
			//{0x39, 0xFF, 5, { 0xFF,0x98,0x06,0x04,0x01}}, // Change to Page 1 CMD
			array[0] = 0x00043902;
			array[1] = 0x018198FF;
			dsi_set_cmdq(array, 2, 1);
		
			array[0] = 0x00013700;
			dsi_set_cmdq(array, 1, 1);
			atomic_set(&ESDCheck_byCPU,1);
			read_reg_v2(0x01, &buf[0], 1);  //0x81
			atomic_set(&ESDCheck_byCPU,0);
			id = buf[0];
		
			return (0x81 == id)?1:0;

#else
	return 0;
#endif
}
#else
static unsigned int lcm_ata_check(unsigned char *buffer)
{
    int data[4] = {0,0,0,0};
    int res = 0;
    int rawdata = 0;
    int lcm_vol = 0;

#ifdef AUXADC_LCM_VOLTAGE_CHANNEL
    res = IMM_GetOneChannelValue(AUXADC_LCM_VOLTAGE_CHANNEL,data,&rawdata);
    if(res < 0)
    {
	    return 0;		   
    }
#endif

    lcm_vol = data[0]*1000+data[1]*10;
	
    if (lcm_vol>=MIN_VOLTAGE &&lcm_vol <= MAX_VOLTAGE)
    {
	    return 1;
    }
    return 0;
}
#endif//temp solution end
*/
LCM_DRIVER ili9806e_dsi_vdo_dj_wvga_d2030_lcm_drv = 
{
	.name			= "ili9806e_dsi_vdo_dj_wvga_d2030",
	.set_util_funcs		= lcm_set_util_funcs,
	.get_params		= lcm_get_params,
	.init			= lcm_init,
    .suspend        = lcm_suspend,
    .resume         = lcm_resume,
   // .compare_id     = rgk_lcm_compare_id,
	//.ata_check		= lcm_ata_check,
#if (LCM_DSI_CMD_MODE)
	//.set_backlight	= lcm_setbacklight,
	//.update		= lcm_update,
#endif
};

