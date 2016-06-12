#include <utils/Log.h>
#include <fcntl.h>
#include <math.h>
#include <string.h>

#include "camera_custom_nvram.h"
#include "camera_custom_sensor.h"
#include "image_sensor.h"
#include "kd_imgsensor_define.h"
#include "camera_AE_PLineTable_sp0a09mipiraw.h"
#include "camera_info_sp0a09mipiraw.h"
#include "camera_custom_AEPlinetable.h"
#include "camera_custom_tsf_tbl.h"
const NVRAM_CAMERA_ISP_PARAM_STRUCT CAMERA_ISP_DEFAULT_VALUE =
{{
	
	
    //Version
    Version: NVRAM_CAMERA_PARA_FILE_VERSION,
    //SensorId
    SensorId: SENSOR_ID,
    ISPComm:{
        {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        }
    },
    ISPPca: {
#include INCLUDE_FILENAME_ISP_PCA_PARAM
    },
    ISPRegs:{
#include INCLUDE_FILENAME_ISP_REGS_PARAM
    },
    ISPMfbMixer:{{
    {
      0x00000000, 0x00000000
    },
    {
      0x00000000, 0x00000000
    },
    {
      0x00000000, 0x00000000
    },
    {
      0x00000000, 0x00000000
    },
    {
      0x00000000, 0x00000000
    },
    {
      0x00000000, 0x00000000
    },
    {
      0x00000000, 0x00000000
    }
    }},
    ISPMulitCCM:{
      Poly22:{
        75475,      // i4R_AVG
        15544,      // i4R_STD
        98250,      // i4B_AVG
        21643,      // i4B_STD
        0,      // i4R_MAX
        0,      // i4R_MIN
        0,      // i4G_MAX
        0,      // i4G_MIN
        0,      // i4B_MAX
        0,      // i4B_MIN
        {  // i4P00[9]
            4380000, -1780000, -40000, -660000, 3255000, -35000, 2500, -1912500, 4470000
        },
        {  // i4P10[9]
            908325, -1059680, 151355, -110611, -27083, 137694, 35056, 345207, -380263
        },
        {  // i4P01[9]
            643739, -770991, 127251, -224667, -163460, 388127, -30588, -174370, 204958
        },
        {  // i4P20[9]
            0, 0, 0, 0, 0, 0, 0, 0, 0
        },
        {  // i4P11[9]
            0, 0, 0, 0, 0, 0, 0, 0, 0
        },
        {  // i4P02[9]
            0, 0, 0, 0, 0, 0, 0, 0, 0
        }

      },

      AWBGain:{
        // Strobe
        {
          512,    // i4R
          512,    // i4G
          512    // i4B
        },
        // A
        {
          585,    // i4R
          512,    // i4G
          1201    // i4B
        },
        // TL84
        {
          681,    // i4R
          512,    // i4G
          1007    // i4B
        },
        // CWF
        {
          811,    // i4R
          512,    // i4G
          1038    // i4B
        },
        // D65
        {
          942,    // i4R
          512,    // i4G
          684    // i4B
        },
        // Reserved 1
        {
          512,    // i4R
          512,    // i4G
          512    // i4B
        },
        // Reserved 2
        {
          512,    // i4R
          512,    // i4G
          512    // i4B
        },
        // Reserved 3
        {
          512,    // i4R
          512,    // i4G
          512    // i4B
        }
      },
      Weight:{
        1, // Strobe
        1, // A
        1, // TL84
        1, // CWF
        1, // D65
        1, // Reserved 1
        1, // Reserved 2
        1  // Reserved 3
                }
    },
    //bInvokeSmoothCCM
    bInvokeSmoothCCM: MTRUE

}};

