package co.dilaver.quoter.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thebluealliance.spectrum.SpectrumDialog;

import co.dilaver.quoter.R;
import co.dilaver.quoter.adapters.TextFontAdapter;
import co.dilaver.quoter.constants.Fonts;
import co.dilaver.quoter.helper.CircleView;
import co.dilaver.quoter.storage.SharedPrefStorage;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    LinearLayout qodTextFontSetting;
    LinearLayout qodTextColorSetting;
    TextView qodTextFontSettingFeedback;
    CircleView qodTextColorSettingFeedback;

    SharedPrefStorage storage;
    Typeface font;

    private TextFontAdapter textFontAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        storage = new SharedPrefStorage(getActivity());
        font = Typeface.createFromAsset(getActivity().getAssets(), storage.getQodFont());

        textFontAdapter = new TextFontAdapter(getActivity());

        qodTextFontSetting = (LinearLayout) view.findViewById(R.id.qodTextFontSetting);
        qodTextColorSetting = (LinearLayout) view.findViewById(R.id.qodTextColorSetting);
        qodTextFontSettingFeedback = (TextView) view.findViewById(R.id.qodTextFontSettingFeedback);
        qodTextColorSettingFeedback = (CircleView) view.findViewById(R.id.qodTextColorSettingFeedback);

        qodTextFontSetting.setOnClickListener(this);
        qodTextColorSetting.setOnClickListener(this);

        qodTextFontSettingFeedback.setTypeface(font);
        qodTextColorSettingFeedback.setCircleColor(storage.getQodColor());

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == qodTextFontSetting ){
            showFontSelectionDialog();
        }else if ( v == qodTextColorSetting ){

            new SpectrumDialog.Builder(getActivity())
                    .setColors(R.array.demo_colors)
                    .setDismissOnColorSelected(true)
                    .setOutlineWidth(2)
                    .setSelectedColor(storage.getQodColor())
                    .setOnColorSelectedListener(new SpectrumDialog.OnColorSelectedListener() {
                        @Override public void onColorSelected(boolean positiveResult, @ColorInt int color) {
                            if (positiveResult) {
                                qodTextColorSettingFeedback.setCircleColor(color);
                                storage.setQodColor(color);
                            } else {

                            }
                        }
                    }).build().show(getActivity().getSupportFragmentManager(), "dialog_demo_1");
        }

    }

    private void showFontSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setAdapter(textFontAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String textFontString = "";
                switch (which) {
                    case Fonts.ALEXBRUSH_REGULAR:
                        textFontString = "AlexBrush-Regular.ttf";
                        break;
                    case Fonts.ALLER_LT:
                        textFontString = "Aller_Lt.ttf";
                        break;
                    case Fonts.ALLURA_REGULAR:
                        textFontString = "Allura-Regular.otf";
                        break;
                    case Fonts.AMATICSC_REGULAR:
                        textFontString = "AmaticSC-Regular.ttf";
                        break;
                    case Fonts.ANTONIO_REGULAR:
                        textFontString = "Antonio-Regular.ttf";
                        break;
                    case Fonts.ARIZONIA_REGULAR:
                        textFontString = "Arizonia-Regular.ttf";
                        break;
                    case Fonts.BEBAS___:
                        textFontString = "BEBAS___.ttf";
                        break;
                    case Fonts.CAPTURE_IT:
                        textFontString = "Capture_it.ttf";
                        break;
                    case Fonts.CAVIARDREAMS:
                        textFontString = "CaviarDreams.ttf";
                        break;
                    case Fonts.CHUNKFIVE:
                        textFontString = "Chunkfive.otf";
                        break;
                    case Fonts.DANCINGSCRIPT_REGULAR:
                        textFontString = "DancingScript-Regular.otf";
                        break;
                    case Fonts.DOSIS_REGULAR:
                        textFontString = "Dosis-Regular.otf";
                        break;
                    case Fonts.EXO_REGULAR:
                        textFontString = "Exo-Regular.otf";
                        break;
                    case Fonts.FFF_TUSJ:
                        textFontString = "FFF_Tusj.ttf";
                        break;
                    case Fonts.GOODDOG:
                        textFontString = "GoodDog.otf";
                        break;
                    case Fonts.GRANDHOTEL_REGULAR:
                        textFontString = "GrandHotel-Regular.otf";
                        break;
                    case Fonts.GREATVIBES_REGULAR:
                        textFontString = "GreatVibes-Regular.otf";
                        break;
                    case Fonts.KAUSHANSCRIPT_REGULAR:
                        textFontString = "KaushanScript-Regular.otf";
                        break;
                    case Fonts.LATO_REGULAR:
                        textFontString = "Lato-Regular.ttf";
                        break;
                    case Fonts.LEAGUEGOTHIC_REGULAR:
                        textFontString = "LeagueGothic-Regular.otf";
                        break;
                    case Fonts.LEARNINGCURVE_OT:
                        textFontString = "LearningCurve_OT.otf";
                        break;
                    case Fonts.LOBSTERTWO_REGULAR:
                        textFontString = "LobsterTwo-Regular.otf";
                        break;
                    case Fonts.LOBSTER:
                        textFontString = "Lobster.otf";
                        break;
                    case Fonts.MATHLETE_BULKY:
                        textFontString = "Mathlete-Bulky.otf";
                        break;
                    case Fonts.MONTSERRAT_REGULAR:
                        textFontString = "Montserrat-Regular.otf";
                        break;
                    case Fonts.OPENSANS_REGULAR:
                        textFontString = "OpenSans-Regular.ttf";
                        break;
                    case Fonts.OSWALD_REGULAR:
                        textFontString = "Oswald-Regular.ttf";
                        break;
                    case Fonts.PACIFICO:
                        textFontString = "Pacifico.ttf";
                        break;
                    case Fonts.PLAYFAIRDISPLAYSC_REGULAR:
                        textFontString = "PlayfairDisplaySC-Regular.ttf";
                        break;
                    case Fonts.QUICKSAND_REGULAR:
                        textFontString = "Quicksand-Regular.otf";
                        break;
                    case Fonts.RALEWAY_REGULAR:
                        textFontString = "Raleway-Regular.ttf";
                        break;
                    case Fonts.ROBOTO_REGULAR:
                        textFontString = "Roboto-Regular.ttf";
                        break;
                    case Fonts.ROBOTOSLAB_REGULAR:
                        textFontString = "RobotoSlab-Regular.ttf";
                        break;
                    case Fonts.SEASRN__:
                        textFontString = "SEASRN__.ttf";
                        break;
                    case Fonts.SOFIA_REGULAR:
                        textFontString = "Sofia-Regular.otf";
                        break;
                    case Fonts.SOURCESANSPRO_REGULAR:
                        textFontString = "SourceSansPro-Regular.otf";
                        break;
                    case Fonts.TITILLIUM_REGULAR:
                        textFontString = "Titillium-Regular.otf";
                        break;
                    case Fonts.WALKWAY_BOLD:
                        textFontString = "Walkway_Bold.ttf";
                        break;
                    case Fonts.WINDSONG:
                        textFontString = "Windsong.ttf";
                        break;
                    case Fonts.BLACK_JACK:
                        textFontString = "black_jack.ttf";
                        break;
                    case Fonts.CAC_CHAMPAGNE:
                        textFontString = "cac_champagne.ttf";
                        break;
                    case Fonts.OSTRICH_REGULAR:
                        textFontString = "ostrich-regular.ttf";
                        break;
                }

                Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), textFontString);
                qodTextFontSettingFeedback.setTypeface(typeface);
                storage.setQodFont(textFontString);
            }
        });
        builder.setTitle(getString(R.string.str_ChooseTextFont));
        builder.setCancelable(true);
        builder.show();
    }
}
