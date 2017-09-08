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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import co.dilaver.quoter.QuoterApplication;
import co.dilaver.quoter.R;
import co.dilaver.quoter.activities.MainActivity;
import co.dilaver.quoter.activities.ShareActivity;
import co.dilaver.quoter.adapters.QuotesAdapter;
import co.dilaver.quoter.helper.QuoteParser;
import co.dilaver.quoter.models.Quote;
import co.dilaver.quoter.network.QuoterRestClient;
import co.dilaver.quoter.storage.SharedPrefStorage;
import cz.msebera.android.httpclient.Header;

public class PopularFragment extends Fragment implements QuotesAdapter.LongClickListener, MainActivity.ActionBarItemsClickListener {

    private QuotesAdapter quotesAdapter;
    private ArrayList<Quote> popularQuotesList;
    private ProgressBar pbPopularQuotes;
    private CoordinatorLayout rootLayout;
    private TextView noPopularData;
    private QuoteParser quoteParser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);

        quoteParser = new QuoteParser();
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
                noPopularData.setVisibility(View.GONE);
                pbPopularQuotes.setVisibility(View.VISIBLE);

                getPopularQuotes();
            }
        });

        getPopularQuotes();

        return view;
    }

    private void getPopularQuotes() {
        QuoterRestClient.get(QuoterRestClient.POPULAR, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    if (getActivity() != null) {
                        pbPopularQuotes.setVisibility(View.GONE);
                        
                        List<Quote> popularQuote = quoteParser.parseQuotesFromReddit(response);

                        for (Quote quote : popularQuote) {
                            if (quote.getQuoteAuthor().length() < 20 && quote.getQuoteAuthor().length() > 0) {
                                popularQuotesList.add(quote);
                            }
                        }

                        quotesAdapter.setList(popularQuotesList);

                        Toast.makeText(getActivity(), getString(R.string.str_longClickToShare), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                pbPopularQuotes.setVisibility(View.GONE);
                noPopularData.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void longClicked(View view, int position) {
        showAlertDialog(position);
    }

    private void showAlertDialog(final int pos) {
        CharSequence[] items = {getString(R.string.str_Save), getString(R.string.str_Share),getString(R.string.str_Copy)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    SharedPrefStorage sharedPrefStorage = new SharedPrefStorage(getActivity());
                    Gson gson = new Gson();

                    if (!QuoterApplication.savedQuotesList.contains(popularQuotesList.get(pos))) {
                        QuoterApplication.savedQuotesList.add(popularQuotesList.get(pos));
                        sharedPrefStorage.setSavedQuotes(gson.toJson(QuoterApplication.savedQuotesList));
                    }

                    Snackbar.make(rootLayout, getString(R.string.str_AddedToFavoriteQuotes), Snackbar.LENGTH_SHORT).show();
                } else if (item == 1) {
                    Intent shareIntent = new Intent(getActivity(), ShareActivity.class);
                    shareIntent.putExtra("quote", popularQuotesList.get(pos).getQuoteText());
                    shareIntent.putExtra("author", popularQuotesList.get(pos).getQuoteAuthor());
                    startActivity(shareIntent);
                } else if (item == 2) {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Text", popularQuotesList.get(pos).getQuoteText() + " - " +  popularQuotesList.get(pos).getQuoteAuthor());
                    clipboard.setPrimaryClip(clip);

                    Snackbar.make(rootLayout, getString(R.string.str_QuoteCopied), Snackbar.LENGTH_SHORT).show();
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
    public void qodCopyClicked() {

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
