package com.example.momoproject.mah_jongg_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Spinner

class MarkToolActivity : AppCompatActivity() {

    //和了
    private lateinit var completionSpinner: Spinner
    //雀頭
    private lateinit var headSpinner: Spinner
    //待ち
    private lateinit var waitSpinner: Spinner
    //順子
    private lateinit var shuntuTyuTyanSpinner: Spinner
    private lateinit var shuntuTannYaoSpinner: Spinner
    //明刻子
    private lateinit var koutuMeiTyuTyanSpinner: Spinner
    private lateinit var koutuMeiTannYaoSpinner: Spinner
    //暗刻子
    private lateinit var koutuAnTyuTyanSpinner: Spinner
    private lateinit var koutuAnTannYaoSpinner: Spinner
    //明槓子
    private lateinit var kantuMeiTyuTyanSpinner: Spinner
    private lateinit var kantuMeiTannYaoSpinner: Spinner
    //暗槓子
    private lateinit var kantuAnTyuTyanSpinner: Spinner
    private lateinit var kantuAnTannYaoSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mark_tool)
        init()
    }

    private fun init() {
        //和了
        completionSpinner=findViewById(R.id.spinner)
        //雀頭
        headSpinner=findViewById(R.id.spinner2)
        //待ち
        waitSpinner=findViewById(R.id.spinner3)
        //順子
        shuntuTyuTyanSpinner=findViewById(R.id.shunTT)
        shuntuTannYaoSpinner=findViewById(R.id.shunTY)
        //明刻子
        koutuMeiTyuTyanSpinner=findViewById(R.id.kouMTT)
        koutuMeiTannYaoSpinner=findViewById(R.id.kouMTY)
        //暗刻子
        koutuAnTyuTyanSpinner=findViewById(R.id.kouATT)
        koutuAnTannYaoSpinner=findViewById(R.id.kouATY)
        //明槓子
        kantuMeiTyuTyanSpinner=findViewById(R.id.kanMTT)
        kantuMeiTannYaoSpinner=findViewById(R.id.kanMTY)
        //暗槓子
        kantuAnTyuTyanSpinner=findViewById(R.id.kanATT)
        kantuAnTannYaoSpinner=findViewById(R.id.kanATY)
    }
}