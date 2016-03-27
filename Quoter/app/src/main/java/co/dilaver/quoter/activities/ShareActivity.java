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

package co.dilaver.quoter.activities;

import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;

import co.dilaver.quoter.R;
import co.dilaver.quoter.adapters.TextFontAdapter;
import co.dilaver.quoter.constants.Fonts;
import co.dilaver.quoter.fragments.ColorPickerFragment;
import info.hoang8f.widget.FButton;

public class ShareActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener  {

    private static final String TAG = ShareActivity.class.getSimpleName();
    FButton chooseTextColor;
    FButton chooseBackgroundColor;
    FButton chooseTextFont;

    SeekBar chooseQuoteTextSize;
    SeekBar chooseAuthorTextSize;
    TextView quoteTextSizeDisplay;
    TextView authorTextSizeDisplay;

    RelativeLayout previewLayout;
    ImageView finalImage;
    TextView previewQuote;
    TextView previewAuthor;

    TextFontAdapter textFontAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chooseTextColor = (FButton) findViewById(R.id.btChooseTextColor);
        chooseBackgroundColor = (FButton) findViewById(R.id.btChooseBackgroundColor);
        chooseTextFont = (FButton) findViewById(R.id.btChooseTextFont);
        chooseQuoteTextSize = (SeekBar) findViewById(R.id.sbChooseQuoteTextSize);
        chooseAuthorTextSize = (SeekBar) findViewById(R.id.sbChooseAuthorTextSize);
        quoteTextSizeDisplay = (TextView) findViewById(R.id.tvQuoteTextSizeDisplay);
        authorTextSizeDisplay = (TextView) findViewById(R.id.tvAuthorTextSizeDisplay);

        previewLayout = (RelativeLayout) findViewById(R.id.rlPreviewLayout);
        finalImage = (ImageView) findViewById(R.id.ivPreviewBackground);
        previewQuote = (TextView) findViewById(R.id.tvPreviewQuote);
        previewAuthor = (TextView) findViewById(R.id.tvPreviewAuthor);

        textFontAdapter = new TextFontAdapter(this);

        chooseTextColor.setOnClickListener(this);
        chooseBackgroundColor.setOnClickListener(this);
        chooseTextFont.setOnClickListener(this);
        chooseQuoteTextSize.setOnSeekBarChangeListener(this);
        chooseAuthorTextSize.setOnSeekBarChangeListener(this);

