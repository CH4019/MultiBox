package com.ch4019.multibox.ui.screen.bmi

import android.widget.Toast
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ch4019.multibox.components.CardButton
import com.ch4019.multibox.components.ShowProgress
import com.ch4019.multibox.viewmodel.bmi.BmiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BmiPage() {
    val bmiViewModel: BmiViewModel = viewModel()
    val bmiState = bmiViewModel.bmiState.collectAsState()
    val sheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val bmiHeight = remember{ mutableStateOf("")}
    val bmiWeight = remember{ mutableStateOf("")}
    val context = LocalContext.current
    val value by animateFloatAsState(
        targetValue = if (bmiState.value.bmi < 40.0){
            (bmiState.value.bmi/40.0).toFloat()
        }else{ 1f }
        ,
        animationSpec = TweenSpec(durationMillis = 500),
        label = ""
    )

    LaunchedEffect(sheetState.currentValue){
        withContext(Dispatchers.IO){
            if (sheetState.currentValue == SheetValue.Hidden){
                showBottomSheet.value = false
            }
        }
    }

    Scaffold(
        topBar = {}
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            Card {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    OutlinedTextField(
                        value = bmiHeight.value,
                        onValueChange = { bmiHeight.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f),
                        placeholder = { Text("请输入身高") },
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Card (
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ){
                        Column (
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(text = "cm")
                        }
                    }
                }
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    OutlinedTextField(
                        value = bmiWeight.value,
                        onValueChange = { bmiWeight.value = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .weight(1f),
                        placeholder = { Text("请输入体重") },
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Card (
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ){
                        Column (
                            modifier = Modifier.padding(8.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Text(text = "kg")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            CardButton(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    if (bmiHeight.value.isNotBlank() && bmiWeight.value.isNotBlank()){
                        bmiViewModel.getBmi(bmiHeight.value.toDouble(),bmiWeight.value.toDouble())
                        showBottomSheet.value = true
                    }else {
                        Toast.makeText(context, "请输入身高和体重", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(text = "计算")
            }
        }
    }

    if (showBottomSheet.value){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState,
            dragHandle ={ BottomSheetDefaults.DragHandle()},
            windowInsets = WindowInsets(top = 0.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 250.dp)
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Box (
                    contentAlignment = Alignment.Center,
                ){
                    ShowProgress(
                        modifier = Modifier
                            .size(200.dp),
                        progress = value,
                        strokeWidth = 8.dp,
                        strokeCap = StrokeCap.Round,
                        trackColor = Color.Gray
                    )
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        ) {
                            Text(text = bmiState.value.bmi.toString())
                        }
                    }
                }
            }
        }
    }

}