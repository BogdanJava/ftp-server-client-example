package by.bogdan.ftp.client;

import org.apache.commons.net.ftp.FTPClient;

import javax.swing.*;
import java.io.IOException;

/**
 * @author Bogdan Shishkin
 * date/time: 3/15/2018/6:28 PM
 * emails: bahdan.shyshkin@itechart-group.com
 * bogdanshishkin1998@gmail.com
 */

public class CustomFTPClient {

    public static void main(String[] args) {
        String server = "localhost";
        int port = 2221;
        String user = "bogdan";
        String pass = "bugaga1";

        FTPClient ftpClient = FTPUtils.getLoggedInClient(server, port, user, pass);

        while (true) {
            String command = JOptionPane.showInputDialog("What to do?\n1-upload\n2-retrieve");
            if (command.equals("exit")) break;
            switch (command) {
                case "1":
                    uploadFiles(ftpClient);
                    break;
                case "2":
                    getFiles(ftpClient);
                    break;
                default:
                    System.out.println("Incorrect command");
            }
        }
        if (ftpClient != null) {
            FTPUtils.shutdown(ftpClient);
        }

    }

    private static void getFiles(FTPClient ftpClient) {
        String localFileName = JOptionPane.showInputDialog("Enter a new file name");
        String remoteFileName = JOptionPane.showInputDialog("Enter path to the file to retrieve");
        try {
            FTPUtils.retrieveSingleFile(ftpClient, localFileName, remoteFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void uploadFiles(FTPClient ftpClient) {
        String remoteDirPath = JOptionPane.showInputDialog("Enter dist dir");
        FTPUtils.createDirIfNotExists(remoteDirPath);

        String localDirPath = JOptionPane.showInputDialog("Enter src dir");

        try {
            FTPUtils.uploadDirectory(ftpClient, remoteDirPath, localDirPath, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}