        chooseQuoteTextSize.setProgress(25);
        chooseAuthorTextSize.setProgress(15);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "LobsterTwo-Regular.otf");
        finalImage.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        previewQuote.setText(getIntent().getStringExtra("quote"));
        previewAuthor.setText(getIntent().getStringExtra("author"));
        previewQuote.setTextColor(Color.WHITE);
        previewAuthor.setTextColor(Color.WHITE);
        previewQuote.setTextSize(25);
        previewAuthor.setTextSize(15);
        previewQuote.setTypeface(typeface);
        previewAuthor.setTypeface(typeface);
        chooseTextFont.setTypeface(typeface);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home){
            onBackPressed();
        }else if (id == R.id.share_action_done){
            save();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        Bundle args = new Bundle();
        if (v == chooseTextColor) {
            ColorPickerFragment fragment = new ColorPickerFragment();
            args.putInt("color", chooseTextColor.getButtonColor());
            fragment.setArguments(args);
            fragment.show(getFragmentManager(), "TextColorPicker");
            fragment.setColorChangeListener(new ColorPickerFragment.ColorChangeListener() {
                @Override
                public void onColorChanged(int colorValue) {
                    chooseTextColor.setButtonColor(colorValue);
                    previewQuote.setTextColor(colorValue);
                    previewAuthor.setTextColor(colorValue);
                }
            });
        } else if (v == chooseBackgroundColor) {
            ColorPickerFragment fragment = new ColorPickerFragment();
            args.putInt("color", chooseBackgroundColor.getButtonColor());
            fragment.setArguments(args);
            fragment.show(getFragmentManager(), "BackgroundColorPicker");
            fragment.setColorChangeListener(new ColorPickerFragment.ColorChangeListener() {
                @Override
                public void onColorChanged(int colorValue) {
                    chooseBackgroundColor.setButtonColor(colorValue);
                    finalImage.setBackgroundColor(colorValue);
                }
            });
        }else if (v == chooseTextFont) {
            showFontSelectionDialog();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == chooseQuoteTextSize) {
            quoteTextSizeDisplay.setText(String.valueOf(progress));
            previewQuote.setTextSize(progress);
        } else if (seekBar == chooseAuthorTextSize) {
            authorTextSizeDisplay.setText(String.valueOf(progress));
            previewAuthor.setTextSize(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void showFontSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(textFontAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String textFontString = "";
                switch (which) {
                    case Fonts.ALEXBRUSH_REGULAR :
                        textFontString = "AlexBrush-Regular.ttf";
                        break;
                    case Fonts.ALLER_LT :
                        textFontString = "Aller_Lt.ttf";
                        break;
                    case Fonts.ALLURA_REGULAR :
                        textFontString = "Allura-Regular.otf";
                        break;
                    case Fonts.AMATICSC_REGULAR :
                        textFontString = "AmaticSC-Regular.ttf";
                        break;
                    case Fonts.ANTONIO_REGULAR :
                        textFontString = "Antonio-Regular.ttf";
                        break;
                    case Fonts.ARIZONIA_REGULAR :
                        textFontString = "Arizonia-Regular.ttf";
                        break;
                    case Fonts.BEBAS___ :
                        textFontString = "BEBAS___.ttf";
                        break;
                    case Fonts.CAPTURE_IT :
                        textFontString = "Capture_it.ttf";
                        break;
                    case Fonts.CAVIARDREAMS :
                        textFontString = "CaviarDreams.ttf";
                        break;
                    case Fonts.CHUNKFIVE :
                        textFontString = "Chunkfive.otf";
                        break;
                    case Fonts.DANCINGSCRIPT_REGULAR :
                        textFontString = "DancingScript-Regular.otf";
                        break;
                    case Fonts.DOSIS_REGULAR :
                        textFontString = "Dosis-Regular.otf";
                        break;
                    case Fonts.EXO_REGULAR :
                        textFontString = "Exo-Regular.otf";
                        break;
                    case Fonts.FFF_TUSJ :
                        textFontString = "FFF_Tusj.ttf";
                        break;
                    case Fonts.GOODDOG :
                        textFontString = "GoodDog.otf";
                        break;
                    case Fonts.GRANDHOTEL_REGULAR :
                        textFontString = "GrandHotel-Regular.otf";
                        break;
                    case Fonts.GREATVIBES_REGULAR :
                        textFontString = "GreatVibes-Regular.otf";
                        break;
                    case Fonts.KAUSHANSCRIPT_REGULAR :
                        textFontString = "KaushanScript-Regular.otf";
                        break;
                    case Fonts.LATO_REGULAR :
                        textFontString = "Lato-Regular.ttf";
                        break;
                    case Fonts.LEAGUEGOTHIC_REGULAR :
                        textFontString = "LeagueGothic-Regular.otf";
                        break;
                    case Fonts.LEARNINGCURVE_OT :
                        textFontString = "LearningCurve_OT.otf";
                        break;
                    case Fonts.LOBSTERTWO_REGULAR :
                        textFontString = "LobsterTwo-Regular.otf";
                        break;
                    case Fonts.LOBSTER :
                        textFontString = "Lobster.otf";
                        break;
                    case Fonts.MATHLETE_BULKY :
                        textFontString = "Mathlete-Bulky.otf";
                        break;
                    case Fonts.MONTSERRAT_REGULAR :
                        textFontString = "Montserrat-Regular.otf";
                        break;
                    case Fonts.OPENSANS_REGULAR :
                        textFontString = "OpenSans-Regular.ttf";
                        break;
                    case Fonts.OSWALD_REGULAR :
                        textFontString = "Oswald-Regular.ttf";
                        break;
                    case Fonts.PACIFICO :
                        textFontString = "Pacifico.ttf";
                        break;
                    case Fonts.PLAYFAIRDISPLAYSC_REGULAR :
                        textFontString = "PlayfairDisplaySC-Regular.ttf";
                        break;
                    case Fonts.QUICKSAND_REGULAR :
                        textFontString = "Quicksand-Regular.otf";
                        break;
                    case Fonts.RALEWAY_REGULAR :
                        textFontString = "Raleway-Regular.ttf";
                        break;
                    case Fonts.ROBOTO_REGULAR :
                        textFontString = "Roboto-Regular.ttf";
                        break;
                    case Fonts.ROBOTOSLAB_REGULAR :
                        textFontString = "RobotoSlab-Regular.ttf";
                        break;
                    case Fonts.SEASRN__ :
                        textFontString = "SEASRN__.ttf";
                        break;
                    case Fonts.SOFIA_REGULAR :
                        textFontString = "Sofia-Regular.otf";
                        break;
                    case Fonts.SOURCESANSPRO_REGULAR :
                        textFontString = "SourceSansPro-Regular.otf";
                        break;
                    case Fonts.TITILLIUM_REGULAR :
                        textFontString = "Titillium-Regular.otf";
                        break;
                    case Fonts.WALKWAY_BOLD :
                        textFontString = "Walkway_Bold.ttf";
                        break;
                    case Fonts.WINDSONG :
                        textFontString = "Windsong.ttf";
                        break;
                    case Fonts.BLACK_JACK :
                        textFontString = "black_jack.ttf";
                        break;
                    case Fonts.CAC_CHAMPAGNE :
                        textFontString = "cac_champagne.ttf";
                        break;
                    case Fonts.OSTRICH_REGULAR :
                        textFontString = "ostrich-regular.ttf";
                        break;
                }

                Typeface typeface = Typeface.createFromAsset(getAssets(), textFontString);
                chooseTextFont.setTypeface(typeface);
                previewQuote.setTypeface(typeface);
                previewAuthor.setTypeface(typeface);
            }
        });
        builder.setTitle(getString(R.string.str_ChooseTextFont));
        builder.setCancelable(true);
        builder.show();
    }

    public void save() {

        previewLayout.setDrawingCacheEnabled(true);
        previewLayout.buildDrawingCache();
        Bitmap bmp = Bitmap.createBitmap(previewLayout.getDrawingCache());
        previewLayout.setDrawingCacheEnabled(false);

        ContextWrapper cw = new ContextWrapper(this);
        File directory = cw.getExternalFilesDir(null);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File image = new File(directory, "image.jpg");

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(image);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + image.getAbsolutePath()));
        shareIntent.setType("image/jpeg");
        startActivity(Intent.createChooser(shareIntent, getString(R.string.str_ShareWith)));
    }

}
