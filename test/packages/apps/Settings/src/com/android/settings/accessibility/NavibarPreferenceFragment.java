package com.android.settings.accessibility;

import com.android.settings.widget.ToggleSwitch;
import com.android.settings.widget.ToggleSwitch.OnBeforeCheckedChangeListener;

import android.provider.Settings;

public class NavibarPreferenceFragment extends ToggleFeaturePreferenceFragment {

	@Override
	protected void onPreferenceToggled(String preferenceKey, boolean enabled) {
		Settings.Secure.putInt(getContentResolver(),
                "accessibility_navibar_enabled", enabled ? 1 : 0);
	}

	@Override
	protected void onInstallSwitchBarToggleSwitch() {
		super.onInstallSwitchBarToggleSwitch();
		mToggleSwitch.setOnBeforeCheckedChangeListener(new OnBeforeCheckedChangeListener() {
			
			@Override
			public boolean onBeforeCheckedChanged(ToggleSwitch toggleSwitch,
					boolean checked) {
				mSwitchBar.setCheckedInternal(checked);
                getArguments().putBoolean(AccessibilitySettings.EXTRA_CHECKED, checked);
                onPreferenceToggled(mPreferenceKey, checked);
				return false;
			}
		});
	}
    protected int getMetricsCategory() {
        return -1;
    }
}
