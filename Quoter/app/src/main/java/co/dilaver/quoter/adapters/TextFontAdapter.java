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

package co.dilaver.quoter.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import co.dilaver.quoter.R;
import co.dilaver.quoter.constants.Fonts;

public class TextFontAdapter extends BaseAdapter{

    private final LayoutInflater inflater;
    private final Context context;

    public TextFontAdapter(Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case Fonts.ALEXBRUSH_REGULAR :
                return Fonts.ALEXBRUSH_REGULAR;
            case Fonts.ALLER_LT :
                return Fonts.ALLER_LT;
            case Fonts.ALLURA_REGULAR :
                return Fonts.ALLURA_REGULAR;
            case Fonts.AMATICSC_REGULAR :
                return Fonts.AMATICSC_REGULAR;
            case Fonts.ANTONIO_REGULAR :
                return Fonts.ANTONIO_REGULAR;
            case Fonts.ARIZONIA_REGULAR :
                return Fonts.ARIZONIA_REGULAR;
            case Fonts.BEBAS___ :
                return Fonts.BEBAS___;
            case Fonts.CAPTURE_IT :
                return Fonts.CAPTURE_IT;
            case Fonts.CAVIARDREAMS :
                return Fonts.CAVIARDREAMS;
            case Fonts.CHUNKFIVE :
                return Fonts.CHUNKFIVE;
            case Fonts.DANCINGSCRIPT_REGULAR :
                return Fonts.DANCINGSCRIPT_REGULAR;
            case Fonts.DOSIS_REGULAR :
                return Fonts.DOSIS_REGULAR;
            case Fonts.EXO_REGULAR :
                return Fonts.EXO_REGULAR;
            case Fonts.FFF_TUSJ :
                return Fonts.FFF_TUSJ;
            case Fonts.GOODDOG :
                return Fonts.GOODDOG;
            case Fonts.GRANDHOTEL_REGULAR :
                return Fonts.GRANDHOTEL_REGULAR;
            case Fonts.GREATVIBES_REGULAR :
                return Fonts.GREATVIBES_REGULAR;
            case Fonts.KAUSHANSCRIPT_REGULAR :
                return Fonts.KAUSHANSCRIPT_REGULAR;
            case Fonts.LATO_REGULAR :
                return Fonts.LATO_REGULAR;
            case Fonts.LEAGUEGOTHIC_REGULAR :
                return Fonts.LEAGUEGOTHIC_REGULAR;
            case Fonts.LEARNINGCURVE_OT :
                return Fonts.LEARNINGCURVE_OT;
            case Fonts.LOBSTERTWO_REGULAR :
                return Fonts.LOBSTERTWO_REGULAR;
            case Fonts.LOBSTER :
                return Fonts.LOBSTER;
            case Fonts.MATHLETE_BULKY :
                return Fonts.MATHLETE_BULKY;
            case Fonts.MONTSERRAT_REGULAR :
                return Fonts.MONTSERRAT_REGULAR;
            case Fonts.OPENSANS_REGULAR :
                return Fonts.OPENSANS_REGULAR;
            case Fonts.OSWALD_REGULAR :
                return Fonts.OSWALD_REGULAR;
            case Fonts.PACIFICO :
                return Fonts.PACIFICO;
            case Fonts.PLAYFAIRDISPLAYSC_REGULAR :
                return Fonts.PLAYFAIRDISPLAYSC_REGULAR;
            case Fonts.QUICKSAND_REGULAR :
                return Fonts.QUICKSAND_REGULAR;
            case Fonts.RALEWAY_REGULAR :
                return Fonts.RALEWAY_REGULAR;
            case Fonts.ROBOTO_REGULAR :
                return Fonts.ROBOTO_REGULAR;
            case Fonts.ROBOTOSLAB_REGULAR :
                return Fonts.ROBOTOSLAB_REGULAR;
            case Fonts.SEASRN__ :
                return Fonts.SEASRN__;
            case Fonts.SOFIA_REGULAR :
                return Fonts.SOFIA_REGULAR;
            case Fonts.SOURCESANSPRO_REGULAR :
                return Fonts.SOURCESANSPRO_REGULAR;
            case Fonts.TITILLIUM_REGULAR :
                return Fonts.TITILLIUM_REGULAR;
            case Fonts.WALKWAY_BOLD :
                return Fonts.WALKWAY_BOLD;
            case Fonts.WINDSONG :
                return Fonts.WINDSONG;
            case Fonts.BLACK_JACK :
                return Fonts.BLACK_JACK;
            case Fonts.CAC_CHAMPAGNE :
                return Fonts.CAC_CHAMPAGNE;
            case Fonts.OSTRICH_REGULAR :
                return Fonts.OSTRICH_REGULAR;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 42;
    }

