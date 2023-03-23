package com.example.cft.ui.screen.info.view

import android.content.Intent
import android.net.Uri
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
            openMaps(viewModel.binData.country.latitude, viewModel.binData.country.longitude)
        }
        binding.textViewBankPhone.setOnClickListener {
            openPhone(viewModel.binData.bank.phone)
        }
        binding.textViewBankUrl.setOnClickListener {
            openBrowser(viewModel.binData.bank.url)
        }
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun openMaps(latitude: Float?, longitude: Float?) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:${latitude},${longitude}")
        )
        startActivity(intent)
    }

    private fun openPhone(phone: String?) {
        val intent = Intent(
            Intent.ACTION_DIAL,
            Uri.parse("tel:${phone}")
        )
        startActivity(intent)
    }

    private fun openBrowser(url: String?) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://${url}")
        )
        startActivity(intent)
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

        val luhn: String? = when (viewModel.binData.number.luhn) {
            true -> "Yes"
            false -> "No"
            else -> null
        }

        val prepaid: String? = when (viewModel.binData.prepaid) {
            true -> "Yes"
            false -> "No"
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
                && luhn == null
            )
                textViewLabelCardNumber.visibility = View.GONE
            if (viewModel.binData.number.length == null) {
                textViewSubLabelCardNumberLength.visibility = View.GONE
                textViewNumberLength.visibility = View.GONE
            } else
                textViewNumberLength.text = viewModel.binData.number.length.toString()
            if (luhn == null) {
                textViewSubLabelCardNumberLuhn.visibility = View.GONE
                textViewNumberLuhn.visibility = View.GONE
            } else
                textViewNumberLuhn.text = luhn
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
                textViewLabelCountry.visibility = View.GONE
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