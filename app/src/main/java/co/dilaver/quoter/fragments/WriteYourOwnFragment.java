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

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.gson.Gson;

import co.dilaver.quoter.R;
import co.dilaver.quoter.activities.MainActivity;
import co.dilaver.quoter.activities.ShareActivity;
import co.dilaver.quoter.application.MyApplication;
import co.dilaver.quoter.models.Quote;
import co.dilaver.quoter.storage.SharedPrefStorage;


public class WriteYourOwnFragment extends Fragment implements MainActivity.ActionBarItemsClickListener{

    private EditText quoteText;
    private EditText quoteAuthor;
    private CoordinatorLayout rootLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_write_your_own, container, false);


        rootLayout = (CoordinatorLayout) view.findViewById(R.id.clWyoRoot);
        MainActivity activity = (MainActivity) getActivity();
        activity.setActionBarItemsClickListener(this);

        quoteText = (EditText) view.findViewById(R.id.etMyQuote);
        quoteAuthor = (EditText) view.findViewById(R.id.etMyAuthor);



        return view;
    }

    @Override
    public void qodFavoriteClicked() {

    }

    @Override
    public void qodShareClicked() {

    }

    @Override
    public void wyoSaveClicked() {
        if (!quoteText.getText().toString().equals("") &&  !quoteAuthor.getText().toString().equals("")){
            SharedPrefStorage sharedPrefStorage = new SharedPrefStorage(getActivity());
            Gson gson = new Gson();

            Quote myQuote = new Quote(quoteText.getText().toString(),quoteAuthor.getText().toString());
            if (!MyApplication.savedQuotesList.contains(myQuote)){
                MyApplication.savedQuotesList.add(myQuote);
                sharedPrefStorage.setSavedQuotes(gson.toJson(MyApplication.savedQuotesList));
            }

            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            Snackbar.make(rootLayout, getString(R.string.str_AddedToFavoriteQuotes), Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void wyoShareClicked() {
        if (!quoteText.getText().toString().equals("") &&  !quoteAuthor.getText().toString().equals("")) {
            Intent shareIntent = new Intent(getActivity(), ShareActivity.class);
            shareIntent.putExtra("quote", quoteText.getText().toString());
            shareIntent.putExtra("author", quoteAuthor.getText().toString());
            startActivity(shareIntent);
        }
    }

    @Override
    public void pqInfoClicked() {

    }
}
