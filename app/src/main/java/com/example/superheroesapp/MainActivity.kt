package com.example.superheroesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.animateValueAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.zIndex
import com.example.superheroesapp.model.AllMyHeroes
import com.example.superheroesapp.model.Hero
import com.example.superheroesapp.ui.theme.SuperHeroesAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperHeroesAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { SuperHeroTopAppBar() }
                    ) { paddingValues ->
                    SuperHeroesApp(modifier = Modifier, contentpadding = paddingValues)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuperHeroTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(title =
    {Text(
        text = stringResource(R.string.app_name)
    )}
    )
}

@Composable
fun SuperHeroesApp(
    heroes: List<Hero> = AllMyHeroes.heroes,
    modifier: Modifier = Modifier,
    contentpadding: PaddingValues

) {
    LazyColumn(
        modifier = modifier.padding(contentpadding),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(heroes) {
            SuperHeroItem(
                hero = it,
                modifier = Modifier.padding(8.dp),

            )
        }
    }
}


@Composable
fun SuperHeroItem(hero: Hero, modifier: Modifier = Modifier) {

    var expanded by remember {mutableStateOf(false)}

    val position by animateIntAsState(
        targetValue = if (!expanded) 72 else 340,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    val positionText by animateIntAsState(
        targetValue = if (!expanded) 50 else 340,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Card(
        modifier = modifier
            .shadow(
                elevation = 4.dp, // Высота тени
                clip = false,
                shape = MaterialTheme.shapes.medium
            )) {
        Box(modifier = Modifier
            .align(Alignment.End)
            .fillMaxSize()
            .padding(16.dp)


            ) {
            Box(modifier = Modifier.zIndex(1f)
                .fillMaxWidth()
                .offset(positionText.dp, 0.dp)
                .height(72.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant))
            {Text(text = "")}
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)) {
                Row(
                ) {
                    SuperHeroInfo(
                        hero.nameRes, hero.descriptionRes,
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)

                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Spacer(modifier = Modifier.width(72.dp))
                }
            }
            Row(modifier = Modifier
                .width(position.dp)
                .animateContentSize()
                .zIndex(2f),
                horizontalArrangement = Arrangement.End

            ) {
                SuperHeroImage(
                    hero.imageRes, onClick = {expanded = !expanded},


                )
            }
        }
    }
}

@Composable
fun SuperHeroInfo(name: Int, descr: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround) {
        Text(
            text = stringResource(name),
            maxLines = 1,
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(descr),
            maxLines = 2,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun SuperHeroImage(image: Int, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Image(
        painter = painterResource(image),
        contentDescription = null,
        modifier = Modifier
            .size(72.dp)
            .clip(shape = MaterialTheme.shapes.small)
            .clickable(onClick = onClick)
    )
}



@Preview(showBackground = true)
@Composable
fun PreviewHero() {
    SuperHeroesAppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { SuperHeroTopAppBar() }
        ) { paddingValues ->
            SuperHeroesApp(modifier = Modifier, contentpadding = paddingValues)
        }
    }
}




//@Composable
//fun SuperHeroItem(hero: Hero, modifier: Modifier = Modifier) {
//    Card(
//        modifier = modifier
//            .shadow(
//                elevation = 4.dp, // Высота тени
//                clip = false,
//                shape = MaterialTheme.shapes.medium
//            )) {
//        Row(modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//            horizontalArrangement = Arrangement.SpaceBetween) {
//            SuperHeroInfo(hero.nameRes, hero.descriptionRes,
//                modifier = Modifier.fillMaxHeight().weight(1f))
//            Spacer(modifier = Modifier.width(16.dp))
//            SuperHeroImage(hero.imageRes)
//        }
//    }
//}



