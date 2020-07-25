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
val networkCallback = NetworkCallback.getInstance(context)
```
2. Register
```
networkCallback.setOnNetworkStateChangeListener(object: NetworkCallback.OnNetworkStateChangeListener {
	fun onNetworkStateChange(isOnline: Boolean) {
		when (isOnline) {
			true -> {}
			false -> {}
		}
	}
)
```
```
networkCallback.setOnNetworkStateChangeListener { isOnline -> }
```
3. Unregister
```
networkCallback.unregister(context)
```
