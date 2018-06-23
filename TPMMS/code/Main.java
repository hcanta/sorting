/**
 * This Project Implements the Two Phase Multi way merge sort algorithm
 */
package code;
import code.utils.Tpmms;
import java.util.Arrays;

/**
 * Main Class
 * Runs the code
 * @author hcanta
 */
public class Main {

	/**
	 * Runs the code
	 * @param args  argument to run the algorithm please refer to readme 
	 */
    public static void main(String[] args) {
        if (!run(args)) {
            System.out.println("To run without any logger information");
            System.out.println("Usage: java -Xmx5m  -cp bin/ code.Main Employees500000.txt Projects.txt");
            System.out.println("To run with console logger information");
            System.out.println("Usage: java -Xmx5m  -cp bin/ code.Main -c Employees500000.txt Projects.txt");
            System.out.println("To run and generate a log file");
            System.out.println("Usage: java -Xmx5m  -cp bin/ code.Main -f Employees500000.txt Projects.txt");
            System.out.println("To run with console logger and log file");
            System.out.println("Usage: java -Xmx5m  -cp bin/ code.Main -cf Employees500000.txt Projects.txt");
            System.out.println("Or");
            System.out.println("Usage: java -Xmx5m  -cp bin/ code.Main -d Employees500000.txt Projects.txt");
            return;
        }
        else
        {            
            System.out.println("Two Pass Multi Way Merge Sort Completed");
        }
    }

    /**
     * Process the inputed arguments
     * @param args Arguments received from the main method
     * @return Returned if the files were sorted properly
     */
    private static boolean run(String[] args) {
        boolean toFile = false;
        boolean toConsole = false;
        if(args.length < 2 || args.length > 3)
            return false;
        switch (args[0])
        {
            case("-c"):
                toConsole = true;
                break;
            case("-f"):
                toFile = true;
                break;
            case("-cf"):
            case ("-d"):
                toConsole= true;
                toFile= true;
                break;
            default:
                break;
        }
        int startI = toFile || toConsole ? 1 : 0;
        Tpmms algo = new Tpmms(Arrays.copyOfRange(args, startI, args.length), toFile, toConsole);
        return algo.run();
        
    }

}