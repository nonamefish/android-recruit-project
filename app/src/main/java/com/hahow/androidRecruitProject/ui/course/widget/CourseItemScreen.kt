package com.hahow.androidRecruitProject.ui.course.widget

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.hahow.androidRecruitProject.R
import com.hahow.androidRecruitProject.ui.course.CourseUiItem
import com.hahow.androidRecruitProject.ui.course.TagType
import com.hahow.androidRecruitProject.ui.theme.HahowColor
import com.hahow.androidRecruitProject.ui.theme.HahowTypography

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CourseItemScreen(
    course: CourseUiItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.padding(12.dp)
    ) {
        Box {
            Card(
                modifier = modifier.clip(RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(120.dp)
                        .height(80.dp)
                ) {
                    GlideImage(
                        model = course.coverImageUrl,
                        contentDescription = course.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )

                    if (course.imageTagText != null) {
                        ImageTag(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            tagText = course.imageTagText,
                            tagType = course.imageTagType
                        )
                    }
                }
            }

            if (course.imageBadgeText != null) {
                ImageBadge(course.imageBadgeText)
            }
        }
        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Title(
                title = course.title,
                titleBadgeText = course.titleBadgeText,
                isCompulsory = course.isCompulsory
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (course.studiedDateText != null) {
                    PassedDate(course.studiedDateText)
                }

                CourseProgressBar(
                    modifier = Modifier.weight(1f),
                    progressBarText = course.progressBarText,
                    progressPercent = course.progressPercent
                )

                if (course.deadlineText != null) {
                    DeadLine(
                        deadlineText = course.deadlineText,
                        isCompulsory = course.isCompulsory
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = stringResource(id = R.string.course_more_icon_desc),
                    colorFilter = ColorFilter.tint(HahowColor.hahow_gray_500),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun PassedDate(studiedDateText: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = stringResource(id = R.string.course_passed_icon_desc),
            modifier = Modifier
                .size(16.dp)
                .padding(end = 2.dp)
        )
        Text(
            studiedDateText,
            style = HahowTypography.body01,
            color = HahowColor.hahow_green_500
        )
    }
}

@Composable
fun DeadLine(deadlineText: String, isCompulsory: Boolean) {
    val color = if (isCompulsory) HahowColor.hahow_red_900 else HahowColor.hahow_gray_500

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = stringResource(id = R.string.course_deadline_icon_desc),
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier
                .size(16.dp)
                .padding(end = 2.dp)
        )
        Text(deadlineText, style = HahowTypography.body01, color = color)
    }
}

@Composable
fun Title(title: String, titleBadgeText: String?, isCompulsory: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val (annotatedString, inlineContent) = if (titleBadgeText != null) {
            val tagId = "tag"
            val annotatedTitle = buildAnnotatedString {
                appendInlineContent(tagId, "[tag]")
                append(title)
            }
            val inline = mapOf(
                tagId to titleBadgeInlineContent(titleBadgeText, isCompulsory)
            )
            annotatedTitle to inline
        } else {
            buildAnnotatedString { append(title) } to emptyMap()
        }

        Text(
            text = annotatedString,
            inlineContent = inlineContent,
            style = HahowTypography.subtitle01,
            maxLines = 2,
            textAlign = TextAlign.Start,
            color = HahowColor.hahow_black_100,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
        )
    }
}

@Composable
fun titleBadgeInlineContent(titleBadgeText: String, isCompulsory: Boolean): InlineTextContent =
    InlineTextContent(
        Placeholder(
            width = 42.sp,
            height = 22.sp,
            placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
        )
    ) {
        TitleBadge(
            titleBadgeText = titleBadgeText,
            isCompulsory = isCompulsory
        )
    }

@Composable
private fun TitleBadge(titleBadgeText: String, isCompulsory: Boolean) {
    Box(
        modifier = Modifier
            .background(
                color = if (isCompulsory) HahowColor.hahow_green_300 else HahowColor.hahow_gray_300,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 2.dp, horizontal = 8.dp)
    ) {
        Text(
            titleBadgeText,
            color = if (isCompulsory) HahowColor.hahow_green_500 else HahowColor.hahow_gray_800,
            style = HahowTypography.body01
        )
    }
}

