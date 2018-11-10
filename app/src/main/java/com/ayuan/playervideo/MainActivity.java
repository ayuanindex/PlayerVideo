package com.ayuan.playervideo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int currentPosition = 0;//当前视频播放的位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找到控件播放指定视频的内容
        final SurfaceView surfaceView = (SurfaceView) findViewById(R.id.sv_video);
        /*new Thread() {
            @Override
            public void run() {
                super.run();
                SystemClock.sleep(400);*/
        //获取Holder对象用来维护视频播放的内容
        SurfaceHolder holder = surfaceView.getHolder();
        //添加Holder的生命周期方法
        holder.addCallback(new SurfaceHolder.Callback() {

            //这个方法执行了说明SurfaceView准备好了
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //172.50.223.22
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource("http://172.50.223.22:8080/bb.mp4");
                    mediaPlayer.prepareAsync();
                    //设置显示个surfaceView  SurfaceHolder是用来维护视频播放的内容
                    mediaPlayer.setDisplay(holder);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                            //继续上次的位置进行播放
                            if (currentPosition != 0) {
                                mediaPlayer.seekTo(currentPosition);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            //当SurfaceView销毁的时候开始执行此方法（相当于SurfaceView不可见的时候）
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //停止播放视频
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    //获取到当前播放的位置
                    currentPosition = mediaPlayer.getCurrentPosition();
                    mediaPlayer.stop();
                }
            }
        });

   /*         }
        }.start();*/
    }
}
