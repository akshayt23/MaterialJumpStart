package com.rubberduck.materialjumpstart.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.rubberduck.materialjumpstart.fragments.MainFragment;
import com.rubberduck.materialjumpstart.R;


public class MainActivity extends ActionBarActivity implements
        MainFragment.OnEnterNameFragmentInteractionListener {

    public static final String TAG = "MainActivity";

    AccountHeader.Result accountHeader;
    Drawer.Result drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);

        IProfile iProfile = new ProfileDrawerItem()
                .withName("Akshay")
                .withEmail("akshay.thapa23@gmail.com")
                .withIcon(getResources().getDrawable(R.drawable.photo));

        //Create AccountHeader
        accountHeader = new AccountHeader()
                .withActivity(this)
                .withHeaderBackground(R.drawable.navbar_header)
                .addProfiles(iProfile)
                .withSavedInstance(savedInstanceState)
                .build();

        //Create the drawer
        drawer = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(accountHeader)
                //.withFullscreen(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Option1").withIcon(R.drawable.ic_check_green_48dp).
                                withTextColor(Color.BLACK).withSelectedTextColor(Color.parseColor("#00796B")),
                        new PrimaryDrawerItem().withName("Option2").withIcon(R.drawable.ic_star_yellow_96dp).
                                withTextColor(Color.BLACK).withSelectedTextColor(Color.parseColor("#00796B")),
                        new PrimaryDrawerItem().withName("Option3").withIcon(R.drawable.ic_close_red_36dp).
                                withTextColor(Color.BLACK).withSelectedTextColor(Color.parseColor("#00796B")),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Settings").withIcon(R.drawable.ic_settings_grey600_48dp).
                                withTextColor(Color.BLACK).withSelectedTextColor(Color.parseColor("#00796B")),
                                //withTintSelectedIcon(true).withSelectedIcon(R.drawable.ic_settings_green_48dp),
                        new PrimaryDrawerItem().withName("Report").withIcon(R.drawable.ic_report_problem_grey600_48dp).
                                withTextColor(Color.BLACK).withSelectedTextColor(Color.parseColor("#00796B")),
                        new PrimaryDrawerItem().withName(("Rate Us On Google Play")).withIcon(R.drawable.ic_play_shopping_bag_grey600_48dp).
                                withTextColor(Color.BLACK).withSelectedTextColor(Color.parseColor("#00796B"))
                )
                .withSavedInstance(savedInstanceState)
                .build();

        drawer.setSelectionByIdentifier(1, false);
        drawer.keyboardSupportEnabled(this, true);
        accountHeader.setActiveProfile(iProfile);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEnterNameFragmentInteraction(String nameEntered) {
    }
}