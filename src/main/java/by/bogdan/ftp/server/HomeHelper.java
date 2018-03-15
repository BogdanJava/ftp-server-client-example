package by.bogdan.ftp.server;

import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.User;
import org.apache.ftpserver.ftplet.UserManager;

/**
 * @author Bogdan Shishkin
 * date/time: 3/15/2018/5:58 PM
 * emails: bahdan.shyshkin@itechart-group.com
 * bogdanshishkin1998@gmail.com
 */

public class HomeHelper {

    private static final String HOME_URL = "D:\\home\\";

    public static String getFilePath(String fileName) {
        return HOME_URL + fileName;
    }

    public static void saveUser(User user, UserManager userManager) {
        try {
            userManager.save(user);
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }
}
