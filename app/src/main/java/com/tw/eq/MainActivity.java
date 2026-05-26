package com.tw.eq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.tw.eq.a.d;

public class MainActivity extends AppCompatActivity {

    // Константы для режимов
    private static final int MODE_KOMETA_226S = 0;
    private static final int MODE_KOMETA_225 = 1;
    
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
    
    // Частоты для 5-полосного EQ
    private static final String[] FREQ_5BAND = {"63 Hz", "330 Hz", "2000 Hz", "6300 Hz", "15000 Hz"};
    
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
    
    private int currentMode = MODE_KOMETA_226S;
    private int[] eq5Values = new int[5];
    private int[] eq3Values = new int[3];
    private SharedPreferences prefs;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        prefs = getSharedPreferences("master_mitsubishi_eq", MODE_PRIVATE);
        
        initUI();
        loadSavedValues();
        setupListeners();
    }
    
    private void initUI() {
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
                        sendToDSP(52 + index, value + 12);
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
        
        SeekBar.OnSeekBarChangeListener listener3 = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    int value = progress - 12;
                    if (seekBar.getId() == R.id.seek_bass) {
                        eq3Values[0] = value;
                        valueBass.setText((value > 0 ? "+" : "") + value + " dB");
                        sendToDSP(57, value + 12);
                    } else if (seekBar.getId() == R.id.seek_mid) {
                        eq3Values[1] = value;
                        valueMid.setText((value > 0 ? "+" : "") + value + " dB");
                        sendToDSP(58, value + 12);
                    } else if (seekBar.getId() == R.id.seek_treble) {
                        eq3Values[2] = value;
                        valueTreble.setText((value > 0 ? "+" : "") + value + " dB");
                        sendToDSP(59, value + 12);
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
        
        // Настройка спиннеров
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
        
        // Предустановки
        ArrayAdapter<String> presetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PRESET_NAMES);
        presetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presetSpinner.setAdapter(presetAdapter);
        presetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    applyPreset(position);
                    presetSpinner.setSelection(0);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        
        // Кнопка сброса
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetToFlat();
            }
        });
    }
    
    private void applyPreset(int presetIndex) {
        if (currentMode == MODE_KOMETA_226S) {
            int[] values = PRESETS_5BAND[presetIndex - 1];
            for (int i = 0; i < 5; i++) {
                eq5Values[i] = values[i];
                seek5Band[i].setProgress(values[i] + 12);
                value5Band[i].setText((values[i] > 0 ? "+" : "") + values[i] + " dB");
                sendToDSP(52 + i, values[i] + 12);
            }
        } else {
            // Для 3-полосного можно добавить свои пресеты
            int[] values = PRESETS_5BAND[presetIndex - 1];
            eq3Values[0] = values[0];
            eq3Values[1] = values[2];
            eq3Values[2] = values[4];
            seekBass.setProgress(values[0] + 12);
            seekMid.setProgress(values[2] + 12);
            seekTreble.setProgress(values[4] + 12);
            valueBass.setText((values[0] > 0 ? "+" : "") + values[0] + " dB");
            valueMid.setText((values[2] > 0 ? "+" : "") + values[2] + " dB");
            valueTreble.setText((values[4] > 0 ? "+" : "") + values[4] + " dB");
            sendToDSP(57, values[0] + 12);
            sendToDSP(58, values[2] + 12);
            sendToDSP(59, values[4] + 12);
        }
        saveValues();
        Toast.makeText(this, "Пресет применён", Toast.LENGTH_SHORT).show();
    }
    
    private void resetToFlat() {
        if (currentMode == MODE_KOMETA_226S) {
            for (int i = 0; i < 5; i++) {
                eq5Values[i] = 0;
                seek5Band[i].setProgress(12);
                value5Band[i].setText("0 dB");
                sendToDSP(52 + i, 12);
            }
        } else {
            eq3Values[0] = eq3Values[1] = eq3Values[2] = 0;
            seekBass.setProgress(12);
            seekMid.setProgress(12);
            seekTreble.setProgress(12);
            valueBass.setText("0 dB");
            valueMid.setText("0 dB");
            valueTreble.setText("0 dB");
            sendToDSP(57, 12);
            sendToDSP(58, 12);
            sendToDSP(59, 12);
        }
        saveValues();
        Toast.makeText(this, "Сброшено в Flat", Toast.LENGTH_SHORT).show();
    }
    
    private void sendToDSP(int address, int value) {
        // Отправка в DSP через TWUtil (если доступен)
        d dsp = d.a();
        if (dsp != null) {
            dsp.write(257, address, value);
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
        seekBass.setProgress(eq3Values[0] + 12);
        seekMid.setProgress(eq3Values[1] + 12);
        seekTreble.setProgress(eq3Values[2] + 12);
        valueBass.setText((eq3Values[0] > 0 ? "+" : "") + eq3Values[0] + " dB");
        valueMid.setText((eq3Values[1] > 0 ? "+" : "") + eq3Values[1] + " dB");
        valueTreble.setText((eq3Values[2] > 0 ? "+" : "") + eq3Values[2] + " dB");
    }
    
    private void setupListeners() {
        // Вызов DSP при запуске
        d dsp = d.a();
        if (dsp != null) {
            dsp.open(new short[]{257});
            dsp.start();
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        d dsp = d.a();
        if (dsp != null) {
            dsp.close();
        }
    }
}
