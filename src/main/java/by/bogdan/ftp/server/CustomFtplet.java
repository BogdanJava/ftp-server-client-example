package by.bogdan.ftp.server;

import org.apache.ftpserver.ftplet.*;

import java.io.IOException;

/**
 * @author Bogdan Shishkin
 * date/time: 3/15/2018/6:02 PM
 * emails: bahdan.shyshkin@itechart-group.com
 * bogdanshishkin1998@gmail.com
 */

public class CustomFtplet implements Ftplet {

    @Override
    public void init(FtpletContext ftpletContext) throws FtpException {
        System.out.println("INIT FTPLET");
    }

    @Override
    public void destroy() {
        System.out.println("DESTROY FTPLET");
    }

    @Override
    public FtpletResult beforeCommand(FtpSession ftpSession, FtpRequest ftpRequest) throws FtpException, IOException {
        return FtpletResult.DEFAULT;
    }

    @Override
    public FtpletResult afterCommand(FtpSession ftpSession, FtpRequest ftpRequest, FtpReply ftpReply) throws FtpException, IOException {
        return FtpletResult.DEFAULT;
    }

    @Override
    public FtpletResult onConnect(FtpSession ftpSession) throws FtpException, IOException {
        return FtpletResult.DEFAULT;
    }

    @Override
    public FtpletResult onDisconnect(FtpSession ftpSession) throws FtpException, IOException {
        return FtpletResult.DEFAULT;
    }
}
