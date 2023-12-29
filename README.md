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
This software is meant to be used as is. Instructions on how to use it are detailed below.

## Detailed Setup Instructions
### Setting up the Hardware
#### Setting up the Raspberry Pis
- Hostnames should be `ae-boat` for boatstation system and `ae-ground` for groundstation.
- Install Java 17 (version is important) using this command: `sudo apt install openjdk-17-jdk -y`
### Setting up the IDE (Order is important)
- Install version 17.X.X of the Java Development Kit (JDK) on your development machine. 
- Install NetBeans IDE.
- Have your raspberry pi nearby and connected to the **same network** as your development computer
- Tools > Java Platforms > Add Platform > Remote Java Standard Edition > Fill out the values > Make sure to test platform
- Host: `ae-boat` or `ae-ground`
- Username: `ae`
- Use Password Authentication: Password is available here: [link](https://docs.google.com/document/d/1STqq8CaIILNGDAxn7XrZWzC51A9SBmzn0EntwRxDspY/edit)
- Remote JRE Path can be found using this command on the Raspeberry Pi `sudo update-alternatives --display java`
  The path should look something like this: `/usr/lib/jvm/java-17-openjdk-armhf/`
- Set working directory to `/home/ae/Desktop/Netbeans` for ease.
- if you get stuck, refer to this: https://www.instructables.com/Efficient-Development-of-Java-for-the-Raspberry-Pi/

### Running the code
- Make sure your code is configured to use the remote jdk created ^
- To run the program on your own computer, select Boatstation Emulation or Groundstation Emulation as the run config.
- Then select Customize... from the same dropdown and set the following arguments:
- Boatstation Emulation -> Arguments: `run boatNetwork`
- Groundstation Emulation -> Arguments: `run groundNetwork`



