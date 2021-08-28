package ru.job4j.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.job4j.io.EchoServer.Request.*;

public final class EchoServer {
    private final static Pattern GET_PATTERN = Pattern.compile("GET /\\?msg=(\\S*) HTTP/1\\.1");
    private static EchoServer server = null;
    private final Map<String, Request> stringToRequest = new HashMap<>(Map.of(
            "GET /?msg=Hello HTTP/1.1", HELLO,
            "GET /?msg=Exit HTTP/1.1", EXIT));
    private Map.Entry<String, Request> requestEntry = Map.entry("", ANOTHER);
    private final Map<Request, Consumer<PrintStream>> requestToResponse = new EnumMap<>(Request.class);

    enum Request {
        HELLO, EXIT, ANOTHER
    }

    private EchoServer() {
        requestToResponseInit();
    }

    public static EchoServer getServer() {
        return server == null
                ? new EchoServer()
                : server;
    }

    public void doJob() throws IOException {
        try (ServerSocket server = new ServerSocket(9000)) {
            while (requestEntry.getValue() != EXIT) {
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
            requestEntry = Map.entry(method, stringToRequest.getOrDefault(method, ANOTHER));
    }

    public void writeResponse(PrintStream out) {
            out.println("HTTP/1.1 200 OK");
            out.println();
            requestToResponse.get(requestEntry.getValue())
                    .accept(out);
    }

    private void requestToResponseInit() {
        requestToResponse.put(HELLO, out -> out.println("Hello, dear friend."));
        requestToResponse.put(ANOTHER, out -> {
            var matcher = GET_PATTERN.matcher(requestEntry.getKey());
            if (matcher.matches()) {
                out.println(matcher.group(1));
            }
        });
        requestToResponse.put(EXIT, out -> { });
    }

    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger(EchoServer.class.getName());
        try {
            EchoServer.getServer().doJob();
        } catch (IOException e) {
            log.error("Exception in doJob", e);
        }
    }
}