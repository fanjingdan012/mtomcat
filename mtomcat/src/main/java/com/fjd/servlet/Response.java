package com.fjd.servlet;

import com.fjd.utils.FileUtil;
import com.fjd.utils.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class Response {
    private OutputStream outputStream;

    public Response(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String content) {
        try {
            outputStream.write(content.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void writeHtml(String path) {
        String resourcePath = FileUtil.getResourcePath(path);
        File file = new File(resourcePath);
        if (file.exists()) {
            FileUtil.writeFile(file, outputStream);

        } else {
            write(HttpUtil.getHttpResponseContext404());
        }
    }
}
