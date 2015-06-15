package net.majorkernelpanic.example1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager;

/**
 * A straightforward example of how to use the RTSP server included in libstreaming.
 */
public class MainActivity extends Activity implements Session.Callback {

  private final static String TAG = "MainActivity";

  private SurfaceView mSurfaceView;

  private Session mSession;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    setContentView(R.layout.activity_main);
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    mSurfaceView = (SurfaceView) findViewById(R.id.surface);
    
    // Sets the port of the RTSP server to 8086
    Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    editor.putString(RtspServer.KEY_PORT, String.valueOf(8086));
    editor.commit();

    // Configures the SessionBuilder
    mSession = SessionBuilder.getInstance()
    .setSurfaceView(mSurfaceView)
    .setPreviewOrientation(90)
    .setContext(getApplicationContext())
    .setCallback(this)
    .setAudioEncoder(SessionBuilder.AUDIO_NONE)
    .setVideoEncoder(SessionBuilder.VIDEO_H264).build();
    mSession.configure();
    
    // Starts the RTSP server
    this.startService(new Intent(this,RtspServer.class));

  }
  
  @Override
  public void onSessionConfigured() {
    Log.d(TAG,"Preview configured.");
    mSession.start();
  }

  @Override
  public void onBitrateUpdate(long bitrate) {
    Log.d(TAG,"Bitrate: "+bitrate);
  }

  @Override
  public void onSessionError(int reason, int streamType, Exception e) {
    Log.d(TAG,"onSessionError");
    
  }

  @Override
  public void onPreviewStarted() {
    Log.d(TAG,"onPreviewStarted");
    
  }

  @Override
  public void onSessionStarted() {
    Log.d(TAG,"onSessionStarted");
    
  }

  @Override
  public void onSessionStopped() {
    Log.d(TAG,"onSessionStopped");
  }

}
