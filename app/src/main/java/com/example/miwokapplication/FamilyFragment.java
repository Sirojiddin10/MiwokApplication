package com.example.miwokapplication;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FamilyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FamilyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FamilyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FamilyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FamilyFragment newInstance(String param1, String param2) {
        FamilyFragment fragment = new FamilyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_item, container, false);
        RecyclerView recyclerView;
        ArrayList<Word> words = new ArrayList<>();
        WordAdapter wordAdapter = new WordAdapter(words);
        words.add(new Word("father", "әpә", R.mipmap.family_father, R.raw.family_father));
        words.add(new Word("mother", "әṭa", R.mipmap.family_mother, R.raw.family_mother));
        words.add(new Word("son", "angsi", R.mipmap.family_son, R.raw.family_son));
        words.add(new Word("daughter", "tune", R.mipmap.family_daughter, R.raw.family_daughter));
        words.add(new Word("older brother", "taachi", R.mipmap.family_older_brother, R.raw.family_older_brother));
        words.add(new Word("younger brother", "chalitti", R.mipmap.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word("older sister", "teṭe", R.mipmap.family_older_sister, R.raw.family_older_sister));
        words.add(new Word("younger sister", "kolliti", R.mipmap.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word("grandmother", "ama", R.mipmap.family_grandmother, R.raw.family_grandmother));
        words.add(new Word("grandfather", "paapa", R.mipmap.family_grandfather, R.raw.family_grandfather));
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        wordAdapter.setOnItemClickListener(position -> {
            Word word = words.get(position);
            int request = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            if (request == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                releaseMediaPlayer();
                mediaPlayer = MediaPlayer.create(getContext(), word.getmAudioId());
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mp -> {
                            releaseMediaPlayer();
                        }
                );
            }
        });
        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = null;
        audioManager.abandonAudioFocus(audioFocusChangeListener);
    }
}