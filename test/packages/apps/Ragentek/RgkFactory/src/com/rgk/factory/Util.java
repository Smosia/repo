package com.rgk.factory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.rgk.factory.backled.BackLED;
import com.rgk.factory.battery.Battery;
import com.rgk.factory.bluetooth.Bluetooth;
import com.rgk.factory.buttonlight.ButtonLightActivity;
import com.rgk.factory.earphone.EarPhone;
import com.rgk.factory.fingerprint.FingerPrint;
import com.rgk.factory.flash.FlashlightManager;
import com.rgk.factory.flash.ProactiveFlashlightManager;
import com.rgk.factory.fm.FM;
import com.rgk.factory.gps.GPS;
import com.rgk.factory.hall.Hall;
import com.rgk.factory.headset.HeadSet;
import com.rgk.factory.key.KeyTestActivity;
import com.rgk.factory.lcd.LCD;
import com.rgk.factory.lcdblur.LcdBlur;
import com.rgk.factory.loundspeaker.LoundSpeaker;
import com.rgk.factory.maincamera.MainCamera;
import com.rgk.factory.memory.Memory;
import com.rgk.factory.microphone.MicrPhone;
import com.rgk.factory.microphone2.MicrPhone2;
import com.rgk.factory.notificationlight.NotificationLight;
import com.rgk.factory.otg.Otg;
import com.rgk.factory.sdcard.SdCard;
import com.rgk.factory.sensor.DistanceSensor;
import com.rgk.factory.sensor.GravitySensor;
import com.rgk.factory.sensor.Gyroscope;
import com.rgk.factory.sensor.LightSensor;
import com.rgk.factory.sensor.MSensor;
import com.rgk.factory.sensor.SensorCalibration;
import com.rgk.factory.simcard.SimCard;
import com.rgk.factory.simsignal.SimSignal;
import com.rgk.factory.subcamera.SubCamera;
import com.rgk.factory.tdcoder.CaptureActivity;
import com.rgk.factory.temperature.Temperature;
import com.rgk.factory.telephony.Telephony;
import com.rgk.factory.touchpanel.TouchPanel;
import com.rgk.factory.versioninfo.MotherboardInfo;
import com.rgk.factory.versioninfo.VersionInfo;
import com.rgk.factory.vibrator.Vibration;
import com.rgk.factory.wifi.WiFi;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Log;

