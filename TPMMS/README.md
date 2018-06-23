#TPMMS
 Two Phase  Multi-Way Merge Sort

## To Compile 
 Please use: javac -d bin/ .\code\Main.java

## To Run 
    The flag -Xmx5m indicates the amount of memory to use. You can add memory by replacing the number. 
    Example -Xmx10m. It is preferable to not go below 5mb i.e -Xmx5m.
    
    To run without any logger information

        "Usage: java -Xmx5m  -cp bin/ code.Main Employees500000.txt Projects.txt"

    To run with console logger information

        "Usage: java -Xmx5m  -cp bin/ code.Main -c Employees500000.txt Projects.txt"

    To run and generate a log file

        "Usage: java -Xmx5m  -cp bin/ code.Main -f Employees500000.txt Projects.txt"

    To run with console logger and log file

        "Usage: java -Xmx5m  -cp bin/ code.Main -cf Employees500000.txt Projects.txt"

        Or

        "Usage: java -Xmx5m  -cp bin/ code.Main -d Employees500000.txt Projects.txt"