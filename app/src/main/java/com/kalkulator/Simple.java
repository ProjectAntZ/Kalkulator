package com.kalkulator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;

enum OperationType {
    NONE(0), EQ(1), ADD(2), SUB(3), DIV(4), MUL(5), SIN(6), COS(7), TAN(8), LN(9), SQRT(10), X2(11), XY(12), LOG(13);

    private final int id;
    private OperationType(int i) {
        id=i;
    }

    public int toInt() {
        return id;
    }

    public static OperationType fromInt(int id) {
        switch (id) {
            case 0:
                return OperationType.NONE;
            case 1:
                return OperationType.EQ;
            case 2:
                return OperationType.ADD;
            case 3:
                return OperationType.SUB;
            case 4:
                return OperationType.DIV;
            case 5:
                return OperationType.MUL;
            case 6:
                return OperationType.SIN;
            case 7:
                return OperationType.COS;
            case 8:
                return OperationType.TAN;
            case 9:
                return OperationType.LN;
            case 10:
                return OperationType.SQRT;
            case 11:
                return OperationType.X2;
            case 12:
                return OperationType.XY;
            case 13:
                return OperationType.LOG;
        }
        return null;
    }

    public static BigDecimal calculate(OperationType type, BigDecimal a, BigDecimal b) {
        switch (type) {
            case ADD:
                return a.add(b);
            case SUB:
                return a.subtract(b);
            case DIV:
                if (b.doubleValue()==0.0) throw new ArithmeticException("Division by 0");
                return a.divide(b);
            case MUL:
                return a.multiply(b);
            case SIN:
                return BigDecimal.valueOf(Math.sin(a.doubleValue()));
            case COS:
                return BigDecimal.valueOf(Math.cos(a.doubleValue()));
            case TAN:
                return BigDecimal.valueOf(Math.tan(a.doubleValue()));
            case LN:
                if (a.doubleValue()<=0.0) throw new ArithmeticException("Number must be >= 0.0");
                return BigDecimal.valueOf(Math.log(a.doubleValue()));
            case SQRT:
                return BigDecimal.valueOf(Math.sqrt(a.doubleValue()));
            case X2:
                return BigDecimal.valueOf(Math.pow(a.doubleValue(), 2.0));
            case XY:
                return BigDecimal.valueOf(Math.pow(a.doubleValue(), b.doubleValue()));
            case LOG:
                if (a.doubleValue()<=0.0) throw new ArithmeticException("Number must be >= 0.0");
                return BigDecimal.valueOf(Math.log10(a.doubleValue()));
        }
        return null;
    }
}

