package com.example.android.popularmovies;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class popularMoviesPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            Preference orderby = findPreference(getString(R.string.order_by_key));
            bindPreferencesWithValue(orderby);
        }

        private void bindPreferencesWithValue(Preference orderby) {
            orderby.setOnPreferenceChangeListener(this);
            SharedPreferences sharedpref = PreferenceManager.getDefaultSharedPreferences(orderby.getContext());
            String preferenceString = sharedpref.getString(orderby.getKey(),"");
            onPreferenceChange(orderby,preferenceString);

        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String summaryValue = newValue.toString();
            if(preference instanceof ListPreference){
                ListPreference listpreference1 = (ListPreference) preference;
                int prefIndex = listpreference1.findIndexOfValue(summaryValue);
                if(prefIndex >= 0){
                    CharSequence[] labels = listpreference1.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            }else{
                preference.setSummary(summaryValue);
            }


            return true;
        }
    }
}
