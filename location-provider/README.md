# Network State
```
implementation 'androidx.essentials:location-provider:latest_version'
```
## Requirements
```
minSdkVersion 21
```
## Usage
1. Initialize
```
val locationProvider = LocationProvider.getInstance(context)
```
2. Set Listener
```
locationProvider.setOnLocationChangeListener { latitude, longitude -> }
```
