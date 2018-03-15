package by.bogdan.ftp.client;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.ftpserver.ftplet.FtpException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

        if(subFiles != null && subFiles.length > 0) {
            for(File item : subFiles) {
                String remoteFilePath = remoteDirPath + "\\" + remoteParentDir + "\\" + item.getName();
                if(remoteParentDir.equals("")) {
                    remoteFilePath = remoteDirPath + "\\" + item.getName();
                }

                if(item.isFile()) {
                    String localFilePath = item.getAbsolutePath();
                    System.out.println("About to upload the file: " + localFilePath);
                    boolean uploaded = uploadSingleFile(ftpClient, localFilePath, remoteFilePath);
                    if(uploaded) {
                        System.out.println("Uploaded a file to: " + remoteFilePath);
                    } else {
                        System.out.println("Couldn't upload the file: " + localFilePath);
                    }
                } else {
                    boolean created = ftpClient.makeDirectory(remoteFilePath);
                    if(created) {
                        System.out.println("Created the directory: " + remoteFilePath);
                    } else {
                        System.out.println("Couldn't create the directory: " + remoteFilePath);
                    }
                }

                String parent = remoteParentDir + "\\" + item.getName();
                if(remoteParentDir.equals("")) {
                    parent = item.getName();
                }

                localParentDir = item.getAbsolutePath();
                uploadDirectory(ftpClient, remoteDirPath, localParentDir, parent);
            }
        }
    }

    public static boolean uploadSingleFile(FTPClient ftpClient, String localPath, String remotePath) throws IOException {
        File localFile = new File(localPath);

        try (InputStream inputStream = new FileInputStream(localFile)) {
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            return ftpClient.storeFile(remotePath, inputStream);
        }
    }
}
