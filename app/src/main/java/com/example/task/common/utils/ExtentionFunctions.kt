package com.example.task.common.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import androidx.core.net.toUri

@SuppressLint("QueryPermissionsNeeded")
fun Context.openGoogleMaps(lat: Double, lng: Double) {
    val gmmIntentUri = "geo:$lat,$lng?q=$lat,$lng".toUri()
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
        setPackage("com.google.android.apps.maps") // Google Maps package
    }

    when {
        mapIntent.resolveActivity(packageManager) != null -> {
            startActivity(mapIntent)
        }
        else -> {
            val webUri = "https://www.google.com/maps/search/?api=1&query=$lat,$lng".toUri()
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            startActivity(webIntent)
        }
    }
}

fun Context.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = (this as? Activity)?.currentFocus
    currentFocus?.windowToken?.let { token ->
        imm.hideSoftInputFromWindow(token, 0)
    }
}