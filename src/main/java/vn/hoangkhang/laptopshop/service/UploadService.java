package vn.hoangkhang.laptopshop.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;

@Service
public class UploadService {
    private final ServletContext servletContext;

    public UploadService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String handleUploadFile(MultipartFile file, String targetFolder) {
        // don't upload file
        if (file.isEmpty())
            return "";
        // relative path: absolute path
        String rootPath = this.servletContext.getRealPath("/resources/images");
        String finalName = "";
        try {
            byte[] bytes = file.getBytes();

            File dir = new File(rootPath + File.separator + targetFolder);
            if (!dir.exists())
                dir.mkdirs();

            // Create the file on server
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);
            // uuid

            BufferedOutputStream stream = new BufferedOutputStream(
                    new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalName;
    }

    public List<String> handleUploadMultipleFiles(MultipartFile[] files, String targetFolder) {
        List<String> fileNames = new ArrayList<>();

        String rootPath = this.servletContext.getRealPath("/resources/images");

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String finalName = "";
            try {
                byte[] bytes = file.getBytes();

                File dir = new File(rootPath + File.separator + targetFolder);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

                File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);

                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                fileNames.add(finalName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileNames;
    }
}
