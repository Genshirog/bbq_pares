package Back_end;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {
    public Formatter(){

    }

    public String capitalizeLetters(String input){
        String[] words = input.split(" ");
        StringBuilder builder = new StringBuilder();
        for(String word : words){
            if(!word.isEmpty()){
                builder.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return builder.toString().trim();
    }

    public String capitalizeHyphenLetters(String input) {
        StringBuilder builder = new StringBuilder();
        String[] words = input.split("-");

        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                builder.append(Character.toUpperCase(words[i].charAt(0))) // Capitalize the first letter
                        .append(words[i].substring(1).toLowerCase()); // Lowercase the rest
            }
            // Append a hyphen back if it's not the last word
            if (i < words.length - 1) {
                builder.append("-");
            }
        }

        return builder.toString();
    }


    public String phone_formatter(String phoneNumber){
        phoneNumber = phoneNumber.replaceAll("\\D","");
        if(phoneNumber.length() == 11 && phoneNumber.startsWith("09")){
            return phoneNumber.substring(0,4) + "-" + phoneNumber.substring(4,7) + "-" + phoneNumber.substring(7,11);
        }else{
            throw new IllegalArgumentException("Invalid phone number");
        }
    }

    public String isValidEmail(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        boolean isValid =  matcher.matches();
        if(isValid){
            return email;
        }else{
            throw new IllegalArgumentException("Invalid email");
        }
    }

    public String isValidShift(String shift){
        switch (shift){
            case "Day" -> {return "Day";}
            case "Night" -> {return "Night";}
            case "Full-Time" -> {return "Full-Time";}
            default -> {throw new IllegalArgumentException("Invalid shift");}
        }
    }
}
