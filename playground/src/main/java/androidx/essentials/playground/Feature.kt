package androidx.essentials.playground

import androidx.essentials.playground.autocomplete.AutoCompleteActivity
import androidx.essentials.playground.backdrop.BackdropActivity
import androidx.essentials.playground.chips.ChipsActivity
import androidx.essentials.playground.date.DateActivity
import androidx.essentials.playground.firebase.FirebaseActivity
import androidx.essentials.playground.location.LocationActivity
import androidx.essentials.playground.preferences.PreferencesActivity
import androidx.essentials.playground.textfield.TextFieldActivity
import androidx.essentials.playground.time.TimeActivity
import androidx.essentials.view.getActivityResultContract
import kotlin.reflect.KClass

enum class Feature(
    private val kClass: KClass<*>,
    val compose: Boolean = false,
) {
    AutoComplete(AutoCompleteActivity::class),
    Backdrop(BackdropActivity::class),
    Chips(ChipsActivity::class),
    Firebase(FirebaseActivity::class, true),
    Date(DateActivity::class),
    Location(LocationActivity::class, true),
    Preferences(PreferencesActivity::class, true),
    TextField(TextFieldActivity::class, true),
    Time(TimeActivity::class),
    ;

    val activityResultContract
        get() = getActivityResultContract(kClass)
}