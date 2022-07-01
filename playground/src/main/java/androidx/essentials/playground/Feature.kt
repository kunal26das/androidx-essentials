package androidx.essentials.playground

import androidx.essentials.playground.autocomplete.AutoCompleteActivity
import androidx.essentials.playground.chips.ChipsActivity
import androidx.essentials.playground.datetime.DateTimePickerActivity
import androidx.essentials.playground.firebase.FirebaseActivity
import androidx.essentials.playground.location.LocationActivity
import androidx.essentials.playground.preferences.SharedPreferencesActivity
import androidx.essentials.playground.textfield.TextFieldActivity
import androidx.essentials.view.getActivityResultContract
import kotlin.reflect.KClass

enum class Feature(
    private val kClass: KClass<*>,
) {
    AutoComplete(AutoCompleteActivity::class),
    Chips(ChipsActivity::class),
    Firebase(FirebaseActivity::class),
    DateTimePicker(DateTimePickerActivity::class),
    Location(LocationActivity::class),
    SharedPreferences(SharedPreferencesActivity::class),
    TextField(TextFieldActivity::class),
    ;

    val activityResultContract
        get() = getActivityResultContract(kClass)
}