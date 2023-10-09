package com.ch4019.multibox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ch4019.multibox.config.MainNavRoute
import com.ch4019.multibox.ui.screen.bingwallpapers.BingWallpapersPage
import com.ch4019.multibox.ui.screen.bmi.BmiPage
import com.ch4019.multibox.ui.screen.express.ExpressInquiryPage
import com.ch4019.multibox.ui.screen.index.IndexPage
import com.ch4019.multibox.ui.screen.oneWord.OneWordPage
import com.ch4019.multibox.ui.screen.start.StartPage
import com.ch4019.multibox.ui.screen.timerscreen.TimerPage
import com.ch4019.multibox.ui.screen.translation.TranslationPage
import com.ch4019.multibox.ui.theme.MultiBoxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //设置全屏
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            MultiBoxTheme {
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = MainNavRoute.START_PAGE,
                        enterTransition = {
                            slideInHorizontally(
                                animationSpec = tween(300),
                                initialOffsetX = { it }
                            )
                        },
                        exitTransition = {
                            slideOutHorizontally(
                                animationSpec = tween(300),
                                targetOffsetX = { -it }
                            )
                        },
                        popEnterTransition = {
                            slideInHorizontally(
                                animationSpec = tween(300),
                                initialOffsetX = { -it }
                            )
                        },
                        popExitTransition = {
                            slideOutHorizontally(
                                animationSpec = tween(300),
                                targetOffsetX = { it }
                            )
                        }
                    ) {
                        composable(MainNavRoute.START_PAGE){
                            StartPage(navController)
                        }
                        composable(MainNavRoute.MAIN_NAV) {
                            IndexPage(navController)
                        }
                        composable(MainNavRoute.EXPRESS_INQUIRY){
                            ExpressInquiryPage(navController)
                        }
                        composable(MainNavRoute.BING_WALLPAPERS){
                            BingWallpapersPage(navController)
                        }
                        composable(MainNavRoute.TRANSLATION){
                            TranslationPage(navController)
                        }
                        composable(MainNavRoute.TIMER_SCREEN){
                            TimerPage()
                        }
                        composable(MainNavRoute.ONE_WORD){
                            OneWordPage()
                        }
                        composable(MainNavRoute.BMI_PAGE){
                            BmiPage()
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MultiBoxTheme {

    }
}