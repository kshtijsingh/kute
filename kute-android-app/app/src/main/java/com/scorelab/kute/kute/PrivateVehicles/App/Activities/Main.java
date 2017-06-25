package com.scorelab.kute.kute.PrivateVehicles.App.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scorelab.kute.kute.PrivateVehicles.App.DataModels.Person;
import com.scorelab.kute.kute.PrivateVehicles.App.Fragments.HomeBaseFragment;
import com.scorelab.kute.kute.PrivateVehicles.App.Services.SyncFacebookFriendsToFirebase;
import com.scorelab.kute.kute.R;

import org.json.JSONArray;
import org.json.JSONObject;


public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView route_request_number;
    private final String TAG = "MainPrivateVehicle";
    BroadcastReceiver sync_friend_service_receiver;
    IntentFilter filter_sync_friend_receiver;
    private final String Action=SyncFacebookFriendsToFirebase.class.getName()+"Complete";

/*********************** Overrides ************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        route_request_number = (TextView) findViewById(R.id.numberRouteRequests);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /****************************** Drawer Layout Setup ************************/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        /******************************* End Of Drawer Setup **************************/

        /********************* Load Home Base Fragment**********/
        getSupportFragmentManager().beginTransaction().replace(R.id.frameDrawer, new HomeBaseFragment()).commit();
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("user_credentials", 0); // 0 - for private mode

        //Initialising Broadcast receiver with its intent filter
        sync_friend_service_receiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"Returned From SyncFacebookFriendsToFirebase Service");
                //TODO alter the friend fragment to show up friends
                Boolean x=pref.getBoolean("Sync_Friends_db", true);
                Log.d(TAG,"onReceive Broadcast"+x.toString());
            }
        };
        filter_sync_friend_receiver=new IntentFilter(Action);

        /****************** Sync Data For First Timers to the app *********/
        //Register yourself to the firebase db if this is the first time the user enters the app


        Log.d("SharedPreference", pref.getString("Profile_Image", null));



    }

    /****************** End of Overrides ********************/
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(sync_friend_service_receiver,filter_sync_friend_receiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(sync_friend_service_receiver);
    }

    /************* Functions handling the navigation drawer **************/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /********************* End of nav drawer functions *********************/

}