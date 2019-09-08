package com.ehedgehog.android.videoapp.videolist;

import com.ehedgehog.android.videoapp.Video;

import java.util.Collections;
import java.util.List;

public class VideoListPresenter {

    private VideoListView mView;
    private List<Video> mVideos;

//    public VideoListPresenter(VideoListView view) {
//        mView = view;
//    }

    public void attachView(VideoListView view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }

    public void init() {
        if (mVideos == null) {
            mVideos = Collections.emptyList();
            mView.showEmptyView();
            mView.showVideoList();
        }
    }

    public void onBindVideoItem(VideoListItemView item, int position) {
        item.bind(mVideos.get(position));
    }

    public void onItemClick(VideoListItemView item, int position) {
        item.navigateToVideoPlayer(mVideos.get(position));
    }

    public int getVideoListSize() {
        return mVideos.size();
    }

    public void setVideos(List<Video> videos) {
        mVideos = videos;
        if (mVideos == null || mVideos.isEmpty())
            mView.showEmptyView();
        else
            mView.hideEmptyView();
    }
}
