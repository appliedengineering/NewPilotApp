/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import com.fazecast.jSerialComm.SerialPort;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import newpilotapp.data.BoatDataManager;
import newpilotapp.logging.Console;

/**
 * Serial hardware driver
 * @author Jeffrey
 */
public class SerialDriver extends Driver {
    
    private int baudRate = 115200;
    private int dataBits = 8;
    private int stopBits = SerialPort.ONE_STOP_BIT;
    private int parity   = SerialPort.NO_PARITY;
    private int readTimeout = 100;
    
    private SerialPort serialPort;
    
    // private byte[] command = new byte[0];
    
    private String serialPortName = null;

    
    /**
     * Initializes an interface
     */
    public void init() {
        init(true);
    }
    public void init(boolean isFirst) {
        SerialPort[] availablePorts = SerialPort.getCommPorts();

        // use the for loop to print the available serial ports
        StringBuffer portData = new StringBuffer();
        portData.append("Available Serial Ports\n\n");
        serialPort = null;
        
        
        for(SerialPort s : availablePorts) {
             portData.append(s.getPortLocation()).append(" ").append(s.getSystemPortPath()).append(" ").append(s.toString()).append("\n");
             if(s.getPortLocation().equals(serialPortName) || s.getSystemPortPath().equals(serialPortName)) {
                serialPort = s;
             }
        }
        BoatDataManager.portData.setValue(portData); // update ports info
        
        if(serialPort == null) {
            if(isFirst) throw new IllegalStateException(String.format("Serial port: %s not found!!! (%s)", serialPortName, this.getClass().getName()));
            if(!isFirst)  return;
        }

        //Sets all serial port parameters at one time
        serialPort.setComPortParameters(baudRate,
                                          dataBits,
                                          stopBits,
                                            parity);

        //Set Read Time outs
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 
                                         readTimeout, 
                                            0); 

        serialPort.openPort();
    }
    
    /**
     * Stops an interface
     */
    public void stop(){
        serialPort.closePort();
        serialPort = null;

    }
    
    /**
     * Restarts an interface
     */
    public void restart() {
        stop();
        init();
    }
    
//    public void setCommand(byte[] command) {
//        this.command = command;
//    }

    public SerialData recieveData(byte[] command) {
        SerialData serialData = new SerialData();
        if(serialPort == null) {
            try {
                // delay before restart
                Thread.sleep(100);
            } catch (InterruptedException ex) {}
            this.init(false); // restart
            // if its still null, just return blank data
            if(serialPort == null) return serialData;
        }
        try {
            byte[] readBuffer = new byte[256];
            if(command.length != 0) {
                serialPort.writeBytes(command, command.length);
            }
            int numRead = serialPort.readBytes(readBuffer,
                                                 readBuffer.length);
            
            if(numRead == -1) {
                // somethings wrong
                this.stop();
            }
            
            serialData.byteData = Arrays.copyOfRange(readBuffer, 0, numRead);
            // System.out.print("Read " + numRead + " bytes - ");
//            String S; 
//            S = new String(readBuffer, "UTF-8");
            // System.out.println("Received -> "+ S);
            
        } catch (Exception e) {
            // corrupted data
            Console.log(e.toString());
        }
        
        return serialData;
    }

    @Override
    public boolean isDataPresent() {
        return true; // this only applies to network connections
    }
    
    public static class SerialData extends DriverData{
        public byte[] byteData;
    }
    
    // GETTERS AND SETTERS

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(int parity) {
        this.parity = parity;
    }

    public String getSerialPortName() {
        return serialPortName;
    }

    public void setSerialPortName(String serialPortName) {
        this.serialPortName = serialPortName;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    
    
    
    
}
