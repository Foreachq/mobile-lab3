package com.example.mobilki_3.ui.listen;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobilki_3.R;
import com.example.mobilki_3.ui.profile.ProfileViewModel;
import java.util.ArrayList;


public class ListenFragment extends Fragment {
    private ProfileViewModel profileViewModel;
    private ArrayList<Music> musicList = new ArrayList<>();
    private MediaPlayer mediaPlayer;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listen, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileViewModel = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);
        String mood = profileViewModel.getCurrentMood().getValue();
        if (mood == null) {
            mood = "";
        }

        final TextView moodTextView = view.findViewById(R.id.textViewMood);
        final Button playButton = view.findViewById(R.id.btnPlayListener);
        final Button pauseButton = view.findViewById(R.id.btnPauseListener);
        final Button resumeButton = view.findViewById(R.id.btnResumeListener);

        moodTextView.setText(mood);
        initializeMusicList(mood);
        RecyclerView recyclerView = view.findViewById(R.id.musicList);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        final MusicAdapter adapter = new MusicAdapter(view.getContext(), musicList);
        recyclerView.setAdapter(adapter);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getSelected() != null) {
                    releaseMP();
                    mediaPlayer = MediaPlayer.create(getActivity(), adapter.getSelected().getSource());
                    mediaPlayer.start();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mediaPlayer != null) && (mediaPlayer.isPlaying())) {
                    mediaPlayer.pause();
                }
            }
        });

        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((mediaPlayer != null) && (!mediaPlayer.isPlaying())) {
                    mediaPlayer.start();
                }
            }
        });

    }

    public void initializeMusicList(String mood){
        musicList.clear();
        if (mood.equals("calm")){
            musicList.add(new Music("Louis Armstrong","What A Wonderful World", R.raw.louis_armstrong_what_a_wonderful_world));
            musicList.add(new Music("Alex Band","Only One", R.raw.alex_band_only_one));
        } else if (mood.equals("relax")){
            musicList.add(new Music("Secret Garden","The Promise", R.raw.secret_garden_the_promise));
            musicList.add(new Music("Airub","But I Know You Are Along", R.raw.airub_but_i_know_you_are_along));
            musicList.add(new Music("Dj Rostej","Like A Dream", R.raw.dj_rostej_like_a_dream));
        } else if (mood.equals("focus")){
            musicList.add(new Music("Brighter Note","Morning Meditation", R.raw.brighter_note_morning_meditation));
            musicList.add(new Music("Tom Green","Hot Summer", R.raw.tom_green_hot_summer));
        } else if (mood.equals("excited")){
            musicList.add(new Music("Barracuda","Ass Up", R.raw.baracuda_ass_up));
            musicList.add(new Music("Scotty","Sundown", R.raw.scotty_sundown));
        } else {
            musicList.add(new Music("Louis Armstrong","What A Wonderful World", R.raw.louis_armstrong_what_a_wonderful_world));
            musicList.add(new Music("Alex Band","Only One", R.raw.alex_band_only_one));
            musicList.add(new Music("Secret Garden","The Promise", R.raw.secret_garden_the_promise));
            musicList.add(new Music("Airub","But I Know You Are Along", R.raw.airub_but_i_know_you_are_along));
            musicList.add(new Music("Dj Rostej","Like A Dream", R.raw.dj_rostej_like_a_dream));
            musicList.add(new Music("Brighter Note","Morning Meditation", R.raw.brighter_note_morning_meditation));
            musicList.add(new Music("Tom Green","Hot Summer", R.raw.tom_green_hot_summer));
            musicList.add(new Music("Barracuda","Ass Up", R.raw.baracuda_ass_up));
            musicList.add(new Music("Scotty","Sundown", R.raw.scotty_sundown));
        }
    }

    private void releaseMP() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMP();
    }
}
