
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
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import co.dilaver.quoter.fragments.AboutMeFragment;
import co.dilaver.quoter.fragments.CreditsFragment;
import co.dilaver.quoter.fragments.FavoriteQuotesFragment;
import co.dilaver.quoter.fragments.PopularFragment;
import co.dilaver.quoter.fragments.QODFragment;
import co.dilaver.quoter.R;
import co.dilaver.quoter.fragments.WriteYourOwnFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    MenuItem qodFavorite;
    MenuItem qodShare;
    MenuItem wyoDone;
    MenuItem wyoShare;
    MenuItem pqInfo;

    ActionBarItemsClickListener actionBarItemsClickListener;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.str_QOD));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = new QODFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (fragment instanceof QODFragment) {
                super.onBackPressed();
            } else {
                getSupportActionBar().setTitle(getString(R.string.str_QOD));

                qodFavorite.setVisible(true);
                qodShare.setVisible(true);
                wyoDone.setVisible(false);
                wyoShare.setVisible(false);
                pqInfo.setVisible(false);

                fragment = new QODFragment();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, fragment);
                ft.commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        qodFavorite = menu.findItem(R.id.qod_action_favorite);
        qodShare = menu.findItem(R.id.qod_action_share);
        wyoDone = menu.findItem(R.id.wyo_action_done);
        wyoShare = menu.findItem(R.id.wyo_action_share);
        pqInfo = menu.findItem(R.id.pq_action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.qod_action_favorite) {
            if (actionBarItemsClickListener != null) {
                actionBarItemsClickListener.qodFavoriteClicked();
            }

            return true;
        } else if (id == R.id.qod_action_share) {
            if (actionBarItemsClickListener != null) {
                actionBarItemsClickListener.qodShareClicked();
            }

            return true;
        } else if (id == R.id.wyo_action_done) {
            if (actionBarItemsClickListener != null) {
                actionBarItemsClickListener.wyoSaveClicked();
            }

            return true;
        } else if (id == R.id.wyo_action_share) {
            if (actionBarItemsClickListener != null) {
                actionBarItemsClickListener.wyoShareClicked();
            }

            return true;
        } else if (id == R.id.pq_action_info) {
            if (actionBarItemsClickListener != null) {
                actionBarItemsClickListener.pqInfoCliked();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.qod) {
            getSupportActionBar().setTitle(getString(R.string.str_QOD));

            qodFavorite.setVisible(true);
            qodShare.setVisible(true);
            wyoDone.setVisible(false);
            wyoShare.setVisible(false);
            pqInfo.setVisible(false);

            fragment = new QODFragment();

        } else if (id == R.id.qodPopular) {
            getSupportActionBar().setTitle(getString(R.string.str_PopularQuotes));

            qodFavorite.setVisible(false);
            qodShare.setVisible(false);
            wyoDone.setVisible(false);
            wyoShare.setVisible(false);
            pqInfo.setVisible(true);

            fragment = new PopularFragment();

        } else if (id == R.id.writeYourOwn) {
            getSupportActionBar().setTitle(getString(R.string.str_WriteYourOwn));

            qodFavorite.setVisible(false);
            qodShare.setVisible(false);
            wyoDone.setVisible(true);
            wyoShare.setVisible(true);
            pqInfo.setVisible(false);

            fragment = new WriteYourOwnFragment();

        } else if (id == R.id.aboutMe) {
            getSupportActionBar().setTitle(getString(R.string.str_AboutMe));

            qodFavorite.setVisible(false);
            qodShare.setVisible(false);
            wyoDone.setVisible(false);
            wyoShare.setVisible(false);
            pqInfo.setVisible(false);

            fragment = new AboutMeFragment();

        } else if (id == R.id.credits) {
            getSupportActionBar().setTitle(getString(R.string.str_Credits));

            qodFavorite.setVisible(false);
            qodShare.setVisible(false);
            wyoDone.setVisible(false);
            wyoShare.setVisible(false);
            pqInfo.setVisible(false);

            fragment = new CreditsFragment();

        } else if (id == R.id.favoriteQuotes) {
            getSupportActionBar().setTitle(getString(R.string.str_FavoriteQuotes));

            qodFavorite.setVisible(false);
            qodShare.setVisible(false);
            wyoDone.setVisible(false);
            wyoShare.setVisible(false);
            pqInfo.setVisible(false);

            fragment = new FavoriteQuotesFragment();
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame, fragment);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public interface ActionBarItemsClickListener {
        void qodFavoriteClicked();

        void qodShareClicked();

        void wyoSaveClicked();

        void wyoShareClicked();

        void pqInfoCliked();
    }

    public void setActionBarItemsClickListener(ActionBarItemsClickListener actionBarItemsClickListener) {
        this.actionBarItemsClickListener = actionBarItemsClickListener;
    }


}
