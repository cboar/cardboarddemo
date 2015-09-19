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

	private GLObject floor, screen;
	private GLContext ctx;

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		CardboardView cardboardView = (CardboardView) findViewById(R.id.cardboard_view);
		cardboardView.setRestoreGLStateEnabled(false);
		cardboardView.setRenderer(this);
		setCardboardView(cardboardView);
	}
	@Override
	public void onSurfaceCreated(EGLConfig config){
		GLES20.glClearColor(0.1f, 0.1f, 0.1f, 0.5f);

		ctx = new GLContext(this);

		floor = new GLObject(ctx.program,
				GLGen.rect(100, 100), GLGen.color(0.7f, 0.7f, 0.7f, 6));
		floor.translate(0, -50f, 0).rotate(90, 0, 0);
		screen = new GLObject(ctx.program,
				GLGen.rect(19.2f, 10.8f), GLGen.color(0.7f, 0.7f, 0.7f, 6));
		screen.translate(0, 0, 30f)
			.rotate(0, 0, 180)
			.texture(GLGen.loadTexture(this, R.drawable.wrangler));
	}
	@Override
	public void onNewFrame(HeadTransform head){
		ctx.updateCamera(head);
	}
	@Override
	public void onDrawEye(Eye eye){
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		ctx.setupEyeDraw(eye);
		floor.draw(ctx);
		screen.draw(ctx);
	}
	@Override
	public void onCardboardTrigger(){
	}

	@Override public void onRendererShutdown(){}
	@Override public void onSurfaceChanged(int width, int height){}
	@Override public void onFinishFrame(Viewport viewport) {}
}
