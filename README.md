# Simple Web Server - Java Socket Programming

A basic HTTP web server implemented from scratch using Java Socket Programming (TCP).

## Features

- **Port**: Runs on port 8080
- **Protocol**: HTTP/1.1
- **Libraries**: Uses only standard `java.net` and `java.io` (no external frameworks)
- **Supports**: HTML, CSS, JavaScript, images, and text files
- **Security**: Includes directory traversal protection

## How It Works

1. **Creates server socket** on port 8080
2. **Waits for client connections** from browsers
3. **Parses HTTP request headers** to extract the requested filename
4. **Reads files** from the local disk
5. **Sends HTTP response** with appropriate headers and file content

## How to Run

### 1. Compile the Java file:
```bash
javac SimpleWebServer.java
```

### 2. Run the server:
```bash
java SimpleWebServer
```

### 3. Test in your browser:
Open your browser and navigate to:
- http://localhost:8080/
- http://localhost:8080/index.html

## HTTP Methods Supported

- **GET**: Retrieve files from the server

## Error Handling

The server handles the following HTTP status codes:
- **200 OK**: File found and sent successfully
- **400 Bad Request**: Malformed HTTP request
- **403 Forbidden**: Access denied (directory traversal attempt)
- **404 Not Found**: File does not exist
- **501 Not Implemented**: HTTP method not supported

## File Structure

```
web_socket/
├── SimpleWebServer.java    # Main server implementation
├── index.html              # Sample HTML file to test
└── README.md               # This file
```

## Testing

Place any HTML, CSS, JS, or image files in the same directory as the server, and they will be served when requested.

## Stopping the Server

Press `Ctrl + C` in the terminal to stop the server.

## Note

This is a basic educational implementation. For production use, consider:
- Multi-threading for concurrent connections
- Proper logging
- Configuration files
- HTTPS support
- More robust error handling
