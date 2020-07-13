package com.github.daniilbug.lifeisbetter

interface StringResolver {
    fun getString(stringId: Int): String
    fun getString(stringId: Int, vararg args: Any): String
}