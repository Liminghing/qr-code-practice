package com.myqr.myqrcodescaner

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract.CommonDataKinds.Im
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class scanner_activity_1 : AppCompatActivity() {
    var edittext: EditText?=null
    var go_bt: ImageView?=null
    var down_bt: ImageView?=null
    var share_bt: ImageView?=null
    var back_bt_1:ImageView?=null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner1)
        var barcodeBitmap: Bitmap?=null
        edittext=findViewById(R.id.editTx)
        go_bt=findViewById(R.id.go_bt)
        down_bt=findViewById(R.id.down_bt)
        share_bt=findViewById(R.id.share_bt)
        var bitmap:Bitmap?=null
        back_bt_1=findViewById(R.id.back_bt_1)
        back_bt_1?.setOnClickListener {
            finish()
        }
        go_bt?.setOnClickListener {
            var str=edittext?.text.toString()
            try {
                val barcodeEncoder = BarcodeEncoder()
                bitmap = barcodeEncoder.encodeBitmap("$str", BarcodeFormat.QR_CODE, 400, 400)
                val imageViewQrCode: ImageView = findViewById<View>(R.id.imageqr) as ImageView
                imageViewQrCode.setImageBitmap(bitmap)
            } catch (e: Exception) {
            }
        }
        share_bt?.setOnClickListener {
            if (bitmap!=null){
                shareGeneratedBitmap(bitmap!!)
            }
        }
        down_bt?.setOnClickListener {
            if (bitmap!=null){
                saveBitmapToGallery(this,bitmap!!,"ddd")
                Toast.makeText(this,"Qr code saved to gallery",Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun shareGeneratedBitmap(bitmap: Bitmap) {
        val bitmapPath = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Generated Qrcode", null)
        val bitmapUri = Uri.parse(bitmapPath)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
        startActivity(Intent.createChooser(intent, "Share Generated Qrcode"))
    }

    fun saveBitmapToGallery(context: Context, bitmap: Bitmap, displayName: String) {
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }

        val resolver = context.contentResolver
        var imageUri: Uri? = null
        resolver.insert(imageCollection, contentValues)?.also { uri ->
            imageUri = uri
        }

        imageUri?.let { uri ->
            saveBitmapToFile(context, bitmap, uri)
        }
    }

    private fun saveBitmapToFile(context: Context, bitmap: Bitmap, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outputStream ->
            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, outputStream)) {
                throw IOException("Could not save bitmap.")
            }
        }
    }

    private inline fun <T> sdk29AndUp(onSdk29: () -> T): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            onSdk29()
        } else {
            null
        }
    }


}