package com.example.mobilki_3.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilki_3.R;
import com.example.mobilki_3.data.model.Profile;
import com.example.mobilki_3.horoscope.Horoscope;
import com.example.mobilki_3.horoscope.ZodiacSign;
import com.example.mobilki_3.horoscope.ZodiacSignAdapter;
import com.example.mobilki_3.ui.login.LoginResult;
import com.example.mobilki_3.ui.login.LoginViewModel;
import com.example.mobilki_3.ui.profile.ProfileViewModel;
import com.example.mobilki_3.ui.profile.ProfileViewModelFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private LoginViewModel loginViewModel;
    private static final HashMap<Integer, ToggleButton> toggleGroup = new HashMap<>();
    private View screenView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        screenView = inflater.inflate(R.layout.fragment_main, container, false);
        loginViewModel = new ViewModelProvider(getActivity()).get(LoginViewModel.class);
        profileViewModel = new ViewModelProvider(getActivity(), new ProfileViewModelFactory()).get(ProfileViewModel.class);
        String userId = loginViewModel.getLoginResult().getValue().getSuccess().getId();
        profileViewModel.find(userId);
        Profile profile = profileViewModel.getCurrentProfile().getValue();

        //final ImageView photo = (ImageView) screenView.findViewById(R.id.imageView);
        final TextView nameTextView = (TextView) screenView.findViewById(R.id.textViewNameMain);
        final ToggleButton buttonMood1 = (ToggleButton) screenView.findViewById(R.id.toggleButton1);
        final ToggleButton buttonMood2 = (ToggleButton) screenView.findViewById(R.id.toggleButton2);
        final ToggleButton buttonMood3 = (ToggleButton) screenView.findViewById(R.id.toggleButton3);
        final ToggleButton buttonMood4 = (ToggleButton) screenView.findViewById(R.id.toggleButton4);
        toggleGroup.clear();
        toggleGroup.put(0, buttonMood1);
        toggleGroup.put(1, buttonMood2);
        toggleGroup.put(2, buttonMood3);
        toggleGroup.put(3, buttonMood4);

        /*
        if ((profile != null) && (profile.getPhoto() != null)) {
            photo.setImageBitmap(profile.getPhoto().getBitmap());
        } else {
            photo.setImageResource(R.drawable.avatar);
        }

         */

        if (profile != null) {
            String welcome = getString(R.string.welcome) + profile.getName();
            nameTextView.setText(welcome);
        }

        String currentMood = "";
        if (profileViewModel.getCurrentMood().getValue() != null){
            currentMood = profileViewModel.getCurrentMood().getValue();
        }
        if (currentMood.equals("calm")){
            buttonMood1.setChecked(true);
            buttonMood1.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.calm_light));
        }
        if (currentMood.equals("relax")){
            buttonMood2.setChecked(true);
            buttonMood2.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.relax_light));
        }
        if (currentMood.equals("focus")){
            buttonMood3.setChecked(true);
            buttonMood3.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.focus_light));
        }
        if (currentMood.equals("excited")){
            buttonMood4.setChecked(true);
            buttonMood4.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.excited_light));
        }

        getWebsite();

        CompoundButton.OnCheckedChangeListener toggleButtonListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    // if toggle button is enabled/on
                    if (compoundButton == buttonMood1){
                        profileViewModel.setCurrentMood("calm");
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.calm_light));
                    }
                    if (compoundButton == buttonMood2){
                        profileViewModel.setCurrentMood("relax");
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.relax_light));
                    }
                    if (compoundButton == buttonMood3){
                        profileViewModel.setCurrentMood("focus");
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.focus_light));
                    }
                    if (compoundButton == buttonMood4){
                        profileViewModel.setCurrentMood("excited");
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.excited_light));
                    }
                    for (HashMap.Entry<Integer, ToggleButton> button : toggleGroup.entrySet()) {
                        if (!button.getValue().equals(compoundButton)){
                            button.getValue().setChecked(false);
                        }
                    }
                }
                else{
                    // If toggle button is disabled/off
                    if (compoundButton == buttonMood1){
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.calm_dark));
                    }
                    if (compoundButton == buttonMood2){
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.relax_dark));
                    }
                    if (compoundButton == buttonMood3){
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.focus_dark));
                    }
                    if (compoundButton == buttonMood4){
                        compoundButton.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.excited_dark));
                    }
                }
            }
        };
        buttonMood1.setOnCheckedChangeListener(toggleButtonListener);
        buttonMood2.setOnCheckedChangeListener(toggleButtonListener);
        buttonMood3.setOnCheckedChangeListener(toggleButtonListener);
        buttonMood4.setOnCheckedChangeListener(toggleButtonListener);

/*
        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                if (loginResult.getSuccess() != null) {
                    String welcome = getString(R.string.welcome) + loginResult.getSuccess().getDisplayName();
                    nameTextView.setText(welcome);
                }
            }
        });


 */
        return screenView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect(Horoscope.getInstance().getHoroscopeUrl()).get();
                    Elements products = doc.getElementsByClass("horoscope_list");
                    int signIndex = 1;
                    for (ZodiacSign zodiacSign : Horoscope.getInstance().getHoroscopeList()) {
                        String text = products.get(0).getElementsByClass("text_box").get(signIndex).text();
                        int pos = text.indexOf(" ");
                        text = text.substring(pos + 1);
                        pos = text.indexOf(" ");
                        text = text.substring(pos + 1);
                        Horoscope.getInstance().setText(zodiacSign, text);

                        Bitmap image = null;
                        try {
                            URL url = new URL(zodiacSign.getImgUrl());
                            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        } catch (IOException e) {
                            builder.append("Error : ").append(e.getMessage()).append("\n");
                        }
                        zodiacSign.setImage(image);
                        signIndex++;
                    }
                } catch (Exception e){
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }
                showHoroscope();
            }
        }).start();
    }

    private void showHoroscope(){
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RecyclerView recyclerView = screenView.findViewById(R.id.horoscopeList);
                    recyclerView.addItemDecoration(new DividerItemDecoration(screenView.getContext(), LinearLayoutManager.VERTICAL));
                    ZodiacSignAdapter adapter = new ZodiacSignAdapter(screenView.getContext(), Horoscope.getInstance().getHoroscopeList());
                    recyclerView.setAdapter(adapter);
                }
            });
        }
    }

}

