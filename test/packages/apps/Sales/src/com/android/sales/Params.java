package com.android.sales;

import com.mediatek.telephony.TelephonyManagerEx;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.CellLocation;
import android.content.Context;

public class Params {
	public String IMEI;
	public String IMSI;
	public String ICCID;
	public String MSISDN;
	public String MNC;
	public String NCLS;
	public String CellID;
	public String LAC;

	public static Params getParams(Context context, int simId) {		
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);	
		Params result = new Params();
		result.IMEI = tm.getDeviceId(simId);
		result.IMSI = tm.getSubscriberId(simId);
		
		if ((result.IMSI != null) && (result.IMSI.length() > 0)) {
		    TelephonyManagerEx tmEx = TelephonyManagerEx.getDefault();
			result.ICCID = tmEx.getSimSerialNumber(simId);
			result.MSISDN = tmEx.getLine1Number(simId);
			result.MNC = tmEx.getNetworkOperator(simId);
			result.NCLS = getNetworkTypeString(tmEx.getNetworkType(simId));
			
			CellLocation cellLocation = tm.getCellLocation();

			result.CellID = null;
			result.LAC = null;
			if (cellLocation instanceof GsmCellLocation) {
				GsmCellLocation localGsmCellLocation = (GsmCellLocation) cellLocation;
				int cid = localGsmCellLocation.getCid();
				int lac = localGsmCellLocation.getLac();
				if (cid != -1)
					result.CellID = String.valueOf(cid);
				if (lac != -1)
					result.LAC = String.valueOf(lac);
			} else if (cellLocation instanceof CdmaCellLocation) {
				CdmaCellLocation localCdmaCellLocation = (CdmaCellLocation) cellLocation;
				int id = localCdmaCellLocation.getBaseStationId();
				int longitude = localCdmaCellLocation.getBaseStationLongitude();
				int latitude = localCdmaCellLocation.getBaseStationLatitude();
				if (id != -1)
					result.CellID = String.valueOf(id);
				if ((longitude != -1) && (latitude != -1)) {
					result.LAC = String.format("%d:%d", longitude, latitude);
				}
			}
		}
		return result;
	}

	private static String getNetworkTypeString(int paramInt) {
		switch (paramInt) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
			return "GSM";
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_HSUPA:
		case TelephonyManager.NETWORK_TYPE_HSPA:
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return "WCDMA";
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_1xRTT:
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			return "CDMA";
		case TelephonyManager.NETWORK_TYPE_LTE:
			return "LTE";
		case TelephonyManager.NETWORK_TYPE_IDEN:
		case TelephonyManager.NETWORK_TYPE_EHRPD:
		default:
			return null;
		}
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public String getIMSI() {
		return IMSI;
	}

	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}

	public String getICCID() {
		return ICCID;
	}

	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}

	public String getMSISDN() {
		return MSISDN;
	}

	public void setMSISDN(String mSISDN) {
		MSISDN = mSISDN;
	}

	public String getMNC() {
		return MNC;
	}

	public void setMNC(String mNC) {
		MNC = mNC;
	}

	public String getNCLS() {
		return NCLS;
	}

	public void setNCLS(String nCLS) {
		NCLS = nCLS;
	}

	public String getCellID() {
		return CellID;
	}

	public void setCellID(String cellID) {
		CellID = cellID;
	}

	public String getLAC() {
		return LAC;
	}

	public void setLAC(String lAC) {
		LAC = lAC;
	}
}
