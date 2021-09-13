English | [简体中文](README_CN.md)

# SingleClick

A library that gracefully handles repeated clicks in Android.

[![Maven Central](https://img.shields.io/maven-central/v/cc.taylorzhang/single-click.svg?style=flat)](https://search.maven.org/artifact/cc.taylorzhang/single-click)
[![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)
[![License](https://img.shields.io/badge/License-Apache--2.0-brightgreen.svg?style=flat)](LICENSE)

## Download

```groovy
repositories {
    mavenCentral()
}

dependencies {
    implementation 'cc.taylorzhang:single-click:1.1.0'
}
```

## Usage

### View

kotlin:

```kotlin
btn1.onSingleClick {
    // handle single click
}

btn2.onSingleClick(interval = 2000, isShareSingleClick = false) {
    // handle single click
}
```

java:

```java
SingleClickUtil.onSingleClick(btn1, v -> {
    // handle single click
});

SingleClickUtil.onSingleClick(btn2, 2000, false, v -> {
    // handle single click
});
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

java:

```java
public class YourViewModel extends ViewModel {

    public void handleClick() {
        // handle single click
    }
}
```

### Rich Text

kotlin:

```kotlin
tvText.movementMethod = LinkMovementMethod.getInstance()
tvText.highlightColor = Color.TRANSPARENT
tvText.text = buildSpannedString {
    append("normalText")
    onSingleClick({
        // handle single click
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
        // handle single click
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

Adapter use [BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper), the code is similar if you use other adapters.

Kotlin Item Click:

```kotlin
adapter.setOnItemClickListener { _, view, _ ->
    view.determineTriggerSingleClick {
        // handle single click
    }
}
```

Kotlin Item Child Click:

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

Java Item Click:

```java
adapter.setOnItemClickListener((adapter1, view, position) -> {
    SingleClickUtil.determineTriggerSingleClick(view, v -> {
        // handle single click
    });
});
```

Java Item Child Click:

```java
adapter.setOnItemChildClickListener((adapter1, view, position) -> {
    if (view.getId() == R.id.btn1) {
        // handle normal click
    } else if (view.getId() == R.id.btn2) {
        SingleClickUtil.determineTriggerSingleClick(view, v -> {
            // handle single click
        });
    }
});
```

## License

[Apache license 2.0](LICENSE) © Taylor Zhang