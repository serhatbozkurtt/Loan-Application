package com.example.loanapplication.retro

import com.google.gson.annotations.SerializedName

data class kredi_istek(
        @SerializedName("IdentityNumber"   ) var IdentityNumber   : String? = null,
        @SerializedName("PhoneNumber"      ) var PhoneNumber      : String? = null,
        @SerializedName("LoanAmount"       ) var LoanAmount       : Int?    = null,
        @SerializedName("LoanTerm"         ) var LoanTerm         : Int?    = null,
        @SerializedName("MonthlyIncome"    ) var MonthlyIncome    : Int?    = null,
        @SerializedName("EmploymentStatus" ) var EmploymentStatus : Int?    = null,
        @SerializedName("ResidenceType"    ) var ResidenceType    : Int?    = null,
        @SerializedName("Sector"           ) var Sector           : Int?    = null,
        @SerializedName("ProfessionCode"   ) var ProfessionCode   : Int?    = null,
        @SerializedName("EducationLevel"   ) var EducationLevel   : Int?    = null,
        @SerializedName("TitleCode"        ) var TitleCode        : Int?    = null
)
