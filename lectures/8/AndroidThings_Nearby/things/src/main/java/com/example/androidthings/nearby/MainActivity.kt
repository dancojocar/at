package com.example.androidthings.nearby

import android.app.Activity
import android.os.Bundle

class MainActivity : Activity() {
  private lateinit var advManager: NearbyAdvManager
  private lateinit var lcdManager: LCDManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    logi("Starting Android Things app...")
    advManager = NearbyAdvManager(this, object : NearbyAdvManager.EventListener {
      override fun onMessage(message: String) {
        logi("received message: $message")
        lcdManager.displayString(message)
      }
    })
  }

  override fun onStart() {
    super.onStart()
    lcdManager = LCDManager()
    lcdManager.displayString("Hello world!")

  }

  override fun onPause() {
    super.onPause()
    lcdManager.close()
  }

  override fun onStop() {
    super.onStop()
    advManager.close()
  }
}
