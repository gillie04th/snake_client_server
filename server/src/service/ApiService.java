package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;

import utils.Message;

public class ApiService {

    private String url;
    private HttpClient httpClient;

    public ApiService(String url) {
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

    public String post(String resource, Message data) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(new URI(url + resource)).POST(BodyPublishers.ofString("{\"email\":\"test\", \"password\":\"test\"}")).build();
            HttpResponse response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // URL url = new URL (this.url + resource);
            // HttpURLConnection con = (HttpURLConnection)url.openConnection();
            // con.setRequestMethod("POST");
            // con.setRequestProperty("Content-Type", "application/json");
            // con.setRequestProperty("Accept", "application/json");
            // con.setDoOutput(true);
            // String jsonInputString = "{\"email\": \"test\", \"password\": \"test\"}";
            // try(OutputStream os = con.getOutputStream()) {
            //     byte[] input = jsonInputString.getBytes("utf-8");
            //     os.write(input, 0, input.length);
            // }
            // System.out.println("Debug");
            // try(BufferedReader br = new BufferedReader(
            // new InputStreamReader(con.getInputStream(), "utf-8"))) {
            //     StringBuilder response = new StringBuilder();
            //     String responseLine = null;
            //     while ((responseLine = br.readLine()) != null) {
            //         response.append(responseLine.trim());
            //     }
            //     System.out.println(response.toString());
            // }

            System.out.println(response.body().toString());
            return response.toString();
        } catch (IOException e) {
            return e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }
}
