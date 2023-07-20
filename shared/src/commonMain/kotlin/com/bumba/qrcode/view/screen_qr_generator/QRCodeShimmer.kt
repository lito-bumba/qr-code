package com.bumba.qrcode.view.screen_qr_generator

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun QRCodeShimmer() {
    val shimmerColorShades = listOf(
        Color.Black.copy(0.5f),
        Color.LightGray.copy(0.2f),
        Color.Black.copy(0.5f),
    )

    val transition = rememberInfiniteTransition()
    val transitionAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = shimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(transitionAnim, transitionAnim)
    )

    ShimmerItem(brush = brush)
}

@Composable
private fun ShimmerItem(brush: Brush) {
    Surface(
        color = Color.Transparent,
        modifier = Modifier.alpha(.5f)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(300.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = brush)
            )
            Spacer(
                modifier = Modifier
                    .fillMaxSize(.8f)
                    .background(brush = brush)
            )
        }
    }
}
