package org.poc.app.resources;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.poc.app.service.FtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/file/")
public class FileApi {

	@Value("${file.downloadPath:/}")
	private String downloadPath;
	
	@Autowired
	private FtpService ftpService;
	
	/**
	 *
	 * @param String fileName
	 * @return ResponseEntity<byte[]>
	 * @throws IOException
	 */
	@PostMapping(value = "/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileName") String fileName) throws IOException {
        File file = ftpService.downloadFile(fileName, downloadPath + fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
    
    /**
     * 
     * @param MultipartFile uploadFile
     * @return ResponseEntity<String>
     */
	@PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile uploadFile) {
		
        if (uploadFile.isEmpty()) {
            return "Please select a file!";
        }

        try {
            ftpService.uploadFile(uploadFile);
        } catch (IOException e) {
            return "Invalid input!";
        }

        return "Successfully uploaded - " + uploadFile.getOriginalFilename();
    }
}
