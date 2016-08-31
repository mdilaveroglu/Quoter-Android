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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import co.dilaver.quoter.R;
import co.dilaver.quoter.activities.ShareActivity;
import co.dilaver.quoter.adapters.QuotesAdapter;
import co.dilaver.quoter.application.MyApplication;
import co.dilaver.quoter.models.Quote;
import co.dilaver.quoter.storage.SharedPrefStorage;


public class FavoriteQuotesFragment extends Fragment implements QuotesAdapter.LongClickListener {

    private static final String TAG = PopularFragment.class.getSimpleName();
    private QuotesAdapter quotesAdapter;
    private ArrayList<Quote> favoriteQuotesList;
    private CoordinatorLayout rootLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_quotes, container, false);

        rootLayout = (CoordinatorLayout) view.findViewById(R.id.clFavoriteRoot);
        favoriteQuotesList = new ArrayList<>();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvFavoriteQuotes);
        TextView noFavoriteQuotes = (TextView) view.findViewById(R.id.tvNoFavoriteQuotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        quotesAdapter = new QuotesAdapter(getActivity());
        quotesAdapter.setLongClickListener(this);
        recyclerView.setAdapter(quotesAdapter);

        favoriteQuotesList = MyApplication.savedQuotesList;
        quotesAdapter.setList(favoriteQuotesList);

        if (favoriteQuotesList.isEmpty()) {
            noFavoriteQuotes.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(getActivity(), getString(R.string.str_longClickToDelete), Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void longClicked(View view, int position) {
        showAlertDialog(position);
    }

    private void showAlertDialog(final int pos) {
        CharSequence[] items = {getString(R.string.str_Delete), getString(R.string.str_Share), getString(R.string.str_Copy)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    //Delete

                    SharedPrefStorage sharedPrefStorage = new SharedPrefStorage(getActivity());
                    Gson gson = new Gson();

                    for (int i = 0; i < MyApplication.savedQuotesList.size(); i++) {
                        if (MyApplication.savedQuotesList.get(i).getQuoteAuthor().equals(favoriteQuotesList.get(pos).getQuoteAuthor()) &&
                                MyApplication.savedQuotesList.get(i).getQuoteText().equals(favoriteQuotesList.get(pos).getQuoteText())) {

                            MyApplication.savedQuotesList.remove(i);
                            favoriteQuotesList = MyApplication.savedQuotesList;
                            sharedPrefStorage.setSavedQuotes(gson.toJson(MyApplication.savedQuotesList));
                        }
                    }

                    quotesAdapter.setList(favoriteQuotesList);
                    quotesAdapter.notifyDataSetChanged();

                } else if (item == 1) {
                    Intent shareIntent = new Intent(getActivity(), ShareActivity.class);
                    shareIntent.putExtra("quote", favoriteQuotesList.get(pos).getQuoteText());
                    shareIntent.putExtra("author", favoriteQuotesList.get(pos).getQuoteAuthor());
                    startActivity(shareIntent);
                } else if (item == 2) {
                    ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Activity.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Copied Text", favoriteQuotesList.get(pos).getQuoteText() + " - " +  favoriteQuotesList.get(pos).getQuoteAuthor());
                    clipboard.setPrimaryClip(clip);

                    Snackbar.make(rootLayout, getString(R.string.str_QuoteCopied), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
