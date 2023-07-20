package com.bumba.qrcode.view.icon

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import com.bumba.qrcode.util.Platform
import com.bumba.qrcode.util.getPlatform

val IconShare = materialIcon(name = "Filled.Share") {
    if (getPlatform() == Platform.ANDROID)
        materialPath {
            moveTo(18.0f, 16.08f)
            curveToRelative(-0.76f, 0.0f, -1.44f, 0.3f, -1.96f, 0.77f)
            lineTo(8.91f, 12.7f)
            curveToRelative(0.05f, -0.23f, 0.09f, -0.46f, 0.09f, -0.7f)
            reflectiveCurveToRelative(-0.04f, -0.47f, -0.09f, -0.7f)
            lineToRelative(7.05f, -4.11f)
            curveToRelative(0.54f, 0.5f, 1.25f, 0.81f, 2.04f, 0.81f)
            curveToRelative(1.66f, 0.0f, 3.0f, -1.34f, 3.0f, -3.0f)
            reflectiveCurveToRelative(-1.34f, -3.0f, -3.0f, -3.0f)
            reflectiveCurveToRelative(-3.0f, 1.34f, -3.0f, 3.0f)
            curveToRelative(0.0f, 0.24f, 0.04f, 0.47f, 0.09f, 0.7f)
            lineTo(8.04f, 9.81f)
            curveTo(7.5f, 9.31f, 6.79f, 9.0f, 6.0f, 9.0f)
            curveToRelative(-1.66f, 0.0f, -3.0f, 1.34f, -3.0f, 3.0f)
            reflectiveCurveToRelative(1.34f, 3.0f, 3.0f, 3.0f)
            curveToRelative(0.79f, 0.0f, 1.5f, -0.31f, 2.04f, -0.81f)
            lineToRelative(7.12f, 4.16f)
            curveToRelative(-0.05f, 0.21f, -0.08f, 0.43f, -0.08f, 0.65f)
            curveToRelative(0.0f, 1.61f, 1.31f, 2.92f, 2.92f, 2.92f)
            curveToRelative(1.61f, 0.0f, 2.92f, -1.31f, 2.92f, -2.92f)
            reflectiveCurveToRelative(-1.31f, -2.92f, -2.92f, -2.92f)
            close()
        }
    else
        materialPath {
            moveTo(16.0f, 5.0f)
            lineToRelative(-1.42f, 1.42f)
            lineToRelative(-1.59f, -1.59f)
            lineTo(12.99f, 16.0f)
            horizontalLineToRelative(-1.98f)
            lineTo(11.01f, 4.83f)
            lineTo(9.42f, 6.42f)
            lineTo(8.0f, 5.0f)
            lineToRelative(4.0f, -4.0f)
            lineToRelative(4.0f, 4.0f)
            close()
            moveTo(20.0f, 10.0f)
            verticalLineToRelative(11.0f)
            curveToRelative(0.0f, 1.1f, -0.9f, 2.0f, -2.0f, 2.0f)
            lineTo(6.0f, 23.0f)
            curveToRelative(-1.11f, 0.0f, -2.0f, -0.9f, -2.0f, -2.0f)
            lineTo(4.0f, 10.0f)
            curveToRelative(0.0f, -1.11f, 0.89f, -2.0f, 2.0f, -2.0f)
            horizontalLineToRelative(3.0f)
            verticalLineToRelative(2.0f)
            lineTo(6.0f, 10.0f)
            verticalLineToRelative(11.0f)
            horizontalLineToRelative(12.0f)
            lineTo(18.0f, 10.0f)
            horizontalLineToRelative(-3.0f)
            lineTo(15.0f, 8.0f)
            horizontalLineToRelative(3.0f)
            curveToRelative(1.1f, 0.0f, 2.0f, 0.89f, 2.0f, 2.0f)
            close()
        }
}
