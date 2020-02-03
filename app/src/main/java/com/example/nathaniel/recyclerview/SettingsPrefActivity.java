package com.example.nathaniel.recyclerview;


import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

public class SettingsPrefActivity extends AppCompatActivity {
    private static final String TAG = SettingsPrefActivity.class.getSimpleName();

    @Override
    public Resources.Theme getTheme() {
        int themeId =this.getSharedPreferences("mySPreference",0).getInt("ThemeId",R.style.AppTheme);

        Resources.Theme theme = super.getTheme();
        if (true){
            theme.applyStyle(themeId,true);
        }
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        public static final String FONTS_PREF_KEY = "key_fonts";
        public static final String THEMES_PREF_KEY = "key_themes";
        public static final String SOUND_MODE_PREF_KEY = "key_sound_mode";
        public static final String RINGTONE_PREF_KEY ="key_ringtone";
        public static final String VIBRATE_WHEN_RINGING_PREF_KEY ="key_vibrate_when_ringing";

        public static String uriValue;
        NotificationManager notificationManager;
        AudioManager audioManager;
        Ringtone ringtone;
        private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);
            final Preference ring = findPreference(RINGTONE_PREF_KEY);

            audioManager = (AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);
            preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    uriValue = key;

                    if (key.equals(FONTS_PREF_KEY)) {
                        ListPreference fontPreference = (ListPreference) findPreference(FONTS_PREF_KEY);
                        String value = fontPreference.getValue();
//                        Toast.makeText(getActivity(), "Your selected " + value, Toast.LENGTH_SHORT).show();
                        fontPreference.setSummary(sharedPreferences.getString(key, ""));
                    } else if (key.equals(THEMES_PREF_KEY)) {
                        ListPreference themePreference = (ListPreference) findPreference(THEMES_PREF_KEY);
                        String value = themePreference.getValue();

//                        Toast.makeText(getActivity(), "Your selected " + value, Toast.LENGTH_SHORT).show();
                        themePreference.setSummary(sharedPreferences.getString(key, ""));
                    } else if (key.equals(SOUND_MODE_PREF_KEY)) {
                        ListPreference sound_modePreference = (ListPreference) findPreference(SOUND_MODE_PREF_KEY);
                        String value = sound_modePreference.getValue();
                        sound_modePreference.setSummary(sharedPreferences.getString(key, ""));
                        notificationManager = (NotificationManager) getActivity().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (audioManager != null && notificationManager.isNotificationPolicyAccessGranted()) {
                                switch (value) {
                                    case "Sound":
                                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                        break;
                                    case "Vibrate":
                                        audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                        break;
                                    case "Mute":
                                        audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                        break;
                                    default:
                                        audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                        break;
                                }
                            } else {
                                Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                                startActivityForResult(intent, 12);
                            }
                        } else {
                            switch (value) {
                                case "Sound":
                                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                    break;
                                case "Vibrate":
                                    audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                                    break;
                                case "Mute":
                                    audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                                    break;
                                default:
                                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                                    break;
                            }
                        }
                    }
