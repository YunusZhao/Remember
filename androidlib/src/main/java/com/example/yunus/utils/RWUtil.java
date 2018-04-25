package com.example.yunus.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RWUtil {

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    public static boolean isEmpty(String input){
        if(input==null||input.isEmpty())
            return true;
        for(int i=0; i<input.length(); i++){
            char c=input.charAt(i);
            if(c!=' '&&c!='\t'&&c!='\r'&&c!='\n')
            {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String str) {
        String regex = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\" +
                ".]*[a-zA-Z]$";
        return match(regex, str);
    }

    public static boolean isName(String str) {
        String regex = "^\\w{3,6}$";
        return match(regex, str);
    }

    public static boolean isPwd(String str) {
        String regex = "^\\w{6,12}$";
        return match(regex, str);
    }

    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
