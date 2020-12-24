package com.ad2d.slava.abandoneddungeons.Shaders;

import android.opengl.Matrix;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ad2d.slava.abandoneddungeons.Game.Map;
import com.firework.Modules.GameObjects.Sprite;
import com.firework.Modules.Graphics.Batching.SpriteBatch;
import com.firework.Modules.Graphics.Camera;
import com.firework.Modules.Graphics.Meshes.Mesh;
import com.firework.Modules.Graphics.Shaders.Shader;
import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Graphics.Textures.Texture;
import com.firework.Modules.Graphics.Textures.TextureUtils;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_DYNAMIC_DRAW;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_SRC_ALPHA;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_STRIP;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glDisable;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1f;
import static android.opengl.GLES20.glUniform2fv;
import static android.opengl.GLES20.glUniform3fv;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static com.ad2d.slava.abandoneddungeons.Game.Map.FLOOR;
import static com.ad2d.slava.abandoneddungeons.Game.Map.WALL;

/**
 * Created by Slava on 15.04.2017.
 */

public class MapShader extends Shader {

    private final float[] textureCordsBuffer;

    /*
     * Создание объекта шейдера спрайта из текстуры и сетки
     *
     */

    protected int a_vertexPos;
    protected int u_texturePos;
    protected int a_textureCordsPos;
    protected int u_matrixPos;
    protected int u_position;

    protected int u_lightPosition;
    protected int u_lightPower;


    protected Camera camera;
    protected Map map;
    protected Texture texture;
    protected Mesh mesh;

    protected FloatBuffer staticTextureCords;

    protected float[] cellPositions;

    protected int staticTextureCdsVBO;

    protected int cellSize;

    protected float[] tempPlayerCords = new float[2];

    /**
     * Создание объекта шейдера спрайта из текстуры и сетки
     *
     */

    public MapShader(Camera camera, Map map, Texture texture, int cellSize) {
        this.camera = camera;
        this.map = map;
        this.texture = texture;
        this.mesh = new Mesh();

        String vertexShader =
                "uniform mat4 u_matrix;\n" +
                "uniform vec2 u_position;\n" +
                "\n" +
                "uniform vec2 u_lightPosition;\n" +
                "\n" +
                "attribute vec3 a_vertex;\n" +
                "attribute vec2 a_textureCord;\n" +
                "\n" +
                "varying vec2 v_texture_cords;\n" +
                "varying float v_lightDistance;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    v_texture_cords.s = a_textureCord.s;\n" +
                "    v_texture_cords.t = a_textureCord.t;\n" +
                "\n" +
                "    v_lightDistance = distance(u_position, u_lightPosition);\n" +
                "\n" +
                "    gl_Position = u_matrix * vec4(a_vertex.x + u_position.x, a_vertex.y + u_position.y, a_vertex.z, 1.0);\n" +
                "}";

        String fragmentShader =
                "precision mediump float;\n" +
                        "\n" +
                        "uniform sampler2D u_texture;\n" +
                        "uniform float u_lightPower;\n" +
                        "\n" +
                        "varying vec2 v_texture_cords;\n" +
                        "varying float v_lightDistance;\n" +
                        "\n" +
                        "void main() {\n" +
                        "    vec4 texture_color = texture2D(u_texture, v_texture_cords);\n" +
                        "    float light = 0.0;\n" +
                        "    if(v_lightDistance < u_lightPower)\n" +
                        "        light = 1.0-(1.0/(u_lightPower - v_lightDistance)*0.1);\n" +
                        "    light = light > 0.0 ? light : 0.0;\n" +
                        "\n" +
                        "    gl_FragColor = vec4(texture_color.x + light, texture_color.y + light, texture_color.z + light, texture_color.w);\n" +
                        "}";
        this.vertexShaderID = ShaderUtils.createShader(vertexShader, GL_VERTEX_SHADER);
        this.fragmentShaderID = ShaderUtils.createShader(fragmentShader, GL_FRAGMENT_SHADER);

        this.programID = ShaderUtils.createProgram(this.vertexShaderID, this.fragmentShaderID);

        u_matrixPos = glGetUniformLocation(programID, "u_matrix");

        a_vertexPos = glGetAttribLocation(programID, "a_vertex");
        a_textureCordsPos = glGetAttribLocation(programID, "a_textureCord");
        u_texturePos = glGetAttribLocation(programID, "u_texture");
        u_position = glGetUniformLocation(programID, "u_position");

        u_lightPosition = glGetUniformLocation(programID, "u_lightPosition");
        u_lightPower = glGetUniformLocation(programID, "u_lightPower");

        this.cellSize = cellSize;
        textureCordsBuffer = TextureUtils.cutTextureFloat(texture, cellSize);

        staticTextureCords = ShaderUtils.createFloatBuffer(map.getFullCount() * 8);

        for (int i = 0; i < map.getCapacity(); i++) {
            int type = map.getCellType(i);
            if(type > 0)
                for (int j = 0; j < 8; j++) {
                    staticTextureCords.put(textureCordsBuffer[(type-1) * 8 + j]);
                }
        }

        cellPositions = new float[map.getFullCount() * 2];

        int x = 0;
        for (int y = 0; y < map.getSizeY(); y++) {
            for (int i = 0; x < map.getSizeX(); i += 2) {
                int type = map.getCellType(y * map.getSizeX());
                if (type != Map.NONE && type != Map.LAVA) {
                    cellPositions[y * 2 * map.getSizeX() + i] = -x;
                    cellPositions[y * 2 * map.getSizeX() + i + 1] = y;
                }
                x++;
            }
            x = 0;
        }

        staticTextureCords.flip();

        staticTextureCdsVBO = ShaderUtils.createVBO(staticTextureCords, GL_DYNAMIC_DRAW);
    }

