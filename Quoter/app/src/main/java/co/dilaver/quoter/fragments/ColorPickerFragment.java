/*
 * Copyright 2016 Mehmet Dilaveroğlu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.dilaver.quoter.fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.larswerkman.lobsterpicker.LobsterPicker;
import com.larswerkman.lobsterpicker.OnColorListener;
import com.larswerkman.lobsterpicker.sliders.LobsterOpacitySlider;
import com.larswerkman.lobsterpicker.sliders.LobsterShadeSlider;

import co.dilaver.quoter.R;


public class ColorPickerFragment extends DialogFragment {

    ColorChangeListener colorChangeListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.str_ChooseTextColor));

        int oldColor = getArguments().getInt("color");

        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_color_picker, null);
        builder.setView(view);

        final LobsterPicker lobsterPicker = (LobsterPicker) view.findViewById(R.id.lobsterpicker);
        LobsterShadeSlider shadeSlider = (LobsterShadeSlider) view.findViewById(R.id.shadeslider);
        LobsterOpacitySlider opacitySlider = (LobsterOpacitySlider) view.findViewById(R.id.opacityslider);

        lobsterPicker.setColor(oldColor);
        lobsterPicker.setHistory(oldColor);

        lobsterPicker.addDecorator(shadeSlider);
        lobsterPicker.addDecorator(opacitySlider);
        lobsterPicker.addOnColorListener(new OnColorListener() {
            @Override
            public void onColorChanged(int color) {
                lobsterPicker.setHistory(color);
            }

            @Override
            public void onColorSelected(int color) {

            }
        });

        builder.setPositiveButton(getString(R.string.str_OK), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (colorChangeListener != null){
                    colorChangeListener.onColorChanged(lobsterPicker.getColor());
                }
                dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.str_Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();

    }

    public void setColorChangeListener(ColorChangeListener colorChangeListener){
        this.colorChangeListener = colorChangeListener;
    }

    public interface ColorChangeListener{
        void onColorChanged(int colorValue);
    }
}
