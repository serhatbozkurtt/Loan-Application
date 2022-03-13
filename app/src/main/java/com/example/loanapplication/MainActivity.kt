package com.example.loanapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.loanapplication.retro.getParameter.getParameter
import com.example.loanapplication.retro.kredi_istek
import com.example.loanapplication.retro.kredi_req_interface.getKrediCevap
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var key = ""

    var IdentityNumber = ""
    var PhoneNumber = ""
    var LoanAmount = 0
    var LoanTerm = 0
    var MonthlyIncome = 0
    var ResidenceType = 0
    var EmploymentStatus = 0
    var Sector = 0
    var ProfessionCode = 0
    var TitleCode = 0
    var EducationLevel = 0


    private var MSTEgitimDuzeyi = mutableMapOf(0 to "MSTEgitimDuzeyi")
    var MSTEgitimDuzeyi_list = ArrayList<String>()
    private var MSTCalistigiYerdekiUnvani = mutableMapOf(0 to "MSTCalistigiYerdekiUnvani")
    var MSTCalistigiYerdekiUnvani_list = ArrayList<String>()
    private var MSTMusteriSektor = mutableMapOf(0 to "MSTMusteriSektor")
    var MSTMusteriSektor_list = ArrayList<String>()
    private var MSTCalismaDurumu = mutableMapOf(0 to "MSTCalismaDurumu")
    var MSTCalismaDurumu_list = ArrayList<String>()
    private var MSTMeslekKodu2 = mutableMapOf(0 to "MSTMeslekKodu2")
    var MSTMeslekKodu2_list = ArrayList<String>()
    private var BKRIkametDurumu = mutableMapOf(0 to "BKRIkametDurumu")
    var BKRIkametDurumu_list = ArrayList<String>()
    private var kredi_response = mutableMapOf(0 to "kredi_response")
    var kredi_response_list = ArrayList<String>()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitle(R.string.title)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        SearchSpinner()
        SearchSpinner2()
        SearchSpinner3()
        SearchSpinner4()
        SearchSpinner5()
        SearchSpinner6()
        submit()
        //popup ()

        Thread {
            //Do some Network Request
            MSTCalistigiYerdekiUnvani = getParameter("MSTCalistigiYerdekiUnvani") as MutableMap<Int, String>
            MSTCalistigiYerdekiUnvani_list.addAll(MSTCalistigiYerdekiUnvani.values)
            MSTCalismaDurumu = getParameter("MSTCalismaDurumu") as MutableMap<Int, String>
            MSTCalismaDurumu_list.addAll(MSTCalismaDurumu.values)
            MSTEgitimDuzeyi = getParameter("MSTEgitimDuzeyi") as MutableMap<Int, String>
            MSTEgitimDuzeyi_list.addAll(MSTEgitimDuzeyi.values)
            MSTMeslekKodu2 = getParameter("MSTMeslekKodu2") as MutableMap<Int, String>
            MSTMeslekKodu2_list.addAll(MSTMeslekKodu2.values)
            BKRIkametDurumu = getParameter("BKRIkametDurumu") as MutableMap<Int, String>
            BKRIkametDurumu_list.addAll(BKRIkametDurumu.values)
            MSTMusteriSektor = getParameter("MSTMusteriSektor") as MutableMap<Int, String>
            MSTMusteriSektor_list.addAll(MSTMusteriSektor.values)

            runOnUiThread{
                //Update UI
            }
        }.start()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun submit() {

        submitButton.setOnClickListener(){

            IdentityNumber = identityEditText.text.toString()
            PhoneNumber = phoneEditText.text.toString()
            key = LoanAmountEditText.text.toString()
            LoanAmount = key.toInt()
            key  = loanTermEditText.text.toString()
            LoanTerm = key.toInt()
            key  = MonthlyIncomeEditText.text.toString()
            MonthlyIncome = key.toInt()

            val kredi_req = kredi_istek(IdentityNumber, PhoneNumber, LoanAmount, LoanTerm,
                    MonthlyIncome, EmploymentStatus, ResidenceType, Sector, ProfessionCode, EducationLevel, TitleCode)

            val json_kredi = Gson().toJson(kredi_req)
            println("json_kredi -->> " + json_kredi)

            Thread {


                kredi_response = getKrediCevap(kredi_req) as MutableMap<Int, String>
                println("kredi_response.get(1) --- >" + kredi_response.get(1))

                val status_descp =  kredi_response.get(2)
                val fstatus_descp = kredi_response.get(1)
                val intent = Intent(this, PopUpWindow::class.java)

                intent.putExtra("StatusDescription", status_descp)
                intent.putExtra("FailedStatusDescription", fstatus_descp)
                intent.putExtra("darkstatusbar", false)
                startActivity(intent)
                runOnUiThread{
                    //Update UI
                }
            }.start()

        }



    }

    fun SearchSpinner6()  {
        val searchmethod = ArrayAdapter(this, android.R.layout.simple_spinner_item, MSTMeslekKodu2_list)
        searchmethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        meslek_kodu!!.adapter = searchmethod


        meslek_kodu.setTitle("Müşterinin Mesleği")

        meslek_kodu.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                println(item)
                println("list item " + MSTMeslekKodu2_list)
                println(" key of item " + MSTMeslekKodu2.filterValues { it == item }.keys)

                val reversed = MSTMeslekKodu2.entries.associate{ (k,v)-> v to k}

                ProfessionCode = reversed[item]!!

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    fun SearchSpinner5()  {
        val searchmethod = ArrayAdapter(this, android.R.layout.simple_spinner_item, MSTMusteriSektor_list)
        searchmethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sektor!!.adapter = searchmethod


        sektor.setTitle("Müşterinin Çalıştığı Sektör")

        sektor.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                println(item)
                println("list item " + MSTMusteriSektor_list)
                println(" key of item " + MSTMusteriSektor.filterValues { it == item }.keys)

                val reversed = MSTMusteriSektor.entries.associate{ (k,v)-> v to k}

                Sector = reversed[item]!!
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    fun SearchSpinner4()  {
        val searchmethod = ArrayAdapter(this, android.R.layout.simple_spinner_item, BKRIkametDurumu_list)
        searchmethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ikametgah!!.adapter = searchmethod


        ikametgah.setTitle("Müşterinin İkametgah Tipi")

        ikametgah.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                println(item)
                println("list item " + BKRIkametDurumu_list)
                println(" key of item " + BKRIkametDurumu.filterValues { it == item }.keys)

                val reversed = BKRIkametDurumu.entries.associate{ (k,v)-> v to k}

                ResidenceType = reversed[item]!!
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    fun SearchSpinner3()  {
        val searchmethod = ArrayAdapter(this, android.R.layout.simple_spinner_item, MSTCalismaDurumu_list)
        searchmethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        calismadurumu!!.adapter = searchmethod


        calismadurumu.setTitle("Müşterinin Çalışma Durumu")

        calismadurumu.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                println(item)
                println("list item " + MSTCalismaDurumu_list)
                println(" key of item " + MSTCalismaDurumu.filterValues { it == item }.keys)

                val reversed = MSTCalismaDurumu.entries.associate{ (k,v)-> v to k}

                EmploymentStatus = reversed[item]!!
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    fun SearchSpinner2()  {
        val searchmethod = ArrayAdapter(this, android.R.layout.simple_spinner_item, MSTCalistigiYerdekiUnvani_list)
        searchmethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        unvan_kodu!!.adapter = searchmethod


        unvan_kodu.setTitle("Müşterinin Unvanı")

        unvan_kodu.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                println(item)
                println("list item " + MSTCalistigiYerdekiUnvani_list)
                println(" key of item " + MSTCalistigiYerdekiUnvani.filterValues { it == item }.keys)
                val reversed = MSTCalistigiYerdekiUnvani.entries.associate{ (k,v)-> v to k}

                TitleCode = reversed[item]!!


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

    fun SearchSpinner()  {
        val searchmethod = ArrayAdapter(this, android.R.layout.simple_spinner_item, MSTEgitimDuzeyi_list)
        searchmethod.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        egitim_kodu!!.adapter = searchmethod
        unvan_kodu.setTitle("Müşterinin Eğitim Düzeyi")

        egitim_kodu.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                val item = parent.getItemAtPosition(pos)
                println(item)
                println("list item " + MSTEgitimDuzeyi_list)
                println(" key of item " + MSTEgitimDuzeyi.filterValues { it == item }.keys)
                val reversed = MSTEgitimDuzeyi.entries.associate{ (k,v)-> v to k}

                EducationLevel = reversed[item]!!

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

    }

}

private fun Intent.putExtra(ıdentityNumber: String?) {

}
