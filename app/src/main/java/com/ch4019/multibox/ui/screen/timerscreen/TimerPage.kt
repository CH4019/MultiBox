package com.ch4019.multibox.ui.screen.timerscreen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ch4019.multibox.R
import com.ch4019.multibox.components.CardButton
import com.ch4019.multibox.viewmodel.timer.TimerViewModel

@Composable
fun TimerPage(
//    navController: NavHostController,
) {
    val timerViewModel : TimerViewModel = viewModel()
    val translationState = timerViewModel.timerState.collectAsState()
    var isStart by remember{ mutableStateOf(false) }
    LaunchedEffect(Unit){
            timerViewModel.start()
    }

    val spacer = (1..12).toList()

    val spacer2 = listOf(
        1,2,3,4,6,7,8,9,
        11,12,13,14,16,17,18,19,
        21,22,23,24,26,27,28,29,
        31,32,33,34,36,37,38,39,
        41,42,43,44,46,47,48,49,
        51,52,53,54,56,57,58,59,
    )
    val animatedRotation by animateFloatAsState(
        targetValue = translationState.value.degrees,
        animationSpec = tween(durationMillis = 100, easing = LinearEasing), label = ""
    )


    Scaffold (
        topBar = {}
    ){
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Box (
                modifier = Modifier
                    .size(300.dp)
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ){
                Canvas(
                    modifier = Modifier
                        .size(300.dp)
                ) {
                    drawCircle(
                        color = Color.DarkGray,
                        radius = size.height / 2,
                        center = Offset(size.width / 2,size.height / 2)
                    )
                }
                spacer.forEach {
                    Canvas(modifier = Modifier
                        .size(300.dp)
                    ){
                        rotate(
                            degrees = it*30f
                        ){
                            drawLine(
                                color = Color.Gray,
                                strokeWidth = 8f,
                                cap = StrokeCap.Round,
                                start = Offset(size.width / 2,40f),
                                end = Offset(size.width / 2, 20f),
                            )
                        }
                    }
                }
                spacer2.forEach {
                    Canvas(modifier = Modifier
                        .size(300.dp)
                    ){
                        rotate(
                            degrees = it*6f
                        ){
                            drawLine(
                                color = Color.Gray,
                                strokeWidth = 4f,
                                cap = StrokeCap.Round,
                                start = Offset(size.width / 2,30f),
                                end = Offset(size.width / 2, 20f),
                            )
                        }
                    }
                }
                Canvas(
                    modifier = Modifier
                        .size(300.dp)
                ) {
                    rotate(degrees = animatedRotation ){
                        drawLine(
                            color = Color.Red,
                            strokeWidth = 8f,
                            cap = StrokeCap.Round,
                            start = Offset(size.width / 2,(size.height / 2)-8f),
                            end = Offset(size.width / 2, 20f),
                        )
                        drawCircle(
                            color = Color.Red,
                            radius = 8f,
                            style = Stroke(
                                width = 4f
                            ),
                            center = Offset(size.width / 2,size.height / 2)
                        )
                        drawLine(
                            color = Color.Red,
                            strokeWidth = 8f,
                            cap = StrokeCap.Round,
                            start = Offset(size.width / 2,(size.height / 2)+8f),
                            end = Offset(size.width / 2, (size.height / 2)+16f),
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Card (
                shape = RoundedCornerShape(15.dp)
            ){
                Row (
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(text = "${ translationState.value.minutes }")
                    Text(text = ":")
                    Text(text = "${ translationState.value.seconds }")
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Row (
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ){
                CardButton(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = {
                        if (isStart){
                            timerViewModel.pause()
                        }else {
                            timerViewModel.resume()
                        }
                        isStart = !isStart
                    },
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = if (isStart) R.drawable.pause_opsz24 else R.drawable.play_arrow_opsz24),
                        contentDescription = null
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                CardButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = {
                        isStart = false
                        timerViewModel.pause()
                        timerViewModel.initTimerState()
                    },
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.restart_opsz24),
                        contentDescription = null
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TimerPagePreview() {
    TimerPage()
}