package com.ehedgehog.android.videoapp.videolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ehedgehog.android.videoapp.R;

public class VideosAdapter extends RecyclerView.Adapter<VideoHolder> {

    //    private List<Video> mVideos;
    private VideoListPresenter mPresenter;

    public VideosAdapter(VideoListPresenter presenter) {
        mPresenter = presenter;
    }

//    public VideosAdapter(List<Video> videos) {
//        mVideos = videos;
//    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_video, parent, false);
        return new VideoHolder(view, mPresenter);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {
        mPresenter.onBindVideoItem(holder, position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getVideoListSize();
    }

//    public void setVideos(List<Video> videos) {
//        mVideos = videos;
//        notifyDataSetChanged();
//    }
}
