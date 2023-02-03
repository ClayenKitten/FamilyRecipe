package com.clayenkitten.recipelibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.clayenkitten.recipelibrary.model.Recipe
import com.clayenkitten.recipelibrary.model.test_recipe
import com.clayenkitten.recipelibrary.ui.theme.RecipeLibraryTheme
import com.clayenkitten.recipelibrary.util.AutoResizeText
import com.clayenkitten.recipelibrary.util.FontSizeRange


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeLibraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RecipeViewer(recipe = test_recipe)
                }
            }
        }
    }
}

@Composable
fun RecipeViewer(recipe: Recipe) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        MainInfo(recipe)
        Details(recipe)
        Box(Modifier.weight(1f)) {
            Ingredients(recipe.ingredients)
        }
        Actions()
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RectangleShape,
        ) {
            Text("COOK")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipePreview() {
    RecipeViewer(test_recipe)
}

@Composable
fun MainInfo(recipe: Recipe) {
    Surface(
        elevation = 1.dp,
    ) {
        Column {
            MainImage(recipe.title, recipe.imageUrl)
            recipe.description?.let { description ->
                Text(
                    text = description,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .heightIn(0.dp, 80.dp)
                        .padding(10.dp, 2.dp, 10.dp, 5.dp)
                        .verticalScroll(rememberScrollState())
                )
            }
        }
    }
}

@Composable
fun MainImage(title: String, url: String) {
    Box {
        AsyncImage(
            model = url,
            contentDescription = "Recipe photo",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .aspectRatio(1.5f),
            contentScale = ContentScale.Crop
        )
        AutoResizeText(
            text = title,
            fontSizeRange = FontSizeRange(16.sp, 40.sp, 2.sp),
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        0F to Color.Transparent,
                        0.1F to Color.Black.copy(alpha = 0.2F),
                        1F to Color.Black.copy(alpha = 0.7F)
                    )
                )
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 24.dp),
            color = Color.White
        ) { layout ->
            layout.didOverflowHeight || (layout.lineCount > 1 && layout.layoutInput.style.fontSize > 24.sp)
        }
//        Text(
//            text = title,
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = fontSize,
//            onTextLayout = { textLayoutResult ->
//                fontSize = when (textLayoutResult.lineCount) {
//                    1 -> 26.sp
//                    2 -> 24.sp
//                    3 -> 24.sp
//                    else -> 24.sp
//                }
//                readyToDraw = true
//            },
//            overflow = TextOverflow.Ellipsis,
//            maxLines = 4,
//            modifier = Modifier
//                .align(Alignment.BottomStart)
//                .fillMaxWidth()
//                .background(
//                    Brush.verticalGradient(
//                        0F to Color.Transparent,
//                        0.1F to Color.Black.copy(alpha = 0.2F),
//                        1F to Color.Black.copy(alpha = 0.7F)
//                    )
//                )
//                .drawWithContent { if (readyToDraw) drawContent() }
//                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 24.dp),
//            color = Color.White
//        )
    }
}

@Composable
fun Details(recipe: Recipe) {
    Row(
        Modifier
            .padding(10.dp, 0.dp)
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        EntryCard(
            Modifier
                .weight(4f, true)
                .fillMaxHeight(),
            icons = {
                Icon(
                    painter = painterResource(R.drawable.clock_24),
                    contentDescription = "Overall cooking time",
                    modifier = Modifier.size(22.dp),
                )
                Icon(
                    painter = painterResource(R.drawable.local_dining_24),
                    contentDescription = "Active cooking time",
                    modifier = Modifier.size(22.dp),
                )
                Icon(
                    painter = painterResource(R.drawable.kitchen_24),
                    contentDescription = "Active cooking time",
                    modifier = Modifier.size(22.dp),
                )
            },
            "Overall: ${recipe.time.overall_time}",
            "Active: ${recipe.time.active_time}",
            "Preparation: ${recipe.time.prepare_time}",
        )
        Column(
            Modifier.weight(3f, true),
            Arrangement.spacedBy(10.dp),
        ) {
            EntryCard(
                Modifier.fillMaxWidth(),
                icons = {
                    Icon(
                        painterResource(R.drawable.servings_24),
                        contentDescription = "Number of servings",
                        modifier = Modifier.size(22.dp),
                    )
                },
                recipe.servings,
            )
            Card(Modifier.fillMaxWidth()) {
                Column(
                    Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        for (_i in 0..4) {
                            Icon(
                                Icons.Filled.Star,
                                null,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                    Text(
                        "${recipe.review.review_number} reviews",
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun Actions() {
    Card(Modifier.padding(10.dp, 0.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Filled.FavoriteBorder,
                contentDescription = "Favorite",
                modifier = Modifier.size(22.dp),
            )
            Icon(
                painter = painterResource(R.drawable.bookmark_24),
                contentDescription = "Bookmark",
                modifier = Modifier.size(22.dp),
            )
            Icon(
                Icons.Filled.Share,
                contentDescription = "Share",
                modifier = Modifier.size(22.dp),
            )
        }
    }
}

@Composable
fun Ingredients(ingredients: List<Pair<String, String>>) {
    Card(Modifier.padding(10.dp, 0.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(20.dp, 10.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "Ingredients",
                fontWeight = FontWeight.Bold,
            )
            for (ingredient in ingredients) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = ingredient.first)
                    Text(text = ingredient.second)
                }
            }
        }
    }
}

@Composable
fun EntryCard(
    modifier: Modifier,
    icons: @Composable () -> Unit,
    vararg texts: String,
) {
    Card(modifier) {
        Row(
            Modifier
                .padding(10.dp)
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
        ) {
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                icons()
            }
            Column(
                Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                for (text in texts) {
                    Text(
                        text,
                        Modifier,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}