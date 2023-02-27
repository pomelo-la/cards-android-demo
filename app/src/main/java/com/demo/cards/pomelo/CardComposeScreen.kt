package com.demo.cards.pomelo

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.material.composethemeadapter.MdcTheme
import com.pomelo.cards.widgets.CardsResult
import com.pomelo.cards.widgets.ui.activatecard.PomeloActivateCardView
import com.pomelo.cards.widgets.ui.card.bottomsheet.PomeloCardBottomSheet
import com.pomelo.cards.widgets.ui.card.image.PomeloCardView
import com.pomelo.cards.widgets.ui.changepin.PomeloChangePinComposable

@Suppress("LongMethod", "ComplexMethod")
@Composable
fun CardComposeScreen() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var showSensitiveCardData by rememberSaveable { mutableStateOf(false) }
    var showCardBottomSheet by remember { mutableStateOf(false) }

    MdcTheme {

        Scaffold(
            scaffoldState = scaffoldState,
            backgroundColor = Color.Transparent
        ) { paddingValue ->
            Box(
                Modifier.padding(paddingValue)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PomeloCardView(
                        name = "Juan Perez",
                        lastFourDigits = "0334",
                        showCardData = showSensitiveCardData,
                        cardId = BuildConfig.CARD_ID,
                        imageUrl = "file:///android_asset/asset_card_1.png",
                        scaffoldState = scaffoldState,
                        onResultListener = { cardsResult, _ ->
                            when (cardsResult) {
                                CardsResult.NETWORK_ERROR -> {}
                                CardsResult.BIOMETRIC_ERROR -> {}
                                CardsResult.SUCCESS -> {}
                            }
                        },
                        onHideData = {}
                    )

                    Row(modifier = Modifier.padding(top = 24.dp)) {
                        OutlinedButton(
                            onClick = { showSensitiveCardData = !showSensitiveCardData }
                        ) {
                            Text(
                                text = stringResource(id = R.string.show_data),
                                style = MaterialTheme.typography.button
                            )
                        }

                        OutlinedButton(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth(),
                            onClick = {
                                showCardBottomSheet = true
                            }
                        ) {
                            Text(
                                text = "Ver bottom sheet",
                                style = MaterialTheme.typography.button
                            )
                        }
                    }

                    PomeloActivateCardView(
                        scaffoldState = scaffoldState,
                        onResultListener = { cardsResult, _ ->
                            when (cardsResult) {
                                CardsResult.NETWORK_ERROR -> {
                                }
                                CardsResult.BIOMETRIC_ERROR -> {
                                }
                                CardsResult.SUCCESS -> {
                                }
                            }
                        }
                    )

                    PomeloChangePinComposable(
                        cardId = BuildConfig.CARD_ID,
                        scaffoldState = scaffoldState,
                        onResultListener = { cardsResult, message ->
                            when (cardsResult) {
                                CardsResult.NETWORK_ERROR -> {
                                }
                                CardsResult.BIOMETRIC_ERROR -> {
                                }
                                CardsResult.SUCCESS -> {

                                }
                            }
                        }
                    )
                }
                /*PomeloCardBottomSheet.showSensitiveData(
                    cardId = BuildConfig.CARD_ID,
                    titleCard = stringResource(id = R.string.card_name),
                    showSensitiveData = showCardBottomSheet,
                    onDismiss = { showCardBottomSheet = false },
                    scaffoldState = scaffoldState,
                    onResultListener = { cardsResult, message ->
                        when (cardsResult) {
                            CardsResult.NETWORK_ERROR -> Log.e(cardsResult.name, message ?: "")
                            CardsResult.BIOMETRIC_ERROR -> {}
                            CardsResult.SUCCESS -> Log.d("cardsTest", "Success compose bottom")
                        }
                    }
                )*/
            }
        }
    }
}
