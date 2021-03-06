/* MXC622X motion sensor driver
 *
 *
 *
 * This software is licensed under the terms of the GNU General Public
 * License version 2, as published by the Free Software Foundation, and
 * may be copied, distributed, and modified under those terms.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 */
#include "cust_acc.h"
#include "accel.h"
#include "mxc622x.h"

#define SW_SIMULATE_I2C	0			//if use GPIO simulate I2C, define 1, 
									// if use hardware I2C, define 0

/*----------------------------------------------------------------------------*/
#define I2C_DRIVERID_MXC622X 345
/*----------------------------------------------------------------------------*/
#define DEBUG 1
/*----------------------------------------------------------------------------*/
//#define CONFIG_MXC622X_LOWPASS   /*apply low pass filter on output*/       //lyon 
/*----------------------------------------------------------------------------*/
#define MXC622X_AXIS_X          0
#define MXC622X_AXIS_Y          1
#define MXC622X_AXIS_Z          2
#define MXC622X_AXES_NUM        3
#define MXC622X_DATA_LEN        2
#define MXC622X_DEV_NAME        "MXC622X"
#define FTM_CUST_ACC "/data/mxc622x"

#define MXC622X_INIT_SUCC	(0)
#define MXC622X_INIT_FAIL	(-1)

#define ANDROID6
/*----------------------------------------------------------------------------*/

/*the adapter id will be available in customization*/
//static unsigned short mxc622x_force[] = {0x00, MXC622X_I2C_SLAVE_ADDR, I2C_CLIENT_END, I2C_CLIENT_END};
//static const unsigned short *const mxc622x_forces[] = { mxc622x_force, NULL };
//static struct i2c_client_address_data mxc622x_addr_data = { .forces = mxc622x_forces,};

//modify
static const struct i2c_device_id mxc622x_i2c_id[] = {{MXC622X_DEV_NAME,0},{}}; /* ????3?��?D?��?BOARD_INFO��??��??2??���̡¨�?probeo����y */
//static struct i2c_board_info __initdata i2c_mxc622x={ I2C_BOARD_INFO("MXC622X", (MXC622X_I2C_SLAVE_ADDR>>1))};


/*----------------------------------------------------------------------------*/
static int mxc622x_i2c_probe(struct i2c_client *client, const struct i2c_device_id *id); 
static int mxc622x_i2c_remove(struct i2c_client *client);

//static int mxc622x_i2c_detect(struct i2c_client *client, int kind, struct i2c_board_info *info);
static int mxc622x_suspend(struct i2c_client *client, pm_message_t msg) ;
static int mxc622x_resume(struct i2c_client *client);

#ifdef ANDROID6
static int mxc622x_local_init(void);
static int mxc622x_remove(void);

static struct acc_init_info  mxc622x_init_info = {
	.name   = MXC622X_DEV_NAME,
	.init   = mxc622x_local_init,
	.uninit = mxc622x_remove,
	};
#endif

#ifdef CONFIG_OF
static const struct of_device_id accel_of_match[] = {
	{.compatible = "mediatek,gsensor"},
	{},
};
#endif

static struct acc_hw accel_cust;
static struct acc_hw *hw = &accel_cust;

int	cali_offset[3] = {0};
static int	s_nInitFlag = MXC622X_INIT_FAIL;
/*----------------------------------------------------------------------------*/
typedef enum {
    ADX_TRC_FILTER  = 0x01,
    ADX_TRC_RAWDATA = 0x02,
    ADX_TRC_IOCTL   = 0x04,
    ADX_TRC_CALI	= 0X08,
    ADX_TRC_INFO	= 0X10,
} ADX_TRC;
/*----------------------------------------------------------------------------*/
struct scale_factor{
    u8  whole;
    u8  fraction;
};
/*----------------------------------------------------------------------------*/
struct data_resolution {
    struct scale_factor scalefactor;
    int                 sensitivity;
};
/*----------------------------------------------------------------------------*/
#define C_MAX_FIR_LENGTH (32)
/*----------------------------------------------------------------------------*/
struct data_filter {
    s16 raw[C_MAX_FIR_LENGTH][MXC622X_AXES_NUM];
    int sum[MXC622X_AXES_NUM];
    int num;
    int idx;
};
/*----------------------------------------------------------------------------*/
struct mxc622x_i2c_data {
    struct i2c_client *client;
    struct acc_hw *hw;
    struct hwmsen_convert   cvt;
    
    /*misc*/
    struct data_resolution *reso;
    atomic_t                trace;
    atomic_t                suspend;
    atomic_t                selftest;
	atomic_t				filter;
    s16                     cali_sw[MXC622X_AXES_NUM+1];

    /*data*/
    int                      offset[MXC622X_AXES_NUM+1];  /*+1: for 4-byte alignment*/
    char                     data[MXC622X_AXES_NUM+1];

#if defined(CONFIG_MXC622X_LOWPASS)
    atomic_t                firlen;
    atomic_t                fir_en;
    struct data_filter      fir;
#endif 
    /*early suspend*/
#if defined(CONFIG_HAS_EARLYSUSPEND)
    struct early_suspend    early_drv;
#endif     
};

/*----------------------------------------------------------------------------*/
static struct i2c_driver mxc622x_i2c_driver = {
	 .driver = {
		 .name			 = MXC622X_DEV_NAME,
	#ifdef CONFIG_OF
		 .of_match_table = accel_of_match,
	#endif
	 },
	 .probe 			 = mxc622x_i2c_probe,
	 .remove			 = mxc622x_i2c_remove,
#if !defined(CONFIG_HAS_EARLYSUSPEND)    
    .suspend            = mxc622x_suspend,
    .resume             = mxc622x_resume,
#endif
	.id_table = mxc622x_i2c_id,
};

/*----------------------------------------------------------------------------*/
static struct i2c_client *mxc622x_i2c_client = NULL;
//static struct platform_driver mxc622x_gsensor_driver;
static struct mxc622x_i2c_data *obj_i2c_data = NULL;
static bool sensor_power = false;
static struct GSENSOR_VECTOR3D gsensor_gain;
//static char selftestRes[8]= {0}; 


/*----------------------------------------------------------------------------*/
#define TAG		"accel_cali"
#define GSE_TAG                  "[Gsensor] "
#define GSE_FUN(f)               printk(KERN_INFO GSE_TAG"%s\n", __FUNCTION__)
#define GSE_ERR(fmt, args...)    printk(KERN_ERR GSE_TAG"%s %d : "fmt, __FUNCTION__, __LINE__, ##args)
#define GSE_LOG(fmt, args...)    printk(KERN_INFO GSE_TAG fmt, ##args)
/*----------------------------------------------------------------------------*/
static struct data_resolution mxc622x_data_resolution[] = {
 /*8 combination by {FULL_RES,RANGE}*/
    {{15, 6},  64},   /*+/-8g  in 10-bit resolution: 15.6 mg/LSB*/
};
/*----------------------------------------------------------------------------*/
static struct data_resolution mxc622x_offset_resolution = {{15, 6}, 64};
static void MXC622X_power(struct acc_hw *hw, unsigned int on) 
{
#if 0
	static unsigned int power_on = 0;

	if(hw->power_id != POWER_NONE_MACRO)		// have externel LDO
	{        
		GSE_LOG("power %s\n", on ? "on" : "off");
		if(power_on == on)	// power status not change
		{
			GSE_LOG("ignore power control: %d\n", on);
		}
		else if(on)	// power on
		{
			if(!hwPowerOn(hw->power_id, hw->power_vol, "MXC622X"))
			{
				GSE_ERR("power on fails!!\n");
			}
		}
		else	// power off
		{
			if (!hwPowerDown(hw->power_id, "MXC622X"))
			{
				GSE_ERR("power off fail!!\n");
			}			  
		}
	}
	power_on = on;    
#endif
}

