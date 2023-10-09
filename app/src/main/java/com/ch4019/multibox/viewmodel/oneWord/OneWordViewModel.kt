package com.ch4019.multibox.viewmodel.oneWord

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class OneWordViewModel : ViewModel(){
    private var _oneWordState = MutableStateFlow(OneWordState())
    val oneWordState: StateFlow<OneWordState>
        get() = _oneWordState

    init {
        initOneWordState()
    }

    private fun initOneWordState(){
        _oneWordState.update {
            it.copy(
                c = "",
                id = 0,
                type = "",
                from = "",
                fromWho = "",
                uuid = "",
                hitokoto = ""
            )
        }
    }

//    companion object {
//        private val client = OkHttpClient()
//        private val json = Json{ ignoreUnknownKeys = true }
//    }

    private val json = Json { ignoreUnknownKeys = true }

    private fun connect(
        isSelected : Boolean,
        c: String
    ):OneWordResponse {
        val url = if (isSelected){
            "https://v1.hitokoto.cn/?c=${c}"
        }else{
            "https://v1.hitokoto.cn/"
        }
        val request = Request.Builder()
            .url(url)
            .header("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Mobile Safari/537.36 Edg/115.0.1901.203")
            .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: "{}"
        Log.d("responseBody",responseBody)
        return json.decodeFromString<OneWordResponse>(responseBody)
    }

    private val client = OkHttpClient()
    fun getOneWord(
        c : String,
    ){
        val isSelected = c.isNotBlank()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val data = connect(isSelected,c)
                Log.d("data", data.hitokoto)
                _oneWordState.update {
                    it.copy(
                        c = c,
                        id = data.id,
                        type = data.type,
                        from = data.from,
                        fromWho = if (data.fromWho.isNullOrBlank()){"未知"}else data.fromWho,
                        uuid = data.uuid,
                        hitokoto = data.hitokoto
                    )
                }
            } catch (e: Exception) {
                Log.e("getOneWord", "Error occurred", e)
            }
        }
    }


}