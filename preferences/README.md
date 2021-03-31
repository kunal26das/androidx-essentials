# Preferences
Now get `LiveData` (or `MutableLiveData`) of your preference, and observe it.
```
implementation 'androidx.essentials:preferences:latest_version'
```
## Requirements
```
minSdkVersion 21
```
## Example
```
import androidx.essentials.preferences.SharedPreferences
import androidx.essentials.preferences.SharedPreferences.Companion.mutableLiveData
```
```
class SharedPreferencesViewModel : ViewModel(), SharedPreferences {

    val _int by mutableLiveData<Int>(Preference.INT)
    val _long by mutableLiveData<Long>(Preference.LONG)
    val _float by mutableLiveData<Float>(Preference.FLOAT)
    val _string by mutableLiveData<String>(Preference.STRING)
    val _boolean by mutableLiveData<Boolean>(Preference.BOOLEAN)

}
```
## Demo
Check the [Play Ground](/app/src/main/java/androidx/essentials/playground) app.

