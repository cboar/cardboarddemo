package org.cboar.ytvr;

import java.nio.FloatBuffer;
import android.graphics.Bitmap;
import android.opengl.GLES20;

public class GLTexture {

	public static GLTexture WHITE;

	private FloatBuffer texture;
	private int pTexture, pTexCoord, pTexData;

	public GLTexture(Bitmap bmp, int program){
		this.pTexData = GLGen.loadTexture(bmp);
        this.pTexture = GLES20.glGetUniformLocation(program, "u_Texture");
        this.pTexCoord = GLES20.glGetAttribLocation(program, "a_TexCoord");
        this.texture = GLGen.bufferFloat(new float[]{ 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1 });

        GLES20.glEnableVertexAttribArray(pTexCoord);
	}

	public void setup(){
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, pTexData);
		GLES20.glUniform1i(pTexture, 0);
		GLES20.glVertexAttribPointer(pTexCoord, 2, GLES20.GL_FLOAT, false, 0, texture);
	}
}
