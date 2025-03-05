package com.example.mentalguru.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private val initialTime = 1 * 60 * 1000L

    private val _timeLeft = MutableLiveData(initialTime)
    val timeLeft: LiveData<Long> = _timeLeft

    private val _timerRunning = MutableLiveData(false)
    val timerRunning: LiveData<Boolean> = _timerRunning

    private val _timerFinished = MutableLiveData(false)
    val timerFinished: LiveData<Boolean> = _timerFinished

    private var countDownTimer: CountDownTimer? = null

    fun toggleTimer() {
        when {
            _timerFinished.value == true -> resetTimer()
            _timerRunning.value == true -> stopTimer()
            else -> startTimer()
        }
    }

    private fun startTimer() {
        _timerRunning.value = true
        _timerFinished.value = false

        countDownTimer = object : CountDownTimer(_timeLeft.value ?: initialTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeLeft.postValue(millisUntilFinished)
            }

            override fun onFinish() {
                _timeLeft.postValue(0)
                _timerRunning.postValue(false)
                _timerFinished.postValue(true)
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        _timerRunning.value = false
    }

    private fun resetTimer() {
        _timeLeft.value = initialTime
        _timerFinished.value = false
    }

    override fun onCleared() {
        super.onCleared()
        countDownTimer?.cancel()
    }
}
