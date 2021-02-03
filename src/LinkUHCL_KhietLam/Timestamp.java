/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LinkUHCL_KhietLam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author kitsune
 */
public class Timestamp {

    //a format string for the date and time
    public static final String DATE_FORMAT_NOW = "MM-dd-yy HH:mm:ss";
    //get the date and time now
    static Calendar curtime = Calendar.getInstance();
    static SimpleDateFormat timestamp = new SimpleDateFormat(DATE_FORMAT_NOW);
    public static String timestamp()
    {
        return timestamp.format(curtime.getTime());
    }
   
    
}

