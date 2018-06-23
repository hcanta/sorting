/**
 * Contains the utils to read and write data and objects
 */
package code.utils;

import code.logger.MyLogger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Implementation of the tppms algorithm
 * @author hcanta
 *@version 2.1
 */
public class Tpmms 
{

    private MyLogger _logger;
    private String[] _files;
    private short _data1Size;
    private short _data2Size;

    public Tpmms(String[] args, boolean toFile, boolean toConsole)
    {
        this._logger = new MyLogger(toFile, toConsole);
        this._files = args;
 
    }

    public boolean run()
    {
        int heapSize = (int) Runtime.getRuntime().maxMemory();
        _logger.write("Find Maimum Heap Size = " + heapSize);
        boolean result =  phaseOne(heapSize);  
       
        if(result)
        {
        	 _logger.write("Initializing phase Two.");
        	result = phaseTwo(heapSize);
        }
        return result;   
    }
    
    private boolean phaseOne(int heapsize)
    {
        boolean nextFile = true;
        boolean data1 = true;
        for(byte i = 0; (i < this._files.length) && nextFile; i++)
        {
            nextFile = phaseOneHelper(this._files[i], data1, heapsize);
            data1 =! data1;
        }
        return nextFile;
    }
    
    private boolean phaseOneHelper(String filename, boolean data1, int heapSize)
    {
    	short subListSize = (short) (data1?  ((heapSize / (Constants.BLOCK_SIZE*Constants.BLOCK_SECTIONS )) * Constants.NO_DATA_ONE_TUPPLES_ONE_BLOCK) : ((heapSize / (Constants.BLOCK_SIZE*Constants.BLOCK_SECTIONS)) * Constants.NO_DATA_TWO_TUPPLES_ONE_BLOCK));
    	_logger.write("Sublist Size: " +subListSize);
    	_logger.write("Phase One on File: "+filename);
    	File file = new File(filename);
    	if(!file.exists())
    	{
    		_logger.write(filename+ " cannot be found");
    		return false;
    	}
        try
        {   
            short counter = 0;
            long currentFilePointer = 0L;

            do 
            {
                ArrayList<Data> dataList = new ArrayList<Data>();
                currentFilePointer = Utilities.readFile(filename, currentFilePointer, dataList, subListSize, this._logger);
                Collections.sort(dataList);
                Utilities.writeSortedSubListFile((data1 ? Constants.DATA_ONE_SORTED : Constants.DATA_TWO_SORTED )+ String.valueOf(counter) + ".temp", dataList, data1);

                dataList.clear();
                counter++;

            } while (currentFilePointer != -1);
            
            if(data1)
            {
                this._data1Size = counter;
            }
            else
            {
                this._data2Size = counter;
            }
            return true;
        }
        catch(IOException f)
        {
            _logger.write("File Not found message to be logged");
            return false;
        }
    }
    
    private boolean phaseTwo(int heapSize) 
    {
       if(!phaseTwoHelper(_data1Size, true, heapSize))
    	   return false;
       return phaseTwoHelper(_data2Size, false, heapSize);       
    }

    private boolean phaseTwoHelper(int fileCounter, boolean data1, int heapSize)
    {
    	long [] seekArray = new long[fileCounter];
    	String file = "";
        String prefix = data1 ? Constants.DATA_ONE_SORTED : Constants.DATA_TWO_SORTED;
        short capacity = (short) (heapSize/ 1000000);
        capacity = (short) (capacity *(data1 ? Constants.NO_DATA_ONE_TUPPLES_ONE_BLOCK : Constants.NO_DATA_TWO_TUPPLES_ONE_BLOCK ) );
        short outputListSize =   (short) (30 * capacity);
       // capacity = (short) (capacity * 5);
        StringBuffer outputList = new StringBuffer();
        HashMap<Integer, ArrayList<Data>> mapping =  new HashMap<Integer, ArrayList<Data>>();
        int index = 0;
        long sortKey;
        boolean iteration = true;
        for(int i =0; i< fileCounter; i++ )
        {
          mapping.put(i,  new ArrayList<Data>());
        }
        short currentOutputSize = 0;
        do
        {  
            //Fill it                 
            for( int i = 0; i < fileCounter; i++)
            {
                
                file =  prefix + String.valueOf(i) + ".temp";
                
                if(mapping.get(i).size() == 0 && seekArray[i] != -1)
                {
                    for(int j = mapping.get(i).size(); j < capacity; j++)
                    {
						try 
						{
							long temp;
							temp = Utilities.readLine(file, seekArray[i], mapping.get(i));
							seekArray[i] = temp;
	                        if(temp == -1)
	                        {
	                            break;
	                        }
						} 
						catch (IOException e) 
						{
							_logger.write(e.getMessage());
							return false;
						}
                        
                    }
                }
            }
            // Place Smallest
            sortKey = Integer.MAX_VALUE;
            index = -1;
            for( int i = 0; i < fileCounter; i++)
            {
                if(mapping.get(i).size() > 0 && mapping.get(i).get(0).getId()<sortKey)
                {
                    sortKey = mapping.get(i).get(0).getId();
                    index = i;
                }
            }
            if(index > -1)
            {
                outputList.append(mapping.get(index).get(0).toString());
                outputList.append("\n");
                currentOutputSize ++;
                mapping.get(index).remove(0);
            }
            //Flush
            if(currentOutputSize == outputListSize)
            {
            	currentOutputSize = 0;
                //printToFile
                try 
                {
					Utilities.writeOutput(outputList.toString(), iteration, data1);
				} 
                catch (IOException e) 
                {
					_logger.write(e.getMessage());
					return false;
				}
                iteration = false;
                outputList.setLength(0);
               
            }
        }while(Utilities.notDoneReading(seekArray));

        ArrayList<Data> output = new ArrayList<Data>();
        for(int i =0; i< fileCounter; i++)
        {
        	if(mapping.get(i).size() > 0)
        		output.addAll( mapping.get(i));
        }
       
        mapping.clear();
        if(outputList.length() !=0)
        {
            Collections.sort(output);
            for(Data data : output)
            {
            	outputList.append(data.toString());
            	outputList.append("\n");
            }
            try 
            {
				Utilities.writeOutput(outputList.toString(), iteration, data1);
			} 
            catch (IOException e) 
            {
				
				_logger.write(e.getMessage());
				return false;
			}
            outputList.setLength(0);
        }
        return true;
    }
  

    
}