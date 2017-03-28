package com.example.administrator.treasure;


import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.treasure.commons.ActivityUtils;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/27.
 */

public class MainMP4Framgment extends Fragment implements TextureView.SurfaceTextureListener {


    /**
     * 视频播放：
     * 1. MediaPlayer播放视频
     * 2. TextureView展示视图播放
     * 使用TextureView的时候我们需要SufaceTexure，渲染、呈现我们播放的内容
     */


    private TextureView textureView;
    private ActivityUtils mActivityUtils;
    private MediaPlayer mMediaPlayer;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Fragment全屏显示播放视频的控件
        textureView = new TextureView(getActivity());

        return textureView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 当我们所要展示的视频播放的控件已经准备好的时候，就可以播放视频
        // 什么时候准备好呢，我们可以设置一个监听，看有没有准备好或者是有没有变化
        textureView.setSurfaceTextureListener(this);
    }

    // 确实准备好了，可以进行视频播放的展示了
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, int i, int i1) {

        /**
         * 视频展示的控件已经准备好了，可以播放视频了
         * 1. 找到播放的资源
         * 2. 什么时候可以播放：使用MediaPlayer播放，当他准备好的时候可以播放
         * 3. 基本设置在播放之前可以设置一下：播放到哪个上面、设置循环播放等
         */

        try {
            // 打开播放的资源文件
            AssetFileDescriptor openFd = getContext().getAssets().openFd("welcome.mp4");

            // 拿到MediaPlayer需要的资源类型
            FileDescriptor fileDescriptor = openFd.getFileDescriptor();

            mMediaPlayer = new MediaPlayer();

            // 设置播放的资源给MediaPlayer
            mMediaPlayer.setDataSource(fileDescriptor, openFd.getStartOffset(), openFd.getLength());

            // 异步准备
            mMediaPlayer.prepareAsync();

            // 设置准备的监听：看一下有没有准备好，可不可以播放
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                // 准备好了，视频可以播放了
                @Override
                public void onPrepared(MediaPlayer mp) {

                    Surface mySurface = new Surface(surfaceTexture);
                    mMediaPlayer.setSurface(mySurface);
                    mMediaPlayer.setLooping(true);// 循环播放
                    mMediaPlayer.start();// 开始播放
                }
            });

        } catch (IOException e) {
            mActivityUtils.showToast("媒体文件播放失败");
        }


    }

    // 视频展示的大小变化结束的时候
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    // 销毁的时候
    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    // 更新的时候
    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            // 释放资源
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
