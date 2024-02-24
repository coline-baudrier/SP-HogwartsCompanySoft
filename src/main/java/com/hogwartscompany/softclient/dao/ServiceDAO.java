package com.hogwartscompany.softclient.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwartscompany.softclient.model.Employee;
import com.hogwartscompany.softclient.model.NewServiceSite;
import com.hogwartscompany.softclient.model.ServiceSite;
import com.hogwartscompany.softclient.model.Worksite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Provider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServiceDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:5655/api/v1/services";

    public List<ServiceSite> getAllServiceSites() {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                in.close();
            } else {
                responseString.append("Erreur de réponse de l'API. Code : ").append(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel à l'API : ").append(e.getMessage());
        }
        return parseJsonArray(responseString.toString());
    }

    public List<ServiceSite> getServicesByWorksite(int idWorksite) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/searchByWorksite/" + idWorksite);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                in.close();
            } else {
                responseString.append("Erreur de réponse de l'API. Code : ").append(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel à l'API : ").append(e.getMessage());
        }
        List<ServiceSite> allServices = parseJsonArray(responseString.toString());
        List<ServiceSite> servicesBySite = new ArrayList<>();

        for (ServiceSite service : allServices) {
            if (service.getIdWorksite() == idWorksite) {
                servicesBySite.add(service);
            }
        }
        return servicesBySite;
    }

    public ServiceSite getServiceSiteById(int idServiceSite) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/" + idServiceSite);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                in.close();
            } else {
                responseString.append("Erreur de réponse de l'API. Code : ").append(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel de l'API : ").append(e.getMessage());
        }
        ServiceSite responseServiceSite = parseJsonString(responseString.toString());
        return responseServiceSite;
    }

    public void createServiceSite(NewServiceSite newServiceSite) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newServiceSite);
            byte[] input = jsonWorksite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();
    }

    public Worksite deleteServiceSite(int idServiceSite) throws IOException {
        URL url = new URL(API_URL + "/" + idServiceSite);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Le site de travail a été supprimé avec succès.");
        } else {
            throw new IOException("Échec de la suppression du chantier. Code de réponse : " + responseCode);
        }
        connection.disconnect();
        return null;
    }

    public Worksite updateServiceSite (int idServiceSite, NewServiceSite newServiceSite) throws IOException {
        URL url = new URL(API_URL + "/" + idServiceSite);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);
        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newServiceSite);
            byte[] input = jsonWorksite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();
        return null;
    }

    public List<ServiceSite> searchServiceSiteByName(String searchTerm) {
        StringBuilder responseString = new StringBuilder();
        try {
            URL url = new URL(API_URL + "/searchService?searchServiceSite=" + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                in.close();
            } else {
                responseString.append("Erreur de réponse de l'API. Code : ").append(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
            responseString.append("Erreur lors de l'appel à l'API : ").append(e.getMessage());
        }
        return parseJsonArray(responseString.toString());
    }

    public static ServiceSite parseJsonString(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, ServiceSite.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ServiceSite> parseJsonArray(String jsonArray) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, new TypeReference<List<ServiceSite>>() {

            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
