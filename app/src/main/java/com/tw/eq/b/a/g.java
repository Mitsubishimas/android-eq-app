package com.tw.eq.b.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import com.tw.eq.R;
import com.tw.eq.theme.i;
import com.tw.eq.view.BaseView;
import com.tw.eq.view.MasterMitsubishiView;

public final class g extends a<MasterMitsubishiView, com.tw.eq.e.a.f> implements MasterMitsubishiView {
    
    // Константы для режимов эквалайзера
    private static final int MODE_KOMETA_226S = 0;  // 5 полос
    private static final int MODE_KOMETA_225 = 1;   // 3 полосы
    
    // ID режимов для DSP (если нужно отправлять в DSP)
    private static final int DSP_MODE_KOMETA_226S = 14;
    private static final int DSP_MODE_KOMETA_225 = 15;
    
    // Предустановки для 5-полосного эквалайзера
    private static final int[][] PRESETS_5BAND = {
        {0, 0, 0, 0, 0},           // 0: Flat
        {2, 1, 0, 1, 2},           // 1: Classic
        {3, 2, 0, 2, 3},           // 2: Jazz
        {4, 2, 0, -1, -2},         // 3: Rock
        {2, 2, 1, 2, 3},           // 4: Pop
        {3, 1, -1, -2, -3},        // 5: Dance
        {1, 2, 3, 2, 1},           // 6: Vocal
        {-2, -1, 0, 1, 2}          // 7: Treble Boost
    };
    
    private static final String[] PRESET_NAMES_5BAND = {
        "Flat", "Classic", "Jazz", "Rock", "Pop", "Dance", "Vocal", "Treble Boost"
    };
    
    // Предустановки для 3-полосного эквалайзера (Bass, Mid, Treble)
    private static final int[][] PRESETS_3BAND = {
        {0, 0, 0},      // 0: Flat
        {3, 0, 2},      // 1: Classic
        {2, 1, 3},      // 2: Jazz
        {4, 0, -1},     // 3: Rock
        {2, 2, 3},      // 4: Pop
        {4, -1, -2},    // 5: Dance
        {1, 3, 2},      // 6: Vocal
        {-1, 0, 4}      // 7: Treble Boost
    };
    
    private static final String[] PRESET_NAMES_3BAND = {
        "Flat", "Classic", "Jazz", "Rock", "Pop", "Dance", "Vocal", "Treble Boost"
    };
    
    // Частоты для 5-полосного эквалайзера
    private static final int[] FREQUENCIES_5BAND = {63, 330, 2000, 6300, 15000};
    private static final String[] FREQ_NAMES_5BAND = {"63Hz", "330Hz", "2000Hz", "6300Hz", "15000Hz"};
    
    // UI элементы
    private Spinner modeSpinner;
    private Spinner presetSpinner;
    private SeekBar[] seekBars5Band;
    private TextView[] seekValues5Band;
    private SeekBar bassSeek, midSeek, trebleSeek;
    private TextView bassValue, midValue, trebleValue;
    private View layout5Band;
    private View layout3Band;
    private TextView titleFrequency;
    
    private int currentMode = MODE_KOMETA_226S;
    private int[] currentValues5Band = new int[5];
    private int[] currentValues3Band = new int[3];
    private boolean fromPreset = false;
    
    @Override
    public final BaseView a() {
        return this;
    }
    
