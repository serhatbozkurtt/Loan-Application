package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class info_egitim(
    @SerializedName("Header" ) var Header: Header? = Header(),
    @SerializedName("Data" ) var Data: Data_egitim? = Data_egitim(),

)
