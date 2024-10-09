package com.github.enaknalla.mediarrtv.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.tv.material3.Border
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.CompactCard
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import coil.compose.AsyncImage
import com.github.enaknalla.mediarrtv.domain.MediaItem
import com.github.enaknalla.mediarrtv.domain.MediaList

data class ButtonProps(
    val text: String,
    val onClick: (item: MediaItem) -> Unit
)

data class ButtonCallbacks(
    val play: ((item: MediaItem) -> Unit)?,
    val markAsWatched: ButtonProps?,
    val goToDetails: ButtonProps?,
    val favorite: ButtonProps?
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ImmersiveList(
    data: List<MediaList>,
    btnCallbacks: ButtonCallbacks? = null,
    headerItem: MediaItem? = null
) {
    var selected by remember { mutableStateOf(headerItem ?: data.first().items.first()) }

    ConstraintLayout {
        val (header, list) = createRefs()

        // background
        AsyncImage(
            model = "https://image.tmdb.org/t/p/original${selected.banner}",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
        Box(
            Modifier
                .fillMaxSize()
                .immersiveListGradient()
        )

        // header
        Header(selected, header, list, btnCallbacks)

        MediaList(data, header, list) {
            if (headerItem == null) selected = it
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ConstraintLayoutScope.MediaList(
    data: List<MediaList>,
    header: ConstrainedLayoutReference,
    list: ConstrainedLayoutReference,
    onFocus: (item: MediaItem) -> Unit
) {
    val firstChildFr = remember { FocusRequester() }

    LazyColumn(modifier = Modifier
        .focusRestorer { firstChildFr }
        .constrainAs(list) {
            top.linkTo(header.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
        }
        .fillMaxHeight(0.5f)
        .padding(bottom = 20.dp)
    ) {
        items(data) { item ->
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(16.dp)
            )

            LazyRow(
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .focusRestorer { firstChildFr },
                contentPadding = PaddingValues(start = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                itemsIndexed(item.items) { index, card ->
                    Card(index, card, firstChildFr) {
                        onFocus(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun ConstraintLayoutScope.Header(
    item: MediaItem,
    header: ConstrainedLayoutReference,
    list: ConstrainedLayoutReference,
    btnCallbacks: ButtonCallbacks?
) {
    Box(modifier = Modifier
        .constrainAs(header) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(list.top)
        }
        .fillMaxHeight(0.5f)
        .fillMaxWidth(0.6f)
        .padding(top = 8.dp, start = 28.dp)
    ) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )

            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(bottom = 16.dp),
            )

            Text(
                text = item.info1!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Text(
                text = item.info2!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.8f),
                modifier = Modifier.padding(bottom = 18.dp)
            )

            Text(
                text = item.description!!,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            btnCallbacks?.let {
                Row(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    it.play?.let {
                        Btn(
                            onClick = { it(item) }) {
                            Text(text = "Play")
                        }
                    }

                    it.markAsWatched?.let {
                        Btn(
                            onClick = { it.onClick(item) }) {
                            Text(text = it.text)
                        }
                    }

                    it.goToDetails?.let {
                        Btn(
                            onClick = { it.onClick(item) }) {
                            Text(text = it.text)
                        }
                    }

                    it.favorite?.let {
                        Btn(
                            onClick = { it.onClick(item) }) {
                            Text(text = it.text)
                        }
                    }
                }
            }
        }
    }

}

@Composable
private fun Card(
    index: Int,
    item: MediaItem,
    focusRequester: FocusRequester,
    onFocusChanged: (item: MediaItem) -> Unit
) {
    CompactCard(
        modifier = Modifier
            .width(196.dp)
            .aspectRatio(16f / 9)
            .ifElse(index == 0, Modifier.focusRequester(focusRequester))
            .onFocusChanged {
                if (it.isFocused) {
                    onFocusChanged(item)
                }
            },
        onClick = {},
        image = {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w342${item.poster}",
                contentDescription = item.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )
        },
        title = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(8.dp)
            )
        },
        subtitle = {
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(0.6f),
                modifier = Modifier.padding(start = 8.dp, bottom = 8.dp, top = 0.dp)
            )
        },
        border = CardDefaults.border(
            focusedBorder = Border(
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
            )
        )
    )

}

private fun Modifier.ifElse(
    condition: Boolean,
    ifTrueModifier: Modifier,
    elseModifier: Modifier = Modifier
): Modifier = then(if (condition) ifTrueModifier else elseModifier)

@SuppressLint("SuspiciousModifierThen")
private fun Modifier.immersiveListGradient(): Modifier = composed {
    val color = MaterialTheme.colorScheme.surface

    val colorAlphaList = listOf(1.0f, 0.2f, 0.0f)
    val colorStopList = listOf(0.2f, 0.8f, 0.9f)

    val colorAlphaList2 = listOf(1.0f, 0.1f, 0.0f)
    val colorStopList2 = listOf(0.1f, 0.4f, 0.9f)
    this
        .then(
            background(
                brush = Brush.linearGradient(
                    colorStopList[0] to color.copy(alpha = colorAlphaList[0]),
                    colorStopList[1] to color.copy(alpha = colorAlphaList[1]),
                    colorStopList[2] to color.copy(alpha = colorAlphaList[2]),
                    start = Offset(0.0f, 0.0f),
                    end = Offset(Float.POSITIVE_INFINITY, 0.0f)
                )
            )
        )
        .then(
            background(
                brush = Brush.linearGradient(
                    colorStopList2[0] to color.copy(alpha = colorAlphaList2[0]),
                    colorStopList2[1] to color.copy(alpha = colorAlphaList2[1]),
                    colorStopList2[2] to color.copy(alpha = colorAlphaList2[2]),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(0f, 0f)
                )
            )
        )
}
