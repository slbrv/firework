package com.firework.Modules.System;

import java.util.ArrayList;

/**
 * Created by slava on 17.01.17.
 */
public class Pool<T> {
    protected final ArrayList<T> objects;
    protected final PoolHandler<T> handler;
    protected final int maxSize;


    public Pool(PoolHandler<T> handler, int maxSize) {
        this.objects = new ArrayList<T>(maxSize);
        this.handler = handler;
        this.maxSize = maxSize;
    }

    public T addObject() {
        T temp;
        if (objects.size() == 0)
            temp = handler.createObject();
        else
            temp = objects.remove(objects.size() - 1);
        return temp;
    }

    public void free(T object) {
        if (objects.size() < maxSize)
            objects.add(object);
    }
}
