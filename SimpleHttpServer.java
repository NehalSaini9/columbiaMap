import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class SimpleHttpServer {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MapHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Server started on http://localhost:8080/");
    }

    static class MapHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<html><body>" +
                    "<h1>Interactive Campus Map</h1>" +
                    "<div id='map' style='height: 600px; width: 100%;'></div>" +
                    "<script src='https://unpkg.com/leaflet/dist/leaflet.js'></script>" +
                    "<link rel='stylesheet' href='https://unpkg.com/leaflet/dist/leaflet.css' />" +
                    "<script>" +
                    "var map = L.map('map').setView([40.7128, -74.0060], 13);" +
                    "L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {" +
                    "attribution: '&copy; <a href=\"https://www.openstreetmap.org/copyright\">OpenStreetMap</a> contributors'" +
                    "}).addTo(map);" +
                    "L.marker([40.807384, -73.963036]).addTo(map)" +
                    ".bindPopup('Columbia Campus')" +
                    ".openPopup();" +
                    "L.marker([40.806290, -73.963005]).addTo(map)" +
                    ".bindPopup('Butler Library: STUDY SPOT')" +
                    ".openPopup();" +
                    "</script>" +
                    "</body></html>";

            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}