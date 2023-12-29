/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.drivers;

import external.org.msgpack.core.MessagePack;
import external.org.msgpack.core.MessagePacker;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import newpilotapp.data.BoatDataManager;
import newpilotapp.data.DataPoint;

/**
 *
 * @author jeffrey
 */
public class DebugDataCalc {
    
    public static byte[] generateDebugData() {
        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MessagePacker packer = MessagePack.newDefaultPacker(out);

            Map<String, List<DataPoint>> data = BoatDataManager.dataFromBoatController.getValue();
            for(int i = 0; i < BoatDataManager.DATA_KEYS.length; i++) {
                List<DataPoint> points = data.get(BoatDataManager.DATA_KEYS[i]);
                DataPoint lastPoint;
                if(points == null) {
                    points = new ArrayList<>();
                    data.put(BoatDataManager.DATA_KEYS[i], points);
                }
                
                lastPoint = new DataPoint();
                lastPoint.valueDouble = Math.random()*100d;
                lastPoint.valueBool = Math.random() > 0.5d;
                
                points.add(lastPoint);
                    
                if(BoatDataManager.isDataKeyNumerical(BoatDataManager.DATA_KEYS[i])) {
                    packer.packFloat((float) lastPoint.valueDouble); // reduce bytes
                } else {
                    packer.packBoolean(lastPoint.valueBool);
                }
            }
            packer.close();
            byte[] arr = out.toByteArray();
            System.out.println(arr.length);
            System.out.println(Arrays.toString(arr));
            BoatDataManager.dataFromBoatController.valueWasUpdated();
            return arr;
        } catch (IOException e) {
            return new byte[0];
        }
    }
    
    
    
    public static void main(String[] args) {
        DebugDataCalc.generateDebugData();
    }
    
}
