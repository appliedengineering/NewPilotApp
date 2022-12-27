package newpilotapp;

import com.fazecast.jSerialComm.*;

public class SerialExample {
    
//    public static void main (String[] Args) {
//        SerialPort[] AvailablePorts = SerialPort.getCommPorts();
//
//       // use the for loop to print the available serial ports
//        for(SerialPort S : AvailablePorts) {
//             System.out.println("\n  " + S.toString() + " " + S.getSystemPortPath());
//        }
//        
//        SerialPort arduinoSerial = AvailablePorts[1];
//        
//         
//         
//        int BaudRate = 9600;
//        int DataBits = 8;
//        int StopBits = SerialPort.ONE_STOP_BIT;
//        int Parity   = SerialPort.NO_PARITY;
//
//        //Sets all serial port parameters at one time
//        arduinoSerial.setComPortParameters(BaudRate,
//                                          DataBits,
//                                          StopBits,
//                                            Parity);
//
//        //Set Read Time outs
//        arduinoSerial.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 
//                                         2000, 
//                                            0); 
//
//        arduinoSerial.openPort();
//        
//        try {
//            
//            byte[] readBuffer = new byte[220];
//            arduinoSerial.writeBytes(new byte[] {97, 4}, 2);
//            int numRead = arduinoSerial.readBytes(readBuffer,
//                                                 readBuffer.length);
//
//            System.out.print("Read " + numRead + " bytes - ");
//            String S = new String(readBuffer, "UTF-8"); 
//
//            System.out.println("Received -> "+ S);
//            
//        } catch(Exception e) {
//            
//        } finally {
//            arduinoSerial.closePort();
//        }
//        
//        
//    }
}