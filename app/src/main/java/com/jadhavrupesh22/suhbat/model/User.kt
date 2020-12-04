package com.jadhavrupesh22.suhbat.model

import android.hardware.camera2.CameraManager
import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class User(
    var uid: String = "",
    var username: String = "",
    var tokenId: String = "",
    var emailId: String = "",
    var password: String = "",
    var profileUrl: String = "",
    var lastSeen: Timestamp = Timestamp(Date(System.currentTimeMillis())),
    var available: Boolean = false
) : Parcelable