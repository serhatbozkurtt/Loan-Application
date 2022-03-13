package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class Data_egitim(
    @SerializedName("ParameterInfo" ) var ParameterInfo : ArrayList<ParameterInfo> = arrayListOf()

)

