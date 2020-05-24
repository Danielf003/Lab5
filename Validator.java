import examples.*;
/**The class for input validation. */
public class Validator {
    public static boolean isEmpty(String s){
        return s.length() == 0;
    }
    public static boolean isNull(String s){
        return s == null;
    }
    public static boolean isInt(String s){
        try {
            Integer.parseInt(s);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    public static boolean isLong(String s){
        try {
            Long.parseLong(s);
            return true;
        }
        catch(NumberFormatException e){
            return false;
        }
    }
    public static boolean isDiff(String s){
        try {
            Difficulty.valueOf(s);
            return true;
        }
        catch(IllegalArgumentException e){
            return false;
        }
    }
}