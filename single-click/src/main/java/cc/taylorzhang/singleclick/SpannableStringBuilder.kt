package cc.taylorzhang.singleclick

import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.text.inSpans
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/20
 *     desc   : SpannableStringBuilder extensions.
 *     version: 1.0.0
 * </pre>
 */

/**
 * Wrap appended text in [builderAction] in a [ClickableSpan].
 * If selected and single clicked, the [listener] will be invoked.
 *
 * @param listener Single click listener.
 * @param interval Single click interval.Unit is [TimeUnit.MILLISECONDS].
 * @param isShareSingleClick True if this view is share single click interval whit other view
 *   in same Activity, false otherwise.
 * @param updateDrawStateAction Update draw state action.
 * @see SpannableStringBuilder.inSpans
 */
inline fun SpannableStringBuilder.onSingleClick(
    listener: View.OnClickListener,
    interval: Int = SingleClickUtil.singleClickInterval,
    isShareSingleClick: Boolean = true,
    noinline updateDrawStateAction: ((TextPaint) -> Unit)? = null,
    builderAction: SpannableStringBuilder .() -> Unit
): SpannableStringBuilder = inSpans(
    object : ClickableSpan() {
        override fun onClick(widget: View) {
            widget.determineTriggerSingleClick(interval, isShareSingleClick, listener)
        }

        override fun updateDrawState(ds: TextPaint) {
            updateDrawStateAction?.invoke(ds)
        }
    },
    builderAction = builderAction
)