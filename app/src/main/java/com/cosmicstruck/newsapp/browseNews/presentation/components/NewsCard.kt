package com.cosmicstruck.newsapp.browseNews.presentation.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cosmicstruck.newsapp.R
import coil3.compose.AsyncImage
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.ui.theme.NewsAppTheme
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import me.saket.swipe.rememberSwipeableActionsState

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    data: Data,
    onClick: () -> Unit,
    onSwipe : ((Data) -> Unit )? = null
){
    val context = LocalContext.current
    Log.d("NEWSCARD REACHED","REACHED IN NEWS CARD")
    val bookmark = SwipeAction(
        onSwipe = {
            Toast.makeText(context,"BOOKMARK ADDED", Toast.LENGTH_LONG).show()
            onSwipe?.invoke(data)
        },
        icon = {Icons.Default.FavoriteBorder},
        background = Color.Red
    )

    val swipeState = rememberSwipeableActionsState()
    SwipeableActionsBox(
        modifier = Modifier.fillMaxWidth(),
        startActions = if (onSwipe != null) listOf(
            bookmark
        ) else emptyList(),
        state = swipeState,
        swipeThreshold = 8.dp
    ) {
        Row(
            modifier = Modifier
                .clickable{
                    Log.d("CLICKING","NEWSCARD CLICKED ${data.title}")
                    onClick.invoke()
                }
        ) {
            data.image?.let { Log.d("IMAGE CHECKING",it) }
            AsyncImage(
                modifier = Modifier
                    .size(96.dp),
                model = data.image,
                placeholder = painterResource(id = R.drawable.browse),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(100.dp)
            ) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = if(!isSystemInDarkTheme()) Color.Black else Color.White,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_time),
                        contentDescription = null,
                        modifier = Modifier.size(8.dp),
                        tint = if (!isSystemInDarkTheme()) Color.Black else Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = data.published_at,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (!isSystemInDarkTheme()) Color.Black.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.6f)
                    )
                }
            }
        }
    }

}

