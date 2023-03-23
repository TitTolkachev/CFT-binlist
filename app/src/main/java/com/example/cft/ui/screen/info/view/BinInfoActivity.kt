package com.example.cft.ui.screen.info.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.cft.databinding.ActivityBinInfoBinding
import com.example.cft.ui.screen.info.viewmodel.BinInfoViewModel

class BinInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBinInfoBinding
    private lateinit var viewModel: BinInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[BinInfoViewModel::class.java]

        binding = ActivityBinInfoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.loadBinData(intent)

        initTextViews()

        binding.textViewCoordinates.setOnClickListener {
            viewModel.openMaps(this)
        }
        binding.textViewBankPhone.setOnClickListener {
            viewModel.openPhone(this)
        }
        binding.textViewBankUrl.setOnClickListener {
            viewModel.openBrowser(this)
        }
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun initTextViews() {

        var coordinates: String? = null
        if (viewModel.binData.country.latitude != null
            && viewModel.binData.country.longitude != null
        )
            coordinates = "(latitude: ${viewModel.binData.country.latitude.toString()}" +
                    ", longitude: ${viewModel.binData.country.longitude.toString()})"

        var bankName: String? = null
        if (viewModel.binData.bank.name != null) {
            bankName = viewModel.binData.bank.name
            if (viewModel.binData.bank.city != null)
                bankName += ", ${viewModel.binData.bank.city}"
        } else if (viewModel.binData.bank.city != null)
            bankName = viewModel.binData.bank.city

        val prepaid: String? = when (viewModel.binData.prepaid) {
            true -> "YES"
            false -> "NO"
            else -> null
        }

        with(binding) {
            if (viewModel.binData.scheme == null) {
                textViewLabelScheme.visibility = View.GONE
                textViewScheme.visibility = View.GONE
            } else
                textViewScheme.text = viewModel.binData.scheme
            if (viewModel.binData.brand == null) {
                textViewLabelBrand.visibility = View.GONE
                textViewBrand.visibility = View.GONE
            } else
                textViewBrand.text = viewModel.binData.brand
            if (viewModel.binData.number.length == null
                && viewModel.binData.number.luhn == null
            )
                textViewLabelCardNumber.visibility = View.GONE
            if (viewModel.binData.number.length == null) {
                textViewSubLabelCardNumberLength.visibility = View.GONE
                textViewNumberLength.visibility = View.GONE
            } else
                textViewNumberLength.text = viewModel.binData.number.length.toString()
            if (viewModel.binData.number.luhn == null) {
                textViewSubLabelCardNumberLuhn.visibility = View.GONE
                textViewNumberLuhn.visibility = View.GONE
            } else
                textViewNumberLuhn.text = viewModel.binData.number.luhn.toString()
            if (viewModel.binData.type == null) {
                textViewLabelType.visibility = View.GONE
                textViewType.visibility = View.GONE
            } else
                textViewType.text = viewModel.binData.type
            if (prepaid == null) {
                textViewLabelPrepaid.visibility = View.GONE
                textViewPrepaid.visibility = View.GONE
            } else
                textViewPrepaid.text = prepaid
            if (viewModel.binData.country.emoji == null
                && viewModel.binData.country.name == null
                && coordinates == null
            )
                textViewLabelCoutry.visibility = View.GONE
            if (viewModel.binData.country.emoji == null)
                textViewEmoji.visibility = View.GONE
            else
                textViewEmoji.text = viewModel.binData.country.emoji
            if (viewModel.binData.country.name == null)
                textViewCountry.visibility = View.GONE
            else
                textViewCountry.text = viewModel.binData.country.name
            if (coordinates == null)
                textViewCoordinates.visibility = View.GONE
            else
                textViewCoordinates.text = coordinates
            if (bankName == null
                && viewModel.binData.bank.url == null
                && viewModel.binData.bank.phone == null
            )
                textViewLabelBank.visibility = View.GONE
            if (bankName == null)
                textViewBankName.visibility = View.GONE
            else
                textViewBankName.text = bankName
            if (viewModel.binData.bank.url == null)
                textViewBankUrl.visibility = View.GONE
            else
                textViewBankUrl.text = viewModel.binData.bank.url
            if (viewModel.binData.bank.phone == null)
                textViewBankPhone.visibility = View.GONE
            else
                textViewBankPhone.text = viewModel.binData.bank.phone

            textViewBinNumber.text = viewModel.binNumber
        }
    }
}