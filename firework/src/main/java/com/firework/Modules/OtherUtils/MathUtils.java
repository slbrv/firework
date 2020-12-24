package com.firework.Modules.OtherUtils;

import com.firework.Modules.System.Time;
import com.firework.Modules.Transform.Rect2;
import com.firework.Modules.Transform.Vector2;

/**
 * Created by Slava on 16.03.2017.
 */

public class MathUtils {

    public static float distance(Vector2 pos1, Vector2 pos2) {
        return (float) Math.sqrt(sqr(pos2.getX() - pos1.getX()) + sqr(pos2.getY() - pos1.getY()));
    }

    public static float distance(float x1, float y1, float x2, float y2)
    {
        return (float) Math.sqrt(sqr(x2 - x1) + sqr(y2 - y1));
    }

    public static boolean inRect(Rect2 rect, float x, float y) {
        if (rect.getPoint1().getX() + rect.getPosition().getX() <= x && rect.getPoint1().getY() + rect.getPosition().getY() >= y && rect.getPoint2().getX() + rect.getPosition().getX() >= x && rect.getPoint2().getY() + rect.getPosition().getY() <= y)
            return true;
        else
            return false;
    }

    /**
     * "Мягкое" изменение позиции
     *
     * @param from - начальная позиция
     * @param to   - конечная позиция
     * @param out  - выходная позиция
     * @param time - Врея перехода от позиции from до позиции to
     */

    public static void smooth(Vector2 from, Vector2 to, Vector2 out, float time) {
        if(MathUtils.distance(to, out) > 0.05f) {
            float x = (to.getX() - from.getX()) / time * Time.deltaTime;
            float y = (to.getY() - from.getY()) / time * Time.deltaTime;

            out.add(x, y);
        }
    }

    public static boolean isRectCross(Rect2 rect1, Rect2 rect2)
    {
        if(inRect(rect2, rect1.getPoint1().getX(), rect1.getPoint1().getY()))
            return true;
        else if(inRect(rect2, rect1.getPoint2().getX(), rect1.getPoint1().getY()))
            return true;
        else if(inRect(rect2, rect1.getPoint1().getX(), rect1.getPoint2().getY()))
            return true;
        else if(inRect(rect2, rect1.getPoint2().getX(), rect1.getPoint2().getY()))
            return true;
        else
            return false;
    }

    public static float sqr(float x) {
        return x * x;
    }

    public static float max(float x, float y)
    {
        return x >= y ? x : y;
    }
    public static float min(float x, float y)
    {
        return x >= y ? y : x;
    }
}
