package com.example.harganow.utils

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import coil.ImageLoader
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import okhttp3.OkHttpClient
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class ImageGetter {
    fun GetImage(id: String): String {
        return "https://myharga.kpdn.gov.my/myharga/mobile/apps/" + id + ".png"
    }

    companion object {
        fun GetImage(id: String): String {
            return "https://myharga.kpdn.gov.my/myharga/mobile/apps/" + id + ".png"
        }
    }
}

fun createUnsafeOkHttpClient(context: Context): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(
            chain: Array<out java.security.cert.X509Certificate>?, authType: String?
        ) {
        }

        override fun checkServerTrusted(
            chain: Array<out java.security.cert.X509Certificate>?, authType: String?
        ) {
        }

        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
    })

    val sslContext = SSLContext.getInstance("SSL").apply {
        init(null, trustAllCerts, SecureRandom())
    }

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }.build()
}

@Composable
fun SSLUnsafeImage(context: Context, url: String, contentDescription: String) {
    val imageLoader = remember {
        ImageLoader.Builder(context).okHttpClient(createUnsafeOkHttpClient(context)).build()
    }
    val request = remember {
        ImageRequest.Builder(context).data(url).build()
    }
    val painter = rememberImagePainter(request = request, imageLoader = imageLoader)
    Image(painter = painter, contentDescription = contentDescription)
}

@Composable
fun SSLUnsafeImage(context: Context, url: String, contentDescription: String, modifier: Modifier) {
    val imageLoader = remember {
        ImageLoader.Builder(context).okHttpClient(createUnsafeOkHttpClient(context)).build()
    }
    val request = ImageRequest.Builder(context).data(url).build()

    val painter = rememberImagePainter(request = request, imageLoader = imageLoader)
    Image(painter = painter,
        contentDescription = contentDescription,
        modifier = modifier
    )
}
