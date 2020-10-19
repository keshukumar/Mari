package gautam.easydevelope.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gautam on 4/6/2017.
 */

public class DateUtils {

    public static Date stringToDate(String dateString, String formatString){
        try {
            DateFormat sdf = new SimpleDateFormat(formatString);
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String dateToString(Date date, String format){
        try {
            DateFormat sdf = new SimpleDateFormat(format);
            String s = sdf.format(date);
            return s;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String changeFormat(String dateString, String fromFormat, String toFormat){
        return DateUtils.dateToString(DateUtils.stringToDate(dateString, fromFormat), toFormat);
    }
}
