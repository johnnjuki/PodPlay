package com.podplay.android.ui.screens.subscriptions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.podplay.android.R

@Composable
fun SubscriptionsRoute() {
    SubscriptionsScreen()
}

@Composable
fun SubscriptionsScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(id = R.string.subscriptions))
    }
}