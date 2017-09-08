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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import co.dilaver.quoter.R;

public class CreditsFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_credits, container, false);

        TextView androidLink = (TextView) view.findViewById(R.id.tvAndroidLink);
        TextView androidSupportLibraryLink = (TextView) view.findViewById(R.id.tvAndroidSupportLibraryLink);
        TextView asyncHTTPLink = (TextView) view.findViewById(R.id.tvAsyncHTTPLink);
        TextView gsonLink = (TextView) view.findViewById(R.id.tvGsonLink);
        TextView autoTextViewLink = (TextView) view.findViewById(R.id.tvAutoTextViewLink);
        TextView lobsterpickerLink = (TextView) view.findViewById(R.id.tvLobsterpickerLink);
        TextView fButtonLink = (TextView) view.findViewById(R.id.tvFButtonLink);
        TextView jSoupLink = (TextView) view.findViewById(R.id.tvJSoupLink);
        TextView circleImageViewLink = (TextView) view.findViewById(R.id.tvCircleImageViewLink);

        TextView googleCredit = (TextView) view.findViewById(R.id.tvGoogleCredit);
        TextView freepikCredit = (TextView) view.findViewById(R.id.tvFreepikCredit);
        TextView qodCredit = (TextView) view.findViewById(R.id.tvQodCredit);
        TextView popularCredit = (TextView) view.findViewById(R.id.tvPopularCredit);
        TextView fontsCredit = (TextView) view.findViewById(R.id.tvFontsCredit);
        TextView backgroundCredit = (TextView) view.findViewById(R.id.tvBackgroundCredit);

        androidLink.setMovementMethod(LinkMovementMethod.getInstance());
        androidSupportLibraryLink.setMovementMethod(LinkMovementMethod.getInstance());
        asyncHTTPLink.setMovementMethod(LinkMovementMethod.getInstance());
        gsonLink.setMovementMethod(LinkMovementMethod.getInstance());
        autoTextViewLink.setMovementMethod(LinkMovementMethod.getInstance());
        lobsterpickerLink.setMovementMethod(LinkMovementMethod.getInstance());
        fButtonLink.setMovementMethod(LinkMovementMethod.getInstance());
        jSoupLink.setMovementMethod(LinkMovementMethod.getInstance());
        circleImageViewLink.setMovementMethod(LinkMovementMethod.getInstance());

        googleCredit.setText(Html.fromHtml(getString(R.string.str_googleCredit)));
        googleCredit.setMovementMethod(LinkMovementMethod.getInstance());

        freepikCredit.setText(Html.fromHtml(getString(R.string.str_freepikCredit)));
        freepikCredit.setMovementMethod(LinkMovementMethod.getInstance());

        qodCredit.setMovementMethod(LinkMovementMethod.getInstance());
        popularCredit.setMovementMethod(LinkMovementMethod.getInstance());
        fontsCredit.setMovementMethod(LinkMovementMethod.getInstance());
        backgroundCredit.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}