/*----------------------------------------------------------------------------*/
static int MXC622X_SetDataResolution(struct mxc622x_i2c_data *obj)
{
	//int err;
	//u8  dat, reso;
#if 0 
	if((err = hwmsen_read_byte(obj->client, MXC622X_REG_DATA_FORMAT, &dat)))
	{
		GSE_ERR("write data format fail!!\n");
		return err;
	}

	/*the data_reso is combined by 3 bits: {FULL_RES, DATA_RANGE}*/
	reso  = (dat & MXC622X_FULL_RES) ? (0x04) : (0x00);
	reso |= (dat & MXC622X_RANGE_16G); 

	if(reso < sizeof(mxc622x_data_resolution)/sizeof(mxc622x_data_resolution[0]))
	{        
		obj->reso = &mxc622x_data_resolution[reso];
		return 0;
	}
	else
	{
		return -EINVAL;
	}
#endif 
	obj->reso = &mxc622x_data_resolution[2];
	return 0;

}
/*----------------------------------------------------------------------------*/
static int MXC622X_ReadData(struct i2c_client *client, char data[MXC622X_AXES_NUM])
{
	char addr = MXC622X_REG_DATAX0;
	char buf[MXC622X_DATA_LEN] = {0};
	int err = 0;
//	struct mxc622x_i2c_data *priv = i2c_get_clientdata(client); 

	if(NULL == client)
	{
		return -EINVAL;
	}

	err = hwmsen_read_block(client, addr, buf, MXC622X_DATA_LEN);
	if(err)
	{
		GSE_ERR("error: %d\n", err);
	}
	else
	{
		data[MXC622X_AXIS_X] = (signed char)(buf[0]);
		data[MXC622X_AXIS_Y] = (signed char)(buf[1]);
		data[MXC622X_AXIS_Z] = 0;
		//printk("[+++++++ ACC MXC622X +++++++++++] %s: data[0] = %d, data[1] = %d\n", __FUNCTION__, buf[0], buf[1]);
	}
	
	return err;
}
/*----------------------------------------------------------------------------*/

static int MXC622X_ReadOffset(struct i2c_client *client,  int data[MXC622X_AXES_NUM])
{    
	int err = 0;
//	int acc[MXC622X_AXES_NUM] = {0};
	
	struct mxc622x_i2c_data *obj =  obj_i2c_data;

	if(NULL == client)
	{
		return -EINVAL;
	}

	if(MXC622X_ReadData(client, obj->data))
	{        
		err = -3;
	}
	else
	{
		data[obj->cvt.map[MXC622X_AXIS_X]] = obj->cvt.sign[MXC622X_AXIS_X]*obj->data[MXC622X_AXIS_X];
		data[obj->cvt.map[MXC622X_AXIS_Y]] = obj->cvt.sign[MXC622X_AXIS_Y]*obj->data[MXC622X_AXIS_Y];
		data[obj->cvt.map[MXC622X_AXIS_Z]] = obj->cvt.sign[MXC622X_AXIS_Z]*obj->data[MXC622X_AXIS_Z];

		//Out put the mg
		data[MXC622X_AXIS_X] = (signed char)data[MXC622X_AXIS_X] * GRAVITY_EARTH_1000 / 64;
		data[MXC622X_AXIS_Y] = (signed char)data[MXC622X_AXIS_Y] * GRAVITY_EARTH_1000 / 64;
		data[MXC622X_AXIS_Z] = 32 * GRAVITY_EARTH_1000 / 64;

		//sprintf(data, "%04x %04x %04x", acc[MXC622X_AXIS_X], acc[MXC622X_AXIS_Y], acc[MXC622X_AXIS_Z]);
	//		printk("%s : data = (%d,%d,%d), acc = (%d,%d,%d)\n", __func__, data[0], data[1], data[2],
	//			acc[0], acc[1], acc[2]);
	
		printk("%s : data = (%d,%d,%d)\n", __func__, data[0], data[1], data[2]);
	}

	return err;
}

/*----------------------------------------------------------------------------*/
static int MXC622X_ResetCalibration(struct i2c_client *client)
{
//	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
//	s8 ofs[MXC622X_AXES_NUM] = {0x00, 0x00, 0x00};
	int err = MXC622X_SUCCESS;
/*
	if((err = hwmsen_write_block(client, MXC622X_REG_OFSX, ofs, MXC622X_AXES_NUM)))
	{
		GSE_ERR("error: %d\n", err);
	}

	memset(obj->cali_sw, 0x00, sizeof(obj->cali_sw));
*/
	return err;    
}
/*----------------------------------------------------------------------------*/

static int MXC622X_ReadCalibration(struct i2c_client *client, int dat[MXC622X_AXES_NUM])
{
    //struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
    int err =  MXC622X_SUCCESS;
	int i;
	int xyz[MXC622X_AXES_NUM];
	int buf[MXC622X_AXES_NUM] = {0};
    //int mul;

	for(i = 0; i < 20; i++)
	{
		err = MXC622X_ReadOffset(client, xyz);
		if (err) {
			GSE_ERR("read offset fail, %d\n", err);
			return err;
		}    
		buf[0] += (int)xyz[0];
		buf[1] += (int)xyz[1];
	}    
	dat[0] = buf[0] / 20;
	dat[1] = buf[1] / 20;

	printk(TAG "%s cali dat = (%d,%d)\n", __func__, cali_offset[0], cali_offset[1]);
    return err;
}

/*----------------------------------------------------------------------------*/
#if 0
static int MXC622X_ReadCalibrationEx(struct i2c_client *client, int act[MXC622X_AXES_NUM], int raw[MXC622X_AXES_NUM])
{  
	/*raw: the raw calibration data; act: the actual calibration data*/
	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
	int err;
//	int mul;

	if((err = MXC622X_ReadOffset(client, obj->offset)))
	{
		GSE_ERR("read offset fail, %d\n", err);
		return err;
	}    
/*
	mul = obj->reso->sensitivity/mxc622x_offset_resolution.sensitivity;
	raw[MXC622X_AXIS_X] = obj->offset[MXC622X_AXIS_X]*mul + obj->cali_sw[MXC622X_AXIS_X];
	raw[MXC622X_AXIS_Y] = obj->offset[MXC622X_AXIS_Y]*mul + obj->cali_sw[MXC622X_AXIS_Y];
	raw[MXC622X_AXIS_Z] = obj->offset[MXC622X_AXIS_Z]*mul + obj->cali_sw[MXC622X_AXIS_Z];

	act[obj->cvt.map[MXC622X_AXIS_X]] = obj->cvt.sign[MXC622X_AXIS_X]*raw[MXC622X_AXIS_X];
	act[obj->cvt.map[MXC622X_AXIS_Y]] = obj->cvt.sign[MXC622X_AXIS_Y]*raw[MXC622X_AXIS_Y];
	act[obj->cvt.map[MXC622X_AXIS_Z]] = obj->cvt.sign[MXC622X_AXIS_Z]*raw[MXC622X_AXIS_Z];                        
*/                       
	return 0;
}
#endif
/*----------------------------------------------------------------------------*/

