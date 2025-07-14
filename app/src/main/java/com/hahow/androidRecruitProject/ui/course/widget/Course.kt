package com.hahow.androidRecruitProject.ui.course.widget

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
import com.hahow.androidRecruitProject.domain.model.assignment.Assigner
import com.hahow.androidRecruitProject.domain.model.assignment.Assignment
import com.hahow.androidRecruitProject.domain.model.assignment.AssignmentTimeline
import com.hahow.androidRecruitProject.domain.model.assignment.Rule
import com.hahow.androidRecruitProject.domain.model.course.Course
import com.hahow.androidRecruitProject.domain.model.course.CourseSource
import com.hahow.androidRecruitProject.domain.model.course.Teacher
import com.hahow.androidRecruitProject.ui.theme.HahowColor
import com.hahow.androidRecruitProject.ui.theme.HahowTypography
import com.hahow.androidRecruitProject.util.DateUtil
import kotlin.math.abs
import kotlin.math.roundToInt

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Course(
    course: Course,
    modifier: Modifier = Modifier
) {
    Row(modifier = Modifier.padding(12.dp)) {

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

                    CourseInfoTag(
                        modifier = Modifier.align(Alignment.BottomEnd),
                        course = course
                    )
                }
            }

            CourseTypeTag(course.source)
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

                PassedDate(course.studiedAt)

                CourseProgressBar(
                    modifier = Modifier.weight(1f),
                    completionPercentage = course.completionPercentage
                )

                DeadLine(
                    studiedAt = course.studiedAt,
                    dueAt = course.recentStartedAssignment?.timeline?.dueAt,
                    rule = course.recentStartedAssignment?.rule
                )

                // more icon
                Image(
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = stringResource(id = R.string.course_more_icon_desc),
                    colorFilter = ColorFilter.tint(HahowColor.gray_500),
                    modifier = Modifier.size(20.dp)
                )
            }

        }

    }
}


@Composable
fun PassedDate(studiedAt: String?) {
    if (studiedAt == null) return

    val date = DateUtil.extractDateFromIso(studiedAt)
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
            "$date ${stringResource(id = R.string.course_passed)}",
            style = HahowTypography.body01,
            color = HahowColor.green_500
        )
    }
}

@Composable
fun DeadLine(studiedAt: String?, dueAt: String?, rule: Rule?) {
    if (studiedAt != null) return

    val isCompulsory = rule == Rule.COMPULSORY
    val color = if (isCompulsory) HahowColor.red_900 else HahowColor.gray_500
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val deadlineText = getDeadlineText(dueAt)

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
            if (isCompulsory) stringResource(id = R.string.course_rule_compulsory) else stringResource(
                id = R.string.course_rule_elective
            ),
            color = if (isCompulsory) {
                HahowColor.green_500
            } else {
                HahowColor.gray_800
            },
            style = HahowTypography.body01
        )
    }
}

@Composable
private fun CourseInfoTag(
    modifier: Modifier,
    course: Course
) {
    val assignerName = course.recentStartedAssignment?.assigners?.firstOrNull()?.name
    val tagText: String?
    val backgroundColor: Color

    when {
        assignerName != null -> {
            tagText = "$assignerName ${stringResource(id = R.string.course_assigner_tag)}"
            backgroundColor = HahowColor.green_700
        }

        course.lastViewedAt != null -> {
            val daysAgo = abs(DateUtil.daysFromNow(course.lastViewedAt))
            tagText = "$daysAgo ${stringResource(id = R.string.course_last_viewed_tag)}"
            backgroundColor = HahowColor.black_100.copy(alpha = 0.3f)
        }

        else -> return
    }

    Box(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(topStart = 4.dp, bottomStart = 4.dp))
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
private fun CourseTypeTag(source: CourseSource?) {
    if (source == CourseSource.TENANT_COURSE) {
        Column(
            modifier = Modifier.offset(x = (-4).dp, y = 4.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        HahowColor.blue_700,
                        RoundedCornerShape(4.dp, 4.dp, 4.dp, 0.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    stringResource(id = R.string.course_type_tenant),
                    color = Color.White,
                    style = HahowTypography.subtitle02
                )
            }
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
                    color = HahowColor.blue_900
                )
            }
        }
    }
}

@Composable
private fun CourseProgressBar(
    modifier: Modifier,
    completionPercentage: Float?
) {
    Row(modifier) {
        val percent = ((completionPercentage ?: 0f) * 100f).roundToInt()
        Text(
            text = "$percent%",
            style = HahowTypography.body01,
            color = HahowColor.gray_500,
        )
        Box(
            modifier = Modifier
                .height(4.dp)
                .weight(1f)
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
                        brush = Brush.horizontalGradient(
                            colors = listOf(HahowColor.green_700, HahowColor.green_500)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .fillMaxWidth(completionPercentage ?: 0f)
                    .fillMaxHeight()
            )
        }
    }

}


@Composable
private fun getDeadlineText(dueAt: String?): String {
    if (dueAt.isNullOrBlank()) return "無期限"

    val days = DateUtil.daysFromNow(dueAt)
    return when {
        days < 0L -> stringResource(id = R.string.course_deadline_overdue)
        days == 0L -> stringResource(id = R.string.course_deadline_within_one_day)
        days in 1..7 -> stringResource(id = R.string.course_deadline_days_left, days)
        else -> "${DateUtil.extractDateFromIso(dueAt)} ${stringResource(id = R.string.course_deadline_end)}"
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCompulsoryCourse() {
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
        studiedAt = "2022-02-23T10:34:04Z",
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

@Preview(showBackground = true)
@Composable
fun PreviewElectiveCourse() {
    val sampleCourse = Course(
        id = "1",
        title = "CEPT-B2B跨境電商技能認證-前言",
        coverImageUrl = "https://imgur.com/AIFqs1I.png",
        totalSeconds = 1329,
        enrollmentsCount = 3,
        averageRating = null,
        level = "BEGINNER",
        completionStatus = "STUDYING",
        completionPercentage = 0f,
        source = CourseSource.UNLIMITED_PRODUCT,
        studiedAt = null,
        enrolled = true,
        teacher = Teacher("賴順賢 Gary"),
        recentStartedAssignment = Assignment(
            id = "assignment1",
            title = "test_assignment_240516_01",
            rule = Rule.ELECTIVE,
            timeline = AssignmentTimeline(
                dueAt = "2024-05-19T15:59:53Z",
                startAt = null
            ),
            assigners = listOf(Assigner(id = "90608", name = "Daisuke")),
            completedAt = null
        ),
        lastViewedAt = null,
        accessExpiredReason = null
    )
    Course(sampleCourse)
}

@Preview(showBackground = true)
@Composable
fun PreviewNormalCourse() {
    val sampleCourse = Course(
        id = "1",
        title = "不學 Coding 的邏輯思考，工程溝通更容易",
        coverImageUrl = "https://imgur.com/AIFqs1I.png",
        totalSeconds = 1329,
        enrollmentsCount = 3,
        averageRating = null,
        level = "BEGINNER",
        completionStatus = "STUDYING",
        completionPercentage = 0.56f,
        source = null,
        studiedAt = null,
        enrolled = true,
        teacher = Teacher("賴順賢 Gary"),
        recentStartedAssignment = null,
        lastViewedAt = "2024-04-16T02:14:50Z",
        accessExpiredReason = null
    )
    Course(sampleCourse)
}