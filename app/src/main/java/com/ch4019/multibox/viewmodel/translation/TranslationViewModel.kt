package com.ch4019.multibox.viewmodel.translation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class TranslationViewModel : ViewModel(){
    private val _translationState = MutableStateFlow(TranslationState())
    val translationState = _translationState.asStateFlow()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage


    init {
        initTranslationState()
    }

    private fun initTranslationState(){
        viewModelScope.launch(Dispatchers.IO) {
            _translationState.update {
                it.copy(
                    textInput = "",
                    from = "auto",
                    to = "zh-CN",
                    textOutput = ""
                )
            }
        }
    }

    private val client = OkHttpClient()

    private fun fetchTranslation(
        input : String,
        from : String,
        to : String
    ): TranslationResponse {
        val url = "https://translate.appworlds.cn/?text=This%20is%20a%20test%20text&from=en&to=zh-CN"
        val request = Request.Builder()
            .url(url)
            .header("User-Agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Mobile Safari/537.36 Edg/115.0.1901.203")
            .build()
        val response = client.newCall(request).execute()
        val responseBody = response.body?.string() ?: "{}"
        Log.d("responseBody",responseBody)
        return Json.decodeFromString<TranslationResponse>(responseBody) // 反序列化
    }

    fun translation(
        input : String,
        from : String,
        to: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true) // 开始加载
            try {
                val data = fetchTranslation(input,from,to)
                if (data.code == 200){
                    _translationState.update {
                        it.copy(textOutput = data.data)
                    }
                }else{
                    _translationState.update {
                        it.copy(textOutput = data.msg)
                    }
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
            } finally {
                _isLoading.postValue(false) // 加载完成
            }
        }
    }
}