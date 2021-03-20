package cc.taylorzhang.singleclick

/**
 * <pre>
 *     author : Taylor Zhang
 *     time   : 2021/03/19
 *     desc   : Single click util.
 *     version: 1.0.0
 * </pre>
 */
object SingleClickUtil {

    /**
     * Global single click interval.
     */
    var singleClickInterval: Int = 1000
        set(value) {
            if (value <= 0) {
                throw IllegalArgumentException("Single click interval must be greater than 0.")
            }
            field = value
        }
}