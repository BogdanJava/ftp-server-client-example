package by.bogdan.ftp.server;

import by.bogdan.ftp.security.CustomPasswordEncryptor;
import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.Ftplet;
import org.apache.ftpserver.ftplet.UserManager;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.ssl.SslConfigurationFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;

import java.io.File;
import java.io.IOException;
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

    private static final int FTP_PORT = 2221;
    private static File settings;

    static {
        settings = new File("src/main/resources/serverconfig.properties");
        if (!settings.exists()) {
            try {
                settings.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory listenerFactory = new ListenerFactory();

        listenerFactory.setPort(FTP_PORT);

        serverFactory.addListener("default", listenerFactory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(settings);
        userManagerFactory.setPasswordEncryptor(new CustomPasswordEncryptor());

        BaseUser user = new BaseUser();
        user.setName("bogdan");
        user.setPassword("bugaga1");
        user.setHomeDirectory(HomeHelper.HOME_URL);

        List<Authority> authorities = new ArrayList<>();
        authorities.add(new WritePermission());
        user.setAuthorities(authorities);

        UserManager userManager = userManagerFactory.createUserManager();
        HomeHelper.saveUser(user, userManager);

        serverFactory.setUserManager(userManager);
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