    @Override
    public final com.tw.eq.e.a b() {
        return new com.tw.eq.e.a.f(getActivity());
    }
    
    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_master_mitsubishi, container, false);
        
        // Инициализация UI
        modeSpinner = view.findViewById(R.id.mode_spinner);
        presetSpinner = view.findViewById(R.id.preset_spinner);
        layout5Band = view.findViewById(R.id.layout_5band);
        layout3Band = view.findViewById(R.id.layout_3band);
        titleFrequency = view.findViewById(R.id.title_frequency);
        
        // Инициализация 5-полосных SeekBar
        seekBars5Band = new SeekBar[5];
        seekValues5Band = new TextView[5];
        int[] seekIds = {R.id.seek_63hz, R.id.seek_330hz, R.id.seek_2000hz, R.id.seek_6300hz, R.id.seek_15000hz};
        int[] valueIds = {R.id.value_63hz, R.id.value_330hz, R.id.value_2000hz, R.id.value_6300hz, R.id.value_15000hz};
        
        for (int i = 0; i < 5; i++) {
            final int index = i;
            seekBars5Band[i] = view.findViewById(seekIds[i]);
            seekValues5Band[i] = view.findViewById(valueIds[i]);
            seekBars5Band[i].setMax(24);  // -12 до +12 (0 = -12, 12 = 0, 24 = +12)
            seekBars5Band[i].setProgress(12); // начальное значение 0
            seekBars5Band[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && !fromPreset) {
                        int value = progress - 12;
                        currentValues5Band[index] = value;
                        seekValues5Band[index].setText((value > 0 ? "+" : "") + value + " dB");
                        onEqValueChange5Band(index, value);
                    }
                }
                @Override public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override public void onStopTrackingTouch(SeekBar seekBar) {}
            });
            currentValues5Band[i] = 0;
            seekValues5Band[i].setText("0 dB");
        }
        
        // Инициализация 3-полосных SeekBar
        bassSeek = view.findViewById(R.id.seek_bass);
        midSeek = view.findViewById(R.id.seek_mid);
        trebleSeek = view.findViewById(R.id.seek_treble);
        bassValue = view.findViewById(R.id.value_bass);
        midValue = view.findViewById(R.id.value_mid);
        trebleValue = view.findViewById(R.id.value_treble);
        
        bassSeek.setMax(24);
        midSeek.setMax(24);
        trebleSeek.setMax(24);
        bassSeek.setProgress(12);
        midSeek.setProgress(12);
        trebleSeek.setProgress(12);
        
        SeekBar.OnSeekBarChangeListener listener3Band = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && !fromPreset) {
                    int value = progress - 12;
                    if (seekBar.getId() == R.id.seek_bass) {
                        currentValues3Band[0] = value;
                        bassValue.setText((value > 0 ? "+" : "") + value + " dB");
                        onEqValueChange3Band(0, value);
                    } else if (seekBar.getId() == R.id.seek_mid) {
                        currentValues3Band[1] = value;
                        midValue.setText((value > 0 ? "+" : "") + value + " dB");
                        onEqValueChange3Band(1, value);
                    } else if (seekBar.getId() == R.id.seek_treble) {
                        currentValues3Band[2] = value;
                        trebleValue.setText((value > 0 ? "+" : "") + value + " dB");
                        onEqValueChange3Band(2, value);
                    }
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        };
        
        bassSeek.setOnSeekBarChangeListener(listener3Band);
        midSeek.setOnSeekBarChangeListener(listener3Band);
        trebleSeek.setOnSeekBarChangeListener(listener3Band);
        
        // Настройка спиннера выбора режима
        String[] modes = {getString(R.string.kometa_226s), getString(R.string.kometa_225)};
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, modes);
        modeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(modeAdapter);
        modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentMode = position;
                if (position == MODE_KOMETA_226S) {
                    layout5Band.setVisibility(View.VISIBLE);
                    layout3Band.setVisibility(View.GONE);
                    titleFrequency.setText(R.string.eq_5band);
                    // Уведомляем DSP о смене режима
                    if (com.tw.eq.e.a.d != null) {
                        com.tw.eq.e.a.d.write(257, 0, DSP_MODE_KOMETA_226S);
                    }
                    loadSavedValues5Band();
                } else {
                    layout5Band.setVisibility(View.GONE);
                    layout3Band.setVisibility(View.VISIBLE);
                    titleFrequency.setText(R.string.eq_3band);
                    if (com.tw.eq.e.a.d != null) {
                        com.tw.eq.e.a.d.write(257, 0, DSP_MODE_KOMETA_225);
                    }
                    loadSavedValues3Band();
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        
        // Настройка спиннера предустановок
        updatePresetSpinner();
        presetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    fromPreset = true;
                    if (currentMode == MODE_KOMETA_226S) {
                        applyPreset5Band(position - 1);
                    } else {
                        applyPreset3Band(position - 1);
                    }
                    fromPreset = false;
                    presetSpinner.setSelection(0);
                }
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
        
        // Кнопка сброса
        view.findViewById(R.id.reset_eq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetToFlat();
            }
        });
        
        // Загрузка сохранённых значений
        loadSavedValues5Band();
        loadSavedValues3Band();
        
        return view;
    }
    
    private void updatePresetSpinner() {
        String[] presetNames = (currentMode == MODE_KOMETA_226S) ? PRESET_NAMES_5BAND : PRESET_NAMES_3BAND;
        String[] items = new String[presetNames.length + 1];
        items[0] = "-- " + getString(R.string.select_preset) + " --";
        System.arraycopy(presetNames, 0, items, 1, presetNames.length);
        ArrayAdapter<String> presetAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, items);
        presetAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        presetSpinner.setAdapter(presetAdapter);
    }
    
    private void applyPreset5Band(int presetIndex) {
        int[] values = PRESETS_5BAND[presetIndex];
        for (int i = 0; i < 5; i++) {
            currentValues5Band[i] = values[i];
            seekBars5Band[i].setProgress(values[i] + 12);
            seekValues5Band[i].setText((values[i] > 0 ? "+" : "") + values[i] + " dB");
            onEqValueChange5Band(i, values[i]);
        }
    }
    
    private void applyPreset3Band(int presetIndex) {
        int[] values = PRESETS_3BAND[presetIndex];
        currentValues3Band[0] = values[0];
        currentValues3Band[1] = values[1];
        currentValues3Band[2] = values[2];
        bassSeek.setProgress(values[0] + 12);
        midSeek.setProgress(values[1] + 12);
        trebleSeek.setProgress(values[2] + 12);
        bassValue.setText((values[0] > 0 ? "+" : "") + values[0] + " dB");
        midValue.setText((values[1] > 0 ? "+" : "") + values[1] + " dB");
        trebleValue.setText((values[2] > 0 ? "+" : "") + values[2] + " dB");
        onEqValueChange3Band(0, values[0]);
        onEqValueChange3Band(1, values[1]);
        onEqValueChange3Band(2, values[2]);
    }
    
    private void resetToFlat() {
        fromPreset = true;
        if (currentMode == MODE_KOMETA_226S) {
            for (int i = 0; i < 5; i++) {
                currentValues5Band[i] = 0;
                seekBars5Band[i].setProgress(12);
                seekValues5Band[i].setText("0 dB");
                onEqValueChange5Band(i, 0);
            }
        } else {
            currentValues3Band[0] = 0;
            currentValues3Band[1] = 0;
            currentValues3Band[2] = 0;
            bassSeek.setProgress(12);
            midSeek.setProgress(12);
            trebleSeek.setProgress(12);
            bassValue.setText("0 dB");
            midValue.setText("0 dB");
            trebleValue.setText("0 dB");
            onEqValueChange3Band(0, 0);
            onEqValueChange3Band(1, 0);
            onEqValueChange3Band(2, 0);
        }
        fromPreset = false;
    }
    
    private void onEqValueChange5Band(int band, int value) {
        // Отправка значения в DSP
        // Адреса для 5-полосного эквалайзера: 52-56
        if (com.tw.eq.e.a.d != null) {
            com.tw.eq.e.a.d.write(257, 52 + band, value + 12);
        }
        // Сохранение в SharedPreferences
        saveValues5Band();
    }
    
    private void onEqValueChange3Band(int band, int value) {
        // Отправка значения в DSP
        // Адреса для 3-полосного эквалайзера: 57-59
        if (com.tw.eq.e.a.d != null) {
            com.tw.eq.e.a.d.write(257, 57 + band, value + 12);
        }
        // Сохранение в SharedPreferences
        saveValues3Band();
    }
    
    private void saveValues5Band() {
        SharedPreferences prefs = getActivity().getSharedPreferences("master_mitsubishi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (int i = 0; i < 5; i++) {
            editor.putInt("eq5_" + i, currentValues5Band[i]);
        }
        editor.apply();
    }
    
    private void saveValues3Band() {
        SharedPreferences prefs = getActivity().getSharedPreferences("master_mitsubishi", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("eq3_bass", currentValues3Band[0]);
        editor.putInt("eq3_mid", currentValues3Band[1]);
        editor.putInt("eq3_treble", currentValues3Band[2]);
        editor.apply();
    }
    
    private void loadSavedValues5Band() {
        SharedPreferences prefs = getActivity().getSharedPreferences("master_mitsubishi", Context.MODE_PRIVATE);
        for (int i = 0; i < 5; i++) {
            currentValues5Band[i] = prefs.getInt("eq5_" + i, 0);
            seekBars5Band[i].setProgress(currentValues5Band[i] + 12);
            seekValues5Band[i].setText((currentValues5Band[i] > 0 ? "+" : "") + currentValues5Band[i] + " dB");
        }
    }
    
    private void loadSavedValues3Band() {
        SharedPreferences prefs = getActivity().getSharedPreferences("master_mitsubishi", Context.MODE_PRIVATE);
        currentValues3Band[0] = prefs.getInt("eq3_bass", 0);
        currentValues3Band[1] = prefs.getInt("eq3_mid", 0);
        currentValues3Band[2] = prefs.getInt("eq3_treble", 0);
        bassSeek.setProgress(currentValues3Band[0] + 12);
        midSeek.setProgress(currentValues3Band[1] + 12);
        trebleSeek.setProgress(currentValues3Band[2] + 12);
        bassValue.setText((currentValues3Band[0] > 0 ? "+" : "") + currentValues3Band[0] + " dB");
        midValue.setText((currentValues3Band[1] > 0 ? "+" : "") + currentValues3Band[1] + " dB");
        trebleValue.setText((currentValues3Band[2] > 0 ? "+" : "") + currentValues3Band[2] + " dB");
    }
    
    @Override
    public void a(i iVar) {
        super.a(iVar);
        // Применение темы
        if (iVar != null && iVar.b != null) {
            int color = iVar.b.i;
            if (color != 0) {
                if (titleFrequency != null) titleFrequency.setTextColor(color);
                for (TextView tv : seekValues5Band) {
                    if (tv != null) tv.setTextColor(color);
                }
                if (bassValue != null) bassValue.setTextColor(color);
                if (midValue != null) midValue.setTextColor(color);
                if (trebleValue != null) trebleValue.setTextColor(color);
            }
        }
    }
    
    @Override
    public void onResume() {
        super.onResume();
        loadSavedValues5Band();
        loadSavedValues3Band();
    }
}