    public void refresh()//Texture data refreshing
    {
        staticTextureCords.position(0);

        for (int i = 0; i < map.getCapacity(); i++) {
            int type = map.getCellType(i);
            if(type > 0)
                for (int j = 0; j < 8; j++) {
                    staticTextureCords.put(textureCordsBuffer[(type-1) * 8 + j]);
                }
        }
        staticTextureCords.flip();
        ShaderUtils.loadDataToVBO(staticTextureCords, staticTextureCdsVBO, GL_DYNAMIC_DRAW);
    }

    public void reload()//reload all data(when player go to new location)
    {
        cellPositions = new float[map.getFullCount() * 2];

        int x = 0;
        for (int y = 0; y < map.getSizeY(); y++) {
            for (int i = 0; x < map.getSizeX(); i += 2) {
                int type = map.getCellType(y * map.getSizeX());
                if (type != Map.NONE && type != Map.LAVA) {
                    cellPositions[y * 2 * map.getSizeX() + i] = -x;
                    cellPositions[y * 2 * map.getSizeX() + i + 1] = y;
                }
                x++;
            }
            x = 0;
        }
        staticTextureCords = ShaderUtils.createFloatBuffer(map.getCapacity() * 8);

        for (int i = 0; i < map.getCapacity(); i++) {
            int type = map.getCellType(i);
            if(type > 0)
                for (int j = 0; j < 8; j++) {
                    staticTextureCords.put(textureCordsBuffer[(type-1) * 8 + j]);
                }
        }
        staticTextureCords.flip();
        ShaderUtils.loadDataToVBO(staticTextureCords, staticTextureCdsVBO, GL_DYNAMIC_DRAW);
    }

    public void start() {
        glUseProgram(programID);

        glUniformMatrix4fv(u_matrixPos, 1, false, camera.getCombinedMatrix(), 0);
        tempPlayerCords[0] = -map.getPlayer().getPositionX();
        tempPlayerCords[1] = map.getPlayer().getPositionY();
        glUniform2fv(u_lightPosition, 1, tempPlayerCords, 0);
        glUniform1f(u_lightPower, 2);

        if (texture.hasAlpha()) {
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        }

        texture.link(u_texturePos);

        glVertexAttribPointer(a_vertexPos, 3, GL_FLOAT, false, 0, mesh.getVertices());
        glEnableVertexAttribArray(a_vertexPos);

    }

    @Override
    public void draw() {

        for (int i = 0; i < map.getCapacity(); i++) {
            int type = map.getCellType(i);
            if(type != Map.NONE && type != Map.LAVA) {
                glUniform2fv(u_position, 1, cellPositions, i * 2);

                glBindBuffer(GL_ARRAY_BUFFER, staticTextureCdsVBO);
                glVertexAttribPointer(a_textureCordsPos, 2, GL_FLOAT, false, 0, i * 32);
                glEnableVertexAttribArray(a_textureCordsPos);

                glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, mesh.getIndices());
                glBindBuffer(GL_ARRAY_BUFFER, 0);
            }
        }
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
