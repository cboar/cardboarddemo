package org.cboar.ytvr;

import com.google.vrtoolkit.cardboard.Eye;
import com.google.vrtoolkit.cardboard.HeadTransform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

		int vertex = loadGLShader(ctx, GLES20.GL_VERTEX_SHADER, R.raw.vertex);
		int fragment = loadGLShader(ctx, GLES20.GL_FRAGMENT_SHADER, R.raw.fragment);

		program = GLES20.glCreateProgram();
		GLES20.glAttachShader(program, vertex);
		GLES20.glAttachShader(program, fragment);
		GLES20.glLinkProgram(program);
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

	private static String readInputStream(InputStream inputStream){
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line).append("\n");
			}
			reader.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static int loadGLShader(Context ctx, int type, int resId) {
		InputStream is = ctx.getResources().openRawResource(resId);
		String code = readInputStream(is);
		int shader = GLES20.glCreateShader(type);
		GLES20.glShaderSource(shader, code);
		GLES20.glCompileShader(shader);

		final int[] compileStatus = new int[1];
		GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

		if (compileStatus[0] == 0) {
			GLES20.glDeleteShader(shader);
			shader = 0;
			throw new RuntimeException("Error creating shader.");
		}

		return shader;
	}
}
