package com.carce.countrysearch.networkService

sealed class RequestState {
    object Loading : RequestState()
    class Failure(val e: Throwable) : RequestState()
    object Success: RequestState()
    object Empty : RequestState()
}
