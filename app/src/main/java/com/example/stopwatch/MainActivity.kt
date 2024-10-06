package com.example.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1




    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            isRunning = !isRunning
            if(isRunning) {
                start()
            } else {
                pause()
            }
        }

        binding.lapButton.setOnClickListener {
            recordLapTime()
        }
        binding.resetFab.setOnClickListener {
            reset()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun start() {
        binding.fab.setImageResource(R.drawable.baseline_pause_24)
        timerTask = timer(period = 10){
            time++
            val sec = time/100
            val milli = time % 100
            runOnUiThread {
                binding.secTextVIew.text = "$sec"
                binding.milliTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        binding.fab.setImageResource(R.drawable.baseline_play_arrow_24)
        timerTask?.cancel() // 종료 , 초기화
    }


    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}"


        binding.lapLayout.addView(textView, 0)
        lap++
    }


    private fun reset() {
        timerTask?.cancel()

        // 모든 변수 초기화
        time = 0
        isRunning = false
        binding.fab.setImageResource(R.drawable.baseline_play_arrow_24)
        binding.secTextVIew.text = "0"
        binding.milliTextView.text = "00"

        //모든 랩타임 제거, 랩 초기화
        binding.lapLayout.removeAllViews()
        lap = 1

    }
}