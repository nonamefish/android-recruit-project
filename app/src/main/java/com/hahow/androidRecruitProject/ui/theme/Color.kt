package com.hahow.androidRecruitProject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


/**
 * 顏色命名邏輯：color_code，例如：green_500 代表綠色的500色階
 * background 屬性會根據當前主題（淺色或深色）返回對應的背景顏色
 */
object HahowColor {
    val green_500 = Color(0xff0D331C)
    val green_300 = Color(0xff0D331C)
    val white_100 = Color(0xffFFFFFF)
    val gray_100 = Color(0xff121212)
    val red_900 = Color(0xffB01D2D)

    val background
        @Composable get() = getColor(white_100, gray_100)
}

val LightColorScheme = lightColorScheme(
    primary = HahowColor.green_500,
    secondary = HahowColor.green_500,
    error = HahowColor.red_900,
    background = Color.White,
    surface = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = HahowColor.green_300,
    secondary = HahowColor.green_300,
    error = HahowColor.red_900,
    background = HahowColor.gray_100,
    surface = HahowColor.gray_100
)

@Composable
fun getColor(lightColor: Color, darkColor: Color): Color {
    return if (isSystemInDarkTheme()) lightColor else darkColor
}