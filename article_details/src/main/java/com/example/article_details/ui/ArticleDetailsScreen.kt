package com.example.article_details.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.mvvm.SharedViewModel

@Composable
fun ArticleDetailsScreen(
    sharedViewModel: SharedViewModel,
    viewModel: ArticleDetailsViewModel = hiltViewModel()
) {
    val article = sharedViewModel.article.value
    DetailsContent(article,viewModel)
}
