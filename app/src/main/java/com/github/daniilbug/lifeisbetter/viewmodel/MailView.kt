package com.github.daniilbug.lifeisbetter.viewmodel

import android.os.Parcel
import android.os.Parcelable
import java.util.*

enum class MailFeedBack {
    GOOD, NEUTRAL, BAD, NONE
    ;
    companion object {
        fun fromNumber(numberFeedback: Int) = when (numberFeedback) {
            -1 -> NONE
            0 -> BAD
            1 -> NEUTRAL
            2 -> GOOD
            else -> error("There is not feedback for that number: $numberFeedback")
        }
    }
}

fun MailFeedBack.toNumber() = when (this) {
    MailFeedBack.NONE -> -1
    MailFeedBack.BAD -> 0
    MailFeedBack.NEUTRAL -> 1
    MailFeedBack.GOOD -> 2
}

data class MailView(
    val id: String,
    val text: String,
    val date: Date,
    val feedBack: MailFeedBack
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readStringWithChecking(),
        parcel.readStringWithChecking(),
        Date(parcel.readLong()),
        MailFeedBack.valueOf(parcel.readStringWithChecking())
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(text)
        parcel.writeLong(date.time)
        parcel.writeString(feedBack.toString())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MailView> {
        override fun createFromParcel(parcel: Parcel): MailView {
            return MailView(parcel)
        }

        override fun newArray(size: Int): Array<MailView?> {
            return arrayOfNulls(size)
        }
    }
}

private fun Parcel.readStringWithChecking(): String {
    val str = readString()
    checkNotNull(str)
    return str
}