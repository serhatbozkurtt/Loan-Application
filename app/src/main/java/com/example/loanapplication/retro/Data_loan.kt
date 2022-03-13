package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class Data_loan(
      //@SerializedName("ApplicationResult " ) var ApplicationResult  : ArrayList<ApplicationResult> = arrayListOf()
        @SerializedName("ApplicationResult" ) var ApplicationResult : ApplicationResult? = ApplicationResult()

)
