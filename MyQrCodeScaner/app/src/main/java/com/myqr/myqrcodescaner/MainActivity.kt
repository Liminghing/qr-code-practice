package com.myqr.myqrcodescaner

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class MainActivity : AppCompatActivity() {
    var Button_1:ImageView?=null
    var Button_2:ImageView?=null


    private val WRITE_EXTERNAL_STORAGE_REQUEST = 1
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 권한이 부여되지 않았을 경우 권한 요청
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_STORAGE_REQUEST
            )
        } else {
            // 이미 권한이 부여된 경우 파일 저장 등의 작업 수행
        }



        Button_1=findViewById(R.id.bt1)
        Button_2=findViewById(R.id.bt2)

        Button_1?.setOnClickListener {
            val intent = Intent( this, scanner_activity_1::class.java )
            startActivity(intent)
        }
        Button_2?.setOnClickListener {
            val intent = Intent(this, CustomBarcodeScannerActivity::class.java)
            startActivity(intent)
        }

    }
    // 커스텀 스캐너 실행하기
    // Custom SCAN - onClick
    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            WRITE_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 부여된 경우
                    // 권한이 필요한 작업 수행
                } else {
                    // 권한이 거부된 경우
                    // 사용자에게 안내 또는 다른 조치 수행
                }
            }
        }
    }
}