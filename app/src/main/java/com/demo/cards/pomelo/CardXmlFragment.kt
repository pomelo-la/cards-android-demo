package com.demo.cards.pomelo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import com.demo.cards.pomelo.databinding.FragmentCardXmlBinding
import com.pomelo.cards.widgets.CardsResult
import com.pomelo.cards.widgets.ui.card.bottomsheet.PomeloCardBottomSheet

class CardXmlFragment : Fragment() {

    private lateinit var binding: FragmentCardXmlBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCardXmlBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("ComplexMethod")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pomeloCardView.init(
            imageUrl = "file:///android_asset/asset_card_1.png",
            name = "Juan Perez",
            lastFourDigits = "0334"
        )

        binding.showInformation.setOnClickListener {
            binding.pomeloCardView.showSensitiveData(
                cardId = BuildConfig.CARD_ID,
                onResultListener = { cardsResult, _ ->
                    when (cardsResult) {
                        CardsResult.NETWORK_ERROR -> {}
                        CardsResult.BIOMETRIC_ERROR -> {}
                        CardsResult.SUCCESS -> {}
                    }
                }
            )
        }

        binding.showBottomSheet.setOnClickListener {
            PomeloCardBottomSheet.showSensitiveData(requireContext(),
                BuildConfig.CARD_ID,
                getString(R.string.card_name)
            ) { result, _ ->
                when (result) {
                    CardsResult.NETWORK_ERROR -> {}
                    CardsResult.BIOMETRIC_ERROR -> {}
                    CardsResult.SUCCESS -> {

                    }
                }
            }
        }

        binding.activateCard.init { cardsResult, message ->
            when (cardsResult) {
                CardsResult.NETWORK_ERROR -> Log.e(cardsResult.name, message ?: "")
                CardsResult.BIOMETRIC_ERROR -> {}
                CardsResult.SUCCESS -> {}
            }
        }

        binding.changePin.init(
            cardId = BuildConfig.CARD_ID,
            onResultListener = { cardsResult, message ->
                when (cardsResult) {
                    CardsResult.NETWORK_ERROR -> Log.e(cardsResult.name, message ?: "")
                    CardsResult.BIOMETRIC_ERROR -> {}
                    CardsResult.SUCCESS -> {}
                }
            }
        )
    }
}
