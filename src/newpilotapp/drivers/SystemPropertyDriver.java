/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Jeffrey
 */
public class SystemPropertyDriver extends Driver {
    
    public final String fileName;
    
    public SystemPropertyDriver (String propertyFilePath) {
        fileName = propertyFilePath;
    }
    
    
    @Override
    public DriverData recieveData() {
       
        String line = null;
        SystemPropertyData data = new SystemPropertyData();
        try {
        
            FileReader fileReader = new FileReader(fileName);

            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                data.systemProperty = line;
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        return data;
    }

    @Override
    public boolean isDataPresent() {
        return true; // data is always present
    }
    
    
    public static class SystemPropertyData extends DriverData{
        public String systemProperty;
    }
    
    
}
