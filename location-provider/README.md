# Location Provider
[ ![Download](https://api.bintray.com/packages/kunal26das/androidx.essentials/location-provider/images/download.svg) ](https://bintray.com/kunal26das/androidx.essentials/location-provider/_latestVersion)
```
implementation 'androidx.essentials:location-provider:latest_version'
```
## Requirements
```
minSdkVersion 21
```
```
ActivityCompat.requestPermissions(
  context, arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
  ), 0
)
```
## Usage
1. Initialize
```
val locationProvider = LocationProvider.getInstance(context)
```
2. Set Listener
```
locationProvider.setOnLocationChangeListener(object: OnLocationChangeListener {
  override fun onLocationChange(latitude: Double, longitude: Double) {}
})
```
```
locationProvider.setOnLocationChangeListener { latitude, longitude -> }
```
