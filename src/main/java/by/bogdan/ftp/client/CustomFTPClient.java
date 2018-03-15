package by.bogdan.ftp.client;

import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;

/**
 * @author Bogdan Shishkin
 * date/time: 3/15/2018/6:28 PM
 * emails: bahdan.shyshkin@itechart-group.com
 * bogdanshishkin1998@gmail.com
 *
 * http://www.codejava.net/java-se/networking/ftp/how-to-upload-a-directory-to-a-ftp-server
 * Допилить
 */

public class CustomFTPClient {

    public static void main(String[] args) {
        String server = "localhost";
        int port = 21;
        String user = "username";
        String pass = "password";

        FTPClient ftpClient = new FTPClient();

        try {
            // connect and login to the server
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

            System.out.println("Connected");

            String remoteDirPath = "/Upload";
            String localDirPath = "E:/Test/Download/FTP/Test";

            FTPUtils.uploadDirectory(ftpClient, remoteDirPath, localDirPath, "");

            // log out and disconnect from the server
            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}