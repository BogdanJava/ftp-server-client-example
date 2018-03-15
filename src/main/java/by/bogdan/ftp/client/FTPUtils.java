package by.bogdan.ftp.client;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.ftpserver.ftplet.FtpException;

import java.io.File;
import java.io.IOException;

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
                }
            }
        }
    }

    public static boolean uploadSingleFile() throws FtpException {
        return false;
    }
}
