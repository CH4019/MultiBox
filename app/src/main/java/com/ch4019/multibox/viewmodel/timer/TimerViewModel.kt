package com.ch4019.multibox.viewmodel.timer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import kotlin.math.roundToInt
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds


class TimerViewModel : ViewModel(){
    private var _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState>
        get() = _timerState
    init {
        initTimerState()
    }
//  初始化/复位
     fun initTimerState(){
        _timerState.update {
            it.copy(
                degrees = 0f,
                seconds = 0.00,
                minutes = 0
            )
        }
    }

    private fun setDateTime(){
        val degrees  = _timerState.value.degrees
        val minutes = _timerState.value.minutes
        val seconds = _timerState.value.seconds
        viewModelScope.launch(Dispatchers.IO) {
            if (seconds >= 59.99){
                _timerState.update {
                    it.copy(
                        degrees = (degrees + 0.6F) % 360,
                        seconds = 0.00,
                        minutes = minutes+1
                    )
                }
            }else{
                _timerState.update {
                    it.copy(
                        degrees = (degrees + 0.6F) % 360,
                        seconds = (seconds * 10 + 1).roundToInt() / 10.0,
                        minutes = minutes
                    )
                }

            }
        }

    }

    private val shouldPause = MutableStateFlow(true)

    fun start(){
        tickerFlow(100.milliseconds)
            .filter { !shouldPause.value }
            .map { LocalDateTime.now() }
            .distinctUntilChanged { old, new ->
                old.nano == new.nano
            }
            .onEach {
                setDateTime()
            }
            .launchIn(viewModelScope)
    }

    fun pause() {
        shouldPause.value = true
    }
    fun resume() {
        shouldPause.value = false
    }

    private fun tickerFlow(period: Duration, initialDelay: Duration = Duration.ZERO) = flow {
        delay(initialDelay)
        while (true) {
            emit(Unit)
            delay(period.inWholeMilliseconds)
        }
    }.conflate()
}