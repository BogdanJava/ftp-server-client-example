package by.bogdan.ftp.client;

import by.bogdan.ftp.server.HomeHelper;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.*;

/**
 * @author Bogdan Shishkin
 * date/time: 3/15/2018/6:08 PM
 * emails: bahdan.shyshkin@itechart-group.com
 * bogdanshishkin1998@gmail.com
 */

public class FTPUtils {

    public static void uploadDirectory(FTPClient ftpClient, String remoteDirPath,
                                       String localParentDir, String remoteParentDir) throws IOException {
        System.out.println("LISTING directory: " + localParentDir);

        File localDir = new File(localParentDir);
        File[] subFiles = localDir.listFiles();

        if (subFiles != null && subFiles.length > 0) {
            for (File item : subFiles) {
                String remoteFilePath = remoteDirPath + "\\" + remoteParentDir + "\\" + item.getName();
                if (remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "\\" + item.getName();
                }

                if (item.isFile()) {
                    String localFilePath = item.getAbsolutePath();
                    System.out.println("About to upload the file: " + localFilePath);
                    boolean uploaded = uploadSingleFile(ftpClient, localFilePath, remoteFilePath);
                    if (uploaded) {
                        System.out.println("Uploaded a file to: " + remoteFilePath);
                    } else {
                        System.out.println("Couldn't upload the file: " + localFilePath);
                    }
                } else {
                    boolean created = ftpClient.makeDirectory(remoteFilePath);
                    if (created) {
                        System.out.println("Created the directory: " + remoteFilePath);
                    } else {
                        System.out.println("Couldn't create the directory: " + remoteFilePath);
                    }
                }

                String parent = remoteParentDir + "\\" + item.getName();
                if (remoteParentDir.equals("")) {
                    parent = item.getName();
                }

                localParentDir = item.getAbsolutePath();
                uploadDirectory(ftpClient, remoteDirPath, localParentDir, parent);
            }
        }
    }

    public static void retrieveSingleFile(FTPClient ftpClient, String localPath, String remotePath) throws IOException {
        File file = new File(HomeHelper.HOME_URL + localPath);
        if (file.exists()) {
            System.out.println("File with such name is already exists");
            return;
        } else {
            file.createNewFile();
        }

        try (InputStream inputStream = ftpClient.retrieveFileStream(remotePath);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
             BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(file))) {

            StringBuilder stringBuilder = new StringBuilder();
            bufferedReader.lines().forEach(line -> stringBuilder.append(line).append("\n"));
            writer.write(stringBuilder.toString().getBytes());
        }
    }


    public static boolean uploadSingleFile(FTPClient ftpClient, String localPath, String remotePath) throws IOException {
        File localFile = new File(localPath);

        try (InputStream inputStream = new FileInputStream(localFile)) {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.storeFile(remotePath, inputStream);
        }
    }

    public static FTPClient getLoggedInClient(String server, int port, String username, String password) {
        FTPClient ftpClient = new FTPClient();

        // connect and login to the server
        try {
            ftpClient.connect(server, port);
            boolean loggedIn = ftpClient.login(username, password);

            System.out.println("Logged in: " + loggedIn);

            // use local passive mode to pass firewall
            ftpClient.enterLocalPassiveMode();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("Connected");
        return ftpClient;
    }

    public static void shutdown(FTPClient ftpClient) {

        // log out and disconnect from the server
        try {
            ftpClient.logout();
            ftpClient.disconnect();

            System.out.println("Disconnected");
        } catch (IOException e) {
            System.out.println("Cannot disconnect");
            e.printStackTrace();
        }
    }

    public static void createDirIfNotExists(String path) {
        File file = new File(HomeHelper.HOME_URL + path);
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
