package cc.taylorzhang.singleclick.sample;

import androidx.lifecycle.ViewModel;

/**
 * Created by ZhangKun on 2021/9/9.
 */
public class JavaSampleViewModel extends ViewModel {

    public void handleBtn3Click() {
        LogUtil.i("btn3 clicked");
    }

    public void handleBtn4Click() {
        LogUtil.i("btn4 clicked");
    }
}
