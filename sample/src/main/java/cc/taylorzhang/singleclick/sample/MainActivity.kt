package cc.taylorzhang.singleclick.sample

import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import cc.taylorzhang.singleclick.determineTriggerSingleClick
import cc.taylorzhang.singleclick.onSingleClick
import cc.taylorzhang.singleclick.sample.databinding.ActivityMainBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/20
 *     desc   : Main activity.
 *     version: 1.0.0
 * </pre>
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private val mViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBinding.viewModel = mViewModel

        initButton()
        initRecyclerView()
        initTextView()
    }

    private fun initButton() {
        mBinding.btn1.onSingleClick {
            LogUtil.i("btn1 clicked")
        }

        mBinding.btn2.onSingleClick(interval = 2000, isShareSingleClick = false) {
            LogUtil.i("btn2 clicked")
        }
    }

    private fun initRecyclerView() {
        val list = resources.getStringArray(R.array.list).toMutableList()
        val adapter = object : BaseQuickAdapter<String, BaseViewHolder>(
            android.R.layout.simple_list_item_1, list
        ) {
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.getView<TextView>(android.R.id.text1).apply {
                    textSize = 20f
                    text = item
                    gravity = Gravity.CENTER
                }
            }
        }
        adapter.setOnItemClickListener { _, view, position ->
            view.determineTriggerSingleClick {
                LogUtil.i("${adapter.getItem(position)} clicked")
            }
        }
        mBinding.recyclerView.layoutManager = LinearLayoutManager(this)
        mBinding.recyclerView.adapter = adapter
    }

    private fun initTextView() {
        mBinding.tvText.movementMethod = LinkMovementMethod.getInstance()
        mBinding.tvText.highlightColor = Color.TRANSPARENT
        mBinding.tvText.text = buildSpannedString {
            append("normalText")
            onSingleClick({
                LogUtil.i("clickText clicked")
            }) {
                color(Color.GREEN) { append("clickText") }
            }
        }
    }
}