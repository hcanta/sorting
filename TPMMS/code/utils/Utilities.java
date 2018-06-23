/**
 * Contains the utils to read and write data and objects
 */
package code.utils;

import code.logger.MyLogger;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Implementation of the utilities class
 * @author hcanta
 */
public class Utilities 
{
	/**
	 * Reads the data from a file
	 * @param fileName The file to be read 
	 * @param currentFilePointer The starting point to read the file
	 * @param list The list to store the data read
	 * @param subListSize The amount of data to be read
	 * @param logger The logger element if activated
	 * @return The current file pointer or  -1 if at the end of the file
	 * @throws IOException the file was not found exception or other io exception
	 */
    public static long readFile(String fileName, long currentFilePointer, List<Data> list,int subListSize, MyLogger logger)
    throws  IOException 
    {
        logger.write("Entering Function Readfile");
        long currentPointer = currentFilePointer;

        try (RandomAccessFile raf = new RandomAccessFile(fileName, "r")) 
        {
            
            String line;
            raf.seek(currentPointer);
            if (currentFilePointer == raf.length())
            		return -1;
            logger.write("Current file Pointer " + currentPointer+" in file: " + fileName );

            while ((line = raf.readLine()) != null) 
            {
                Data temp = new Data(Integer.parseInt(line.substring(0, 7)), line.toCharArray());
                
                list.add(temp);

                if(list.size() == subListSize)
                {
                    break;
                }
            }
            currentPointer = raf.getFilePointer();
            raf.close();
        }
        return currentPointer;
    }

    /**
     * Stores a Sublist in an array
     * @param fileName The filename where the list will stored
     * @param list The list to be stored
     * @param data1 Whether it should be stored in the data 1 folder or not
     * @throws FileNotFoundException Can t find the directory or file
     * @throws IOException can t find the directory or file
     */
    public static  void writeSortedSubListFile(String fileName, ArrayList<Data> list, boolean data1)
            throws FileNotFoundException, IOException
    {
    	PrintWriter   file = new PrintWriter  (data1 ? "temp/Data1/" + fileName : "temp/Data2/" + fileName );
    	
    	for( Data data : list)
    		file.write(dataToString(data) +"\n");
    	file.flush();
        file.close();
      
    }
    
    public static <T> void writeFile(String fileName, List<T> list, boolean data1, int [] counter)
            throws FileNotFoundException, IOException
    {
        counter[2] = counter[2]+ 1;
        try(PrintWriter writer = new PrintWriter(data1 ? "temp/Employees/" + fileName : "temp/Projects/" + fileName   , "UTF-8")){
            for (T temp : list) {
                writer.println(temp.toString());
                counter[5] = counter[5]+1;
            }
        }
    }
    
    public static <T> void writeOutput(String str, boolean iteration, boolean data1)
    	    throws FileNotFoundException, IOException
    	    {
    	      
    	        String filename = data1 ? Constants.DATA_ONE_OUTPUT : Constants.DATA_TWO_OUTPUT;
    	        File file = new File(filename);
    	        if(file.exists() && iteration)
    	        {
    	        	file.delete();
    	        	file.createNewFile();
    	        }
    	        
    	        
    	        BufferedWriter bw = null;
    	        FileWriter fw = null;
    	        
    	        
    	            try {

    	              
	                fw = new FileWriter(filename, true);
	                bw = new BufferedWriter(fw);

	                bw.write(str);
	                
	                bw.flush();
    	            } 
    	            catch (IOException  e) 
    	            {
    	                e.printStackTrace();
    	            } 
    	        
    	        try {

    	            if (bw != null)
    	                bw.close();

    	            if (fw != null)
    	                fw.close();

    	        } catch (IOException ex) {
    	            ex.printStackTrace();
    	        }                
    	        
    	    }
    
    public static boolean notDoneReading(long [] array)
    {
        for(int i =0; i< array.length; i++)
        {
            if(array[i] > -1)
            {
                return true; 
            }
        }
        return false;
    }
    
    public static long readLine(String fileName, long currentFilePointer, ArrayList<Data> list) throws FileNotFoundException, IOException
    {
        String line;
        RandomAccessFile raf = new RandomAccessFile(fileName, "r");
        raf.seek(currentFilePointer);
     
        line = raf.readLine();
        
        long pointer = raf.getFilePointer();
        raf.close();
        if(line != null)
        {
            list.add( (Data)(stringToData(line)));
        }
        
        return line != null ? pointer : -1;
    }
    
	/**
	 * Convert a serializable object to a string
	 * @param o the object to be converted
	 * @return the string representation of the object
	 */ 
	private static String dataToString(Serializable o)
	{
		 ByteArrayOutputStream baos = new ByteArrayOutputStream();
		 ObjectOutputStream oos;
		try 
		{
			 oos = new ObjectOutputStream(baos);
			 oos.writeObject(o);
			 oos.close();
			 return Base64.getEncoder().encodeToString(baos.toByteArray());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Convert from String to Object
	 * @param str string info
	 * @return the object
	 */
	private static Object stringToData(String str)
	{
		byte [] data = Base64.getDecoder().decode( str );
		  try 
		  {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
			Object o  = ois.readObject();
			ois.close();
			return o;
		  } 
		  catch (IOException | ClassNotFoundException e) 
		  {
			
			e.printStackTrace();
		  }
		return null;		
	}

}