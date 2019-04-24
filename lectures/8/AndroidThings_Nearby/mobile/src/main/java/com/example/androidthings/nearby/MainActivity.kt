package com.example.androidthings.nearby

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private lateinit var dsvManager: NearbyDsvManager

  private val listener = object : NearbyDsvManager.EventListener {
    override fun onDiscovered() {
      logi("Endpoint discovered")
      Toast.makeText(this@MainActivity, "Endpoint discovered", Toast.LENGTH_LONG).show()
    }

    override fun startDiscovering() {
      logi("Start discovering...")
      Toast.makeText(this@MainActivity, "Start discovering...", Toast.LENGTH_LONG).show()
    }

    override fun onConnected() {
      logi("Connected")
      Toast.makeText(this@MainActivity, "Connected", Toast.LENGTH_LONG).show()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    btn.setOnClickListener {
      val txt = ed.text.toString()
      logd("Txt [$txt]")
      dsvManager.sendData(txt)
    }
  }

  override fun onStart() {
    super.onStart()

    if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

      logd("Ask for permission")
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
        logd("Permission granted")
        dsvManager = NearbyDsvManager(this, listener)

      } else {
        logd("Permission granted")
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            1410)
      }
    } else {
      logd("Permission granted 1")
      dsvManager = NearbyDsvManager(this, listener)
    }


  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    logd("On Request Permission")
    when (requestCode) {
      1410 -> dsvManager = NearbyDsvManager(this.application, listener)
    }
  }
}
