package com.ch4019.multibox.ui.screen.translation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ch4019.multibox.components.CardButton
import com.ch4019.multibox.components.ChipList
import com.ch4019.multibox.model.SelectList
import com.ch4019.multibox.viewmodel.translation.TranslationViewModel

@Composable
fun TranslationPage(
    navController: NavHostController,
) {
    val translationViewModel : TranslationViewModel = viewModel()
    val translationState = translationViewModel.translationState.collectAsState()
    var textInput by remember{ mutableStateOf("") }
    var textOutput by remember{ mutableStateOf("")}
    val from = remember{ mutableStateOf("")}
    val to = remember{ mutableStateOf("")}
    textOutput = translationState.value.textOutput
    val context = LocalContext.current
    val selectValue = listOf(
        SelectList("简体中文","zh-CN") ,
        SelectList("英语","en"),
        SelectList("简体中文","zh-CN"),
        SelectList("简体中文","zh-CN"),
        SelectList("简体中文","zh-CN"),
        SelectList("简体中文","zh-CN"),
        SelectList("简体中文","zh-CN")
    )
    val isSelectedFrom = remember{ mutableStateOf(false)}
    val isSelectedTo = remember{ mutableStateOf(false)}
    Scaffold (
        topBar = {}
    ){ it ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp)
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ){
                ChipList(
                    isSelected = isSelectedFrom,
                    menuItems = selectValue,
                    value = from,
                    setLabel = "请选择语言"
                )
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
                ChipList(
                    isSelected = isSelectedTo,
                    menuItems = selectValue,
                    value = to,
                    setLabel = "请选择语言"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp),
                placeholder = { Text("请输入需要翻译的内容") },
                shape = RoundedCornerShape(15.dp)
            )

            CardButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {
                    if (isSelectedFrom.value&&isSelectedTo.value){
                        if (textInput.isNotBlank()){
                            translationViewModel.translation(textInput, from.value, to.value)
                        }else {
                            Toast.makeText(context, "请输入需要翻译的内容", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(context, "请选择翻译的语言", Toast.LENGTH_SHORT).show()
                    }
                }
            ) { Text(text = "翻译") }

            OutlinedTextField(
                value = textOutput,
                onValueChange = {  },
                enabled = false,
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                placeholder = { Text("翻译后的内容") },
                shape = RoundedCornerShape(15.dp)
            )
        }
    }

}