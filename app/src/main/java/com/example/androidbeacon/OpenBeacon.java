package com.example.androidbeacon;

import android.app.Activity;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseSettings;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.BeaconTransmitter;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

import java.util.Arrays;

public class OpenBeacon extends Activity implements MonitorNotifier {

    protected static final String TAG = "OpenBeacon";
    protected static final String uuid = "2eadb97e-1dd2-11b2-8000-080027b246c5";

    protected String major = "1";
    protected String minor = "1";
    protected String name = "name";

    TextView m_textview;
    TextView m_textResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_beacon_layout);
        //Component
        initialComponent();

        intent_get();
    }

    private void intent_get() {
        Intent intent = getIntent();
        major = intent.getStringExtra("major");
        minor = intent.getStringExtra("minor");
        name = intent.getStringExtra("name");
        Log.d(TAG, "major: "+ major+" minor: "+ minor+" name: "+name);
        m_textview.setText("major: "+ major+" minor: "+ minor+" name: "+name);
    }

    private void initialComponent() {
        //component
        m_textview = (TextView) findViewById(R.id.textView_oprnbeacon);
        m_textResult = (TextView) findViewById(R.id.BLEresult);
    }


    //持續執行
    protected void onResume() {
        super.onResume();
        Beacon beacon = new Beacon.Builder()
                .setId1(uuid)
                .setId2(major)
                .setId3(minor)
                .setManufacturer(0x004C) // Radius Networks.  Change this for other beacon layouts
                .setTxPower(-40)
                .setDataFields(Arrays.asList(new Long[]{0l})) // Remove this for beacon layouts without d: fields
                .build();
        BeaconParser beaconParser = new BeaconParser()
                .setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"); //ibeacon -> 0215

        BeaconTransmitter beaconTransmitter = new BeaconTransmitter(getApplicationContext(), beaconParser);
        beaconTransmitter.startAdvertising(beacon, new AdvertiseCallback() {
            @Override
            public void onStartSuccess(AdvertiseSettings settingsInEffect) {
                m_textResult.setText("是否開啟Beacon: "+"成功開啟!");
            }
            @Override
            public void onStartFailure(int errorCode) {
                m_textResult.setText("是否開啟Beacon: "+"目前無開啟!");
            }
        });
    }

    @Override
    public void didEnterRegion(Region region) {

    }

    @Override
    public void didExitRegion(Region region) {

    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {

    }
}
