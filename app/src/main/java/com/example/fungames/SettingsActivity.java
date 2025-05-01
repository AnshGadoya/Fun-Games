package com.example.fungames;

import android.content.Context;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.SeekBar;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;


public class SettingsActivity extends AppCompatActivity {

    public static boolean isCheck = false;

    private Switch switchMusic, switchDarkMode, switchVibration, switchAds;
    private SeekBar volumeSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v ->{
            vibrateIfEnabled();
            finish();
        });

        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        switchDarkMode = findViewById(R.id.switch_darkmode);
        switchVibration = findViewById(R.id.switch_vibration);
        switchMusic = findViewById(R.id.switch_music);

        // Set initial toggle and volume
        switchMusic.setChecked(MusicManager.isPlaying());
        volumeSeekBar.setProgress((int)(MusicManager.getCurrentVolume() * 100));

        // Load saved value
        boolean musicEnabled = getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                .getBoolean("music_enabled", false);

        // Load saved values for dark mode and vibration
        boolean darkModeEnabled = getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                .getBoolean("dark_mode_enabled", false);
        switchDarkMode.setChecked(darkModeEnabled);

        boolean vibrationEnabled = getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                .getBoolean("vibration_enabled", false);
        switchVibration.setChecked(vibrationEnabled);


        switchMusic.setChecked(musicEnabled);
        isCheck = musicEnabled;

        // Volume restore
        volumeSeekBar.setProgress((int)(MusicManager.getCurrentVolume() * 100));

        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vibrateIfEnabled();
            isCheck = isChecked;

            getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("music_enabled", isChecked)
                    .apply();

            if (isChecked) {
                MusicManager.start(this);
            } else {
                MusicManager.stop();
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                MusicManager.setVolume(volume);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vibrateIfEnabled();

            // Save to SharedPreferences
            getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("dark_mode_enabled", isChecked)
                    .apply();

            // Apply theme change (requires restart or recreate)
            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });

        switchVibration.setOnCheckedChangeListener((buttonView, isChecked) -> {
            vibrateIfEnabled();
            // Save to SharedPreferences
            getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                    .edit()
                    .putBoolean("vibration_enabled", isChecked)
                    .apply();

        });




    }
    public void vibrateIfEnabled() {
        boolean vibrationEnabled = getSharedPreferences("SettingsPrefs", MODE_PRIVATE)
                .getBoolean("vibration_enabled", false);

        if (vibrationEnabled) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null && vibrator.hasVibrator()) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    vibrator.vibrate(100); // Deprecated but needed for older devices
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (switchMusic.isChecked()) {
            MusicManager.start(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isFinishing()) {
            MusicManager.stop(); // Only play in settings or dashboard
        }
    }
}
