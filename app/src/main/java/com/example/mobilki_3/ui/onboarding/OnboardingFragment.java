package com.example.mobilki_3.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mobilki_3.R;
import com.example.mobilki_3.ui.login.LoginFragment;

public class OnboardingFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.btnEnterToAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OnboardingFragment.this)
                        .navigate(R.id.action_OnboardingFragment_to_loginFragment);
            }
        });

        view.findViewById(R.id.textViewRegisterOnboarding).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(OnboardingFragment.this)
                        .navigate(R.id.action_OnboardingFragment_to_registerFragment);
            }
        });
    }
}