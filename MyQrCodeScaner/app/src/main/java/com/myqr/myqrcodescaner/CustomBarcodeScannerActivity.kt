package com.myqr.myqrcodescaner

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions

class CustomBarcodeScannerActivity : AppCompatActivity() {

    private lateinit var captureManager: CaptureManager
    private lateinit var decoratedBarcodeView: DecoratedBarcodeView
    private lateinit var btnFlash : ImageView
    private lateinit var btnChange : ImageView
    var back_bt_:ImageView?=null
    private var isFlash : Boolean = false   // 플래시가 켜져있는지 확인하기 위한 변수


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_barcode_scanner)

        decoratedBarcodeView = findViewById(R.id.decoratedBarcodeView)  // 커스텀 바코드 뷰
        btnFlash = findViewById(R.id.btnFlash)          // 플래시 버튼
        btnChange = findViewById(R.id.btnChange)
        back_bt_ = findViewById(R.id.back_bt_1_)
        back_bt_?.setOnClickListener {
            finish()
        }
        //back_cam()

        captureManager = CaptureManager( this, decoratedBarcodeView)
        captureManager.initializeFromIntent( intent, savedInstanceState )
        captureManager.setShowMissingCameraPermissionDialog(true,"카메라 권한 요청")	// 권한요청 다이얼로그 보이게 할 지 말 지
        captureManager.decode()		// decoding 시작
        decoratedBarcodeView.decodeContinuous(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                Toast.makeText(this@CustomBarcodeScannerActivity,"$result",Toast.LENGTH_SHORT).show()
                var str=result.toString()
                val intent = Intent(this@CustomBarcodeScannerActivity,wedview::class.java)
                intent.putExtra("str", str)
                startActivity(intent)
            }

            override fun possibleResultPoints(resultPoints: List<com.google.zxing.ResultPoint>) {

            }
        })

        btnChange.setOnClickListener {
            changeCamera()
        }
        // 플래시 버튼 클릭 ?
        btnFlash.setOnClickListener {
            if( !isFlash ) {
                // 플래시가 현재 꺼져있을 때 버튼이 눌렸다면 플래시를 켠다.

                isFlash = true
                decoratedBarcodeView.setTorchOn()

            }
            else {
                // 플래시가 현재 켜져있을 때 버튼이 눌렸다면 플래시를 끈다.

                isFlash = false
                decoratedBarcodeView.setTorchOff()
            }
        }
    }
    private fun changeCamera(){
        decoratedBarcodeView.run {

            pause()

            when(barcodeView.cameraSettings.requestedCameraId){
                1 -> barcodeView.cameraSettings.requestedCameraId = 0
                else -> barcodeView.cameraSettings.requestedCameraId = 1
            }

            resume()
        }
    }
// LifeCycle 에 따라 CaptureManager 또한 처리해주어야 한다.

    override fun onResume() {
        super.onResume()
        captureManager.onResume()
    }

    override fun onPause() {
        super.onPause()
        captureManager.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        captureManager.onDestroy()
    }

    // onSaveInstanceState ? 또한 처리해주어야 한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        captureManager.onSaveInstanceState(outState)
    }

    // 카메라 권한을 요청할 수 있기 때문에
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        captureManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}