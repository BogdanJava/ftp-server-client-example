package by.bogdan.ftp.security;

import org.apache.ftpserver.usermanager.PasswordEncryptor;

/**
 * @author Bogdan Shishkin
 * date/time: 3/15/2018/5:54 PM
 * emails: bahdan.shyshkin@itechart-group.com
 * bogdanshishkin1998@gmail.com
 */

public class CustomPasswordEncryptor implements PasswordEncryptor {

    public String encrypt(String s) {
        return s;
    }

    public boolean matches(String s, String s1) {
        return s.equals(s1);
    }
}
