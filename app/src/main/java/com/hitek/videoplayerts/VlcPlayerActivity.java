package com.hitek.videoplayerts;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import hb.xvideoplayer.MxVideoPlayer;
import hb.xvideoplayer.MxVideoPlayerWidget;

public class VlcPlayerActivity extends AppCompatActivity {

//    private String mMediaURL = "https://www.hdpvrcapture.com/hdpvrcapturev3/samples/20140122_132744-1920x1080p30.ts";
//    private String mMediaURL = "https://www.hdpvrcapture.com/hdpvrcapture/samples/20090228_085119-H.264.m2ts.mp4";
//    private String mMediaURL = "rtmp://162.211.126.179/live/1016";
    private String mMediaURL = "https://www.hdpvrcapture.com/hdpvrcapturev3/samples/20131211_102729-1920x1080p30.ts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlc_player);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }

    public static String getUrl() {
//        return "rtmp://162.211.126.179/live/1016";
//        return "https://www.hdpvrcapture.com/hdpvrcapturev3/samples/20140122_132744-1920x1080p30.ts";

        return "https://www.hdpvrcapture.com/hdpvrcapturev3/samples/20131211_102729-1920x1080p30.ts";
//        return "https://www.hdpvrcapture.com/hdpvrcapture/samples/20090228_085119-H.264.m2ts.mp4";
    }

}