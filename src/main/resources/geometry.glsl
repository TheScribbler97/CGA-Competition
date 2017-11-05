#version 400

layout(points, invocations = 3) in;
layout(triangle_strip, max_vertices = 4) out;

in vec2 vgTexCoordFront[];
in vec2 vgTexCoordRight[];
in vec2 vgTexCoordBack[];
in vec2 vgTexCoordLeft[];
in vec2 vgTexCoordTop[];
in vec2 vgTexCoordBottom[];

uniform float blockSize;
uniform vec3 camPos;

uniform mat4 view;
uniform mat4 projection;

uniform float textureBlockSize;

out vec2 gfTexCoord;

void main()
{
    vec3 camDir = gl_in[0].gl_Position.xyz-camPos;
    if(gl_InvocationID == 0)
    {
        if(dot(camDir,vec3(0,1,0))<0)
        {
            //Top
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordTop[0]+vec2(textureBlockSize,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordTop[0]+vec2(textureBlockSize,textureBlockSize);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordTop[0]+vec2(0,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordTop[0]+vec2(0,textureBlockSize);
            EmitVertex();
            EndPrimitive();
        }
        else
        {
            //Bottom
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBottom[0]+vec2(textureBlockSize,textureBlockSize);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBottom[0]+vec2(0,textureBlockSize);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBottom[0]+vec2(textureBlockSize,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBottom[0]+vec2(0,0);
            EmitVertex();
            EndPrimitive();
        }
    }
    else if(gl_InvocationID == 1)
    {
        if(dot(camDir,vec3(1,0,0))<0)
        {
            //Right
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordRight[0]+vec2(textureBlockSize,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordRight[0]+vec2(textureBlockSize,textureBlockSize);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordRight[0]+vec2(0,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordRight[0]+vec2(0,textureBlockSize);
            EmitVertex();
            EndPrimitive();
        }
        else
        {
            //Left
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordLeft[0]+vec2(0,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordLeft[0]+vec2(textureBlockSize,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordLeft[0]+vec2(0,textureBlockSize);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2,  blockSize/2, 0.0));
            gfTexCoord = vgTexCoordLeft[0]+vec2(textureBlockSize,textureBlockSize);
            EmitVertex();
            EndPrimitive();
        }
    }
    else if(gl_InvocationID == 2)
    {
        if(dot(camDir,vec3(0,0,1))<0)
        {
            //Front
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, blockSize/2, 0.0));
            gfTexCoord = vgTexCoordFront[0]+vec2(0,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, blockSize/2, 0.0));
            gfTexCoord = vgTexCoordFront[0]+vec2(textureBlockSize,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2, blockSize/2, 0.0));
            gfTexCoord = vgTexCoordFront[0]+vec2(0,textureBlockSize);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2, blockSize/2, 0.0));
            gfTexCoord = vgTexCoordFront[0]+vec2(textureBlockSize,textureBlockSize);
            EmitVertex();
            EndPrimitive();
        }
        else
        {
            //Back
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBack[0]+vec2(textureBlockSize,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBack[0]+vec2(textureBlockSize,textureBlockSize);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBack[0]+vec2(0,0);
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            gfTexCoord = vgTexCoordBack[0]+vec2(0,textureBlockSize);
            EmitVertex();
            EndPrimitive();
        }
    }
}