package com.rgk.phonemanager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.rgk.phonemanager.TrashFastExpandableListViewAdapter.OnChildItemClickListener;
import com.rgk.phonemanager.TrashFastExpandableListViewAdapter.OnGroupItemCheckedClickListener;
import com.rgk.phonemanager.util.APKFileItem;
import com.rgk.phonemanager.util.AppUtil;
import com.rgk.phonemanager.util.BaseActivity;
import com.rgk.phonemanager.util.FileUtils;
import com.rgk.phonemanager.util.NotificationUtil;
import com.rgk.phonemanager.util.TrashItem;
import com.rgk.phonemanager.view.util.Pie;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ScannerAndCleaner extends BaseActivity implements OnClickListener,
		OnGroupExpandListener, OnChildItemClickListener,
		OnGroupItemCheckedClickListener{
	
	private static final String TAG = "ScannerAndCleaner";

	public static boolean mPain = false;
	
	private static boolean mNeedClearAllCache = false;

	private Pie mPie;

	private int mBackground0 = 0;
	private int mBackground1 = 0;
	private int mBackground2 = 0;
	private int mBackground3 = 0;
	private int mBackground4 = 0;

	private int mAnimaColor1 = 0;
	private int mAnimaColor2 = 0;
	private int mAnimaColor3 = 0;
	private int mAnimaColor4 = 0;

	private int mHiddenNumber = 0;

	private Button mOnekeyClear;
	private ExpandableListView mExpandableListView;
	
	private static final int INDEX_FOR_USELESSAPK = 0;
	private static final int INDEX_FOR_MEMORYTRASH = 1;
	private static final int INDEX_FOR_SERVICETRASH = 2;
	private static final int INDEX_FOR_APPCACHE = 3;

	private static final int MSG_EXAND_PIE_ITEM = 4;
	private static final int MSG_SHRINK_PIE_ITEM = 5;
	private static final int MSG_UPDATE_SIZE = 6;
	private static final int MSG_END_TRASH_SEARCH = 7;
	private static final int MSG_UPDATE_TRASH_CLEAR = 8;

	private static final String INDEX_FOR_PIE = "index";

	private List<TrashItem> mGroupTypes;
	private List<List<TrashItem>> mGroupMembers;
	
	private List<TrashItem> mAppCache;
	private List<TrashItem> mRemoveTrashItem = new ArrayList<TrashItem>();
	private List<TrashItem> mServiceTrash;
	private List<TrashItem> mUselessApk;
	private List<TrashItem> mMemoryTrash;
	
	private List<APKFileItem> mApkFileList;
	
	private long mApkSize = 0L;
	private int mApkNum = 100;
	
	private long mCacheSize = 0L;
	private int mCacheNum = 100;
	
	private long mMemorySize = 0L;
	private int mMemoryNum = 100;
	
	private long mServiceSize = 0L;
	private int mServiceNum = 100;
	
	private long mSystemCacheSize = 0L;
	
	private String mEndPkgName;
	
	private TrashFastExpandableListViewAdapter mAdapter;
	
	private Timer mQueryCacheTimer = new Timer();
	private TimerTask mQueryCacheTask;
	private Timer mClearCacheTimer = new Timer();
	private TimerTask mClearCacheTask;
	
	private ActivityManager mActivityManager = null;
	private PackageManager mPackageManager = null;

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_EXAND_PIE_ITEM:
				final int i = msg.getData().getInt(INDEX_FOR_PIE);
				mPie.expand(INDEX_FOR_USELESSAPK,
						new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {

							@Override
							public void afterAnima() {
								if (i == INDEX_FOR_USELESSAPK) {
									if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				
										File imagesDir = new File(Environment.getExternalStorageDirectory()
												.getAbsolutePath().toString());
										File path = new File("/storage/sdcard1");
										mApkFileList = new ArrayList<APKFileItem>();
				
										FileUtils.findApkFile(mApkFileList, imagesDir.getPath(),
												getApplicationContext(), mHandler);
										FileUtils.findApkFile(mApkFileList, path.getPath(),
												getApplicationContext(), mHandler);
				
									} else {
										File imagesDir = new File(Environment.getExternalStorageDirectory()
												.getAbsolutePath().toString());
										FileUtils.findApkFile(mApkFileList, imagesDir.getPath(),
												getApplicationContext(), mHandler);
									}
									gitUselessApk(mApkFileList, mHandler);	
								}
							}
				});
				break;

			case MSG_SHRINK_PIE_ITEM:
				final int j = msg.getData().getInt(INDEX_FOR_PIE);
				mPie.shrink(j, new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {

					public void afterAnima() {
						
						Message endMsg = mHandler.obtainMessage(MSG_UPDATE_SIZE);
						Bundle mbundle = new Bundle();
						mbundle.putInt(INDEX_FOR_PIE, j);
						endMsg.setData(mbundle);
						mHandler.sendMessage(endMsg);
						
						switch (j) {
						case INDEX_FOR_USELESSAPK:
							mPie.expand(INDEX_FOR_MEMORYTRASH,
									new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
										public void afterAnima() {
											clearMemory(mHandler);
										}		
							});
							break;
						case INDEX_FOR_MEMORYTRASH:
							mPie.expand(INDEX_FOR_SERVICETRASH,
									new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
										public void afterAnima() {
											searchBackService(mHandler);
										}		
							});
							break;
						case INDEX_FOR_SERVICETRASH:
							mPie.expand(INDEX_FOR_APPCACHE,
									new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
										public void afterAnima() {
											if (mNeedClearAllCache) {
												queryToatalCache();
											} else {
												queryTrashCache();
											}											
										}		
							});
							break;
						case INDEX_FOR_APPCACHE:
							mHandler.sendEmptyMessage(MSG_END_TRASH_SEARCH);
							break;
						default:
							break;
						}				
					}
					
				});
				break;
				
			case MSG_UPDATE_SIZE:
				final int x = msg.getData().getInt(INDEX_FOR_PIE);
				long size = 0;
				switch (x) {
				case INDEX_FOR_USELESSAPK:
					size = mApkSize;
					break;
				case INDEX_FOR_MEMORYTRASH:
					size = mMemorySize;
					break;
				case INDEX_FOR_SERVICETRASH:
					size = mServiceSize;
					break;
				case INDEX_FOR_APPCACHE:
					size = mCacheSize;
					break;
				default:
					break;
				}
				
				stopBar(x);
				beginBar(x + 1);
				setSize(x, size);
				mAdapter.notifyDataSetChanged();
				break;
				
			case MSG_END_TRASH_SEARCH:
				int finalScore = (int) ((mApkNum + mCacheNum + mMemoryNum + mServiceNum) / 4);
				
				if (finalScore == 100) {
					gotoClearSuccess();
				} else {
					stopAllSearch();
					setmPain(true);
					mExpandableListView.setEnabled(true);
				}
				break;
				
			case MSG_UPDATE_TRASH_CLEAR:
				gotoClearSuccess();
				break;
			default:
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanner_and_cleaner);

		initView();
		initPie();
		initData();
		searchTrash();
	}

	private void initView() {
		setmPain(false);
		mAnimaColor1 = getApplicationContext().getResources().getColor(
				R.color.animation_color_1);
		mAnimaColor2 = getApplicationContext().getResources().getColor(
				R.color.animation_color_2);
		mAnimaColor3 = getApplicationContext().getResources().getColor(
				R.color.animation_color_3);
		mAnimaColor4 = getApplicationContext().getResources().getColor(
				R.color.animation_color_4);

		mBackground0 = getApplicationContext().getResources().getColor(
				R.color.back_clear);
		mBackground1 = getApplicationContext().getResources().getColor(
				R.color.one_color);
		mBackground2 = getApplicationContext().getResources().getColor(
				R.color.two_color);
		mBackground3 = getApplicationContext().getResources().getColor(
				R.color.three_color);
		mBackground4 = getApplicationContext().getResources().getColor(
				R.color.four_color);

		mPie = (Pie) findViewById(R.id.pie);

		mOnekeyClear = (Button) findViewById(R.id.onekey_clear);
		mOnekeyClear.setOnClickListener(this);

		mExpandableListView = (ExpandableListView) findViewById(R.id.trash_home_expandablelistview);
		mExpandableListView.setDivider(null);
		mExpandableListView.setOnGroupExpandListener(this);
		
		mGroupTypes = new ArrayList<TrashItem>();
		mGroupMembers = new ArrayList<List<TrashItem>>();
		mAdapter = new TrashFastExpandableListViewAdapter(getApplicationContext(), mGroupTypes, mGroupMembers);
		mExpandableListView.setAdapter(mAdapter);
		mExpandableListView.setEnabled(false);
		mAdapter.setOnChildItemClickListener(this);
		mAdapter.setOnGroupItemCheckedClickListener(this);
	}

	private void initPie() {
		int count = 4;
		int[] back_color = { mBackground2, mBackground1, mBackground4,
				mBackground3 };
		int[] anima_color = { mAnimaColor2, mAnimaColor1, mAnimaColor4,
				mAnimaColor3 };
		int[] maxs = { 100, 100, 100, 100 };
		int[] mins = { 0, 0, 0, 0 };
		int[] values = { 0, 0, 0, 0 };
		int line_color = mBackground0;
		mPie.init(count, back_color, anima_color, maxs, mins, values, line_color);
	}

	private void initData() {
		mHiddenNumber = 0;

		mGroupTypes.clear();
		mGroupTypes.add(getInitGroupDate(
				R.drawable.trash_useless_installation_package,
				R.string.trash_useless_installation_package, 0, true, true));
		mGroupTypes.add(getInitGroupDate(R.drawable.ic_none_launcher,
				R.string.trash_memory_cleaner, 0, true, false));

		mGroupTypes.add(getInitGroupDate(R.drawable.trash_uninstall_residues,
				R.string.trash_uninstall_residues, 0, true, false));

		mGroupTypes.add(getInitGroupDate(R.drawable.trash_cache,
				R.string.trash_cache, 0, true, false));
		mAdapter.notifyDataSetChanged();
	}
	
	private TrashItem getInitGroupDate(int mGroupIconId, int mGroupInfoId,
			long mGroupSize, boolean IsChecked, boolean IsShowProgressBar) {
		return new TrashItem(getResources().getDrawable(mGroupIconId),
				getResources().getString(mGroupInfoId), mGroupSize, IsChecked,
				IsShowProgressBar, null);

	}
	
	private void searchTrash() {
		initTrashDate();
		queryCacheSchedule();
	}
	
	private void initTrashDate() {
		mAppCache = new ArrayList<TrashItem>();
		mServiceTrash = new ArrayList<TrashItem>();
		mUselessApk = new ArrayList<TrashItem>();
		mMemoryTrash = new ArrayList<TrashItem>();

		mGroupMembers.clear();
	}
	
	private void queryCacheSchedule() {
		if (mQueryCacheTask != null) {
			mQueryCacheTask.cancel();
		}
		mQueryCacheTask = new QueryCacheTask();
		mQueryCacheTimer.schedule(mQueryCacheTask, 0);
	}
	
	class QueryCacheTask extends TimerTask {
		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Message msg = mHandler.obtainMessage(MSG_EXAND_PIE_ITEM);
			Bundle bundle = new Bundle();
			bundle.putInt(INDEX_FOR_PIE, INDEX_FOR_USELESSAPK);
			msg.setData(bundle);
			mHandler.sendMessage(msg);
		}
	}
	
	private void gitUselessApk(List<APKFileItem> ApkFileList, Handler handler) {
		for (APKFileItem item : ApkFileList) {
			if (item.getType() == 1) { // Has been installed apk
				mApkSize += item.getApkFileSize();
				mUselessApk.add(new TrashItem(item.getApkicon(), item
						.getApkFileName(), item.getApkFileSize(), true, false,
						item.getApkFileAbsolutePath()));
				continue;
			}
			if (item.getType() == 3) { // Corrupted apk
				mApkSize += item.getApkFileSize();
				mUselessApk.add(new TrashItem(getResources().getDrawable(
						R.drawable.trash_useless_installation_package), item
						.getApkFileName(), item.getApkFileSize(), true, false,
						item.getApkFileAbsolutePath()));
			}
		}
		
		mApkNum = sizeToNum(mApkSize, mApkNum);
				
		mPie.setvalue(INDEX_FOR_USELESSAPK, mApkNum, true,
				new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
					@Override
					public void afterAnima() {
						Message msg = mHandler.obtainMessage(MSG_SHRINK_PIE_ITEM);
						Bundle bundle = new Bundle();
						bundle.putInt(INDEX_FOR_PIE, INDEX_FOR_USELESSAPK);
						msg.setData(bundle);
						mHandler.sendMessage(msg);
					}
				});
	}
	
	private void clearMemory(Handler handler) {
		if (mPackageManager == null) {
			mPackageManager = this.getPackageManager();
		}
		
		if (mActivityManager == null) {
			mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		}
		
		AppUtil proutils = new AppUtil(getApplicationContext());
		
		List<RunningAppProcessInfo> listInfo = mActivityManager.getRunningAppProcesses();
		
		if (listInfo == null || listInfo.isEmpty() || listInfo.size() == 0) {
			return;
		}

		for (RunningAppProcessInfo info : listInfo) {
			ApplicationInfo app = proutils.getApplicationInfo(info.processName);
			
			String packageName = info.processName;
			if (app == null) {
				continue;
			}
				
			if ((app.flags & app.FLAG_SYSTEM) > 0) {
				continue;
			}
			
			if (!("system".equals(packageName)
					|| "android.process.media".equals(packageName)
					|| "android.process.acore".equals(packageName)
					|| "com.android.phone".equals(packageName)
					|| "com.android.systemui".equals(packageName)
					|| "com.adups.fota".equals(packageName)
					|| "com.mediatek.bluetooth".equals(packageName)
					|| "com.rgk.phonemanager".equals(packageName))) {
				
				TrashItem trashItem = new TrashItem();

				trashItem.setFilePath(packageName);
				trashItem.setIsChecked(true);

				Drawable task_icon = app.loadIcon(mPackageManager);
				String task_name = app.loadLabel(mPackageManager).toString();
				if (task_icon == null) {
					trashItem.setmGroupIcon(getApplicationContext().getResources().getDrawable(R.drawable.ic_none_launcher));
				} else {
					trashItem.setmGroupIcon(task_icon);
				}
				trashItem.setmGroupInfo(task_name);

				int[] myMempid = new int[] { info.pid };
				Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(myMempid);
				int memSize = (int) ((int) memoryInfo[0].dalvikPrivateDirty * 1024.0);

				trashItem.setmGroupSize(memSize);
				mMemoryTrash.add(trashItem);				
				mMemorySize += memSize;
			}
		}
		
		mMemoryNum = sizeToNum(mMemorySize, mMemoryNum);
		
		mPie.setvalue(INDEX_FOR_MEMORYTRASH, mMemoryNum, true,
				new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
					@Override
					public void afterAnima() {
						Message msg = mHandler.obtainMessage(MSG_SHRINK_PIE_ITEM);
						Bundle bundle = new Bundle();
						bundle.putInt(INDEX_FOR_PIE, INDEX_FOR_MEMORYTRASH);
						msg.setData(bundle);
						mHandler.sendMessage(msg);
					}
				});
	}
	
	private void searchBackService(Handler handler) {
		if (mPackageManager == null) {
			mPackageManager = this.getPackageManager();
		}
		
		if (mActivityManager == null) {
			mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
		}		
		
		AppUtil proutils = new AppUtil(getApplicationContext());
		
		List<RunningServiceInfo> listInfo = mActivityManager.getRunningServices(100);
		
		if (listInfo == null || listInfo.isEmpty() || listInfo.size() == 0) {
			return;
		}
		
		for (RunningServiceInfo info : listInfo) {						                                			            
			String packageName = info.service.getPackageName();
			ApplicationInfo app = proutils.getApplicationInfo(packageName);

            if (!("system".equals(packageName)
					|| "android.process.media".equals(packageName)
					|| "android.process.acore".equals(packageName)
					|| "com.android.phone".equals(packageName)
					|| "com.android.systemui".equals(packageName)
					|| "com.mediatek.bluetooth".equals(packageName)
					|| "com.adups.fota".equals(packageName)
					|| "com.android.inputmethod.latin".equals(packageName)
					|| "com.android.server.telecom".equals(packageName)
					|| (packageName != null && packageName.startsWith("com.google.android"))
					|| "com.rgk.phonemanager".equals(packageName))) {
				TrashItem trashItem = new TrashItem();

				trashItem.setFilePath(packageName);
				trashItem.setIsChecked(true);

				Drawable task_icon = null;
				
				if (app != null) {
					task_icon = app.loadIcon(mPackageManager);
				}
				
				String task_name = info.service.getClassName();
				if (task_icon == null) {
					trashItem.setmGroupIcon(getApplicationContext().getResources().getDrawable(R.drawable.ic_none_launcher));
				} else {
					trashItem.setmGroupIcon(task_icon);
				}
				trashItem.setmGroupInfo(task_name);
				
				int[] myMempid = new int[] { info.pid };
				Debug.MemoryInfo[] memoryInfo = mActivityManager.getProcessMemoryInfo(myMempid);
				int memSize = (int) ((int) memoryInfo[0].dalvikPrivateDirty * 1024.0);

				trashItem.setmGroupSize(memSize);
				
				mServiceTrash.add(trashItem);				
				mServiceSize += memSize;
			}

		}
		
		mServiceNum = sizeToNum(mServiceSize, mServiceNum);
		
		mPie.setvalue(INDEX_FOR_SERVICETRASH, mServiceNum, true,
				new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
					@Override
					public void afterAnima() {
						Message msg = mHandler.obtainMessage(MSG_SHRINK_PIE_ITEM);
						Bundle bundle = new Bundle();
						bundle.putInt(INDEX_FOR_PIE, INDEX_FOR_SERVICETRASH);
						msg.setData(bundle);
						mHandler.sendMessage(msg);
					}
				});
	}
	
	private void queryTrashCache() {		
		String pkgName = "";
		
		for (int i = 0; i < mMemoryTrash.size(); i++) {
			pkgName = mMemoryTrash.get(i).getFilePath();
			Drawable mIcon = mMemoryTrash.get(i).getmGroupIcon();
			String mAppName = mMemoryTrash.get(i).getmGroupInfo();
			mAppCache.add(new TrashItem(mIcon, mAppName, 0, true, pkgName));
			
			try {
				queryPkgCacheSize(pkgName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if ("".equals(pkgName)) {
			mPie.setvalue(INDEX_FOR_APPCACHE, 100, true,
					new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {

						@Override
						public void afterAnima() {
							Message msg = mHandler.obtainMessage(MSG_SHRINK_PIE_ITEM);
							Bundle bundle = new Bundle();
							bundle.putInt(INDEX_FOR_PIE, INDEX_FOR_APPCACHE);
							msg.setData(bundle);
							mHandler.sendMessage(msg);
						}

					});
		} else {
			mEndPkgName = pkgName;
		}		
	}
	
	private void queryToatalCache() {

		if (mPackageManager == null) {
			mPackageManager = getApplicationContext().getPackageManager();
		}
		List<ApplicationInfo> apps = mPackageManager
				.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES
						| PackageManager.GET_ACTIVITIES);

		String pkgName = "";

		for (ApplicationInfo info : apps) {
			pkgName = info.packageName;
			Drawable mIcon = info.loadIcon(mPackageManager);
			String mAppName = info.loadLabel(mPackageManager).toString();
			mAppCache.add(new TrashItem(mIcon, mAppName, 0, true, pkgName));

			try {
				queryPkgCacheSize(pkgName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		mEndPkgName = pkgName;				
	}
	
	private void queryPkgCacheSize(String pkgName) throws Exception {
		if (!TextUtils.isEmpty(pkgName)) {
			if (mPackageManager == null) {
				mPackageManager = getApplicationContext().getPackageManager();
			}
			try {
				String methodName = "getPackageSizeInfo";
				Class<?> parameterType1 = String.class;
				Class<?> parameterType2 = IPackageStatsObserver.class;
				Method getPackageSizeInfo = mPackageManager.getClass()
						.getMethod(methodName, parameterType1, parameterType2);
				getPackageSizeInfo.invoke(mPackageManager, pkgName,
						mStatsObserver);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private IPackageStatsObserver.Stub mStatsObserver = new IPackageStatsObserver.Stub() {

		@Override
		public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
				throws RemoteException {
			mCacheSize += pStats.cacheSize;

			for (int i = 0; i < mAppCache.size(); i++) {
				if (mAppCache.get(i).getFilePath().equals(pStats.packageName)) {
					mAppCache.get(i).setmGroupSize(pStats.cacheSize);
				}
			}

			mSystemCacheSize += pStats.cacheSize;

			if (mEndPkgName.equals(pStats.packageName)) {
				mCacheNum = sizeToNum(mCacheSize, mCacheNum);

				mPie.setvalue(INDEX_FOR_APPCACHE, mCacheNum, true,
						new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {

							@Override
							public void afterAnima() {
								Message msg = mHandler.obtainMessage(MSG_SHRINK_PIE_ITEM);
								Bundle bundle = new Bundle();
								bundle.putInt(INDEX_FOR_PIE, INDEX_FOR_APPCACHE);
								msg.setData(bundle);
								mHandler.sendMessage(msg);
							}

						});
			}
		}
	};
	
	private int sizeToNum(long size, int num) {
		if (size > 0) {
			float size_m = size / 1024 / 1024;
			
			if (size_m >= 150) {
				num -= 32;
			} else if (size_m >= 100 && size_m < 150) {
				num -= 26;
			} else if (size_m >= 50 && size_m < 100) {
				num -= 22;
			} else if (size_m >= 30 && size_m < 50)  {
				num -= 16;
			} else if (size_m >= 15 && size_m < 30) {
				num -= 12;
			} else if (size_m >= 10 && size_m < 15) {
				num -= 8;
			} else if (size_m >= 5 && size_m < 10) {
				num -= 6;
			} else if (size_m >= 0 && size_m < 5 ) {
				num -= 3;
			}
		}
		return num;
	}

	public static boolean ismPain() {
		return mPain;
	}

	public void setmPain(boolean mPain) {
		ScannerAndCleaner.mPain = mPain;
	}
	
	private void beginBar(int i) {
		if (i < mGroupTypes.size()) {
			mGroupTypes.get(i).setIsShowProgressBar(true);
		}		
	}
	
	private void setSize(int i, long size) {
		mGroupTypes.get(i).setmGroupSize(size);
	}
	
	private void stopBar(int i) {
		mGroupTypes.get(i).setIsShowProgressBar(false);
	}
	
	private void gotoClearSuccess() {
		Intent intent = new Intent(ScannerAndCleaner.this, TrashQuickClearSuccess.class);
		startActivity(intent);
		finish();
	}
	
	private void stopAllSearch() {
		for (int i = 0; i < mAppCache.size(); i++) {
			if (mAppCache.get(i).getmGroupSize() == 0) {
				mRemoveTrashItem.add(mAppCache.get(i));
			}
		}

		stopProgressBar();
		updateUselessApk();
		updateMemory();
		updateBackService();
		updateAppCache();
		mOnekeyClear.setVisibility(View.VISIBLE);
		mAdapter.notifyDataSetChanged();
	}
	
	private void stopProgressBar() {
		for (TrashItem item : mGroupTypes) {
			item.setIsShowProgressBar(false);
		}
	}
	
	private void updateUselessApk() {
		mHiddenNumber = 0;

		if (mUselessApk.size() <= 0) {
			mGroupTypes.remove(INDEX_FOR_USELESSAPK);
			mHiddenNumber++;
		} else {
			mGroupMembers.add(mUselessApk);
		}
	}
	
	private void updateMemory() {
		if (mMemoryTrash.size() > 0) {
			mGroupMembers.add(mMemoryTrash);
		} else {
			mGroupTypes.remove(INDEX_FOR_MEMORYTRASH - mHiddenNumber);
			mHiddenNumber++;
		}
	}
	
	private void updateBackService() {
		if (mServiceSize <= 0) {
			mGroupTypes.remove(INDEX_FOR_SERVICETRASH - mHiddenNumber);
			mHiddenNumber++;
		} else {
			mGroupMembers.add(mServiceTrash);
		}
	}
	
	private void updateAppCache() {
		if (mSystemCacheSize > 0) {
			mAppCache.removeAll(mRemoveTrashItem);
			mGroupTypes.get(INDEX_FOR_APPCACHE - mHiddenNumber).setmGroupSize(mSystemCacheSize);
			mRemoveTrashItem.clear();
			mGroupMembers.add(mAppCache);
		} else {
			mGroupTypes.remove(INDEX_FOR_APPCACHE - mHiddenNumber);
			mHiddenNumber++;
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.onekey_clear:
				mAdapter = new TrashFastExpandableListViewAdapter(
						getApplicationContext(), mGroupTypes, mGroupMembers);
				mExpandableListView.setAdapter(mAdapter);

				mOnekeyClear.setVisibility(View.INVISIBLE);
				refreshData();

				clearCacheSchedule();
                NotificationUtil.getUtil().cancelLowMemory(ScannerAndCleaner.this);
				break;
			default:
				break;
		}
	}
	
	private void refreshData() {
		mHiddenNumber = 0;
		mGroupTypes.clear();

		if (mApkNum != 100) {
			mGroupTypes.add(getInitGroupDate(
							R.drawable.trash_useless_installation_package,
							R.string.trash_useless_installation_package, 0,
							true, true));
		}
		if (mMemoryNum != 100) {
			mGroupTypes.add(getInitGroupDate(R.drawable.ic_none_launcher,
					R.string.trash_memory_cleaner, 0, true, true));
		}

		if (mServiceNum != 100) {
			mGroupTypes.add(getInitGroupDate(
					R.drawable.trash_uninstall_residues,
					R.string.trash_uninstall_residues, 0, true, true));
		}

		if (mCacheNum != 100) {
			mGroupTypes.add(getInitGroupDate(R.drawable.trash_cache,
					R.string.trash_cache, 0, true, true));
		}
		mAdapter.notifyDataSetChanged();
	}
	
	private void clearCacheSchedule() {
		if (mClearCacheTask != null) {
			mClearCacheTask.cancel();
		}
		mClearCacheTask = new ClearCacheTask();
		mClearCacheTimer.schedule(mClearCacheTask, 500);
	}
	
	private class ClearCacheTask extends TimerTask {
		@Override
		public void run() {
			mGroupMembers.clear();
			clearUselessApk();
			clearBackMemory();
			clearBackService();
			clearAppCache();
		}
	}
	
	private void clearUselessApk() {
		mRemoveTrashItem.clear();
		if (mUselessApk.size() > 0) {
			mPie.setvalue(INDEX_FOR_USELESSAPK, 100, true,
					new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
						@Override
						public void afterAnima() {
							for (TrashItem item : mUselessApk) {
								if (item.isIsChecked()) {
									deleteApk(item.getFilePath());
									mRemoveTrashItem.add(item);
								}
							}
							mUselessApk.removeAll(mRemoveTrashItem);
							mRemoveTrashItem.clear();
						}
					});
		}
	}
	
	private void deleteApk(String path) {
		File file = new File(path);
		if (file != null && file.exists()) {
			file.delete();
		}
	}
	
	private void clearBackMemory() {
		mRemoveTrashItem.clear();
		mPie.setvalue(INDEX_FOR_MEMORYTRASH, 100, true,
				new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {
					@Override
					public void afterAnima() {
						for (int i = 0; i < mMemoryTrash.size(); i++) {
							if (mMemoryTrash.get(i) != null
									&& mMemoryTrash.get(i).getFilePath() != null
									&& mMemoryTrash.get(i).isIsChecked()) {
								String pkgName = mMemoryTrash.get(i).getFilePath();

								if (mActivityManager == null) {
									mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
								}
								mActivityManager.forceStopPackage(pkgName);
								
								mRemoveTrashItem.add(mMemoryTrash.get(i));
							}
						}
						
						mMemoryTrash.removeAll(mRemoveTrashItem);
						mRemoveTrashItem.clear();
					}
				});
	}
	
	private void clearBackService() {
		mRemoveTrashItem.clear();
		if (mServiceTrash.size() > 0) {
			mPie.setvalue(INDEX_FOR_SERVICETRASH, 100, true,
					new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {

						@Override
						public void afterAnima() {
							for (TrashItem item : mServiceTrash) {
								if (item.isIsChecked()) {
									if (mActivityManager == null) {
										mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
									}
									mActivityManager.forceStopPackage(item.getFilePath());
									stopService(new Intent().setClassName(item.getFilePath(), item.getmGroupInfo()));
									mRemoveTrashItem.add(item);
								}
							}
							mServiceTrash.removeAll(mRemoveTrashItem);
							mRemoveTrashItem.clear();
						}
					});
		}
	}
	
	private void clearAppCache() {
		mRemoveTrashItem.clear();
		if (mAppCache.size() > 0) {
			mPie.setvalue(INDEX_FOR_APPCACHE, 100, true,
					new com.rgk.phonemanager.view.util.Pie.AnimaCallBack() {

						@Override
						public void afterAnima() {
							for (TrashItem item : mAppCache) {
								if (item.isIsChecked()) {
									mRemoveTrashItem.add(item);
								}
							}
							mAppCache.removeAll(mRemoveTrashItem);

							clearSystemCache();
							mRemoveTrashItem.clear();
							mHandler.sendEmptyMessage(MSG_UPDATE_TRASH_CLEAR);
						}
					});
		} else {
			mHandler.sendEmptyMessage(MSG_UPDATE_TRASH_CLEAR);
		}
	}
	
	private void clearSystemCache() {
		if (mPackageManager == null) {
			mPackageManager = getApplicationContext().getPackageManager();
		}
		try {
			String methodName = "deleteApplicationCacheFiles";
			Class<?> parameterType1 = String.class;

			Class<?> parameterType2 = IPackageDataObserver.class;

			Method deleteApplicationCacheFiles = mPackageManager.getClass()
					.getMethod(methodName, parameterType1, parameterType2);

			for (int i = 0; i < mRemoveTrashItem.size(); i++) {
				if (mRemoveTrashItem.get(i).isIsChecked()) {
					String cacheFiles = mRemoveTrashItem.get(i).getFilePath();
					deleteApplicationCacheFiles.invoke(mPackageManager, cacheFiles,
							new IPackageDataObserver.Stub() {
								@Override
								public void onRemoveCompleted(String packageName,
										boolean succeeded) throws RemoteException {

								}
							});
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void onGroupExpand(int groupPosition) {
		Log.d(TAG, "onGroupExpand groupPosition:" + groupPosition);
	}

	public void onGroupItemCheckedClickd(int groupPosition) {
		Log.d(TAG, "onGroupItemCheckedClickd groupPosition:" + groupPosition);
		boolean isChecked = false;
		
		if (mGroupMembers != null && mGroupMembers.size() != 0) {
			for (TrashItem item : mGroupMembers.get(groupPosition)) {
				if (item.isIsChecked()) {
					isChecked = true;
					break;
				}
			}
			
			for (TrashItem item : mGroupMembers.get(groupPosition)) {
				item.setIsChecked(!isChecked);
			}
			mGroupTypes.get(groupPosition).setIsChecked(!isChecked);
			
			mAdapter.notifyDataSetChanged();
		}
	}

	public void onChildItemClicked(int groupPosition, int childPosition) {
		Log.d(TAG, "onChildItemClicked groupPosition:" + groupPosition + "|childPosition:" + childPosition);
		
		int checkedNum = 0;
		
		boolean isChecked = mGroupMembers.get(groupPosition).get(childPosition)
				.isIsChecked();
		mGroupMembers.get(groupPosition).get(childPosition)
				.setIsChecked(!isChecked);
		
		if (!isChecked) {
			mGroupTypes.get(groupPosition).setIsChecked(true);
		} else {
			for (TrashItem item : mGroupMembers.get(groupPosition)) {
				if (item.isIsChecked()) {
					checkedNum++;
					mGroupTypes.get(groupPosition).setIsChecked(true);
					break;
				}
			}
			
			if (checkedNum == 0) {
				mGroupTypes.get(groupPosition).setIsChecked(false);
			}
		}
		
		mAdapter.notifyDataSetChanged();
	}

}
