package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    // State: ID of the current artwork (1, 2 or 3)
    var currentArtwork by remember { mutableStateOf(1) }

    val imageRes = when (currentArtwork) {
        1 -> R.drawable.art1
        2 -> R.drawable.art2
        else -> R.drawable.art3
    }
    val title = when (currentArtwork) {
        1 -> R.string.art1_title
        2 -> R.string.art2_title
        else -> R.string.art3_title
    }
    val artist = when (currentArtwork) {
        1 -> R.string.art1_artist
        2 -> R.string.art2_artist
        else -> R.string.art3_artist
    }
    val year = when (currentArtwork) {
        1 -> R.string.art1_year
        2 -> R.string.art2_year
        else -> R.string.art3_year
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ArtworkWall(imageRes = imageRes, contentDescription = title)
        Spacer(modifier = Modifier.height(24.dp))
        ArtworkDescriptor(title = title, artist = artist, year = year)
        Spacer(modifier = Modifier.height(24.dp))
        DisplayController(
            onPreviousClick = {
                currentArtwork = when (currentArtwork) {
                    1 -> 3
                    2 -> 1
                    else -> 2
                }
            },
            onNextClick = {
                currentArtwork = when (currentArtwork) {
                    1 -> 2
                    2 -> 3
                    else -> 1
                }
            }
        )
    }
}

@Composable
fun ArtworkWall(
    @DrawableRes imageRes: Int,
    @StringRes contentDescription: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = stringResource(contentDescription),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(24.dp)
                .height(360.dp)
        )
    }
}

@Composable
fun ArtworkDescriptor(
    @StringRes title: Int,
    @StringRes artist: Int,
    @StringRes year: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Light
            )
            Row {
                Text(text = stringResource(artist), fontWeight = FontWeight.Bold)
                Text(text = " (${stringResource(year)})")
            }
        }
    }
}

@Composable
fun DisplayController(
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(onClick = onPreviousClick, modifier = Modifier.width(140.dp)) {
            Text(stringResource(R.string.previous))
        }
        Button(onClick = onNextClick, modifier = Modifier.width(140.dp)) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}