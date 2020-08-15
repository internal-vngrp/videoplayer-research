package com.hitek.videoplayerts;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ext.rtmp.RtmpDataSourceFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hitek.vlc_player.MediaControl;
import com.vlc.lib.VlcVideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import hb.xvideoplayer.MxVideoPlayer;
import hb.xvideoplayer.MxVideoPlayerWidget;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

//    private String mMediaURL = "https://www.hdpvrcapture.com/hdpvrcapturev3/samples/20140122_132744-1920x1080p30.ts";
//    private String mMediaURL = "https://www.hdpvrcapture.com/hdpvrcapture/samples/20090228_085119-H.264.m2ts.mp4";
//    private String mMediaURL = "rtmp://162.211.126.179/live/1016";
    private String mMediaURL = "https://www.hdpvrcapture.com/hdpvrcapturev3/samples/20131211_102729-1920x1080p30.ts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//        prepareMxVideoPlayer();
        prepareExoVideoPlayer();
//        prepareVLCVideoPlayer();
//        prepareDefaultVideoPlayer();

    }

    void prepareMxVideoPlayer() {
        MxVideoPlayerWidget videoPlayerWidget = (MxVideoPlayerWidget) findViewById(R.id.mpw_video_player);
//        videoPlayerWidget.autoStartPlay(mMediaURL, MxVideoPlayer.SCREEN_LAYOUT_NORMAL, "video name");
        videoPlayerWidget.startPlay(mMediaURL, MxVideoPlayer.SCREEN_LAYOUT_NORMAL, "video name");
    }

    // can play rtmp
    void prepareExoVideoPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

//Create the player
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        PlayerView playerView = findViewById(R.id.simple_player);
        playerView.setPlayer(player);

        RtmpDataSourceFactory rtmpDataSourceFactory = new RtmpDataSourceFactory();
// This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(rtmpDataSourceFactory)
                .createMediaSource(Uri.parse(mMediaURL));

// Prepare the player with the source.
        player.prepare(videoSource);
//auto start playing
        player.setPlayWhenReady(true);
    }

    // Can play rtmp and .ts (not seekable, not get duration)
    private VlcVideoView vlcVideoView;
    private TextView logInfo;
    void prepareVLCVideoPlayer() {
        logInfo = findViewById(R.id.info);
        vlcVideoView = findViewById(R.id.player);

        vlcVideoView.setMediaListenerEvent(new MediaControl(vlcVideoView, logInfo));
        vlcVideoView.setPath(mMediaURL);
        vlcVideoView.startPlay();
    }

    // Can not play .ts and rtmp
    private VideoView videoView;
    private int position = 0;
    private MediaController mediaController;
    void prepareDefaultVideoPlayer() {
        videoView = (VideoView) findViewById(R.id.videoView);
        mediaController = new MediaController(MainActivity.this);
        // Set the videoView that acts as the anchor for the MediaController.
        mediaController.setAnchorView(videoView);
        // Set MediaController for VideoView
        videoView.setMediaController(mediaController);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {

                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });

        playURLVideo(this, videoView, mMediaURL);
    }

    // String videoURL = "https://raw.githubusercontent.com/o7planning/webexamples/master/_testdatas_/mov_bbb.mp4";
    // String videoURL = "https://www.radiantmediaplayer.com/media/bbb-360p.mp4";
    public static void playURLVideo(Context context, VideoView videoView, String videoURL)  {
        try {
            Log.i(TAG, "Video URL: "+ videoURL);

            Uri uri= Uri.parse( videoURL );

            videoView.setVideoURI(uri);
            videoView.requestFocus();

        } catch(Exception e) {
            Log.e(TAG, "Error Play URL Video: "+ e.getMessage());
            Toast.makeText(context,"Error Play URL Video: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MxVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
//        if (MxVideoPlayer.backPress()) {
//            return;
//        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}