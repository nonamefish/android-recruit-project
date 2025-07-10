package `in`.hahow.androidRecruitProject.ui.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    viewModel: BaseViewModel,
    onEventCollect: (event: BaseViewModel.BaseEvent) -> Unit = {},
    shouldCollectEvents: Boolean = true,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable BoxScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        if (shouldCollectEvents) {
            viewModel.eventsFlow.collect { event ->
                when (event) {
//                    is BaseViewModel.Event.ShowSnackBar -> showSnackBar(
//                        scope = scope,
//                        snackBarData = event.snackBarData
//                    )

                }
                onEventCollect(event)
            }
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        content()
    }
}