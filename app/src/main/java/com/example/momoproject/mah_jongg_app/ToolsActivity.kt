package com.example.momoproject.mah_jongg_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class ToolsActivity :AppCompatActivity() {
    private lateinit var okButton: Button
    private lateinit var pointButton: Button
    private lateinit var markButton: Button

    private lateinit var basicPointTextView: TextView
    private lateinit var parentTextView: TextView
    private lateinit var childTextView: TextView

    private lateinit var translateSpinner: Spinner
    private lateinit var markSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.point_tools)
        okButton = findViewById(R.id.calculationButton)
        pointButton = findViewById(R.id.pointTable)
        markButton = findViewById(R.id.markTable)
        basicPointTextView=findViewById(R.id.basicText)
        parentTextView=findViewById(R.id.pearentText)
        childTextView=findViewById(R.id.childText)
        translateSpinner=findViewById(R.id.hanSpinner)
        markSpinner=findViewById(R.id.markSpinner)



    }
}