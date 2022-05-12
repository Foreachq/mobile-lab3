package com.example.mobilki_3.ui.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilki_3.R;
import com.example.mobilki_3.data.LoginRepository;
import com.example.mobilki_3.data.ProfileRepository;
import com.example.mobilki_3.data.model.Profile;
import com.example.mobilki_3.ui.profile.ProfileViewModel;
import com.example.mobilki_3.ui.profile.ProfileViewModelFactory;


import org.jetbrains.annotations.NotNull;

public class RegisterFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private ProfileViewModel profileViewModel;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(getActivity(), new LoginViewModelFactory()).get(LoginViewModel.class);
        //loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        profileViewModel = new ViewModelProvider(getActivity(), new ProfileViewModelFactory()).get(ProfileViewModel.class);

        final EditText loginEditText = view.findViewById(R.id.loginRegister);
        final EditText emailEditText = view.findViewById(R.id.emailRegister);
        final EditText passwordEditText = view.findViewById(R.id.passwordRegister);
        final Button registerButton = view.findViewById(R.id.btnRegister);
        final Button cancelButton = view.findViewById(R.id.btnCancelRegister);
        final TextView messageTextView = view.findViewById(R.id.textViewMessageRegister);
        final ProgressBar loadingProgressBar = view.findViewById(R.id.loadingRegister);
        messageTextView.setText("");

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                registerButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    emailEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    messageTextView.setText(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    String userId = loginViewModel.getLoginResult().getValue().getSuccess().getId();
                    profileViewModel.find(userId);
                    Profile profile = profileViewModel.getCurrentProfile().getValue();
                    if (profile == null){
                        profileViewModel.newProfile(userId, loginEditText.getText().toString(), emailEditText.getText().toString());
                    }
                    loginViewModel.login("","",false);  //// del ?????
                    NavHostFragment.findNavController(RegisterFragment.this)
                            .navigate(R.id.action_registerFragment_to_loginFragment);
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                messageTextView.setText("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(emailEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        emailEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        loginEditText.addTextChangedListener(afterTextChangedListener);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.register(emailEditText.getText().toString(),
                        passwordEditText.getText().toString(), loginEditText.getText().toString());
                LoginRepository.getInstance().close(getActivity().getApplicationContext());
                ProfileRepository.getInstance().close(getActivity().getApplicationContext());

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.login("","",false);
                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
            }
        });
    }
}