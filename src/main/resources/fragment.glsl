#version 330 core

in vec2 gfTexCoord;

uniform sampler2D albedoSampler;

out vec4 fsColor;

void main()
{
    fsColor = texture2D(albedoSampler, gfTexCoord);
}