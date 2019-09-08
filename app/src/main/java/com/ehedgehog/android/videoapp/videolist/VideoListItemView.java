package com.ehedgehog.android.videoapp.videolist;

import com.ehedgehog.android.videoapp.Video;

public interface VideoListItemView {

    void bind(Video video);

    void navigateToVideoPlayer(Video video);

}
