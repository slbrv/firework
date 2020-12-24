package com.firework.Modules.Graphics;

import android.util.Log;

import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.OtherUtils.MathUtils;

import java.nio.FloatBuffer;

/**
 * Created by Slava on 17.03.2017.
 */

public class Color {

    public float r, g, b, a;

    public static final float ipf = 1.0f/255.0f;//A constant that indicates how much an int can be between 0.0f and 1.0f (int per float)

    public Color(Color copy)
    {
        this.r = copy.r;
        this.g = copy.g;
        this.b = copy.b;
        this.a = copy.a;
    }

    public Color(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public Color(float r, float g, float b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = 1.0f;
    }

    public Color(int r, int g, int b, int a)
    {
        this.r = ipf * r;
        this.g = ipf * g;
        this.b = ipf * b;
        this.a = ipf * a;
    }

    public Color(int r, int g, int b)
    {
        this.r = ipf * r;
        this.g = ipf * g;
        this.b = ipf * b;
        this.a = 1.0f;
    }

    public void set(float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void set(float r, float g, float b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void set(int r, int g, int b, int a)
    {
        this.r = ipf * r;
        this.g = ipf * g;
        this.b = ipf * b;
        this.a = ipf * a;
    }

    public void set(int r, int g, int b)
    {
        this.r = ipf * r;
        this.g = ipf * g;
        this.b = ipf * b;
    }

    public void mix(Color target, float degree)
    {
        this.r = (Math.abs(this.r - target.r) * degree) + MathUtils.min(this.r, target.r);
        this.g = (Math.abs(this.g - target.g) * degree) + MathUtils.min(this.g, target.g);
        this.b = (Math.abs(this.b - target.b) * degree) + MathUtils.min(this.b, target.b);
        this.a = (Math.abs(this.a - target.a) * degree) + MathUtils.min(this.a, target.a);
    }

    @Override
    public String toString()
    {
        return "R: " + r + " G: " + g + " B: " + b + " A: " + a;
    }
}
