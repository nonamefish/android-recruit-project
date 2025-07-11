package com.hahow.androidRecruitProject.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

/***
 * 不照 material3 的規範來定義 typography，因為字型類別很多時會不夠用
 * 這邊只定義了三個字型類別，實際上可以依照需求增加更多
 */
object HahowTypography {

    val heading01: TextStyle
        @Composable get() = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center
        )

    val subtitle01: TextStyle
        @Composable get() = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center
        )

    val subtitle02: TextStyle
        @Composable get() = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight(600),
            textAlign = TextAlign.Center
        )

    val body01: TextStyle
        @Composable get() = TextStyle(
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

    val body02: TextStyle
        @Composable get() = TextStyle(
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )
}