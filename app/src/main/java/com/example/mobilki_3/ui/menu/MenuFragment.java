package com.example.mobilki_3.ui.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilki_3.R;
import com.example.mobilki_3.ui.listen.ListenFragment;
import com.example.mobilki_3.ui.login.LoginViewModel;
import com.example.mobilki_3.ui.main.MainFragment;
import com.example.mobilki_3.ui.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuFragment extends Fragment {
    private LoginViewModel loginViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View screenView = inflater.inflate(R.layout.fragment_menu, container, false);
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        BottomNavigationView bottomNav = screenView.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (loginViewModel.getLoginResult().getValue().getSuccess().isProfile()){
            bottomNav.setSelectedItemId(R.id.item_profile);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ProfileFragment()).commit();
        } else {
            bottomNav.setSelectedItemId(R.id.item_main);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new MainFragment()).commit();
        }


        // Inflate the layout for this fragment
        return screenView;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.item_main:
                    selectedFragment = new MainFragment();
                    break;
                case R.id.item_music:
                    selectedFragment = new ListenFragment();
                    break;
                case R.id.item_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            if (selectedFragment != null) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_content, selectedFragment)
                        .commit();
            }
            return true;
        }
    };
}