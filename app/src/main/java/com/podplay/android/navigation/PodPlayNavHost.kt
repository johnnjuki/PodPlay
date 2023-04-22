package com.podplay.android.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.podplay.android.ui.screens.episode_details.EpisodeDetailsRoute
import com.podplay.android.ui.screens.home.HomeRoute
import com.podplay.android.ui.screens.podcast_details.PodcastDetailsRoute
import com.podplay.android.ui.screens.search.SearchRoute
import com.podplay.android.util.Constants.FEED_TITLE_KEY
import com.podplay.android.util.Constants.FEED_URL_KEY
import com.podplay.android.util.Constants.GUID_KEY
import com.podplay.android.util.Constants.IMAGE_URL_KEY
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun PodPlayNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route,
    ) {

        // HOME
        composable(Screens.Home.route) {
            HomeRoute(onSearchBarClick = {
                navController.navigate(Screens.Search.route)
            })
        }

        // Search
        composable(Screens.Search.route) {
            SearchRoute(
                onSearchResultClick = { feedUrl ->
                    val encodedUrl = URLEncoder.encode(feedUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate(Screens.PodcastDetails.replaceFeedUrl(encodedUrl))
                },
            )
        }

        // Podcast Details
        composable(
            route = Screens.PodcastDetails.route,
            arguments = listOf(navArgument(FEED_URL_KEY) { type = NavType.StringType })
        ) { navBackStackEntry ->
            val feedUrl = navBackStackEntry.arguments?.getString(FEED_URL_KEY) ?: ""
            PodcastDetailsRoute(
                feedUrl = feedUrl,
                navigateUp = { navController.navigateUp() },
                onEpisodeClick = { guid, imageUrl, feedTitle ->
                    val encodedUrl = URLEncoder.encode(imageUrl, StandardCharsets.UTF_8.toString())
                    navController.navigate(
                        Screens.EpisodeDetails.replaceRoute(
                            guid,
                            encodedUrl,
                            feedTitle
                        )
                    )
                }
            )
        }

        // Episode Details
        composable(
            route = Screens.EpisodeDetails.route,
            arguments = listOf(
                navArgument(GUID_KEY) { type = NavType.StringType },
                navArgument(FEED_TITLE_KEY) { type = NavType.StringType },
                navArgument(IMAGE_URL_KEY) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val guid = navBackStackEntry.arguments?.getString(GUID_KEY) ?: ""
            val feedTitle = navBackStackEntry.arguments?.getString(FEED_TITLE_KEY) ?: ""
            val imageUrl = navBackStackEntry.arguments?.getString(IMAGE_URL_KEY) ?: ""

            Log.d("TAGGGG", "$guid, $feedTitle, $imageUrl")

            EpisodeDetailsRoute(
                guid = guid,
                feedTitle = feedTitle,
                imageUrl = imageUrl,
                navigateUp = { navController.navigateUp() })
        }

    }
}