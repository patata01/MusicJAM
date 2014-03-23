/*
 * Aplicació d'exemple que mostra com gravar i reproduir àudio.
 * 
 * Aquesta aplicació necessita permís per escriure en emmagatzematge extern
 * i també permís per gravar audio:
 *
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 */

package net.infobosccoma.gravacioaudio;

import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;


public class GravacioAudio extends Activity {
	private static final String LOG_TAG = "GravacioAudio";
	private static String mFileName = null;

	private RecordButton mRecordButton = null;
	private MediaRecorder mRecorder = null;

	private PlayButton mPlayButton = null;
	private MediaPlayer mPlayer = null;


	/**
	 * Constructor
	 */
	public GravacioAudio() {
		mFileName = "gravacioAudio.3gp";
		File path = new File(Environment.getExternalStorageDirectory(), LOG_TAG);
		if (!path.exists())
			path.mkdirs();

		mFileName = new File(path, mFileName).getAbsolutePath();
	}


	@Override
	public void onPause() {
		super.onPause();
		if (mRecorder != null) {
			mRecorder.release();
			mRecorder = null;
		}

		if (mPlayer != null) {
			mPlayer.release();
			mPlayer = null;
		}
	}
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		LinearLayout ll = new LinearLayout(this);
		mRecordButton = new RecordButton(this);
		ll.addView(mRecordButton, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		mPlayButton = new PlayButton(this);
		ll.addView(mPlayButton, new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 0));
		setContentView(ll);
	}
	
	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	/**
	 * Iniciar la reproducció
	 */
	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			mPlayer.setDataSource(mFileName);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}
	}

	/**
	 * Aturar la reproducció
	 */
	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	/**
	 * Iniciar la gravació
	 */
	private void startRecording() {
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	/**
	 * Aturar la gravació
	 */
	private void stopRecording() {
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;
	}

	/**
	 * Classe que estén la classe Button per implementar un botó de gravació
	 * 
	 * @author Marc Nicolau
	 *
	 */
	class RecordButton extends Button {
		private boolean mStartRecording = true;

		private OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onRecord(mStartRecording);
				if (mStartRecording) {
					setText("Stop recording");
				} else {
					setText("Start recording");
				}
				mStartRecording = !mStartRecording;
			}
		};

		public RecordButton(Context ctx) {
			super(ctx);
			setText("Start recording");
			setOnClickListener(clicker);
		}
	}

	/**
	 * Classe que estén la classe Button per implementar un botó de reproducció
	 * 
	 * @author Marc Nicolau
	 *
	 */
	class PlayButton extends Button {
		private boolean mStartPlaying = true;

		private OnClickListener clicker = new OnClickListener() {
			public void onClick(View v) {
				onPlay(mStartPlaying);
				if (mStartPlaying) {
					setText("Stop playing");
				} else {
					setText("Start playing");
				}
				mStartPlaying = !mStartPlaying;
			}
		};

		public PlayButton(Context ctx) {
			super(ctx);
			setText("Start playing");
			setOnClickListener(clicker);
		}
	}
}