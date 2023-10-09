package com.ch4019.multibox.ui.screen.bmi

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ch4019.multibox.components.CardButton
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
                OutlinedTextField(
                    value = bmiHeight.value,
                    onValueChange = { bmiHeight.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("请输入身高") },
                    shape = RoundedCornerShape(15.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = bmiWeight.value,
                    onValueChange = { bmiWeight.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth(),
                    placeholder = { Text("请输入体重") },
                    shape = RoundedCornerShape(15.dp)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            CardButton(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    bmiViewModel.getBmi(bmiHeight.value.toInt(),bmiWeight.value.toDouble())
                    showBottomSheet.value = true
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
                Card {
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