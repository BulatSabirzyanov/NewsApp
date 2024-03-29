package com.example.favorites.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.mvvm.BaseViewState
import com.example.common.mvvm.SharedViewModel
import com.example.common.widget.CenteredText
import com.example.common.widget.LottieErrorView
import com.example.common.widget.ProgressIndicator
import com.example.favorites.ui.component.FavoriteContent
import timber.log.Timber

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    sharedViewModel: SharedViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val uiStateFavorite = viewModel.uiStateFavorite.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.background,
                elevation = 6.dp,
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    modifier = Modifier.height(51.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.h2,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        text = "Favorites",
                    )
                }

            }
        },
    )
    {
        Box(modifier = Modifier.padding(paddingValues = it).fillMaxSize()) {
            when (uiState.value) {
                is BaseViewState.Data -> {
                    FavoriteContent(uiStateFavorite.value, viewModel, sharedViewModel)
                }
                is BaseViewState.Empty -> {
                    CenteredText("No Favorites")
                }
                is BaseViewState.Error -> {
                    Timber.e((uiState.value as BaseViewState.Error).throwable.toString())
                    LottieErrorView(
                        action = {
                            viewModel.onTriggerEvent(FavoriteEvent.RefreshScreen)
                        },
                        errorMessage = (uiState.value as BaseViewState.Error).throwable.message.toString()
                    )
                }
                is BaseViewState.Loading -> {
                    ProgressIndicator()
                }
            }
        }
    }
}
