package com.firework.Platforms.Android.Input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by slava on 17.01.17.
 */

public class AndroidAccelerometerHandler implements SensorEventListener {
    protected float accX;//Accelerometer X cord;
    protected float accY;//Accelerometer Y cord;
    protected float accZ;//Accelerometer Z cord;


    public AndroidAccelerometerHandler(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelerometer = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        accX = event.values[0];
        accY = event.values[1];
        accZ = event.values[2];
    }

    public float getX() {
        return accX;
    }

    public float getY() {
        return accY;
    }

    public float getZ() {
        return accZ;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
