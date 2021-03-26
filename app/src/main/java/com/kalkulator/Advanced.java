package com.kalkulator;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;

public class Advanced extends Simple {
    Button sinBtn, cosBtn, tanBtn, lnBtn,
            sqrtBtn, x2Btn, xyBtn, logBtn;

    public void useOneArgOperation(OperationType type) {
        first = new BigDecimal(field.getText().toString());
        this.type = type;
        calculateAndAddToField();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        layout = R.layout.activity_advanced;
        super.onCreate(savedInstanceState);

        sinBtn = (Button) findViewById(R.id.sinBtn);
        sinBtn.setOnClickListener(a -> {
            useOneArgOperation(OperationType.SIN);
        });
        cosBtn = (Button) findViewById(R.id.cosBtn);
        cosBtn.setOnClickListener(a -> {
            useOneArgOperation(OperationType.COS);
        });
        tanBtn = (Button) findViewById(R.id.tanBtn);
        sinBtn.setOnClickListener(a -> {
            useOneArgOperation(OperationType.TAN);
        });
        lnBtn = (Button) findViewById(R.id.lnBtn);
        lnBtn.setOnClickListener(a -> {
            useOneArgOperation(OperationType.LN);
        });

        sqrtBtn = (Button) findViewById(R.id.sqrtBtn);
        sqrtBtn.setOnClickListener(a -> {
            useOneArgOperation(OperationType.SQRT);
        });
        x2Btn = (Button) findViewById(R.id.x2Btn);
        x2Btn.setOnClickListener(a -> {
            useOneArgOperation(OperationType.X2);
        });
        xyBtn = (Button) findViewById(R.id.xyBtn);
        xyBtn.setOnClickListener(a -> {
            useTwoArgOperation(OperationType.XY);
        });
        logBtn = (Button) findViewById(R.id.logBtn);
        logBtn.setOnClickListener(a -> {
            useOneArgOperation(OperationType.LOG);
        });
    }
}