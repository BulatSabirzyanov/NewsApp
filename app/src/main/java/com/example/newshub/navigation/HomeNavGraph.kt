package com.example.newshub.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.HomeScreenDirections
import com.example.article_details.ui.ArticleDetailsScreen
import com.example.common.mvvm.SharedViewModel
import com.example.home.HomeScreen
import com.example.source_content.ui.SourceContentScreen

fun NavGraphBuilder.homeNavGraph(sharedViewModel: SharedViewModel) {

    navigation(
        route = HomeScreenDirections.root.destination,
        startDestination = HomeScreenDirections.homeScreen.destination
    ){
        composable(HomeScreenDirections.homeScreen.destination) {
            HomeScreen(sharedViewModel)
        }
        composable(HomeScreenDirections.detailsScreen().destination) {
            ArticleDetailsScreen(sharedViewModel)
        }
        composable(HomeScreenDirections.sourceContentScreen.destination) {
            SourceContentScreen(sharedViewModel)
        }
    }
}
