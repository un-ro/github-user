package com.unero.githubuser.ui.fragments.setting

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.unero.githubuser.R

class SettingFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener  {

    private lateinit var reminder: String
    private lateinit var switch: SwitchPreference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        localeSettings()
        reminder()
        alarmReceiver = AlarmReceiver()
        setSharedPref()
    }

    private fun setSharedPref() {
        val sharedPreferences = preferenceManager.sharedPreferences
        switch.isChecked = sharedPreferences.getBoolean(reminder, false)
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == reminder) {
            if (sharedPreferences != null) {
                switch.isChecked = sharedPreferences.getBoolean(reminder, false)
            }
        }

        val state = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminder, false)
        setReminder(state)
    }

    private fun reminder() {
        reminder = "key_alarm"
        switch = findPreference<SwitchPreference>(reminder) as SwitchPreference
    }

    private fun localeSettings() {
        val languagePreference: Preference? = findPreference("key_language")
        val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        languagePreference?.intent = languageIntent
    }

    private fun setReminder(state: Boolean) {
        if (state) {
            context?.let { alarmReceiver.setRepeatingAlarm(it) }
        } else {
            context?.let { alarmReceiver.cancelRepeatingAlarm(it) }
        }
    }
}