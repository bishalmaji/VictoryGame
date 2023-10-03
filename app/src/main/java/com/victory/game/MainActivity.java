package com.victory.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.victory.game.fragments.Login;
import com.victory.game.fragments.Register;
import com.victory.game.utils.AppDataUtil;

public class MainActivity extends AppCompatActivity implements Register.OnMenuHiddenListener, Login.OnMenuHiddenListener{
    private BottomNavigationView bottomNavigationView;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         bottomNavigationView=findViewById(R.id.main_bottom_nav);

        bottomNavigationView.getMenu().findItem(R.id.login).setTitle("Profile");


        navController= Navigation.findNavController(this,R.id.nav_container);
        //set 3 or 4 menu item based on login status from shared pref.
        //Navigate to Home of login based on login status
        if(!isLoggedIn()){
            Log.e("TAG", "onCreate: main is called but button not changed" );
           //change the profile menu icon to register
            //navigate to profile fragment
            bottomNavigationView.getMenu().findItem(R.id.win).setVisible(false);
            bottomNavigationView.getMenu().findItem(R.id.profile).setVisible(false);
            bottomNavigationView.getMenu().findItem(R.id.login).setVisible(true);
//            //we can opt for navigate to profile

        }else{
            bottomNavigationView.getMenu().findItem(R.id.profile).setVisible(true);
            bottomNavigationView.getMenu().findItem(R.id.win).setVisible(true);
            bottomNavigationView.getMenu().findItem(R.id.login).setVisible(false);
        }

        NavigationUI.setupWithNavController(bottomNavigationView,navController);
    }

    private boolean isLoggedIn() {
        AppDataUtil appDataUtil=new AppDataUtil(MainActivity.this);
        return appDataUtil.getBooleanData("login");
    }

    @Override
    public void registerSuccess() {
        bottomNavigationView.getMenu().findItem(R.id.login).setVisible(false);
        bottomNavigationView.getMenu().findItem(R.id.profile).setVisible(true);
        bottomNavigationView.getMenu().findItem(R.id.win).setVisible(true);
        //pop out the register fragment and start home
        navController.popBackStack(R.id.home,false);

    }


    @Override
    public void loginSuccess() {
        bottomNavigationView.getMenu().findItem(R.id.login).setVisible(false);
        bottomNavigationView.getMenu().findItem(R.id.profile).setVisible(true);
        bottomNavigationView.getMenu().findItem(R.id.win).setVisible(true);
        //pop out the register fragment and start home
        navController.popBackStack(R.id.home,false);

    }
}