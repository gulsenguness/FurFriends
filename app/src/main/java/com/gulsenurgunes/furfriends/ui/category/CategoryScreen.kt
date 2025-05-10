package com.gulsenurgunes.furfriends.ui.category

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gulsenurgunes.furfriends.R
import com.gulsenurgunes.furfriends.navigation.TopBar
import com.gulsenurgunes.furfriends.ui.home.Category
import com.gulsenurgunes.furfriends.ui.home.CategoryGrid

@Composable
fun CategoryScreen() {

    Scaffold(
        topBar = {
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
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Catgory(categories = sampleCategories)
        }
    }

}

@Composable
fun Catgory(
    categories: List<Category2>,
){
    Column(
    ) {
        CategoryGrid(
            text = "Happy To Welcome You To\nOur Circle Of Friends",
            categories = listOf(
                Category("Dogs", R.drawable.dogs),
                Category("Cats", R.drawable.cats),
                Category("Rabbits", R.drawable.rabbits),
                Category("Parrot", R.drawable.parrot)
            ),
            onClick = {}
        )
        CategoryListUI(categories = categories)

    }
}

@Composable
fun CategoryCard(
    category: Category2,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(200.dp)
            .height(220.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.Gray)
    ) {
        Image(
            painter = painterResource(id = category.imageRes),
            contentDescription = category.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear",
                tint = Color.Black
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${category.title} (${category.itemCount} Items)",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
@Composable
fun CategoryListUI(
    categories: List<Category2>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = "Discover Latest Collection",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {}
                )
            }
        }
    }
}
private val sampleCategories = listOf(
    Category2("Belt", 24,R.drawable.cats),
    Category2("Hat", 12,R.drawable.dogs),
    Category2("Shoes", 32,R.drawable.dogs),
    Category2("Bag", 8,R.drawable.cats),
    Category2("Watch", 15,R.drawable.dogs)
)
data class Category2(
    val title: String,
    val itemCount: Int,
    val imageRes:Int
)
@Preview(showBackground = true)
@Composable
fun CategoryPreview() {
    CategoryScreen()
}