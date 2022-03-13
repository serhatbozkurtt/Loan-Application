package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class Header(
        @SerializedName("StatusCode"          ) var StatusCode          : String? = null,
        @SerializedName("StatusDescription"   ) var StatusDescription   : String? = null,
        @SerializedName("StatusDescriptionEn" ) var StatusDescriptionEn : String? = null,
        @SerializedName("ObjectID"            ) var ObjectID            : String? = null

)
