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
    val green_800 = Color(0xff0d331c)
    val green_700 = Color(0xff2d5a5a)
    val green_500 = Color(0xff00c6b3)
    val green_300 = Color(0xfff0f8f8)
    val white_100 = Color(0xffffffff)
    val gray_900 = Color(0xff1e201f)
    val gray_800 = Color(0xff757575)
    val gray_500 = Color(0xffa5a6a5)
    val gray_300 = Color(0xffe8e8e8)
    val red_900 = Color(0xffDA3F48)
    val black_100 = Color(0xff000000)

    val background
        @Composable get() = getColor(white_100, gray_900)
}

val LightColorScheme = lightColorScheme(
    primary = HahowColor.green_800,
    secondary = HahowColor.green_800,
    error = HahowColor.red_900,
    background = Color.White,
    surface = Color.White
)

val DarkColorScheme = darkColorScheme(
    primary = HahowColor.green_300,
    secondary = HahowColor.green_300,
    error = HahowColor.red_900,
    background = HahowColor.gray_900,
    surface = HahowColor.gray_900
)

@Composable
fun getColor(lightColor: Color, darkColor: Color): Color {
    return if (isSystemInDarkTheme()) lightColor else darkColor
}