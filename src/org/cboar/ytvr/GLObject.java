package org.cboar.ytvr;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import android.opengl.GLES20;
import android.opengl.Matrix;

public class GLObject {

	private final float[]
		model, view, viewProj;
	private int
		pPos, pColor, pModel, pView, pViewProj;
	private final FloatBuffer
		vertices, colors;

	public GLObject(int program, float[] vert, float[] col){
		this.model = new float[16];
		this.view = new float[16];
		this.viewProj = new float[16];

		this.vertices = bufferFloat(vert);
		this.colors = bufferFloat(col);

        this.pPos = GLES20.glGetAttribLocation(program, "a_Position");
        this.pColor = GLES20.glGetAttribLocation(program, "a_Color");
		this.pModel = GLES20.glGetUniformLocation(program, "u_Model");
        this.pView = GLES20.glGetUniformLocation(program, "u_MVMatrix");
        this.pViewProj = GLES20.glGetUniformLocation(program, "u_MVP");

		GLES20.glEnableVertexAttribArray(pPos);
		GLES20.glEnableVertexAttribArray(pColor);
	}

	public void draw(GLContext ctx){
		Matrix.multiplyMM(ctx.modelView, 0, ctx.view, 0, model, 0);
		Matrix.multiplyMM(ctx.modelViewProj, 0, ctx.perspective, 0, ctx.modelView, 0);

		GLES20.glUniformMatrix4fv(pModel, 1, false, model, 0);
		GLES20.glUniformMatrix4fv(pView, 1, false, ctx.modelView, 0);
		GLES20.glUniformMatrix4fv(pViewProj, 1, false, ctx.modelViewProj, 0);
		GLES20.glVertexAttribPointer(pPos, 3, GLES20.GL_FLOAT, false, 0, vertices);
		GLES20.glVertexAttribPointer(pColor, 4, GLES20.GL_FLOAT, false, 0, colors);

		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);
	}

	public void translate(float x, float y, float z){
		Matrix.setIdentityM(model, 0);
		Matrix.translateM(model, 0, x, y, z);
	}

	private static FloatBuffer bufferFloat(float[] buff){
		ByteBuffer bb = ByteBuffer.allocateDirect(buff.length * 4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(buff);
		fb.position(0);
		return fb;
	}
}
