English | [简体中文](README_CN.md)

# SingleClick

A library that gracefully handles repeated clicks in Android.

[![Download](https://api.bintray.com/packages/taylorzhang/maven/single-click/images/download.svg?style=flat)](https://bintray.com/taylorzhang/maven/single-click/)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg?style=flat)](LICENSE)

## Download

```groovy
dependencies {
    implementation 'cc.taylorzhang:single-click:1.0.0'
}
```

## Usage

### View

```kotlin
mBinding.btn1.onSingleClick {
    // handle single click
}

mBinding.btn2.onSingleClick(interval = 2000, isShareSingleClick = false) {
    // handle single click
}
```

### DataBinding

layout:

```xml
<androidx.appcompat.widget.AppCompatButton
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:text="@string/btn"
    app:isShareSingleClick="@{false}"
    app:onSingleClick="@{()->viewModel.handleClick()}"
    app:singleClickInterval="@{2000}" />
```

kotlin:

```kotlin
class YourViewModel : ViewModel() {

    fun handleClick() {
        // handle single click
    }
}
```

### Rich Text

```kotlin
mBinding.tvText.movementMethod = LinkMovementMethod.getInstance()
mBinding.tvText.highlightColor = Color.TRANSPARENT
mBinding.tvText.text = buildSpannedString {
    append("normalText")
    onSingleClick({
        // handle single click
    }) {
        color(Color.GREEN) { append("clickText") }
    }
}
```

### RecyclerView

Adapter use [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper), the code is similar if you use other adapters.

Item Click:

```kotlin
adapter.setOnItemClickListener { _, view, _ ->
    view.determineTriggerSingleClick {
        // handle single click
    }
}
```

Item Child Click:

```kotlin
adapter.addChildClickViewIds(R.id.btn1, R.id.btn2)
adapter.setOnItemChildClickListener { _, view, _ ->
    when (view.id) {
        R.id.btn1 -> {
            // handle normal click
        }
        R.id.btn2 -> view.determineTriggerSingleClick {
            // handle single click
        }
    }
}
```

## License

[Apache license 2.0](LICENSE) © Taylor Zhang