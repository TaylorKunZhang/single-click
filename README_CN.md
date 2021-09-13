[English](README.md) | 简体中文

# SingleClick

在 Android 中优雅处理重复点击的库。

[![Maven Central](https://img.shields.io/maven-central/v/cc.taylorzhang/single-click.svg?style=flat)](https://search.maven.org/artifact/cc.taylorzhang/single-click)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg?style=flat)](LICENSE)

## 下载

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'cc.taylorzhang:single-click:1.0.0'
}
```

## 使用

### View

kotlin:

```kotlin
btn1.onSingleClick {
    // 处理单次点击
}

btn2.onSingleClick(interval = 2000, isShareSingleClick = false) {
    // 处理单次点击
}
```

java:

```java
SingleClickUtil.onSingleClick(btn1, v -> {
    // 处理单次点击
});

SingleClickUtil.onSingleClick(btn2, 2000, false, v -> {
    // 处理单次点击
});
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

kotlin:

```kotlin
class YourViewModel : ViewModel() {

    fun handleClick() {
        // 处理单次点击
    }
}
```

java:

```java
public class YourViewModel extends ViewModel {

    public void handleClick() {
        // 处理单次点击
    }
}
```

### 富文本

kotlin:

```kotlin
tvText.movementMethod = LinkMovementMethod.getInstance()
tvText.highlightColor = Color.TRANSPARENT
tvText.text = buildSpannedString {
    append("normalText")
    onSingleClick({
        // 处理单次点击
    }) {
        color(Color.GREEN) { append("clickText") }
    }
}
```

java:

```java
tvText.setMovementMethod(LinkMovementMethod.getInstance());
tvText.setHighlightColor(Color.TRANSPARENT);
SpannedString spannedString = SpannableStringBuilderKt.buildSpannedString(builder -> {
    builder.append("normalText");
    SingleClickUtil.onSingleClick(builder, v -> {
        // 处理单次点击
    }, builder1 -> {
        SpannableStringBuilderKt.color(builder1, Color.GREEN, builder2 -> {
            builder.append("clickText");
            return null;
        });
        return null;
    });
    return null;
});
tvText.setText(spannedString);
```

### RecyclerView

适配器使用 [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)，如果你使用其他适配器代码也是类似的。

Kotlin Item 点击:

```kotlin
adapter.setOnItemClickListener { _, view, _ ->
    view.determineTriggerSingleClick {
        // 处理单次点击
    }
}
```

Kotlin Item Child 点击:

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

Java Item 点击:

```java
adapter.setOnItemClickListener((adapter1, view, position) -> {
    SingleClickUtil.determineTriggerSingleClick(view, v -> {
        // 处理普通点击
    });
});
```

Java Item Child 点击:

```java
adapter.setOnItemChildClickListener((adapter1, view, position) -> {
    if (view.getId() == R.id.btn1) {
        // handle normal click
    } else if (view.getId() == R.id.btn2) {
        SingleClickUtil.determineTriggerSingleClick(view, v -> {
            // 处理单次点击
        });
    }
});
```

## License

[Apache license 2.0](LICENSE) © Taylor Zhang