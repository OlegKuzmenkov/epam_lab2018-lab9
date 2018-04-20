package com.oleg_kuzmenkov.android.nrgnavigationviewapplication;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private final String IMAGE_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTqdwpGZy2mKyTVAurmM1ZYzEjGQ7_nmx230t3YOcytYul4XNCnbw";
    private final String TAG = "Message";

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set Toolbar to replace the ActionBar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        //Setup the DrawerToggle
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,  R.string.drawer_close);

        // Bind actions the DrawerLayout to ActionBarToggle
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        ImageView imageView = headerLayout.findViewById(R.id.header_image);
        // Download and set image into ImageView
        Picasso.with(this).load(IMAGE_URL).into(imageView);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.content_frame);

        if (fragment == null){
            fragment = new MainFragment();
            fragmentManager.beginTransaction().add(R.id.content_frame, fragment).commit();
        }

        Log.i(TAG, "count of fragments in back stack = "+fragmentManager.getBackStackEntryCount());
        Log.i(TAG, "count of fragments in fragment manager = "+fragmentManager.getFragments().size());

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();
                Fragment fragment = null;

                switch(menuItem.getItemId()) {
                    case R.id.nav_first_fragment:
                        fragment = new FirstFragment();
                        break;
                    case R.id.nav_second_fragment:
                        fragment = new SecondFragment();
                        break;
                    default:
                        fragment = new MainFragment();
                }

                // Set title for action bar
                //setTitle(menuItem.getTitle());

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack(null).commit();

                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Synchronize the switching state after
        // there will be an onRestoreInstanceState
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //Send any configuration changes to the switches drawer
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            super.onBackPressed();
        }
        else {
            Fragment fragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            unCheckAllMenuItems();
        }
    }

    /**
     * Uncheck all menu items
     */
    private void unCheckAllMenuItems(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        int size = navigationView.getMenu().size();

        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }

    }
}