const NVRAM_CAMERA_3A_STRUCT CAMERA_3A_NVRAM_DEFAULT_VALUE =
{
    NVRAM_CAMERA_3A_FILE_VERSION, // u4Version
    SENSOR_ID, // SensorId

    // AE NVRAM
    {
        // rDevicesInfo
        {
            1024,    // u4MinGain, 1024 base = 1x
            6144,    // u4MaxGain, 16x
            100,    // u4MiniISOGain, ISOxx  
            128,    // u4GainStepUnit, 1x/8 
            42190,    // u4PreExpUnit 
            20,    // u4PreMaxFrameRate
            42190,    // u4VideoExpUnit  
            30,    // u4VideoMaxFrameRate 
            1024,    // u4Video2PreRatio, 1024 base = 1x 
            42190,    // u4CapExpUnit 
            20,    // u4CapMaxFrameRate
            1024,    // u4Cap2PreRatio, 1024 base = 1x
            42190,    // u4Video1ExpUnit
            30,    // u4Video1MaxFrameRate
            1024,    // u4Video12PreRatio, 1024 base = 1x
            42190,    // u4Video2ExpUnit
            30,    // u4Video2MaxFrameRate
            1024,    // u4Video22PreRatio, 1024 base = 1x
            42190,    // u4Custom1ExpUnit
            30,    // u4Custom1MaxFrameRate
            1024,    // u4Custom12PreRatio, 1024 base = 1x
            42190,    // u4Custom2ExpUnit
            30,    // u4Custom2MaxFrameRate
            1024,    // u4Custom22PreRatio, 1024 base = 1x
            42190,    // u4Custom3ExpUnit
            30,    // u4Custom3MaxFrameRate
            1024,    // u4Custom32PreRatio, 1024 base = 1x
            42190,    // u4Custom4ExpUnit
            30,    // u4Custom4MaxFrameRate
            1024,    // u4Custom42PreRatio, 1024 base = 1x
            42190,    // u4Custom5ExpUnit
            30,    // u4Custom5MaxFrameRate
            1024,    // u4Custom52PreRatio, 1024 base = 1x
            24,    // u4LensFno, Fno = 2.8
            350    // u4FocusLength_100x
        },
        // rHistConfig
        {
            4,    // u4HistHighThres
            40,    // u4HistLowThres
            2,    // u4MostBrightRatio
            1,    // u4MostDarkRatio
            160,    // u4CentralHighBound
            20,    // u4CentralLowBound
            {240, 230, 220, 210, 200},    // u4OverExpThres[AE_CCT_STRENGTH_NUM] 
            {62, 70, 82, 108, 141},    // u4HistStretchThres[AE_CCT_STRENGTH_NUM] 
            {18, 22, 26, 30, 34}    // u4BlackLightThres[AE_CCT_STRENGTH_NUM] 
        },
        // rCCTConfig
        {
            TRUE,    // bEnableBlackLight
            TRUE,    // bEnableHistStretch
            TRUE,    // bEnableAntiOverExposure
            TRUE,    // bEnableTimeLPF
            FALSE,    // bEnableCaptureThres
            FALSE,    // bEnableVideoThres
            FALSE,    // bEnableVideo1Thres
            FALSE,    // bEnableVideo2Thres
            FALSE,    // bEnableCustom1Thres
            FALSE,    // bEnableCustom2Thres
            FALSE,    // bEnableCustom3Thres
            FALSE,    // bEnableCustom4Thres
            FALSE,    // bEnableCustom5Thres
            FALSE,    // bEnableStrobeThres
            50,    // u4AETarget
            47,    // u4StrobeAETarget
            50,    // u4InitIndex
            4,    // u4BackLightWeight
            32,    // u4HistStretchWeight
            4,    // u4AntiOverExpWeight
            2,    // u4BlackLightStrengthIndex
            2,    // u4HistStretchStrengthIndex
            2,    // u4AntiOverExpStrengthIndex
            2,    // u4TimeLPFStrengthIndex
            {1, 3, 5, 7, 8},    // u4LPFConvergeTable[AE_CCT_STRENGTH_NUM] 
            90,    // u4InDoorEV = 9.0, 10 base 
            -8,    // i4BVOffset delta BV = value/10 
            0,    // u4PreviewFlareOffset
            0,    // u4CaptureFlareOffset
            3,    // u4CaptureFlareThres
            64,    // u4VideoFlareOffset
            3,    // u4VideoFlareThres
            64,    // u4CustomFlareOffset
            3,    // u4CustomFlareThres
            64,    // u4StrobeFlareOffset
            3,    // u4StrobeFlareThres
            160,    // u4PrvMaxFlareThres
            0,    // u4PrvMinFlareThres
            160,    // u4VideoMaxFlareThres
            0,    // u4VideoMinFlareThres
            18,    // u4FlatnessThres    // 10 base for flatness condition.
            75,    // u4FlatnessStrength
            //rMeteringSpec
            {
                //rHS_Spec
                {
                    FALSE,//bEnableHistStretch           // enable histogram stretch
                    1024,//u4HistStretchWeight          // Histogram weighting value
                    40,//u4Pcent                      // 
                    160,//u4Thd                        // 0~255
                    75, //74,//u4FlatThd                    // 0~255
                    120,//u4FlatBrightPcent
                    120,//u4FlatDarkPcent
                    //sFlatRatio
                    {
                        1000,  //i4X1
                        1024,  //i4Y1
                        2400,  //i4X2
                        0  //i4Y2
                    },
                    TRUE, //bEnableGreyTextEnhance
                    1800, //u4GreyTextFlatStart, > sFlatRatio.i4X1, < sFlatRatio.i4X2
                    {
                        10,  //i4X1
                        1024,  //i4Y1
                        80,  //i4X2
                        0  //i4Y2
                    }
                },
                //rAOE_Spec
                {
                    FALSE, //bEnableAntiOverExposure
                    1024, //u4AntiOverExpWeight
                    10, //u4Pcent
                    200, //u4Thd
                    TRUE, //bEnableCOEP
                    1, //u4COEPcent
                    106, //u4COEThd
                    0, //u4BVCompRatio
                    //sCOEYRatio;     // the outer y ratio
                    {
                        23,  //i4X1
                        1024,  //i4Y1
                        47,  //i4X2
                        0  //i4Y2
                    },
                    //sCOEDiffRatio;  // inner/outer y difference ratio
                    {
                        1500,  //i4X1
                        0,  //i4Y1
                        2100,  //i4X2
                        1024  //i4Y2
                    }
                },
                //rABL_Spec
                {
                    FALSE,//bEnableBlackLight
                    0,//u4BackLightWeight
                    0,//u4Pcent
                    0,//u4Thd
                    0, // center luminance
                    0, // final target limitation, 256/128 = 2x
                    //sFgBgEVRatio
                    {
                        0,  //i4X1
                        0,  //i4Y1
                        0,  //i4X2
                        0  //i4Y2
                    },
                    //sBVRatio
                    {
                        0,  //i4X1
                        0,  //i4Y1
                        0,  //i4X2
                        0  //i4Y2
                    }
                },
                //rNS_Spec
                {
                    TRUE, // bEnableNightScene
                    5,//u4Pcent
                    170,//u4Thd
                    72,//u4FlatThd
                    200,//u4BrightTonePcent
                    92,//u4BrightToneThd
                    500,//u4LowBndPcent
                    5,//u4LowBndThd
                    26,//u4LowBndThdLimit
                    50,//u4FlatBrightPcent
                    300,//u4FlatDarkPcent
                    //sFlatRatio
                    {
                        1200,  //i4X1
                        1024,  //i4Y1
                        2400,  //i4X2
                        0  //i4Y2
                    },
                    //sBVRatio
                    {
                        -500,  //i4X1
                        1024,  //i4Y1
                        3000,  //i4X2
                        0  //i4Y2
                    },
                    TRUE, // bEnableNightSkySuppresion
                    //sSkyBVRatio
                    {
                        -4000,  //i4X1
                        1024,  //i4Y1
                        -2000,  //i4X2
                        0  //i4Y2
                    }
        },
                // rTOUCHFD_Spec
        {
            40,                     // uMeteringYLowBound;
            50,                     // uMeteringYHighBound;
                    40, //uFaceYLowBound;
                    50, //uFaceYHighBound;
                    3,  //uFaceCentralWeight;
                    120,//u4MeteringStableMax;
                    80, //u4MeteringStableMin;
                }
            }, //End rMeteringSpec
            // rFlareSpec
            {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //uPrvFlareWeightArr[16];
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //uVideoFlareWeightArr[16];
                96,                                               //u4FlareStdThrHigh;
                48,                                               //u4FlareStdThrLow;
                0,                                                //u4PrvCapFlareDiff;
                4,                                                //u4FlareMaxStepGap_Fast;
                0,                                                //u4FlareMaxStepGap_Slow;
                1800,                                             //u4FlarMaxStepGapLimitBV;
                0,                                                //u4FlareAEStableCount;
            },
            //rAEMoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                190, //u4Bright2TargetEnd
                20,   //u4Dark2TargetStart
                90, //u4B2TEnd
                70,  //u4B2TStart
                60,  //u4D2TEnd
                90,  //u4D2TStart
            },
            //rAEVideoMoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                10,  //u4B2TStart
                10,  //u4D2TEnd
                90,  //u4D2TStart
            },

            //rAEVideo1MoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                10,  //u4B2TStart
                10,  //u4D2TEnd
                90,  //u4D2TStart
            },

            //rAEVideo2MoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                10,  //u4B2TStart
                10,  //u4D2TEnd
                90,  //u4D2TStart
            },

            //rAECustom1MoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                10,  //u4B2TStart
                10,  //u4D2TEnd
                90,  //u4D2TStart
            },

            //rAECustom2MoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                10,  //u4B2TStart
                10,  //u4D2TEnd
                90,  //u4D2TStart
            },

            //rAECustom3MoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                10,  //u4B2TStart
                10,  //u4D2TEnd
                90,  //u4D2TStart
            },

            //rAECustom4MoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                10,  //u4B2TStart
                10,  //u4D2TEnd
                90,  //u4D2TStart
            },

            //rAECustom5MoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                150,  //u4Bright2TargetEnd
                20,    //u4Dark2TargetStart
                90, //u4B2TEnd
                  10,   //u4B2TStart
                  10,   //u4D2TEnd
                90,  //u4D2TStart
            },
            //rAEFaceMoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                190,  //u4Bright2TargetEnd
                  10,    //u4Dark2TargetStart
                80, //u4B2TEnd
                30,  //u4B2TStart
                20,  //u4D2TEnd
                60,  //u4D2TStart
            },

            //rAETrackingMoveRatio =
            {
                100, //u4SpeedUpRatio
                100, //u4GlobalRatio
                190,  //u4Bright2TargetEnd
                10,    //u4Dark2TargetStart
                80, //u4B2TEnd
                30,  //u4B2TStart
                20,  //u4D2TEnd
                60,  //u4D2TStart
            },
            //rAEAOENVRAMParam =
                {
                1,      // i4AOEStrengthIdx: 0 / 1 / 2
                128,    // u4BVCompRatio
            {
                {
                        47,  //u4Y_Target
                        10,  //u4AOE_OE_percent
                        160,  //u4AOE_OEBound
                        15,    //u4AOE_DarkBound
                        950,    //u4AOE_LowlightPrecent
                        5,    //u4AOE_LowlightBound
                        100,    //u4AOESceneLV_L
                        150,    //u4AOESceneLV_H
                        40,    //u4AOE_SWHdrLE_Bound
                    },
                    {
                        47,  //u4Y_Target
                        10,  //u4AOE_OE_percent
                        180,  //u4AOE_OEBound
                20,    //u4AOE_DarkBound
                        950,    //u4AOE_LowlightPrecent
                10,    //u4AOE_LowlightBound
                100,    //u4AOESceneLV_L
                150,    //u4AOESceneLV_H
                        40,    //u4AOE_SWHdrLE_Bound
                    },
                    {
                        47,  //u4Y_Target
                        10,  //u4AOE_OE_percent
                        200,  //u4AOE_OEBound
                        25,    //u4AOE_DarkBound
                        950,    //u4AOE_LowlightPrecent
                        15,    //u4AOE_LowlightBound
                        100,    //u4AOESceneLV_L
                        150,    //u4AOESceneLV_H
                        40,    //u4AOE_SWHdrLE_Bound
                    }
                }
                }
        }
    },
    // AWB NVRAM
    {
        {
            {
                // AWB calibration data
                {
                    // rUnitGain (unit gain: 1.0 = 512)
                    {
                            0,    // i4R
                            0,    // i4G
                            0    // i4B
                    },
                    // rGoldenGain (golden sample gain: 1.0 = 512)
                    {
                            0,    // i4R
                            0,    // i4G
                            0    // i4B
                    },
                    // rTuningUnitGain (Tuning sample unit gain: 1.0 = 512)
                    {
                            0,    // i4R
                            0,    // i4G
                            0    // i4B
                    },
                    // rD65Gain (D65 WB gain: 1.0 = 512)
                {
                    963,    // D65Gain_R
                    512,    // D65Gain_G
                    665    // D65Gain_B
                }
            },
            // Original XY coordinate of AWB light source
            {
                // Strobe
                {
                    137,    // i4X
                    -330    // i4Y
                },
                // Horizon
                {
                    -370,    // OriX_Hor
                    -360    // OriY_Hor
                },
                // A
                {
                    -249,    // OriX_A
                    -367    // OriY_A
                },
                // TL84
                {
                    -125,    // OriX_TL84
                    -357    // OriY_TL84
                },
                // CWF
                {
                    -70,    // OriX_CWF
                    -417    // OriY_CWF
                },
                // DNP
                {
                    -31,    // OriX_DNP
                    -329    // OriY_DNP
                },
                // D65
                {
                    137,    // OriX_D65
                    -330    // OriY_D65
                },
                // DF
                {
                    0,    // OriX_DF
                    0    // OriY_DF
                }
            },
            // Rotated XY coordinate of AWB light source
            {
                // Strobe
                {
                    113,    // i4X
                    -338    // i4Y
                },
                // Horizon
                {
                    -394,    // RotX_Hor
                    -333    // RotY_Hor
                },
                // A
                {
                    -274,    // RotX_A
                    -348    // RotY_A
                },
                // TL84
                {
                    -150,    // RotX_TL84
                    -347    // RotY_TL84
                },
                // CWF
                {
                    -99,    // RotX_CWF
                    -410    // RotY_CWF
                },
                // DNP
                {
                    -54,    // RotX_DNP
                    -326    // RotY_DNP
                },
                // D65
                {
                    113,    // RotX_D65
                    -338    // RotY_D65
                },
                // DF
                {
                    86,    // RotX_DF
                    -401    // RotY_DF
                }
            },
            // AWB gain of AWB light source
            {
                // Strobe 
                {
                    963,    // i4R
                    512,    // i4G
                    665    // i4B
                },
                // Horizon 
                {
                    512,    // AWBGAIN_HOR_R
                    519,    // AWBGAIN_HOR_G
                    1392    // AWBGAIN_HOR_B
                },
                // A 
                {
                    600,    // AWBGAIN_A_R
                    512,    // AWBGAIN_A_G
                    1179    // AWBGAIN_A_B
                },
                // TL84 
                {
                    701,    // AWBGAIN_TL84_R
                    512,    // AWBGAIN_TL84_G
                    983    // AWBGAIN_TL84_B
                },
                // CWF 
                {
                    819,    // AWBGAIN_CWF_R
                    512,    // AWBGAIN_CWF_G
                    991    // AWBGAIN_CWF_B
                },
                // DNP 
                {
                    767,    // AWBGAIN_DNP_R
                    512,    // AWBGAIN_DNP_G
                    833    // AWBGAIN_DNP_B
                },
                // D65 
                {
                    963,    // AWBGAIN_D65_R
                    512,    // AWBGAIN_D65_G
                    665    // AWBGAIN_D65_B
                },
                // DF 
                {
                    512,    // AWBGAIN_DF_R
                    512,    // AWBGAIN_DF_G
                    512    // AWBGAIN_DF_B
                }
            },
            // Rotation matrix parameter
            {
                4,    // RotationAngle
                255,    // Cos
                18    // Sin
            },
            // Daylight locus parameter
            {
                -149,    // i4SlopeNumerator
                128    // i4SlopeDenominator
            },
            // Predictor gain
            {
                100, // i4PrefRatio100
                // DaylightLocus_L
                {
                    933,    // i4R
                    530,    // i4G
                    685    // i4B
                },
                // DaylightLocus_H
                {
                    714,    // i4R
                    512,    // i4G
                    936    // i4B
                },
                // Temporal General
                {
                    963,    // i4R
                    512,    // i4G
                    665    // i4B
                },
                // AWB LSC Gain
                {
                    757,        // i4R
                    512,        // i4G
                    876        // i4B
                }
            },
            // AWB light area
            {
                // Strobe:FIXME
                {
                    230,    // i4RightBound
                    130,    // i4LeftBound
                    -288,    // i4UpperBound
                    -388    // i4LowerBound
                },
                // Tungsten
                {
                    -230,    // TungRightBound
                    -794,    // TungLeftBound
                    -220,    // TungUpperBound
                    -360    // TungLowerBound
                },
                // Warm fluorescent
                {
                    -230,    // WFluoRightBound
                    -794,    // WFluoLeftBound
                    -360,    // WFluoUpperBound
                    -480    // WFluoLowerBound
                },
                // Fluorescent
                {
                    -85,    // FluoRightBound
                    -230,    // FluoLeftBound
                    -270,    // FluoUpperBound
                    -360    // FluoLowerBound
                },
                // CWF
                {
                0,    // CWFRightBound
                -230,    // CWFLeftBound
                -360,    // CWFUpperBound
                -468    // CWFLowerBound
                },
                // Daylight
                {
                    143,    // DayRightBound
                    -85,    // DayLeftBound
                    -290,    // DayUpperBound
                    -359    // DayLowerBound
                },
                // Shade
                {
                    473,    // ShadeRightBound
                    143,    // ShadeLeftBound
                    -298,    // ShadeUpperBound
                    -367    // ShadeLowerBound
                },
                // Daylight Fluorescent
                {
                    143,    // DFRightBound
                    0,    // DFLeftBound
                    -360,    // DFUpperBound
                    -465    // DFLowerBound
                }
            },
            // PWB light area
            {
                // Reference area
                {
                    473,    // PRefRightBound
                    -794,    // PRefLeftBound
                    -220,    // PRefUpperBound
                    -480    // PRefLowerBound
                },
                // Daylight
                {
                    168,    // PDayRightBound
                    -85,    // PDayLeftBound
                    -290,    // PDayUpperBound
                    -359    // PDayLowerBound
                },
                // Cloudy daylight
                {
                    268,    // PCloudyRightBound
                    93,    // PCloudyLeftBound
                    -290,    // PCloudyUpperBound
                    -359    // PCloudyLowerBound
                },
                // Shade
                {
                    368,    // PShadeRightBound
                    93,    // PShadeLeftBound
                    -290,    // PShadeUpperBound
                    -359    // PShadeLowerBound
                },
                // Twilight
                {
                    -85,    // PTwiRightBound
                    -245,    // PTwiLeftBound
                    -290,    // PTwiUpperBound
                    -359    // PTwiLowerBound
                },
                // Fluorescent
                {
                    163,    // PFluoRightBound
                    -250,    // PFluoLeftBound
                    -288,    // PFluoUpperBound
                    -460    // PFluoLowerBound
                },
                // Warm fluorescent
                {
                    -174,    // PWFluoRightBound
                    -374,    // PWFluoLeftBound
                    -288,    // PWFluoUpperBound
                    -460    // PWFluoLowerBound
                },
                // Incandescent
                {
                    -174,    // PIncaRightBound
                    -374,    // PIncaLeftBound
                    -290,    // PIncaUpperBound
                    -359    // PIncaLowerBound
                },
                // Gray World
                {
                    5000,    // PGWRightBound
                    -5000,    // PGWLeftBound
                    5000,    // PGWUpperBound
                    -5000    // PGWLowerBound
                }
            },
            // PWB default gain	
            {
                // Daylight
                {
                    863,    // PWB_Day_R
                    512,    // PWB_Day_G
                    725    // PWB_Day_B
                },
                // Cloudy daylight
                {
                    1028,    // PWB_Cloudy_R
                    512,    // PWB_Cloudy_G
                    593    // PWB_Cloudy_B
                },
                // Shade
                {
                    1094,    // PWB_Shade_R
                    512,    // PWB_Shade_G
                    551    // PWB_Shade_B
                },
                // Twilight
                {
                    666,    // PWB_Twi_R
                    512,    // PWB_Twi_G
                    978    // PWB_Twi_B
                },
                // Fluorescent
                {
                    833,    // PWB_Fluo_R
                    512,    // PWB_Fluo_G
                    873    // PWB_Fluo_B
                },
                // Warm fluorescent
                {
                    624,    // PWB_WFluo_R
                    512,    // PWB_WFluo_G
                    1218    // PWB_WFluo_B
                },
                // Incandescent
                {
                    580,    // PWB_Inca_R
                    512,    // PWB_Inca_G
                    1145    // PWB_Inca_B
                },
                // Gray World
                {
                    512,    // PWB_GW_R
                    512,    // PWB_GW_G
                    512    // PWB_GW_B
                }
            },
            // AWB preference color	
            {
                // Tungsten
                {
                    0,    // TUNG_SLIDER
                    5689    // TUNG_OFFS
                },
                // Warm fluorescent	
                {
                    0,    // WFluo_SLIDER
                    5687    // WFluo_OFFS
                },
                // Shade
                {
                    0,    // Shade_SLIDER
                    1410    // Shade_OFFS
                },
                // Sunset Area
                {
                    -25,   // i4Sunset_BoundXr_Thr
                    -326    // i4Sunset_BoundYr_Thr
                },
                // Sunset Vertex
                {
                    -30,   // i4Sunset_BoundXr_Thr
                    -326    // i4Sunset_BoundYr_Thr
                },
                // Shade F Area
                {
                    -230,   // i4BoundXrThr
                    -355    // i4BoundYrThr
                },
                // Shade F Vertex
                {
                    -200,   // i4BoundXrThr
                    -365    // i4BoundYrThr
                },
                // Shade CWF Area
                {
                    -230,   // i4BoundXrThr
                    -453    // i4BoundYrThr
                },
                // Shade CWF Vertex
                {
                    -200,   // i4BoundXrThr
                    -440    // i4BoundYrThr
                },
                // Low CCT Area
                {
                    -414,   // i4BoundXrThr
                    119    // i4BoundYrThr
                },
                // Low CCT Vertex
                {
                    -414,   // i4BoundXrThr
                    119    // i4BoundYrThr
                }
            },
            // CCT estimation
            {
                // CCT
                {
                    2300,    // i4CCT[0]
                    2850,    // i4CCT[1]
                    3750,    // i4CCT[2]
                    5100,    // i4CCT[3]
                    6500     // i4CCT[4]
                },
                // Rotated X coordinate
                {
                -507,    // i4RotatedXCoordinate[0]
                -387,    // i4RotatedXCoordinate[1]
                -263,    // i4RotatedXCoordinate[2]
                -167,    // i4RotatedXCoordinate[3]
                0    // i4RotatedXCoordinate[4]
                }
                }
            },
            // Algorithm Tuning Paramter
            {
                // AWB Backup Enable
                0,

            // Daylight locus offset LUTs for tungsten
            {
                21, // i4Size: LUT dimension
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000}, // i4LUTIn
                {0, 350, 700, 1224, 1224, 1224, 1225, 1225, 1226, 1226, 1227, 1227, 1228, 1228, 1228, 1229, 1229, 1230, 1230, 1231, 1231} // i4LUTOut
            },
            // Daylight locus offset LUTs for WF
            {
                21, // i4Size: LUT dimension
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000}, // i4LUTIn
                {0, 350, 700, 850, 1200, 1224, 1225, 1225, 1226, 1226, 1227, 1227, 1228, 1228, 1228, 1229, 1229, 1230, 1230, 1231, 1231} // i4LUTOut
            },
            // Daylight locus offset LUTs for Shade
            {
                21, // i4Size: LUT dimension
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000}, // i4LUTIn
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000} // i4LUTOut
            },
                // Preference gain for each light source
                {
                    //        LV0              LV1              LV2              LV3              LV4              LV5              LV6              LV7              LV8              LV9
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                    //        LV10             LV11             LV12             LV13             LV14             LV15             LV16             LV17             LV18
          	            {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
        	        }, // STROBE
        	        {
                    {530, 530, 480}, {530, 530, 480}, {530, 530, 480}, {530, 530, 480}, {530, 530, 480}, {520, 520, 496}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {500, 512, 512}, 
           	            {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
        	        }, // TUNGSTEN
        	        {
                    {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {500, 512, 508}, 
           	            {512, 512, 508}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
        	        }, // WARM F
        	        {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}
                    }, // F
                    {
                    {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 600}, 
                    {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
                    }, // CWF
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}
                    }, // DAYLIGHT
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
                    }, // SHADE
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
                    } // DAYLIGHT F
                },
                // Parent block weight parameter
                {
                    1,      // bEnable
                    6           // i4ScalingFactor: [6] 1~12, [7] 1~6, [8] 1~3, [9] 1~2, [>=10]: 1
                },
                // AWB LV threshold for predictor
                {
                    115, //100,    // i4InitLVThr_L
                    155, //140,    // i4InitLVThr_H
                    100 //80      // i4EnqueueLVThr
                },
                // AWB number threshold for temporal predictor
                {
                        65,     // i4Neutral_ParentBlk_Thr
                    //LV0   1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  50,  25,   2,   2,   2,   2,   2,   2,   2}  // (%) i4CWFDF_LUTThr
                },
                // AWB light neutral noise reduction for outdoor
                {
                    //LV0  1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18
                    // Non neutral
                    {   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                    // Flurescent
                    {   0,   0,   0,   0,   0,   3,   5,   5,   5,   5,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                    // CWF
                    {   0,   0,   0,   0,   0,   3,   5,   5,   5,   5,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                    // Daylight
                    {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   2,   2,   2,   2,   2,   2,   2,   2},  // (%)
                    // DF
                    {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                },
                // AWB feature detection
                {
                // Sunset Prop
                    {
                        1,          // i4Enable
                        120,        // i4LVThr_L
                        130,        // i4LVThr_H
                        10,         // i4SunsetCountThr
                        0,          // i4SunsetCountRatio_L
                        171         // i4SunsetCountRatio_H
                    },
                    // Shade F Detection
                    {
                        1,          // i4Enable
                        55,        // i4LVThr_L
                        70,        // i4LVThr_H
                        96         // i4DaylightProb
                    },
                    // Shade CWF Detection
                    {
                        1,          // i4Enable
                        55,        // i4LVThr_L
                        70,        // i4LVThr_H
                        192         // i4DaylightProb
                    },
                    // Low CCT
                    {
                        0,          // i4Enable
                        256        // i4SpeedRatio
                    }
                },
                // AWB non-neutral probability for spatial and temporal weighting look-up table (Max: 100; Min: 0)
                {
                    //LV0   1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18
                    {   0,  33,  66, 100, 100, 100, 100, 100, 100, 100, 100,  70,  30,  20,  10,   0,   0,   0,   0}
                },
                // AWB daylight locus probability look-up table (Max: 100; Min: 0)
                {   //LV0    1     2     3      4     5     6     7     8      9      10     11    12   13     14    15   16    17    18
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  50,  25,   0,   0,   0,   0}, // Strobe
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  25,   0,   0,   0}, // Tungsten
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  25,  25,  25,   0,   0,   0}, // Warm fluorescent
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  95,  75,  50,  25,  25,  25,   0,   0,   0}, // Fluorescent
                    {  90,  90,  90,  90,  90,  90,  90,  90,  90,  90,  80,  55,  30,  30,  30,  30,   0,   0,   0}, // CWF
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  40,  30,  20}, // Daylight
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  25,   0,   0,   0,   0}, // Shade
                    {  90,  90,  90,  90,  90,  90,  90,  90,  90,  90,  80,  55,  30,  30,  30,  30,   0,   0,   0} // Daylight fluorescent
                }
            }
        },
        {
          {
                // AWB calibration data
                {
                    // rUnitGain (unit gain: 1.0 = 512)
                    {
                            0,    // i4R
                            0,    // i4G
                            0    // i4B
                    },
                    // rGoldenGain (golden sample gain: 1.0 = 512)
                    {
                            0,    // i4R
                            0,    // i4G
                            0    // i4B
                    },
                    // rTuningUnitGain (Tuning sample unit gain: 1.0 = 512)
                    {
                            0,    // i4R
                            0,    // i4G
                            0    // i4B
                    },
                    // rD65Gain (D65 WB gain: 1.0 = 512)
                {
                    963,    // D65Gain_R
                    512,    // D65Gain_G
                    665    // D65Gain_B
                }
            },
            // Original XY coordinate of AWB light source
            {
                // Strobe
                {
                    137,    // i4X
                    -330    // i4Y
                },
                // Horizon
                {
                    -370,    // OriX_Hor
                    -360    // OriY_Hor
                },
                // A
                {
                    -249,    // OriX_A
                    -367    // OriY_A
                },
                // TL84
                {
                    -125,    // OriX_TL84
                    -357    // OriY_TL84
                },
                // CWF
                {
                    -70,    // OriX_CWF
                    -417    // OriY_CWF
                },
                // DNP
                {
                    -31,    // OriX_DNP
                    -329    // OriY_DNP
                },
                // D65
                {
                    137,    // OriX_D65
                    -330    // OriY_D65
                },
                // DF
                {
                    0,    // OriX_DF
                    0    // OriY_DF
                }
            },
            // Rotated XY coordinate of AWB light source
            {
                // Strobe
                {
                    113,    // i4X
                    -338    // i4Y
                },
                // Horizon
                {
                    -394,    // RotX_Hor
                    -333    // RotY_Hor
                },
                // A
                {
                    -274,    // RotX_A
                    -348    // RotY_A
                },
                // TL84
                {
                    -150,    // RotX_TL84
                    -347    // RotY_TL84
                },
                // CWF
                {
                    -99,    // RotX_CWF
                    -410    // RotY_CWF
                },
                // DNP
                {
                    -54,    // RotX_DNP
                    -326    // RotY_DNP
                },
                // D65
                {
                    113,    // RotX_D65
                    -338    // RotY_D65
                },
                // DF
                {
                    86,    // RotX_DF
                    -401    // RotY_DF
                }
            },
            // AWB gain of AWB light source
            {
                // Strobe 
                {
                    963,    // i4R
                    512,    // i4G
                    665    // i4B
                },
                // Horizon 
                {
                    512,    // AWBGAIN_HOR_R
                    519,    // AWBGAIN_HOR_G
                    1392    // AWBGAIN_HOR_B
                },
                // A 
                {
                    600,    // AWBGAIN_A_R
                    512,    // AWBGAIN_A_G
                    1179    // AWBGAIN_A_B
                },
                // TL84 
                {
                    701,    // AWBGAIN_TL84_R
                    512,    // AWBGAIN_TL84_G
                    983    // AWBGAIN_TL84_B
                },
                // CWF 
                {
                    819,    // AWBGAIN_CWF_R
                    512,    // AWBGAIN_CWF_G
                    991    // AWBGAIN_CWF_B
                },
                // DNP 
                {
                    767,    // AWBGAIN_DNP_R
                    512,    // AWBGAIN_DNP_G
                    833    // AWBGAIN_DNP_B
                },
                // D65 
                {
                    963,    // AWBGAIN_D65_R
                    512,    // AWBGAIN_D65_G
                    665    // AWBGAIN_D65_B
                },
                // DF 
                {
                    512,    // AWBGAIN_DF_R
                    512,    // AWBGAIN_DF_G
                    512    // AWBGAIN_DF_B
                }
            },
            // Rotation matrix parameter
            {
                4,    // RotationAngle
                255,    // Cos
                18    // Sin
            },
            // Daylight locus parameter
            {
                -149,    // i4SlopeNumerator
                128    // i4SlopeDenominator
            },
            // Predictor gain
            {
                100, // i4PrefRatio100
                // DaylightLocus_L
                {
                    933,    // i4R
                    530,    // i4G
                    685    // i4B
                },
                // DaylightLocus_H
                {
                    714,    // i4R
                    512,    // i4G
                    936    // i4B
                },
                // Temporal General
                {
                    963,    // i4R
                    512,    // i4G
                    665    // i4B
                },
                // AWB LSC Gain
                {
                    757,        // i4R
                    512,        // i4G
                    876        // i4B
                }
            },
            // AWB light area
            {
                // Strobe:FIXME
                {
                    163,    // i4RightBound
                    63,    // i4LeftBound
                    -288,    // i4UpperBound
                    -388    // i4LowerBound
                },
                // Tungsten
                {
                    -200,    // TungRightBound
                    -794,    // TungLeftBound
                    -278,    // TungUpperBound
                    -348    // TungLowerBound
                },
                // Warm fluorescent
                {
                    -200,    // WFluoRightBound
                    -794,    // WFluoLeftBound
                    -348,    // WFluoUpperBound
                    -450    // WFluoLowerBound
                },
                // Fluorescent
                {
                    -85,    // FluoRightBound
                    -200,    // FluoLeftBound
                    -298,    // FluoUpperBound
                    -379    // FluoLowerBound
                },
                // CWF
                {
                -25,    // CWFRightBound
                -200,    // CWFLeftBound
                -379,    // CWFUpperBound
                -465    // CWFLowerBound
                },
                // Daylight
                {
                    143,    // DayRightBound
                    -85,    // DayLeftBound
                    -298,    // DayUpperBound
                    -379    // DayLowerBound
                },
                // Shade
                {
                    473,    // ShadeRightBound
                    143,    // ShadeLeftBound
                    -298,    // ShadeUpperBound
                    -367    // ShadeLowerBound
                },
                // Daylight Fluorescent
                {
                    143,    // DFRightBound
                    -25,    // DFLeftBound
                    -379,    // DFUpperBound
                    -465    // DFLowerBound
                }
            },
            // PWB light area
            {
                // Reference area
                {
                    473,    // PRefRightBound
                    -794,    // PRefLeftBound
                    -253,    // PRefUpperBound
                    -465    // PRefLowerBound
                },
                // Daylight
                {
                    168,    // PDayRightBound
                    -85,    // PDayLeftBound
                    -298,    // PDayUpperBound
                    -379    // PDayLowerBound
                },
                // Cloudy daylight
                {
                    268,    // PCloudyRightBound
                    93,    // PCloudyLeftBound
                    -298,    // PCloudyUpperBound
                    -379    // PCloudyLowerBound
                },
                // Shade
                {
                    368,    // PShadeRightBound
                    93,    // PShadeLeftBound
                    -298,    // PShadeUpperBound
                    -379    // PShadeLowerBound
                },
                // Twilight
                {
                    -85,    // PTwiRightBound
                    -245,    // PTwiLeftBound
                    -298,    // PTwiUpperBound
                    -379    // PTwiLowerBound
                },
                // Fluorescent
                {
                    163,    // PFluoRightBound
                    -250,    // PFluoLeftBound
                    -288,    // PFluoUpperBound
                    -460    // PFluoLowerBound
                },
                // Warm fluorescent
                {
                    -174,    // PWFluoRightBound
                    -374,    // PWFluoLeftBound
                    -288,    // PWFluoUpperBound
                    -460    // PWFluoLowerBound
                },
                // Incandescent
                {
                    -174,    // PIncaRightBound
                    -374,    // PIncaLeftBound
                    -298,    // PIncaUpperBound
                    -379    // PIncaLowerBound
                },
                // Gray World
                {
                    5000,    // PGWRightBound
                    -5000,    // PGWLeftBound
                    5000,    // PGWUpperBound
                    -5000    // PGWLowerBound
                }
                },
                // PWB default gain
            {
                // Daylight
                {
                    881,    // PWB_Day_R
                    512,    // PWB_Day_G
                    738    // PWB_Day_B
                },
                // Cloudy daylight
                {
                    1049,    // PWB_Cloudy_R
                    512,    // PWB_Cloudy_G
                    603    // PWB_Cloudy_B
                },
                // Shade
                {
                    1117,    // PWB_Shade_R
                    512,    // PWB_Shade_G
                    561    // PWB_Shade_B
                },
                // Twilight
                {
                    679,    // PWB_Twi_R
                    512,    // PWB_Twi_G
                    995    // PWB_Twi_B
                },
                // Fluorescent
                {
                    833,    // PWB_Fluo_R
                    512,    // PWB_Fluo_G
                    873    // PWB_Fluo_B
                },
                // Warm fluorescent
                {
                    624,    // PWB_WFluo_R
                    512,    // PWB_WFluo_G
                    1218    // PWB_WFluo_B
                },
                // Incandescent
                {
                    592,    // PWB_Inca_R
                    512,    // PWB_Inca_G
                    1165    // PWB_Inca_B
                },
                // Gray World
                {
                    512,    // PWB_GW_R
                    512,    // PWB_GW_G
                    512    // PWB_GW_B
                }
            },
            // AWB preference color	
            {
                // Tungsten
                {
                    0,    // TUNG_SLIDER
                    5962    // TUNG_OFFS
                },
                // Warm fluorescent	
                {
                    0,    // WFluo_SLIDER
                    5277    // WFluo_OFFS
                },
                // Shade
                {
                    0,    // Shade_SLIDER
                    1410    // Shade_OFFS
                },
                // Sunset Area
                {
                    -30,   // i4Sunset_BoundXr_Thr
                    -326    // i4Sunset_BoundYr_Thr
                },
                // Sunset Vertex
                {
                    -30,   // i4Sunset_BoundXr_Thr
                    -326    // i4Sunset_BoundYr_Thr
                },
                // Shade F Area
                {
                    -200,   // i4BoundXrThr
                    -351    // i4BoundYrThr
                },
                // Shade F Vertex
                {
                    -200,   // i4BoundXrThr
                    -365    // i4BoundYrThr
                },
                // Shade CWF Area
                {
                    -200,   // i4BoundXrThr
                    -414    // i4BoundYrThr
                },
                // Shade CWF Vertex
                {
                    -200,   // i4BoundXrThr
                    -440    // i4BoundYrThr
                },
                // Low CCT Area
                {
                    -414,   // i4BoundXrThr
                    119    // i4BoundYrThr
                },
                // Low CCT Vertex
                {
                    -414,   // i4BoundXrThr
                    119    // i4BoundYrThr
                }
            },
                // CCT estimation
            {
                // CCT
                {
                    2300,    // i4CCT[0]
                    2850,    // i4CCT[1]
                    3750,    // i4CCT[2]
                    5100,    // i4CCT[3]
                    6500     // i4CCT[4]
                },
                // Rotated X coordinate
                {
                -507,    // i4RotatedXCoordinate[0]
                -387,    // i4RotatedXCoordinate[1]
                -263,    // i4RotatedXCoordinate[2]
                -167,    // i4RotatedXCoordinate[3]
                0    // i4RotatedXCoordinate[4]
                }
                }
            },
            // Algorithm Tuning Paramter
            {
                // AWB Backup Enable
                0,

            // Daylight locus offset LUTs for tungsten
            {
                21, // i4Size: LUT dimension
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000}, // i4LUTIn
                {0, 350, 700, 1224, 1224, 1224, 1225, 1225, 1226, 1226, 1227, 1227, 1228, 1228, 1228, 1229, 1229, 1230, 1230, 1231, 1231} // i4LUTOut
            },
            // Daylight locus offset LUTs for WF
            {
                21, // i4Size: LUT dimension
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000}, // i4LUTIn
                {0, 350, 700, 850, 1200, 1224, 1225, 1225, 1226, 1226, 1227, 1227, 1228, 1228, 1228, 1229, 1229, 1230, 1230, 1231, 1231} // i4LUTOut
            },
            // Daylight locus offset LUTs for Shade
            {
                21, // i4Size: LUT dimension
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000}, // i4LUTIn
                {0, 500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000} // i4LUTOut
            },
                // Preference gain for each light source
                {
                    //        LV0              LV1              LV2              LV3              LV4              LV5              LV6              LV7              LV8              LV9
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                    //        LV10             LV11             LV12             LV13             LV14             LV15             LV16             LV17             LV18
          	            {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
        	        }, // STROBE
        	        {
          	            {530, 530, 480}, {530, 530, 480}, {530, 530, 480}, {530, 530, 480}, {530, 530, 480}, {520, 520, 496}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
           	            {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
        	        }, // TUNGSTEN
        	        {
                        {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508}, {512, 512, 508},
           	            {512, 512, 508}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
        	        }, // WARM F
        	        {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}
                    }, // F
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
                    }, // CWF
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}, {502, 512, 512}
                    }, // DAYLIGHT
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
                    }, // SHADE
                    {
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512},
                        {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}, {512, 512, 512}
                    } // DAYLIGHT F
                },
                // Parent block weight parameter
                {
                    1,      // bEnable
                    6           // i4ScalingFactor: [6] 1~12, [7] 1~6, [8] 1~3, [9] 1~2, [>=10]: 1
                },
                // AWB LV threshold for predictor
                {
                    115, //100,    // i4InitLVThr_L
                    155, //140,    // i4InitLVThr_H
                    100 //80      // i4EnqueueLVThr
                },
                // AWB number threshold for temporal predictor
                {
                        65,     // i4Neutral_ParentBlk_Thr
                    //LV0   1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  50,  25,   2,   2,   2,   2,   2,   2,   2}  // (%) i4CWFDF_LUTThr
                },
                // AWB light neutral noise reduction for outdoor
                {
                    //LV0  1    2    3    4    5    6    7    8    9    10   11   12   13   14   15   16   17   18
                    // Non neutral
                    {   3,   3,   3,   3,   3,   3,   3,   3,   3,   3,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                    // Flurescent
                    {   0,   0,   0,   0,   0,   3,   5,   5,   5,   5,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                    // CWF
                    {   0,   0,   0,   0,   0,   3,   5,   5,   5,   5,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                    // Daylight
                    {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   2,   2,   2,   2,   2,   2,   2,   2},  // (%)
                    // DF
                    {   0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   5,  10,  10,  10,  10,  10,  10,  10,  10},  // (%)
                },
                // AWB feature detection
                {
                // Sunset Prop
                    {
                        1,          // i4Enable
                        120,        // i4LVThr_L
                        130,        // i4LVThr_H
                        10,         // i4SunsetCountThr
                        0,          // i4SunsetCountRatio_L
                        171         // i4SunsetCountRatio_H
                    },
                    // Shade F Detection
                    {
                        1,          // i4Enable
                        55,        // i4LVThr_L
                        70,        // i4LVThr_H
                        96         // i4DaylightProb
                    },
                    // Shade CWF Detection
                    {
                        1,          // i4Enable
                        55,        // i4LVThr_L
                        70,        // i4LVThr_H
                        192         // i4DaylightProb
                    },
                    // Low CCT
                    {
                        0,          // i4Enable
                        256        // i4SpeedRatio
                    }
                },
                // AWB non-neutral probability for spatial and temporal weighting look-up table (Max: 100; Min: 0)
                {
                    //LV0   1    2    3    4    5    6    7    8    9   10   11   12   13   14   15   16   17   18
                    {   0,  33,  66, 100, 100, 100, 100, 100, 100, 100, 100,  70,  30,  20,  10,   0,   0,   0,   0}
                },
                // AWB daylight locus probability look-up table (Max: 100; Min: 0)
                {   //LV0    1     2     3      4     5     6     7     8      9      10     11    12   13     14    15   16    17    18
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  50,  25,   0,   0,   0,   0}, // Strobe
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  25,   0,   0,   0}, // Tungsten
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  25,  25,  25,   0,   0,   0}, // Warm fluorescent
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  95,  75,  50,  25,  25,  25,   0,   0,   0}, // Fluorescent
                    {  90,  90,  90,  90,  90,  90,  90,  90,  90,  90,  80,  55,  30,  30,  30,  30,   0,   0,   0}, // CWF
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  40,  30,  20}, // Daylight
                    { 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100,  75,  50,  25,   0,   0,   0,   0}, // Shade
                    {  90,  90,  90,  90,  90,  90,  90,  90,  90,  90,  80,  55,  30,  30,  30,  30,   0,   0,   0} // Daylight fluorescent
                }
            }
        }
    },


    // Flash AWB NVRAM
    {
#include INCLUDE_FILENAME_FLASH_AWB_PARA
    },

    {0}
};