static int MXC622X_WriteCalibration(struct i2c_client *client, int dat[MXC622X_AXES_NUM])
{
	//struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
	int err = MXC622X_SUCCESS;
#if 0	
        int cali[MXC622X_AXES_NUM], raw[MXC622X_AXES_NUM];
	int lsb = mxc622x_offset_resolution.sensitivity;
	int divisor = obj->reso->sensitivity/lsb;

	if((err = MXC622X_ReadCalibrationEx(client, cali, raw)))	/*offset will be updated in obj->offset*/
	{ 
		GSE_ERR("read offset fail, %d\n", err);
		return err;
	}


	/*calculate the real offset expected by caller*/
	cali[MXC622X_AXIS_X] += dat[MXC622X_AXIS_X];
	cali[MXC622X_AXIS_Y] += dat[MXC622X_AXIS_Y];
	cali[MXC622X_AXIS_Z] += dat[MXC622X_AXIS_Z];

	GSE_LOG("UPDATE: (%+3d %+3d %+3d)\n", 
		dat[MXC622X_AXIS_X], dat[MXC622X_AXIS_Y], dat[MXC622X_AXIS_Z]);

	obj->offset[MXC622X_AXIS_X] = (s8)(obj->cvt.sign[MXC622X_AXIS_X]*(cali[obj->cvt.map[MXC622X_AXIS_X]])/(divisor));
	obj->offset[MXC622X_AXIS_Y] = (s8)(obj->cvt.sign[MXC622X_AXIS_Y]*(cali[obj->cvt.map[MXC622X_AXIS_Y]])/(divisor));
	obj->offset[MXC622X_AXIS_Z] = (s8)(obj->cvt.sign[MXC622X_AXIS_Z]*(cali[obj->cvt.map[MXC622X_AXIS_Z]])/(divisor));

	/*convert software calibration using standard calibration*/
	obj->cali_sw[MXC622X_AXIS_X] = obj->cvt.sign[MXC622X_AXIS_X]*(cali[obj->cvt.map[MXC622X_AXIS_X]])%(divisor);
	obj->cali_sw[MXC622X_AXIS_Y] = obj->cvt.sign[MXC622X_AXIS_Y]*(cali[obj->cvt.map[MXC622X_AXIS_Y]])%(divisor);
	obj->cali_sw[MXC622X_AXIS_Z] = obj->cvt.sign[MXC622X_AXIS_Z]*(cali[obj->cvt.map[MXC622X_AXIS_Z]])%(divisor);

	GSE_LOG("NEWOFF: (%+3d %+3d %+3d): (%+3d %+3d %+3d) / (%+3d %+3d %+3d)\n", 
		obj->offset[MXC622X_AXIS_X]*divisor + obj->cali_sw[MXC622X_AXIS_X], 
		obj->offset[MXC622X_AXIS_Y]*divisor + obj->cali_sw[MXC622X_AXIS_Y], 
		obj->offset[MXC622X_AXIS_Z]*divisor + obj->cali_sw[MXC622X_AXIS_Z], 
		obj->offset[MXC622X_AXIS_X], obj->offset[MXC622X_AXIS_Y], obj->offset[MXC622X_AXIS_Z],
		obj->cali_sw[MXC622X_AXIS_X], obj->cali_sw[MXC622X_AXIS_Y], obj->cali_sw[MXC622X_AXIS_Z]);

	if((err = hwmsen_write_block(obj->client, MXC622X_REG_OFSX, obj->offset, MXC622X_AXES_NUM)))
	{
		GSE_ERR("write offset fail: %d\n", err);
		return err;
	}
#endif 
	return err;
}

/*----------------------------------------------------------------------------*/
static int MXC622X_CheckDeviceID(struct i2c_client *client)
{
	u8 databuf[10];    
	int res = 0;

	memset(databuf, 0, sizeof(u8)*10);    
	databuf[0] = MXC622X_REG_DEVID;    

	//client->addr >>= 1;
	//client->ext_flag = (client->ext_flag) & (~I2C_DMA_FLAG);
	printk("%s, i2c timing = %d, addr = %x\n", __func__, client->timing, client->addr);
	res = i2c_master_send(client, databuf, 0x01);
	if(res <= 0)
	{
		printk("%s, i2c err, addr = %x, flag = %d\n", __func__, client->addr, client->flags);
		goto exit_MXC622X_CheckDeviceID;
	}
	
	udelay(500);

	databuf[0] = 0x0;        
	res = i2c_master_recv(client, databuf, 0x01);
	if(res <= 0)
	{
		goto exit_MXC622X_CheckDeviceID;
	}
	
      printk("tangzibo_CheckDeviceID[130] %x\n ", databuf[0]);
         databuf[0]= (databuf[0]&0x3f);
printk("tangzibo_CheckDeviceID[131] %x\n ", databuf[0]);
	if(databuf[0]!=MXC622X_FIXED_DEVID)
	{
		return MXC622X_ERR_IDENTIFICATION;
	}

	exit_MXC622X_CheckDeviceID:
	if (res <= 0)
	{
		return MXC622X_ERR_I2C;
	}
	//lyon
	return MXC622X_SUCCESS;
}
/*----------------------------------------------------------------------------*/
static int MXC622X_SetPowerMode(struct i2c_client *client, bool enable)
{
	char databuf[2]={0};    
	int res = 0;
	char addr = MXC622X_REG_CTRL;

	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);

	
	if(enable == sensor_power)
	{
		GSE_LOG("Sensor power status is newest!\n");
		return MXC622X_SUCCESS;
	}

	databuf[1] &= ~MXC622X_MEASURE_MODE;
	
	if(enable == true)
	{
		databuf[1] |= MXC622X_MEASURE_MODE;
	}
	else
	{
		databuf[1] |= MXC622X_CTRL_PWRDN;
	}
	
	databuf[0] = addr;


	res = i2c_master_send(client, databuf, 0x2);

	if(res <= 0)
	{
		GSE_LOG("set power mode failed!\n");
		return MXC622X_ERR_I2C;
	}
	else if(atomic_read(&obj->trace) & ADX_TRC_INFO)
	{
		GSE_LOG("set power mode ok %d!\n", databuf[1]);
	}

	sensor_power = enable;
        mdelay(20);
	
	return MXC622X_SUCCESS;    
}
/*----------------------------------------------------------------------------*/
static int MXC622X_SetDataFormat(struct i2c_client *client, u8 dataformat)
{
	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
	//u8 databuf[10];    
	//int res = 0;
/*
	memset(databuf, 0, sizeof(u8)*10);    
	databuf[0] = MXC622X_REG_DATA_FORMAT;    
	databuf[1] = dataformat;

	res = i2c_master_send(client, databuf, 0x2);

	if(res <= 0)
	{
		return MXC622X_ERR_I2C;
	}
	
*/
	return MXC622X_SetDataResolution(obj);    
}
/*----------------------------------------------------------------------------*/
static int MXC622X_SetBWRate(struct i2c_client *client, u8 bwrate)
{
	//u8 databuf[10];    
	//int res = 0;
/*
	memset(databuf, 0, sizeof(u8)*10);    
	databuf[0] = MXC622X_REG_BW_RATE;    
	databuf[1] = bwrate;

	res = i2c_master_send(client, databuf, 0x2);

	if(res <= 0)
	{
		return MXC622X_ERR_I2C;
	}
*/
//lyon
	return MXC622X_SUCCESS;    
}
/*----------------------------------------------------------------------------*/
static int MXC622X_SetIntEnable(struct i2c_client *client, u8 intenable)
{
	//u8 databuf[10];    
	//int res = 0;
/*
	memset(databuf, 0, sizeof(u8)*10);    
	databuf[0] = MXC622X_REG_INT_ENABLE;    
	databuf[1] = intenable;

	res = i2c_master_send(client, databuf, 0x2);

	if(res <= 0)
	{
		return MXC622X_ERR_I2C;
	}
*/	
	return MXC622X_SUCCESS;    
}
/*----------------------------------------------------------------------------*/

