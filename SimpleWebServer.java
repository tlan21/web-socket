import java.io.*;
import java.net.*;
import java.nio.file.*;

public class SimpleWebServer {
    // Constants
    private static final int PORT = 8080;
    private static final String WEB_ROOT = ".";
    
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        
        try {
            // Initialize ServerSocket on port 8080
            serverSocket = new ServerSocket(PORT);
            System.out.println("Web Server started on port " + PORT);
            System.out.println("Web root directory: " + new File(WEB_ROOT).getAbsolutePath());
            
            // Accept connections in infinite loop
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                
                // Handle the client request
                handleClient(clientSocket);
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing server socket: " + e.getMessage());
                }
            }
        }
    }
    
    private static void handleClient(Socket clientSocket) {
        BufferedReader in = null;
        OutputStream out = null;
        
        try {
            // Get input and output streams
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = clientSocket.getOutputStream();
            
            // Read the first line of the HTTP request
            String requestLine = in.readLine();
            
            if (requestLine == null || requestLine.isEmpty()) {
                return;
            }
            
            System.out.println("Request: " + requestLine);
            
            // Parse the request line (e.g., "GET /index.html HTTP/1.1")
            String[] tokens = requestLine.split(" ");
            if (tokens.length < 2) {
                return;
            }
            
            String filePath = tokens[1];
            
            // Read remaining headers (consume them)
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                // Just consume the headers
            }
            
            // Crucial: If the path is /, map it to /index.html
            if (filePath.equals("/")) {
                filePath = "/index.html";
            }
            
            // Remove leading slash
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            
            // Check if the file exists
            Path path = Paths.get(WEB_ROOT, filePath);
            File file = path.toFile();
            
            if (file.exists() && !file.isDirectory()) {
                // File Found - Send 200 OK
                byte[] fileBytes = Files.readAllBytes(path);
                String contentType = getContentType(filePath);
                
                // Send HTTP Header
                String header = "HTTP/1.1 200 OK\r\n" +
                               "Content-Type: " + contentType + "\r\n" +
                               "Content-Length: " + fileBytes.length + "\r\n" +
                               "Connection: close\r\n" +
                               "\r\n";
                
                out.write(header.getBytes());
                out.write(fileBytes);
                out.flush();
                
                // Print log
                System.out.println("File sent: " + filePath);
                
            } else {
                // File Not Found - Send 404
                String response = "HTTP/1.1 404 Not Found\r\n" +
                                 "Content-Type: text/plain\r\n" +
                                 "Connection: close\r\n" +
                                 "\r\n" +
                                 "404 - File Not Found";
                
                out.write(response.getBytes());
                out.flush();
                
                // Print log
                System.out.println("404 File Not Found: " + filePath);
            }
            
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            // Close the socket in finally block
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    
    private static String getContentType(String fileName) {
        if (fileName.endsWith(".html") || fileName.endsWith(".htm")) {
            return "text/html";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else {
            return "application/octet-stream";
        }
    }
}
