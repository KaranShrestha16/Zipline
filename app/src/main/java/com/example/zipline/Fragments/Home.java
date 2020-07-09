package com.example.zipline.Fragments;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.zipline.R;

public class Home extends Fragment {
    private VideoView videoView_home;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        videoView_home=view.findViewById(R.id.video_view_home);
        MediaController mediaController= new MediaController(getContext());
        mediaController.setAnchorView(videoView_home);
        //set media controller
//        videoView_home.setMediaController(mediaController);
//        videoView_home.setZOrderMediaOverlay(true);
        // setting url of video
        videoView_home.setVideoURI(Uri.parse("android.resource://"+getContext().getPackageName()+"/"+R.raw.video));
        videoView_home.requestFocus();
        videoView_home.setZOrderOnTop(false);
//        // muting the sound of video
//        AudioManager audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
//        videoView_home.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.setLooping(true);
//            }
//        });

        videoView_home.start();

        return view;
    }


}
