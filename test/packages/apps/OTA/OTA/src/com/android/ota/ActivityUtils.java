/************************************************************************
 History:
 1. chuiguo.zeng@ragentek.com 2014.02.26 BUG_ID:none
 Description: Add the support that upgrade with both internal and external SD when the SD solution is Shared SD.
 ************************************************************************/

package com.android.ota;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import android.os.SystemProperties;
import android.os.IBinder;
import android.os.Build;

//add bug_id:none zengchuiguo 20140226 (start)
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.os.Environment;
//add bug_id:none zengchuiguo 20140226 (end)

/**
 *
 * @author yulong.liang
 * @date 2012-5-21 9:52:11
 */
public class ActivityUtils {
    public static final int quency_days=3;//update system,Frequency

    private static final int AP_CFG_REEB_PRODUCT_NEW_INFO_LID = 46;  //92 kk should be  45

    //add QELS-4235 wanglu 20130118  start
    //modify to MTK liangyulong 20130312
    public static String getInternalVersion() {
        String internalVersion = SystemProperties.get("ro.internal.version", "");
        String rgkVersion = SystemProperties.get("ro.internal.version.rgk", "");
        if(!rgkVersion.trim().equals("")) {
            internalVersion = rgkVersion;
        }
        if(internalVersion.trim().equals("")){
            String externalVersion = SystemProperties.get("ro.custom.build.version", "");
            return externalVersion.trim();
        }
        else{
            return internalVersion.trim();
        }

    }
    //add QELS-4235 wanglu 20130118 end

    private static final String TAG="ActivityUtils";
    public static String setParameter(Context context,String mkey,String mValue){
        try{
            SharedPreferences shared = context.getSharedPreferences("soft", Context.MODE_PRIVATE);
            Editor edit = shared.edit();
            edit.putString(mkey, mValue);
            edit.commit();
            shared = null;
        }catch(Exception e){
            return e.toString();
        }
        return "";
    }
    public static String getParameter(Context context,String mkey){
        try{
            SharedPreferences shared = context.getSharedPreferences("soft", Context.MODE_PRIVATE);
            String mValue=shared.getString(mkey, "");//不存在时，默认为""
            shared = null;
            return mValue;
        }catch(Exception e){
            return "";
        }
    }
    //DCELWYL-74 modify the download request type  20121210 yulong.liang start
    public static String getServerUrl(Context context){
        String MAIN_URL="";
        String TEST_URL="";
        try{
            MAIN_URL=context.getString(R.string.MAIN_URL);
            TEST_URL=context.getString(R.string.TEST_URL);
            int inState=android.provider.Settings.System.getInt(context.getContentResolver(), "ota_server_url",0);
            if(inState==0){
                return MAIN_URL;
            }
            else{
                return TEST_URL;
            }
        }catch(Exception e){
            return MAIN_URL;
        }
    }
    /**

     * 两个时间之间相差距离多少天

     * @param beginTime 时间参数 1：

     * @param endTime 时间参数 2：

     * @return 相差天数

     */

    public static long getDistanceDays(String str1, String str2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Date beginTime;;

        Date endTime;

        long days=0;

        try {

            beginTime = df.parse(str1);

            endTime = df.parse(str2);

            long time1 = beginTime.getTime();

            long time2 = endTime.getTime();

            long diff ;

            if(time1<time2) {

                diff = time2 - time1;

            } else {

                diff = time1 - time2;

            }

            days = diff / (1000 * 60 * 60 * 24);

        } catch (ParseException e) {

            e.printStackTrace();

        }

        return days;

    }
    /**

     　　* 获取存储卡的剩余容量，单位为M

     　　*@param filePath

     　　*@return availableSpare

     　　**/

