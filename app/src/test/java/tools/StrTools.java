package tools;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StrTools {

    private static String TAG = "StrTools";

    public static String strFromInputstream(InputStream in)  {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            Log.d(TAG, builder.toString());
        }
        catch(IOException ioe){
            ioe.printStackTrace();
        }
        finally {
            return builder.toString();
        }
    }

}
