/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mycollections;

import java.util.ArrayList;

/**
 *
 * @author Pete
 */

//dependencies hashcode() method and Math.floor()
public class MyHashMap<K, V> {
    private MyArrayList<MyLinkedList<K, V>> elements;;
    private double constant = 0.61803;
//    int[] powersofTwo = {32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 
//    65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864,
//    134217728, 268435456, 536870912, 1073741824};
    private int size;
    
    //maybe there shouldn't be initialcapacity? then myarraylist capacity would always be power of two
    public MyHashMap(int initialCapacity){
        elements = new MyArrayList(initialCapacity);
    }
    
    public void put(K key, V value){
        int hash = key.hashCode();
        int index = getIndex(hash);
        
        if(elements.get(index) == null){
            MyLinkedList l = new MyLinkedList();
            l.insert(key, value);
            elements.set(index, l);
        }else{
            elements.get(index).insert(key, value);
        }
        size++;
    }

    private int getIndex(int key){
        int m = elements.getCapacity();
        int index = (int) (m * fr(constant * key));
        return index;
    }
           
    public double fr(double x){
        return x - (int) x;
    }
    
    public V get(K key){
        int hash = key.hashCode();
        int index = getIndex(hash);
        
        if(elements.get(index) == null){
            return null;
        }else{
            return elements.get(index).get(key);
        }
    }
    
    
    private void reshuffle(){
        
    }
    
}
