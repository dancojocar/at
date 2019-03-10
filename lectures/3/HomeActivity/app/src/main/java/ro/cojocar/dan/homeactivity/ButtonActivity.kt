package ro.cojocar.dan.homeactivity

import android.app.Activity
import android.os.Bundle
import com.google.android.things.pio.Gpio
import com.google.android.things.pio.GpioCallback
import com.google.android.things.pio.PeripheralManager
import java.io.IOException


/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class ButtonActivity : Activity() {
  private companion object {
    const val BUTTON_PIN_NAME = "GPIO2_IO07" // GPIO port wired to the button
  }

  private lateinit var buttonGpio: Gpio

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val manager = PeripheralManager.getInstance()
    try {
      // Step 1. Create GPIO connection.
      buttonGpio = manager.openGpio(BUTTON_PIN_NAME)
      // Step 2. Configure as an input.
      buttonGpio.setDirection(Gpio.DIRECTION_IN)
      // Step 3. Enable edge trigger events.
      buttonGpio.setEdgeTriggerType(Gpio.EDGE_FALLING)
      // Step 4. Register an event callback.
      buttonGpio.registerGpioCallback(mCallback)
    } catch (e: IOException) {
      loge("Error on PeripheralIO API", e)
    }
  }

  // Step 4. Register an event callback.
  private val mCallback = GpioCallback {
    logi("GPIO changed, button pressed")
    // Step 5. Return true to keep callback active.
    true
  }

  override fun onDestroy() {
    super.onDestroy()

    // Step 6. Close the resource
    buttonGpio.unregisterGpioCallback(mCallback)
    try {
      buttonGpio.close()
    } catch (e: IOException) {
      loge("Error on PeripheralIO API", e)
    }
  }
}
