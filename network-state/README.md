# Network State
```
implementation 'androidx.essentials:network-state:latest_version'
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
