

#include "drStd.h"
#include "DrApi/DrApi.h"

#include "drRpmbOps.h"
#include "drSmgmt.h"

#define RPMB_MAX_USER_NUM MAX_DR_SESSIONS

#define RPMB_PART_START_AUTO 0xFFFFFFFF
#define RPMB_PART_SIZE_AUTO 0xFFFFFFFF

enum {
    RPMB_USER_ID0 = 0,
    RPMB_USER_ID1,
    RPMB_USER_ID2,
    RPMB_USER_ID3,
    RPMB_USER_ID4,
};


const char * const rpmb_err_msg[] = {
    "",
    "General failure",
    "Authentication failure",
    "Counter failure",
    "Address failure",
    "Write failure",
    "Read failure",
    "Authentication key not yet programmed",

};

typedef struct _rpmb_part {
    uint32_t uid;
    uint32_t start_block;
    uint32_t length;
    
} DRRPMB_PART, *PDRRPMB_PART;

static DRRPMB_PART rpmb_part_tbl[RPMB_MAX_USER_NUM] = {
    {RPMB_USER_ID0, RPMB_PART_START_AUTO, RPMB_PART_SIZE_AUTO},
    {RPMB_USER_ID1, RPMB_PART_START_AUTO, RPMB_PART_SIZE_AUTO},
    {RPMB_USER_ID2, RPMB_PART_START_AUTO, RPMB_PART_SIZE_AUTO},
    {RPMB_USER_ID3, RPMB_PART_START_AUTO, RPMB_PART_SIZE_AUTO},
    {RPMB_USER_ID4, RPMB_PART_START_AUTO, RPMB_PART_SIZE_AUTO}
};

static unsigned int rpmb_user_cnt = 0;


void drRpmbPartInit(uint32_t rpmb_size)
{
    int i;

    for (i = 0; i < RPMB_MAX_USER_NUM; i++) {

        if (rpmb_part_tbl[i].start_block == RPMB_PART_START_AUTO &&
            rpmb_part_tbl[i].start_block == RPMB_PART_SIZE_AUTO) {
            
            rpmb_part_tbl[i].start_block = i * rpmb_size / 0x200 / RPMB_MAX_USER_NUM;
            rpmb_part_tbl[i].length = rpmb_size / 0x200 / RPMB_MAX_USER_NUM;
        }
    }
}


uint16_t drRpmbGetBlkIdx(int uid)
{
    return rpmb_part_tbl[uid].start_block;
}


int drRpmbGetSize()
{
    return 128*1024*2; //temp 256KB.
}

uint16_t cpu_to_be16p(uint16_t *p)
{
    return (((*p << 8)&0xFF00) | (*p >> 8));
}

uint32_t cpu_to_be32p(uint32_t *p)
{
    return (((*p & 0xFF) << 24) | ((*p & 0xFF00) << 8) | ((*p & 0xFF0000) >> 8) | (*p & 0xFF000000) >> 24 );
}

#if 0


int drRpmbRegister(uint32_t sid)
{
    if (rpmb_user_cnt >= RPMB_MAX_USER_NUM)
        return -1;
    
    drRpmbPartition[rpmb_user_cnt].sid = sid;
    drRpmbPartition[rpmb_user_cnt].start_block = 128 * rpmb_user_cnt;

    rpmb_user_cnt++;    

    return 0;
}


int drRpmbReadData()
{


}



#endif
