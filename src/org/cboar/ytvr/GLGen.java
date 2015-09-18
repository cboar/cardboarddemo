package org.cboar.ytvr;

public class GLGen {

	public static float[] rect(float w, float h){
		return new float[]{
			w, 0, -h, -w, 0, -h,
			-w, 0, h, w, 0, -h,
			-w, 0, h, w, 0, h
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
}
