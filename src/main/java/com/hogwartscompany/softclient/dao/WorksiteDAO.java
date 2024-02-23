package com.hogwartscompany.softclient.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hogwartscompany.softclient.model.Address;
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

public class WorksiteDAO {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:5655/api/v1/worksites";

    public List<Worksite> getAllWorksites() {
        //La méthode nous permet de récupérer les sites de travail avec le GET de l'API (adresse)
        //On récupère une chaîne de caractères JSON (format donné par l'API) pour la découper en fonction des champs définis dans notre classe
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL); // Utilisation de l'URL stockée en tant que variable globale
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //On vérifie que la connexion s'est effectivement faite avant de continuer
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //On a besoin de spécifier l'encodage UTF-8 lors de la création InputStreamReader → affichage des accents
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                String inputLine; //Ligne récupérée

                //Tant que la boucle ne tombe pas sur une ligne vide, on continue de rajouter les lignes
                while ((inputLine = in.readLine()) != null) {
                    responseString.append(inputLine);
                }
                //On ferme la lecture à la première ligne vide
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

    public Worksite getWorksiteById(int idWorksite) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/" + idWorksite);
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
        Worksite responseWorksite = parseJsonString(responseString.toString());
        return responseWorksite;
    }

    public void createWorksite(NewWorksite newWorksite) throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newWorksite);
            byte[] input = jsonWorksite.getBytes("utf-8");
            outputStream.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Échec de la requête : " + responseCode);
        }
        connection.disconnect();
    }

    public Worksite deleteWorksite(int idWorksite) throws IOException {
            URL url = new URL(API_URL + "/" + idWorksite);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // La suppression a réussi
            System.out.println("Le site de travail a été supprimé avec succès.");
        } else {
            // La suppression a échoué, lancer une exception avec le code de réponse
            throw new IOException("Échec de la suppression du chantier. Code de réponse : " + responseCode);
        }
        connection.disconnect();
        return null;
    }

    public Worksite updateWorksite (int idWorksite, NewWorksite newWorksite) throws IOException {
        URL url = new URL(API_URL + "/" + idWorksite);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        try (OutputStream outputStream = connection.getOutputStream()) {
            String jsonWorksite = objectMapper.writeValueAsString(newWorksite);
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

    public List<Worksite> searchWorksiteByName(String searchTerm) {
        StringBuilder responseString = new StringBuilder();

        try {
            URL url = new URL(API_URL + "/searchWorksite?searchWorksite=" + URLEncoder.encode(searchTerm, StandardCharsets.UTF_8));
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

    public static Worksite parseJsonString(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonString, Worksite.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Création de la méthode parseJsonArray pour découper les chaînes de caractères récupérées → sous format de table vu que GetAll
    private List<Worksite> parseJsonArray(String jsonArray) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonArray, new TypeReference<List<Worksite>>() {

            });
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
