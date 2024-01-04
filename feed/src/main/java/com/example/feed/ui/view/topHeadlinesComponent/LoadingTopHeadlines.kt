package com.example.feed.ui.view.topHeadlinesComponent

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingTopheadlines(modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = 10.dp,
        modifier = Modifier
            .height(250.dp)
            .padding(start = 7.dp, end = 7.dp)
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MaterialTheme.colors.primaryVariant)
        }
    }
}

@Composable
fun ErrorLoadingTopheadlines(modifier: Modifier, error: String, refresh: () -> Unit) {
    Card(
        shape = RoundedCornerShape(18.dp),
        elevation = 10.dp,
        modifier = Modifier
            .height(250.dp)
            .padding(start = 7.dp, end = 7.dp)
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = error, textAlign = TextAlign.Center,
                    style = TextStyle(color = MaterialTheme.colors.primaryVariant, fontSize = 16.sp)
                )
                Spacer(Modifier.height(3.dp))
                Button(shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colors.primaryVariant),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    onClick = {
                        refresh()
                    }) {
                    Text(
                        text = "Refresh",
                        style = TextStyle(
                            color = MaterialTheme.colors.primaryVariant,
                            fontSize = 16.sp
                        )
                    )
                }
            }
        }
    }
}

