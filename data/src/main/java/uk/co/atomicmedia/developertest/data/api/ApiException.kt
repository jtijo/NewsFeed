package uk.co.atomicmedia.developertest.data.api

sealed class ApiException(message: String): RuntimeException(message) {
    class NetworkError: ApiException("No connection")
    data class HttpError(val code: Int): ApiException("HttpError: $code")
}