public class Simple extends AppCompatActivity {
    BigDecimal first;
    OperationType type;
    EditText field;
    Button bkspBtn, ceBtn, cBtn, signBtn,
            Btn7, Btn8, Btn9, divBtn,
            Btn4, Btn5, Btn6, mulBtn,
            Btn1, Btn2, Btn3, subBtn,
            Btn0, dotBtn, eqBtn, addBtn;
    Integer layout = R.layout.activity_simple;
    Boolean valueSet = false;

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("first", first.doubleValue());
        savedInstanceState.putInt("OperationType", type.toInt());
        savedInstanceState.putString("field", field.getText().toString());
        savedInstanceState.putBoolean("valueSet", valueSet);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        first = BigDecimal.valueOf(savedInstanceState.getDouble("first"));
        type = OperationType.fromInt(savedInstanceState.getInt("OperationType"));
        field.setText(savedInstanceState.getString("field"));
        valueSet = savedInstanceState.getBoolean("valueSet");
    }

    public void addCharNumberToField(String text) {
        Editable currText = field.getText();

        if(this.type==OperationType.EQ) this.type=OperationType.NONE;

        if((currText.charAt(0)=='0' && currText.length()==1) || valueSet) {
            field.setText(text);
            valueSet = false;
        }
        else {
            field.setText(currText.append(text));
        }
    }

    public void calculateAndAddToField() {
        if (this.type!=OperationType.NONE && this.type!=OperationType.EQ) {
            BigDecimal second;
            BigDecimal res;
            try {
                second = new BigDecimal(field.getText().toString());
                res = OperationType.calculate(this.type, first, second);
                valueSet = true;
                if (res == null) {
                    this.type = OperationType.NONE;
                } else {
                    String resStr = String.valueOf(res);
                    field.setText(resStr);
                    this.type = OperationType.EQ;
                }
            } catch (ArithmeticException e) {
                field.setText("0");
                this.type = OperationType.NONE;
                valueSet = true;

                Snackbar info = Snackbar.make(findViewById(R.id.numberField), e.getMessage(), Snackbar.LENGTH_SHORT);
                info.show();
            } catch (NumberFormatException ignore) {
                field.setText("0");
                this.type = OperationType.NONE;
                valueSet = true;

                Snackbar info = Snackbar.make(findViewById(R.id.numberField), "Result too long", Snackbar.LENGTH_SHORT);
                info.show();
            }
        }
    }

    public void useTwoArgOperation(OperationType type) {
        if (this.type!=OperationType.NONE && this.type!=OperationType.EQ) {
            calculateAndAddToField();
            if (this.type!=type) first = new BigDecimal(field.getText().toString());
            this.type = type;
        }
        else {
            first = new BigDecimal(field.getText().toString());
            this.type = type;
            valueSet = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        field = (EditText) findViewById(R.id.numberField);

        field.setText("0");
        type = OperationType.NONE;

        bkspBtn = (Button) findViewById(R.id.bkspBtn);
        bkspBtn.setOnClickListener(a -> {
            Editable currText = (Editable) field.getText();
            if (!valueSet) {
                if (currText.length() == 1) {
                    field.setText("0");
                } else field.setText(currText.subSequence(0, currText.length() - 1));
            }
        });
        ceBtn = (Button) findViewById(R.id.ceBtn);
        ceBtn.setOnClickListener(a -> {
            field.setText("0");
            valueSet = false;
        });
        cBtn = (Button) findViewById(R.id.cBtn);
        cBtn.setOnClickListener(a -> {
            first = new BigDecimal("0.0");
            type = OperationType.NONE;
            field.setText("0");
            valueSet = false;
        });
        signBtn = (Button) findViewById(R.id.signBtn);
        signBtn.setOnClickListener(a -> {
            Editable currText = (Editable) field.getText();
            if (currText.charAt(0)=='-') {
                field.setText(currText.subSequence(1, currText.length()));
            } else {
                field.setText(currText.insert(0, "-"));
            }
        });

        Btn7 = (Button) findViewById(R.id.Btn7);
        Btn7.setOnClickListener(a -> {
            addCharNumberToField("7");
        });
        Btn8 = (Button) findViewById(R.id.Btn8);
        Btn8.setOnClickListener(a -> {
            addCharNumberToField("8");
        });
        Btn9 = (Button) findViewById(R.id.Btn9);
        Btn9.setOnClickListener(a -> {
            addCharNumberToField("9");
        });
        divBtn = (Button) findViewById(R.id.divBtn);
        divBtn.setOnClickListener(a -> {
            useTwoArgOperation(OperationType.DIV);
        });

        Btn4 = (Button) findViewById(R.id.Btn4);
        Btn4.setOnClickListener(a -> {
            addCharNumberToField("4");
        });
        Btn5 = (Button) findViewById(R.id.Btn5);
        Btn5.setOnClickListener(a -> {
            addCharNumberToField("5");
        });
        Btn6 = (Button) findViewById(R.id.Btn6);
        Btn6.setOnClickListener(a -> {
            addCharNumberToField("6");
        });
        mulBtn = (Button) findViewById(R.id.mulBtn);
        mulBtn.setOnClickListener(a -> {
            useTwoArgOperation(OperationType.MUL);
        });

        Btn1 = (Button) findViewById(R.id.Btn1);
        Btn1.setOnClickListener(a -> {
            addCharNumberToField("1");
        });
        Btn2 = (Button) findViewById(R.id.Btn2);
        Btn2.setOnClickListener(a -> {
            addCharNumberToField("2");
        });
        Btn3 = (Button) findViewById(R.id.Btn3);
        Btn3.setOnClickListener(a -> {
            addCharNumberToField("3");
        });
        subBtn = (Button) findViewById(R.id.subBtn);
        subBtn.setOnClickListener(a -> {
            useTwoArgOperation(OperationType.SUB);
        });

        Btn0 = (Button) findViewById(R.id.Btn0);
        Btn0.setOnClickListener(a -> {
            addCharNumberToField("0");
        });
        dotBtn = (Button) findViewById(R.id.dotBtn);
        dotBtn.setOnClickListener(a -> {
            Editable currText = (Editable) field.getText();
            if(!currText.toString().contains(".") && !valueSet)
                field.setText(currText.append("."));
        });
        eqBtn = (Button) findViewById(R.id.eqBtn);
        eqBtn.setOnClickListener(a -> {
            calculateAndAddToField();
        });
        addBtn = (Button) findViewById(R.id.addBtn);
        addBtn.setOnClickListener(a -> {
            useTwoArgOperation(OperationType.ADD);
        });
    }
}