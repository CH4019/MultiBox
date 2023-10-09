package com.ch4019.multibox.ui.screen.oneWord

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ch4019.multibox.R
import com.ch4019.multibox.components.CardButton
import com.ch4019.multibox.components.ChipList
import com.ch4019.multibox.model.SelectList
import com.ch4019.multibox.viewmodel.oneWord.OneWordState
import com.ch4019.multibox.viewmodel.oneWord.OneWordViewModel

@Composable
fun OneWordPage() {
    val oneWordViewModel : OneWordViewModel = viewModel()
    val oneWordState = oneWordViewModel.oneWordState.collectAsState()
    val context = LocalContext.current
    val selectValue = listOf(
        SelectList("随机","") ,
        SelectList("动画","a") ,
        SelectList("漫画","b"),
        SelectList("游戏","c"),
        SelectList("文学","d"),
        SelectList("原创","e"),
        SelectList("来自网络","f"),
        SelectList("其他","g"),
        SelectList("影视","h"),
        SelectList("诗词","i"),
        SelectList("网易云","j"),
        SelectList("哲学","k"),
        SelectList("抖机灵","l"),
    )
    val isSelected = remember{ mutableStateOf(false) }
    val select = remember{ mutableStateOf("")}

    Scaffold(
        topBar = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        ) {
            if (oneWordState.value.hitokoto.isNotBlank()){
                CardView(oneWordState = oneWordState)
            }else{
                Card {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        Text(text = "请刷新")
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ChipList(
                    isSelected = isSelected,
                    menuItems = selectValue,
                    value = select,
                    setLabel = "请选择类别"
                )
                Spacer(modifier = Modifier.width(16.dp))
                CardButton(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = {
                        if (isSelected.value){
                            oneWordViewModel.getOneWord(select.value)
                        }else{
                            Toast.makeText(context, "请选择类别", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_public_refresh ),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "刷新")
                }
            }
        }
    }
}

@Composable
fun CardView(
    oneWordState: State<OneWordState>
) {
    val clipboardManager = LocalClipboardManager.current
    val type : String =  when(oneWordState.value.type){
        "a" ->  "动画"
        "b" ->  "漫画"
        "c" ->  "游戏"
        "d" ->  "文学"
        "e" ->  "原创"
        "f" ->  "来自网络"
        "h" ->  "影视"
        "i" ->  "诗词"
        "j" ->  "网易云"
        "k" ->  "哲学"
        "l" ->  "抖机灵"
        else -> "其他"
    }
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(text = oneWordState.value.hitokoto)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "--${oneWordState.value.from} (${oneWordState.value.fromWho})")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = type)
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = {
                        clipboardManager.setText(
                            buildAnnotatedString {
                                append(oneWordState.value.hitokoto+"\n")
                                withStyle(style = SpanStyle()){
                                    append("--"+oneWordState.value.from+oneWordState.value.fromWho)
                                }
                            }
                        )
                    }
                ) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_public_copy ),
                        contentDescription = null
                    )
                }
            }
        }
    }
}