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
    val hahow_green_800 @Composable get() = getColor(green_800, green_600)
    val hahow_green_700 @Composable get() = getColor(green_700, green_600)
    val hahow_green_500 @Composable get() = getColor(green_600, green_500)
    val hahow_green_300 @Composable get() = getColor(green_500, green_700)
    val hahow_white_100 @Composable get() = getColor(white_100, black_100)
    val hahow_gray_800 @Composable get() = getColor(gray_800, gray_600)
    val hahow_gray_500 @Composable get() = getColor(gray_700, gray_800)
    val hahow_gray_300 @Composable get() = getColor(gray_600, gray_800)
    val hahow_red_900 @Composable get() = getColor(red_900, orange_900)
    val hahow_blue_900 @Composable get() = getColor(blue_900, blue_800)
    val hahow_blue_700 @Composable get() = getColor(blue_800, blue_800)
    val hahow_black_100 @Composable get() = getColor(black_100, white_100)

    val background
        @Composable get() = getColor(white_100, gray_900)

    val green_800 = Color(0xff0d331c)
    val green_700 = Color(0xff2d5a5a)
    val green_600 = Color(0xff00c6b3)
    val green_500 = Color(0xfff0f8f8)

    val white_100 = Color(0xffffffff)
    val gray_900 = Color(0xff424242)
    val gray_800 = Color(0xff757575)
    val gray_700 = Color(0xffa5a6a5)
    val gray_600 = Color(0xffe8e8e8)

    val red_900 = Color(0xffda3f48)
    val orange_900 = Color(0xffa0522d)
    val blue_900 = Color(0xff12172b)
    val blue_800 = Color(0xff1976d2)
    val black_100 = Color(0xff000000)
}

val LightColorScheme = lightColorScheme(
    primary = HahowColor.green_600,
    secondary = HahowColor.green_600,
    error = HahowColor.red_900,
    background = HahowColor.white_100,
    surface = HahowColor.white_100
)

val DarkColorScheme = darkColorScheme(
    primary = HahowColor.green_800,
    secondary = HahowColor.green_800,
    error = HahowColor.orange_900,
    background = HahowColor.gray_900,
    surface = HahowColor.gray_900
)

@Composable
fun getColor(lightColor: Color, darkColor: Color): Color {
    return if (isSystemInDarkTheme()) darkColor else lightColor
}