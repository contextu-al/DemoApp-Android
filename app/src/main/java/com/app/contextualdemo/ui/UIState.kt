package com.app.contextualdemo.ui

sealed class UIState<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T> : UIState<T>()
    class Error<T>(message: String) : UIState<T>(message = message)
    class Response<T>(data: T) : UIState<T>(data = data)
}