package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static ru.job4j.io.EchoServer.Request.*;

public class EchoServer {
    private final Map<String, Request> stringToRequest = new HashMap<>(Map.of(
            "GET /?msg=Hello HTTP/1.1", HELLO,
            "GET /?msg=Exit HTTP/1.1", EXIT));
    private Map.Entry<String, Request> request = Map.entry("", ANOTHER);
    private final static Pattern GET_PATTERN = Pattern.compile("GET /\\?msg=(\\S*) HTTP/1\\.1");

    enum Request {
        HELLO, EXIT, ANOTHER
    }

    public void doJob() throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (request.getValue() != EXIT) {
                Socket socket = server.accept();
                try (var in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     var out = new PrintStream(socket.getOutputStream(), true)) {
                    readRequest(in);
                    writeResponse(out);
                }
            }
        }
    }

    public void readRequest(BufferedReader in) throws IOException {
            var method = in.readLine();
            request = Map.entry(method, stringToRequest.getOrDefault(method, ANOTHER));
    }

    public void writeResponse(PrintStream out) {
            out.println("HTTP/1.1 200 OK");
            out.println();
            switch (request.getValue()) {
                case HELLO:
                    out.println("Hello, dear friend.");
                    break;
                case ANOTHER:
                    var matcher = GET_PATTERN.matcher(request.getKey());
                    if (matcher.matches()) {
                        out.println(matcher.group(1));
                    }
                    break;
                case EXIT:
                    break;
                default:
            }
    }

    public static void main(String[] args) throws IOException {
        new EchoServer().doJob();
    }
}