static int mxc622x_init_client(struct i2c_client *client, int reset_cali)
{
	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
	int res = 0;


        res = MXC622X_CheckDeviceID(client); 
	if(res != MXC622X_SUCCESS)
	{
		return res;
	}	
	printk("MXC622X_CheckDeviceID ok \n");
	
	res = MXC622X_SetBWRate(client, MXC622X_BW_100HZ);
	if(res != MXC622X_SUCCESS ) 
	{
		return res;
	}
	printk("MXC622X_SetBWRate OK!\n");

	
	res = MXC622X_SetDataFormat(client, MXC622X_RANGE_2G);
	if(res != MXC622X_SUCCESS) 
	{
		return res;
	}
	printk("MXC622X_SetDataFormat OK!\n");

	gsensor_gain.x = gsensor_gain.y = gsensor_gain.z = obj->reso->sensitivity;


	res = MXC622X_SetIntEnable(client, 0x00);        
	if(res != MXC622X_SUCCESS)
	{
		return res;
	}
	printk("MXC622X disable interrupt function!\n");


	res = MXC622X_SetPowerMode(client, true);
	if(res != MXC622X_SUCCESS)
	{
		printk("MXC622X_SetPowerMode Fail!\n");
		return res;
	}
	printk("MXC622X_SetPowerMode OK!\n");


	if(0 != reset_cali)
	{ 
		/*reset calibration only in power on*/
		res = MXC622X_ResetCalibration(client);
		if(res != MXC622X_SUCCESS)
		{
		    return res;
		}
	}
	printk("MXC622X_init_client OK!\n");
#ifdef CONFIG_MXC622X_LOWPASS
	memset(&obj->fir, 0x00, sizeof(obj->fir));  
#endif

	mdelay(20);


	return MXC622X_SUCCESS;
}
/*----------------------------------------------------------------------------*/
static int MXC622X_ReadChipInfo(struct i2c_client *client, char *buf, int bufsize)
{
	u8 databuf[10];    

	memset(databuf, 0, sizeof(u8)*10);

	if((NULL == buf)||(bufsize<=30))
	{
		return -1;
	}
	
	if(NULL == client)
	{
		*buf = 0;
		return -2;
	}

	sprintf(buf, "MXC622X Chip");
	return 0;
}
/*----------------------------------------------------------------------------*/
static int MXC622X_ReadSensorData(struct i2c_client *client, char *buf, int bufsize)
{
	struct mxc622x_i2c_data *obj =  obj_i2c_data; //(struct mxc622x_i2c_data*)i2c_get_clientdata(client);
	int acc[MXC622X_AXES_NUM] = {0};
	int res = 0;
	
	client = obj->client;
//	memset(acc, 0, sizeof(u8)*MXC622X_AXES_NUM);

	if(NULL == buf)
	{
		return -1;
	}
	if(NULL == client)
	{
		*buf = 0;
		return -2;
	}
	if(sensor_power == false)
	{
		res = MXC622X_SetPowerMode(client, true);
		if(res)
		{
			GSE_ERR("Power on mxc622x error %d!\n", res);
		}
		msleep(20);
	}

	
	if(MXC622X_ReadData(client, obj->data))
	{        
		GSE_ERR("I2C error: ret value=%d", res);
		return -3;
	}
	else
	{

		//char strbuf[MXC622X_BUFSIZE];
		//s16   data[MXC622X_AXES_NUM+1];
		//struct file *fp;
		//mm_segment_t old_fs = get_fs();
		set_fs(KERNEL_DS);
#if 0
		fp = filp_open(FTM_CUST_ACC,O_RDONLY,0);

		if(fp != 0xfffffffe)//open file suscess
		{
		//fp->f_op->llseek(fp, 0, 0);
			fp->f_pos = 0;
			fp->f_op->read(fp,
			strbuf,
			MXC622X_BUFSIZE,
			&fp->f_pos);	
			filp_close(fp, NULL);	
			set_fs(old_fs);	
			sscanf(strbuf, "%02x %02x %02x", &data[MXC622X_AXIS_X], &data[MXC622X_AXIS_Y], &data[MXC622X_AXIS_Z]);
			obj->data[MXC622X_AXIS_X] -= data[MXC622X_AXIS_X];
			obj->data[MXC622X_AXIS_Y] -= data[MXC622X_AXIS_Y];
			obj->data[MXC622X_AXIS_Z] -= (data[MXC622X_AXIS_Z]-(obj->cvt.sign[MXC622X_AXIS_Z]*obj->reso->sensitivity));		
			//printk("BMA220_SET_CALIBRATION value is %x %x %x -> %d %d %d\r\n",data[BMA220_AXIS_X], data[BMA220_AXIS_Y], data[BMA220_AXIS_Z],obj->data[BMA220_AXIS_X],obj->data[BMA220_AXIS_Y],obj->data[BMA220_AXIS_Z]);
		}
#endif 	
#if 0
		printk("mxc622x_raw data x=%d, y=%d, z=%d \n",obj->data[MXC622X_AXIS_X],obj->data[MXC622X_AXIS_Y],obj->data[MXC622X_AXIS_Z]);
		obj->data[MXC622X_AXIS_X] += obj->cali_sw[MXC622X_AXIS_X];
		obj->data[MXC622X_AXIS_Y] += obj->cali_sw[MXC622X_AXIS_Y];
		obj->data[MXC622X_AXIS_Z] += obj->cali_sw[MXC622X_AXIS_Z];
#endif
		/*remap coordinate*/
		acc[obj->cvt.map[MXC622X_AXIS_X]] = obj->cvt.sign[MXC622X_AXIS_X]*obj->data[MXC622X_AXIS_X];
		acc[obj->cvt.map[MXC622X_AXIS_Y]] = obj->cvt.sign[MXC622X_AXIS_Y]*obj->data[MXC622X_AXIS_Y];
		acc[obj->cvt.map[MXC622X_AXIS_Z]] = obj->cvt.sign[MXC622X_AXIS_Z]*obj->data[MXC622X_AXIS_Z];
		//printk("cvt x=%d, y=%d, z=%d \n",obj->cvt.sign[MXC622X_AXIS_X],obj->cvt.sign[MXC622X_AXIS_Y]);

		//printk("+++++++++++++ acc : %d, %d\n", obj->data[MXC622X_AXIS_X], obj->data[MXC622X_AXIS_Y]);
                //acc[MXC622X_AXIS_X] = -(obj->data[MXC622X_AXIS_X]);
		//acc[MXC622X_AXIS_Y] = obj->data[MXC622X_AXIS_Y];
		//printk("+++++++++++++ acc : %d, %d\n", acc[0], acc[1]);
		//GSE_LOG("Mapped gsensor data: %d, %d!\n", acc[MXC622X_AXIS_X], acc[MXC622X_AXIS_Y]);
		//Out put the mg
		acc[MXC622X_AXIS_X] = (signed char)acc[MXC622X_AXIS_X] * GRAVITY_EARTH_1000 / 64 - cali_offset[0];
		acc[MXC622X_AXIS_Y] = (signed char)acc[MXC622X_AXIS_Y] * GRAVITY_EARTH_1000 / 64 - cali_offset[1];
		acc[MXC622X_AXIS_Z] = 32 * GRAVITY_EARTH_1000 / 64;

		//printk("||||||||||||| acc : %d, %d, %d\n", acc[0], acc[1], acc[2]);
		sprintf(buf, "%04x %04x %04x", acc[MXC622X_AXIS_X], acc[MXC622X_AXIS_Y], acc[MXC622X_AXIS_Z]);
		/*
		if(atomic_read(&obj->trace) & ADX_TRC_IOCTL)
		{
			GSE_LOG("gsensor data: %s!\n", buf);
		}*/
	}
	
	return 0;
}
/*----------------------------------------------------------------------------*/

static int MXC622X_ReadRawData(struct i2c_client *client, char *buf)
{
	struct mxc622x_i2c_data *obj = (struct mxc622x_i2c_data*)i2c_get_clientdata(client);
	int res = 0;
printk("anrry:MXC622X_ReadRawData\n");
	if (!buf || !client)
	{
		return EINVAL;
	}
	
	if((res = MXC622X_ReadData(client, obj->data)))
	{        
		GSE_ERR("I2C error: ret value=%d", res);
		return EIO;
	}
	else
	{
		sprintf(buf, "%04x %04x %04x", obj->data[MXC622X_AXIS_X], 
			obj->data[MXC622X_AXIS_Y], obj->data[MXC622X_AXIS_Z]);
	
	}
	
	return 0;
}

/*----------------------------------------------------------------------------*/
#if 0
static int MXC622X_SET_CALIBRATION(struct i2c_client *client)
{
	//struct mxc622x_i2c_data *obj = (struct mxc622x_i2c_data*)i2c_get_clientdata(client);
	char strbuf[MXC622X_BUFSIZE];
	s16   data[MXC622X_AXES_NUM+1];
	struct file *fp;
	MXC622X_ReadData(client, data);
	sprintf(strbuf, "%02x %02x %02x", data[MXC622X_AXIS_X], data[MXC622X_AXIS_Y], data[MXC622X_AXIS_Z]);	
	//write to nvram
	mm_segment_t old_fs = get_fs();
	set_fs(KERNEL_DS);

	fp = filp_open(FTM_CUST_ACC,O_WRONLY|O_CREAT, 0644);
	fp->f_pos = 0;
	fp->f_op->write(fp,
				  strbuf,
				  MXC622X_BUFSIZE,
				  &fp->f_pos);	
	filp_close(fp, NULL);	
	printk("MXC622X_SET_CALIBRATION value is %x %x %x\r\n",data[MXC622X_AXIS_X], data[MXC622X_AXIS_Y], data[MXC622X_AXIS_Z]);

	set_fs(old_fs);	
	return 0;
}
#endif

static ssize_t show_chipinfo_value(struct device_driver *ddri, char *buf)
{
	struct i2c_client *client = mxc622x_i2c_client;
	char strbuf[MXC622X_BUFSIZE];
	if(NULL == client)
	{
		GSE_ERR("i2c client is null!!\n");
		return 0;
	}
	
	MXC622X_ReadChipInfo(client, strbuf, MXC622X_BUFSIZE);
	return snprintf(buf, PAGE_SIZE, "%s\n", strbuf);        
}

