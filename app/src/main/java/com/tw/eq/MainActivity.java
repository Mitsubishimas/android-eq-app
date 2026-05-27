package com.tw.eq;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    // Константы для команд DSP
    private static final int MODULE_SOUND = 4;
    private static final int C_EQ_GAIN = 1;      // Установка полосы EQ
    private static final int C_EQ_MODE = 2;       // Установка режима EQ
    private static final int C_BAL_FADE = 3;      // Баланс/Фейдер
    private static final int C_LOUD = 5;          // Громкость
    private static final int C_EQ_Q = 16;         // Q-фактор

    // Режимы эквалайзера
    private static final int MODE_KOMETA_226S = 0;  // 5 полос
    private static final int MODE_KOMETA_225 = 1;   // 3 полосы

    // Предустановки для 5-полосного EQ
    private static final int[][] PRESETS_5BAND = {
        {0, 0, 0, 0, 0},       // Flat
        {3, 2, 1, 2, 3},       // Classic
        {4, 3, 1, 3, 4},       // Jazz
        {5, 3, 0, -1, -2},     // Rock
        {3, 3, 2, 3, 4},       // Pop
        {4, 2, -1, -2, -3},    // Dance
        {2, 3, 4, 3, 2},       // Vocal
        {-1, 0, 1, 2, 4}       // Treble Boost
    };

    private static final String[] PRESET_NAMES = {"Flat", "Classic", "Jazz", "Rock", "Pop", "Dance", "Vocal", "Treble Boost"};
    private static final String[] FREQ_5BAND = {"63 Hz", "330 Hz", "2000 Hz", "6300 Hz", "15000 Hz"};

    // UI элементы
    private LinearLayout rootLayout;
    private TextView titleText;
    private TextView dspStatus;
    private Spinner modeSpinner;
    private Spinner presetSpinner;
    private LinearLayout layout5Band;
    private LinearLayout layout3Band;
    private SeekBar[] seek5Band = new SeekBar[5];
    private TextView[] value5Band = new TextView[5];
    private SeekBar seekBass, seekMid, seekTreble;
    private TextView valueBass, valueMid, valueTreble;
    private Button resetButton;

    private int currentMode = MODE_KOMETA_226S;
    private int[] eq5Values = new int[5];
    private int[] eq3Values = new int[3];
    private SharedPreferences prefs;
    private boolean dspConnected = false;
    private Object remoteInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Устанавливаем полноэкранный режим и ландшафтную ориентацию
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initUI();
        checkPermissions();
        initDSPConnection();
        loadSavedValues();
    }

    private void initUI() {
        // Создаём корневой layout с градиентным фоном
        rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setPadding(32, 48, 32, 32);
        setContentView(rootLayout);

        // Градиентный фон
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.parseColor("#1a1a2e"), Color.parseColor("#16213e")});
        gradient.setCornerRadius(0);
        rootLayout.setBackground(gradient);

        // Заголовок
        titleText = new TextView(this);
        titleText.setText("MASTER MITSUBISHI EQ");
        titleText.setTextSize(28);
        titleText.setTextColor(Color.parseColor("#00BFFF"));
        titleText.setTypeface(null, android.graphics.Typeface.BOLD);
        titleText.setGravity(android.view.Gravity.CENTER);
        titleText.setPadding(0, 0, 0, 32);
        rootLayout.addView(titleText);

        // Статус DSP
        dspStatus = new TextView(this);
        dspStatus.setText("🔍 Поиск DSP...");
        dspStatus.setTextSize(14);
        dspStatus.setTextColor(Color.parseColor("#FFA500"));
        dspStatus.setGravity(android.view.Gravity.CENTER);
        dspStatus.setPadding(0, 0, 0, 24);
        rootLayout.addView(dspStatus);

        // Спиннер выбора режима
        TextView modeLabel = createLabel("Режим эквалайзера");
        rootLayout.addView(modeLabel);

        modeSpinner = createSpinner(new String[]{"КОМЕТА 226С (5 полос)", "КОМЕТА 225 (3 полосы)"});
        rootLayout.addView(modeSpinner);

        // Спиннер предустановок
        TextView presetLabel = createLabel("ПРЕДУСТАНОВКИ");
        rootLayout.addView(presetLabel);

        presetSpinner = createSpinner(PRESET_NAMES);
        rootLayout.addView(presetSpinner);

        // Разделитель
        rootLayout.addView(createDivider());

        // 5-полосный эквалайзер
        layout5Band = new LinearLayout(this);
        layout5Band.setOrientation(LinearLayout.VERTICAL);
        layout5Band.setPadding(0, 16, 0, 16);

        TextView title5Band = new TextView(this);
        title5Band.setText("▸ 5-ПОЛОСНЫЙ ЭКВАЛАЙЗЕР");
        title5Band.setTextSize(16);
        title5Band.setTextColor(Color.parseColor("#00BFFF"));
        title5Band.setTypeface(null, android.graphics.Typeface.BOLD);
        title5Band.setPadding(0, 0, 0, 16);
        layout5Band.addView(title5Band);

        // Создаём 5 полос
        int[] seekIds = {R.id.seek_63hz, R.id.seek_330hz, R.id.seek_2000hz, R.id.seek_6300hz, R.id.seek_15000hz};
        int[] valueIds = {R.id.value_63hz, R.id.value_330hz, R.id.value_2000hz, R.id.value_6300hz, R.id.value_15000hz};

        for (int i = 0; i < 5; i++) {
            final int index = i;
            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(android.view.Gravity.CENTER_VERTICAL);
            row.setPadding(0, 8, 0, 8);

            TextView freqLabel = new TextView(this);
            freqLabel.setText(FREQ_5BAND[i]);
            freqLabel.setTextSize(14);
            freqLabel.setTextColor(Color.parseColor("#CCCCCC"));
            freqLabel.setWidth(120);
            row.addView(freqLabel);

            seek5Band[i] = new SeekBar(this);
            seek5Band[i].setMax(24);
            seek5Band[i].setProgress(12);
            LinearLayout.LayoutParams seekParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            seek5Band[i].setLayoutParams(seekParams);
            seek5Band[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int value = progress - 12;
                        eq5Values[index] = value;
                        value5Band[index].setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(index, value);
                        saveValues();
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            row.addView(seek5Band[i]);

            value5Band[i] = new TextView(this);
            value5Band[i].setText("0 dB");
            value5Band[i].setTextSize(12);
            value5Band[i].setTextColor(Color.parseColor("#00BFFF"));
            value5Band[i].setWidth(60);
            value5Band[i].setGravity(android.view.Gravity.END);
            row.addView(value5Band[i]);

            layout5Band.addView(row);
        }
        rootLayout.addView(layout5Band);

        // 3-полосный эквалайзер
        layout3Band = new LinearLayout(this);
        layout3Band.setOrientation(LinearLayout.VERTICAL);
        layout3Band.setVisibility(View.GONE);
        layout3Band.setPadding(0, 16, 0, 16);

        TextView title3Band = new TextView(this);
        title3Band.setText("▸ 3-ПОЛОСНЫЙ ЭКВАЛАЙЗЕР");
        title3Band.setTextSize(16);
        title3Band.setTextColor(Color.parseColor("#00BFFF"));
        title3Band.setTypeface(null, android.graphics.Typeface.BOLD);
        title3Band.setPadding(0, 0, 0, 16);
        layout3Band.addView(title3Band);

        // Bass
        LinearLayout bassRow = create3BandRow("BASS", 0);
        layout3Band.addView(bassRow);

        // Mid
        LinearLayout midRow = create3BandRow("MID", 1);
        layout3Band.addView(midRow);

        // Treble
        LinearLayout trebleRow = create3BandRow("TREBLE", 2);
        layout3Band.addView(trebleRow);

        rootLayout.addView(layout3Band);

        // Кнопка сброса
        resetButton = new Button(this);
        resetButton.setText("СБРОС");
        resetButton.setTextSize(16);
        resetButton.setTextColor(Color.WHITE);
        GradientDrawable btnGradient = new GradientDrawable();
        btnGradient.setColor(Color.parseColor("#00BFFF"));
        btnGradient.setCornerRadius(30);
        resetButton.setBackground(btnGradient);
        resetButton.setPadding(48, 16, 48, 16);
        resetButton.setOnClickListener(v -> resetToFlat());
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setGravity(android.view.Gravity.CENTER);
        buttonLayout.addView(resetButton);
        rootLayout.addView(buttonLayout);
    }

    private LinearLayout create3BandRow(String label, final int band) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(android.view.Gravity.CENTER_VERTICAL);
        row.setPadding(0, 8, 0, 8);

        TextView labelView = new TextView(this);
        labelView.setText(label);
        labelView.setTextSize(14);
        labelView.setTextColor(Color.parseColor("#CCCCCC"));
        labelView.setWidth(120);
        row.addView(labelView);

        SeekBar seekBar = new SeekBar(this);
        seekBar.setMax(24);
        seekBar.setProgress(12);
        LinearLayout.LayoutParams seekParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        seekBar.setLayoutParams(seekParams);
        row.addView(seekBar);

        TextView valueView = new TextView(this);
        valueView.setText("0 dB");
        valueView.setTextSize(12);
        valueView.setTextColor(Color.parseColor("#00BFFF"));
        valueView.setWidth(60);
        valueView.setGravity(android.view.Gravity.END);
        row.addView(valueView);

        if (band == 0) {
            seekBass = seekBar;
            valueBass = valueView;
            seekBass.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int value = progress - 12;
                        eq3Values[band] = value;
                        valueBass.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(band, value);
                        saveValues();
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        } else if (band == 1) {
            seekMid = seekBar;
            valueMid = valueView;
            seekMid.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int value = progress - 12;
                        eq3Values[band] = value;
                        valueMid.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(band, value);
                        saveValues();
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        } else {
            seekTreble = seekBar;
            valueTreble = valueView;
            seekTreble.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int value = progress - 12;
                        eq3Values[band] = value;
                        valueTreble.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(band, value);
                        saveValues();
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        return row;
    }

    private TextView createLabel(String text) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(12);
        label.setTextColor(Color.parseColor("#888888"));
        label.setPadding(0, 16, 0, 8);
        return label;
    }

    private Spinner createSpinner(String[] items) {
        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setPadding(8, 8, 8, 8);
        return spinner;
    }

    private View createDivider() {
        View divider = new View(this);
        divider.setBackgroundColor(Color.parseColor("#333333"));
        divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        divider.setPadding(0, 16, 0, 16);
        return divider;
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!android.provider.Settings.System.canWrite(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(android.net.Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        boolean allGranted = true;
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        if (!allGranted) {
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    private void initDSPConnection() {
        prefs = getSharedPreferences("master_mitsubishi_eq", MODE_PRIVATE);

        // Пытаемся подключиться к DSP через Remote класс
        try {
            Class<?> remoteClass = Class.forName("com.syu.remote.Remote");
            java.lang.reflect.Method getAutoTools = remoteClass.getMethod("getAutoTools", Context.class);
            remoteInstance = getAutoTools.invoke(null, this);
            dspConnected = true;
            dspStatus.setText("✅ DSP подключен");
            dspStatus.setTextColor(Color.parseColor("#00FF00"));
            Toast.makeText(this, "DSP готов к работе", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            dspConnected = false;
            dspStatus.setText("⚠️ DSP не обнаружен (демо-режим)");
            dspStatus.setTextColor(Color.parseColor("#FF6600"));
            Toast.makeText(this, "DSP не найден. Настройки сохраняются локально.", Toast.LENGTH_LONG).show();
        }
    }

    private void sendEQValue(int band, int value) {
        if (!dspConnected || remoteInstance == null) return;

        try {
            Class<?> remoteClass = remoteInstance.getClass();
            java.lang.reflect.Method commadMethod = remoteClass.getMethod("commad", int.class, int.class, int[].class);
            commadMethod.invoke(remoteInstance, MODULE_SOUND, C_EQ_GAIN, new int[]{band, value + 12});
        } catch (Exception e) {
            // Ошибка отправки - игнорируем
        }
    }

    private void saveValues() {
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < 5; i++) {
            editor.putInt("eq5_" + i, eq5Values[i]);
        }
        editor.putInt("eq3_bass", eq3Values[0]);
        editor.putInt("eq3_mid", eq3Values[1]);
        editor.putInt("eq3_treble", eq3Values[2]);
        editor.apply();
    }

    private void loadSavedValues() {
        for (int i = 0; i < 5; i++) {
            eq5Values[i] = prefs.getInt("eq5_" + i, 0);
            seek5Band[i].setProgress(eq5Values[i] + 12);
            value5Band[i].setText((eq5Values[i] > 0 ? "+" : "") + eq5Values[i] + " dB");
        }
        eq3Values[0] = prefs.getInt("eq3_bass", 0);
        eq3Values[1] = prefs.getInt("eq3_mid", 0);
        eq3Values[2] = prefs.getInt("eq3_treble", 0);
        if (seekBass != null) seekBass.setProgress(eq3Values[0] + 12);
        if (seekMid != null) seekMid.setProgress(eq3Values[1] + 12);
        if (seekTreble != null) seekTreble.setProgress(eq3Values[2] + 12);
        if (valueBass != null) valueBass.setText((eq3Values[0] > 0 ? "+" : "") + eq3Values[0] + " dB");
        if (valueMid != null) valueMid.setText((eq3Values[1] > 0 ? "+" : "") + eq3Values[1] + " dB");
        if (valueTreble != null) valueTreble.setText((eq3Values[2] > 0 ? "+" : "") + eq3Values[2] + " dB");
    }

    private void resetToFlat() {
        if (currentMode == MODE_KOMETA_226S) {
            for (int i = 0; i < 5; i++) {
                eq5Values[i] = 0;
                seek5Band[i].setProgress(12);
                value5Band[i].setText("0 dB");
                sendEQValue(i, 0);
            }
        } else {
            eq3Values[0] = eq3Values[1] = eq3Values[2] = 0;
            if (seekBass != null) seekBass.setProgress(12);
            if (seekMid != null) seekMid.setProgress(12);
            if (seekTreble != null) seekTreble.setProgress(12);
            if (valueBass != null) valueBass.setText("0 dB");
            if (valueMid != null) valueMid.setText("0 dB");
            if (valueTreble != null) valueTreble.setText("0 dB");
            sendEQValue(0, 0);
            sendEQValue(1, 0);
            sendEQValue(2, 0);
        }
        saveValues();
        Toast.makeText(this, "Сброшено в Flat", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
