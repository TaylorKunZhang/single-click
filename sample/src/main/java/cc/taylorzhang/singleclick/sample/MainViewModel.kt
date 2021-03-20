package cc.taylorzhang.singleclick.sample

import androidx.lifecycle.ViewModel

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/20
 *     desc   : Main Activity View Model
 *     version: 1.0.0
 * </pre>
 */
class MainViewModel : ViewModel() {

    fun handleBtn3Click() {
        LogUtil.i("btn3 clicked")
    }

    fun handleBtn4Click() {
        LogUtil.i("btn4 clicked")
    }
}