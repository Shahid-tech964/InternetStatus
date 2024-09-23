package com.example.broadcastreciiver

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint( "CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun InternetStatus(context: Context) {
    var isConnected by  remember { mutableStateOf(false) }

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            CoroutineScope(Dispatchers.Main).launch {
                isConnected = true
            }
        }

        override fun onLost(network: Network) {
            CoroutineScope(Dispatchers.Main).launch {   isConnected= false }


        }
    }

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkRequest
    = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .build()
DisposableEffect(key1 = true) {
    connectivityManager.registerNetworkCallback(networkRequest,
    networkCallback)
    onDispose {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}


    // Use isConnected in your Compose UI to display internet status
    if(isConnected){
        val snakbarhoststate= remember {
            SnackbarHostState()
        }
        val scope = rememberCoroutineScope()
        Scaffold(snackbarHost = { SnackbarHost(hostState = snakbarhoststate) }) {
            scope.launch {  snakbarhoststate.showSnackbar("Internet conneted") }

        }

    }
    else{
        val snakbarhoststate= remember {
            SnackbarHostState()
        }
        val scope = rememberCoroutineScope()
        Scaffold(snackbarHost = { SnackbarHost(hostState = snakbarhoststate) }) {
            scope.launch {  snakbarhoststate.showSnackbar("Internet not  conneted") }

        }

    }

}




