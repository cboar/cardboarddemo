package org.cboar.ytvr;

import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;

import android.content.res.Resources;
import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class GLContext {

	public final float[]
		camera, view, modelView, modelViewProj, headView;
	public float[] perspective;
	public final int program;

	public GLContext(Context ctx){
		this.camera = new float[16];
		this.view = new float[16];
		this.modelView = new float[16];
		this.modelViewProj = new float[16];
		this.headView = new float[16];

		int vertex = GLGen.loadShader(ctx, R.raw.vertex, GLES20.GL_VERTEX_SHADER);
		int fragment = GLGen.loadShader(ctx, R.raw.fragment, GLES20.GL_FRAGMENT_SHADER);

		program = GLES20.glCreateProgram();
		GLES20.glAttachShader(program, vertex);
		GLES20.glAttachShader(program, fragment);
		GLES20.glLinkProgram(program);

		GLTexture.WHITE = new GLTexture(GLGen.loadTexture(ctx, R.drawable.white), program);
	}

	public void updateCamera(HeadTransform head){
		Matrix.setLookAtM(camera, 0, 0.0f, 0.0f, 0.01f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		head.getHeadView(headView, 0);
	}

	public void setupEyeDraw(Eye eye){
		GLES20.glUseProgram(program);
		Matrix.multiplyMM(view, 0, eye.getEyeView(), 0, camera, 0);
		perspective = eye.getPerspective(0.01f, 300.0f);
	}
}
