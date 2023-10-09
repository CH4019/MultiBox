package com.ch4019.multibox.ui.screen.express

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ch4019.multibox.components.CardButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpressInquiryPage(
    navController: NavHostController,
) {
    var nu by remember{ mutableStateOf("")}
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ){
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nu,
            onValueChange = { nu = it },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text("快递单号") },
            shape = RoundedCornerShape(15.dp)
        )

        CardButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = {
                if (nu.isNotBlank()){ showBottomSheet = true }
                 }
        ) { Text(text = "查询") }
    }

    if (showBottomSheet){
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState
        ) {
            Column (
                modifier =  Modifier.fillMaxSize()
            ){

            }
//            Button(onClick = {
//                scope.launch { sheetState.hide() }.invokeOnCompletion {
//                    if (!sheetState.isVisible) { showBottomSheet = false }
//                }
//            }) {
//                Text("Hide bottom sheet")
//            }
        }
    }
}