#version 330 core

layout (location = 0) in vec3 pvPos;
layout (location = 1) in vec2 pvTexCoord;
//layout (location = 2) in vec3 pvNormal;

uniform mat4 model;

out vec2 vgTexCoord;

void main()
{
    gl_Position = model*vec4(pvPos, 1.0);
    vgTexCoord = pvTexCoord;
}