#if 0
static ssize_t gsensor_init(struct device_driver *ddri, char *buf, size_t count)
	{
		struct i2c_client *client = mxc622x_i2c_client;
		char strbuf[MXC622X_BUFSIZE];
		
		if(NULL == client)
		{
			GSE_ERR("i2c client is null!!\n");
			return 0;
		}
		mxc622x_init_client(client, 1);
		return snprintf(buf, PAGE_SIZE, "%s\n", strbuf);			
	}
#endif
/*----------------------------------------------------------------------------*/
static ssize_t show_sensordata_value(struct device_driver *ddri, char *buf)
{
	struct i2c_client *client = mxc622x_i2c_client;
	char strbuf[MXC622X_BUFSIZE];
	
	if(NULL == client)
	{
		GSE_ERR("i2c client is null!!\n");
		return 0;
	}
	printk("anrry:show_sensordata_value\n");
	MXC622X_ReadSensorData(client, strbuf, MXC622X_BUFSIZE);
	//BMA150_ReadRawData(client, strbuf);
	return snprintf(buf, PAGE_SIZE, "%s\n", strbuf);            
}
#if 0
static ssize_t show_sensorrawdata_value(struct device_driver *ddri, char *buf, size_t count)
	{
		struct i2c_client *client = mxc622x_i2c_client;
		char strbuf[MXC622X_BUFSIZE];
		
		if(NULL == client)
		{
			GSE_ERR("i2c client is null!!\n");
			return 0;
		}
		printk("anrry::show_sensorrawdata_value\n");
		//BMA150_ReadSensorData(client, strbuf, BMA150_BUFSIZE);
		MXC622X_ReadRawData(client, strbuf);
		return snprintf(buf, PAGE_SIZE, "%s\n", strbuf);			
	}
#endif

/*----------------------------------------------------------------------------*/

static ssize_t show_cali_value(struct device_driver *ddri, char *buf)
{
	struct i2c_client *client = mxc622x_i2c_client;
	struct mxc622x_i2c_data *obj;
	int err, len = 0, mul;
	int tmp[MXC622X_AXES_NUM];

	if(NULL == client)
	{
		GSE_ERR("i2c client is null!!\n");
		return 0;
	}

	obj = i2c_get_clientdata(client);


	err = MXC622X_ReadOffset(client, obj->offset);
	if(err)
	{
		return -EINVAL;
	}
#if 0
	else if(err = MXC622X_ReadCalibration(client, tmp))
	{
		return -EINVAL;
	}
#endif
	else
	{    
		mul = obj->reso->sensitivity/mxc622x_offset_resolution.sensitivity;
		len += snprintf(buf+len, PAGE_SIZE-len, "[HW ][%d] (%+3d, %+3d, %+3d) : (0x%02X, 0x%02X, 0x%02X)\n", mul,                        
			obj->offset[MXC622X_AXIS_X], obj->offset[MXC622X_AXIS_Y], obj->offset[MXC622X_AXIS_Z],
			obj->offset[MXC622X_AXIS_X], obj->offset[MXC622X_AXIS_Y], obj->offset[MXC622X_AXIS_Z]);
		len += snprintf(buf+len, PAGE_SIZE-len, "[SW ][%d] (%+3d, %+3d, %+3d)\n", 1, 
			obj->cali_sw[MXC622X_AXIS_X], obj->cali_sw[MXC622X_AXIS_Y], obj->cali_sw[MXC622X_AXIS_Z]);

		len += snprintf(buf+len, PAGE_SIZE-len, "[ALL]    (%+3d, %+3d, %+3d) : (%+3d, %+3d, %+3d)\n", 
			obj->offset[MXC622X_AXIS_X]*mul + obj->cali_sw[MXC622X_AXIS_X],
			obj->offset[MXC622X_AXIS_Y]*mul + obj->cali_sw[MXC622X_AXIS_Y],
			obj->offset[MXC622X_AXIS_Z]*mul + obj->cali_sw[MXC622X_AXIS_Z],
			tmp[MXC622X_AXIS_X], tmp[MXC622X_AXIS_Y], tmp[MXC622X_AXIS_Z]);
		
		return len;
    }
}
/*----------------------------------------------------------------------------*/
static ssize_t store_cali_value(struct device_driver *ddri, const char *buf, size_t count)
{
	struct i2c_client *client = mxc622x_i2c_client;  
	int err, x, y, z;
	int dat[MXC622X_AXES_NUM];

	if(!strncmp(buf, "rst", 3))
	{
		err = MXC622X_ResetCalibration(client);
		if(err)
		{
			GSE_ERR("reset offset err = %d\n", err);
		}	
	}
	else if(3 == sscanf(buf, "0x%02X 0x%02X 0x%02X", &x, &y, &z))
	{
		dat[MXC622X_AXIS_X] = x;
		dat[MXC622X_AXIS_Y] = y;
		dat[MXC622X_AXIS_Z] = z;
		err = MXC622X_WriteCalibration(client, dat);
		if(err)
		{
			GSE_ERR("write calibration err = %d\n", err);
		}		
	}
	else
	{
		GSE_ERR("invalid format\n");
	}
	
	return count;
}


/*----------------------------------------------------------------------------*/
static ssize_t show_firlen_value(struct device_driver *ddri, char *buf)
{
#ifdef CONFIG_MXC622X_LOWPASS
	struct i2c_client *client = mxc622x_i2c_client;
	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
	if(atomic_read(&obj->firlen))
	{
		int idx, len = atomic_read(&obj->firlen);
		GSE_LOG("len = %2d, idx = %2d\n", obj->fir.num, obj->fir.idx);

		for(idx = 0; idx < len; idx++)
		{
			GSE_LOG("[%5d %5d %5d]\n", obj->fir.raw[idx][MXC622X_AXIS_X], obj->fir.raw[idx][MXC622X_AXIS_Y], obj->fir.raw[idx][MXC622X_AXIS_Z]);
		}
		
		GSE_LOG("sum = [%5d %5d %5d]\n", obj->fir.sum[MXC622X_AXIS_X], obj->fir.sum[MXC622X_AXIS_Y], obj->fir.sum[MXC622X_AXIS_Z]);
		GSE_LOG("avg = [%5d %5d %5d]\n", obj->fir.sum[MXC622X_AXIS_X]/len, obj->fir.sum[MXC622X_AXIS_Y]/len, obj->fir.sum[MXC622X_AXIS_Z]/len);
	}
	return snprintf(buf, PAGE_SIZE, "%d\n", atomic_read(&obj->firlen));
#else
	return snprintf(buf, PAGE_SIZE, "not support\n");
#endif
}
/*----------------------------------------------------------------------------*/
static ssize_t store_firlen_value(struct device_driver *ddri, const char *buf, size_t count)
{
#ifdef CONFIG_MXC622X_LOWPASS
	struct i2c_client *client = mxc622x_i2c_client;  
	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);
	int firlen;

	if(1 != sscanf(buf, "%d", &firlen))
	{
		GSE_ERR("invallid format\n");
	}
	else if(firlen > C_MAX_FIR_LENGTH)
	{
		GSE_ERR("exceeds maximum filter length\n");
	}
	else
	{ 
		atomic_set(&obj->firlen, firlen);
		if(NULL == firlen)
		{
			atomic_set(&obj->fir_en, 0);
		}
		else
		{
			memset(&obj->fir, 0x00, sizeof(obj->fir));
			atomic_set(&obj->fir_en, 1);
		}
	}
#endif    
	return count;
}
/*----------------------------------------------------------------------------*/
static ssize_t show_trace_value(struct device_driver *ddri, char *buf)
{
	ssize_t res;
	struct mxc622x_i2c_data *obj = obj_i2c_data;
	if (obj == NULL)
	{
		GSE_ERR("i2c_data obj is null!!\n");
		return 0;
	}
	
	res = snprintf(buf, PAGE_SIZE, "0x%04X\n", atomic_read(&obj->trace));     
	return res;    
}
/*----------------------------------------------------------------------------*/
static ssize_t store_trace_value(struct device_driver *ddri, const char *buf, size_t count)
{
	struct mxc622x_i2c_data *obj = obj_i2c_data;
	int trace;
	if (obj == NULL)
	{
		GSE_ERR("i2c_data obj is null!!\n");
		return 0;
	}
	
	if(1 == sscanf(buf, "0x%x", &trace))
	{
		atomic_set(&obj->trace, trace);
	}	
	else
	{
		GSE_ERR("invalid content: '%s', length = %d\n", buf, count);
	}
	
	return count;    
}

