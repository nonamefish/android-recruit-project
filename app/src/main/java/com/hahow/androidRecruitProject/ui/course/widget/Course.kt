package com.hahow.androidRecruitProject.ui.course.widget

import android.widget.Space
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.hahow.androidRecruitProject.domain.model.assignment.Assigner
import com.hahow.androidRecruitProject.domain.model.assignment.Assignment
import com.hahow.androidRecruitProject.domain.model.assignment.AssignmentTimeline
import com.hahow.androidRecruitProject.domain.model.assignment.Rule
import com.hahow.androidRecruitProject.domain.model.course.Course
import com.hahow.androidRecruitProject.domain.model.course.CourseSource
import com.hahow.androidRecruitProject.ui.theme.HahowColor
import com.hahow.androidRecruitProject.ui.theme.HahowTypography
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import com.hahow.androidRecruitProject.R
import com.hahow.androidRecruitProject.util.DateUtil
import kotlin.math.roundToInt

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Course(
    course: Course,
    modifier: Modifier = Modifier
) {
    Row(modifier = Modifier.padding(12.dp)) {

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
                // 左上角課程型態 tag
                if (course.source == CourseSource.TENANT_COURSE) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(
                                Color(0xFF1976D2),
                                RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            "企業課程",
                            color = Color.White,
                            style = HahowTypography.subtitle02
                        )
                    }
                }
                // 右下角訊息 tag
                val assignerName = course.recentStartedAssignment?.assigners?.firstOrNull()?.name
                when {
                    assignerName != null -> {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .background(
                                    HahowColor.green_700,
                                    RoundedCornerShape(4.dp, 0.dp, 4.dp, 0.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "$assignerName 指派",
                                color = Color.White,
                                style = HahowTypography.subtitle02
                            )
                        }
                    }

                    course.lastViewedAt != null -> {
                        val daysAgo = daysAgo(course.lastViewedAt)
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .background(
                                    HahowColor.black_100.copy(alpha = 0.3f),
                                    RoundedCornerShape(8.dp, 0.dp, 8.dp, 0.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                "$daysAgo 天前觀看",
                                color = Color.White,
                                style = HahowTypography.subtitle02
                            )
                        }
                    }
                }
            }
        }


        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {

            Title(
                title = course.title,
                rule = course.recentStartedAssignment?.rule
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                if (course.studiedAt != null) {
                    PassedDate(course.studiedAt)
                }

                // 學習進度條

                Text(
                    text = "${((course.completionPercentage ?: 0f) * 100f).roundToInt()}%",
                    style = HahowTypography.body01,
                    color = HahowColor.gray_500,
                )

                Box(
                    modifier = Modifier.height(4.dp).weight(1f)
                ) {
                    Spacer(
                        modifier = Modifier
                            .background(HahowColor.gray_300, RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )

                    Spacer(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(colors = listOf(HahowColor.green_700, HahowColor.green_500)),
                                shape = RoundedCornerShape(4.dp)
                            )
                            .fillMaxWidth(course.completionPercentage ?: 0f)
                            .fillMaxHeight()
                    )
                }

                // 期限文案
                if (course.studiedAt == null) {
                    DeadLine(
                        dueAt = course.recentStartedAssignment?.timeline?.dueAt,
                        rule = course.recentStartedAssignment?.rule
                    )
                }

                // more icon
                Image(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = "更多選項",
                    colorFilter = ColorFilter.tint(HahowColor.gray_500),
                    modifier = Modifier.size(20.dp)
                )
            }

        }

    }
}


@Composable
fun PassedDate(studiedAt: String) {
    val date = DateUtil.extractDateFromIso(studiedAt)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_check),
            contentDescription = "通過圖示",
            modifier = Modifier
                .size(16.dp)
                .padding(end = 2.dp)
        )
        Text(
            "$date 通過",
            style = HahowTypography.body01,
            color = HahowColor.green_500
        )
    }
}

