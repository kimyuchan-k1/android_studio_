package com.example.bmicalculator

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bmicalculator.databinding.ActivityResultBinding
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityResultBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val height = intent.getFloatExtra("height",0f)
        val weight = intent.getFloatExtra("weight",0f)

        val bmi = weight / (height / 100.0f).pow(2.0f)

        when{
            bmi >= 35 -> binding.resultTextView.text = "고도 비만"
            bmi >= 30 -> binding.resultTextView.text = "2단계 비만"
            bmi >= 25 -> binding.resultTextView.text = "1단계 비만"
            bmi >= 23 -> binding.resultTextView.text = "과체중"
            bmi >= 18.5 -> binding.resultTextView.text = "정상"
            else -> binding.resultTextView.text = "저체중"

        }

        //이미지 표시
        when {
            bmi >= 23 ->
                binding.imageView.setImageResource(
                    R.drawable.baseline_sentiment_very_dissatisfied_24)
            bmi >= 18.5 ->
                binding.imageView.setImageResource(
                    R.drawable.baseline_sentiment_satisfied_alt_24)
            else ->
                binding.imageView.setImageResource(
                    R.drawable.baseline_sentiment_dissatisfied_24)
        }

        //토스트 메시지
        Toast.makeText(this, "$bmi", Toast.LENGTH_SHORT).show()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}