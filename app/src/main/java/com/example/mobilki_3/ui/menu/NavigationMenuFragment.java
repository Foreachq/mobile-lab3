package com.example.mobilki_3.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mobilki_3.R;
import com.google.android.material.navigation.NavigationView;


public class NavigationMenuFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View screenView = inflater.inflate(R.layout.fragment_menu_navigation, container, false);
        final DrawerLayout drawerLayout = screenView.findViewById(R.id.drawerLayout);
        screenView.findViewById(R.id.btnMenuMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = screenView.findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        NavController navController = Navigation.findNavController(screenView.findViewById(R.id.navHostFragment));
        NavigationUI.setupWithNavController(navigationView, navController);

        // Inflate the layout for this fragment
        return screenView;
    }

}
