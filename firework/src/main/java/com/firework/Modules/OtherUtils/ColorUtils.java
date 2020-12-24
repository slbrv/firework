package com.firework.Modules.OtherUtils;

import android.util.Log;

import com.firework.Modules.Graphics.Color;

/**
 * Created by Slava on 23.04.2017.
 */

public class ColorUtils {

    /**
     * Color mixing
     * @param result - result of mixing
     * @param c1 - first color
     * @param c2 - second color
     * @param degree - degree of mixing
     */
    public static void mix(Color result, Color c1, Color c2, float degree)
    {
        float mR = (Math.abs(c1.r - c2.r) * degree) + MathUtils.min(c1.r, c2.r);
        float mG = (Math.abs(c1.g - c2.g) * degree) + MathUtils.min(c1.g, c2.g);
        float mB = (Math.abs(c1.b - c2.b) * degree) + MathUtils.min(c1.b, c2.b);
        float mA = (Math.abs(c1.a - c2.a) * degree) + MathUtils.min(c1.a, c2.a);
        result.set(mR, mG, mB, mA);
    }
}
