package org.poc.app.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.poc.app.security.FtpConfig;
import org.poc.app.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FtpService {

	@Autowired
    private FtpRemoteFileTemplate remoteFileTemplate;

    @Autowired
    private FtpConfig.FtpGateway gateway;

    /**
     * 
     * @param String fileName
     * @param String savePath
     * @return File
     */
    public File downloadFile(String fileName, String savePath) {
        return remoteFileTemplate.execute(session -> {
            boolean existFile = session.exists(fileName);
            if (existFile) {
                InputStream is = session.readRaw(fileName);
                return FileUtils.convertInputStreamToFile(is, savePath);
            } else {
            	System.out.println("file : {} not exist"+ fileName);
                return null;
            }
        });
    }

    /**
     * 
     * @param MultipartFile multipartFile
     * @throws IOException
     */
    public void uploadFile(MultipartFile multipartFile) throws IOException {
        gateway.send(FileUtils.convert(multipartFile));
    }
}
