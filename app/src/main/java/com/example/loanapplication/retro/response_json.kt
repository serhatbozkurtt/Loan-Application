package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class response_json(
        @SerializedName("Header" ) var Header: Header? = Header(),
        @SerializedName("Data"   ) var Data : Data ? = Data ()
) {

}
