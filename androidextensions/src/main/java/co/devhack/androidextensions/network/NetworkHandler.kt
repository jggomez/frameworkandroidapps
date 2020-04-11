package co.devhack.androidextensions.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi

class NetworkHandler(private val context: Context) {

    val isConnected get() = context.networkInfo?.isConnected

    val isWifiConnected: Boolean?
        get() =
            context.networkInfo?.isConnected!! &&
                    context.networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)!!

    val isCellNetworkConnected: Boolean?
        get() =
            context.networkInfo?.isConnected!! &&
                    context.networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)!!
}

val Context.networkInfo: NetworkInfo?
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo

val Context.networkCapabilities: NetworkCapabilities?
    @RequiresApi(Build.VERSION_CODES.M)
    get() =
        (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .getNetworkCapabilities((this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork)
