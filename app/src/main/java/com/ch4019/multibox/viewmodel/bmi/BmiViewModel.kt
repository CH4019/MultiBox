package com.ch4019.multibox.viewmodel.bmi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.pow
import kotlin.math.roundToInt

class BmiViewModel : ViewModel(){
    private var _bmiState = MutableStateFlow(BmiState())
    val bmiState: StateFlow<BmiState>
        get() = _bmiState

    init {
        initBmi()
    }

    private fun initBmi(){
        _bmiState.update {
            it.copy(
                height = 0.0,
                weight = 0.0
            )
        }
    }

    fun getBmi(
        h:Double,
        w:Double
    ){
        val heightInMeters = h / 100.0
        val bmi = ((w / heightInMeters.pow(2.0))*10).roundToInt() / 10.0
        _bmiState.update {
            it.copy(
                height = h,
                weight = w,
                bmi = bmi
            )
        }
    }
}