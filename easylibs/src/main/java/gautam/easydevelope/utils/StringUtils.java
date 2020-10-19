package gautam.easydevelope.utils;

/**
 * Created by gautam on 4/12/2017.
 */

public class StringUtils{

    public static String firstCaps(String string){

        String s1 = string.substring(0, 1).toUpperCase();
        String stringCapitalized = s1 + string.substring(1);
        return stringCapitalized;
    }
}
