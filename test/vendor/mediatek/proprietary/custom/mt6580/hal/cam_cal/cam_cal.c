#include "camera_custom_cam_cal.h"//for 658x new compilier option#include "camera_custom_eeprom.h"
#include <string.h>
CAM_CAL_TYPE_ENUM CAM_CALInit(void)
{
	return CAM_CAL_USED;
}

unsigned int CAM_CALDeviceName(char* DevName)
{
	return 1;
}

unsigned int HI842_CAM_CALDeviceName(char* DevName)
{
	char* str= "HI842_CAM_CAL_DRV" ;
	strcat (DevName , str ) ;
        return 0;
}