    @Override
    public int getCount() {
        return 42;
    }

    @Override
    public String getItem(int position) {
        return context.getString(R.string.str_SampleText);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        int rowType = getItemViewType(position);
        Typeface font;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
            holder.text = (TextView) convertView.findViewById(android.R.id.text1);


            switch (rowType) {
                case Fonts.ALEXBRUSH_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"AlexBrush-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.ALLER_LT :
                    font = Typeface.createFromAsset(context.getAssets(),"Aller_Lt.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.ALLURA_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Allura-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.AMATICSC_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"AmaticSC-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.ANTONIO_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Antonio-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.ARIZONIA_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Arizonia-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.BEBAS___ :
                    font = Typeface.createFromAsset(context.getAssets(),"BEBAS___.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.CAPTURE_IT :
                    font = Typeface.createFromAsset(context.getAssets(),"Capture_it.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.CAVIARDREAMS :
                    font = Typeface.createFromAsset(context.getAssets(),"CaviarDreams.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.CHUNKFIVE :
                    font = Typeface.createFromAsset(context.getAssets(),"Chunkfive.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.DANCINGSCRIPT_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"DancingScript-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.DOSIS_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Dosis-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.EXO_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Exo-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.FFF_TUSJ :
                    font = Typeface.createFromAsset(context.getAssets(),"FFF_Tusj.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.GOODDOG :
                    font = Typeface.createFromAsset(context.getAssets(),"GoodDog.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.GRANDHOTEL_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"GrandHotel-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.GREATVIBES_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"GreatVibes-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.KAUSHANSCRIPT_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"KaushanScript-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.LATO_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Lato-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.LEAGUEGOTHIC_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"LeagueGothic-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.LEARNINGCURVE_OT :
                    font = Typeface.createFromAsset(context.getAssets(),"LearningCurve_OT.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.LOBSTERTWO_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"LobsterTwo-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.LOBSTER :
                    font = Typeface.createFromAsset(context.getAssets(),"Lobster.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.MATHLETE_BULKY :
                    font = Typeface.createFromAsset(context.getAssets(),"Mathlete-Bulky.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.MONTSERRAT_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Montserrat-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.OPENSANS_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"OpenSans-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.OSWALD_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Oswald-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.PACIFICO :
                    font = Typeface.createFromAsset(context.getAssets(),"Pacifico.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.PLAYFAIRDISPLAYSC_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"PlayfairDisplaySC-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.QUICKSAND_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Quicksand-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.RALEWAY_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Raleway-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.ROBOTO_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Roboto-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.ROBOTOSLAB_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"RobotoSlab-Regular.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.SEASRN__ :
                    font = Typeface.createFromAsset(context.getAssets(),"SEASRN__.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.SOFIA_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Sofia-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.SOURCESANSPRO_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"SourceSansPro-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.TITILLIUM_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"Titillium-Regular.otf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.WALKWAY_BOLD :
                    font = Typeface.createFromAsset(context.getAssets(),"Walkway_Bold.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.WINDSONG :
                    font = Typeface.createFromAsset(context.getAssets(),"Windsong.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.BLACK_JACK :
                    font = Typeface.createFromAsset(context.getAssets(),"black_jack.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.CAC_CHAMPAGNE :
                    font = Typeface.createFromAsset(context.getAssets(),"cac_champagne.ttf");
                    holder.text.setTypeface(font);
                    break;
                case Fonts.OSTRICH_REGULAR :
                    font = Typeface.createFromAsset(context.getAssets(),"ostrich-regular.ttf");
                    holder.text.setTypeface(font);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(context.getString(R.string.str_SampleText));

        return convertView;
    }

    public static class ViewHolder {
        public TextView text;
    }
}
