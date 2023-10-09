package com.ch4019.multibox.ui.screen.bingwallpapers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ch4019.multibox.ui.theme.MultiBoxTheme

@Composable
fun BingWallpapersPage(
    navController: NavHostController,
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ){

    }
}

@Preview(showBackground = true)
@Composable
fun PageShow(){
    MultiBoxTheme {
    }
}