    public static long getAvailableStore(String filePath) {
        StatFs statFs = new StatFs(filePath);
        long blocSize = statFs.getBlockSize();
        long availaBlock = statFs.getAvailableBlocks();
        long availableSpare = availaBlock * blocSize;
        return availableSpare;
    }
    //网络是否通畅
    public static boolean haveInternet(Context context){
        NetworkInfo info=((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if(info==null || !info.isConnected()){
            return false;
        }
        if(info.isRoaming()){
            //here is the roaming option you can change it if you want to disable internet while roaming, just return false
            return true;
        }
        return true;
    }
    //sdcard 是否可用
    public static boolean isExistSdcard(){
        if(android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

    //add bug_id:none zengchuiguo 20140226 (start)
    public static boolean isExternalSDCardExist(Context context) {
        StorageManager storageManager = StorageManager.from(context);
        StorageVolume[] volumes = storageManager.getVolumeList();
        for (int i = 0; i < volumes.length; i++) {
            if (volumes[i].isRemovable() && Environment.MEDIA_MOUNTED.equals(
                    storageManager.getVolumeState(volumes[i].getPath()))) {
                return true;
            }
        }
        return false;
    }
    //add bug_id:none zengchuiguo 20140226 (end)

///////////////// start 校验文件 //////////////////////////////////

    /***
     *
     * 下载完毕，进行校验,针对 差分包
     */
    private static final File DEFAULT_KEYSTORE =
            new File("/system/etc/security/otacerts.zip");
    public static void verifyPackage(File packageFile,File deviceCertsZipFile,String TAG)
            throws IOException, GeneralSecurityException {
//        long fileLen = packageFile.length();
//        RandomAccessFile raf = new RandomAccessFile(packageFile, "r");
//        try {
//            int lastPercent = 0;
//            long lastPublishTime = System.currentTimeMillis();
//
//            raf.seek(fileLen - 6);
//            byte[] footer = new byte[6];
//            raf.readFully(footer);
//
//            if (footer[2] != (byte)0xff || footer[3] != (byte)0xff) {
//                throw new SignatureException("no signature in file (no footer)");
//            }
//
//            int commentSize = (footer[4] & 0xff) | ((footer[5] & 0xff) << 8);
//            int signatureStart = (footer[0] & 0xff) | ((footer[1] & 0xff) << 8);
//            Log.v(TAG, String.format("comment size %d; signature start %d",
//                    commentSize, signatureStart));
//
//            byte[] eocd = new byte[commentSize + 22];
//            raf.seek(fileLen - (commentSize + 22));
//            raf.readFully(eocd);
//
//            // Check that we have found the start of the
//            // end-of-central-directory record.
//            if (eocd[0] != (byte)0x50 || eocd[1] != (byte)0x4b ||
//                    eocd[2] != (byte)0x05 || eocd[3] != (byte)0x06) {
//                throw new SignatureException("no signature in file (bad footer)");
//            }
//
//            for (int i = 4; i < eocd.length-3; ++i) {
//                if (eocd[i  ] == (byte)0x50 && eocd[i+1] == (byte)0x4b &&
//                        eocd[i+2] == (byte)0x05 && eocd[i+3] == (byte)0x06) {
//                    throw new SignatureException("EOCD marker found after start of EOCD");
//                }
//            }
//        }
//        finally {
//            raf.close();
//        }
    }
    /** @return the set of certs that can be used to sign an OTA package. */
    private static HashSet<Certificate> getTrustedCerts(File keystore)
            throws IOException, GeneralSecurityException {
        HashSet<Certificate> trusted = new HashSet<Certificate>();
        if (keystore == null) {
            keystore = DEFAULT_KEYSTORE;
        }
        ZipFile zip = new ZipFile(keystore);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                trusted.add(cf.generateCertificate(zip.getInputStream(entry)));
            }
        } finally {
            zip.close();
        }
        return trusted;
    }

    /***
     * 校验完整包 （只校验文件夹是否损坏）
     * @param contx
     * @return
     */
    public static void verifyCompletePackage(File packageFile,File deviceCertsZipFile,String TAG)
            throws IOException, GeneralSecurityException {
        long fileLen = packageFile.length();
        RandomAccessFile raf = new RandomAccessFile(packageFile, "r");
        try {
            raf.seek(fileLen - 6);
            byte[] footer = new byte[6];
            raf.readFully(footer);
            if (footer[2] != (byte)0xff || footer[3] != (byte)0xff) {
                throw new SignatureException("no signature in file (no footer)");
            }
        }
        finally {
            raf.close();
        }
    }
    ///////////////// end 校验文件 //////////////////////////////////

    //获取手机序列号 IMEI
    public static String getPhoneIMEI(Context contx){
        TelephonyManager tm=(TelephonyManager)contx.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }
    //获取手机硬件信息
    public static String getHardwareVersion(){
        return "P2";
    }
    //获取网络类型
    public static String CheckNetworkState(Context contx){
        ConnectivityManager manager=(ConnectivityManager) contx.getSystemService(Context.CONNECTIVITY_SERVICE);
        State mobile=manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        State wifi=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if(mobile==State.CONNECTED||mobile==State.CONNECTING){
            return "mobile";
        }
        if(wifi==State.CONNECTED||wifi==State.CONNECTING){
            return "wifi";
        }
        return "";

    }
    //自动更新
    public static void setNextNotify(Context context,Intent intent,boolean isCancel,int days){
        try{
            AlarmManager  am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            if(!isCancel){
                am.cancel(pi);
            }
            else{
                long currTime=System.currentTimeMillis();
                setParameter(context, "currTime", currTime+"");
                Log.d(TAG, "currTime="+currTime);
                long times=currTime+(60*60)*1000*24*days;
                am.set(AlarmManager.RTC_WAKEUP,times, pi); //  (60*60)*1000
            }
        }catch(Exception e){
            Log.d(TAG, "Error:"+e.toString());
        }

    }
    // add BUG_ID:QYLE-2297 20120711 yulong.liang start
    //下载完毕后，延迟更新重启机器
    public static  void delayNotify(Context context,Intent intent,boolean isCancel){
        try{
            AlarmManager  am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            if(!isCancel){
                am.cancel(pi);
            }
            else{
                long currTime=System.currentTimeMillis();
                long times=currTime+(60*60)*1000;
                am.set(AlarmManager.RTC_WAKEUP,times, pi);
            }
        }
        catch(Exception e){
            Log.d(TAG, "Error:"+e.toString());
        }
    }
    // add BUG_ID:QYLE-2297 20120711 yulong.liang end
    public static String getSdcardPath(){
        return "/sdcard/";
    }
    //add download statistics 20121023 yulong.liang end
    //Read data from nvram
    public static int  readNVData(int flag) {
        IBinder binder = ServiceManager.getService("NvRAMAgent");
        int b = -1;
        if(binder!=null){
            NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
            byte[] buff = null;
            try {
                buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read buffer from nvram
                if(buff!=null){
                    b = buff[flag];
                    if(b==-1)b=0;
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if(buff!=null){
                for(int i=0;i<buff.length;i++){
                    System.out.println(i+"=i:"+buff[i]);
                }
            }

        }
        System.out.println("b:"+b);
        return b;
    }
    //Write data to nvram
    public static int  writeNVData(int indexNum,int indexValue) {
        IBinder binder = ServiceManager.getService("NvRAMAgent");
        if(binder!=null){
            NvRAMAgent agent = NvRAMAgent.Stub.asInterface(binder);
            byte[] buff = null;
            try {
                buff = agent.readFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID);// read buffer from nvram
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            buff[indexNum]=(byte) indexValue;
		/*
		for(int i=0;i<10;i++){
		       System.out.println(i+"=fi:"+buff[i]);
		}
		*/
            int flag=0;
            try{
                flag = agent.writeFile(AP_CFG_REEB_PRODUCT_NEW_INFO_LID,buff);
            }catch(Exception e){
                e.printStackTrace();
            }
            System.out.println("flag="+flag);
            return flag;
        }
        return 0;
    }
    /***
     * register cellphone
     * sParam
     * 2:Already registered or Registration failed
     * 1:Successfully registered server-side
     */
    public static void registerCellPhone(String sParam){
        if((!sParam.trim().equals("2"))&&(sParam.trim().equals("1"))){
            int i0=ActivityUtils.readNVData(250);
            if(i0==0){
                ActivityUtils.writeNVData(250,1);
            }
        }
    }
    public static String getRegisterParam(Context context){
/** delete for Sales ,liangyulong 20130813
 int inState=android.provider.Settings.System.getInt(context.getContentResolver(), "ota_server_url",0);
 int regParm=readNVData(250);
 if(inState==1){
 return regParm+"";//local test
 }
 if(regParm==0){//not register
 int callP1=readNVData(251);
 if(callP1>=60){
 return regParm+"";
 }
 else{
 return beta;
 }
 }
 else{
 return regParm+"";
 }
 **/
        return beta;
    }
    public static String getNeedCompareNumber(){
        String snumber=Build.DISPLAY;
        snumber = snumber.substring(0, snumber.indexOf("P2")-1);
        snumber=snumber.substring(snumber.lastIndexOf(".")+1,snumber.length());
        return snumber;

    }
    public static final String beta="v1.0";
}