/*----------------------------------------------------------------------------*/
static ssize_t show_status_value(struct device_driver *ddri, char *buf)
{
	ssize_t len = 0;    
	struct mxc622x_i2c_data *obj = obj_i2c_data;
	if (obj == NULL)
	{
		GSE_ERR("i2c_data obj is null!!\n");
		return 0;
	}	
	
	if(obj->hw)
	{
		len += snprintf(buf+len, PAGE_SIZE-len, "CUST: %d %d (%d %d)\n", 
	            obj->hw->i2c_num, obj->hw->direction, obj->hw->power_id, obj->hw->power_vol);   
	}
	else
	{
		len += snprintf(buf+len, PAGE_SIZE-len, "CUST: NULL\n");
	}
	return len;    
}
/*----------------------------------------------------------------------------*/
static ssize_t show_power_status_value(struct device_driver *ddri, char *buf)
{
	if(sensor_power)
		printk("G sensor is in work mode, sensor_power = %d\n", sensor_power);
	else
		printk("G sensor is in standby mode, sensor_power = %d\n", sensor_power);

	return 0;
}
/*----------------------------------------------------------------------------*/
static DRIVER_ATTR(chipinfo,   S_IWUSR | S_IRUGO, show_chipinfo_value,      NULL);
static DRIVER_ATTR(sensordata, S_IWUSR | S_IRUGO, show_sensordata_value,    NULL);
static DRIVER_ATTR(cali,       S_IWUSR | S_IRUGO, show_cali_value,          store_cali_value);
static DRIVER_ATTR(firlen,     S_IWUSR | S_IRUGO, show_firlen_value,        store_firlen_value);
static DRIVER_ATTR(trace,      S_IWUSR | S_IRUGO, show_trace_value,         store_trace_value);
static DRIVER_ATTR(status,               S_IRUGO, show_status_value,        NULL);
static DRIVER_ATTR(powerstatus,               S_IRUGO, show_power_status_value,        NULL);
/*----------------------------------------------------------------------------*/
static struct driver_attribute *mxc622x_attr_list[] = {
	&driver_attr_chipinfo,     /*chip information*/
	&driver_attr_sensordata,   /*dump sensor data*/
	&driver_attr_cali,         /*show calibration data*/
	&driver_attr_firlen,       /*filter length: 0: disable, others: enable*/
	&driver_attr_trace,        /*trace log*/
	&driver_attr_status,
	&driver_attr_powerstatus,
};
/*----------------------------------------------------------------------------*/
static int mxc622x_create_attr(struct device_driver *driver) 
{
	int idx, err = 0;
	int num = (int)(sizeof(mxc622x_attr_list)/sizeof(mxc622x_attr_list[0]));
	if (driver == NULL)
	{
		return -EINVAL;
	}

	for(idx = 0; idx < num; idx++)
	{
		err = driver_create_file(driver, mxc622x_attr_list[idx]);
		if(err)
		{            
			GSE_ERR("driver_create_file (%s) = %d\n", mxc622x_attr_list[idx]->attr.name, err);
			break;
		}
	}    
	return err;
}
/*----------------------------------------------------------------------------*/
static int mxc622x_delete_attr(struct device_driver *driver)
{
	int idx ,err = 0;
	int num = (int)(sizeof(mxc622x_attr_list)/sizeof(mxc622x_attr_list[0]));

	if(driver == NULL)
	{
		return -EINVAL;
	}
	

	for(idx = 0; idx < num; idx++)
	{
		driver_remove_file(driver, mxc622x_attr_list[idx]);
	}
	

	return err;
}

/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/

int gsensor_operate(void* self, uint32_t command, void* buff_in, int size_in,
		void* buff_out, int size_out, int* actualout)
{
	int err = 0;
	int value;//, sample_delay;	
	struct mxc622x_i2c_data *priv = (struct mxc622x_i2c_data*)self;
	struct hwm_sensor_data* gsensor_data;
	char buff[MXC622X_BUFSIZE];
	
	//GSE_FUN(f);
	switch (command)
	{
		case SENSOR_DELAY:
			break;

		case SENSOR_ENABLE:
			if((buff_in == NULL) || (size_in < sizeof(int)))
			{
				GSE_ERR("Enable sensor parameter error!\n");
				err = -EINVAL;
			}
			else
			{
				value = *(int *)buff_in;
				if(((value == 0) && (sensor_power == false)) ||((value == 1) && (sensor_power == true)))
				{
					GSE_LOG("Gsensor device have updated!\n");
				}
				else
				{
					err = MXC622X_SetPowerMode(priv->client, !sensor_power);
				}
			}
			break;

		case SENSOR_GET_DATA:
			if((buff_out == NULL) || (size_out< sizeof(struct hwm_sensor_data)))
			{
				GSE_ERR("get sensor data parameter error!\n");
				err = -EINVAL;
			}
			else
			{
				gsensor_data = (struct hwm_sensor_data *)buff_out;

				err = MXC622X_ReadSensorData(priv->client, buff, MXC622X_BUFSIZE);
				if(!err)
				{
				   sscanf(buff, "%x %x %x", &gsensor_data->values[0], 
					   &gsensor_data->values[1], &gsensor_data->values[2]);				
				   gsensor_data->status = SENSOR_STATUS_ACCURACY_MEDIUM;				
				   gsensor_data->value_divide = 1000;
				}
			}
			break;
		default:
			GSE_ERR("gsensor operate function no this parameter %d!\n", command);
			err = -1;
			break;
	}
	
	return err;
}

