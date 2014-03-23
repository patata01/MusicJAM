package net.infobosccoma.videoplayer;

import java.io.File;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends Activity {

	private VideoView videoView;
	private MediaController mediaController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_player);

		videoView = (VideoView) findViewById(R.id.videoView1);
		mediaController = new MediaController(this);
		videoView.setMediaController(mediaController);

		videoView.setVideoURI(Uri
				.parse("http://192.168.0.247/android/video.mp4"));
		videoView.start();
		mediaController.show();
		videoView.requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_player, menu);
		return true;
	}

}
