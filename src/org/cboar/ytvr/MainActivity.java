package org.cboar.ytvr;

import com.google.vrtoolkit.cardboard.CardboardActivity;
import com.google.vrtoolkit.cardboard.CardboardView;
import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;
import com.google.vrtoolkit.cardboard.Viewport;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;

public class MainActivity extends CardboardActivity implements CardboardView.StereoRenderer {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        CardboardView cb_view = (CardboardView) findViewById(R.id.cardboard_view);
		cb_view.setRestoreGLStateEnabled(false);
        cb_view.setRenderer(this);
        setCardboardView(cb_view);
    }

    @Override
    public void onSurfaceCreated(EGLConfig config){
		GLES20.glClearColor(0.1f, 0.1f, 0.1f, 0.5f);
	}
	@Override
	public void onNewFrame(HeadTransform headTransform){

	}
	@Override
	public void onDrawEye(Eye eye){

	}
	@Override
    public void onFinishFrame(Viewport viewport){

    }
    @Override
    public void onCardboardTrigger(){

	}

	@Override public void onRendererShutdown(){}
    @Override public void onSurfaceChanged(int width, int height){}
}
