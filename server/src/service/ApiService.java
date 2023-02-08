package service;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {

    private String url;
    private HttpClient httpClient;

    public ApiService(String url){
        this.url = url;
        this.httpClient = HttpClient.newHttpClient();
    }
    
    public String get(String resource, String params) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + resource + params)).GET().build();
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().toString();
        } catch (URISyntaxException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }

    public String post(String resource, String params) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(new URI(url + resource))
                                    .POST(HttpRequest.BodyPublishers.ofString(params))
                                    .build();
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body().toString();
        } catch (URISyntaxException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            return e.getMessage();
        }
    }
}
