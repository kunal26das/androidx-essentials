package androidx.essentials.playground.date

import com.google.android.material.datepicker.CalendarConstraints
import kotlinx.parcelize.Parcelize

@Parcelize
class DateValidator(
    private val startDate: Long? = null,
    private val endDate: Long? = null,
) : CalendarConstraints.DateValidator {

    override fun isValid(date: Long): Boolean {
        return if (startDate != null) {
            startDate <= date
        } else if (endDate != null) {
            endDate >= date
        } else true
    }

}