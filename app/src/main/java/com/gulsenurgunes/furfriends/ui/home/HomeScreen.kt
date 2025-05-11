package com.gulsenurgunes.furfriends.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.common.UIState
import com.gulsenurgunes.furfriends.navigation.TopBar

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {

    Scaffold(topBar = {
        TopBar(
            title = {
                Image(
                    painter = painterResource(R.drawable.first),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                )
                Spacer(Modifier.width(8.dp))
                Text("Hello, Gülşen Güneş")
            },
            actions = {
                IconButton(onClick = {}) {
                    BadgedBox(badge = { Badge { Text("2") } }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Bildirimler")
                    }
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = "Ara")
                }
            }
        )
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Home()
//            when (uiState) {
//                is UIState<Any?>.Success -> Home()
//                is UIState<Any?>.Loading -> {
//                    if ((uiState as UIState<Any?>.Loading).isLoading) {
//                        CircularProgressIndicator()
//                    }
//                }
//
//                is UIState<>.Error -> {
//                    val message = (uiState as UIState<Any?>.Error).message
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "Bir hata oluştu:\n$message",
//                            color = Color.Red
//                        )
//                        Spacer(Modifier.height(12.dp))
//                        Button(onClick = { homeViewModel.retry() }) {
//                            Text("Tekrar Dene")
//                        }
//                    }
//                }
//            }
        }
    }
}

@Composable
fun Home() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Section()

        CategoryGrid(
            text = "Find Best Category",
            categories = listOf(
                Category("Dogs", R.drawable.dogs),
                Category("Cats", R.drawable.cats),
                Category("Rabbits", R.drawable.rabbits),
                Category("Parrot", R.drawable.parrot)
            ),
            onClick = {}
        )

        PetGrooming()
        Video()
        DividerC()
        Food()
        Product()
        MiddleImages()
        Comment()
    }
}


@Composable
fun Section() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFFFF0F0)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text("We Give Preference To Your Pets", style = MaterialTheme.typography.titleLarge)
                Text(
                    "No code need. Plus free shippng on $99+ orders!",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = {}) { Text("Adopt A Pet") }
            }
            Image(
                painter = painterResource(R.drawable.splash),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            )
        }
    }
}

@Composable
fun CategoryGrid(text:String,categories: List<Category>, onClick: (Category) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "See All",
        )
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(8.dp)
            .height(300.dp)
            .wrapContentHeight(),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { cat ->
            Card(
                onClick = {},
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxWidth()
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(cat.imageRes),
                        contentDescription = cat.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .align(Alignment.BottomCenter)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.6f)
                                    )

                                )
                            )
                    )
                    Text(
                        text = cat.title,
                        fontSize = 24.sp,
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PetGrooming() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(R.drawable.hairdresser),
                    contentDescription = "Pet Grooming",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Pet Grooming",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Adopt A Pet",
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.paw),
                    contentDescription = "Pet Grooming",
                    modifier = Modifier.size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standart dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries",
            fontSize = 16.sp
        )

    }
}

@Composable
fun Video() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Box(
                Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(40.dp)
                        .background(Color(0xFFFFD54F), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "50%\nSale",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Image(
                    painter = painterResource(R.drawable.dogs),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )

            }
        }
        Card(
            onClick = {},
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(150.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFFFB74D)),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "PLAY VIDEO",
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.Black)
                        )
                    }
                    Image(
                        painter = painterResource(R.drawable.dogs),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )

                }
            }
        }
    }
}

@Composable
fun DividerC() {
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        thickness = 1.dp,
        color = Color.Gray
    )
}

@Composable
fun Food() {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Reliable Healthy Food\n For Your Pet",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "See All",
            )
        }
        val items = listOf("Dogs Food", "Cats Food", "Rabbits Food", "Parrot Food")
        var selectedItem by remember { mutableStateOf<String?>(null) }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(items) { item ->
                val isSelected = item == selectedItem
                OutlinedButton(
                    onClick = {
                        selectedItem = if (isSelected) null else item
                    },
                    modifier = Modifier
                        .height(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) Color.Black else Color.White,
                        contentColor = if (isSelected) Color.White else Color.Black
                    )
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }
    }
}

@Composable
fun Product() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(320.dp)
            .padding(16.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(16.dp))
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            content = {
                items(4) { index ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Kart ${index + 1}")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun MiddleImages(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.parrot),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 32.dp))
                )
                Image(
                    painter = painterResource(R.drawable.rabbits),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topEnd = 32.dp))
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.dogs),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(bottomEnd = 32.dp))
                )
                Image(
                    painter = painterResource(R.drawable.cats),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                )
            }

        }
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(R.drawable.dogs),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(bottomEnd = 64.dp, bottomStart = 64.dp))
        )
    }
}

@Composable
fun Comment() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "What Pet Lovers Say About Us?",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = "See All",
        )
    }
}

data class Category(val title: String, val imageRes: Int)

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    HomeScreen()
}




