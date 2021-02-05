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
                GPS.locationService.setInterval(Integer.parseInt(newValue.toString()));
                return false;
            }
        });

        final CheckBoxPreference ch = (CheckBoxPreference) findPreference("background");
        ch.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                if (ch.isChecked()) {
                    ch.setChecked(true);
                    if(GPS.locationService.active== false) {
                        startLocationBackground();
                    }
                } else {
                    ch.setChecked(false);
                    if(GPS.locationService.active== true){
                        Log.d("LOCATION_UPDATE","entro nel if");
                        getActivity().stopService(GPS.intentLocationService);
                    }

                }
                return true;
            }
    });
    }

    public void startLocationBackground(){
        getActivity().startService(GPS.intentLocationService);

    }


}
