package cc.taylorzhang.singleclick

import android.text.SpannedString
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.text.buildSpannedString
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/21
 *     desc   : ClickableSpan single click test.
 *     version: 1.0.0
 * </pre>
 */
@RunWith(AndroidJUnit4::class)
class ClickableSpanSingleClickTest {

    @get:Rule
    val rule = ActivityScenarioRule(SingleClickTestActivity::class.java)

    private lateinit var scenario: ActivityScenario<SingleClickTestActivity>

    @Before
    fun setup() {
        scenario = rule.scenario
    }

    @Test
    fun testOneClickableSpanSingleClickInterval() {
        var text1SingleClickedCount = 0
        val interval = 1500

        scenario.onActivity {
            it.tvText.text = buildSpannedString {
                onSingleClick(
                    {
                        text1SingleClickedCount++
                    }, interval = interval
                ) {
                    append("text1")
                }
            }
        }

        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Assert.assertEquals(1, text1SingleClickedCount)

        Thread.sleep(interval.toLong())
        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Assert.assertEquals(2, text1SingleClickedCount)
    }

    @Test
    fun testTwoClickableSpanSingleClickInterval() {
        var text1SingleClickedCount = 0
        var text2SingleClickedCount = 0
        val interval = 1500

        scenario.onActivity {
            it.tvText.text = buildSpannedString {
                onSingleClick(
                    {
                        text1SingleClickedCount++
                    }, interval = interval
                ) {
                    append("text1")
                }
                onSingleClick(
                    {
                        text2SingleClickedCount++
                    }, interval = interval
                ) {
                    append("text2")
                }
            }
        }

        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Espresso.onView(ViewMatchers.withSubstring("text2")).perform(clickClickableSpan("text2"))
        Assert.assertEquals(1, text1SingleClickedCount)
        Assert.assertEquals(0, text2SingleClickedCount)

        Thread.sleep(interval.toLong())
        Espresso.onView(ViewMatchers.withSubstring("text2")).perform(clickClickableSpan("text2"))
        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Assert.assertEquals(1, text1SingleClickedCount)
        Assert.assertEquals(1, text2SingleClickedCount)
    }

    @Test
    fun testOneClickableSpanNotShareSingleClick() {
        var text1SingleClickedCount = 0
        var text2SingleClickedCount = 0

        scenario.onActivity {
            it.tvText.text = buildSpannedString {
                onSingleClick(
                    {
                        text1SingleClickedCount++
                    }, isShareSingleClick = false
                ) {
                    append("text1")
                }
                onSingleClick({ text2SingleClickedCount++ }) { append("text2") }
            }
        }

        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Espresso.onView(ViewMatchers.withSubstring("text2")).perform(clickClickableSpan("text2"))
        Assert.assertEquals(1, text1SingleClickedCount)
        Assert.assertEquals(1, text2SingleClickedCount)
    }

    @Test
    fun testTwoClickableSpanNotShareSingleClick() {
        var text1SingleClickedCount = 0
        var text2SingleClickedCount = 0

        scenario.onActivity {
            it.tvText.text = buildSpannedString {
                onSingleClick(
                    {
                        text1SingleClickedCount++
                    }, isShareSingleClick = false
                ) {
                    append("text1")
                }
                onSingleClick(
                    {
                        text2SingleClickedCount++
                    },
                    isShareSingleClick = false
                ) {
                    append("text2")
                }
            }
        }

        Espresso.onView(ViewMatchers.withSubstring("text1")).perform(clickClickableSpan("text1"))
        Espresso.onView(ViewMatchers.withSubstring("text2")).perform(clickClickableSpan("text2"))
        Assert.assertEquals(1, text1SingleClickedCount)
        Assert.assertEquals(1, text2SingleClickedCount)
    }

    private fun clickClickableSpan(textToClick: CharSequence): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return Matchers.instanceOf(TextView::class.java)
            }

            override fun getDescription(): String {
                return "clicking on a ClickableSpan"
            }

            override fun perform(uiController: UiController?, view: View) {
                val textView = view as TextView
                val spannedString = textView.text as SpannedString
                if (spannedString.isEmpty()) {
                    // TextView is empty, nothing to do
                    throw NoMatchingViewException.Builder()
                        .includeViewHierarchy(true)
                        .withRootView(textView)
                        .build()
                }

                // Get the links inside the TextView and check if we find textToClick
                val spans = spannedString.getSpans(
                    0, spannedString.length,
                    ClickableSpan::class.java
                )
                if (spans.isNotEmpty()) {
                    var spanCandidate: ClickableSpan?
                    for (span in spans) {
                        spanCandidate = span
                        val start = spannedString.getSpanStart(spanCandidate)
                        val end = spannedString.getSpanEnd(spanCandidate)
                        val sequence = spannedString.subSequence(start, end)
                        if (textToClick.toString() == sequence.toString()) {
                            span.onClick(textView)
                            return
                        }
                    }
                }
                throw NoMatchingViewException.Builder()
                    .includeViewHierarchy(true)
                    .withRootView(textView)
                    .build()
            }
        }
    }
}