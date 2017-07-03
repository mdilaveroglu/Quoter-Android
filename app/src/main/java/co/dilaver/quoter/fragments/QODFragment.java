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
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import co.dilaver.quoter.application.QuoterApplication;
import co.dilaver.quoter.databinding.FragmentQodBinding;
import co.dilaver.quoter.models.Quote;
import co.dilaver.quoter.network.QuoterRestClient;
import co.dilaver.quoter.storage.SharedPrefStorage;
import cz.msebera.android.httpclient.Header;
import me.grantland.widget.AutofitHelper;

public class QODFragment extends Fragment implements MainActivity.ActionBarItemsClickListener, View.OnClickListener {

    private SharedPrefStorage sharedPrefStorage;

    private FragmentQodBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qod, container, false);

        sharedPrefStorage = new SharedPrefStorage(getActivity());

        AutofitHelper.create(binding.autofitTextViewQuoteOfTheDayText);

        Quote qod = sharedPrefStorage.getQod();

        if (qod.getQuoteText().equals(Quote.EMPTY) || qod.getQuoteAuthor().equals(Quote.EMPTY)) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            setQuoteOfTheDayTextAndAuthor(qod);
        }

        getQod();

        ((MainActivity) getActivity()).setActionBarItemsClickListener(this);

        binding.textViewNoData.setOnClickListener(this);
        binding.fab.setOnClickListener(this);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        setQuoteOfTheDayTextAndAuthorFontAndColor();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_view_no_data:
                binding.textViewNoData.setVisibility(View.GONE);
                binding.progressBar.setVisibility(View.VISIBLE);
                getQod();
                break;
            case R.id.fab:
                startActivity(ShareActivity.getCallingIntent(getActivity(), sharedPrefStorage.getQod()));
                break;
        }
    }

    private void setQuoteOfTheDayTextAndAuthor(Quote qod) {
        binding.autofitTextViewQuoteOfTheDayText.setText(getString(R.string.str_WithinQuotation, qod.getQuoteText()));
        binding.textViewQuoteOfTheDayAuthor.setText(qod.getQuoteAuthor());

    }

    private void setQuoteOfTheDayTextAndAuthorFontAndColor() {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), sharedPrefStorage.getQodFont());

        binding.autofitTextViewQuoteOfTheDayText.setTypeface(font);
        binding.autofitTextViewQuoteOfTheDayText.setTextColor(sharedPrefStorage.getQodColor());

        binding.textViewQuoteOfTheDayAuthor.setTypeface(font);
        binding.textViewQuoteOfTheDayAuthor.setTextColor(sharedPrefStorage.getQodColor());
    }

    private void getQod() {
        QuoterRestClient.get(QuoterRestClient.QOD, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (getActivity() != null) {
                        binding.progressBar.setVisibility(View.GONE);
                        parseQodResponse(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                if (binding.progressBar.isShown()) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.textViewNoData.setVisibility(View.VISIBLE);
                } else {
                    if (isAdded()) {
                        Snackbar.make(binding.coordinatorLayout, getString(R.string.str_NoInternetConnection), Snackbar.LENGTH_INDEFINITE)
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
        Elements qodTd = rows.get(0).select("td");
        Elements author = rows.get(1).select("td");
        Whitelist whitelist = Whitelist.none();

        String newQuote = Html.fromHtml(Jsoup.clean(qodTd.toString(), whitelist)).toString();
        String newAuthor = Html.fromHtml(Jsoup.clean(author.toString(), whitelist).replace("~", "")).toString();

        Quote qod = sharedPrefStorage.getQod();

        if (!qod.getQuoteText().equals(newQuote) || !qod.getQuoteAuthor().equals(newAuthor)) {
            Snackbar.make(binding.coordinatorLayout, getString(R.string.str_Refreshing), Snackbar.LENGTH_SHORT).show();
        }

        sharedPrefStorage.setQodText(newQuote);
        sharedPrefStorage.setQodAuthor(newAuthor);

        setQuoteOfTheDayTextAndAuthor(qod);
    }

    @Override
    public void qodFavoriteClicked() {
        Snackbar.make(binding.coordinatorLayout, getString(R.string.str_AddedToFavoriteQuotes), Snackbar.LENGTH_SHORT).show();

        Gson gson = new Gson();

        Quote qod = sharedPrefStorage.getQod();
        Quote quote = new Quote(qod.getQuoteText(), qod.getQuoteAuthor());
        if (!QuoterApplication.savedQuotesList.contains(quote)) {
            QuoterApplication.savedQuotesList.add(quote);
            sharedPrefStorage.setSavedQuotes(gson.toJson(QuoterApplication.savedQuotesList));
        }
    }

    @Override
    public void qodCopyClicked() {
        Quote qod = sharedPrefStorage.getQod();

        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", qod.getQuoteText() + " - " + qod.getQuoteAuthor());
        clipboard.setPrimaryClip(clip);

        Snackbar.make(binding.coordinatorLayout, getString(R.string.str_QuoteCopied), Snackbar.LENGTH_SHORT).show();
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
