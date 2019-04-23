package sample.model;

import org.mindrot.jbcrypt.BCrypt;

public class Hashing {
    private static String GetRandomSalt()
    {
        return BCrypt.gensalt(10);
    }

    public static String HashPassword(String password)
    {
        return BCrypt.hashpw(password, GetRandomSalt());
    }

    public static Boolean ValidatePassword(String password, String correctHash)
    {
        return BCrypt.checkpw(password, correctHash);
    }
}