/****************************************************************************** 
 * Function Configuration
******************************************************************************/
static int mxc622x_open(struct inode *inode, struct file *file)
{
	
        file->private_data = mxc622x_i2c_client;

	if(file->private_data == NULL)
	{
		GSE_ERR("null pointer!!\n");
		return -EINVAL;
	}
	return nonseekable_open(inode, file);

}
/*----------------------------------------------------------------------------*/
static int mxc622x_release(struct inode *inode, struct file *file)
{
	file->private_data = NULL;
	return 0;
}
/*----------------------------------------------------------------------------*/
static long mxc622x_unlocked_ioctl(struct file *file, unsigned int cmd,
       unsigned long arg)
{
	struct i2c_client *client = (struct i2c_client*)file->private_data;
	//struct mxc622x_i2c_data *obj = (struct mxc622x_i2c_data*)i2c_get_clientdata(client);	
	char strbuf[MXC622X_BUFSIZE];
	void __user *data;
	struct SENSOR_DATA sensor_data = {0};
	int err = MXC622X_SUCCESS;
	int cali[3] = {0};

	//GSE_FUN(f);
	if(_IOC_DIR(cmd) & _IOC_READ)
	{
		err = !access_ok(VERIFY_WRITE, (void __user *)arg, _IOC_SIZE(cmd));
	}
	else if(_IOC_DIR(cmd) & _IOC_WRITE)
	{
		err = !access_ok(VERIFY_READ, (void __user *)arg, _IOC_SIZE(cmd));
	}

	if(err)
	{
		GSE_ERR("access error: %08X, (%2d, %2d)\n", cmd, _IOC_DIR(cmd), _IOC_SIZE(cmd));
		return -EFAULT;
	}

        printk("mxc622x_ioctl cmd = 0x%x\n",cmd);

	switch(cmd)
	{
		case GSENSOR_IOCTL_INIT:
			mxc622x_init_client(client, 0);	      
			printk("mxc622x_ioctl GSENSOR_IOCTL_INIT = 0x%x\n",GSENSOR_IOCTL_INIT);		
			break;

		case GSENSOR_IOCTL_READ_CHIPINFO:
			data = (void __user *) arg;
			printk("mxc622x_ioctl GSENSOR_IOCTL_READ_CHIPINFO = 0x%x\n",GSENSOR_IOCTL_READ_CHIPINFO);		
			if(data == NULL)
			{
				err = -EINVAL;
				break;	  
			}
			
			MXC622X_ReadChipInfo(client, strbuf, MXC622X_BUFSIZE);
			if(copy_to_user(data, strbuf, strlen(strbuf)+1))
			{
				err = -EFAULT;
				break;
			}				 
			break;	  

		case GSENSOR_IOCTL_READ_SENSORDATA:
			data = (void __user *) arg;
			printk("anrry::mxc622x_ioctl GSENSOR_IOCTL_READ_SENSORDATA = 0x%x\n",GSENSOR_IOCTL_READ_SENSORDATA);	
			if(data == NULL)
			{
				err = -EINVAL;
				break;	  
			}
			
			MXC622X_ReadSensorData(client, strbuf, MXC622X_BUFSIZE);
			if(copy_to_user(data, strbuf, strlen(strbuf)+1))
			{
				err = -EFAULT;
				break;	  
			}				 
			break;

		case GSENSOR_IOCTL_READ_GAIN:
			data = (void __user *) arg;
			printk("mxc622x_ioctl GSENSOR_IOCTL_READ_GAIN = 0x%x\n",GSENSOR_IOCTL_READ_GAIN);	
			if(data == NULL)
			{
				err = -EINVAL;
				break;	  
			}			
			
			if(copy_to_user(data, &gsensor_gain, sizeof(struct GSENSOR_VECTOR3D)))
			{
				err = -EFAULT;
				break;
			}				 
			break;

		case GSENSOR_IOCTL_READ_RAW_DATA:
			data = (void __user *) arg;
			printk("mxc622x_ioctl GSENSOR_IOCTL_READ_RAW_DATA = 0x%x\n",GSENSOR_IOCTL_READ_RAW_DATA);	
			if(data == NULL)
			{
				err = -EINVAL;
				break;	  
			}
			MXC622X_ReadRawData(client, strbuf);
			if(copy_to_user(data, strbuf, strlen(strbuf)+1))
			{
				err = -EFAULT;
				break;	  
			}
			break;	  

		case GSENSOR_IOCTL_SET_CALI:
			data = (void __user*)arg;
			if(copy_from_user(&sensor_data, data, sizeof(sensor_data)))
			{
				err = -EFAULT;
				break;	  
			}
			cali_offset[0] = sensor_data.x;
			cali_offset[1] = sensor_data.y;
			printk(TAG "mxc622x_ioctl GSENSOR_IOCTL_SET_CALI, offset (x,y) = (%d,%d)", cali_offset[0], cali_offset[1]);	

			break;

		case GSENSOR_IOCTL_CLR_CALI:
			printk("mxc622x_ioctl GSENSOR_IOCTL_CLR_CALI = 0x%x\n",GSENSOR_IOCTL_CLR_CALI);	
			err = MXC622X_ResetCalibration(client);
			break;

		case GSENSOR_IOCTL_GET_CALI:
			data = (void __user*)arg;
			printk(TAG "mxc622x_ioctl GSENSOR_IOCTL_GET_CALI = 0x%x\n",GSENSOR_IOCTL_GET_CALI);	
			if(data == NULL)
			{
				err = -EINVAL;
				break;	  
			}
			err = MXC622X_ReadCalibration(client, cali);
			if(err)
			{
				break;
			}
			
			sensor_data.x = cali[0];
			sensor_data.y = cali[1];
			sensor_data.z = cali[2];
			printk("GSENSOR_IOCTL_GET_CALI, sensor data copy to user (%d,%d,%d)\n", sensor_data.x, sensor_data.y, sensor_data.z);
			if(copy_to_user(data, &sensor_data, sizeof(sensor_data)))
			{
				err = -EFAULT;
				break;
			}	
                 	
			break;
#if 0
		case GSENSOR_IOCTL_SET_CALIBRATION:
		     printk("mxc622x_ioctl GSENSOR_IOCTL_SET_CALIBRATION = 0x%x\n",GSENSOR_IOCTL_SET_CALIBRATION);				
		     err = MXC622X_SET_CALIBRATION(client);
		break;	
#endif

		default:
		       printk("mxc622x_ioctl default = 0x%x\n",cmd);	
			GSE_ERR("unknown IOCTL: 0x%08x\n", cmd);
			err = -ENOIOCTLCMD;
			break;
			
	}
        printk("mxc622x_ioctl err = 0x%x\n",err);		
	return err;
}


/*----------------------------------------------------------------------------*/
static const struct file_operations mxc622x_fops = {
	.owner = THIS_MODULE,
	.open = mxc622x_open,
	.release = mxc622x_release,
	.unlocked_ioctl = mxc622x_unlocked_ioctl,
};
/*----------------------------------------------------------------------------*/
static struct miscdevice mxc622x_device = {
	.minor = MISC_DYNAMIC_MINOR,
	.name = "gsensor",
	.fops = &mxc622x_fops,
};
/*----------------------------------------------------------------------------*/
//#ifndef CONFIG_HAS_EARLYSUSPEND
/*----------------------------------------------------------------------------*/
static int mxc622x_suspend(struct i2c_client *client, pm_message_t msg) 
{
	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);    
	int err = 0;
	GSE_FUN();    

	printk("%s\n", __func__);
	if(msg.event == PM_EVENT_SUSPEND)
	{   
		
		if(obj == NULL)
		{
			GSE_ERR("null pointer!!\n");
			return -EINVAL;
		}
		atomic_set(&obj->suspend, 1);
		
		err = MXC622X_SetPowerMode(obj->client, false);
		if(err)
		{
			GSE_ERR("write power control fail!!\n");
			return err;
		}        
		MXC622X_power(obj->hw, 0);
		GSE_LOG("mxc622x_suspend ok\n");
	}
	return err;
}
/*----------------------------------------------------------------------------*/
static int mxc622x_resume(struct i2c_client *client)
{
	struct mxc622x_i2c_data *obj = i2c_get_clientdata(client);        
	int err;
	GSE_FUN();

	printk("%s\n", __func__);
	if(obj == NULL)
	{
		GSE_ERR("null pointer!!\n");
		return -EINVAL;
	}

	MXC622X_power(obj->hw, 1);
	if((err = mxc622x_init_client(client, 0)))
	{
		GSE_ERR("initialize client fail!!\n");
		return err;        
	}
	atomic_set(&obj->suspend, 0);
	GSE_LOG("mxc622x_resume ok\n");

	return 0;
}
/*----------------------------------------------------------------------------*/
//#else /*CONFIG_HAS_EARLY_SUSPEND is defined*/
/*----------------------------------------------------------------------------*/
#if 0
static void mxc622x_early_suspend(struct early_suspend *h) 
{
 	int err;
	struct mxc622x_i2c_data *obj = container_of(h, struct mxc622x_i2c_data, early_drv);
	GSE_FUN();    

	if(obj == NULL)
	{
		GSE_ERR("null pointer!!\n");
		return;
	}
	atomic_set(&obj->suspend, 1); 
	if((err = MXC622X_SetPowerMode(obj->client, false)))
	{
		GSE_ERR("write power control fail!!\n");
		return;
	}

	sensor_power = false;
	
	//MXC622X_power(obj->hw, 0);
}
/*----------------------------------------------------------------------------*/
static void mxc622x_late_resume(struct early_suspend *h)
{
	int err;
	struct mxc622x_i2c_data *obj = container_of(h, struct mxc622x_i2c_data, early_drv);
	if((err = MXC622X_SetPowerMode(obj->client, true)))
	{
		GSE_ERR("write power control fail!!\n");
		return;
	}

	sensor_power = true;
}
#endif
/*----------------------------------------------------------------------------*/
//#endif /*CONFIG_HAS_EARLYSUSPEND*/
/*----------------------------------------------------------------------------*/
#if 0
static int mxc622x_i2c_detect(struct i2c_client *client, int kind, struct i2c_board_info *info) 
{    
	strcpy(info->type, MXC622X_DEV_NAME);
	return 0;
}
#endif
static int mxc622x_open_report_data(int open)
{
/* should queuq work to report event if  is_report_input_direct=true */
	return 0;
}
static int mxc622x_enable_nodata(int en)
{
	int res =0;
	int retry = 0;
	bool power=false;
	
	if(1==en)
	{
		power=true;
	}
	if(0==en)
	{
		power =false;
	}

	for(retry = 0; retry < 3; retry++){
		res = MXC622X_SetPowerMode(obj_i2c_data->client, power);
		if(res == 0)
		{
			GSE_LOG("MXC622X_SetPowerMode done\n");
			break;
		}
		GSE_LOG("MXC622X_SetPowerMode fail\n");
	}

	
	if(res != MXC622X_SUCCESS)
	{
		printk("mxc622x_SetPowerMode fail!\n");
		return -1;
	}
	printk("mxc622x_enable_nodata OK!\n");
	return 0;
}

