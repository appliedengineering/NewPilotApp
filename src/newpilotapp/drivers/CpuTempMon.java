/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

/**
 *
 * @author Jeffrey
 */
public class CpuTempMon extends SystemPropertyDriver {

    public static String CPU_TEMP_PATH = "/sys/class/thermal/thermal_zone0/temp";
    
    public CpuTempMon() {
        super(CPU_TEMP_PATH);
    }

    @Override
    public TempData recieveData() {
        TempData tempData = new TempData();
        SystemPropertyData data = (SystemPropertyData) super.recieveData();
        tempData.tempC = (Integer.parseInt(data.systemProperty) / 1000); 
        tempData.tempF = ((tempData.tempC / 5) * 9) + 32;
        return tempData;
    }
 
    public static class TempData extends DriverData {
        public double tempC, tempF;
    }
}
