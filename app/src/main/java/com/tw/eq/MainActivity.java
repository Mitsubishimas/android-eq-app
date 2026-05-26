package com.tw.eq;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    // Разрешения
    private static final int REQUEST_PERMISSIONS = 100;
    
    // Режимы эквалайзера
    private static final int MODE_KOMETA_226S = 0;
    private static final int MODE_KOMETA_225 = 1;
    
    // Типы DSP для Teyes C33
    private static final int DSP_TYPE_UNKNOWN = 0;
    private static final int DSP_TYPE_ADAU1701 = 1;
    private static final int DSP_TYPE_ROHM32107 = 2;
    private static final int DSP_TYPE_BOTH = 3;
    
    // I2C адреса для Teyes C33
    private static final int I2C_BUS = 1;
    private static final int I2C_ADDR_ADAU1701 = 0x34;
    private static final int I2C_ADDR_ROHM32107 = 0x38;
    
    // Регистры ADAU1701
    private static final int ADAU1701_REG_EQ_START = 0x40;
    private static final int ADAU1701_REG_STATUS = 0x00;
    
    // Регистры Rohm32107
    private static final int ROHM_REG_EQ_START = 0x20;
    private static final int ROHM_REG_STATUS = 0x00;
    
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
    
    // UI элементы
    private Spinner modeSpinner;
    private Spinner presetSpinner;
    private View layout5Band;
    private View layout3Band;
    private SeekBar[] seek5Band = new SeekBar[5];
    private TextView[] value5Band = new TextView[5];
    private SeekBar seekBass, seekMid, seekTreble;
    private TextView valueBass, valueMid, valueTreble;
    private Button resetButton;
    private TextView dspStatusText;
    private TextView dspInfoText;
    private TextView titleText;
    
    private int currentMode = MODE_KOMETA_226S;
    private int[] eq5Values = new int[5];
    private int[] eq3Values = new int[3];
    private SharedPreferences prefs;
    
    // DSP статус
    private int dspType = DSP_TYPE_UNKNOWN;
    private boolean dspConnected = false;
    private String dspFirmwareVersion = "";
    
    // Handler для UI обновлений
    private Handler mainHandler = new Handler();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Запрос разрешений
        checkAndRequestPermissions();
        
        prefs = getSharedPreferences("master_mitsubishi_eq", MODE_PRIVATE);
        
        initUI();
        loadSavedValues();
        setupListeners();
        
        // Проверка DSP в отдельном потоке
        new Thread(this::detectDSP).start();
    }
    
    private void checkAndRequestPermissions() {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions = new String[]{
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        } else {
            permissions = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            };
        }
        
        boolean allGranted = true;
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }
        
        if (!allGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }
    }
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }
            if (allGranted) {
                Toast.makeText(this, "Разрешения получены", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Некоторые разрешения не получены", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    private void initUI() {
        titleText = findViewById(R.id.title_text);
        dspStatusText = findViewById(R.id.dsp_status);
        dspInfoText = findViewById(R.id.dsp_info);
        modeSpinner = findViewById(R.id.mode_spinner);
        presetSpinner = findViewById(R.id.preset_spinner);
        layout5Band = findViewById(R.id.layout_5band);
        layout3Band = findViewById(R.id.layout_3band);
        resetButton = findViewById(R.id.reset_eq);
        
        // 5-полосные SeekBar
        int[] seekIds = {R.id.seek_63hz, R.id.seek_330hz, R.id.seek_2000hz, R.id.seek_6300hz, R.id.seek_15000hz};
        int[] valueIds = {R.id.value_63hz, R.id.value_330hz, R.id.value_2000hz, R.id.value_6300hz, R.id.value_15000hz};
        
        for (int i = 0; i < 5; i++) {
            final int index = i;
            seek5Band[i] = findViewById(seekIds[i]);
            value5Band[i] = findViewById(valueIds[i]);
            seek5Band[i].setMax(24);
            seek5Band[i].setProgress(12);
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
        }
        
        // 3-полосные SeekBar
        seekBass = findViewById(R.id.seek_bass);
        seekMid = findViewById(R.id.seek_mid);
        seekTreble = findViewById(R.id.seek_treble);
        valueBass = findViewById(R.id.value_bass);
        valueMid = findViewById(R.id.value_mid);
        valueTreble = findViewById(R.id.value_treble);
        
        seekBass.setMax(24);
        seekMid.setMax(24);
        seekTreble.setMax(24);
        seekBass.setProgress(12);
        seekMid.setProgress(12);
        seekTreble.setProgress(12);
        
        SeekBar.OnSeekBarChangeListener listener3 = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int value = progress - 12;
                    if (seekBar.getId() == R.id.seek_bass) {
                        eq3Values[0] = value;
                        valueBass.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(0, value);
                    } else if (seekBar.getId() == R.id.seek_mid) {
                        eq3Values[1] = value;
                        valueMid.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(1, value);
                    } else if (seekBar.getId() == R.id.seek_treble) {
                        eq3Values[2] = value;
                        valueTreble.setText((value > 0 ? "+" : "") + value + " dB");
                        sendEQValue(2, value);
                    }
                    saveValues();
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };
        
        seekBass.setOnSeekBarChangeListener(listener3);
        seekMid.setOnSeekBarChangeListener(listener3);
        seekTreble.setOnSeekBarChangeListener(listener3);
        
        // Настройка спиннера выбора режима
        String[] modes = {"Комета 226С (5 полос)", "Комета 225 (3 полосы)"};
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, modes);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentMode = position;
                if (position == MODE_KOMETA_226S) {
                    layout5Band.setVisibility(View.VISIBLE);
                    layout3Band.setVisibility(View.GONE);
                } else {
                    layout5Band.setVisibility(View.GONE);
                    layout3Band.setVisibility(View.VISIBLE);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        
        // Настройка спиннера предустановок
        String[] presetItems = new String[PRESET_NAMES.length + 1];
        presetItems[0] = "-- Выберите предустановку --";
        System.arraycopy(PRESET_NAMES, 0, presetItems, 1, PRESET_NAMES.length);
        ArrayAdapter<String> presetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, presetItems);
        presetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presetSpinner.setAdapter(presetAdapter);
        presetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    applyPreset(position - 1);
                    presetSpinner.setSelection(0);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        
        resetButton.setOnClickListener(v -> resetToFlat());
    }
    
    /**
     * Определение типа DSP на Teyes C33
     * Проверяем наличие ADAU1701 и Rohm32107
     */
    private void detectDSP() {
        mainHandler.post(() -> {
            dspStatusText.setText("🔍 Проверка DSP...");
            dspStatusText.setTextColor(0xFFFFA500);
            dspInfoText.setText("");
        });
        
        boolean adau1701Found = false;
        boolean rohm32107Found = false;
        String adauVersion = "";
        String rohmVersion = "";
        
        // 1. Проверка через системный TWUtil
        try {
            Class<?> twUtilClass = Class.forName("android.tw.john.TWUtil");
            Object twInstance = twUtilClass.getMethod("a").invoke(null);
            if (twInstance != null) {
                mainHandler.post(() -> dspInfoText.append("TWUtil найден\n"));
                // Отправляем тестовую команду
                int result = (int) twUtilClass.getMethod("write", int.class, int.class)
                    .invoke(twInstance, 65521, 0);
                if (result == 35) {
                    mainHandler.post(() -> dspInfoText.append("TWUtil ответ: OK\n"));
                }
            }
        } catch (Exception e) {
            mainHandler.post(() -> dspInfoText.append("TWUtil не найден\n"));
        }
        
        // 2. Проверка ADAU1701 через I2C
        adau1701Found = checkI2CDevice(I2C_ADDR_ADAU1701);
        if (adau1701Found) {
            adauVersion = readADAU1701Version();
            mainHandler.post(() -> dspInfoText.append("✅ ADAU1701 обнаружен\n"));
            if (!adauVersion.isEmpty()) {
                mainHandler.post(() -> dspInfoText.append("   Версия: " + adauVersion + "\n"));
            }
        } else {
            mainHandler.post(() -> dspInfoText.append("❌ ADAU1701 не найден\n"));
        }
        
        // 3. Проверка Rohm32107 через I2C
        rohm32107Found = checkI2CDevice(I2C_ADDR_ROHM32107);
        if (rohm32107Found) {
            rohmVersion = readRohmVersion();
            mainHandler.post(() -> dspInfoText.append("✅ Rohm32107 обнаружен\n"));
            if (!rohmVersion.isEmpty()) {
                mainHandler.post(() -> dspInfoText.append("   Версия: " + rohmVersion + "\n"));
            }
        } else {
            mainHandler.post(() -> dspInfoText.append("❌ Rohm32107 не найден\n"));
        }
        
        // 4. Проверка через sysfs
        if (!adau1701Found) {
            adau1701Found = checkSysfsDevice("adau");
            if (adau1701Found) mainHandler.post(() -> dspInfoText.append("✅ ADAU1701 (sysfs)\n"));
        }
        if (!rohm32107Found) {
            rohm32107Found = checkSysfsDevice("rohm");
            if (rohm32107Found) mainHandler.post(() -> dspInfoText.append("✅ Rohm32107 (sysfs)\n"));
        }
        
        // 5. Определяем итоговый тип DSP
        if (adau1701Found && rohm32107Found) {
            dspType = DSP_TYPE_BOTH;
            dspConnected = true;
            mainHandler.post(() -> {
                dspStatusText.setText("✅ Teyes C33 DSP готов");
                dspStatusText.setTextColor(0xFF00FF00);
                dspInfoText.append("\n🎵 Эквалайзер работает через Rohm32107\n");
                dspInfoText.append("🔊 Обработка звука через ADAU1701\n");
                Toast.makeText(this, "DSP Teyes C33 готов к работе!", Toast.LENGTH_SHORT).show();
            });
        } else if (rohm32107Found) {
            dspType = DSP_TYPE_ROHM32107;
            dspConnected = true;
            mainHandler.post(() -> {
                dspStatusText.setText("✅ Rohm32107 (эквалайзер)");
                dspStatusText.setTextColor(0xFF00FF00);
            });
        } else if (adau1701Found) {
            dspType = DSP_TYPE_ADAU1701;
            dspConnected = true;
            mainHandler.post(() -> {
                dspStatusText.setText("✅ ADAU1701 (обработка звука)");
                dspStatusText.setTextColor(0xFF00FF00);
            });
        } else {
            dspConnected = false;
            mainHandler.post(() -> {
                dspStatusText.setText("⚠️ DSP не обнаружен\nДемо-режим");
                dspStatusText.setTextColor(0xFFFF0000);
                dspInfoText.append("\n⚠️ Работа в демо-режиме\n");
                Toast.makeText(this, "DSP не найден. Настройки сохраняются локально.", Toast.LENGTH_LONG).show();
            });
        }
    }
    
    /**
     * Проверка I2C устройства по адресу
     */
    private boolean checkI2CDevice(int address) {
        try {
            String hexAddr = Integer.toHexString(address);
            Process process = Runtime.getRuntime().exec(
                new String[]{"sh", "-c", "i2cdetect -y " + I2C_BUS + " | grep " + hexAddr}
            );
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            reader.close();
            return line != null && line.contains(hexAddr);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Проверка устройства через sysfs
     */
    private boolean checkSysfsDevice(String name) {
        try {
            File sysfsDir = new File("/sys/class/i2c-dev/i2c-" + I2C_BUS + "/device");
            if (sysfsDir.exists()) {
                File[] files = sysfsDir.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.getName().toLowerCase().contains(name)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Чтение версии ADAU1701
     */
    private String readADAU1701Version() {
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sh", "-c", "i2cget -y " + I2C_BUS + " " + Integer.toHexString(I2C_ADDR_ADAU1701) + " 0x1C"}
            );
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = reader.readLine();
            reader.close();
            return result != null ? result : "";
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Чтение версии Rohm32107
     */
    private String readRohmVersion() {
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"sh", "-c", "i2cget -y " + I2C_BUS + " " + Integer.toHexString(I2C_ADDR_ROHM32107) + " 0x00"}
            );
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = reader.readLine();
            reader.close();
            return result != null ? "0x" + result : "";
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * Отправка значения эквалайзера в DSP
     * Для Teyes C33 используем Rohm32107 (адреса 0x20-0x24 для 5 полос)
     */
    private void sendEQValue(int band, int value) {
        if (!dspConnected) return;
        
        int regAddress;
        if (dspType == DSP_TYPE_BOTH || dspType == DSP_TYPE_ROHM32107) {
            // Rohm32107: регистры 0x20-0x24
            regAddress = ROHM_REG_EQ_START + band;
            sendI2CCommand(I2C_ADDR_ROHM32107, regAddress, value + 12);
        } else if (dspType == DSP_TYPE_ADAU1701) {
            // ADAU1701: регистры 0x40-0x44
            regAddress = ADAU1701_REG_EQ_START + band;
            sendI2CCommand(I2C_ADDR_ADAU1701, regAddress, value + 12);
        }
    }
    
    /**
     * Отправка I2C команды
     */
    private void sendI2CCommand(int address, int reg, int value) {
        try {
            String cmd = String.format("i2cset -y %d 0x%02x 0x%02x 0x%02x", 
                I2C_BUS, address, reg, value);
            Runtime.getRuntime().exec(new String[]{"sh", "-c", cmd});
        } catch (Exception e) {
            // Пробуем альтернативный метод через TWUtil
            sendViaTWUtil(address, reg, value);
        }
    }
    
    /**
     * Отправка через TWUtil (альтернативный метод)
     */
    private void sendViaTWUtil(int address, int reg, int value) {
        try {
            Class<?> twUtilClass = Class.forName("android.tw.john.TWUtil");
            Object twInstance = twUtilClass.getMethod("a").invoke(null);
            twUtilClass.getMethod("write", int.class, int.class, int.class)
                .invoke(twInstance, address, reg, value);
        } catch (Exception e) {
            // Игнорируем - DSP не доступен
        }
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
        } else {
            // Для 3-полосного преобразуем пресет
            int[] values = PRESETS_5BAND[presetIndex];
            eq3Values[0] = values[0];
            eq3Values[1] = values[2];
            eq3Values[2] = values[4];
            seekBass.setProgress(values[0] + 12);
            seekMid.setProgress(values[2] + 12);
            seekTreble.setProgress(values[4] + 12);
            valueBass.setText((values[0] > 0 ? "+" : "") + values[0] + " dB");
            valueMid.setText((values[2] > 0 ? "+" : "") + values[2] + " dB");
            valueTreble.setText((values[4] > 0 ? "+" : "") + values[4] + " dB");
            sendEQValue(0, values[0]);
            sendEQValue(1, values[2]);
            sendEQValue(2, values[4]);
        }
        saveValues();
        Toast.makeText(this, "Пресет: " + PRESET_NAMES[presetIndex], Toast.LENGTH_SHORT).show();
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
            seekBass.setProgress(12);
            seekMid.setProgress(12);
            seekTreble.setProgress(12);
            valueBass.setText("0 dB");
            valueMid.setText("0 dB");
            valueTreble.setText("0 dB");
            sendEQValue(0, 0);
            sendEQValue(1, 0);
            sendEQValue(2, 0);
        }
        saveValues();
        Toast.makeText(this, "Сброшено в Flat", Toast.LENGTH_SHORT).show();
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
        seekBass.setProgress(eq3Values[0] + 12);
        seekMid.setProgress(eq3Values[1] + 12);
        seekTreble.setProgress(eq3Values[2] + 12);
        valueBass.setText((eq3Values[0] > 0 ? "+" : "") + eq3Values[0] + " dB");
        valueMid.setText((eq3Values[1] > 0 ? "+" : "") + eq3Values[1] + " dB");
        valueTreble.setText((eq3Values[2] > 0 ? "+" : "") + eq3Values[2] + " dB");
    }
    
    private void setupListeners() {
        // Дополнительная настройка
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Закрываем соединение с DSP если нужно
        try {
            Class<?> twUtilClass = Class.forName("android.tw.john.TWUtil");
            Object twInstance = twUtilClass.getMethod("a").invoke(null);
            twUtilClass.getMethod("close").invoke(twInstance);
        } catch (Exception e) {
            // Игнорируем
        }
    }
}
