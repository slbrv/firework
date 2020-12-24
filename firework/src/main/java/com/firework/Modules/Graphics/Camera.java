package com.firework.Modules.Graphics;


import android.opengl.Matrix;

import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.Transform.Vector2;
import com.firework.Modules.Transform.Vector3;

import java.nio.FloatBuffer;

/**
 * Created by slava on 10.02.17.
 */

public abstract class Camera{

    protected float[] MVPMatrix = new float[16];//Model * View * Projection Matrix
    protected float[] viewMatrix = new float[16];
    protected float[] projectionMatrix = new float[16];

    protected Vector3 position;
    protected Vector3 center;
    protected Vector3 up;
    protected Vector3 size;

    protected float ratio;

    protected float zoom;

    protected float near;
    protected float far;

    /**
     * Супер конструктор
     * @param width - ширина экрана
     * @param height - высота экрана
     * @param near - как близко камера будет видеть
     * @param far - как далеко камера будет видеть
     * @param zoom - значение преближения камеры(чем больше значение, тем ближе будет смотреть камера)
     */

    public Camera(float width, float height, float near, float far, float zoom)//Screen width and screen height
    {
        size = new Vector3(width, height, 0);
        position = new Vector3(0.0f, 0.0f, -10.0f);
        center = new Vector3(0,0,0.0f);
        up = new Vector3(0.0f,1.0f,0.0f);

        this.ratio = size.getX()/size.getY();

        this.near = near;
        this.far = far;

        this.zoom = zoom;

        linkProjection();
        prepare();
    }

    /**
     * Абстрактный метод, устанавливающий матрицу проекции для разных камер
     */

    public abstract void linkProjection();

    /**
     * Возращение позиции камеры
     * @return - (Vector3)позиция камеры
     */

    public Vector3 getPosition() {
        return position;
    }

    /**
     * Возвращение матрицы модели, вида и проекции
     * @return - (float[16])матрица модели, вида и проекции
     */

    public float[] getMVPMatrix(float[] modelMatrix) {
        float[] mvpMatrix = new float[16];
        Matrix.setIdentityM(mvpMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, modelMatrix, 0, viewMatrix, 0);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, mvpMatrix, 0);
        return mvpMatrix;
    }

    /**
     * Возвращение матрицы модели, вида и проекции
     * @return - (float[16])матрица модели, вида и проекции
     */

    public FloatBuffer getMVPMatrix(FloatBuffer modelMatrix) {
        Matrix.setIdentityM(MVPMatrix, 0);
        Matrix.multiplyMM(MVPMatrix, 0, modelMatrix.array(), 0, viewMatrix, 0);
        Matrix.multiplyMM(MVPMatrix, 0, projectionMatrix, 0, MVPMatrix, 0);
        FloatBuffer temp = ShaderUtils.createFloatBuffer(MVPMatrix);
        return temp;
    }


    public float[] getCombinedMatrix()
    {
        float[] result = new float[16];
        Matrix.multiplyMM(result, 0, projectionMatrix, 0, viewMatrix, 0);
        return result;
    }

    /**
     * Возвращение матрицы вида
     * @return - (float[16])матрица вида
     */

    public float[] getViewMatrix() {
        return viewMatrix;
    }

    /**
     * Возвращение матрицы проекции
     * @return - (float[16])матрица проекции
     */

    public float[] getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * Установка направления вектора верха камеры
     * @param up - направление
     */

    public void setUp(Vector3 up) {
        this.up.copy(up);
    }

    /**
     * Установка позиции взгляда камеры
     * @param center - позиция, на которую смотрит камера
     */

    public void setCenter(Vector3 center) {
        this.center.copy(center);
    }

    /**
     * Установка позиции камеры
     * Переделать в абстрактые, так как не будет рабоать в перспективной проекции!
     */

    public void setPosition(Vector3 vector)
    {
        this.position.set(-vector.getX(), vector.getY(), this.position.getZ());
        this.center.set(this.position.getX(), this.position.getY(), 0.0f);
    }

    public void  setPosition(Vector2 vector)
    {
        this.position.set(-vector.getX(), vector.getY(), this.position.getZ());
        this.center.set(this.position.getX(), this.position.getY(), 0.0f);
    }

    public void setPosition(float x, float y, float z)
    {
        this.position.set(-x, y, z);
        this.center.set(this.position.getX(), this.position.getY(), 0.0f);
    }

    public void setPosition(float x, float y)
    {
        this.position.set(-x, y, this.position.getZ());
        this.center.set(this.position.getX(), this.position.getY(), 0.0f);
    }


    public void addPosition(float x, float y)
    {
        this.position.add(-x, y);
        this.center.set(this.position.getX(), this.position.getY(), 0.0f);
    }

    /**
     * Необходимо вызывать каждый раз после устаановки новых свойств камеры!
     */

    public void prepare()
    {
        Matrix.setLookAtM(viewMatrix, 0, position.getX(), position.getY(), position.getZ(), position.getX(), position.getY(), position.getZ() + 1.0f, up.getX(), up.getY(), up.getZ());
        linkProjection();
    }

    /**
     * Получение значение зума камеры
     * @return значение зума камеры
     */

    public float getZoom()
    {
        return zoom;
    }

    /**
     * Установка зума камеры
     * @param zoom - значение зума
     */
    public void setZoom(float zoom)
    {
        this.zoom = zoom;
    }

    public float addZoom(float add)
    {
        return this.zoom += add;
    }

    public Vector2 toCanvasCords(Vector2 vector2)
    {
        vector2.set((vector2.getX() - size.getX()/2) / size.getY() * (1/zoom) * 2, -(vector2.getY() - size.getY()/2) / size.getY() * (1/zoom) * 2);
        return vector2;
    }

    public float toCanvasCordsX(float x)
    {
        return ((x - size.getX()/2) / size.getY() * (1/zoom) * 2);
    }

    public float toCanvasCordsY(float y)
    {
        return -((y - size.getY()/2) / size.getY() * (1/zoom) * 2);
    }

    /**
     * Перевод координат касания в координаты мира
     * @param vector2 - позиция нажатия на экране
     * @return - преобразованная позиция
     */

    public Vector2 toWorldCords(Vector2 vector2)
    {
        vector2.set((vector2.getX() - size.getX()/2) / size.getY() * (1/zoom) * 2 - position.getX(), -(vector2.getY() - size.getY()/2) / size.getY() * (1/zoom) * 2 + position.getY());
        return vector2;
    }


    /**
     * Перевод координаты X - экрана к координате X - мира
     * @param x - координата X нажатия
     * @return преобразованная координата X
     */

    public float toWorldCordsX(float x)
    {
        return ((x - size.getX()) / size.getY() * (1/zoom)) - position.getX();
    }

    /**
     * Перевод координаты Y - экрана к координате Y - мира
     * @param y - координата Y нажатия
     * @return преобразованная координата Y
     */

    public float toWorldCordsY(float y)
    {
        return ((y - size.getY()) / size.getY() * (1/zoom)) - position.getY();
    }


    public float getWidth()
    {
        return ratio/zoom * 2;
    }

    public float getHeight()
    {
        return 1/zoom;
    }
}
