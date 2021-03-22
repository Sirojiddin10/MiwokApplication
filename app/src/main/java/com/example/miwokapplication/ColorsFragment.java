package com.example.miwokapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ColorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ColorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorsFragment newInstance(String param1, String param2) {
        ColorsFragment fragment = new ColorsFragment();
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
        Context context = getContext();
        words.add(new Word("red", "weṭeṭṭi", R.mipmap.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.mipmap.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.mipmap.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.mipmap.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.mipmap.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.mipmap.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә", R.mipmap.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.mipmap.color_mustard_yellow, R.raw.color_mustard_yellow));
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(wordAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
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