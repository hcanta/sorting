/**
 * Utils package to sort the file
 */
package code.utils;

import java.io.Serializable;

/**
 * Holds the implementation of the data structure that will hold the info to be sorted It implements serializable for more efficient write to file
 * @author hcanta
 */
public class Data implements Comparable<Data> , Serializable
{
	/**
	 * Generated Serial Version UID
	 */
	private static final long serialVersionUID = 2605738945289907294L;
	/**
	 * ID of the daTA
	 */
	private int id;
	/**
	 * The data
	 */
	private char[] data;
	
	/**
	 * Constructor for the data
	 * @param id The unique ID of the data
	 * @param dat The data information
	 */
	public Data (int id, char[] dat)
	{
		this.id = id;
		this.data = new char[dat.length];
		System.arraycopy(dat, 0, this.data, 0, dat.length);		
	}

	/**
	 * Returns the unique id of the data
	 * @return The unique ID of the data
	 */
    public int getId() {
		return this.id;
	}
    
    /**
     * Returns the data in a character array
     * @return The data
     */
    public char[] getData(){
    	return this.data;
    }
	
	/**
	 * Compares one data to another
	 * @param other the other Data we n eed to be comparing
	 * @return Whether the data is equal greater or smaller
	 */
	@Override
    public int compareTo(Data other)
    {
        return Integer.compare(this.getId(), other.getId());
    }	
	
	/**
	 * Returns a string representation of the object
	 * @return a string representation of the object
	 */
	@Override
	public String toString()
	{
		return new String(this.data);
	}
}
