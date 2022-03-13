package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class Data(

        @SerializedName("Currency" ) var Currency  : ArrayList<Currency> = arrayListOf()
)
