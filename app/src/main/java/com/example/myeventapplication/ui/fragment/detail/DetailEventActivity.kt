package com.example.myeventapplication.ui.fragment.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.bumptech.glide.Glide
import com.example.myeventapplication.viewModel.MainViewModel
import com.example.myeventapplication.databinding.ActivityDetailEventBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Locale
import org.jsoup.Jsoup


class DetailEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventBinding

    private val mainViewModel: MainViewModel by viewModel()
    private val args: DetailEventActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        showLoading()
        checkConnection()

        getDetailEvent(args.eventId)
        initViewDetailEvent()
    }

    private fun getDetailEvent(eventId: String) {
        mainViewModel.getDetailEvent(eventId)
    }

    private fun showLoading() {
        mainViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewDetailEvent() {
        mainViewModel.eventsSingle.observe(this) { response ->
            if (response != null) {
                Glide.with(this).load(response.mediaCover).into(binding.ivBanner)

                val dateBegin = parseDate(response.beginTime)
                val dateEnd = parseDate(response.endTime)
                val timeBegin = parseTime(response.beginTime)
                val timeEnd = parseTime(response.endTime)
                val quota = response.quota - response.registrants

                val date: String = if (dateBegin == dateEnd) {
                    dateBegin
                } else {
                    "$dateBegin - $dateEnd"
                }

                val description = convertHTMLtoString(response.description)

                binding.tvTitle.text = response.name
                binding.tvSummary.text = response.summary
                binding.tvCategory.text = response.category
                binding.tvOwner.text = response.ownerName
                binding.tvDate.text = date
                binding.tvTime.text = "$timeBegin - $timeEnd"
                binding.tvLocation.text = response.cityName
                binding.tvQuota.text = "Sisa Kouta : $quota"
                binding.webView.loadDataWithBaseURL(
                    null,
                    cleanHtmlContent(response.description),
                    "text/html",
                    "UTF-8",
                    null
                )



                binding.btnRegister.setOnClickListener {
                    val url = response.link
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            } else {
                Log.e("Event Data", "Gagal memuat event")
            }

        }
    }

    private fun parseDate(dateInput: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

        val date = inputFormat.parse(dateInput)
        val formattedDate = outputDate.format(date!!)

        return formattedDate
    }


    private fun parseTime(timeInput: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputDate = SimpleDateFormat("HH:mm:ss", Locale.getDefault())

        val date = inputFormat.parse(timeInput)
        val formattedDate = outputDate.format(date!!)

        return formattedDate
    }

    private fun checkConnection() {
        mainViewModel.errorMessage.observe(this) { error ->
            if (!error.isNullOrEmpty())
                Toast.makeText(this, "$error", Toast.LENGTH_LONG).show()
        }
    }

    private fun convertHTMLtoString(str: String): String {
        val document = Jsoup.parse(str)
        val textOnly = document.text()
        return textOnly
    }

    fun cleanHtmlContent(html: String): String {
        val document = Jsoup.parse(html)

        document.select("img").remove()

        return """
        <html>
        <head>
            <style>
                body { font-family: sans-serif; padding: 16px; }
                img { display: none; } /* Sembunyikan semua gambar */
            </style>
        </head>
        <body>
            ${document.body().html()}
        </body>
        </html>
    """.trimIndent()
    }

}