public class Util {
	public static String TAG = "Util";
	private static PackageManager pm;
	private Context mContext;
	public static List<String> autoTestList,backtestList,singleTestTag,singleTestTitle;
	public static List<Class> autoTestClass,backtestClass,singleTestClass;
	public static boolean backLed = true,lcd = true,touchPanel = true,key = true,vibrator = true,
			gravitySensor = true,calibration = true,distanceSensor = true,lightSensor = true,
			loundSpeaker = true,mainCamera = true,subCamera = true,flash = true,proactiveFlash = false,
			headset = true,earphone = true,fm = true,battery = true,temperature = true,telephony = true,memory = true,
			sdCard = true,simCard = true,simSignal = true,gps = true,bluetooth = true,wiFi = true,
			microphone = true,microphone2 = true,tdCode = true,buttonLight = true,hall = true,otg = true,
			gyroscope = true, magnetic = true, notificationLight, lcdBlur = true, fingerprint = true;
	public static boolean gravitySensorNoZAxis;
	public static int AP_CFG_REEB_PRODUCT_INFO_LID,AP_CFG_RDEB_FILE_WIFI_LID,AP_CFG_CUSTOM_FILE_GPS_LID;
	public static boolean defaultEnglish;
	public Util(Context context) {
		mContext = context;
		pm = context.getPackageManager();
		autoTestList = new ArrayList<String>();
		autoTestClass = new ArrayList<Class>();
		backtestList = new ArrayList<String>();
		backtestClass = new ArrayList<Class>();
		singleTestTag = new ArrayList<String>();
		singleTestTitle = new ArrayList<String>();
		singleTestClass = new ArrayList<Class>();
	}
	public void initValue() {
		//readXML();
		readResources(mContext);
		//setFeatureValue();
		setAutoTestItems();
		setSingleTestItems(mContext);
	}
	private void setInitValue(String tag, String value){
		Log.i(TAG,"setInitValue():tag="+tag+"---value="+value);
		if("backLed".equals(tag)) {
			backLed = Boolean.parseBoolean(value);
		} else if("lcd".equals(tag)) {
			lcd = Boolean.parseBoolean(value);
		} else if("touchPanel".equals(tag)) {
			touchPanel = Boolean.parseBoolean(value);
		} else if("key".equals(tag)) {
			key = Boolean.parseBoolean(value);
		} else if("vibrator".equals(tag)) {
			vibrator = Boolean.parseBoolean(value);
		} else if("gravitySensor".equals(tag)) {
			gravitySensor = Boolean.parseBoolean(value);
		} else if("calibration".equals(tag)) {
			calibration = Boolean.parseBoolean(value);
		} else if("distanceSensor".equals(tag)) {
			distanceSensor = Boolean.parseBoolean(value);
		} else if("lightSensor".equals(tag)) {
			lightSensor = Boolean.parseBoolean(value);
		} else if("loundSpeaker".equals(tag)) {
			loundSpeaker = Boolean.parseBoolean(value);
		} else if("mainCamera".equals(tag)) {
			mainCamera = Boolean.parseBoolean(value);
		} else if("subCamera".equals(tag)) {
			subCamera = Boolean.parseBoolean(value);
		} else if("flash".equals(tag)) {
			flash = Boolean.parseBoolean(value);
		} else if("proactiveFlash".equals(tag)) {
			proactiveFlash = Boolean.parseBoolean(value);
		} else if("headset".equals(tag)) {
			headset = Boolean.parseBoolean(value);
		} else if("earphone".equals(tag)) {
			earphone = Boolean.parseBoolean(value);
		} else if("fm".equals(tag)) {
			fm = Boolean.parseBoolean(value);
		} else if("battery".equals(tag)) {
			battery = Boolean.parseBoolean(value);
		} else if("temperature".equals(tag)) {
			temperature = Boolean.parseBoolean(value);
		} else if("telephony".equals(tag)) {
			telephony = Boolean.parseBoolean(value);
		} else if("memory".equals(tag)) {
			memory = Boolean.parseBoolean(value);
		} else if("sdCard".equals(tag)) {
			sdCard = Boolean.parseBoolean(value);
		} else if("simCard".equals(tag)) {
			simCard = Boolean.parseBoolean(value);
		} else if("simSignal".equals(tag)) {
			simSignal = Boolean.parseBoolean(value);
		} else if("gps".equals(tag)) {
			gps = Boolean.parseBoolean(value);
		} else if("bluetooth".equals(tag)) {
			bluetooth = Boolean.parseBoolean(value);
		} else if("wiFi".equals(tag)) {
			wiFi = Boolean.parseBoolean(value);
		} else if("microphone".equals(tag)) {
			microphone = Boolean.parseBoolean(value);
		} else if("tdCode".equals(tag)) {
			tdCode = Boolean.parseBoolean(value);
		} else if("buttonLight".equals(tag)) {
			buttonLight = Boolean.parseBoolean(value);
		} else if("hall".equals(tag)) {
			hall = Boolean.parseBoolean(value);
		} else if("otg".equals(tag)) {
			otg = Boolean.parseBoolean(value);
		} else if("notificationLight".equals(tag)) {
			notificationLight = Boolean.parseBoolean(value);
		} else if("gyroscope".equals(tag)) {
			gyroscope = Boolean.parseBoolean(value);
		} else if("magnetic".equals(tag)) {
			magnetic = Boolean.parseBoolean(value);
		} else if("lcdBlur".equals(tag)) {
			lcdBlur = Boolean.parseBoolean(value);
		} else if("gravitySensorNoZAxis".equals(tag)) {
			gravitySensorNoZAxis = Boolean.parseBoolean(value);
		} else if("defaultEnglish".equals(tag)) {
			defaultEnglish = Boolean.parseBoolean(value);
		} else if("ap_cfg_reeb_product_info_lid".equals(tag)) {
			AP_CFG_REEB_PRODUCT_INFO_LID = Integer.parseInt(value);
		} else if("ap_cfg_rdeb_file_wifi_lid".equals(tag)) {
			AP_CFG_RDEB_FILE_WIFI_LID = Integer.parseInt(value);
		} else if("ap_cfg_custom_file_gps_lid".equals(tag)) {
			AP_CFG_CUSTOM_FILE_GPS_LID = Integer.parseInt(value);
		}
	}
	private void setFeatureValue() {
		//backLed = pm.hasSystemFeature();
		touchPanel = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN);
		//lcd = pm.hasSystemFeature();
		gps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
		//battery = pm.hasSystemFeature();
		//key = pm.hasSystemFeature();
		//loundSpeaker = pm.hasSystemFeature();
		//earphone = pm.hasSystemFeature();
		microphone = pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
		//headset = pm.hasSystemFeature();
		wiFi = pm.hasSystemFeature(PackageManager.FEATURE_WIFI);
		bluetooth = pm.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH);
		//vibrator = pm.hasSystemFeature(PackageManager.);
		telephony = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);		
		//memory = pm.hasSystemFeature();
		gravitySensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
		lightSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_LIGHT);
		distanceSensor = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_PROXIMITY);
		//sdCard = pm.hasSystemFeature();
		mainCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
		subCamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT);
		//simCard = pm.hasSystemFeature();
		//fm = pm.hasSystemFeature();
		//calibration = pm.hasSystemFeature();
		//tdCode = pm.hasSystemFeature();
		flash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
		//proactiveFlash = pm.hasSystemFeature();
		//buttonLight = pm.hasSystemFeature();
		//hall = pm.hasSystemFeature();
		//otg = pm.hasSystemFeature(PackageManager.FEATURE_USB_HOST);
		gyroscope = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE);
		magnetic = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
		//notificationLight = pm.hasSystemFeature();
		//lcdBlur = pm.hasSystemFeature(PackageManager.FEATURE_USB_HOST);
	}
	private void setSingleTestItems(Context context) {
		singleTestTag.add(VersionInfo.TAG);
		singleTestTitle.add(context.getResources().getString(R.string.soft_version));
		singleTestClass.add(VersionInfo.class);
		singleTestTag.add(MotherboardInfo.TAG);
		singleTestTitle.add(context.getResources().getString(R.string.board_info));
		singleTestClass.add(MotherboardInfo.class);
		
		if(backLed) {
			singleTestTag.add(BackLED.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.BackLED));
			singleTestClass.add(BackLED.class);
		}
		if(lcd) {
			singleTestTag.add(LCD.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.lcd));
			singleTestClass.add(LCD.class);
		}
		if(touchPanel) {
			singleTestTag.add(TouchPanel.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.touch_panel));
			singleTestClass.add(TouchPanel.class);
		}
		if(key) {
			singleTestTag.add(KeyTestActivity.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.key));
			singleTestClass.add(KeyTestActivity.class);
		}
		if(vibrator) {
			singleTestTag.add(Vibration.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Vibrator));
			singleTestClass.add(Vibration.class);
		}
		if(gravitySensor) {
			singleTestTag.add(GravitySensor.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Gravity_Sensor));
			singleTestClass.add(GravitySensor.class);
		}
		if(calibration) {
			singleTestTag.add(SensorCalibration.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Calibration));
			singleTestClass.add(SensorCalibration.class);
		}
		if(distanceSensor) {
			singleTestTag.add(DistanceSensor.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Distance_Sensor));
			singleTestClass.add(DistanceSensor.class);
		}
		if(lightSensor) {
			singleTestTag.add(LightSensor.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Light_Sensor));
			singleTestClass.add(LightSensor.class);
		}
		if(magnetic) {
			singleTestTag.add(MSensor.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.magnetic));
			singleTestClass.add(MSensor.class);
		}
		if(gyroscope) {
			singleTestTag.add(Gyroscope.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.gyroscope));
			singleTestClass.add(Gyroscope.class);
		}
		if(hall) {
			singleTestTag.add(Hall.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.hall));
			singleTestClass.add(Hall.class);
		}		
		if(loundSpeaker) {
			singleTestTag.add(LoundSpeaker.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.LoundSpeaker));
			singleTestClass.add(LoundSpeaker.class);
		}		
		if(mainCamera) {
			singleTestTag.add(MainCamera.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Main_Camera));
			singleTestClass.add(MainCamera.class);
		}
		if(subCamera) {
			singleTestTag.add(SubCamera.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.sub_Camera));
			singleTestClass.add(SubCamera.class);
		}
		if(flash) {
			singleTestTag.add(FlashlightManager.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.key_Flash));
			singleTestClass.add(FlashlightManager.class);
		}	
		if(headset) {
			singleTestTag.add(HeadSet.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Headset));
			singleTestClass.add(HeadSet.class);
		}
		if(proactiveFlash) {
			singleTestTag.add(ProactiveFlashlightManager.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.proactive_flash));
			singleTestClass.add(ProactiveFlashlightManager.class);
		}	
		if(earphone) {
			singleTestTag.add(EarPhone.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Earphone));
			singleTestClass.add(EarPhone.class);
		}
		if(fm) {
			singleTestTag.add(FM.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.FM));
			singleTestClass.add(FM.class);
		}
		if(battery) {
			singleTestTag.add(Battery.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.battery));
			singleTestClass.add(Battery.class);
		}
		if(temperature) {
			singleTestTag.add(Temperature.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.temperature));
			singleTestClass.add(Temperature.class);
		}
		if(telephony) {
			singleTestTag.add(Telephony.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Telephony));
			singleTestClass.add(Telephony.class);
		}
		if(buttonLight) {
			singleTestTag.add(ButtonLightActivity.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.button_light));
			singleTestClass.add(ButtonLightActivity.class);
		}		
		if(notificationLight) {
			singleTestTag.add(NotificationLight.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.notification_light));
			singleTestClass.add(NotificationLight.class);
		}
		if(otg) {
			singleTestTag.add(Otg.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.otg));
			singleTestClass.add(Otg.class);
		}
		if(memory) {
			singleTestTag.add(Memory.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Memory));
			singleTestClass.add(Memory.class);
		}
		if(sdCard) {
			singleTestTag.add(SdCard.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.SDcard));
			singleTestClass.add(SdCard.class);
		}
		if(simCard) {
			singleTestTag.add(SimCard.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.SIM_Card));
			singleTestClass.add(SimCard.class);
		}		
		if(gps) {
			singleTestTag.add(GPS.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.gps));
			singleTestClass.add(GPS.class);
		}
		if(bluetooth) {
			singleTestTag.add(Bluetooth.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Bluetooth));
			singleTestClass.add(Bluetooth.class);
		}
		if(wiFi) {
			singleTestTag.add(WiFi.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.WiFi));
			singleTestClass.add(WiFi.class);
		}		
		if(microphone) {
			singleTestTag.add(MicrPhone.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Microphone));
			singleTestClass.add(MicrPhone.class);
		}
		if(microphone2) {
			singleTestTag.add(MicrPhone2.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.Microphone2));
			singleTestClass.add(MicrPhone2.class);
		}
		if(tdCode) {
			singleTestTag.add(CaptureActivity.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.TD_code));
			singleTestClass.add(CaptureActivity.class);
		}
		if(lcdBlur) {
			singleTestTag.add(LcdBlur.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.lcd_blur));
			singleTestClass.add(LcdBlur.class);
		}
		if(simSignal) {
			singleTestTag.add(SimSignal.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.SIM_Signal));
			singleTestClass.add(SimSignal.class);
		}
		if(fingerprint) {
			singleTestTag.add(FingerPrint.TAG);
			singleTestTitle.add(context.getResources().getString(R.string.FingerPrint));
			singleTestClass.add(FingerPrint.class);
		}		
	}	
	private void setAutoTestItems() {
		autoTestList.add(VersionInfo.TAG);
		autoTestClass.add(VersionInfo.class);
		autoTestList.add(MotherboardInfo.TAG);
		autoTestClass.add(MotherboardInfo.class);

		if(backLed) {
			autoTestList.add(BackLED.TAG);
			autoTestClass.add(BackLED.class);
		}
		if(lcd) {
			autoTestList.add(LCD.TAG);
			autoTestClass.add(LCD.class);
		}
		if(touchPanel) {
			autoTestList.add(TouchPanel.TAG);
			autoTestClass.add(TouchPanel.class);
		}
		if(key) {
			autoTestList.add(KeyTestActivity.TAG);
			autoTestClass.add(KeyTestActivity.class);
		}
		if(vibrator) {
			autoTestList.add(Vibration.TAG);
			autoTestClass.add(Vibration.class);
		}
		if(gravitySensor) {
			autoTestList.add(GravitySensor.TAG);
			autoTestClass.add(GravitySensor.class);
		}
		if(calibration) {
			autoTestList.add(SensorCalibration.TAG);
			autoTestClass.add(SensorCalibration.class);
		}
		if(distanceSensor) {
			autoTestList.add(DistanceSensor.TAG);
			autoTestClass.add(DistanceSensor.class);
		}
		if(lightSensor) {
			autoTestList.add(LightSensor.TAG);
			autoTestClass.add(LightSensor.class);
		}
		if(magnetic) {
			autoTestList.add(MSensor.TAG);
			autoTestClass.add(MSensor.class);
		}
		if(gyroscope) {
			autoTestList.add(Gyroscope.TAG);
			autoTestClass.add(Gyroscope.class);
		}
		if(hall) {
			autoTestList.add(Hall.TAG);
			autoTestClass.add(Hall.class);
		}		
		if(loundSpeaker) {
			autoTestList.add(LoundSpeaker.TAG);
			autoTestClass.add(LoundSpeaker.class);
		}		
		if(mainCamera) {
			autoTestList.add(MainCamera.TAG);
			autoTestClass.add(MainCamera.class);
		}
		if(subCamera) {
			autoTestList.add(SubCamera.TAG);
			autoTestClass.add(SubCamera.class);
		}
		if(flash) {
			autoTestList.add(FlashlightManager.TAG);
			autoTestClass.add(FlashlightManager.class);
		}
		if(earphone) {
			autoTestList.add(EarPhone.TAG);
			autoTestClass.add(EarPhone.class);
		}
		if(proactiveFlash) {
			autoTestList.add(ProactiveFlashlightManager.TAG);
			autoTestClass.add(ProactiveFlashlightManager.class);
		}
		if(fm) {
			autoTestList.add(FM.TAG);
			autoTestClass.add(FM.class);
		}
		if(battery) {
			autoTestList.add(Battery.TAG);
			autoTestClass.add(Battery.class);
		}
		if(temperature) {
			autoTestList.add(Temperature.TAG);
			autoTestClass.add(Temperature.class);
		}
		if(telephony) {
			autoTestList.add(Telephony.TAG);
			autoTestClass.add(Telephony.class);
		}
		if(buttonLight) {
			autoTestList.add(ButtonLightActivity.TAG);
			autoTestClass.add(ButtonLightActivity.class);
		}		
		if(notificationLight) {
			autoTestList.add(NotificationLight.TAG);
			autoTestClass.add(NotificationLight.class);
		}
		if(otg) {
			autoTestList.add(Otg.TAG);
			autoTestClass.add(Otg.class);
		}		
		if(microphone) {
			autoTestList.add(MicrPhone.TAG);
			autoTestClass.add(MicrPhone.class);
		}
		if(microphone2) {
			autoTestList.add(MicrPhone2.TAG);
			autoTestClass.add(MicrPhone2.class);
		}
		if(tdCode) {
			autoTestList.add(CaptureActivity.TAG);
			autoTestClass.add(CaptureActivity.class);
		}
		if(fingerprint) {
			autoTestList.add(FingerPrint.TAG);
			autoTestClass.add(FingerPrint.class);
		}		
		//This test does not require the automatic test
		//And you need to remove the judge in ResultHandle.java
		/**
		if(headset) {
			autoTestList.add(HeadSet.TAG);
			autoTestClass.add(HeadSet.class);
		}
		if(lcdBlur) {
			autoTestList.add(LcdBlur.TAG);
			autoTestClass.add(LcdBlur.class);
		}**/
		
		//Backtest
		
		if(gps) {
			backtestList.add(com.rgk.factory.mservice.GPS.TAG);
			backtestClass.add(com.rgk.factory.mservice.GPS.class);
		}
		if(wiFi) {
			backtestList.add(com.rgk.factory.mservice.WiFi.TAG);
			backtestClass.add(com.rgk.factory.mservice.WiFi.class);
		}
		if(bluetooth) {
			backtestList.add(com.rgk.factory.mservice.Bluetooth.TAG);
			backtestClass.add(com.rgk.factory.mservice.Bluetooth.class);
		}
		if(simCard) {
			backtestList.add(com.rgk.factory.mservice.SimCard.TAG);
			backtestClass.add(com.rgk.factory.mservice.SimCard.class);
		}
		if(memory) {
			backtestList.add(com.rgk.factory.mservice.Memory.TAG);
			backtestClass.add(com.rgk.factory.mservice.Memory.class);
		}
		if(sdCard) {
			backtestList.add(com.rgk.factory.mservice.SdCard.TAG);
			backtestClass.add(com.rgk.factory.mservice.SdCard.class);
		}
	}	
	public void readResources(Context ctx){
		Resources res = ctx.getResources();
		backLed = res.getBoolean(R.bool.backLed);
		lcd = res.getBoolean(R.bool.lcd);
		touchPanel = res.getBoolean(R.bool.touchPanel);
		key = res.getBoolean(R.bool.key);
		vibrator = res.getBoolean(R.bool.vibrator);
		gravitySensor = res.getBoolean(R.bool.gravitySensor);
		calibration = res.getBoolean(R.bool.calibration);
		distanceSensor = res.getBoolean(R.bool.distanceSensor);
		lightSensor = res.getBoolean(R.bool.lightSensor);
		loundSpeaker = res.getBoolean(R.bool.loundSpeaker);
		mainCamera = res.getBoolean(R.bool.mainCamera);
		subCamera = res.getBoolean(R.bool.subCamera);
		flash = res.getBoolean(R.bool.flash);
		proactiveFlash = res.getBoolean(R.bool.proactiveFlash);
		headset = res.getBoolean(R.bool.headset);
		earphone = res.getBoolean(R.bool.earphone);
		fm = res.getBoolean(R.bool.fm);
		battery = res.getBoolean(R.bool.battery);
		temperature = res.getBoolean(R.bool.temperature);
		telephony = res.getBoolean(R.bool.telephony);
		memory = res.getBoolean(R.bool.memory);
		sdCard = res.getBoolean(R.bool.sdCard);
		simCard = res.getBoolean(R.bool.simCard);
		simSignal = res.getBoolean(R.bool.simSignal);
		gps = res.getBoolean(R.bool.gps);
		bluetooth = res.getBoolean(R.bool.bluetooth);
		wiFi = res.getBoolean(R.bool.wiFi);
		microphone = res.getBoolean(R.bool.microphone);
		microphone2 = res.getBoolean(R.bool.microphone2);
		tdCode = res.getBoolean(R.bool.tdCode);
		buttonLight = res.getBoolean(R.bool.buttonLight);
		hall = res.getBoolean(R.bool.hall);
		otg = res.getBoolean(R.bool.otg);
		gyroscope = res.getBoolean(R.bool.gyroscope);
		magnetic = res.getBoolean(R.bool.magnetic);
		notificationLight = res.getBoolean(R.bool.notificationLight);
		lcdBlur = res.getBoolean(R.bool.lcdBlur);
		fingerprint = res.getBoolean(R.bool.fingerprint);		
		
		gravitySensorNoZAxis = res.getBoolean(R.bool.gravitySensorNoZAxis);
		AP_CFG_REEB_PRODUCT_INFO_LID = res.getInteger(R.integer.ap_cfg_reeb_product_info_lid);
		AP_CFG_RDEB_FILE_WIFI_LID = res.getInteger(R.integer.ap_cfg_rdeb_file_wifi_lid);
		AP_CFG_CUSTOM_FILE_GPS_LID = res.getInteger(R.integer.ap_cfg_custom_file_gps_lid);
		defaultEnglish = res.getBoolean(R.bool.defaultEnglish);
	}
	public void readXML() {
		try {
			FileInputStream is = new FileInputStream("/system/etc/boot_cit_factory.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLContentHandler handler = new XMLContentHandler();
			sp.parse(is, handler);
			is.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	class XMLContentHandler extends DefaultHandler {
		private StringBuilder builder;
		private String tagName = null;
		@Override
		public void startDocument() throws SAXException {
			builder = new StringBuilder();
		}     
		@Override
		public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
			if(localName.equals("bool") || localName.equals("integer")) {
				tagName = atts.getValue("name");
			}
			builder.setLength(0);  
		}        
		@Override           
		public void characters(char[] ch, int start, int length) throws SAXException {
			builder.append(ch, start, length);
		}  
		@Override            
		public void endElement(String uri, String localName, String name) throws SAXException {
			if(localName.equals("bool")||localName.equals("integer")) {
				setInitValue(tagName, builder.toString());
			}
		}
	}
}
