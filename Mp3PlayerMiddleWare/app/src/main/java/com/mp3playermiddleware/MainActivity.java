package com.mp3playermiddleware;
import mp3.*;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import dyanamitechetan.vusikview.VusikView;

public class MainActivity extends AppCompatActivity implements  MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener{

    private ImageButton btn_play_pause;
    private SeekBar seekBar;
    private TextView textView,textViewSelectedItem;

    private VusikView musicView;

    private MediaPlayer mediaPlayer;
    private int mediaFileLength;
    private  int realtimeLength;
    final Handler handler = new Handler();

    private IceClient iceClient = new IceClient();

    private com.mp3playermiddleware.HomeFragment defaultHomeFragment;
    private WifiManager wm;
    private String ip,currenteMusicPlayin;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wm = (WifiManager) getSystemService(WIFI_SERVICE);
        ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        ip = ip.replace(".","");

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);

        defaultHomeFragment = new com.mp3playermiddleware.HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                defaultHomeFragment).commit();

        textViewSelectedItem = (TextView)findViewById(R.id.textViewSelectedItem);


        musicView = (VusikView)findViewById(R.id.musicView);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setMax(99);// 100% (0-99)

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (mediaPlayer.isPlaying()){
                    SeekBar seekBar = (SeekBar)view;
                    int playPosition = (mediaFileLength/100) * seekBar.getProgress();
                    mediaPlayer.seekTo(playPosition);
                }
                return false;
            }
        });

        textView = (TextView) findViewById(R.id.textTimer);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        btn_play_pause = (ImageButton) findViewById(R.id.btn_player_pause);
        btn_play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final  ProgressDialog mDialog = new ProgressDialog(MainActivity.this);



                @SuppressLint("StaticFieldLeak") AsyncTask<String,String,String> mp3Player = new AsyncTask<String, String, String>() {

                    @Override
                    protected void onPreExecute() {
                        mDialog.setMessage("SVP Attendre");
                        mDialog.show();
                    }

                    @Override
                    protected String doInBackground(String... strings) {
                        try {

                            mediaPlayer.setDataSource(strings[0]);
                            currenteMusicPlayin = textViewSelectedItem.getText().toString();
                            iceClient.streamer(textViewSelectedItem.getText().toString() , ip);
                            mediaPlayer.prepare();
                        }
                        catch (Exception ex)
                        {

                        }
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {

                        mediaFileLength = mediaPlayer.getDuration();
                        realtimeLength = mediaFileLength;

                        if( currenteMusicPlayin.equals(textViewSelectedItem.getText().toString())){

                            if (!mediaPlayer.isPlaying()){
                                mediaPlayer.start();
                                btn_play_pause.setImageResource(R.drawable.ic_pause);
                            }else {
                                mediaPlayer.pause();
                                btn_play_pause.setImageResource(R.drawable.ic_play);
                            }
                        }
                        else {
                            btn_play_pause.setImageResource(R.drawable.ic_play);
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                            iceClient.stopMusique(ip);

                        }

                        updateSeekBar();
                        mDialog.dismiss();
                    }
                };

                mp3Player.execute("http://"+Config.ip_server+":8090/"+ip+".mp3"); // Direct link com.mp3 file

                musicView.start();
            }
        });


    }

    private void updateSeekBar() {
        seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition() / mediaFileLength)*100));
        if (mediaPlayer.isPlaying()){
            Runnable updater = new Runnable() {
                @SuppressLint("DefaultLocale")
                @Override
                public void run() {
                    updateSeekBar();
                    realtimeLength -= 1000; // declare 1  second
                    textView.setText(String.format("%d:%d", java.util.concurrent.TimeUnit.MICROSECONDS.toMinutes(realtimeLength),
                            java.util.concurrent.TimeUnit.MICROSECONDS.toSeconds(realtimeLength) -
                            java.util.concurrent.TimeUnit.MICROSECONDS.toSeconds(java.util.concurrent.TimeUnit.MICROSECONDS.toMinutes(realtimeLength))));
                }
            };
            handler.postDelayed(updater, 1000); // 1 second
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            selectedFragment = defaultHomeFragment;

                            break;
                        case R.id.navigation_favorities:
                            selectedFragment = new com.mp3playermiddleware.FavoritesFragment();
                            break;
                        case R.id.navigation_speechtotext:
                            selectedFragment = new com.mp3playermiddleware.SpeechToTextFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
     };

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        seekBar.setSecondaryProgress(i);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        btn_play_pause.setImageResource(R.drawable.ic_play);
        musicView.stopNotesFall();
    }
}
