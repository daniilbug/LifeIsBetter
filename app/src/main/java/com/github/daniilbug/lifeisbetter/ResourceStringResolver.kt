package com.github.daniilbug.lifeisbetter

import android.content.Context

class ResourceStringResolver(context: Context):
    StringResolver {
    private val resources = context.resources

    override fun getString(stringId: Int): String {
        return resources.getString(stringId)
    }

    override fun getString(stringId: Int, vararg args: Any): String {
        return resources.getString(stringId, *args)
    }
}