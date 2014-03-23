package net.infobosccoma.gravaciodevideo;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Classe que mostra un diàleg
 * 
 * @author Marc Nicolau
 *
 */
public class Dialeg {

	public static void mostrarDialeg(Context context, String titol,
			String missatge) {
		AlertDialog.Builder dialeg = new AlertDialog.Builder(context);
		dialeg.create();
		dialeg.setTitle(titol);
		dialeg.setMessage(missatge);
		dialeg.show();
	}
}
