package com.example.expandablenotification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var notifManager : NotificationManager
    lateinit var notifChannel : NotificationChannel
    lateinit var notifBuilder : Notification.Builder
    private val description = "Some Description"
    private val channelID = "Some Channel ID"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.btn)

        notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val someintent = Intent(this,LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,someintent,PendingIntent.FLAG_UPDATE_CURRENT)

        btn.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notifChannel = NotificationChannel(channelID,description,NotificationManager.IMPORTANCE_HIGH)
                notifChannel.enableLights(true)
                notifChannel.lightColor = Color.RED
                notifChannel.enableVibration(true)

                notifManager.createNotificationChannel(notifChannel)

                val myBitmap = BitmapFactory.decodeResource(resources,R.drawable.download)
                
                notifBuilder = Notification.Builder(this,channelID)
                        .setContentTitle("Some Title")
                        .setContentText("Some Content Text")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setStyle(Notification.BigPictureStyle().bigPicture(myBitmap))
                        .setContentIntent(pendingIntent)


            }else{
                notifBuilder = Notification.Builder(this)
                        .setContentTitle("Some Title")
                        .setContentText("Some Content Text")
                        .setContentIntent(pendingIntent)
            }
            notifManager.notify(1234,notifBuilder.build())
        }
    }
}


