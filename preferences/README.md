# Preferences
Now get `LiveData` (or `MutableLiveData`) of your preference, and observe it.
```
implementation 'androidx.essentials:preferences:latest_version'
```
## Requirements
```
minSdkVersion 21
```
## Initialize
```
import androidx.essentials.preferences.SharedPreferences
```
### Plaintext
```
SharedPreferences.init(applicationContext, SharedPreferences.Mode.PLAINTEXT)
```
### Encrypted
```
SharedPreferences.init(applicationContext, SharedPreferences.Mode.ENCRYPTED)
```
## Example
```
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
To get preferences:
```
import androidx.essentials.preferences.SharedPreferences.Companion.get
```
```
val _int = get<Int>(Preference.INT)
val _long = get<Long>(Preference.LONG)
val _float = get<Float>(Preference.FLOAT)
val _string = get<String>(Preference.STRING)
val _boolean = get<Boolean>(Preference.BOOLEAN)
```
To put preference:
```
import androidx.essentials.preferences.SharedPreferences.Companion.put
```
```
put(Preference.INT, 0)
put(Preference.LONG, 0L)
put(Preference.FLOAT, 0f)
put(Preference.STRING, "0")
put(Preference.BOOLEAN, false)
```
## Demo
Check the [Play Ground](/app/src/main/java/androidx/essentials/playground) app.

