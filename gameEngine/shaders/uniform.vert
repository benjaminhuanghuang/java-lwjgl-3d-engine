#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec2 textureCoord;

out vec3 passColor;
out vec2 passTextureCoord;

uniform float scale;

void main() {
	gl_Position = vec4(position, 1.0) * vec4(scale, scale,scale, 1) ;
	passColor = color;
	passTextureCoord = textureCoord;
}