package cc.taylorzhang.singleclick

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/21
 *     desc   : View single click test.
 *     version: 1.0.0
 * </pre>
 */
@RunWith(AndroidJUnit4::class)
class ViewSingleClickTest {

    @get:Rule
    val rule = ActivityScenarioRule(SingleClickTestActivity::class.java)

    private lateinit var scenario: ActivityScenario<SingleClickTestActivity>

    @Before
    fun setup() {
        scenario = rule.scenario
    }

    @Test
    fun testOneViewSingleClick() {
        var btn1SingleClickedCount = 0
        val interval = 1500

        scenario.onActivity {
            it.btn1.onSingleClick(interval = 1500) { btn1SingleClickedCount++ }
        }

        onView(withText("btn1")).perform(click())
        onView(withText("btn1")).perform(click())
        onView(withText("btn1")).perform(click())
        assertEquals(1, btn1SingleClickedCount)

        Thread.sleep(interval.toLong())
        onView(withText("btn1")).perform(click())
        onView(withText("btn1")).perform(click())
        onView(withText("btn1")).perform(click())
        assertEquals(2, btn1SingleClickedCount)
    }

    @Test
    fun testTwoViewSingleClick() {
        var btn1SingleClickedCount = 0
        var btn2SingleClickedCount = 0
        val interval = 1500

        scenario.onActivity {
            it.btn1.onSingleClick(interval = interval) { btn1SingleClickedCount++ }
            it.btn2.onSingleClick(interval = interval) { btn2SingleClickedCount++ }
        }

        onView(withText("btn1")).perform(click())
        onView(withText("btn2")).perform(click())
        assertEquals(1, btn1SingleClickedCount)
        assertEquals(0, btn2SingleClickedCount)

        Thread.sleep(interval.toLong())
        onView(withText("btn2")).perform(click())
        onView(withText("btn1")).perform(click())
        assertEquals(1, btn1SingleClickedCount)
        assertEquals(1, btn2SingleClickedCount)
    }

    @Test
    fun testOneViewNotShareSingleClick() {
        var btn1SingleClickedCount = 0
        var btn2SingleClickedCount = 0

        scenario.onActivity {
            it.btn1.onSingleClick(isShareSingleClick = false) { btn1SingleClickedCount++ }
            it.btn2.onSingleClick { btn2SingleClickedCount++ }
        }

        onView(withText("btn1")).perform(click())
        onView(withText("btn2")).perform(click())
        assertEquals(1, btn1SingleClickedCount)
        assertEquals(1, btn2SingleClickedCount)
    }

    @Test
    fun testTwoViewNotShareSingleClick() {
        var btn1SingleClickedCount = 0
        var btn2SingleClickedCount = 0

        scenario.onActivity {
            it.btn1.onSingleClick(isShareSingleClick = false) { btn1SingleClickedCount++ }
            it.btn2.onSingleClick(isShareSingleClick = false) { btn2SingleClickedCount++ }
        }

        onView(withText("btn1")).perform(click())
        onView(withText("btn2")).perform(click())
        assertEquals(1, btn1SingleClickedCount)
        assertEquals(1, btn2SingleClickedCount)
    }
}