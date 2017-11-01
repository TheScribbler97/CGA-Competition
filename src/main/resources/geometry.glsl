#version 400

layout(points, invocations = 3) in;
layout(triangle_strip, max_vertices = 4) out;

in vec2 vgTexCoord[]; // Output from vertex shader for each vertex

uniform float blockSize;
uniform vec3 camPos;

uniform mat4 view;
uniform mat4 projection;

out vec2 gfTexCoord; // Output to fragment shader

void main()
{
    //gfTexCoord = (camPos*-1).xz/10;
    gfTexCoord = (gl_in[0].gl_Position-vec4(camPos,0)).xz/10;
    if(gl_InvocationID == 0)
    {
        gfTexCoord = (gl_in[0].gl_Position-vec4(camPos,0)).xz/10;
        if(dot(gl_in[0].gl_Position.xyz-camPos,vec3(0,1,0))<0)
        {
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            EndPrimitive();
        }
        else
        {
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            EndPrimitive();
        }
    }
    else if(gl_InvocationID == 1)
    {
        if(dot(gl_in[0].gl_Position.xyz-camPos,vec3(1,0,0))<0)
        {
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            EndPrimitive();
        }
        else
        {
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2,  blockSize/2, 0.0));
            EmitVertex();
            EndPrimitive();
        }
    }
    else if(gl_InvocationID == 2)
    {
        if(dot(gl_in[0].gl_Position.xyz-camPos,vec3(0,0,1))<0)
        {
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2, blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2, blockSize/2, 0.0));
            EmitVertex();
            EndPrimitive();
        }
        else
        {
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2, -blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4(-blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            gl_Position = projection*view*(gl_in[0].gl_Position + vec4( blockSize/2,  blockSize/2, -blockSize/2, 0.0));
            EmitVertex();
            EndPrimitive();
        }
    }
}