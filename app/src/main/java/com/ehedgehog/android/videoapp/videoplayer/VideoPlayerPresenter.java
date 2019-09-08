package com.ehedgehog.android.videoapp.videoplayer;

public class VideoPlayerPresenter {

    private VideoPlayerView mView;

    public VideoPlayerPresenter(VideoPlayerView view) {
        mView = view;
    }

    public void onSetupVideoPlayer() {
        mView.setupVideoPlayer();
    }

    public void onPauseVideoPlayer() {
        mView.pauseVideoPlayer();
    }

    public void onResetVideoPlayer() {
        mView.resetVideoPlayer();
    }

}
