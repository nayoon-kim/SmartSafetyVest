package com.example.smartvest;

import android.content.Context;

import java.io.Serializable;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class mBluetoothSPP extends BluetoothSPP implements Serializable {
    public mBluetoothSPP(Context context) {
        super(context);
    }
}
