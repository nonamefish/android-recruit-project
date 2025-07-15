package com.hahow.androidRecruitProject.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.hahow.androidRecruitProject.ui.theme.HahowColor
import com.hahow.androidRecruitProject.ui.theme.HahowTypography

@Composable
fun BoxButton(
    modifier: Modifier = Modifier,
    txtModifier: Modifier = Modifier,
    text: String = "",
    enable: Boolean = true,
    onClick: (() -> Unit) = {},
    textStyle: TextStyle = HahowTypography.subtitle02,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    content: @Composable (() -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        border = BorderStroke(
            width = 1.dp,
            color = if (enable) Color.Transparent else HahowColor.hahow_gray_800
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = HahowColor.hahow_gray_800,
            disabledContainerColor = HahowColor.hahow_gray_300
        ),
        shape = RoundedCornerShape(7.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(0.dp),
        modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
        enabled = enable,
        contentPadding = contentPadding
    ) {
        if (content != null) {
            content()
        } else {
            Text(
                text = text,
                textAlign = TextAlign.Start,
                modifier = txtModifier,
                style = textStyle.copy(
                    color = HahowColor.hahow_gray_300
                )
            )
        }
    }
}