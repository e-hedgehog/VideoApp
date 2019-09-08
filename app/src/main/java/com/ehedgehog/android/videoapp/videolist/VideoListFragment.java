package com.ehedgehog.android.videoapp.videolist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ehedgehog.android.videoapp.R;
import com.ehedgehog.android.videoapp.Video;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Video.*;

public class VideoListFragment extends Fragment implements VideoListView{

    private static final String STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final int REQUEST_STORAGE_PERMISSION = 0;

    private TextView mEmptyView;
    private RecyclerView mRecyclerView;
    private VideosAdapter mAdapter;
    private VideoListPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = new VideoListPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video_list, container, false);

        mEmptyView = v.findViewById(R.id.video_list_empty_view);

        mRecyclerView = v.findViewById(R.id.video_list_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new VideosAdapter(mPresenter);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_STORAGE_PERMISSION:
                if (hasStoragePermission())
                    updateVideos();
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void showVideoList() {
        if (hasStoragePermission()) {
            updateVideos();
        } else
            requestPermissions(new String[]{STORAGE_PERMISSION}, REQUEST_STORAGE_PERMISSION);
    }

    @Override
    public void showEmptyView() {
        mEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideEmptyView() {
        mEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void updateVideos() {
        mPresenter.setVideos(findVideoFiles());
        mAdapter.notifyDataSetChanged();
    }

    private List<Video> findVideoFiles() {
        List<Video> videos = new ArrayList<>();
        Uri uri = Media.EXTERNAL_CONTENT_URI;
        String[] projection = {VideoColumns.TITLE, VideoColumns.DATA,
                VideoColumns.DURATION, VideoColumns.DATE_ADDED};
        try (Cursor cursor = getActivity().getContentResolver()
                .query(uri, projection, null, null, null)) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    videos.add(getVideoFromCursor(cursor));
                }
            }
        }
        return videos;
    }

    private Video getVideoFromCursor(Cursor cursor) {
        Video video = new Video();
        video.setTitle(cursor.getString(cursor.getColumnIndex(VideoColumns.TITLE)));
        video.setLocation(cursor.getString(cursor.getColumnIndex(VideoColumns.DATA)));
        video.setDuration(cursor.getLong(cursor.getColumnIndex(VideoColumns.DURATION)));
        video.setDateAdded(cursor.getLong(cursor.getColumnIndex(VideoColumns.DATE_ADDED)));
        return video;
    }

    private boolean hasStoragePermission() {
        return ContextCompat.checkSelfPermission(getActivity(), STORAGE_PERMISSION) ==
                PackageManager.PERMISSION_GRANTED;
    }

}
