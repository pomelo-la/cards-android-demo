package com.demo.cards.pomelo

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.composethemeadapter.MdcTheme
import com.pomelo.cards.CardsResult
import com.pomelo.cards.ui.activate.PomeloActivateCardView
import com.pomelo.cards.ui.cardview.composables.PomeloCardBottomSheet
import com.pomelo.cards.ui.cardview.composables.PomeloCardView
import com.pomelo.cards.ui.pin.change.PomeloChangePinComposable

@Suppress("LongMethod", "ComplexMethod")
@Composable
fun CardComposeScreen() {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    var showSensitiveCardData by rememberSaveable { mutableStateOf(false) }
    var showCardBottomSheet by remember { mutableStateOf(false) }

    var changePin by remember { mutableStateOf(false) }
    var activate by remember { mutableStateOf(false) }
    var changePinText by remember { mutableStateOf("CAMBIO DE PIN") }
    var activateText by remember { mutableStateOf("ACTIVAR") }

    val context = LocalContext.current
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
                        }
                    )

                    Row(modifier = Modifier.padding(top = 24.dp)) {
                        OutlinedButton(
                            onClick = { showSensitiveCardData = !showSensitiveCardData }
                        ) {
                            Text(
                                text = "Ver datos",
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

                    Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
                    AnimatedVisibility(visible = !activate) {
                        Button(
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .fillMaxWidth(),
                            onClick = {
                                activate = true
                            }
                        ) {
                            Text(
                                text = activateText,
                                style = MaterialTheme.typography.button
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = activate,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
                        PomeloActivateCardView(
                            scaffoldState = scaffoldState,
                            onResultListener = { cardsResult, _ ->
                                when (cardsResult) {
                                    CardsResult.NETWORK_ERROR -> {
                                    }
                                    CardsResult.BIOMETRIC_ERROR -> {
                                    }
                                    CardsResult.SUCCESS -> {
                                        activateText = context.getString(R.string.success_activate)
                                        activate = false
                                    }
                                }
                            }
                        )
                    }
                    AnimatedVisibility(visible = !changePin) {
                        Button(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 16.dp)
                                .fillMaxWidth(),
                            onClick = {
                                changePin = true
                            }
                        ) {
                            Text(
                                text = changePinText,
                                style = MaterialTheme.typography.button
                            )
                        }
                    }

                    AnimatedVisibility(
                        visible = changePin,
                        enter = expandVertically(),
                        exit = shrinkVertically()
                    ) {
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
                                        changePinText = context.getString(com.demo.cards.pomelo.R.string.success_pin)
                                        changePin = false
                                    }
                                }
                            }
                        )
                    }
                }

                PomeloCardBottomSheet(
                    cardId = BuildConfig.CARD_ID,
                    titleCard = "Tarjeta Virtual",
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
                )
            }
        }
    }
}
