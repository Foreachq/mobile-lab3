package com.example.mobilki_3.ui.profile;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilki_3.R;
import com.example.mobilki_3.data.LoginRepository;
import com.example.mobilki_3.data.ProfileRepository;
import com.example.mobilki_3.data.model.Profile;
import com.example.mobilki_3.ui.login.LoginFragment;
import com.example.mobilki_3.ui.login.LoginResult;
import com.example.mobilki_3.ui.login.LoginViewModel;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private LoginViewModel loginViewModel;
    private boolean isShowInfo = false;
    private static int RESULT_LOAD_IMAGE = 1;
    private ImageView photo;
    private Button infoButton, saveButton, changePhotoButton, bmiButton;
    private ConstraintLayout infoLayout;
    private TextView nameTextView, bmiTextView;
    private EditText loginEditText, emailEditText, phoneEditText, birthdayEditText, weightEditText, heightEditText;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(getActivity(), new ProfileViewModelFactory()).get(ProfileViewModel.class);
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        String userId = loginViewModel.getLoginResult().getValue().getSuccess().getId();
        profileViewModel.find(userId);
        Profile profile = profileViewModel.getCurrentProfile().getValue();

        photo = view.findViewById(R.id.imageViewPhotoProfile);
       // exitButton = view.findViewById(R.id.btnExitProfile);
        infoButton = view.findViewById(R.id.btnInfoProfile);
        saveButton = view.findViewById(R.id.btnSaveProfile);
        changePhotoButton = view.findViewById(R.id.btnChangePhoto);
        bmiButton = view.findViewById(R.id.buttonBMI);
        infoLayout = view.findViewById(R.id.layout_content);
        nameTextView = view.findViewById(R.id.textViewNameProfile);
        bmiTextView = view.findViewById(R.id.bmiTextView);
        loginEditText = view.findViewById(R.id.loginProfile);
        emailEditText = view.findViewById(R.id.emailProfile);
        phoneEditText = view.findViewById(R.id.phoneProfile);
        birthdayEditText = view.findViewById(R.id.birthdayProfile);
        weightEditText = view.findViewById(R.id.weightProfile);
        heightEditText = view.findViewById(R.id.heightProfile);

        if ((profile != null) && (profile.getPhoto() != null)) {
            photo.setImageBitmap(profile.getPhoto().getBitmap());
        } else {
            photo.setImageResource(R.drawable.avatar);
        }

        showInfo(profile);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)infoLayout.getLayoutParams();
        layoutParams.height = 0;
        infoLayout.setLayoutParams(layoutParams);

        /*
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginViewModel.login("","",false);
                Navigation.findNavController(view).navigate(R.id.action_navigationMenuFragment_to_OnboardingFragment);
            }
        });
        */
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowInfo){
                    isShowInfo = false;
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)infoLayout.getLayoutParams();
                    layoutParams.height = 0;
                    infoLayout.setLayoutParams(layoutParams);
                    infoButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_down));
                } else {
                    isShowInfo = true;
                    FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)infoLayout.getLayoutParams();
                    layoutParams.height = layoutParams.WRAP_CONTENT;
                    infoLayout.setLayoutParams(layoutParams);
                    infoButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.ic_arrow_up));
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                format.setLenient(false);
                Date date;
                try {
                    date = format.parse(birthdayEditText.getText().toString());
                } catch (ParseException e) {
                    date = new Date();
                }
                int weight = 70;
                try {
                    weight = Integer.parseInt(weightEditText.getText().toString());
                } catch (Exception ignore){}

                int height = 180;
                try {
                    height = Integer.parseInt(heightEditText.getText().toString());
                } catch (Exception ignore){}
                profileViewModel.saveProfileInfo(loginEditText.getText().toString(), date, weight, height, phoneEditText.getText().toString(), emailEditText.getText().toString());
                ProfileRepository.getInstance().close(getActivity().getApplicationContext());
                showInfo(profileViewModel.getCurrentProfile().getValue());
            }
        });

        profileViewModel.getProfileFormState().observe(getViewLifecycleOwner(), new Observer<ProfileFormState>() {
            @Override
            public void onChanged(@Nullable ProfileFormState profileFormState) {
                if (profileFormState == null) {
                    return;
                }
                saveButton.setEnabled(profileFormState.isDataValid());
                if (profileFormState.getEmailError() != null) {
                    emailEditText.setError(getString(profileFormState.getEmailError()));
                }
                if (profileFormState.getPhoneError() != null) {
                    phoneEditText.setError(getString(profileFormState.getPhoneError()));
                }
                if (profileFormState.getBirthdayError() != null) {
                    birthdayEditText.setError(getString(profileFormState.getBirthdayError()));
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                profileViewModel.profileDataChanged(emailEditText.getText().toString(),
                        phoneEditText.getText().toString(), birthdayEditText.getText().toString());
            }
        };
        loginEditText.addTextChangedListener(afterTextChangedListener);
        phoneEditText.addTextChangedListener(afterTextChangedListener);
        birthdayEditText.addTextChangedListener(afterTextChangedListener);

        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        bmiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
               int weight = Integer.parseInt(weightEditText.getText().toString());
               double height = (double)Integer.parseInt(heightEditText.getText().toString())/100;
               double bmi= weight / (Math.pow(height,2));
               bmiTextView.setText(getVerdict(bmi));
            }
        });
    }
    private String getVerdict(double bmi){
        if (bmi < 16) return "Значительный дефицит массы тела";
        if (bmi >= 16 && bmi < 18.5) return "Дефицит массы тела";
        if (bmi >= 18.5 && bmi < 25) return "Норма";
        if (bmi >= 25 && bmi < 30) return "Лишний вес";
        if (bmi >= 30 && bmi < 35) return "Ожирение первой степени";
        if (bmi >= 35 && bmi < 40) return "Ожирение второй степени";
        if (bmi >= 40) return "Ожирение третьей степени";
        return "Ваш вес идеален!";
    }
    public void showInfo(Profile profile){
        if (profile != null) {
            nameTextView.setText(profile.getName());
            loginEditText.setText(profile.getName());
            emailEditText.setText(profile.getEmail());
            phoneEditText.setText(profile.getPhoneNumber());
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            birthdayEditText.setText(dateFormat.format(profile.getBirthday()));
            weightEditText.setText(String.valueOf(profile.getWeight()));
            heightEditText.setText(String.valueOf(profile.getHeight()));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.imageViewPhotoProfile);
            imageView.setImageURI(selectedImage);
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            profileViewModel.saveProfilePhoto(drawable.getBitmap());
            ProfileRepository.getInstance().close(getActivity().getApplicationContext());
            LoginRepository.getInstance().close(getActivity().getApplicationContext());
        }
    }

}