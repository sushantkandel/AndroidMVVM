package com.example.mvvmprac.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_USER_ID = 0

@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var emailVerifiedAt: String? = null,
    var CreatedAt: String? = null,
    var UpdatedAt: String? = null
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}