@Composable
private fun ImageTag(
    modifier: Modifier,
    tagText: String,
    tagType: TagType?
) {
    if (tagType == null) return
    Box(
        modifier = modifier
            .background(
                if (tagType == TagType.Assigner) {
                    HahowColor.hahow_green_800
                } else {
                    HahowColor.black_100.copy(alpha = 0.3f)
                },
                RoundedCornerShape(topStart = 4.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = tagText,
            color = HahowColor.white_100,
            style = HahowTypography.subtitle02
        )
    }
}

@Composable
private fun ImageBadge(typeTagText: String) {
    Column(
        modifier = Modifier.offset(x = (-4).dp, y = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    HahowColor.hahow_blue_700,
                    RoundedCornerShape(4.dp, 4.dp, 4.dp, 0.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                typeTagText,
                color = Color.White,
                style = HahowTypography.subtitle02
            )
        }

        val triangleColor = HahowColor.hahow_blue_900
        Canvas(
            modifier = Modifier
                .width(4.dp)
                .height(4.dp)
        ) {
            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                close()
            }
            drawPath(
                path = path,
                color = triangleColor
            )
        }
    }
}

@Composable
private fun CourseProgressBar(
    modifier: Modifier,
    progressBarText: String,
    progressPercent: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = progressBarText,
            style = HahowTypography.body01,
            color = HahowColor.hahow_gray_500,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Box(
            modifier = Modifier
                .height(4.dp)
                .weight(1f)
        ) {
            Spacer(
                modifier = Modifier
                    .background(HahowColor.hahow_gray_300, RoundedCornerShape(4.dp))
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            Spacer(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(HahowColor.hahow_green_700, HahowColor.hahow_green_500)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxWidth(progressPercent / 100f)
                    .fillMaxHeight()
            )
        }
    }
}


@Preview(showBackground = true, name = "Normal - Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Normal - Dark")
@Composable
fun PreviewCompulsoryCourse() {
    val sampleCourse = CourseUiItem(
        id = "1",
        title = "百萬 YouTuber 攻心剪輯術：後製技巧與 YouTube 經營心法",
        coverImageUrl = "https://example.com/image.jpg",
        progressPercent = 92,
        studiedDateText = "2022-02-23 通過",
        progressBarText = "92%",
        deadlineText = null,
        isCompulsory = true,
        imageBadgeText = "企業課程",
        imageTagType = TagType.Assigner,
        imageTagText = "Daisuke 指派",
        titleBadgeText = "必修"
    )
    CourseItemScreen(sampleCourse)
}

@Preview(showBackground = true, name = "Normal - Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Normal - Dark")
@Composable
fun PreviewElectiveCourse() {
    val sampleCourse = CourseUiItem(
        id = "2",
        title = "CEPT-B2B跨境電商技能認證-前言",
        coverImageUrl = "https://imgur.com/AIFqs1I.png",
        progressPercent = 0,
        studiedDateText = null,
        progressBarText = "0%",
        deadlineText = "1 天內截止",
        isCompulsory = false,
        imageBadgeText = null,
        imageTagType = TagType.Assigner,
        imageTagText = "Daisuke 指派",
        titleBadgeText = "推薦"
    )
    CourseItemScreen(sampleCourse)
}

@Preview(showBackground = true, name = "Normal - Light")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Normal - Dark")
@Composable
fun PreviewNormalCourse() {
    val sampleCourse = CourseUiItem(
        id = "3",
        title = "不學 Coding 的邏輯思考，工程溝通更容易",
        coverImageUrl = "https://imgur.com/AIFqs1I.png",
        progressPercent = 56,
        studiedDateText = null,
        progressBarText = "56%",
        deadlineText = "無期限",
        isCompulsory = false,
        imageBadgeText = null,
        imageTagType = TagType.LastViewed,
        imageTagText = "3 天前觀看",
        titleBadgeText = null
    )
    CourseItemScreen(sampleCourse)
}