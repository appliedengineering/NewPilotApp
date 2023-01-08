/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpilotapp.framework.data;

/**
 *
 * @author Jeffrey
 */
public abstract class LiveDataObserver<T> {
    abstract public void update(T data);
}
