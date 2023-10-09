package com.ch4019.multibox.ui.screen.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ch4019.multibox.R
import com.ch4019.multibox.config.MainNavRoute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

@Composable
fun StartPage(
    navController: NavHostController,
) {
    LaunchedEffect(Dispatchers.IO) {
        delay(1000)
        navController.navigate(MainNavRoute.MAIN_NAV){
            popUpTo(MainNavRoute.START_PAGE){
                saveState = true
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Surface (
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ){
        Box (
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Image(
                painter = painterResource(id = R.drawable.multi_box),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}