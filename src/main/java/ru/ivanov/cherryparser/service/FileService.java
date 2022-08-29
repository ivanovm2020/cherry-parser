package ru.ivanov.cherryparser.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.UUID;

@Service
public class FileService {

    @Value("${upload.path}")
    private String uploadPath;
    private String picture;

    public String getFile(String picture) {
        this.picture = picture;
        int slashIndex = picture.lastIndexOf("/");

        if (slashIndex == picture.length()) {
            picture = picture.substring(1, slashIndex);
        }

        slashIndex = picture.lastIndexOf("/");
        String name =   UUID.randomUUID().toString()
            + "-"
            + picture.substring(slashIndex + 1, picture.length());

        try {
                URL url = new URL(picture);
                InputStream in = url.openStream();
                OutputStream out = new BufferedOutputStream(new FileOutputStream(uploadPath + name));

                for (int nextByte; (nextByte = in.read()) != -1;) {
                    out.write(nextByte);
                }
                out.close();
                in.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        };

        return name;
    }
}
