uniform mat4 u_Model;
uniform mat4 u_MVP;
uniform mat4 u_MVMatrix;

attribute vec2 a_TexCoord;
attribute vec4 a_Position;
attribute vec4 a_Color;

varying vec2 v_TexCoord;
varying vec4 v_Color;

void main() {
	v_Color = a_Color;
	v_TexCoord = a_TexCoord;
	gl_Position = u_MVP * a_Position;
}
