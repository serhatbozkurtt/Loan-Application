package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class loan_response(

        @SerializedName("Header" ) var Header : Header? = Header(),
        @SerializedName("Data"   ) var Data   : Data_loan?   = Data_loan(),
)
