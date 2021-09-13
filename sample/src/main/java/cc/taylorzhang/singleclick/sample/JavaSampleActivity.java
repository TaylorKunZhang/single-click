package cc.taylorzhang.singleclick.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannedString;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.SpannableStringBuilderKt;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;

import cc.taylorzhang.singleclick.SingleClickUtil;
import cc.taylorzhang.singleclick.sample.databinding.ActivityJavaSampleBinding;

public class JavaSampleActivity extends AppCompatActivity {

    private ActivityJavaSampleBinding mBinding;

    private JavaSampleViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_java_sample);
        mViewModel = new ViewModelProvider(getViewModelStore(), getDefaultViewModelProviderFactory())
                .get(JavaSampleViewModel.class);
        mBinding.setViewModel(mViewModel);

        initButton();
        initRecyclerView();
        initTextView();
    }

    private void initButton() {
        SingleClickUtil.onSingleClick(mBinding.btn1, v -> {
            LogUtil.i("btn1 clicked");
        });

        SingleClickUtil.onSingleClick(mBinding.btn2, 2000, false, v -> {
            LogUtil.i("btn2 clicked");
        });
    }

    private void initRecyclerView() {
        String[] array = getResources().getStringArray(R.array.list);
        ArrayList<String> list = new ArrayList<>(Arrays.asList(array));
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(
                android.R.layout.simple_list_item_1, list
        ) {

            @Override
            protected void convert(@NonNull BaseViewHolder holder, String item) {
                TextView text1 = holder.getView(android.R.id.text1);
                text1.setTextSize(20);
                text1.setText(item);
                text1.setGravity(Gravity.CENTER);
            }
        };
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            SingleClickUtil.determineTriggerSingleClick(view, v -> {
                LogUtil.i(adapter.getItem(position) + " clicked");
            });
        });
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerView.setAdapter(adapter);
    }

    private void initTextView() {
        mBinding.tvText.setMovementMethod(LinkMovementMethod.getInstance());
        mBinding.tvText.setHighlightColor(Color.TRANSPARENT);
        SpannedString spannedString = SpannableStringBuilderKt.buildSpannedString(builder -> {
            builder.append("normalText");
            SingleClickUtil.onSingleClick(builder, v -> {
                LogUtil.i("clickText clicked");
            }, builder1 -> {
                SpannableStringBuilderKt.color(builder1, Color.GREEN, builder2 -> {
                    builder.append("clickText");
                    return null;
                });
                return null;
            });
            return null;
        });
        mBinding.tvText.setText(spannedString);
    }
}