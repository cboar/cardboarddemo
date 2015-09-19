package org.cboar.ytvr;

import java.nio.FloatBuffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Bitmap;
import android.opengl.GLUtils;
import android.opengl.GLES20;

public class GLGen {

	public static float[] rect(float w, float h){
		return new float[]{
			w, h, 0, -w, h, 0,
			-w, -h, 0, -w, -h, 0,
			w, -h, 0, w, h, 0
		};
	}

	public static float[] color(float r, float g, float b, int amt){
		float[] result = new float[amt * 4];
		for(int i = 0; i < amt; i++){
			result[i * 4] = r;
			result[i * 4 + 1] = g;
			result[i * 4 + 2] = b;
			result[i * 4 + 3] = 1.0f;
		}
		return result;
	}

	public static int loadTexture(Bitmap bmp){
		final int[] handle = new int[1];
		GLES20.glGenTextures(1, handle, 0);

		if(handle[0] == 0)
			throw new RuntimeException("Error loading texture.");

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, handle[0]);
		GLES20.glTexParameteri(
				GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(
				GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bmp, 0);
		bmp.recycle();

		return handle[0];
	}

	public static Bitmap bmpFromRes(Context ctx, int resID){
		Options options = new Options();
		options.inScaled = false;
		return BitmapFactory.decodeResource(ctx.getResources(), resID, options);
	}

	public static int loadShader(Context ctx, int resID, int type) {
		InputStream is = ctx.getResources().openRawResource(resID);
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

	public static FloatBuffer bufferFloat(float[] buff){
		ByteBuffer bb = ByteBuffer.allocateDirect(buff.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(buff);
		fb.position(0);
		return fb;
	}
}
