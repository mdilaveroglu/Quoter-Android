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

package co.dilaver.quoter.application;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import co.dilaver.quoter.models.Quote;
import co.dilaver.quoter.storage.SharedPrefStorage;

public class QuoterApplication extends Application {

    public static ArrayList<Quote> savedQuotesList;

    @Override
    public void onCreate() {
        super.onCreate();

        savedQuotesList = new ArrayList<>();

        SharedPrefStorage sharedPrefStorage = new SharedPrefStorage(this);
        Gson gson = new Gson();

        if (!sharedPrefStorage.getSavedQuotes().equals(Quote.EMPTY)) {
            savedQuotesList = gson.fromJson(sharedPrefStorage.getSavedQuotes(), new TypeToken<ArrayList<Quote>>() {
            }.getType());
        }
    }

}
