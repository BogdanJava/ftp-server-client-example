package by.bogdan.ftp.server;

import by.bogdan.ftp.security.CustomPasswordEncryptor;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Bogdan Shishkin
 * date/time: 3/15/2018/5:46 PM
 * emails: bahdan.shyshkin@itechart-group.com
 * bogdanshishkin1998@gmail.com
 */

public class FtpServerImpl {

    private static final int FTP_PORT = 2002;

    public static void main(String[] args) {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();

        listenerFactory.setPort(FTP_PORT);
        serverFactory.addListener("default", listenerFactory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("D:\\home\\file.txt"));
        userManagerFactory.setPasswordEncryptor(new CustomPasswordEncryptor());

        BaseUser user = new BaseUser();
        user.setName("AGarraux");
        user.setPassword("bugaga1");
        user.setHomeDirectory(HomeHelper.getFilePath("file.txt"));

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);

        UserManager userManager = userManagerFactory.createUserManager();
        HomeHelper.saveUser(user, userManager);

        serverFactory.setFtplets(new HashMap<String, Ftplet>() {
            {
                put("miaFtplet", new CustomFtplet());
            }
        });

        FtpServer ftpServer = serverFactory.createServer();
        try {
            ftpServer.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }
}
