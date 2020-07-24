# Network-State
[![](https://jitpack.io/v/kunal26das/network-state.svg)](https://jitpack.io/#kunal26das/network-state)

Project level dependency
```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
Module level dependency
```
implementation 'com.github.kunal26das:network-state:version'
```
## Requirements
```
minSdkVersion 19
```
## Usage
1. Initialize
```
val networkCallback = NetworkCallback(context)
```
2. Register
```
networkCallback.register(object: NetworkCallback.OnNetworkStateChangeListener {
	override fun onOnline() {}
	override fun onOffline() {}
})
```
```
networkCallback.register({}, {})
```
3. Unregister
```
networkCallback.unregister()
```
