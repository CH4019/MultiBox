package com.ch4019.multibox.ui.screen.index

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ch4019.multibox.R
import com.ch4019.multibox.components.CustomList
import com.ch4019.multibox.components.DragHandle
import com.ch4019.multibox.config.MainNavRoute
import com.ch4019.multibox.model.PagesList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexPage(
    navController: NavHostController,
) {
    val density = LocalDensity.current
    val sheetState = rememberModalBottomSheetState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val height = with(density) {WindowInsets.statusBars.getTop(density).toDp()}

    val listState = rememberLazyStaggeredGridState()
    var isScroll by remember{ mutableStateOf(false)}
    val showBottomSheet = remember { mutableStateOf(false) }
    LaunchedEffect(listState.firstVisibleItemScrollOffset) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }.collect { offset ->
            isScroll = offset > 0
        }
    }

    LaunchedEffect(sheetState.currentValue){
        withContext(Dispatchers.IO){
            if (sheetState.currentValue == SheetValue.Hidden){
                showBottomSheet.value = false
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = sheetState.currentValue == SheetValue.Hidden,
        modifier = Modifier.fillMaxSize(),
        drawerContent = {
            ModalDrawerSheet(
                windowInsets = WindowInsets(top = WindowInsets.statusBars.getTop(density)),
            ) {
                Column (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                ){
                    Text(text = "测试")
                }
            }
        }
    ) {
        Scaffold (
            topBar = { TopBar(isScroll = isScroll,drawerState,showBottomSheet)}
        ){
            IndexView(it, navController, listState)
        }

    }

    if (showBottomSheet.value){
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState,
            dragHandle = { DragHandle(
                dragHandleVerticalPadding = height
            )},
            windowInsets = WindowInsets(top = 0.dp)
        ) {
            Column (
                modifier =  Modifier.fillMaxSize()
            ){

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    isScroll: Boolean,
    drawerState: DrawerState,
    showBottomSheet: MutableState<Boolean>
) {
    val scope = rememberCoroutineScope()
    CenterAlignedTopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                scope.launch { drawerState.open()}
            }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
        },
        actions = {
                  IconButton(onClick = {
                      showBottomSheet.value = true
                  }) {
                      Icon(
                          imageVector = Icons.Default.Menu,
                          contentDescription = null
                      )
                  }
        },
        colors = if (isScroll){
            TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,

            )
        }else{
            TopAppBarDefaults.topAppBarColors()
        },
        title = { Text(text = "测试")},
    )

}

@Composable
fun IndexView(
    paddingValues: PaddingValues,
    navController: NavHostController,
    listState : LazyStaggeredGridState,
) {

    val pages = listOf(
        PagesList("翻译", MainNavRoute.TRANSLATION),
        PagesList("计时", MainNavRoute.TIMER_SCREEN),
        PagesList("一言", MainNavRoute.ONE_WORD),
        PagesList("BMI", MainNavRoute.BMI_PAGE),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
        PagesList("测试1", MainNavRoute.TRANSLATION),
    )
    val coroutineScope = rememberCoroutineScope()
    val hapticFeedback = LocalHapticFeedback.current


    Box (
        modifier = Modifier
            .padding(paddingValues)
    ){
        LazyVerticalStaggeredGrid(
            state = listState,
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp, 8.dp),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
        ){
            itemsIndexed(pages) { index, item ->
                CustomList(
                    icon = R.drawable.multi_box,
                    title = " $index ${item.name}",
                    route = "",
                    ratio = 3f,
                    onClick = {
                        navController.navigate(item.route){
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
        Box (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd,
        ){
            FloatingActionButton(
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    coroutineScope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    contentDescription = null
                )
            }
        }
    }
}