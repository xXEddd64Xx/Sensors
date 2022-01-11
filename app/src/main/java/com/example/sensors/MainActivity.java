package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView sortida;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sortida = (TextView) findViewById(R.id.sortida);
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> llistaSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: llistaSensors) {
            log(sensor.getName());
        }
        llistaSensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (!llistaSensors.isEmpty()) {
            Sensor orientationSensor = llistaSensors.get(0);
            sensorManager.registerListener( this, orientationSensor, SensorManager.SENSOR_DELAY_UI);}

        llistaSensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (!llistaSensors.isEmpty()) {
            Sensor acelerometerSensor = llistaSensors.get(0);
            sensorManager.registerListener ( this, acelerometerSensor, SensorManager.SENSOR_DELAY_UI);}
        llistaSensors = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD);
        if (!llistaSensors.isEmpty()) {
            Sensor magneticSensor = llistaSensors.get(0);
            sensorManager.registerListener( this, magneticSensor, SensorManager.SENSOR_DELAY_UI);}
        llistaSensors = sensorManager.getSensorList(Sensor.TYPE_TEMPERATURE);
        if (!llistaSensors.isEmpty()) {
            Sensor temperatureSensor = llistaSensors.get(0);
            sensorManager.registerListener( this, temperatureSensor, SensorManager.SENSOR_DELAY_UI);}
    }
    private void log(String string) {
        sortida.append(string + "\n");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int precision) {
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
//Cada sensor pot provocar que un thread principal pasi peraquí així que sincronitzam l’accés.
        synchronized (this) {
            switch(event.sensor.getType()) {
                case Sensor.TYPE_ORIENTATION:
                    for (int i=0 ; i<3 ; i++) {
                    log("Orientació "+i+": "+event.values[i]);
                }
                break;
                case Sensor.TYPE_ACCELEROMETER:
                    for (int i=0 ; i<3 ; i++) {
                    log("Acelerómetre "+i+": "+event.values[i]);
                }

                break;
                case Sensor.TYPE_MAGNETIC_FIELD:
                    for (int i=0 ; i<3 ; i++) {
                    log("Magnetisme "+i+": "+event.values[i]);
                }
                break;
                default:
                    for (int i=0 ; i<event.values.length ; i++) {
                    log("Temperatura "+i+": "+event.values[i]);
                }
            }
        }
    }
}