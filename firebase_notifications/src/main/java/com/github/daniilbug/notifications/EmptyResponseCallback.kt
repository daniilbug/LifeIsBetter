package com.github.daniilbug.notifications

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class EmptyResponseCallback: Callback {
    override fun onFailure(call: Call, e: IOException) {}
    override fun onResponse(call: Call, response: Response) {}
}