package hu.bme.myapplication;

import hu.bme.myapplication.streaming.Session;
import hu.bme.myapplication.streaming.SessionBuilder;
import hu.bme.myapplication.streaming.gl.SurfaceView;
import hu.bme.myapplication.streaming.rtsp.RtspServer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.net.ServerSocket;
import java.net.Socket;

import hu.bme.myapplication.streaming.SessionBuilder;
import hu.bme.myapplication.streaming.gl.SurfaceView;
import hu.bme.myapplication.streaming.rtsp.RtspServer;

/**
 * A straightforward example of how to use the RTSP server included in libstreaming.
 */
public class CameraActivity extends Activity implements View.OnClickListener, Session.Callback, SurfaceHolder.Callback {
	private static boolean ServerInitiatedStoppage = false;
	private static boolean IsStreaming = false;
	private final static String TAG = "CameraActivity";
	private Socket socket = new Socket();
	private SurfaceView mSurfaceView;
	private Session mSession;
	private String destination_ip = "192.168.0.255"; //The server IP where the app gets the "matchcode"
	private int specified_channel_port = 8820; //The port where the app is listening

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ServerInitiatedStoppage = false;
		IsStreaming = false;
		requestPermission();
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_main);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		mSurfaceView = (SurfaceView) findViewById(R.id.surface);
		WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
		String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
		// Sets the port of the RTSP server to 1234
		Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
		String Port_hardCoded = "1234";
		editor.putString(RtspServer.KEY_PORT, Port_hardCoded);
		editor.commit();
		String textToast = "Serving at: rtsp://" + ip + ":" + Port_hardCoded;

		try{
			socket = new Socket(destination_ip,specified_channel_port);
		}
		catch (Exception e){

		}

		Toast.makeText(CameraActivity.this, textToast,
				Toast.LENGTH_LONG).show();
		// Configures the SessionBuilder
		 mSession = SessionBuilder.getInstance()
				 .setCallback(this)
		.setSurfaceView(mSurfaceView)
		.setPreviewOrientation(90)
		.setContext(getApplicationContext())
		.setAudioEncoder(SessionBuilder.AUDIO_NONE)
		.setVideoEncoder(SessionBuilder.VIDEO_H264).build();


		 mSurfaceView.getHolder().addCallback(this);
		// Starts the RTSP server
		Intent myIntent = new Intent(CameraActivity.this, RtspServer.class);
		IsStreaming = true;
		this.startService(myIntent);

	}







	private void requestPermission() {

		ActivityCompat.requestPermissions(this,
				new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
				PERMISSION_REQUEST_CODE);
	}


	public void stopStreaming(){
		mSession.stop();
		mSession.release();
	}

	@Override
	public void onBackPressed() {

		if(ServerInitiatedStoppage || !IsStreaming){
			super.onBackPressed();
		}

		//stopStreaming();
	}



	private static final int PERMISSION_REQUEST_CODE = 200;


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}


	@Override
	public void onDestroy() {
		super.onDestroy();
		mSession.release();
		mSession.stop();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onBitrateUpdate(long bitrate) {

	}

	@Override
	public void onSessionError(int reason, int streamType, Exception e) {

	}

	@Override
	public void onPreviewStarted() {

	}

	@Override
	public void onSessionConfigured() {

	}

	@Override
	public void onSessionStarted() {
		String clientIp = "";
		String textToast = "Client has connected:";

		Toast.makeText(CameraActivity.this, textToast,
				Toast.LENGTH_LONG).show();

	}

	@Override
	public void onSessionStopped() {
		ServerInitiatedStoppage = true;
		onBackPressed();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mSession.startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mSession.stop();
		mSession.release();
	}


}
