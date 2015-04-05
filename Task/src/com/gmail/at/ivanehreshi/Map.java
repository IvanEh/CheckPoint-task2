package com.gmail.at.ivanehreshi;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.ArrayList;


/** A simple Map implementation, implemented in terms of a
 * pair of ArrayLists just to show what a Map has to do (it would 
 * have been easier, but less informative, to subclass AbstractMap).
 * This Map implementation, like TreeSet, guarantees that the 
 * Map's contents will be kept in ascending element order, 
 * sorted according to the natural order of the elements;
 * see Comparable. This does not (yet) allow you to specify your own
 * Comparator.
 * <p>
 * It is a requirement that all objects inserted be able to 
 * call compareTo on all other objects, i.e., they must all
 * be of the same or related classes.
 * <p>
 * Be warned that the entrySet() method is <b>not implemented</b> yet.
 */
public class Map<K, V> implements java.util.Map<K, V> {

  private ArrayList<K> keys;
  private ArrayList<V> values;
  private ArrayList<Long> creationTime;
  private long lifetime = -1;

  public Map() {
    keys = new ArrayList<>();
    values = new ArrayList<>();
    creationTime = new ArrayList<>();
  }

  public Map(long lifetime) {
	  this();
	this.lifetime = lifetime;
  }
  /** Return the number of mappings in this Map. */
  public int size() {
    return keys.size();
  }

  /** Return true if this map is empty. */
  public boolean isEmpty() {
    return size() == 0;
  }

  private boolean isAlive(int i){
	  if(lifetime < 0)
		  return true;
	  
	  long currentTime = System.currentTimeMillis();
	  long delta = currentTime - creationTime.get(i) ;
	  if(delta > lifetime){
		  return false;
	  }
	  
	  return true;
	  
  }
  
  /** Return true if o is contained as a Key in this Map. */
  public boolean containsKey(Object o) {
	int index = keys.indexOf(o);
	if(isAlive(index))
		 return index != -1;
	
	return false;
	
  }

  /** Return true if o is contained as a Value in this Map. */
  public boolean containsValue(Object o) {
		int index = values.indexOf(o);
		if(!isAlive(index))
			 return index != -1;
	   
		return containsValue(o);
  }

  /** Get the object value corresponding to key k. */
  public V get(Object k) {
    int i = keys.indexOf(k);
    if (i == -1)
      return null;
    if(isAlive(i)){
     return values.get(i);
    }
    return get(k);
  }

  /** Put the given pair (k, v) into this map, by maintaining "keys"
   * in sorted order.
   */
  public V put(K k, V v) {
    for (int i=0; i < keys.size(); i++) {
      V old = values.get(i);

      /* Does the key already exist? */
      if (((Comparable)k).compareTo(keys.get(i)) == 0) {
        values.set(i, v);
        return old;
      }

      /* Did we just go past where to put it?
       * i.e., keep keys in sorted order.
       */
      if (((Comparable)k).compareTo(keys.get(i)) == +1) {
        int where = i > 0 ? i -1 : 0;
        keys.add(where, k);
        values.add(where, v);
        creationTime.add(where, System.currentTimeMillis());
        return null;
      }
    }

    // Else it goes at the end.
    keys.add(k);
    values.add(v);
    creationTime.add(System.currentTimeMillis());
    return null;
  }

  /** Put all the pairs from oldMap into this map */
  public void putAll(java.util.Map oldMap) {
    Iterator keysIter = oldMap.keySet().iterator();
    while (keysIter.hasNext()) {
      K k = (K) keysIter.next();
      V v = (V) oldMap.get(k);
      put(k, v);
    }
  }

  public V remove(Object k) {
    int i = keys.indexOf(k);
    if (i == -1)
      return null;
    
    if(isAlive(i)){
	    V old = values.get(i);
	    keys.remove(i);
	    values.remove(i);
	    creationTime.remove(i);
	    return old;
    }
    
    return remove(k);
    
  }

  public void clear() {
    keys.clear();
    values.clear();
    creationTime.clear();
  }

  public java.util.Set keySet() {
    return new TreeSet(keys);
  }

  public java.util.Collection values() {
    return values;
  }

  /** The Map.Entry objects contained in the Set returned by entrySet().
   */
  private class MapEntry<K, V> implements Map.Entry<K, V>, Comparable {
    private K key;
    V value;
    
    MapEntry(K k, V v) {
      key = k;
      value = v;
    }
    public K getKey() { return key; }
    public V getValue() { return value; }
    public V setValue(V nv) {
      throw new UnsupportedOperationException("setValue");
    }
    
    public int compareTo(Object o2) {
      if (!(o2 instanceof MapEntry))
        throw new IllegalArgumentException(
          "Huh? Not a MapEntry?");
      K otherKey = ((MapEntry<K, V>)o2).getKey();
      return ((Comparable)key).compareTo((Comparable)otherKey);
    }
    }

  /** The set of Map.Entry objects returned from entrySet(). */
  private class MyMapSet<K, V> extends AbstractSet {
    List list;
    MyMapSet(ArrayList al) {
      list = al;
    }
    public Iterator iterator() {
      return list.iterator();
    }
    public int size() {
      return list.size();
    }
  }

  /** Returns a set view of the mappings contained in this Map.
   * Each element in the returned set is a Map.Entry.
   * NOT guaranteed fully to implement the contract of entrySet
   * declared in java.util.Map.
   */
    public java.util.Set entrySet() {
    if (keys.size() != values.size())
      throw new IllegalStateException(
        "InternalError: keys and values out of sync");
    ArrayList al = new ArrayList();
    for (int i=0; i<keys.size(); i++) {
      al.add(new MapEntry<K, V>(keys.get(i), values.get(i)));
    }
    return new MyMapSet(al); 
  }

}