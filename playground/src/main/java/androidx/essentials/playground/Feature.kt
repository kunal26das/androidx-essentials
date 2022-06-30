package androidx.essentials.playground

import androidx.essentials.playground.autocomplete.AutoCompleteActivity
import androidx.essentials.playground.backdrop.BackdropActivity
import androidx.essentials.playground.chips.ChipsActivity
import androidx.essentials.playground.date.DateActivity
import androidx.essentials.playground.firebase.FirebaseActivity
import androidx.essentials.playground.location.LocationActivity
import androidx.essentials.playground.preferences.SharedPreferencesActivity
import androidx.essentials.playground.textfield.TextFieldActivity
import androidx.essentials.playground.time.TimeActivity
import androidx.essentials.view.getActivityResultContract
import kotlin.reflect.KClass

enum class Feature(
    private val kClass: KClass<*>,
    val compose: Boolean = true,
) {
    AutoComplete(AutoCompleteActivity::class, false),
    Backdrop(BackdropActivity::class, false),
    Chips(ChipsActivity::class, false),
    Firebase(FirebaseActivity::class),
    Date(DateActivity::class),
    Location(LocationActivity::class),
    SharedPreferences(SharedPreferencesActivity::class),
    TextField(TextFieldActivity::class),
    Time(TimeActivity::class, false),
    ;

    val activityResultContract
        get() = getActivityResultContract(kClass)
}