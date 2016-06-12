package com.android.settings.accessibility;

import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.android.settings.R;

import com.android.settings.SettingsPreferenceFragment;

public class NaviBarOptionPreferenceFragment extends SettingsPreferenceFragment {
	// A: JWBLL-1052 daizhiyi 20150319 {
	private static final String NAVIBAR_PREFERENCE_SCREEN = "navibar_preference_screen";
	// A: }
	private static final String NAVIBAR_DIRECTION_CATEGORY = "navigation_bar_direction";
	private static final String ACCESSIBILITY_NAVIBAR_DIRECTION = "accessibility_navibar_direction";
	static final String EXTRA_CHECKED = "checked";
	static final String EXTRA_TITLE = "title";
	static final String EXTRA_SUMMARY = "summary";
	private PreferenceCategory mDirectionCategory;
	// A: JWBLL-1052 daizhiyi 20150319 {
	private PreferenceScreen mNavibarPreferenceScreen;
	// A: }
	private RadioButtonPreference mNormalDirection;
	private RadioButtonPreference mReverseDirection;
    private Context mContext;
	private final OnClickListener mListener = new OnClickListener() {
		
		@Override
		public void onRodioButtonClicked(RadioButtonPreference preference) {
			mNormalDirection.setChecked(preference == mNormalDirection);
			mReverseDirection.setChecked(preference == mReverseDirection);
			handleNavibarDirectionChanged(preference == mNormalDirection);
		}
	};
	
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		addPreferencesFromResource(R.xml.navigation_bar_option_prefs);
		initAllPreferces();
	}

	private void initAllPreferces() {
		mNavibarPreferenceScreen = (PreferenceScreen) findPreference(NAVIBAR_PREFERENCE_SCREEN);
		mDirectionCategory = (PreferenceCategory) findPreference(NAVIBAR_DIRECTION_CATEGORY);
		mNormalDirection = new RadioButtonPreference(getActivity());
		mNormalDirection.setIcon(R.drawable.accessibility_navibar_direction_normal);
		mNormalDirection.setOnClickListener(mListener);
		mDirectionCategory.addPreference(mNormalDirection);
		mReverseDirection = new RadioButtonPreference(getActivity());
		mReverseDirection.setIcon(R.drawable.accessibility_navibar_direction_reverse);
		mReverseDirection.setOnClickListener(mListener);
		mDirectionCategory.addPreference(mReverseDirection);
	}

	// A: JWBLL-1052 daizhiyi 20150319 {
	private void handleNavibarPreferenceScreenClick() {
		Bundle extras = mNavibarPreferenceScreen.getExtras();
		extras.putString(EXTRA_TITLE,
				getString(R.string.accessibility_navibar_title));
		extras.putCharSequence(EXTRA_SUMMARY, getActivity().getResources()
				.getText(R.string.accessibility_navibar_summary));
		extras.putBoolean(EXTRA_CHECKED, Settings.Secure.getInt(
                getContentResolver(), "accessibility_navibar_enabled", 
                mContext.getResources().getInteger(R.integer.config_navbar_option_on_default)
                ) == 1);
		super.onPreferenceTreeClick(mNavibarPreferenceScreen,
				mNavibarPreferenceScreen);
	}
	// A: }
	
	@Override
	public void onResume() {
		super.onResume();
		updateNavibarPreference();
	}

	private void updateNavibarPreference() {
		// A: JWBLL-1052 daizhiyi 20150319 {
		final boolean navibarEnabled = Settings.Secure.getInt(
                getContentResolver(), "accessibility_navibar_enabled",
                mContext.getResources().getInteger(R.integer.config_navbar_option_on_default)
                ) == 1;
		if (navibarEnabled) {
			mNavibarPreferenceScreen
					.setSummary(R.string.accessibility_feature_state_on);
		} else {
			mNavibarPreferenceScreen
					.setSummary(R.string.accessibility_feature_state_off);
		}
		// A: }
		final boolean navibarReverseDirection = Settings.Secure.getInt(
				getContentResolver(), ACCESSIBILITY_NAVIBAR_DIRECTION, 0) == 1;
		mNormalDirection.setChecked(!navibarReverseDirection);
		mReverseDirection.setChecked(navibarReverseDirection);
	}

	@Override
	public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
			Preference preference) {
		if (preference == mNavibarPreferenceScreen) {
			handleNavibarPreferenceScreenClick();
			return true;
		}
		return super.onPreferenceTreeClick(preferenceScreen, preference);
	}
	
	private void handleNavibarDirectionChanged(boolean isDefault){
		Settings.Secure.putInt(
				getContentResolver(), ACCESSIBILITY_NAVIBAR_DIRECTION, isDefault ? 0 : 1);
	}
	
	class RadioButtonPreference extends CheckBoxPreference{
		
		//private Context mContext;
		private OnClickListener mListener;
		private int mIconMaxWidth;

		public RadioButtonPreference(Context context, AttributeSet attrs,
				int defStyle) {
			super(context, attrs, defStyle);
			mContext = context;
			mIconMaxWidth = context.getResources().getDimensionPixelSize(R.dimen.navibar_direction_icon_width);
			setWidgetLayoutResource(R.layout.preference_widget_radiobutton);
		}

		public RadioButtonPreference(Context context, AttributeSet attrs) {
			this(context, attrs, com.android.internal.R.attr.checkBoxPreferenceStyle);
		}

		public RadioButtonPreference(Context context) {
			this(context, null);
		}
		
		public void setOnClickListener(OnClickListener l){
			mListener = l;
		}
		
		@Override
		protected void onClick() {
			if(mListener != null){
				mListener.onRodioButtonClicked(this);
			}
		}
		
		@Override
		protected void onBindView(View view) {
			super.onBindView(view);
			ImageView iv = (ImageView) view.findViewById(android.R.id.icon);
			iv.setMaxWidth(mIconMaxWidth);
			iv.setScaleType(ScaleType.FIT_XY);
		}
	}
	public interface OnClickListener{
		void onRodioButtonClicked(RadioButtonPreference preference);
	}
    protected int getMetricsCategory() {
        return -1;
    }
}
