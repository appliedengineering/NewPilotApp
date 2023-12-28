# New Pilot App
## Description
The pilot app, but in java. Runs the touchscreen interface on the boat.\
Aims to provide better documentation, offer more relibility and code that can be reused for posterity.
This project is developed using NetBeans IDE. This allows code to be uploaded to the raspberry pi over wifi.

## Notes
### External Libraries
All external libraries are included and configured in this repo.
- JeroMQ
- Msgpack
- jSerialComm
All files int the `external` package are from external libraries, you should not edit them.
### Brief Explanation of files
TODO

## Setup
### Setting up the IDE
- install NetBeans IMPORTANT: make sure you choose Java 17
- have the `ae-boat` raspberry pi nearby and connected to the same network as your development computer
- Tools > Java Platforms > Add Platform > Remote Java Standard Edition > Fill out the values > Make sure to test platform
- java.home should be `/usr/lib/jvm/java-17-openjdk-arm64`
- Set working directory to `/home/ae/Desktop/Netbeans` for ease.
- if you get stuck, refer to this: https://www.instructables.com/Efficient-Development-of-Java-for-the-Raspberry-Pi/
- if you see a large number of packages starting with `external.*`, switch the "Projects" to "Files" view to declutter.

### Running the code
- Make sure your code is configured to use the remote jdk created ^
- You might get the following error. This is normal.
```
Caused by: java.awt.HeadlessException: 
No X11 DISPLAY variable was set,
but this program performed an operation which requires it.
```
- To run the program, open terminal on the *boat* raspberry pi.
- `cd Desktop/`
- `bash runJava.sh`



