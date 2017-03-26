package hssychargingpole.xpg.com.baidumapdemo.zxing;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.Vector;

import hssychargingpole.xpg.com.baidumapdemo.R;
import hssychargingpole.xpg.com.baidumapdemo.zxing.camera.CameraManager;
import hssychargingpole.xpg.com.baidumapdemo.zxing.decoding.InactivityTimer;
import hssychargingpole.xpg.com.baidumapdemo.zxing.decoding.ZxingHandler;
import hssychargingpole.xpg.com.baidumapdemo.zxing.view.ViewfinderView;


/**
 * 二维码的管理类
 */
public class ZxingManager implements Callback {

	private ZxingHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;

	private Activity activity;
	private SurfaceView surfaceView;

	private OnResultListener onResultListener;

	public ZxingManager(Activity activity, ViewfinderView viewfinderView, SurfaceView surfaceView) {
		this.activity = activity;
		this.viewfinderView = viewfinderView;
		this.surfaceView = surfaceView;
	}

	/**
	 * Called when the activity is first created.
	 */
	public void onCreate() {
		Log.i("jason", " ZxingManager  onCreate");
		CameraManager.init(activity);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(activity);
	}

	public void onResume() {
		Log.i("jason", " ZxingManager  onStart");
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		// decodeFormats = null;
		// characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) activity.getSystemService(Context.AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	public void onPause() {
		Log.i("jason", " ZxingManager  onPause");
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();

	}

	public void onDestroy() {
		inactivityTimer.shutdown();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new ZxingHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			Log.i("jason", " ZxingManager  surfaceCreated");
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		Log.i("ZxingManager", obj.getText());
		if (onResultListener != null) {
			onResultListener.OnResult(obj, barcode);
		}
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = activity.getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		@Override
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	public interface OnResultListener {
		void OnResult(Result result, Bitmap barcode);
	}

	public OnResultListener getOnResultListener() {
		return onResultListener;
	}

	public void setOnResultListener(OnResultListener onResultListener) {
		this.onResultListener = onResultListener;
	}

	public Vector<BarcodeFormat> getDecodeFormats() {
		return decodeFormats;
	}

	public void setDecodeFormats(Vector<BarcodeFormat> decodeFormats) {
		this.decodeFormats = decodeFormats;
	}
}