package com.newolf.watchinput

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.wear.ambient.AmbientModeSupport
import com.blankj.utilcode.util.ToastUtils
import kotlin.math.roundToInt

class MainActivity : FragmentActivity(), AmbientModeSupport.AmbientCallbackProvider {

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on   change AmbientModeSupport.AmbientCallbackProvider
//        setAmbientEnabled()

        initListener()
    }

    private fun initListener() {
        val llContent = findViewById<LinearLayout>(R.id.llContent)
        val childCount = llContent.childCount - 1

        for (index: Int in 2..childCount) {
            val child: EditText = llContent.getChildAt(index) as EditText

            child.setOnFocusChangeListener { _, hasFocus ->
                /**
                 * Called when the focus state of a view has changed.
                 *
                 * @param hasFocus The new focus state of v.
                 */
                if (hasFocus){
                    showToast(child.hint)
                }
            }

        }

        findViewById<Button>(R.id.btnShowToast).setOnClickListener {
            showToast("btnShowToast")
        }

        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        scrollView.requestFocus()
        scrollView.setOnGenericMotionListener(View.OnGenericMotionListener { v, ev ->
            Log.e(TAG, "initListener: ev = $ev" )
            if (ev.action == MotionEvent.ACTION_SCROLL ) {
                // Don't forget the negation here
               val delta = 0.toDouble()

                // Swap these axes if you want to do horizontal scrolling instead
                Log.e(TAG, "initListener: delta = $delta" )
                v.scrollBy(0, delta.roundToInt())

                return@OnGenericMotionListener true
            }

            false
        })


    }

    private fun showToast(hint: CharSequence?) {
        Log.e(TAG, "ShowToast: hint = $hint .")
        ToastUtils.showShort(hint)
    }

    /**
     * @return the [AmbientModeSupport.AmbientCallback] to be used by this class to communicate with the
     * entity interested in ambient events.
     */
    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {
        return object : AmbientModeSupport.AmbientCallback() {
            /**
             * Called when an activity is entering ambient mode. This event is sent while an activity is
             * running (after onResume, before onPause). All drawing should complete by the conclusion
             * of this method. Note that `invalidate()` calls will be executed before resuming
             * lower-power mode.
             *
             * @param ambientDetails bundle containing information about the display being used.
             * It includes information about low-bit color and burn-in protection.
             */
            override fun onEnterAmbient(ambientDetails: Bundle?) {
                super.onEnterAmbient(ambientDetails)
                Log.e(TAG, "onEnterAmbient: ")
            }

            /**
             * Called when the system is updating the display for ambient mode. Activities may use this
             * opportunity to update or invalidate views.
             */
            override fun onUpdateAmbient() {
                super.onUpdateAmbient()
                Log.e(TAG, "onUpdateAmbient: ")
            }

            /**
             * Called when an activity should exit ambient mode. This event is sent while an activity is
             * running (after onResume, before onPause).
             */
            override fun onExitAmbient() {
                super.onExitAmbient()
                Log.e(TAG, "onExitAmbient: ")
            }

            /**
             * Called to inform an activity that whatever decomposition it has sent to Sidekick is no
             * longer valid and should be re-sent before enabling ambient offload.
             */
            override fun onAmbientOffloadInvalidated() {
                super.onAmbientOffloadInvalidated()
                Log.e(TAG, "onAmbientOffloadInvalidated: ")

            }
        }
    }
}