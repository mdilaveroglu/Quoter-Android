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


import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import co.dilaver.quoter.R;
import co.dilaver.quoter.activities.MainActivity;
import co.dilaver.quoter.activities.ShareActivity;
import co.dilaver.quoter.application.MyApplication;
import co.dilaver.quoter.models.Quote;
import co.dilaver.quoter.network.QuoterRestClient;
import co.dilaver.quoter.storage.SharedPrefStorage;
import cz.msebera.android.httpclient.Header;
import me.grantland.widget.AutofitHelper;


public class QODFragment extends Fragment implements MainActivity.ActionBarItemsClickListener {

    private static final String TAG = QODFragment.class.getSimpleName();

    private SharedPrefStorage sharedPrefStorage;

    Typeface font;

    private TextView qodText;
    private TextView qodAuthor;
    private TextView noData;
    private CoordinatorLayout rootLayout;
    private ProgressBar loadingQod;

    private String qodString = "";
    private String authorString = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_qod, container, false);

        sharedPrefStorage = new SharedPrefStorage(getActivity());

        MainActivity activity = (MainActivity) getActivity();
        activity.setActionBarItemsClickListener(this);

        font = Typeface.createFromAsset(getActivity().getAssets(), sharedPrefStorage.getQodFont());

        qodText = (TextView) view.findViewById(R.id.tvQodText);
        qodAuthor = (TextView) view.findViewById(R.id.tvQodAuthor);
        noData = (TextView) view.findViewById(R.id.tvNoData);
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.clQodRoot);
        loadingQod = (ProgressBar) view.findViewById(R.id.pbQod);
        noData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noData.setVisibility(View.GONE);
                loadingQod.setVisibility(View.VISIBLE);
                getQod();
            }
        });

        AutofitHelper.create(qodText);

        qodText.setTypeface(font);
        qodAuthor.setTypeface(font);
        qodText.setTextColor(sharedPrefStorage.getQodColor());
        qodAuthor.setTextColor(sharedPrefStorage.getQodColor());

        if (sharedPrefStorage.getQodText().equals("empty") || sharedPrefStorage.getQodAuthor().equals("empty")) {
            loadingQod.setVisibility(View.VISIBLE);
            getQod();
        } else {
            qodString = sharedPrefStorage.getQodText();
            authorString = sharedPrefStorage.getQodAuthor();

            qodText.setText(getString(R.string.str_WithinQuotation, qodString));
            qodAuthor.setText(authorString);
            getQod();
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (font != null) {
            qodText.setTypeface(font);
            qodAuthor.setTypeface(font);
            qodText.setTextColor(sharedPrefStorage.getQodColor());
            qodAuthor.setTextColor(sharedPrefStorage.getQodColor());
        }
    }

    private void getQod() {

        QuoterRestClient.get(QuoterRestClient.QOD, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (getActivity() != null) {
                        loadingQod.setVisibility(View.GONE);
                        parseQodResponse(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                if (loadingQod.isShown()) {
                    loadingQod.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                } else {
                    if (isAdded()) {
                        Snackbar.make(rootLayout, getString(R.string.str_NoInternetConnection), Snackbar.LENGTH_INDEFINITE)
                                .setAction(getString(R.string.str_Retry), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getQod();
                                    }
                                })
                                .show();
                    }
                }
            }
        });
    }

    private void parseQodResponse(JSONObject response) throws JSONException {
        JSONObject parse = response.getJSONObject("parse");
        JSONObject text = parse.getJSONObject("text");
        String content = text.getString("*");

        Document doc = Jsoup.parse(content);
        Elements table = doc.select("table[style=\"text-align:center; width:100%\"]");
        Elements rows = table.select("tr");
        Elements qod = rows.get(0).select("td");
        Elements author = rows.get(1).select("td");
        Whitelist whitelist = Whitelist.none();

        String newQuote = Html.fromHtml(Jsoup.clean(qod.toString(), whitelist)).toString();
        String newAuthor = Html.fromHtml(Jsoup.clean(author.toString(), whitelist).replace("~", "")).toString();

        if (!qodString.equals("") && !authorString.equals("")) {
            if (!qodString.equals(newQuote) || !authorString.equals(newAuthor)) {
                Snackbar.make(rootLayout, getString(R.string.str_Refreshing), Snackbar.LENGTH_SHORT).show();
            }
        }

        qodString = newQuote;
        authorString = newAuthor;

        sharedPrefStorage.setQodText(qodString);
        sharedPrefStorage.setQodAuthor(authorString);

        Log.e(TAG, "quote: " + qodString);
        Log.e(TAG, "author: " + authorString);

        qodText.setText(getString(R.string.str_WithinQuotation, qodString));
        qodAuthor.setText(authorString);
    }

    @Override
    public void qodFavoriteClicked() {
        if (!qodString.equals("") && !authorString.equals("")) {

            Snackbar.make(rootLayout, getString(R.string.str_AddedToFavoriteQuotes), Snackbar.LENGTH_SHORT).show();

            SharedPrefStorage sharedPrefStorage = new SharedPrefStorage(getActivity());
            Gson gson = new Gson();

            Quote quote = new Quote(qodString, authorString);
            if (!MyApplication.savedQuotesList.contains(quote)) {
                MyApplication.savedQuotesList.add(quote);
                sharedPrefStorage.setSavedQuotes(gson.toJson(MyApplication.savedQuotesList));
            }
        }
    }

    @Override
    public void qodShareClicked() {
        if (!qodString.equals("") && !authorString.equals("")) {
            Intent shareIntent = new Intent(getActivity(), ShareActivity.class);
            shareIntent.putExtra("quote", qodString);
            shareIntent.putExtra("author", authorString);
            startActivity(shareIntent);
        }
    }

    @Override
    public void qodCopyClicked() {
        if (!qodString.equals("") && !authorString.equals("")) {
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Copied Text", qodString + " - " + authorString);
            clipboard.setPrimaryClip(clip);

            Snackbar.make(rootLayout, getString(R.string.str_QuoteCopied), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void wyoSaveClicked() {

    }

    @Override
    public void wyoShareClicked() {

    }

    @Override
    public void pqInfoClicked() {

    }
}
