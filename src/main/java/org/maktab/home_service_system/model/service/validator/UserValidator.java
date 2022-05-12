package org.maktab.home_service_system.model.service.validator;

public class UserValidator {
    public static boolean checkPassword(String newPassword) {
        boolean digit = false;
        boolean word = false;
        if (newPassword.length() >= 8) {
            for (char chr : newPassword.toCharArray()) {
                if (Character.isDigit(chr))
                    digit = true;
                if (Character.isLetter(chr))
                    word = true;
            }
            return digit && word;
        }
        return false;
    }
}
