package com.example.simplecalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private TextView tvHistory;

    private String currentNumber = "0";
    private double previousValue = 0;
    private String pendingOperator = null;
    private boolean startNewNumber = true;

    private static final DecimalFormat FORMATTER = new DecimalFormat("#,##0.##########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        tvHistory = findViewById(R.id.tvHistory);

        setupNumberButton(R.id.btn0, "0");
        setupNumberButton(R.id.btn1, "1");
        setupNumberButton(R.id.btn2, "2");
        setupNumberButton(R.id.btn3, "3");
        setupNumberButton(R.id.btn4, "4");
        setupNumberButton(R.id.btn5, "5");
        setupNumberButton(R.id.btn6, "6");
        setupNumberButton(R.id.btn7, "7");
        setupNumberButton(R.id.btn8, "8");
        setupNumberButton(R.id.btn9, "9");

        findViewById(R.id.btnDot).setOnClickListener(v -> onDot());
        findViewById(R.id.btnClear).setOnClickListener(v -> onClear());
        findViewById(R.id.btnSign).setOnClickListener(v -> onToggleSign());
        findViewById(R.id.btnPercent).setOnClickListener(v -> onPercent());

        findViewById(R.id.btnPlus).setOnClickListener(v -> onOperator("+"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> onOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> onOperator("×"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> onOperator("÷"));

        findViewById(R.id.btnEquals).setOnClickListener(v -> onEquals());
    }

    private void setupNumberButton(int id, String digit) {
        Button b = findViewById(id);
        b.setOnClickListener(v -> onDigit(digit));
    }

    private void onDigit(String digit) {
        if (startNewNumber) {
            currentNumber = digit.equals("0") ? "0" : digit;
            startNewNumber = false;
        } else {
            if (currentNumber.equals("0")) {
                currentNumber = digit;
            } else {
                currentNumber += digit;
            }
        }
        updateDisplay();
    }

    private void onDot() {
        if (startNewNumber) {
            currentNumber = "0.";
            startNewNumber = false;
        } else if (!currentNumber.contains(".")) {
            currentNumber += ".";
        }
        updateDisplay();
    }

    private void onClear() {
        currentNumber = "0";
        previousValue = 0;
        pendingOperator = null;
        startNewNumber = true;
        tvHistory.setText("");
        updateDisplay();
    }

    private void onToggleSign() {
        double value = parseCurrentNumber();
        value = -value;
        currentNumber = formatNumber(value);
        updateDisplay();
    }

    private void onPercent() {
        double value = parseCurrentNumber();
        value = value / 100.0;
        currentNumber = formatNumber(value);
        startNewNumber = true;
        updateDisplay();
    }

    private void onOperator(String operator) {
        double value = parseCurrentNumber();

        if (pendingOperator != null && !startNewNumber) {
            previousValue = compute(previousValue, value, pendingOperator);
        } else {
            previousValue = value;
        }

        pendingOperator = operator;
        startNewNumber = true;
        currentNumber = formatNumber(previousValue);
        tvHistory.setText(formatNumber(previousValue) + " " + operator);
        updateDisplay();
    }

    private void onEquals() {
        if (pendingOperator == null) {
            return;
        }
        double value = parseCurrentNumber();
        double result = compute(previousValue, value, pendingOperator);

        tvHistory.setText(formatNumber(previousValue) + " " + pendingOperator + " " + formatNumber(value) + " =");
        currentNumber = formatNumber(result);
        previousValue = result;
        pendingOperator = null;
        startNewNumber = true;
        updateDisplay();
    }

    private double compute(double a, double b, String operator) {
        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "×":
                return a * b;
            case "÷":
                if (b == 0) {
                    return 0;
                }
                return a / b;
            default:
                return b;
        }
    }

    private double parseCurrentNumber() {
        try {
            return Double.parseDouble(currentNumber);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String formatNumber(double value) {
        if (value == Math.floor(value) && !Double.isInfinite(value)) {
            return FORMATTER.format(value);
        }
        return FORMATTER.format(value);
    }

    private void updateDisplay() {
        tvDisplay.setText(currentNumber);
    }
}
