package com.example.girlquotesapp

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import java.io.File
import java.io.FileOutputStream
import java.util.*

class EditActivity : AppCompatActivity() {

    lateinit var imgClick: ImageView
    lateinit var imgBack: ImageView
    lateinit var imgSave: ImageView
    lateinit var txtEditQuotes: TextView
    lateinit var imgShare: ImageView
    lateinit var imgCopy: ImageView
    lateinit var imgAdd: ImageView
    lateinit var txtCatagoryNamm: TextView
    lateinit var builder: Notification.Builder
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    private val channelId="i.apps.notification"
    private val description="Test notification"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        
            initView()
    }

    private fun initView() {

        imgAdd = findViewById(R.id.imgAdd)
        imgClick = findViewById(R.id.imgClick)
        imgBack = findViewById(R.id.imgBack)
        imgShare = findViewById(R.id.imgShare)
        imgCopy = findViewById(R.id.imgCopy)
        imgSave = findViewById(R.id.imgSave)
        txtEditQuotes = findViewById(R.id.txtEditQuotes)
        txtCatagoryNamm = findViewById(R.id.txtCatagoryNamm)

        val imgList = ArrayList<Int>()
        imgList.add(R.drawable.bg_one)
        imgList.add(R.drawable.img1)
        imgList.add(R.drawable.img2)
        imgList.add(R.drawable.img3)
        imgList.add(R.drawable.img4)
        imgList.add(R.drawable.img5)
        imgList.add(R.drawable.bgseven)
        imgList.add(R.drawable.bgeight)

        var count = 0
        imgClick.setOnClickListener {
            if (count == 8) {
                count = 1
            }
            imgClick.setBackgroundResource(imgList[count])
            count++
        }

        val catName: String? = intent.getStringExtra("catagoryE")
        txtCatagoryNamm.text = catName.toString()

        val quotess: String? = intent.getStringExtra("quotes")
        txtEditQuotes.text = quotess.toString()

        imgBack.setOnClickListener {

            finish()
        }
        imgShare.setOnClickListener {

            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, quotess)
            startActivity(Intent.createChooser(shareIntent, "share via"))

        }

        imgCopy.setOnClickListener {

            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", quotess)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "Quotes copied: ", Toast.LENGTH_SHORT).show()

        }

        imgAdd.setOnClickListener {

            gallery()
        }

        imgSave.setOnClickListener {

            save()

        }
    }

    private fun save() {


    }

    fun gallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Please Select your picture"),
            101
        )
    }

    fun camera() {
        val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(i, 100)
    }
}