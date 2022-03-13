package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class Currency(

    @SerializedName("CurrencyCode" ) var CurrencyCode : String? = null,
    @SerializedName("RateDate"     ) var RateDate     : String? = null,
    @SerializedName("SaleRate"     ) var SaleRate     : String? = null,
    @SerializedName("PurchaseRate" ) var PurchaseRate : String? = null

)