//                    else if (key.equals(RINGTONE_PREF_KEY)){
//
//                        Preference ringtonePreference = findPreference(RINGTONE_PREF_KEY);
//
//                       String path = ringtonePreference.getSharedPreferences().getString(key,"");
//                        Log.e(TAG, "onSharedPreferenceChanged: Path is"+path);
//                        ringtone = RingtoneManager.getRingtone(ringtonePreference.getContext(), Uri.parse(path));
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
////                            RingtoneManager.setActualDefaultRingtoneUri(getContext(), RingtoneManager.TYPE_RINGTONE,
////                                    RingtoneManager.getActualDefaultRingtoneUri(getContext(),RingtoneManager.TYPE_RINGTONE));
//                       if (ringtone == null) {
//                                // Clear the summary if there was a lookup error.
//                                ringtonePreference.setSummary(R.string.summary_choose_ringtone);
//                            } else {
//                                // Set the summary to reflect the new ringtone display
//                                // name.
//                                String name = ringtone.getTitle(ringtonePreference.getContext());
////                                ringtonePreference.setSummary(name);
//                                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(),null);
//                                Uri defaultSoundUri = RingtoneManager.getValidRingtoneUri(getContext());
//                                notificationBuilder.setSound(defaultSoundUri);
//
//                                RingtoneManager.setActualDefaultRingtoneUri(getActivity(), RingtoneManager.TYPE_RINGTONE, defaultSoundUri);;
//                            }
//                        }
//
//
//                    }

                    else if (key.equals(VIBRATE_WHEN_RINGING_PREF_KEY)) {
                        Preference vibrateWhenRingingPreference = findPreference(VIBRATE_WHEN_RINGING_PREF_KEY);
                        boolean value = vibrateWhenRingingPreference.getSharedPreferences().getBoolean(key, false);
                        boolean status = false;
                        if (value) {
                            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE) {
                                Toast.makeText(getActivity(), "Your selected " + value, Toast.LENGTH_SHORT).show();
//                                Settings.System.putInt(getContentResolver(), "vibrate_when_ringing", AudioManager.FLAG_VIBRATE?1:0);

                                status = true;
                            } else if (1 == Settings.System.getInt(getActivity().getContentResolver(), "vibrate_when_ringing", 1)) //vibrate on
//                                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
//                                audioManager.setVibrateSetting(AudioManager.RINGER_MODE_VIBRATE,AudioManager.VIBRATE_SETTING_ON);
                                audioManager.setRingerMode(AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
                            status = true;
//                            Settings.System.RINGTONE.

//                            testPref.setSummary("Enabled");
                        }
                        else if (ring instanceof RingtonePreference) {
                                Ringtone ringtone = RingtoneManager.getRingtone(
                                        ring.getContext(), Uri.parse(key));
                                if (ringtone == null) {
                                    // Clear the summary if there was a lookup error.
                                    ring.setSummary(null);
                                } else {
                                    // Set the summary to reflect the new ringtone display
                                    // name.
                                    String name = ringtone.getTitle(ring.getContext());
                                    ring.setSummary(name);
                                }
                            }
                        else {
                            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                            testPref.setSummary("Disabled");
                        }
//                        Toast.makeText(getActivity(), "Your selected "+value, Toast.LENGTH_SHORT).show();
//                        themePreference.setSummary(sharedPreferences.getString(key,""));
                    }
                }

            };
            // feedback preference click listener
            Preference myPref = findPreference(getString(R.string.key_send_feedback));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity());
                    return true;
                }
            });
        }

        @Override
        public void onResume() {

            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);

            Preference fontsPreference = findPreference(FONTS_PREF_KEY);
            fontsPreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(FONTS_PREF_KEY,"Normal"));
            Preference themesPreference = findPreference(THEMES_PREF_KEY);
            themesPreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(THEMES_PREF_KEY,"Normal"));
            Preference sound_modePreference = findPreference(SOUND_MODE_PREF_KEY);
            sound_modePreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(SOUND_MODE_PREF_KEY,"Normal"));
            Preference ringtonePreference = findPreference(RINGTONE_PREF_KEY);

            RingtonePreference pref = (RingtonePreference) findPreference(RINGTONE_PREF_KEY);
//
//           RingtoneManager.setActualDefaultRingtoneUri(getActivity(),RingtoneManager.TYPE_RINGTONE,Uri.parse(RINGTONE_PREF_KEY));
//            ringtonePreference.setOnPreferenceChangeListener(this);
            String strRingtonePreference =getPreferenceScreen().getSharedPreferences().getString(RINGTONE_PREF_KEY, "DEFAULT_SOUND");
//            SharedPreferences  RingtonePreference = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

            strRingtonePreference = pref.getSharedPreferences().getString("key_ringtone", "DEFAULT_SOUND");
            String uuri = "content://settings/system/ringtone/Default (World)";
            Uri uri = Uri.parse(uuri);
            Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), uri);
            String nm = ringtone.getTitle(getActivity());
//            Log.e(TAG, "onPreferenceChange: is not null name is "+nm);
            if (ringtone != null) {
                pref.setSummary(uri.toString());
//                Log.i(TAG, "onPreferenceChange: is not null");
//                Log.e(TAG, "onPreferenceChange: is not null"+ strRingtonePreference+" uri"+uri);
                RingtoneManager.setActualDefaultRingtoneUri(getActivity(),RingtoneManager.TYPE_RINGTONE,uri);

            }
            else {
                pref.setSummary(null);
//                Log.i(TAG, "onPreferenceChange: in null");
            }
        }
