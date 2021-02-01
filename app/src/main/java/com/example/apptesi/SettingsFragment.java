package com.example.apptesi;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.annotation.Nullable;

public class SettingsFragment extends PreferenceFragment {




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        ListPreference chooseInterval = (ListPreference) getPreferenceManager().findPreference("background_sync");
        chooseInterval.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                Log.d("preference", newValue.toString());
                return false;
            }
        });

       // final CheckBoxPreference checkbox2 = (CheckBoxPreference) findPreference("background");

        final CheckBoxPreference ch = (CheckBoxPreference) findPreference("background");
        ch.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (ch.isChecked()) {
                    ch.setChecked(true);
                    startLocationBackground();
                } else {
                    //ch.setEnabled(false);

                    ch.setChecked(false);

                }
                return true;
            }
    });
    }



    public void startLocationBackground(){
        getActivity().startService(new Intent(getActivity(), LocationService.class));


    }

}
