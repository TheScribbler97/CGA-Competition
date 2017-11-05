#version 330 core

layout (location = 0) in vec3 pvPos;
layout (location = 1) in vec2 pvTexCoordFront;
layout (location = 2) in vec2 pvTexCoordRight;
layout (location = 3) in vec2 pvTexCoordBack;
layout (location = 4) in vec2 pvTexCoordLeft;
layout (location = 5) in vec2 pvTexCoordTop;
layout (location = 6) in vec2 pvTexCoordBottom;
//layout (location = 7) in vec3 pvNormal;

out vec2 vgTexCoordFront;
out vec2 vgTexCoordRight;
out vec2 vgTexCoordBack;
out vec2 vgTexCoordLeft;
out vec2 vgTexCoordTop;
out vec2 vgTexCoordBottom;

void main()
{
    gl_Position = vec4(pvPos, 1.0);
    vgTexCoordFront = pvTexCoordFront;
    vgTexCoordRight = pvTexCoordRight;
    vgTexCoordBack = pvTexCoordBack;
    vgTexCoordLeft = pvTexCoordLeft;
    vgTexCoordTop = pvTexCoordTop;
    vgTexCoordBottom = pvTexCoordBottom;
}