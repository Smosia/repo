package com.mediatek.mdmlsample;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by MTK03685 on 2015/10/7.
 */
public class TrapFileReader {
    private static final String TAG = "TrapFileReader";
    String trapFilePath = Environment.getExternalStorageDirectory().getPath() + "/Download/MDML_output.txt";  // hard code now
    private BufferedReader m_fileReader;
    private int m_trapCount = 0;

    public TrapFileReader(){
        // open read file
        try {
            m_fileReader = new BufferedReader(new FileReader(trapFilePath));
        }
        catch (Exception e){
            Log.e(TAG, "Read file open failed. path = " + trapFilePath);
        }
    }

    public StringBuilder GetLastTrapContext(){
        if(m_fileReader != null){
            m_trapCount++;
            try {
                StringBuilder text = new StringBuilder();
                String line;

                // move to the first line after "#*" syntax
                while(true){
                    line = m_fileReader.readLine();
                    Log.d(TAG, "Check prefix is #* , line = " + line);
                    if(line.startsWith("#*")) {
                        // record trap order here !!
                        // TODO: 2015/10/7
                        break;
                    }
                }

                // append line string into string builder until meat the line start with "*#*#" syntax
                while(true) {
                    line = m_fileReader.readLine();
                    if(line.startsWith("*#*#")) {
                        // end of this trap, break
                        Log.d(TAG, "Trap end");
                        break;
                    }
                    text.append(line);
                    text.append('\n');
                }

                return text;
            }
            catch(IOException ioe)
            {
                Log.e(TAG, "File Read error");
                return new StringBuilder("Read File is failed!");
            }
        }else{
            return new StringBuilder("Read File is not open!");
        }
    }

    public int GetCurrentTrapOrder(){
        return m_trapCount;
    }


}