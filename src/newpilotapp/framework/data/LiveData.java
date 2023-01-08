/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.framework.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jeffrey
 */
public class LiveData<T> {
    private T data;
    
    private List<LiveDataObserver<T>> observers = new ArrayList<>();
    
    public LiveData() {}

    public LiveData(T data) {
        this.data = data;
    }
    
    public void setValue(T data) {
        this.data = data;
        callback();
    }
    
    public T getValue() {
        return data;
    }
    
    public void observe(LiveDataObserver o) {
        observers.add(o);
        o.update(data);
    }
    
    private void callback() {
        for(LiveDataObserver<T> o : observers) {
            o.update(data);
        }
    }
    
    
}
