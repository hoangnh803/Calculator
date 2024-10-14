package com.example.calculatorconstraintlayout

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var textResult: TextView
    private lateinit var textOperation: TextView
    private var currentNumber = 0
    private var previousNumber = 0
    private var currentOperation = ""
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textResult = findViewById(R.id.text_result)
        textOperation = findViewById(R.id.text_operation)

        // Số từ 0-9
        for (i in 0..9) {
            val buttonId = resources.getIdentifier("btn_$i", "id", packageName)
            findViewById<Button>(buttonId).setOnClickListener(this)
        }

        // Các phép toán và nút chức năng
        findViewById<Button>(R.id.btn_plus).setOnClickListener(this)
        findViewById<Button>(R.id.btn_minus).setOnClickListener(this)
        findViewById<Button>(R.id.btn_mult).setOnClickListener(this)
        findViewById<Button>(R.id.btn_div).setOnClickListener(this)
        findViewById<Button>(R.id.btn_equal).setOnClickListener(this)
        findViewById<Button>(R.id.btn_c).setOnClickListener(this)
        findViewById<Button>(R.id.btn_ce).setOnClickListener(this)
        findViewById<Button>(R.id.btn_bs).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4,
            R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9 -> {
                val digit = (v as Button).text.toString().toInt()
                appendDigit(digit)
            }
            R.id.btn_plus -> setOperation("+")
            R.id.btn_minus -> setOperation("-")
            R.id.btn_mult -> setOperation("×")
            R.id.btn_div -> setOperation("÷")
            R.id.btn_equal -> calculateResult()
            R.id.btn_c -> clearAll()
            R.id.btn_ce -> clearCurrent()
            R.id.btn_bs -> clearDigit()
        }
        updateDisplay()
    }

    private fun appendDigit(digit: Int) {
        if (isNewOperation) {
            currentNumber = digit
            isNewOperation = false
        } else {
            currentNumber = currentNumber * 10 + digit
        }
    }

    private fun setOperation(operation: String) {
        if (!isNewOperation) {
            calculateResult()
        }
        currentOperation = operation
        previousNumber = currentNumber
        isNewOperation = true
        textOperation.text = "$previousNumber $currentOperation"
    }

    private fun calculateResult() {
        if (currentOperation.isNotEmpty() && !isNewOperation) {
            val result = when (currentOperation) {
                "+" -> previousNumber + currentNumber
                "-" -> previousNumber - currentNumber
                "×" -> previousNumber * currentNumber
                "÷" -> if (currentNumber != 0) previousNumber / currentNumber else 0
                else -> currentNumber
            }
            textOperation.text = "$previousNumber $currentOperation $currentNumber ="
            currentNumber = result
            isNewOperation = true
        }
    }

    private fun clearAll() {
        currentNumber = 0
        previousNumber = 0
        currentOperation = ""
        isNewOperation = true
        textOperation.text = ""
    }

    private fun clearCurrent() {
        currentNumber = 0
        updateDisplay()
    }

    private fun clearDigit() {
        currentNumber /= 10
        updateDisplay()
    }
    private fun updateDisplay() {
        textResult.text = currentNumber.toString()
        if (currentOperation.isNotEmpty() && isNewOperation) {
        } else if (currentOperation.isEmpty()) {
            textOperation.text = ""
        }
    }
}