# Events
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