package org.haifamedicalcenter;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ClientHandler implements Runnable {

    private static final String WEB_ROOT = "webroot"; // Directory where HTML, CSS and JS files are stored.
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             OutputStream out = clientSocket.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null || !requestLine.startsWith("GET")) {
                return;
            }

            String requestedFile = requestLine.split(" ")[1].substring(1);
            if (requestedFile.isEmpty()) {
                requestedFile = "index.html";
            }

            // Debugging: Print requested file
            System.out.println("Requested file: " + requestedFile);

            // Determine content type
            String contentType;
            if (requestedFile.endsWith(".html")) {
                contentType = "text/html";
            } else if (requestedFile.endsWith(".css")) {
                contentType = "text/css";
            } else if (requestedFile.endsWith(".js")) {
                contentType = "application/javascript";
            } else if (requestedFile.endsWith(".jpg") || requestedFile.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (requestedFile.endsWith(".png")) {
                contentType = "image/png";
            } else if (requestedFile.endsWith(".gif")) {
                contentType = "image/gif";
            } else {
                send404(out);
                return;
            }

            Path filePath = Paths.get(WEB_ROOT, requestedFile);

            if (Files.exists(filePath)) {
                if (!Files.isReadable(filePath)) {
                    System.err.println("File exists but is not readable: " + filePath.toAbsolutePath());
                    sendError(out, "403 Forbidden", "File exists but is not readable.");
                } else if (!Files.isRegularFile(filePath)) {
                    System.err.println("Not a regular file: " + filePath.toAbsolutePath());
                    sendError(out, "403 Forbidden", "Requested path is not a regular file.");
                } else {
                    byte[] content = Files.readAllBytes(filePath);
                    sendResponse(out, "200 OK", contentType, content);
                }
            } else {
                System.err.println("File not found: " + filePath.toAbsolutePath());
                send404(out);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        PrintWriter writer = new PrintWriter(out, false);
        writer.println("HTTP/1.1 " + status);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println();
        writer.flush();
        out.write(content);
        out.flush();
    }

    private void send404(OutputStream out) throws IOException {
        String notFoundMessage = "<html><body><h1>404 - Not Found</h1></body></html>";
        sendResponse(out, "404 Not Found", "text/html", notFoundMessage.getBytes());
    }

    private void sendError(OutputStream out, String status, String message) throws IOException {
        String errorHtml = "<html><body><h1>" + status + "</h1><p>" + message + "</p></body></html>";
        sendResponse(out, status, "text/html", errorHtml.getBytes());
    }

}