/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Serial hardware driver
 * @author Jeffrey
 */
public class SerialDriver extends Driver {
    
    private int baudRate = 9600;
    private int dataBits = 8;
    private int stopBits = SerialPort.ONE_STOP_BIT;
    private int parity   = SerialPort.NO_PARITY;
    private int readTimeout = 100;
    
    private SerialPort serialPort;
    
    private byte[] command = new byte[0];
    
    private String serialPortName = null;

    
    /**
     * Initializes an interface
     */
    public void init() {
        SerialPort[] availablePorts = SerialPort.getCommPorts();

        // use the for loop to print the available serial ports
        System.out.println("Available Serial Ports");
        serialPort = null;
        for(SerialPort s : availablePorts) {
             System.out.print(s.toString() + " " + s.getSystemPortPath());
             if(s.getSystemPortPath().equals(serialPortName)) {
                serialPort = s;
                System.out.print(" <active>"); // indicator
             }
             System.out.println();
        }
        if(serialPort == null) throw new IllegalStateException("Serial port not found!!!"); 

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
    }
    
    /**
     * Restarts an interface
     */
    public void restart() {
        stop();
        init();
    }
    
    public void setCommand(byte[] command) {
        this.command = command;
    }

    @Override
    public SerialData recieveData() {
        SerialData serialData = new SerialData();
        if(serialPort == null) throw new IllegalStateException("Serial interface is null, did you call init()?");

        try {
            byte[] readBuffer = new byte[220];
            serialPort.writeBytes(command, command.length);
            int numRead = serialPort.readBytes(readBuffer,
                                                 readBuffer.length);
            serialData.byteData = Arrays.copyOfRange(readBuffer, 0, numRead);
            System.out.print("Read " + numRead + " bytes - ");
            String S; 
            S = new String(readBuffer, "UTF-8");
            System.out.println("Received -> "+ S);
        } catch (Exception e) {
            // corrupted data
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
