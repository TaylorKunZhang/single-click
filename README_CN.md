[English](README.md) | 简体中文

# SingleClick

在 Android 中优雅处理重复点击的库。

[![Download](https://api.bintray.com/packages/taylorzhang/maven/single-click/images/download.svg?style=flat)](https://bintray.com/taylorzhang/maven/single-click/)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg?style=flat)](LICENSE)

## 下载

```groovy
dependencies {
    implementation 'cc.taylorzhang:single-click:1.0.0'
}
```

## 使用

### View

```kotlin
mBinding.btn1.onSingleClick {
    // 处理单次点击
}

mBinding.btn2.onSingleClick(interval = 2000, isShareSingleClick = false) {
    // 处理单次点击
}
```

### 数据绑定

布局:

```xml
<androidx.appcompat.widget.AppCompatButton
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@string/btn"
    app:isShareSingleClick="@{false}"
    app:onSingleClick="@{()->viewModel.handleClick()}"
    app:singleClickInterval="@{2000}" />
```

代码:

```
class YourViewModel : ViewModel() {

    fun handleClick() {
        // 处理单次点击
    }
}
```

### 富文本

```kotlin
mBinding.tvText.movementMethod = LinkMovementMethod.getInstance()
mBinding.tvText.highlightColor = Color.TRANSPARENT
mBinding.tvText.text = buildSpannedString {
    append("normalText")
    onSingleClick({
        // 处理单次点击
    }) {
        color(Color.GREEN) { append("clickText") }
    }
}
```

### RecyclerView

适配器使用 [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)，如果你使用其他适配器代码也是类似的。

Item 点击:

```kotlin
adapter.setOnItemClickListener { _, view, _ ->
    view.determineTriggerSingleClick {
        // 处理单次点击
    }
}
```

Item Child 点击:

```kotlin
adapter.addChildClickViewIds(R.id.btn1, R.id.btn2)
adapter.setOnItemChildClickListener { _, view, _ ->
    when (view.id) {
        R.id.btn1 -> {
            // 处理普通点击
        }
        R.id.btn2 -> view.determineTriggerSingleClick {
            // 处理单次点击
        }
    }
}
```

## License

[Apache license 2.0](LICENSE) © Taylor Zhang