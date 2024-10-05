package com.personal.blog_app.service.impl;

import com.personal.blog_app.service.IFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService implements IFileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // file name
        String fileName = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();
        assert fileName != null;
        String name = randomId.concat(fileName.substring(fileName.lastIndexOf(".")));

        // full path
        String filePath = path + File.separator + name;

        // create folder if not created
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream inputStream = new FileInputStream(fullPath);
        
        return inputStream;
    }
}
