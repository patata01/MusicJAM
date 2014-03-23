package net.infobosccoma.gravaciodevideo;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Classe que hereta de SurfaceView per mostrar el contingut de la c�mera
 * 
 * @author Marc Nicolau
 *
 */
public class PrevisualitzacioCamera extends SurfaceView implements
		SurfaceHolder.Callback {

	// objecte que mostra la previsualitzaci�
	private SurfaceHolder holder;
	// refer�ncia a la c�mera
	private Camera camera;
	// refer�ncia al context de l'aplicaci�
	private Context context;

	/**
	 * Constructor
	 * 
	 * @param context
	 * @param camera
	 */
	public PrevisualitzacioCamera(Context context, Camera camera) {
		super(context);
		this.context = context;
		this.camera = camera;
		// configurar el SurfaceHolder
		holder = getHolder();
		holder.addCallback(this);
		// est� deprecated, per� cal en versions anteriors a Android 3.0
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		if (holder.getSurface() == null) {
			// la zona de previsualitzaci� no existeix
			Dialeg.mostrarDialeg(context, "Previsualitzaci�",
					"No hi ha cap zona de previsualitzaci� disponile");
			return;
		}

		// aturar la previsualitzaci� abans de fer canvis
		try {
			camera.stopPreview();
		} catch (Exception e) {

		}

		// establir la mida de previsualitzaci� i redimensionar o rotar
		// iniciar la previsualitzaci� amb els nous canvis
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (Exception e) {
			Dialeg.mostrarDialeg(
					context,
					"Previsualitzaci�",
					"Error iniciant la previsualitzaci� de la c�mera: "
							+ e.getMessage());
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// connectar la c�mera amb la vista
		try {
			camera.setPreviewDisplay(holder);
			camera.startPreview();
		} catch (IOException e) {
			Dialeg.mostrarDialeg(context, "Previsualitzaci�",
					"Error mentre es configurava la previsualitzaci�");
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		camera.release();
	}

}