static int mxc622x_set_delay(u64 ns)
{
   /* int value =0;
	int sample_delay=0;
	int err;
	value = (int)ns/1000/1000;
	if(value <= 5)
	{
		sample_delay = MXC622X_BW_200HZ;
	}
	else if(value <= 10)
	{
		sample_delay = MXC622X_BW_100HZ;
	}
	else
	{
		sample_delay = MXC622X_BW_50HZ;
	}
				
	err = MXC622X_SetBWRate(obj_i2c_data->client, sample_delay);
	if(err != MXC622X_SUCCESS ) //0x2C->BW=100Hz
	{
		GSE_ERR("mxc622x_set_delay Set delay parameter error!\n");
		return -1;
	}
	GSE_LOG("mxc622x_set_delay (%d)\n",value);*/
	return 0;
}

static int mxc622x_get_data(int* x ,int* y,int* z, int* status)
{
	char buff[MXC622X_BUFSIZE];
	MXC622X_ReadSensorData(obj_i2c_data->client, buff, MXC622X_BUFSIZE);
	
	sscanf(buff, "%x %x %x", x, y, z);		
	*status = SENSOR_STATUS_ACCURACY_MEDIUM;

	return 0;
}
/*----------------------------------------------------------------------------*/
static int mxc622x_i2c_probe(struct i2c_client *client, const struct i2c_device_id *id)
{
	struct i2c_client *new_client;
	struct mxc622x_i2c_data *obj;
	//struct hwmsen_object sobj;
	struct acc_control_path ctl = {0};
	struct acc_data_path data = {0};
	
	int err = 0;

	GSE_FUN();

	if(!(obj = kzalloc(sizeof(*obj), GFP_KERNEL)))
	{
		err = -ENOMEM;
		goto exit;
	}
	
	memset(obj, 0, sizeof(struct mxc622x_i2c_data));

	obj->hw = hw;
	if((err = hwmsen_get_convert(obj->hw->direction, &obj->cvt)))
	{
		GSE_ERR("invalid direction: %d\n", obj->hw->direction);
		goto exit;
	}

	obj_i2c_data = obj;
	obj->client = client;
	obj->client->timing = 100;
	obj->client->addr >>= 1;
	obj->client->ext_flag = obj->client->ext_flag & (~I2C_DMA_FLAG);
	new_client = obj->client;
	printk("mxc622x, i2c client timming = %d, addr = %x, ext_flag = %x\n", new_client->timing, new_client->addr, new_client->ext_flag);
	i2c_set_clientdata(new_client,obj);
	
	atomic_set(&obj->trace, 0);
	atomic_set(&obj->suspend, 0);
	
#ifdef CONFIG_MXC622X_LOWPASS
	if(obj->hw->firlen > C_MAX_FIR_LENGTH)
	{
		atomic_set(&obj->firlen, C_MAX_FIR_LENGTH);
	}	
	else
	{
		atomic_set(&obj->firlen, obj->hw->firlen);
	}
	
	if(atomic_read(&obj->firlen) > 0)
	{
		atomic_set(&obj->fir_en, 1);
	}
	
#endif

	mxc622x_i2c_client = new_client;	

	err = mxc622x_init_client(new_client, 1);
	if(err)
	{
		goto exit_init_failed;
	}

	err = misc_register(&mxc622x_device);
	printk("mxc622x misc register ret = %d\n", err);
	if(err)
	{
		GSE_ERR("mxc622x_device register failed\n");
		goto exit_misc_device_register_failed;
	}

	ctl.is_use_common_factory = false;
	err = mxc622x_create_attr(&(mxc622x_init_info.platform_diver_addr->driver));
    if(err)
	{
		GSE_ERR("create attribute err = %d\n", err);
		goto exit_create_attr_failed;
	}
	ctl.open_report_data = mxc622x_open_report_data;
	ctl.enable_nodata = mxc622x_enable_nodata;
	ctl.set_delay  = mxc622x_set_delay;
	ctl.is_report_input_direct = false;
	ctl.is_support_batch = obj->hw->is_batch_supported;
	err = acc_register_control_path(&ctl);
	if (err) {
		GSE_ERR("register acc control path err\n");
		goto exit_kfree;
	}
	data.get_data = mxc622x_get_data;
	data.vender_div = 1000;
	err = acc_register_data_path(&data);
	if (err) {
		GSE_ERR("register acc data path err= %d\n", err);
		goto exit_kfree;
	}
	#if 0
       sobj.self = obj;
       sobj.polling = 1;
       sobj.sensor_operate = gsensor_operate;

	err = hwmsen_attach(ID_ACCELEROMETER, &sobj);
	if(err)
	{
		GSE_ERR("attach fail = %d\n", err);
		goto exit_kfree;
	}
#endif
#ifdef CONFIG_HAS_EARLYSUSPEND

	obj->early_drv.level    = EARLY_SUSPEND_LEVEL_DISABLE_FB - 1,
	obj->early_drv.suspend  = mxc622x_early_suspend,
	obj->early_drv.resume   = mxc622x_late_resume,    
	register_early_suspend(&obj->early_drv);	
#endif 

	GSE_LOG("%s: OK\n", __func__);    
	s_nInitFlag = MXC622X_INIT_SUCC;
	return 0;

	exit_create_attr_failed:
	misc_deregister(&mxc622x_device);
	exit_misc_device_register_failed:
	exit_init_failed:
	//i2c_detach_client(new_client);
	exit_kfree:
	kfree(obj);
	exit:
	GSE_ERR("%s: err = %d\n", __func__, err);        
	s_nInitFlag = MXC622X_INIT_FAIL;

	return err;
}

/*----------------------------------------------------------------------------*/
static int mxc622x_i2c_remove(struct i2c_client *client)
{
	int err = 0;	

	err = mxc622x_delete_attr(&(mxc622x_init_info.platform_diver_addr->driver));
    if(err)
	{
		GSE_ERR("mxc622x_delete_attr fail: %d\n", err);
	}

	err = misc_deregister(&mxc622x_device);
	if(err)
	{
		GSE_ERR("misc_deregister fail: %d\n", err);
	}

//	err = hwmsen_detach(ID_ACCELEROMETER);
//	if(err)
		mxc622x_i2c_client = NULL;

	i2c_unregister_device(client);
	kfree(i2c_get_clientdata(client));
	return 0;
}

/*****************************************
 *** mxc622x_local_init
 *****************************************/
static int  mxc622x_local_init(void)
{
	GSE_LOG("mxc622x_local_init\n");
//	MXC622X_power(hw, 1);

	if (i2c_add_driver(&mxc622x_i2c_driver)) {
		GSE_ERR("add driver error\n");
		return -1;
	}

//	if (MXC622X_INIT_FAIL == s_nInitFlag)
//		return -1;
	return 0;
}

/*----------------------------------------------------------------------------*/
static int mxc622x_remove(void)
{

    GSE_FUN();    
 //   MXC622X_power(hw, 0);    
    i2c_del_driver(&mxc622x_i2c_driver);
    return 0;
}

/*----------------------------------------------------------------------------*/
static int __init mxc622x_init(void)
{
	const char *name = "mediatek,mxc622x";

	hw = get_accel_dts_func(name, hw);
	acc_driver_add(&mxc622x_init_info);
	return 0;    
}
/*----------------------------------------------------------------------------*/
static void __exit mxc622x_exit(void)
{
	GSE_LOG("mxc622x_exit\n");
}
/*----------------------------------------------------------------------------*/
module_init(mxc622x_init);
module_exit(mxc622x_exit);
/*----------------------------------------------------------------------------*/
MODULE_LICENSE("GPL");
MODULE_DESCRIPTION("MXC622X I2C driver");
MODULE_AUTHOR("Aaron Peng");
