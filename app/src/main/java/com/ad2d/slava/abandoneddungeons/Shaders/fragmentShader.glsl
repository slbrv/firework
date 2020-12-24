precision mediump float;

uniform sampler2D u_texture;
uniform float u_lightPower;

varying vec2 v_texture_cords;
varying float v_lightDistance;

void main() {
    vec4 texture_color = texture2D(u_texture, v_texture_cords);
    float light = 0.0;
    if(v_lightDistance < u_lightPower)
        light = 1.0/(u_lightPower - v_lightDistance)*0.2;
    light = light > 0.0 ? light : 0.0;

    gl_FragColor = vec4(texture_color.x + light, texture_color.y + light, texture_color.z + light, texture_color.w);
}