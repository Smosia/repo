package com.android.settings.emergencyassistant;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.settings.R;

import android.telephony.SubscriptionManager;

public class SendMessageReceiver extends BroadcastReceiver {

	public static final String SEND_MESSAGE_START = "com.android.settings.emergencyassistant.START";
	/*
	 * private static final String LOCATION_URI_PART1 =
	 * "http://api.map.baidu.com/marker?location="; private static final String
	 * LOCATION_URI_PART2 = "&title="; private static final String
	 * LOCATION_URI_PART3 = "&content="; private static final String
	 * LOCATION_URI_PART4 = "&output=html";
	 */

	// String url1 =
	// "http://maps.googleapis.com/maps/api/geocode/json?latlng=30.6096780,114.3579530&sensor=false&language=zh";
	private static final String LOCATION_URI_PART1 = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
	private static final String LOCATION_URI_PART2 = "&sensor=false&language=";

	private static final String TAG = "Settings/SendMessageReceiver";
	private final boolean DEBUG = true;

	private static EmergencyAssistantOperateDB mOperateDB;
	private List<ContactInfo> mEmergencyContactList = new ArrayList<ContactInfo>();
	private List<String> mNumberList = new ArrayList<String>();
	private boolean shouldSendMessage = false;
	private static boolean sendMessageWithLocation = false;
	// A: JWBLJ-407 chenlong.guo 20151022 (START) @{
	private static boolean sendMessageWithTextLocation = false;
	// A: JWBLJ-407 chenlong.guo 20151022 (END) }@
	private static boolean onlyOnce = false;
	// private static boolean needDiableDataConnection = false;
	private static String originGpsState = null;
	private Context mContext;
	private static Thread mGetLocationThread = null;
	private static String locationStr = null;
	private static String locationUriStr = null;
	private static LocationManager locationManager;
	private ConnectivityManager mCm;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log("SendMessageReceiver onReceive!");
		mContext = context;
		mCm = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		String action = intent.getAction();
		onlyOnce = intent.getBooleanExtra("only_once", false);
		boolean cleanAfterSend = intent.getBooleanExtra("clean_after_send",
				false);
		boolean cleanBeforeSend = intent.getBooleanExtra("clean_before_send",
				false);
		if (cleanBeforeSend) {
			cleanEverything();
			return;
		}

		String content = "";
		if (mOperateDB == null) {
			mOperateDB = new EmergencyAssistantOperateDB(mContext);
		}
		mEmergencyContactList = mOperateDB.getContactInfoList();
		for (int i = 0; i < mEmergencyContactList.size(); i++) {
			mNumberList.add(i, mEmergencyContactList.get(i).contactNumber);
			if (!shouldSendMessage && mNumberList.get(i) != null
					&& !mNumberList.get(i).trim().equals("")) {
				shouldSendMessage = true;
			}
		}
		Log("shouldSendMessage=" + shouldSendMessage);
		if (!shouldSendMessage) {
			return;
		}

		if (SEND_MESSAGE_START.equals(action)) {
			try {
				content = readMsgContent(context,
						EmergencyEditMsgActivity.FILE_NAME);
			} catch (Exception e) {
				Log.e(TAG, "SendMessageReceiver readMsgContent() exception!");
			} finally {
				if (content.isEmpty()) {
					content = context.getString(R.string.default_emergency_msg);
				}
			}

			toggleGps(mContext, true);
			if (mGetLocationThread == null) {
				mGetLocationThread = new Thread(new GetLocationRunnable());
				mGetLocationThread.start();
			}

			if (locationStr != null && !locationStr.isEmpty()) {
				/*
				 * String uriPart2 =
				 * context.getString(R.string.emergency_title); String uriPart3
				 * = context.getString(R.string.emergency_message_dispatched);
				 * locationUriStr = LOCATION_URI_PART1 + locationStr +
				 * LOCATION_URI_PART2 + uriPart2 + LOCATION_URI_PART3 + uriPart3
				 * + LOCATION_URI_PART4; content += locationUriStr;
				 */
				String locationPrefix = context
						.getString(R.string.location_prefix);
				content += "\n";
				content += locationPrefix;
				content += locationStr;
			}

			
            int subId = SmsManager.getDefaultSmsSubscriptionId();
            if (subId < 0) {
                int[] list = SubscriptionManager.from(context).getActiveSubscriptionIdList();
                if(list != null && list.length > 0){
                    subId = list[0];
                }
            }
            if (subId > 0) {
                SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(subId);
                ArrayList<String> contents = smsManager.divideMessage(content);
                for (int i = 0; i < mNumberList.size(); i++) {
                    smsManager.sendMultipartTextMessage(mNumberList.get(i), null,
                            contents, null, null);
                }
            }

		}

