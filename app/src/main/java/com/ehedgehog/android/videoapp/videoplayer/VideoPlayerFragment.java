package com.ehedgehog.android.videoapp.videoplayer;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ehedgehog.android.videoapp.R;
import com.ehedgehog.android.videoapp.Video;

public class VideoPlayerFragment extends Fragment implements VideoPlayerView{

    public static final String ARG_VIDEO = "video";
    private static final String KEY_POSITION = "position";

    private VideoView mVideoView;

    private Video mVideo;
    private int mCurrentPosition;

    private VideoPlayerPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mVideo = getArguments().getParcelable(ARG_VIDEO);

        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(KEY_POSITION);
        }

        mPresenter = new VideoPlayerPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_player, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null && mVideo != null) {
            activity.getSupportActionBar().setTitle(mVideo.getTitle());
        }

        Navigation.findNavController(activity, R.id.nav_host_fragment)
                .getCurrentDestination().setLabel(mVideo.getTitle());

        mVideoView = v.findViewById(R.id.video_player);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onSetupVideoPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPauseVideoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onResetVideoPlayer();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_POSITION, mVideoView.getCurrentPosition());
    }

    @Override
    public void setupVideoPlayer() {
        mVideoView.setVideoPath(mVideo.getLocation());
        MediaController mediaController = new MediaController(getActivity());
        mediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(mediaController);
        mVideoView.setOnPreparedListener(mp -> {
            mVideoView.seekTo(mCurrentPosition > 0 ? mCurrentPosition : 1);
            mVideoView.start();
        });

        mVideoView.setOnCompletionListener(mp -> mVideoView.seekTo(0));
    }

    @Override
    public void resetVideoPlayer() {
        mVideoView.stopPlayback();
    }

    @Override
    public void pauseVideoPlayer() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            mVideoView.pause();
    }
}
