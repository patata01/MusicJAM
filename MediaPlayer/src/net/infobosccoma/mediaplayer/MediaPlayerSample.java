package net.infobosccoma.mediaplayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Exemple que mostra com reproduir un àudio des d'un fitxer emmagatzemat a Internet
 * Utilitza la classe MediaPlayer i una classe AsyncTask per gestionar la descàrrega.
 * 
 * @author Marc Nicolau
 *
 */
public class MediaPlayerSample extends Activity {

	static final String AUDIO_PATH = "http://www.susanpiver.com/audio/Dancing in the Dark.mp3";

	// l'objecte amb el qual es fa la reproducció del fitxer
	private MediaPlayer mediaPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_player);
	}

	/**
	 * Implementació de l'esdeveniment clic del botó
	 * @param v
	 */
	public void btnClick(View v) {
		Button b = (Button) v;
		if (b.getText().equals(getText(R.string.reproduir))) {
			new TascaCarrega().execute(AUDIO_PATH);
			b.setText(getText(R.string.aturar));
		} else {
			b.setText(getText(R.string.reproduir));
			mediaPlayer.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mediaPlayer != null) {
			mediaPlayer.release();
		}
	}

	/**
	 * Implementació de la tasca asíncrona
	 * 
	 * @author Marc Nicolau
	 *
	 */
	class TascaCarrega extends AsyncTask<String, Void, Void> {
		ProgressDialog pd = new ProgressDialog(MediaPlayerSample.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd.setMessage("Espera mentre es carrega l'àudio...");
			pd.show();
		}

		@Override
		protected Void doInBackground(String... params) {
			mediaPlayer = MediaPlayer.create(MediaPlayerSample.this,
					Uri.parse(params[0]));
			mediaPlayer.start();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			pd.dismiss();
			super.onPostExecute(result);
		}
	}
}