#include INCLUDE_FILENAME_ISP_LSC_PARAM
//};  //  namespace

const CAMERA_TSF_TBL_STRUCT CAMERA_TSF_DEFAULT_VALUE =
{
    {
        1,  // isTsfEn
        2,  // tsfCtIdx
        {20, 2000, -110, -110, 512, 512, 512, 0}    // rAWBInput[8]
    },

    #include INCLUDE_FILENAME_TSF_PARA
    #include INCLUDE_FILENAME_TSF_DATA
};




typedef NSFeature::RAWSensorInfo<SENSOR_ID> SensorInfoSingleton_T;


namespace NSFeature {
template <>
UINT32
SensorInfoSingleton_T::
impGetDefaultData(CAMERA_DATA_TYPE_ENUM const CameraDataType, VOID*const pDataBuf, UINT32 const size) const
{
    UINT32 dataSize[CAMERA_DATA_TYPE_NUM] = {sizeof(NVRAM_CAMERA_ISP_PARAM_STRUCT),
                                             sizeof(NVRAM_CAMERA_3A_STRUCT),
                                             sizeof(NVRAM_CAMERA_SHADING_STRUCT),
                                             sizeof(NVRAM_LENS_PARA_STRUCT),
                                             sizeof(AE_PLINETABLE_T),
                                             0,
                                             sizeof(CAMERA_TSF_TBL_STRUCT)};

    if (CameraDataType > CAMERA_DATA_TSF_TABLE || NULL == pDataBuf || (size < dataSize[CameraDataType]))
    {
        return 1;
    }

    switch(CameraDataType)
    {
        case CAMERA_NVRAM_DATA_ISP:
            memcpy(pDataBuf,&CAMERA_ISP_DEFAULT_VALUE,sizeof(NVRAM_CAMERA_ISP_PARAM_STRUCT));
            break;
        case CAMERA_NVRAM_DATA_3A:
            memcpy(pDataBuf,&CAMERA_3A_NVRAM_DEFAULT_VALUE,sizeof(NVRAM_CAMERA_3A_STRUCT));
            break;
        case CAMERA_NVRAM_DATA_SHADING:
            memcpy(pDataBuf,&CAMERA_SHADING_DEFAULT_VALUE,sizeof(NVRAM_CAMERA_SHADING_STRUCT));
            break;
        case CAMERA_DATA_AE_PLINETABLE:
            memcpy(pDataBuf,&g_PlineTableMapping,sizeof(AE_PLINETABLE_T));
            break;
        case CAMERA_DATA_TSF_TABLE:
            memcpy(pDataBuf,&CAMERA_TSF_DEFAULT_VALUE,sizeof(CAMERA_TSF_TBL_STRUCT));
            break;
        default:
            return 1;
    }
    return 0;
}}; // NSFeature


