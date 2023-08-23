package com.seif.thewalkingdeadapp.presentation.common

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.seif.thewalkingdeadapp.R
import com.seif.thewalkingdeadapp.domain.model.CharacterDomainModel
import com.seif.thewalkingdeadapp.presentation.LightDarkModePreviews
import com.seif.thewalkingdeadapp.presentation.component.RatingWidget
import com.seif.thewalkingdeadapp.presentation.component.ShimmerEffect
import com.seif.thewalkingdeadapp.presentation.ui.theme.CHARACTER_ITEM_HEIGHT
import com.seif.thewalkingdeadapp.presentation.ui.theme.LightGray
import com.seif.thewalkingdeadapp.presentation.ui.theme.characterPlaceHolderImage
import com.seif.thewalkingdeadapp.utils.Constants.BASE_URL

@Composable
fun CharactersListContent(
    characters: LazyPagingItems<CharacterDomainModel>,
    onCharacterClicked: (characterId: Int) -> Unit
) {
    Log.d("characters", characters.loadState.toString())
    val result = handlePagingResult(charactersLazyPagingItems = characters)
    Log.d("RemoteMediator","result = $result")
    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(characters) { character ->
                Log.d("characters", character.toString())
                CharacterItem(
                    characterDomainModel = character,
                    onCharacterClicked = { onCharacterClicked(it) }
                )
            }
        }
    }
}

@Composable
fun handlePagingResult(
    charactersLazyPagingItems: LazyPagingItems<CharacterDomainModel>
): Boolean {
    charactersLazyPagingItems.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when { // bec we want to show shimmer effect only when initial load is triggered
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                //EmptyScreen(error = error, heroes = heroes)
                false
            }
            charactersLazyPagingItems.itemCount < 1 -> {
                //EmptyScreen()
                false
            }
            else -> true // display all characters
        }
    }
}

@Composable
fun CharacterItem(
    characterDomainModel: CharacterDomainModel?,
    onCharacterClicked: (characterId: Int) -> Unit
) {
    characterDomainModel?.let { character ->
        val painter = rememberAsyncImagePainter(
            model = "$BASE_URL/${characterDomainModel.image}",
            placeholder = painterResource(id = characterPlaceHolderImage),
            error = painterResource(id = characterPlaceHolderImage)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(CHARACTER_ITEM_HEIGHT)
                .clip(RoundedCornerShape(20.dp))
                .clickable {
                    onCharacterClicked(character.id)
                }
        ) {
            Image(
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.FillBounds,
                painter = painter,
                contentDescription = stringResource(R.string.character_image)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .wrapContentWidth()
                    .background(Color.Black.copy(ContentAlpha.medium)),
            ) {
                Text(
                    text = character.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                )

                Text(
                    text = character.about,
                    fontSize = 15.sp,
                    color = LightGray,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 8.dp)
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(rating = character.rating, starSize = 20.dp)

                    Text(
                        text = "(${character.rating})",
                        fontSize = 16.sp,
                        color = LightGray,
                        maxLines = 1,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }
        }
    }
}

@LightDarkModePreviews
@Composable
fun CharacterItemPreview() {
    CharacterItem(
        characterDomainModel = CharacterDomainModel(
            id = 1,
            name = "Rick Grimes",
            realName = "Andrew Lincoln",
            about = "great actor aladsf  aldf  alskdjf  al;kjd lajdf ;asjd;fa sdf;jasd alsdfj;klasdfj;kladsjf;las dfjszd fj'ads fjas;dfjas;d fjas;dljs asd;jklf f;aklsdjf a asd;lfkja ds;lfk",
            totalAppearances = 102,
            image = "https://static.independent.co.uk/s3fs-public/thumbnails/image/2018/07/20/09/andrew-lincoln-rick-grimes-walking-dead.jpg?width=1200",
            quote = "I will kill you, may be not today or tommorow but I will kill you",
            quoteTime = "when negan want to cut his son hand",
            rating = 5.0
        ),
    ) {}
}