package com.myqr.myqrcodescaner

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class CreateQRActivity : AppCompatActivity() {
    var edittext:EditText?=null
    var button: Button?=null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_qractivity)
        edittext=findViewById(R.id.editText)
        button=findViewById(R.id.button)
        button?.setOnClickListener {
            var str=edittext?.text.toString()
            Toast.makeText(this,"$str",Toast.LENGTH_LONG).show()
            try {
                val barcodeEncoder = BarcodeEncoder()
                val bitmap = barcodeEncoder.encodeBitmap("$str", BarcodeFormat.QR_CODE, 400, 400)
                val imageViewQrCode: ImageView = findViewById<View>(R.id.imageViewQrCode) as ImageView
                imageViewQrCode.setImageBitmap(bitmap)
            } catch (e: Exception) {
            }
        }


    }
}