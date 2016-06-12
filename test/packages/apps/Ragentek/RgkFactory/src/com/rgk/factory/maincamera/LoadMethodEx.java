package com.rgk.factory.maincamera;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class LoadMethodEx {

	public static Object Load(String cName, String MethodName, Object[] params) {
		Object retObject = null;
		try {
			Class cls = Class.forName(cName);

			Constructor ct = cls.getDeclaredConstructor(null);
			ct.setAccessible(true);
			Object obj = ct.newInstance(null);

			Class paramTypes[] = getParamTypes(cls, MethodName);

			Method meth = cls.getDeclaredMethod(MethodName, paramTypes);

			meth.setAccessible(true);
			System.out.println("Load");
			retObject = meth.invoke(obj, params);
			System.out.println(retObject);
		} catch (Exception e) {
			System.out.println(e);
		}
		return retObject;
	}

	public static void load() {
//		IConnectivityManager mService = null;
//		Method method1 = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
//		IBinder binder = (IBinder) method1.invoke(null, new Object[] { "connectivity" });
//		IConnectivityManager localhardwareservice = IConnectivityManager.Stub .asInterface(binder);
	}

	public static Class[] getParamTypes(Class cls, String mName) {
		Class[] cs = null;
		Method[] mtd = cls.getDeclaredMethods();
		for (int i = 0; i < mtd.length; i++) {
			if (!mtd[i].getName().equals(mName)) {
				continue;
			}
			cs = mtd[i].getParameterTypes();
		}
		System.out.println("getParamTypes " + cs);
		return cs;
	}
}
