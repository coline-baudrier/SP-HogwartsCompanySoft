package com.hogwartscompany.softclient.dao;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ServiceDAO {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String API_URL = "http://localhost:5655/api/v1/services";
}
