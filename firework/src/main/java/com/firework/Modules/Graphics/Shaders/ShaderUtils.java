package com.firework.Modules.Graphics.Shaders;

import android.util.Log;

import com.firework.Modules.Assets.Loader;
import com.firework.Modules.Graphics.Camera;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glBufferData;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glDeleteProgram;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGenBuffers;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glVertexAttribPointer;


/**
 * Created by Slava on 15.02.2017.
 */

public class ShaderUtils {

    /**
     * Загрузка шейдера по имени(Загрузка происходит из папки шейдеров!)
     * @param shaderPath - имя шейдера
     * @return - возвращение кода шейдера
     */

    public static String loadShader(String shaderPath) {
        return Loader.getFileIO().loadText(shaderPath+".glsl");
    }

    /**
     * Создание float буффера из массива float
     * @param source - исходный массив
     * @return возвращение созданнного буффера
     */

    public static FloatBuffer createFloatBuffer(float[] source)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(source.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer out = byteBuffer.asFloatBuffer();
        out.put(source);
        out.flip();
        return out;
    }

    public static FloatBuffer createFloatBuffer(int count)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(count * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer out = byteBuffer.asFloatBuffer();
        return out;
    }

    /**
     * Создание short буффера из массива short
     * @param source - исходный short массив
     * @return - возвращение созданного short буфера
     */

    public static ShortBuffer createShortBuffer(short[] source)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(source.length * 2);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer out = byteBuffer.asShortBuffer();
        out.put(source);
        out.flip();
        return out;
    }

    public static ShortBuffer createShortBuffer(int count)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(count * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer out = byteBuffer.asShortBuffer();
        return out;
    }

    public static IntBuffer createIntBuffer(int[] source)
    {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(source.length * 2);
        byteBuffer.order(ByteOrder.nativeOrder());
        IntBuffer out = byteBuffer.asIntBuffer();
        out.put(source);
        out.flip();
        return out;
    }

    /**
     * Создание Vertex Buffer Object
     * @param data - исходные данные
     * @param type - тип отрисовки(static или dynamic)
     * @return - номер буффера
     */

    public static int createVBO(Buffer data, int type)
    {
        int[] vbo = new int[1];
        glGenBuffers(1, vbo, 0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
        glBufferData(GL_ARRAY_BUFFER, data.capacity() * 4, data, type);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        return vbo[0];
    }

    public static void loadDataToVBO(Buffer data, int vbo, int type)
    {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, data.capacity() * 4, data, type);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Создание шейдера
     * @param type - тип шейдера(Вершинный или фрагментный)
     * @param shaderName - название шейдера
     * @return - возвращение ID шейдера
     */

    public static int createShader(int type, String shaderName)
    {
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, ShaderUtils.loadShader(shaderName));
        glCompileShader(shaderID);
        int[] status = new int[1];
        glGetShaderiv(shaderID, GL_COMPILE_STATUS, status, 0);
        if(status[0] == 0)
        {
            Log.e("Shader", "Shader error: " + glGetShaderInfoLog(shaderID));
            glDeleteShader(shaderID);
            return 0;
        }
        return shaderID;
    }

    public static int createShader(String program, int type)
    {
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, program);
        glCompileShader(shaderID);
        int[] status = new int[1];
        glGetShaderiv(shaderID, GL_COMPILE_STATUS, status, 0);
        if(status[0] == 0)
        {
            Log.e("Shader", "Shader error: " + glGetShaderInfoLog(shaderID));
            glDeleteShader(shaderID);
            return 0;
        }
        return shaderID;
    }

    /**
     * @param vertexShader - ID вершинного шейдера
     * @param fragmentShader - ID фрагментного шейдера
     * @return - возврашение ID шейдерной программы
     */

    public static int createProgram(int vertexShader, int fragmentShader)
    {
        int programID = glCreateProgram();
        if(vertexShader == 0 || fragmentShader == 0)
        {
            Log.e("Shader", "Programm error: " + glGetProgramInfoLog(programID));
            return 0;
        }
        glAttachShader(programID, vertexShader);
        glAttachShader(programID, fragmentShader);
        glLinkProgram(programID);
        int[] status = new int[1];
        glGetProgramiv(programID, GL_LINK_STATUS, status, 0);
        if(status[0] == 0)
        {
            Log.e("Shader", "Programm error: " + glGetProgramInfoLog(programID));
            glDeleteProgram(programID);
        }
        return programID;
    }

    /*
      *** ИСПОЛЬЗОВАТЬ ИСКЛЮЧИТЕЛЬНО ПОСЛЕ ВЫЗОВА useProgram()! ***
    */

    /**
     * @param programID - ID шейдерной программы
     * @param buffer - массив передаваемых значений
     * @param size - колчичество значений из массива на одну вершину
     * @param field - название поля передаваемого значения
     * @return - возвращение позиции атрибута(сделано, чтобы вызвать linkAttribute(FloatBuffer, int, int) - для увеличения производительности)
     */

    public static int linkAttribute(int programID, Buffer buffer, int size, String field)
    {
        int pos = glGetAttribLocation(programID, field);
        buffer.position(0);
        glVertexAttribPointer(pos, size, GL_FLOAT, false, 0, buffer);
        return pos;
    }

    /**
     * @param buffer - массив передаваемых значений
     * @param size - колчичество значений из массива на одну вершину
     * @param position - позиция атрибута в шейдере
     */

    public static void linkAttribute(Buffer buffer, int size, int position)
    {
        buffer.position(0);
        glVertexAttribPointer(position, size, GL_FLOAT, false, 0, buffer);
    }

    /**
     * @param texturePosition - позиция текстуры в шейдере
     * @param textureID - ID текстуры(int texture.textureID)
     */

    public static void linkTexture(int texturePosition, int textureID)
    {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glUniform1i(texturePosition, 0);
    }
    /**
     * Debug вывод матрицы 4x4
     * @param matrix
     */

    public static void print4x4Matrix(float[] matrix, String name)
    {
        Log.i("MatrixDebug", name);
        for (int y = 0; y < 4; y++) {
            Log.e("MatrixDebug", matrix[y*4] + " , " + matrix[y*4+1] + " , " + matrix[y*4+2] + " , " + matrix[y*4+3]);
        }
        Log.i("MatrixDebug", "----------------------------");
    }
}
