package com.fjd;

import com.fjd.servlet.Request;
import com.fjd.servlet.Response;
import com.fjd.servlet.Servlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void main(String[] args) throws Exception {
        Map<String, ProjectLoader.ProjectConfiginfo> configinfoMap = ProjectLoader.load();
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Server started.");
        while (!serverSocket.isClosed()) {
            Socket request = serverSocket.accept();
            threadPool.execute(() -> {
                try {
                    InputStream inputStream = request.getInputStream();
                    System.out.println("receive request:");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                    String msg = null;
                    StringBuilder requestInfo = new StringBuilder();
                    while ((msg = reader.readLine()) != null) {
                        if (msg.length() == 0) {
                            break;
                        }
                        requestInfo.append(msg);
                    }
                    System.out.println(requestInfo);
                    // parse request info
                    String firstLine = requestInfo.toString().split("\r\n")[0];
                    String projectName = firstLine.split(" ")[1].split("/")[1];
                    String servletPath = firstLine.split(" ")[1].replace("/" + projectName, "");
                    String servletName = configinfoMap.get(projectName).servletMappings.get(servletPath);
                    Servlet servlet = configinfoMap.get(projectName).servletInstances.get(servletName);
                    Request servletRequest = createRequest();
                    Response servletResponse = createResponse();
                    servlet.service(servletRequest, servletResponse);


                    String response = "Hello mTomcat";
                    byte[] responseBytes = response.getBytes();
                    OutputStream outputStream = request.getOutputStream();
                    outputStream.write("HTTP/1.1 200 OK \r\n".getBytes());
                    outputStream.write("Content-Type: text/html;charset=utf-8 \r\n".getBytes());
                    outputStream.write(("Content-Length: " + responseBytes.length + " \r\n").getBytes());
                    outputStream.write(("\r\n").getBytes());
                    outputStream.write(responseBytes);
                    System.out.println("request end");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


    }

    private static Response createResponse() {
        return new Response();
    }

    private static Request createRequest() {
        return new Request() {

        };
    }
}
