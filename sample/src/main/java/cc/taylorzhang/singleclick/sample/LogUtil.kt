package cc.taylorzhang.singleclick.sample

import android.util.Log

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/20
 *     desc   : Log util.
 *     version: 1.0.0
 * </pre>
 */
object LogUtil {

    private var mLogFlag = false

    fun i(any: Any) {
        if (mLogFlag) {
            Log.i(". SingleClick", any.toString())
        } else {
            Log.i(" .SingleClick", any.toString())
        }
        mLogFlag = !mLogFlag
    }
}