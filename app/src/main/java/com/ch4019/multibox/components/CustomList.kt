package com.ch4019.multibox.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomList(
    @DrawableRes icon : Int ,
    title : String,
    route : String,
    ratio : Float,
    onClick: () -> Unit,
) {
    Card(
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth(),
            //.padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClick
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio)
        )
        Column (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}