package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class ApplicationResult(
        @SerializedName("StatusDescription"           ) var StatusDescription           : String? = null,
        @SerializedName("ApplicationNumber"           ) var ApplicationNumber           : String? = null,
        @SerializedName("StatusCode"                  ) var StatusCode                  : String? = null,
        @SerializedName("FailedStatusDescription"     ) var FailedStatusDescription     : String? = null,
        @SerializedName("FailedStatusCode"            ) var FailedStatusCode            : String? = null,
        @SerializedName("AssessmentStatusCode"        ) var AssessmentStatusCode        : String? = null,
        @SerializedName("AssessmentStatusDescription" ) var AssessmentStatusDescription : String? = null

)
