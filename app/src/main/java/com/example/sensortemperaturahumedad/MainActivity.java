package com.example.sensortemperaturahumedad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private TextView temptxt;
    private TextView humedtxt;
    private SensorManager sensormanager;
    private Sensor tempsensor;
    private Sensor humedsensor;
    private Boolean tempdisponible;
    private Boolean humeddisponible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temptxt = findViewById(R.id.Temp);
        humedtxt = findViewById(R.id.Humed);

        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        tempdisponible = false;
        humeddisponible = false;

        if (sensormanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            tempsensor = sensormanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            tempdisponible = true;
        }else {
            temptxt.setText("El sensor de temperatura no esta disponible");
        }
        if (sensormanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) != null){
            humedsensor = sensormanager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            humeddisponible = true;
        }else {
            humedtxt.setText("El sensor de humedad no esta disponible");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        if (sensorEvent.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            temptxt.setText(sensorEvent.values[0] + " ªC");
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            humedtxt.setText(sensorEvent.values[0] + " %");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i){

    }

    @Override
    protected void onResume(){
        super.onResume();
        if (tempdisponible){
            sensormanager.registerListener(this, tempsensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (humeddisponible){
            sensormanager.registerListener(this, humedsensor, SensorManager.SENSOR_DELAY_NORMAL );
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        sensormanager.unregisterListener(this);
    }

}