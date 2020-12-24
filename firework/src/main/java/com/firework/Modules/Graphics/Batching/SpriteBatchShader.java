package com.firework.Modules.Graphics.Batching;

import com.firework.Modules.Graphics.Shaders.Shader;
import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.System.Releasable;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;

/**
 * Created by Slava on 07.03.2017.
 */

public class SpriteBatchShader extends Shader implements Releasable {

    protected SpriteBatch batch;

    protected int a_vertexPos;
    protected int a_colorsPos;
    protected int u_texturePos;
    protected int a_textureCordsPos;
    protected int u_mvpMatrixPos;

    /**
     * Создание объекта шейдера спрайта из текстуры и сетки
     *
     * @param batch - батчер
     */

    public SpriteBatchShader(SpriteBatch batch) {
        String vertexShader = "" +
                "uniform mat4 u_mvpMatrix;\n" +
                "\n" +
                "attribute vec3 a_vertex;\n" +
                "attribute vec4 a_color;\n" +
                "attribute vec2 a_textureCord;\n" +
                "\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texture_cords;\n" +
                "\n" +
                "void main() {\n" +
                "    v_color = a_color;\n" +
                "\n" +
                "    v_texture_cords.s = a_textureCord.s;\n" +
                "    v_texture_cords.t = a_textureCord.t;\n" +
                "\n" +
                "    gl_Position = u_mvpMatrix * vec4(a_vertex, 1.0);\n" +
                "}";

        String fragmentShader = "" +
                "precision mediump float;\n" +
                "\n" +
                "uniform sampler2D u_texture;\n" +
                "\n" +
                "varying vec2 v_texture_cords;\n" +
                "varying vec4 v_color;\n" +
                "\n" +
                "void main() {\n" +
                "    vec4 texture_color = texture2D(u_texture, v_texture_cords);\n" +
                "    gl_FragColor = texture_color * v_color;\n" +
                "}";
        this.vertexShaderID = ShaderUtils.createShader(vertexShader, GL_VERTEX_SHADER);
        this.fragmentShaderID = ShaderUtils.createShader(fragmentShader, GL_FRAGMENT_SHADER);

        this.programID = ShaderUtils.createProgram(this.vertexShaderID, this.fragmentShaderID);

        this.batch = batch;

        u_mvpMatrixPos = glGetUniformLocation(programID, "u_mvpMatrix");

        a_vertexPos = glGetAttribLocation(programID, "a_vertex");
        a_colorsPos = glGetAttribLocation(programID, "a_color");
        a_textureCordsPos = glGetAttribLocation(programID, "a_textureCord");
        u_texturePos = glGetAttribLocation(programID, "u_texture");
    }

    /**
     * Отрисовка спрайта
     */

    public void start() {
        glUseProgram(programID);
        if (batch.getTexture().hasAlpha()) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }
        glEnableVertexAttribArray(a_vertexPos);
        glEnableVertexAttribArray(a_colorsPos);
        glEnableVertexAttribArray(a_textureCordsPos);
        batch.texture.link(u_texturePos);
    }

    @Override
    public void draw() {
        glUniformMatrix4fv(u_mvpMatrixPos, 1, false, batch.getCamera().getMVPMatrix(batch.getSprite().getTransform().getModelMatrix()), 0);

        glVertexAttribPointer(a_vertexPos, 3, GL_FLOAT, false, 0, batch.getSprite().getMesh().getVertices());
        glVertexAttribPointer(a_colorsPos, 4, GL_FLOAT, false, 0, batch.getSprite().getColors());
        //glVertexAttribPointer(a_textureCordsPos, 2, GL_FLOAT, false, 0, batch.getSprite().getTexture_cords());

        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, batch.getSprite().getMesh().getIndices());
    }


    public void stop() {
        glDisableVertexAttribArray(a_vertexPos);
        glDisableVertexAttribArray(a_colorsPos);
        glDisableVertexAttribArray(a_textureCordsPos);

        if (batch.getTexture().hasAlpha())
            glDisable(GL_BLEND);
        glUseProgram(0);
    }

    /**
     * Удаление шейдера из памяти
     */

    @Override
    public void release() {
        batch.release();
    }
}