//            notificationBuilder.setSound(defaultSoundUri);

        @Override
        public void onPause() {

            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
//            if (ringtone == null && ringtone.isPlaying()) {
//                // Clear the summary if there was a lookup error.
//                ringtone.stop();
//                }
            super.onPause();
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            // Check which request we're responding to
            if (requestCode == 12 ) {
                this.onResume();
            }
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
//            updateRingtoneSummary((RingtonePreference) preference, Uri.parse((String) newValue));
//            Log.i(TAG, "onPreferenceChange: is new value "+newValue.toString());
            uriValue = newValue.toString();
            if (preference instanceof RingtonePreference) {
                if (TextUtils.isEmpty(newValue.toString())) {
                    preference.setSummary("choose");
//                    Log.i(TAG, "onPreferenceChange: is empty ");

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(preference.getContext(), Uri.parse(newValue.toString()));
                    if (ringtone != null) {
                        preference.setSummary(ringtone.getTitle(preference.getContext()));
//                        Log.i(TAG, "onPreferenceChange: is not null");

                    } else {
                        preference.setSummary(null);
//                        Log.i(TAG, "onPreferenceChange: in null");
                    }
                }
            }
            return true;
        }

        private void updateRingtoneSummary(RingtonePreference preference, Uri ringtoneUri) {

            Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), ringtoneUri);
            if (ringtone != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    preference.setSummary(ringtone.getTitle(getContext()));
                    RingtoneManager.setActualDefaultRingtoneUri(getContext(),RingtoneManager.TYPE_RINGTONE,ringtoneUri);
                }
            else
                preference.setSummary("Silent");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\n\n-----------------------------\nPlease don't remove this information\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        } catch (PackageManager.NameNotFoundException e) {
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"contact@androidhive.info"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Query from android app");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }

}

//            String path = ringtonePreference.getSharedPreferences().getString(RINGTONE_PREF_KEY,"");
//            Log.i(TAG, "Path: "+path);
//            ringtone = RingtoneManager.getRingtone(ringtonePreference.getContext(), Uri.parse(path));
//            Log.i(TAG, "Ringtone is : "+ringtone.getTitle(getActivity()));

//            sound_modePreference.setSummary(getPreferenceScreen().getSharedPreferences().getString(RINGTONE_PREF_KEY,"Normal"));
//            Ringtone ringtone = RingtoneManager.getRingtone(ringtonePreference.getContext(), Uri.parse(path));
//            String name = ringtone.getTitle(ringtonePreference.getContext());

//            if (ringtone == null) {
//                // Clear the summary if there was a lookup error.
//                ringtonePreference.setSummary(R.string.summary_choose_ringtone);
//            } else {
//                // Set the summary to reflect the new ringtone display
//                // name.
//                String name = ringtone.getTitle(ringtonePreference.getContext());
//                ringtonePreference.setSummary(name);
//
//            }
//            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getActivity(),null);
//            RingtoneManager rm = new RingtoneManager(getActivity());
//
//            String strRingtonePreference = ringtonePreference.getSharedPreferences().getString(RINGTONE_PREF_KEY, "DEFAULT_SOUND");
//            Uri defaultSoundUri = Uri.parse(strRingtonePreference);
//            Ringtone ringtone1 = RingtoneManager.getRingtone(getActivity(),defaultSoundUri);
//            int i = rm.getRingtonePosition(RingtoneManager
//                    .getActualDefaultRingtoneUri(getActivity(),
//                            RingtoneManager.TYPE_RINGTONE));
//            RingtoneManager.setActualDefaultRingtoneUri(getActivity(),
//                    RingtoneManager.TYPE_RINGTONE,
//                    rm.getRingtoneUri(i));
//            ringtonePreference.setSummary(" "+i);
//
//
//
//            ringtone = RingtoneManager.getRingtone(
//                    ringtonePreference.getContext(), Uri.parse(RINGTONE_PREF_KEY));
//            if (ringtone == null) {
//                // Clear the summary if there was a lookup error.
//                ringtonePreference.setSummary(null);
//            } else {
//                // Set the summary to reflect the new ringtone display
//                // name.
//                String namee = ringtone.getTitle(ringtonePreference.getContext());
//                ringtonePreference.setSummary(namee);
//            }