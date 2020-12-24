uniform mat4 u_matrix;
uniform vec2 u_position;

uniform vec2 u_lightPosition;

attribute vec3 a_vertex;
attribute vec2 a_textureCord;

varying vec2 v_texture_cords;
varying float v_lightDistance;

void main()
{
    v_texture_cords.s = a_textureCord.s;
    v_texture_cords.t = a_textureCord.t;

    v_lightDistance = distance(u_position, u_lightPosition);

    gl_Position = u_matrix * vec4(a_vertex.x + u_position.x, a_vertex.y + u_position.y, a_vertex.z, 1.0);
}