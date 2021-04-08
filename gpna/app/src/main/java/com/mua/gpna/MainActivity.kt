package com.mua.gpna

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var etInput : EditText
    private lateinit var btnPredict : Button
    private lateinit var tvOutput : TextView

    private lateinit var gpnaClassifier: GpnaClassifier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initInferenceEngine()
    }

    private fun initInferenceEngine(){
        gpnaClassifier = GpnaClassifier(this)
    }

    private fun initViews(){
        etInput = findViewById(R.id.et_input)
        btnPredict = findViewById(R.id.btn_predict)
        tvOutput = findViewById(R.id.tv_output)

        btnPredict.setOnClickListener {
            tvOutput.text = gpnaClassifier.runInference(etInput.text.toString()).toString()
        }
    }
}