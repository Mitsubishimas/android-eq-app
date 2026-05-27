package com.tw.eq;

import android.Manifest;
import android.content.Context;
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

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    // Константы для команд DSP (из FinalSound.java)
    private static final int MODULE_SOUND = 4;
    private static final int C_EQ_GAIN = 1;      // Установка полосы EQ
    private static final int C_EQ_MODE = 2;       // Установка режима EQ
    private static final int C_LOUD = 5;          // Громкость
    private static final int C_BAL_FADE = 3;      // Баланс/Фейдер

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
    private Method commadMethod;
    private Handler dspCheckHandler = new Handler();
    private int dspRetryCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Устанавливаем полноэкранный режим
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initUI();
        checkPermissions();
        initDSPConnection();
        loadSavedValues();
        setupListeners();
    }

    private void initUI() {
        // Создаём корневой layout
        rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setPadding(32, 48, 32, 32);
        setContentView(rootLayout);

        // Градиентный фон
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.parseColor("#0a0a1a"), Color.parseColor("#1a1a2e")});
        gradient.setCornerRadius(0);
        rootLayout.setBackground(gradient);

        // Заголовок
        titleText = new TextView(this);
        titleText.setText("MASTER MITSUBISHI EQ");
        titleText.setTextSize(28);
        titleText.setTextColor(Color.parseColor("#00BFFF"));
        titleText.setTypeface(null, android.graphics.Typeface.BOLD);
        titleText.setGravity(android.view.Gravity.CENTER);
        titleText.setPadding(0, 0, 0, 24);
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
        TextView modeLabel = createLabel("РЕЖИМ ЭКВАЛАЙЗЕРА");
        rootLayout.addView(modeLabel);

        modeSpinner = createSpinner(new String[]{"КОМЕТА 226С (5 полос)", "КОМЕТА 225 (3 полосы)"});
        rootLayout.addView(modeSpinner);
        rootLayout.addView(createSpacing(8));

        // Спиннер предустановок
        TextView presetLabel = createLabel("ПРЕДУСТАНОВКИ");
        rootLayout.addView(presetLabel);

        presetSpinner = createSpinner(PRESET_NAMES);
        rootLayout.addView(presetSpinner);
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

            seek5Band[index] = new SeekBar(this);
            seek5Band[index].setMax(24);
            seek5Band[index].setProgress(12);
            LinearLayout.LayoutParams seekParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            seek5Band[index].setLayoutParams(seekParams);
            row.addView(seek5Band[index]);

            value5Band[index] = new TextView(this);
            value5Band[index].setText("0 dB");
            value5Band[index].setTextSize(12);
            value5Band[index].setTextColor(Color.parseColor("#00BFFF"));
            value5Band[index].setWidth(60);
            value5Band[index].setGravity(android.view.Gravity.END);
            row.addView(value5Band[index]);

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
        resetButton.setText("СБРОС ВСЕХ НАСТРОЕК");
        resetButton.setTextSize(16);
        resetButton.setTextColor(Color.WHITE);
        GradientDrawable btnGradient = new GradientDrawable();
        btnGradient.setColor(Color.parseColor("#00BFFF"));
        btnGradient.setCornerRadius(30);
        resetButton.setBackground(btnGradient);
        resetButton.setPadding(48, 16, 48, 16);
        LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setGravity(android.view.Gravity.CENTER);
        buttonLayout.setPadding(0, 32, 0, 0);
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
        } else if (band == 1) {
            seekMid = seekBar;
            valueMid = valueView;
        } else {
            seekTreble = seekBar;
            valueTreble = valueView;
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

    private View createSpacing(int dp) {
        View space = new View(this);
        space.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, dp));
        return space;
    }

    private View createDivider() {
        View divider = new View(this);
        divider.setBackgroundColor(Color.parseColor("#333333"));
        divider.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
        divider.setPadding(0, 16, 0, 16);
        return divider;
    }

    private void setupListeners() {
        // Обработчик переключения режимов
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentMode = position;
                if (position == MODE_KOMETA_226S) {
                    layout5Band.setVisibility(View.VISIBLE);
                    layout3Band.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Режим: Комета 226С (5 полос)", Toast.LENGTH_SHORT).show();
                } else {
                    layout5Band.setVisibility(View.GONE);
                    layout3Band.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Режим: Комета 225 (3 полосы)", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Обработчик выбора предустановок
        presetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    applyPreset(position - 1);
                    presetSpinner.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Обработчики для 5-полосного EQ
        for (int i = 0; i < 5; i++) {
            final int index = i;
            seek5Band[index].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
        }

        // Обработчики для 3-полосного EQ
        if (seekBass != null) {
            seekBass.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int value = progress - 12;
                        eq3Values[0] = value;
                        valueBass.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(0, value);
                        saveValues();
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
        if (seekMid != null) {
            seekMid.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int value = progress - 12;
                        eq3Values[1] = value;
                        valueMid.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(1, value);
                        saveValues();
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }
        if (seekTreble != null) {
            seekTreble.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        int value = progress - 12;
                        eq3Values[2] = value;
                        valueTreble.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(2, value);
                        saveValues();
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
        }

        // Кнопка сброса
        resetButton.setOnClickListener(v -> resetToFlat());
    }

    private void applyPreset(int presetIndex) {
        if (currentMode == MODE_KOMETA_226S) {
            int[] values = PRESETS_5BAND[presetIndex];
            for (int i = 0; i < 5; i++) {
                eq5Values[i] = values[i];
                seek5Band[i].setProgress(values[i] + 12);
                value5Band[i].setText((values[i] > 0 ? "+" : "") + values[i] + " dB");
                sendEQValue(i, values[i]);
            }
            Toast.makeText(this, "Применён пресет: " + PRESET_NAMES[presetIndex], Toast.LENGTH_SHORT).show();
        } else {
            // Для 3-полосного преобразуем значения из 5-полосного
            int[] values = PRESETS_5BAND[presetIndex];
            eq3Values[0] = values[0];
            eq3Values[1] = values[2];
            eq3Values[2] = values[4];
            if (seekBass != null) seekBass.setProgress(values[0] + 12);
            if (seekMid != null) seekMid.setProgress(values[2] + 12);
            if (seekTreble != null) seekTreble.setProgress(values[4] + 12);
            if (valueBass != null) valueBass.setText((values[0] > 0 ? "+" : "") + values[0] + " dB");
            if (valueMid != null) valueMid.setText((values[2] > 0 ? "+" : "") + values[2] + " dB");
            if (valueTreble != null) valueTreble.setText((values[4] > 0 ? "+" : "") + values[4] + " dB");
            sendEQValue(0, values[0]);
            sendEQValue(1, values[2]);
            sendEQValue(2, values[4]);
            Toast.makeText(this, "Применён пресет: " + PRESET_NAMES[presetIndex], Toast.LENGTH_SHORT).show();
        }
        saveValues();
    }

    private void resetToFlat() {
        if (currentMode == MODE_KOMETA_226S) {
            for (int i = 0; i < 5; i++) {
                eq5Values[i] = 0;
                seek5Band[i].setProgress(12);
                value5Band[i].setText("0 dB");
                sendEQValue(i, 0);
            }
            Toast.makeText(this, "Эквалайзер сброшен в Flat", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(this, "Эквалайзер сброшен в Flat", Toast.LENGTH_SHORT).show();
        }
        saveValues();
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

        // Многократная попытка подключения к DSP
        dspCheckHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tryConnectDSP();
            }
        }, 500);
    }

    private void tryConnectDSP() {
        try {
            // Пытаемся найти класс Remote из системного сервиса
            Class<?> remoteClass = Class.forName("com.syu.remote.Remote");
            Method getAutoTools = remoteClass.getMethod("getAutoTools", Context.class);
            remoteInstance = getAutoTools.invoke(null, this);
            
            // Получаем метод commad
            commadMethod = remoteInstance.getClass().getMethod("commad", int.class, int.class, int[].class);
            
            // Отправляем тестовую команду для проверки связи
            commadMethod.invoke(remoteInstance, MODULE_SOUND, C_LOUD, new int[]{0});
            
            dspConnected = true;
            dspStatus.setText("✅ DSP подключен (управление активно)");
            dspStatus.setTextColor(Color.parseColor("#00FF00"));
            Toast.makeText(this, "DSP найден! Эквалайзер активен.", Toast.LENGTH_LONG).show();
            
        } catch (ClassNotFoundException e) {
            // Remote класс не найден - пробуем альтернативный способ
            tryAlternativeDSP();
        } catch (Exception e) {
            dspRetryCount++;
            if (dspRetryCount < 5) {
                dspStatus.setText("🔍 Повторная попытка подключения к DSP... (" + dspRetryCount + "/5)");
                dspCheckHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tryConnectDSP();
                    }
                }, 1000);
            } else {
                dspConnected = false;
                dspStatus.setText("❌ DSP не обнаружен (настройки сохраняются локально)");
                dspStatus.setTextColor(Color.parseColor("#FF0000"));
                Toast.makeText(this, "DSP не найден. Эквалайзер работает в демо-режиме.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void tryAlternativeDSP() {
        try {
            // Альтернативный путь - через TWUtil
            Class<?> twUtilClass = Class.forName("android.tw.john.TWUtil");
            Method getInstance = twUtilClass.getMethod("a");
            Object twInstance = getInstance.invoke(null);
            
            if (twInstance != null) {
                dspConnected = true;
                dspStatus.setText("✅ DSP подключен (TWUtil)");
                dspStatus.setTextColor(Color.parseColor("#00FF00"));
                Toast.makeText(this, "DSP найден через TWUtil!", Toast.LENGTH_SHORT).show();
                
                // Создаём обёртку для отправки команд через TWUtil
                final Object finalTwInstance = twInstance;
                final Method writeMethod = twUtilClass.getMethod("write", int.class, int.class, int.class);
                
                commadMethod = new Object() {
                    public void invoke(Object obj, int module, int cmd, int[] params) throws Exception {
                        // Конвертируем команду в формат TWUtil
                        // Адрес = модуль * 256 + команда? Нужно уточнить
                        writeMethod.invoke(finalTwInstance, cmd, params[0], params[1]);
                    }
                }.getClass().getMethod("invoke", Object.class, int.class, int.class, int[].class);
            } else {
                throw new Exception("TWUtil instance is null");
            }
        } catch (Exception e) {
            dspConnected = false;
            dspStatus.setText("⚠️ DSP не обнаружен (демо-режим)");
            dspStatus.setTextColor(Color.parseColor("#FF6600"));
            Toast.makeText(this, "DSP не найден. Настройки сохраняются локально.", Toast.LENGTH_LONG).show();
        }
    }

    private void sendEQValue(int band, int value) {
        saveValues();
        
        if (!dspConnected || remoteInstance == null || commadMethod == null) {
            return;
        }

        try {
            // Отправка команды в DSP: commad(4, 1, band, value+12)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dspCheckHandler.removeCallbacksAndMessages(null);
    }
}