@Composable
fun DeadLine(dueAt: String?, rule: Rule?) {
    val isCompulsory = rule == Rule.COMPULSORY
    val color = if (isCompulsory) HahowColor.red_900 else HahowColor.gray_500
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val deadlineText = getDeadlineText(dueAt)

        Image(
            painter = painterResource(id = R.drawable.ic_clock),
            contentDescription = "時間圖示",
            colorFilter = ColorFilter.tint(color),
            modifier = Modifier
                .size(16.dp)
                .padding(end = 2.dp)
        )

        Text(deadlineText, style = HahowTypography.body01, color = color)
    }
}

@Composable
fun Title(title: String, rule: Rule?) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // 動態建立 annotatedString 與 inlineContent
        val (annotatedString, inlineContent) = if (rule != null) {
            val tagId = "tag"

            val text = buildAnnotatedString {
                appendInlineContent(tagId, "[tag]") // 這個 [tag] 是 placeholder，不會顯示
                append(title)
            }

            val inline = mapOf(
                tagId to InlineTextContent(
                    Placeholder(
                        width = 42.sp,
                        height = 22.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                    )
                ) {
                    TagText(
                        modifier = Modifier,
                        rule = rule
                    )
                }
            )

            text to inline
        } else {
            // 沒有 rule，直接使用 title，沒有 inlineContent
            buildAnnotatedString { append(title) } to emptyMap()
        }

        Text(
            text = annotatedString,
            inlineContent = inlineContent,
            style = HahowTypography.subtitle01,
            maxLines = 2,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            softWrap = true,
        )
    }
}

@Composable
private fun TagText(modifier: Modifier, rule: Rule) {
    val isCompulsory = rule == Rule.COMPULSORY
    Box(
        modifier = modifier
            .background(
                color = if (isCompulsory) {
                    HahowColor.green_300
                } else {
                    HahowColor.gray_300
                },
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 2.dp, horizontal = 8.dp)
    ) {
        Text(
            if (isCompulsory) "必修" else "推薦",
            color = if (isCompulsory) {
                HahowColor.green_500
            } else {
                HahowColor.gray_800
            },
            style = HahowTypography.body01
        )
    }
}


private fun daysAgo(isoString: String): Long {
    return try {
        // 處理 "2023-10-01T12:00:00Z" 格式
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("UTC")
        val date = format.parse(isoString) ?: return 0L

        val now = Calendar.getInstance().time
        val diffInMillis = date.time - now.time
        TimeUnit.MILLISECONDS.toDays(diffInMillis)
    } catch (e: Exception) {
        0L
    }
}

private fun getDeadlineText(dueAt: String?): String {
    if (dueAt.isNullOrBlank()) return "無期限"

    val days = daysAgo(dueAt)
    return try {

        when {
            days < 0L -> "已逾期"
            days == 0L -> "1 天內截止"
            days in 1..7 -> "截止日剩 $days 天"
            else -> {
                "${DateUtil.extractDateFromIso(dueAt)} 截止"
            }
        }
    } catch (e: Exception) {
        ""
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCourse() {
    val sampleCourse = Course(
        id = "1",
        title = "百萬 YouTuber 攻心剪輯術：後製技巧與 YouTube 經營心法",
        coverImageUrl = "https://example.com/image.jpg",
        totalSeconds = 3600,
        enrollmentsCount = 100,
        averageRating = 4.5f,
        level = "Intermediate",
        completionStatus = "Completed",
        completionPercentage = 0.92f,
        source = CourseSource.TENANT_COURSE,
        studiedAt = null,
        enrolled = true,
        teacher = null,
        recentStartedAssignment = Assignment(
            id = "assignment1",
            title = "第一週作業",
            rule = Rule.COMPULSORY,
            timeline = AssignmentTimeline(
                dueAt = "2023-10-10T12:00:00Z",
                startAt = "2023-10-01T12:00:00Z"
            ),
            assigners = listOf(Assigner(id = "90608", name = "Daisuke")),
            completedAt = null
        ),
        lastViewedAt = "2023-10-05T12:00:00Z",
        accessExpiredReason = null
    )
    Course(sampleCourse)
}