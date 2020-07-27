# Network State
[ ![Download](https://api.bintray.com/packages/kunal26das/androidx.essentials/network-state/images/download.svg) ](https://bintray.com/kunal26das/androidx.essentials/network-state/_latestVersion)
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
val networkCallback = NetworkCallback.(context)
```
2. Set Listener
```
networkCallback.setOnNetworkStateChangeListener(object: NetworkCallback.OnNetworkStateChangeListener {
	override fun onNetworkStateChange(isOnline: Boolean) {
		when (isOnline) {
			true -> {}
			false -> {}
		}
	}
)
```
```
networkCallback.setOnNetworkStateChangeListener { isOnline ->
	when (isOnline) {
		true -> {}
		false -> {}
	}
}
```
3. Remove Listener
```
networkCallback.removeListener()
```
