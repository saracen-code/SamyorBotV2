package SamyorClasses;

import java.util.*;

public class WeightedWeb {
    private Map<City, List<Connection>> adjacencyMap;

    public WeightedWeb() {
        this.adjacencyMap = new HashMap<>();
    }

    // Inner class representing a connection between two cities
    private static class Connection {
        City city;
        double weight;
        boolean isSeagoing;

        Connection(City city, double weight, boolean isSeagoing) {
            this.city = city;
            this.weight = weight;
            this.isSeagoing = isSeagoing;
        }

        @Override
        public String toString() {
            return city.getName() + " (Distance: " + weight + ", " + (isSeagoing ? "Seagoing" : "Land") + ")";
        }
    }

    // Add a city
    public void addCity(City city) {
        adjacencyMap.putIfAbsent(city, new ArrayList<>());
    }

    // Add a land connection
    public void addLandConnection(City from, City to, double weight) {
        if (adjacencyMap.containsKey(from) && adjacencyMap.containsKey(to)) {
            adjacencyMap.get(from).add(new Connection(to, weight, false));
            adjacencyMap.get(to).add(new Connection(from, weight, false)); // Undirected graph
        }
    }

    // Add a seagoing connection (Only if both cities have a port)
    public void addSeagoingConnection(City from, City to, double weight) {
        if (from.hasPort() || to.hasPort()) {
            System.out.println("Error: Seagoing connection can only be established between port cities.");
            return;
        }
        if (adjacencyMap.containsKey(from) && adjacencyMap.containsKey(to)) {
            adjacencyMap.get(from).add(new Connection(to, weight, true));
            adjacencyMap.get(to).add(new Connection(from, weight, true)); // Undirected graph
        }
    }

    // Traverse map for a Character, checking if they have enough weight capacity
    public void traverseMap(Character character, City start) {
        if (!adjacencyMap.containsKey(start)) {
            System.out.println("Starting city not found in the network.");
            return;
        }

        System.out.println(character.getName() + " begins traversal from " + start.getName() + " with max weight capacity: " + character.getMaxWeight());

        Set<City> visited = new HashSet<>();
        Queue<City> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            City current = queue.poll();
            System.out.println("Currently at: " + current.getName());

            for (Connection conn : adjacencyMap.getOrDefault(current, new ArrayList<>())) {
                if (!visited.contains(conn.city) && character.getMaxWeight() >= conn.weight) {
                    System.out.println(" -> Moving to " + conn.city.getName() + " (Cost: " + conn.weight + ")");
                    queue.add(conn.city);
                    visited.add(conn.city);
                } else if (character.getMaxWeight() < conn.weight) {
                    System.out.println(" -> Cannot move to " + conn.city.getName() + " (Cost: " + conn.weight + ", too high!)");
                }
            }
        }
    }
}

