package cc.taylorzhang.singleclick

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/21
 *     desc   : Single click test Activity.
 *     version: 1.0.0
 * </pre>
 */
class SingleClickTestActivity : Activity() {

    lateinit var btn1: Button

    lateinit var btn2: Button

    lateinit var tvText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val group = LinearLayout(this)
        group.orientation = LinearLayout.VERTICAL

        btn1 = Button(this)
        btn1.text = "btn1"
        group.addView(btn1)

        btn2 = Button(this)
        btn2.text = "btn2"
        group.addView(btn2)

        tvText = TextView(this)
        group.addView(tvText)

        setContentView(group)
    }
}