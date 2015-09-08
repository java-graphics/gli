#version 400

in vec3 position;
in vec2 inUV;

out vec2 uv;

void main()
{
    gl_Position = vec4(position, 1);
   
    uv = inUV;
}