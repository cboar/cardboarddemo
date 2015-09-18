package org.cboar.ytvr;

import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Viewport;

import android.content.Context;
import android.opengl.GLES20;
import android.os.Bundle;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends CardboardActivity implements CardboardView.StereoRenderer {

	private GLObject floor;
	private GLContext ctx;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		CardboardView cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
		cardboardView.setRestoreGLStateEnabled(false);
		cardboardView.setRenderer(this);
		setCardboardView(cardboardView);
	}
	@Override
	public void onSurfaceCreated(EGLConfig config) {
		GLES20.glClearColor(0.1f, 0.1f, 0.1f, 0.5f);

		ctx = new GLContext(this);
		floor = new GLObject(ctx.program, new float[]{
				200f, 0, -200f, -200f, 0, -200f,
				-200f, 0, 200f, 200f, 0, -200f,
				-200f, 0, 200f, 200f, 0, 200f
			}, new float[]{
				0.0f, 0.4f, 0.9f, 1.0f,
				0.0f, 0.4f, 0.9f, 1.0f,
				0.0f, 0.4f, 0.9f, 1.0f,
				0.0f, 0.4f, 0.9f, 1.0f,
				0.0f, 0.4f, 0.9f, 1.0f,
				0.0f, 0.4f, 0.9f, 1.0f
			});
		floor.translate(0, -20.0f, 0);
	}
	@Override
	public void onNewFrame(HeadTransform head) {
		ctx.updateCamera(head);
	}
	@Override
	public void onDrawEye(Eye eye) {
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		ctx.setupEyeDraw(eye);
		floor.draw(ctx);
	}
	@Override
	public void onCardboardTrigger() {
	}

	@Override public void onRendererShutdown(){}
	@Override public void onSurfaceChanged(int width, int height){}
	@Override public void onFinishFrame(Viewport viewport) {}
}