		if (cleanAfterSend) {
			cleanEverything();
		}
		Log("SendMessageReceiver : onReceive exit!");
	}

	public String readMsgContent(Context context, String filename)
			throws Exception {
		Log("SendMessageReceiver : readMsgContent");
		FileInputStream inStream = context.openFileInput(filename);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;

		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		String content = new String(data);
		inStream.close();
		outStream.close();
		Log("SendMessageReceiver : readMsgContent exit!");
		if (content == null)
			return "";
		return content;
	}

	private String getLoctionStr() {
		return locationStr == null ? "" : locationStr;
	}

	/**
	 * get the state of GPS location.
	 * 
	 * @param context
	 * @return true if enabled.
	 */
	private static boolean getGpsState(Context context) {
		Log.d(TAG, "SendMessageReceiver : getGpsState");
		ContentResolver resolver = context.getContentResolver();
		boolean open = Settings.Secure.isLocationProviderEnabled(resolver,
				LocationManager.GPS_PROVIDER);
		if (originGpsState == null) {
			originGpsState = open ? "open" : "close";
		}
		Log.d(TAG, "getGpsState = " + open);
		Log.d(TAG, "SendMessageReceiver : getGpsState exit!");
		return open;
	}

	/**
	 * Toggles the state of GPS. Actually turn on the gps
	 * 
	 * @param context
	 */
	private void toggleGps(Context context, boolean flag) {
		Log("SendMessageReceiver : toggleGps enter!");
		ContentResolver resolver = context.getContentResolver();
		boolean enabled = getGpsState(context);
		if (enabled ^ flag) {
			Settings.Secure.setLocationProviderEnabled(resolver,
					LocationManager.GPS_PROVIDER, flag);
		}
		Log("SendMessageReceiver : toggleGps exit!");
	}

	/**
	 * update location
	 * 
	 * @param location
	 */
	private void updateNewLocation(Location location) {
		Log("SendMessageReceiver : updateNewLocation enter!");
		Log("updateNewLocation location=" + location);

		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			String lan = Locale.getDefault().getLanguage();

			locationUriStr = LOCATION_URI_PART1 + lat + "," + lng
					+ LOCATION_URI_PART2 + lan;
			if (mCm.getActiveNetworkInfo() == null) {
				/*
				 * mCm.startUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE,
				 * ""); mCm.setMobileDataEnabled(true); needDiableDataConnection
				 * = true;
				 * Log("Now no data connection, so enable mobile data connection"
				 * );
				 */
				locationStr = "lat:" + lat + ", lng:" + lng;
				// A: JWBLJ-407 chenlong.guo 20151022 (START) @{
				if (!sendMessageWithTextLocation && !sendMessageWithLocation
						&& mContext != null && locationStr != null) {
					Log("Send message from location and locationStr:"
							+ locationStr);
					Intent intent = new Intent(
							"com.android.settings.emergencyassistant.START");
					if (onlyOnce) {
						intent.putExtra("clean_after_send", true);
					}
					mContext.sendBroadcast(intent);
					sendMessageWithTextLocation = true;
				}
				// A: JWBLJ-407 chenlong.guo 20151022 (END) }@
			} else {
				new ReadHttpGet().execute(locationUriStr);
				Log("data connection is ok now");
			}
		}

		Log("SendMessageReceiver : updateNewLocation exit!");

	}

	// location listener
	private final LocationListener locationListener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// A: JWBLJ-407 chenlong.guo 20151022 (START) @{
			Log.d(TAG, "locationListener > onStatusChanged");
			Log.d(TAG, "provider:" + provider);
			Log.d(TAG, "status:" + status);
			Log.d(TAG, "extras:" + extras);
			switch (status) {
			case LocationProvider.AVAILABLE:
				Log.d(TAG, "status-AVAILABLE");
				break;
			case LocationProvider.OUT_OF_SERVICE:
				Log.d(TAG, "status-OUT_OF_SERVICE");
				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Log.d(TAG, "status-TEMPORARILY_UNAVAILABLE");
				break;
			default:
				break;
			}
			// A: JWBLJ-407 chenlong.guo 20151022 (END) }@
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
			updateNewLocation(location);
		}

	};
	// A: JWBLJ-407 chenlong.guo 20151022 (START) @{
	GpsStatus.Listener listener = new GpsStatus.Listener() {

		@Override
		public void onGpsStatusChanged(int event) {
			switch (event) {
			case GpsStatus.GPS_EVENT_FIRST_FIX:
				Log.d(TAG, "listener > onGpsStatusChanged, GPS_EVENT_FIRST_FIX");
				break;
			case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
				Log.d(TAG,
						"listener > onGpsStatusChanged, GPS_EVENT_SATELLITE_STATUS");
				if (null == locationManager) {
					Log.d(TAG, "locationManager is NULL");
					break;
				}
				GpsStatus gpsStatus = locationManager.getGpsStatus(null);
				int maxSatellites = gpsStatus.getMaxSatellites();
				Iterator<GpsSatellite> iters = gpsStatus.getSatellites()
						.iterator();
				int count = 0;
				while (iters.hasNext() && count <= maxSatellites) {
					GpsSatellite s = iters.next();
					count++;
				}

				Log.d(TAG, "GpsSatellite, count:" + count);
				break;
			case GpsStatus.GPS_EVENT_STARTED:
				Log.d(TAG, "listener > onGpsStatusChanged, GPS_EVENT_STARTED");
				break;
			case GpsStatus.GPS_EVENT_STOPPED:
				Log.d(TAG, "listener > onGpsStatusChanged, GPS_EVENT_STOPPED");
				break;
			default:
				break;
			}
		}
	};

	// A: JWBLJ-407 chenlong.guo 20151022 (END) }@

	class GetLocationRunnable implements Runnable {
		public void run() {
			Log.d(TAG, "SendMessageReceiver : GetLocationRunnable run enter!");
			Looper.prepare();
			Log("run GetLocationRunnable!");

			String contextService = Context.LOCATION_SERVICE;

			locationManager = (LocationManager) mContext
					.getSystemService(contextService);
			// locationManager.setTestProviderEnabled("gps",true);

			// String provider = LocationManager.GPS_PROVIDER;

			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);//
			criteria.setAltitudeRequired(false);//
			criteria.setBearingRequired(false);//
			criteria.setCostAllowed(true);//
			criteria.setPowerRequirement(Criteria.POWER_LOW);//
			String provider = locationManager.getBestProvider(criteria, true);

			Log("provider=" + provider);

			Location location = locationManager.getLastKnownLocation(provider);

			// 30000: every 30 seconds
			// 5: every 5 meters change.
			locationManager.requestLocationUpdates(provider, 30000, 5,
					locationListener, Looper.myLooper());
			// A: JWBLJ-407 chenlong.guo 20151022 (START) @{
			locationManager.addGpsStatusListener(listener);
			// A: JWBLJ-407 chenlong.guo 20151022 (END) }@
			updateNewLocation(location);
			Looper.loop();

			Log("run GetLocationRunnable! exit");
			Log("SendMessageReceiver : GetLocationRunnable run exit!");
		}
	}

	// clean everything.
	private void cleanEverything() {
		Log.d(TAG, "SendMessageReceiver : cleanEverything enter!");
		Log("cleanEverything removeUpdates out");
		if (locationManager != null) {
			Log("removeUpdates in");
			locationManager.removeUpdates(locationListener);
			// A: JWBLJ-407 chenlong.guo 20151022 (START) @{
			locationManager.removeGpsStatusListener(listener);
			// A: JWBLJ-407 chenlong.guo 20151022 (END) }@
			locationManager = null;
		}

		toggleGps(mContext, "open".equals(originGpsState) ? true : false);

		if (mGetLocationThread != null) {
			mGetLocationThread.interrupt();
			mGetLocationThread = null;
		}

		/*
		 * if (mCm != null && needDiableDataConnection) {
		 * mCm.stopUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "");
		 * mCm.setMobileDataEnabled(false); needDiableDataConnection = false; }
		 */

		locationStr = null;
		locationUriStr = null;
		originGpsState = null;

		if (mOperateDB != null) {
			mOperateDB = null;
		}
		Log("endMessageReceiver : cleanEverything exit!");
	}

	private void Log(String log) {
		if (DEBUG) {
			Log.d(TAG, log);
		}
	}

	class ReadHttpGet extends AsyncTask<Object, Object, Object> {
		@Override
		protected Object doInBackground(Object... params) {

			// TODO Auto-generated method stub

			Log("doInbackground,url:" + params[0].toString());
			HttpGet httpRequest = new HttpGet(params[0].toString());

			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = httpClient.execute(httpRequest);

				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					Log.d(TAG, "HttpStatus.SC_OK");
					String strResult = EntityUtils.toString(httpResponse
							.getEntity());
					return strResult;
				} else {
					return "request error";
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled(Object result) {
			// TODO Auto-generated method stub
			super.onCancelled(result);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub

			super.onPostExecute(result);
			// Log("onPostExecute, result: " + result.toString());

			try {
				if (result != null) {
                    Log("onPostExecute, result: " + result.toString());
					JSONObject jsonObject = new JSONObject(result.toString());
					JSONArray jsonArray = jsonObject.getJSONArray("results");
					JSONObject placemarkObj = jsonArray.getJSONObject(0);

					JSONArray adressArray = placemarkObj
							.getJSONArray("address_components");
					locationStr = adressArray.getJSONObject(3).getString(
							"long_name")
							+ adressArray.getJSONObject(2).getString(
									"long_name")
							+ adressArray.getJSONObject(1).getString(
									"long_name")
							+ adressArray.getJSONObject(0).getString(
									"long_name");

					if (!sendMessageWithLocation && mContext != null
							&& locationStr != null) {
						Log("Send message from http request and locationStr:"
								+ locationStr);
						Intent intent = new Intent(
								"com.android.settings.emergencyassistant.START");
						if (onlyOnce) {
							intent.putExtra("clean_after_send", true);
						}
						mContext.sendBroadcast(intent);
						sendMessageWithLocation = true;
					}
				} else {
                    Log("onPostExecute, result is NULL" );
                }
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}
	}

}
