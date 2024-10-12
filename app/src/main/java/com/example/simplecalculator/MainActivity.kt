package com.example.simplecalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: EditText
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var operation: String? = null
    private var resultDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttons = listOf(R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9)
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { appendDigit((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { handleOperation("+") }
        findViewById<Button>(R.id.btnSub).setOnClickListener { handleOperation("-") }
        findViewById<Button>(R.id.btnMul).setOnClickListener { handleOperation("*") }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { handleOperation("/") }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnSqrt).setOnClickListener { handleSquareRoot() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clearDisplay() }
        findViewById<Button>(R.id.btnBack).setOnClickListener { removeLastChar() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toggleSign() }

        // Add the dot (.) button functionality
        val buttonDot = findViewById<Button>(R.id.btnDot) // Make sure you have a button with this ID in your layout
        buttonDot.setOnClickListener {
            val currentText = display.text.toString()
            // Only add a dot if there is no existing dot in the current input
            if (!currentText.contains(".")) {
                display.append(".")
            }
        }
    }

    private fun appendDigit(digit: String) {
        if (resultDisplayed) {
            display.setText("")
            resultDisplayed = false
        }
        display.append(digit)
    }

    private fun handleOperation(op: String) {
        if (display.text.isNotEmpty()) {
            operand1 = display.text.toString().toDouble()
            operation = op
            display.setText("")
        }
    }

    private fun calculateResult() {
        if (display.text.isNotEmpty()) {
            operand2 = display.text.toString().toDouble()
            val result = when (operation) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> if (operand2 != 0.0) operand1 / operand2 else Double.NaN
                else -> 0.0
            }
            display.setText(result.toString())
            resultDisplayed = true
        }
    }

    private fun handleSquareRoot() {
        if (display.text.isNotEmpty()) {
            val value = display.text.toString().toDouble()
            display.setText(Math.sqrt(value).toString())
        }
    }

    private fun clearDisplay() {
        display.setText("")
        operand1 = 0.0
        operand2 = 0.0
    }

    private fun removeLastChar() {
        val currentText = display.text.toString()
        if (currentText.isNotEmpty()) {
            display.setText(currentText.substring(0, currentText.length - 1))
        }
    }

    private fun toggleSign() {
        if (display.text.isNotEmpty()) {
            val value = display.text.toString().toDouble()
            display.setText((-value).toString())
        }
    }
}
