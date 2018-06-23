package code.logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.logging.Logger;

/***
 */
public class MyLogger
{
    
    private boolean _toFile;
    private boolean _toConsole;
    private Logger _logger = Logger.getLogger(code.utils.Tpmms.class.getName());
    private PrintWriter _writer = null;
    public MyLogger(boolean toFile, boolean toConsole)
    {
        this._toFile = toFile;
        this._toConsole = toConsole;

        if(this._toFile)
        {
            try 
            {
            //Initiate Log File
                _writer = new PrintWriter("logFile.log");
            }
            catch(FileNotFoundException e)
            {
                System.out.println("Failed To Initialize LogFile");

            }
        }
    }

    public void close()
    {
        if(_writer!= null)
        {
            _writer.close();
        }
    }

    public void write(String str)
    {
        if(this._toConsole)
        {
            _logger.info(str+"\n");
            //Use Java Logger function
        }
        if(this._toFile && _writer !=null)
        {
            // Write To file 
            _writer.println(str +" "+ LocalDateTime.now()+"\n");
            _writer.flush();

        }
    }
}