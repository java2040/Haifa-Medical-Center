Haifa Medical Center - A Simple Inplementation of a Java Web Server.

This is a basic web server implemented in Java that serves static files such as HTML, CSS, JavaScript, and images. It listens on port 80 (HTTP) and handles GET requests. The server is multi-threaded, allowing it to handle multiple client connections concurrently.
Setup and Running

Prerequisites: Ensure you have at least Java SE 8.0 installed on your system. You can download it from Oracle's website.

Directory Structure: Place your static web files (e.g., index.html, styles.css, script.js, images) in a directory named webroot within the project root.

Compilation: Compile the Java source files using a Java compiler. For example:
javac org/haifamedicalcenter/Main.java org/haifamedicalcenter/ClientHandler.java


Running the Server: Run the compiled Main class. You may need to run it with elevated privileges if using port 80:
sudo java org.haifamedicalcenter.Main

Alternatively, you can change the port to a higher number (e.g., 8080) in the Main.java file to avoid needing root privileges:
final int PORT = 8080;


In the browser address field type: 

http://localhost/index.html

and press enter to open the home page.


IPv4 and IPv6 Support
The server is configured to bind to all available IPv6 interfaces by default. If you prefer to use IPv4, modify the Main.java file by uncommenting the IPv4 line and commenting out the IPv6 line, like this:

try ( ServerSocket serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName("0.0.0.0")) ) {
    // ...
}

// To work with IPv6 Interfaces use this "try with resources" line.
// try ( ServerSocket serverSocket = new ServerSocket(PORT, 0, InetAddress.getByName("::")) ) {
    // ...
}


Security Considerations

Port Usage: The server listens on port 80 by default, which may require root privileges on some systems. Consider using a higher port number if you encounter permission issues.
HTTPS Support: Currently, the server only supports HTTP. Future updates will include HTTPS support on port 443.

Project Structure

Main.java: Sets up the server socket and listens for incoming connections. Each connection is handled by a new thread running the ClientHandler class.
ClientHandler.java: Processes individual client requests, serving static files from the webroot directory based on the requested URL.

Limitations

Only handles GET requests; other HTTP methods (e.g., POST, PUT, DELETE) are not supported.
Serves only static content; no support for dynamic content or server-side scripting.
Basic error handling; does not handle all possible edge cases.

Contributing
Contributions are welcome! If you'd like to improve the server, please follow these steps:

Fork the repository.
Create a new branch for your feature or bug fix.
Submit a pull request with a clear description of your changes.

Please report any issues or suggest features via the GitHub Issues page.

License

This project is licensed under the MIT License. See the LICENSE file for details.

Contact

For questions or feedback, please contact java87.2023@gmail.com.

