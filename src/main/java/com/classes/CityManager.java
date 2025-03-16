package com.classes;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class CityManager {
    private static final String FILE_PATH = "src/main/java/com/java/data/cities.json";
    private static Gson gson = new Gson();
    private static Map<String, City> cities = new HashMap<>();

    // Load cities and connections from the file
    public static void loadCitiesFromFile() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            JsonArray cityArray = jsonObject.getAsJsonArray("cities");

            for (JsonElement cityElement : cityArray) {
                JsonObject cityData = cityElement.getAsJsonObject();
                String cityName = cityData.get("name").getAsString();
                String saskName = cityData.get("saskName").getAsString();
                int development = cityData.get("development").getAsInt();
                boolean hasPort = cityData.get("hasPort").getAsBoolean();

                City city = new City(cityName, saskName, development, hasPort);

                // Read connections for this city
                JsonArray connections = cityData.getAsJsonArray("connections");
                for (JsonElement connectionElement : connections) {
                    JsonObject connectionData = connectionElement.getAsJsonObject();
                    String connectedCityName = connectionData.get("city").getAsString();
                    double weight = connectionData.get("weight").getAsDouble();
                    boolean isSeagoing = connectionData.get("isSeagoing").getAsBoolean();
                    City connectedCity = cities.get(connectedCityName); // Already loaded cities
                    if (connectedCity != null) {
                        city.addLandConnection(city, connectedCity, weight);  // For simplicity, assume land connection for now
                    }
                }

                cities.put(cityName, city); // Store city
            }
        } catch (IOException e) {
            System.out.println("Error loading cities from file: " + e.getMessage());
        }
    }

    // Save cities and connections to the file
    public static void saveCitiesToFile() {
        JsonObject jsonObject = new JsonObject();
        JsonArray cityArray = new JsonArray();

        for (City city : cities.values()) {
            JsonObject cityData = new JsonObject();
            cityData.addProperty("name", city.getName());
            cityData.addProperty("saskName", city.getSaskName());
            cityData.addProperty("development", city.getDevelopment());
            cityData.addProperty("hasPort", city.hasPort());

            // Serialize connections
            JsonArray connections = new JsonArray();
            for (WeightedWeb.Connection conn : city.getConnections()) {
                JsonObject connectionData = new JsonObject();
                connectionData.addProperty("city", conn.city.getName());
                connectionData.addProperty("weight", conn.weight);
                connectionData.addProperty("isSeagoing", conn.isSeagoing);
                connections.add(connectionData);
            }
            cityData.add("connections", connections);
            cityArray.add(cityData);
        }

        jsonObject.add("cities", cityArray);

        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(jsonObject, writer);
        } catch (IOException e) {
            System.out.println("Error saving cities to file: " + e.getMessage());
        }
    }

    // Add city
    public static void addCity(City city) {
        cities.put(city.getName(), city);
        saveCitiesToFile(); // Save after adding a new city
    }

    public static City getCityByName(String cityName) {
        return cities.get(cityName);
    }
    // Tester com.Main function
    public static void main(String[] args) {
        // Load cities and connections from the file
        loadCitiesFromFile();

        // Print all cities and their connections
        for (City city : cities.values()) {
            System.out.println("City: " + city.getName());
            System.out.println("Sask Name: " + city.getSaskName());
            System.out.println("Development: " + city.getDevelopment());
            System.out.println("Has Port: " + city.hasPort());
            System.out.println("Connections:");

            for (WeightedWeb.Connection conn : city.getConnections()) {
                System.out.println("  - " + conn.city.getName() + " (Weight: " + conn.weight + ", Seagoing: " + conn.isSeagoing + ")");
            }
            System.out.println();
        }

        // Example: Add a new city and save
        City newCity = new City("NewCity", "New Sask", 10, true);
        City anotherCity = new City("AnotherCity", "Another Sask", 5, false);

        newCity.addLandConnection(newCity, anotherCity, 100);
        addCity(newCity);
        addCity(anotherCity);

        // Print again to see new cities added
        System.out.println("Updated Cities and Connections:");
        for (City city : cities.values()) {
            System.out.println("City: " + city.getName());
            System.out.println("Sask Name: " + city.getSaskName());
            System.out.println("Development: " + city.getDevelopment());
            System.out.println("Has Port: " + city.hasPort());
            System.out.println("Connections:");

            for (WeightedWeb.Connection conn : city.getConnections()) {
                System.out.println("  - " + conn.city.getName() + " (Weight: " + conn.weight + ", Seagoing: " + conn.isSeagoing + ")");
            }
            System.out.println();
        }
    }
}
