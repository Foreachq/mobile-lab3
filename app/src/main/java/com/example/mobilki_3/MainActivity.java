package com.example.mobilki_3;

import android.os.Bundle;

import com.example.mobilki_3.data.LoginDataSource;
import com.example.mobilki_3.data.LoginRepository;
import com.example.mobilki_3.data.ProfileDataSource;
import com.example.mobilki_3.data.ProfileRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginDataSource loginDataSource = new LoginDataSource();
        loginDataSource.deserializeObjects(getApplicationContext());
        LoginRepository.getInstance().setDataSource(loginDataSource);

        ProfileDataSource profileDataSource = new ProfileDataSource();
        profileDataSource.deserializeObjects(getApplicationContext());
        ProfileRepository.getInstance().setDataSource(profileDataSource);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ProfileRepository.getInstance().close(getApplicationContext());
    }
}