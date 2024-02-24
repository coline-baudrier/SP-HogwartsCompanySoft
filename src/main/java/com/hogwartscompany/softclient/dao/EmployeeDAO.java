package com.hogwartscompany.softclient.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwartscompany.softclient.model.Employee;
import com.hogwartscompany.softclient.model.NewEmployee;
import com.hogwartscompany.softclient.model.NewWorksite;
import com.hogwartscompany.softclient.model.Worksite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

public class EmployeeDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:5655/api/v1/employees";

    public List<Employee> getAllEmployees() {
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

    public Employee getEmployeeById(int idEmployee) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/" + idEmployee);
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
        Employee responseEmployee = parseJsonString(responseString.toString());
        return responseEmployee;
    }

    public void createEmployee(NewEmployee newEmployee) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newEmployee);
            byte[] input = jsonWorksite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();
    }

    public Employee deleteEmployee(int idEmployee) throws IOException {
        URL url = new URL(API_URL + "/" + idEmployee);
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

    public static Employee parseJsonString(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, Employee.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Employee updateEmployee (int idEmployee, NewEmployee newEmployee) throws IOException {
        URL url = new URL(API_URL + "/" + idEmployee);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newEmployee);
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

    public List<Employee> searchEmployeeByName(String searchTerm) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/searchEmployee?searchEmployee=" + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8));
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

    private List<Employee> parseJsonArray(String jsonArray) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, new TypeReference<List<Employee>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

}