package utils;

public class Utils {

    private Utils(){
    }

    public static String trimString(String str){
        if(str != null){
            return str.trim();
        }
        System.err.println("Given string is null, returning null.");
        return str;
    }
}
