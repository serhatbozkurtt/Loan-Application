package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class ParameterInfo(

    @SerializedName("Description"   ) var Description   : String? = null,
    @SerializedName("GroupCode"     ) var GroupCode     : String? = null,
    @SerializedName("MainGroupCode" ) var MainGroupCode : String? = null,
    @SerializedName("Code"          ) var Code          : String? = null,
    @SerializedName("Detail10"      ) var Detail10      : String? = null,
    @SerializedName("Detail2"       ) var Detail2       : String? = null,
    @SerializedName("Detail1"       ) var Detail1       : String? = null,
    @SerializedName("Detail4"       ) var Detail4       : String? = null,
    @SerializedName("Detail3"       ) var Detail3       : String? = null,
    @SerializedName("Detail6"       ) var Detail6       : String? = null,
    @SerializedName("Detail5"       ) var Detail5       : String? = null,
    @SerializedName("Detail8"       ) var Detail8       : String? = null,
    @SerializedName("Detail7"       ) var Detail7       : String? = null,
    @SerializedName("Detail9"       ) var Detail9       : String? = null

)
