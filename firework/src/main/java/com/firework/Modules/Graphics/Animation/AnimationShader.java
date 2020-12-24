package com.firework.Modules.Graphics.Animation;

import com.firework.Modules.Graphics.Camera;
import com.firework.Modules.Graphics.Meshes.Mesh;
import com.firework.Modules.Graphics.Shaders.Shader;
import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Graphics.Textures.Texture;

import java.nio.FloatBuffer;
import java.util.ArrayList;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glDeleteBuffers;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glIsBuffer;
import static android.opengl.GLES20.glUniform2fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Slava on 30.04.2017.
 */

public class AnimationShader extends Shader {

    /*
     * Создание объекта шейдера спрайта из текстуры и сетки
     *
     */

    protected int a_vertexPos;
    protected int u_texturePos;
    protected int a_textureCordsPos;
    protected int u_matrixPos;
    protected int u_position;


    protected Camera camera;
    protected Texture texture;
    protected Mesh mesh;
    protected ArrayList<Frame> frames;

    protected int textureCdsVBO;

    /**
     * Создание объекта шейдера спрайта из текстуры и сетки
     */

    public AnimationShader(Camera camera, Texture texture, ArrayList<Frame> frames) {
        this.camera = camera;
        this.texture = texture;
        this.mesh = new Mesh();

        String vertexShader = "uniform mat4 u_matrix;\n" +
                "uniform vec2 u_position;\n" +
                "\n" +
                "attribute vec3 a_vertex;\n" +
                "attribute vec2 a_textureCord;\n" +
                "\n" +
                "varying vec2 v_texture_cords;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    v_texture_cords.s = a_textureCord.s;\n" +
                "    v_texture_cords.t = a_textureCord.t;\n" +
                "    gl_Position = u_matrix * vec4(a_vertex.x + u_position.x, a_vertex.y + u_position.y, a_vertex.z, 1.0);\n" +
                "}\n";

        String fragmentShader = "" +
                "precision mediump float;\n" +
                "\n" +
                "uniform sampler2D u_texture;\n" +
                "\n" +
                "varying vec2 v_texture_cords;\n" +
                "\n" +
                "void main() {\n" +
                "    vec4 texture_color = texture2D(u_texture, v_texture_cords);\n" +
                "    gl_FragColor = texture_color;\n" +
                "}";
        this.vertexShaderID = ShaderUtils.createShader(vertexShader, GL_VERTEX_SHADER);
        this.fragmentShaderID = ShaderUtils.createShader(fragmentShader, GL_FRAGMENT_SHADER);

        this.programID = ShaderUtils.createProgram(this.vertexShaderID, this.fragmentShaderID);

        u_matrixPos = glGetUniformLocation(programID, "u_matrix");

        a_vertexPos = glGetAttribLocation(programID, "a_vertex");
        a_textureCordsPos = glGetAttribLocation(programID, "a_textureCord");
        u_texturePos = glGetAttribLocation(programID, "u_texture");
        u_position = glGetUniformLocation(programID, "u_position");

        this.frames = frames;

        reload();
    }

    public void reload() {

        FloatBuffer tempTextureCordsBuffer = ShaderUtils.createFloatBuffer(frames.size() * 8);
        for (int i = 0; i < frames.size(); i++) {
            tempTextureCordsBuffer.put(frames.get(i).getFrameCords());
        }
        if (glIsBuffer(textureCdsVBO)) {
            int[] buffers = new int[1];
            buffers[0] = textureCdsVBO;
            glDeleteBuffers(1, buffers, 0);
        }
        tempTextureCordsBuffer.flip();
        this.textureCdsVBO = ShaderUtils.createVBO(tempTextureCordsBuffer, GL_STATIC_DRAW);
    }

    public void start() {
        glUseProgram(programID);

        glUniformMatrix4fv(u_matrixPos, 1, false, camera.getCombinedMatrix(), 0);

        if (texture.hasAlpha()) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }

        texture.link(u_texturePos);

        glVertexAttribPointer(a_vertexPos, 3, GL_FLOAT, false, 0, mesh.getVertices());
        glEnableVertexAttribArray(a_vertexPos);
    }

    @Deprecated
    @Override
    public void draw() {

    }

    public void draw(int target, float[] position) {
        glUniform2fv(u_position, 1, position, 0);

        glBindBuffer(GL_ARRAY_BUFFER, textureCdsVBO);
        glVertexAttribPointer(a_textureCordsPos, 2, GL_FLOAT, false, 0, target * 32);
        glEnableVertexAttribArray(a_textureCordsPos);

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, mesh.getIndices());
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }


    public void stop() {
        glDisableVertexAttribArray(a_vertexPos);
        glDisableVertexAttribArray(a_textureCordsPos);

        if (texture.hasAlpha())
            glDisable(GL_BLEND);
        glUseProgram(0);
    }

    /**
     * Удаление шейдера из памяти
     */

    @Override
    public void release() {

    }
}
