package com.ehedgehog.android.videoapp.videolist;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ehedgehog.android.videoapp.R;
import com.ehedgehog.android.videoapp.Video;
import com.ehedgehog.android.videoapp.videoplayer.VideoPlayerFragment;

import java.io.File;

public class VideoHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener, VideoListItemView {

    private TextView mVideoTitle;
    private TextView mDuration;
    private TextView mDateAdded;
    private ImageView mVideoThumbnail;
    private VideoListPresenter mPresenter;

    public VideoHolder(@NonNull View itemView, VideoListPresenter presenter) {
        super(itemView);
        mPresenter = presenter;
        itemView.setOnClickListener(this);

        mVideoTitle = itemView.findViewById(R.id.video_title);
        mDuration = itemView.findViewById(R.id.video_duration);
        mDateAdded = itemView.findViewById(R.id.video_date_added);
        mVideoThumbnail = itemView.findViewById(R.id.video_thumbnail);
    }

    @Override
    public void bind(Video video) {
        mVideoTitle.setText(video.getTitle());

        String formattedDuration = DateFormat.format(itemView.getContext()
                .getString(R.string.duration_format), video.getDuration()).toString();
        mDuration.setText(formattedDuration);

        String formattedDate = DateFormat.format(itemView.getContext()
                .getString(R.string.date_format), video.getDateAdded() * 1000).toString();
        mDateAdded.setText(formattedDate);

        Glide.with(itemView.getContext()).load(new File(video.getLocation())).into(mVideoThumbnail);
    }

    @Override
    public void navigateToVideoPlayer(Video video) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(VideoPlayerFragment.ARG_VIDEO, video);
        Navigation.findNavController(itemView)
                .navigate(R.id.action_videoListFragment_to_videoPlayerFragment, bundle);
    }

    @Override
    public void onClick(View v) {
        mPresenter.onItemClick(this, getAdapterPosition());
    }
}
