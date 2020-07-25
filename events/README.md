# Events
[ ![Download](https://api.bintray.com/packages/kunal26das/androidx.essentials/events/images/download.svg) ](https://bintray.com/kunal26das/androidx.essentials/events/_latestVersion)
```
implementation 'androidx.essentials:events:latest_version'
```
## Requirements
```
minSdkVersion 21
```
## Usage
1. Subscribe
```
Events.subscribe(String::class.java) {}
```
2. Publish
```
Events.publish("Message")
```
