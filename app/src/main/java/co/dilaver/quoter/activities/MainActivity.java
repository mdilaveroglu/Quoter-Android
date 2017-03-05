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

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import co.dilaver.quoter.R;
import co.dilaver.quoter.fragments.AboutMeFragment;
import co.dilaver.quoter.fragments.CreditsFragment;
import co.dilaver.quoter.fragments.FavoriteQuotesFragment;
import co.dilaver.quoter.fragments.PopularFragment;
import co.dilaver.quoter.fragments.QODFragment;
import co.dilaver.quoter.fragments.SettingsFragment;
import co.dilaver.quoter.fragments.WriteYourOwnFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public interface ActionBarItemsClickListener {
        void qodFavoriteClicked();

        void qodCopyClicked();

        void wyoSaveClicked();

        void wyoShareClicked();

        void pqInfoClicked();
    }

    private MenuItem qodFavorite;
    private MenuItem qodCopy;
    private MenuItem wyoDone;
    private MenuItem wyoShare;
    private MenuItem pqInfo;

    private ActionBarItemsClickListener actionBarItemsClickListener;
    private Fragment fragment;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.str_QOD));
        }

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        fragment = new QODFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (fragment instanceof QODFragment) {
            super.onBackPressed();
            return;
        }
        View view = bottomNavigationView.findViewById(R.id.action_quote_of_the_day);
        view.performClick();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        qodFavorite = menu.findItem(R.id.qod_action_favorite);
        qodCopy = menu.findItem(R.id.qod_action_copy);
        wyoDone = menu.findItem(R.id.wyo_action_done);
        wyoShare = menu.findItem(R.id.wyo_action_share);
        pqInfo = menu.findItem(R.id.pq_action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.qod_action_favorite:
                if (actionBarItemsClickListener != null) {
                    actionBarItemsClickListener.qodFavoriteClicked();
                }
                return true;
            case R.id.qod_action_copy:
                if (actionBarItemsClickListener != null) {
                    actionBarItemsClickListener.qodCopyClicked();
                }
                return true;
            case R.id.wyo_action_done:
                if (actionBarItemsClickListener != null) {
                    actionBarItemsClickListener.wyoSaveClicked();
                }
                return true;
            case R.id.wyo_action_share:
                if (actionBarItemsClickListener != null) {
                    actionBarItemsClickListener.wyoShareClicked();
                }
                return true;
            case R.id.pq_action_info:
                if (actionBarItemsClickListener != null) {
                    actionBarItemsClickListener.pqInfoClicked();
                }
                return true;
            case R.id.action_about_me:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.str_AboutMe));
                }
                qodFavorite.setVisible(false);
                qodCopy.setVisible(false);
                wyoDone.setVisible(false);
                wyoShare.setVisible(false);
                pqInfo.setVisible(false);

                bottomNavigationView.setVisibility(View.GONE);

                fragment = new AboutMeFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.commit();
                return true;
            case R.id.action_credits:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.str_Credits));
                }
                qodFavorite.setVisible(false);
                qodCopy.setVisible(false);
                wyoDone.setVisible(false);
                wyoShare.setVisible(false);
                pqInfo.setVisible(false);

                bottomNavigationView.setVisibility(View.GONE);

                fragment = new CreditsFragment();
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.replace(R.id.mainFrame, fragment);
                ft2.commit();
                return true;
            case R.id.action_settings:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.str_Settings));
                }
                qodFavorite.setVisible(false);
                qodCopy.setVisible(false);
                wyoDone.setVisible(false);
                wyoShare.setVisible(false);
                pqInfo.setVisible(false);

                bottomNavigationView.setVisibility(View.GONE);

                fragment = new SettingsFragment();
                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                ft3.replace(R.id.mainFrame, fragment);
                ft3.commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_quote_of_the_day:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.str_QOD));
                }
                qodFavorite.setVisible(true);
                qodCopy.setVisible(true);
                wyoDone.setVisible(false);
                wyoShare.setVisible(false);
                pqInfo.setVisible(false);

                bottomNavigationView.setVisibility(View.VISIBLE);

                fragment = new QODFragment();
                break;
            case R.id.action_popular_quotes:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.str_PopularQuotes));
                }
                qodFavorite.setVisible(false);
                qodCopy.setVisible(false);
                wyoDone.setVisible(false);
                wyoShare.setVisible(false);
                pqInfo.setVisible(true);

                fragment = new PopularFragment();
                break;
            case R.id.action_write_your_own:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.str_WriteYourOwn));
                }
                qodFavorite.setVisible(false);
                qodCopy.setVisible(false);
                wyoDone.setVisible(true);
                wyoShare.setVisible(true);
                pqInfo.setVisible(false);

                fragment = new WriteYourOwnFragment();
                break;
            case R.id.action_favorite_quotes:
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.str_FavoriteQuotes));
                }
                qodFavorite.setVisible(false);
                qodCopy.setVisible(false);
                wyoDone.setVisible(false);
                wyoShare.setVisible(false);
                pqInfo.setVisible(false);

                fragment = new FavoriteQuotesFragment();
                break;
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();

        return true;
    }

    public void setActionBarItemsClickListener(ActionBarItemsClickListener actionBarItemsClickListener) {
        this.actionBarItemsClickListener = actionBarItemsClickListener;
    }

}
