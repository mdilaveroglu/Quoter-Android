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

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.dilaver.quoter.R;
import co.dilaver.quoter.activities.MainActivity;
import co.dilaver.quoter.activities.ShareActivity;
import co.dilaver.quoter.adapters.QuotesAdapter;
import co.dilaver.quoter.application.MyApplication;
import co.dilaver.quoter.models.Quote;
import co.dilaver.quoter.network.QuoterRestClient;
import co.dilaver.quoter.storage.SharedPrefStorage;
import cz.msebera.android.httpclient.Header;

public class PopularFragment extends Fragment implements QuotesAdapter.LongClickListener, MainActivity.ActionBarItemsClickListener {

    private static final String TAG = PopularFragment.class.getSimpleName();
    private QuotesAdapter quotesAdapter;
    private ArrayList<Quote> popularQuotesList;
    private ProgressBar pbPopularQuotes;
    private CoordinatorLayout rootLayout;
    private TextView noPopularData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);


        popularQuotesList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvPopularQuotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rootLayout = (CoordinatorLayout) view.findViewById(R.id.clPopularRoot);
        quotesAdapter = new QuotesAdapter(getActivity());
        quotesAdapter.setLongClickListener(this);
        recyclerView.setAdapter(quotesAdapter);

        MainActivity activity = (MainActivity) getActivity();
        activity.setActionBarItemsClickListener(this);

        pbPopularQuotes = (ProgressBar) view.findViewById(R.id.pbPopularQuotes);
        noPopularData = (TextView) view.findViewById(R.id.tvNoPopularData);

        noPopularData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick() called with: " + "v = [" + v + "]");
                noPopularData.setVisibility(View.GONE);
                pbPopularQuotes.setVisibility(View.VISIBLE);

                getPopularQuotes();
            }
        });

        getPopularQuotes();

        return view;
    }

    private void getPopularQuotes() {
        Log.e(TAG, "getPopularQuotes() called with: " + "");

        QuoterRestClient.get(QuoterRestClient.POPULAR, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (getActivity() != null) {
                        pbPopularQuotes.setVisibility(View.GONE);
                        parsePopularQuotesResponse(response);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.e(TAG, "onSuccess: " + response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                pbPopularQuotes.setVisibility(View.GONE);
                noPopularData.setVisibility(View.VISIBLE);
            }
        });
    }

    private void parsePopularQuotesResponse(JSONObject response) throws JSONException {
        JSONObject data = response.getJSONObject("data");
        JSONArray children = data.getJSONArray("children");
        for (int i = 0; i < children.length(); i++) {
            JSONObject quoteChildren = children.getJSONObject(i);
            JSONObject quoteData = quoteChildren.getJSONObject("data");
            String quoteString = quoteData.getString("title");
            if (quoteString.contains("\"")) {
                quoteString = quoteString.replace("\"", "");
            }

            if (quoteString.contains("“")) {
                quoteString = quoteString.replace("“", "");
            }

            if (quoteString.contains("”")) {
                quoteString = quoteString.replace("”", "");
            }


            if (quoteString.contains("-")) {
                Log.e(TAG, "contains short dash: " + i);
                String quoteText = quoteString.split("\\-")[0];
                String quoteAuthor = quoteString.substring(quoteString.indexOf("-") + 1);

                if (quoteAuthor.length() < 20) {
                    popularQuotesList.add(new Quote(quoteText, quoteAuthor));
                    Log.e(TAG, "popularQuotesList: " + popularQuotesList.toString());
                }
            } else if (quoteString.contains("\u2015")) {
                Log.e(TAG, "contains long dash: " + i);

                String quoteText = quoteString.split("\\\u2015")[0];
                String quoteAuthor = quoteString.substring(quoteString.indexOf("\u2015") + 1);

                if (quoteAuthor.length() < 20) {
                    popularQuotesList.add(new Quote(quoteText, quoteAuthor));
                    Log.e(TAG, "popularQuotesList: " + popularQuotesList.toString());
                }

            } else if (quoteString.contains("\u2014")) {
                Log.e(TAG, "contains long dash: " + i);

                String quoteText = quoteString.split("\\\u2014")[0];
                String quoteAuthor = quoteString.substring(quoteString.indexOf("\u2014") + 1);

                if (quoteAuthor.length() < 20) {
                    popularQuotesList.add(new Quote(quoteText, quoteAuthor));
                    Log.e(TAG, "popularQuotesList: " + popularQuotesList.toString());
                }

            } else if (quoteString.contains("\u2013")) {
                Log.e(TAG, "contains long dash: " + i);

                String quoteText = quoteString.split("\\\u2013")[0];
                String quoteAuthor = quoteString.substring(quoteString.indexOf("\u2013") + 1);

                if (quoteAuthor.length() < 20) {
                    popularQuotesList.add(new Quote(quoteText, quoteAuthor));
                    Log.e(TAG, "popularQuotesList: " + popularQuotesList.toString());
                }
            } else if (quoteString.contains("\u2012")) {
                Log.e(TAG, "contains long dash: " + i);

                String quoteText = quoteString.split("\\\u2012")[0];
                String quoteAuthor = quoteString.substring(quoteString.indexOf("\u2012") + 1);

                if (quoteAuthor.length() < 20) {
                    popularQuotesList.add(new Quote(quoteText, quoteAuthor));
                    Log.e(TAG, "popularQuotesList: " + popularQuotesList.toString());
                }
            } else if (quoteString.contains("\u2011")) {
                Log.e(TAG, "contains long dash: " + i);

                String quoteText = quoteString.split("\\\u2011")[0];
                String quoteAuthor = quoteString.substring(quoteString.indexOf("\u2011") + 1);

                if (quoteAuthor.length() < 20) {
                    popularQuotesList.add(new Quote(quoteText, quoteAuthor));
                    Log.e(TAG, "popularQuotesList: " + popularQuotesList.toString());
                }
            } else if (quoteString.contains("\u2010")) {
                Log.e(TAG, "contains long dash: " + i);

                String quoteText = quoteString.split("\\\u2010")[0];
                String quoteAuthor = quoteString.substring(quoteString.indexOf("\u2010") + 1);

                if (quoteAuthor.length() < 20) {
                    popularQuotesList.add(new Quote(quoteText, quoteAuthor));
                    Log.e(TAG, "popularQuotesList: " + popularQuotesList.toString());
                }
            }
        }

        quotesAdapter.setList(popularQuotesList);
        Toast.makeText(getActivity(), getString(R.string.str_longClickToShare), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void longClicked(View view, int position) {
        showAlertDialog(position);
    }

    private void showAlertDialog(final int pos) {
        CharSequence[] items = {getString(R.string.str_Save), getString(R.string.str_Share)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    SharedPrefStorage sharedPrefStorage = new SharedPrefStorage(getActivity());
                    Gson gson = new Gson();

                    if (!MyApplication.savedQuotesList.contains(popularQuotesList.get(pos))) {
                        MyApplication.savedQuotesList.add(popularQuotesList.get(pos));
                        sharedPrefStorage.setSavedQuotes(gson.toJson(MyApplication.savedQuotesList));
                    }

                    Snackbar.make(rootLayout, getString(R.string.str_AddedToFavoriteQuotes), Snackbar.LENGTH_SHORT).show();

                } else if (item == 1) {
                    Intent shareIntent = new Intent(getActivity(), ShareActivity.class);
                    shareIntent.putExtra("quote", popularQuotesList.get(pos).getQuoteText());
                    shareIntent.putExtra("author", popularQuotesList.get(pos).getQuoteAuthor());
                    startActivity(shareIntent);
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void qodFavoriteClicked() {

    }

    @Override
    public void qodShareClicked() {

    }

    @Override
    public void wyoSaveClicked() {

    }

    @Override
    public void wyoShareClicked() {

    }

    @Override
    public void pqInfoClicked() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.str_Info));
        builder.setMessage(Html.fromHtml(getString(R.string.str_TakenFromReddit)));
        builder.setCancelable(true);
        builder.show();
    }
}
