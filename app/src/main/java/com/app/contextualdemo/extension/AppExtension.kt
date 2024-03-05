package com.app.contextualdemo.extension

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getAppString(@StringRes resId: Int): String {
    return LocalContext.current.getString(resId)
}