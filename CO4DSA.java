import java.util.*;

public class BMTCBellmanFord {

    static class Edge {
        int source;
        int destination;
        int weight;

        Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static final int INF = Integer.MAX_VALUE;

    public static void bellmanFord(List<Edge> edges, int vertices,
                                   int source, String[] hubs) {

        int[] dist = new int[vertices];

        Arrays.fill(dist, INF);
        dist[source] = 0;

        System.out.println("=========== BMTC ROUTE ANALYSIS ===========\n");
        System.out.println("Running Bellman-Ford from " +
                           hubs[source] + "...\n");

        // V - 1 relaxations
        for (int i = 1; i < vertices; i++) {

            boolean updated = false;

            for (Edge edge : edges) {

                int u = edge.source;
                int v = edge.destination;
                int w = edge.weight;

                if (dist[u] != INF &&
                    dist[u] + w < dist[v]) {

                    dist[v] = dist[u] + w;
                    updated = true;
                }
            }

            System.out.println("Iteration " + i + " completed.");

            // Early convergence
            if (!updated)
                break;
        }

        System.out.println("\nFinal Distances:\n");

        for (int i = 0; i < vertices; i++) {
            System.out.println(hubs[i] + " : " + dist[i]);
        }

        // Negative Cycle Detection
        boolean negativeCycle = false;

        for (Edge edge : edges) {

            int u = edge.source;
            int v = edge.destination;
            int w = edge.weight;

            if (dist[u] != INF &&
                dist[u] + w < dist[v]) {

                negativeCycle = true;
                break;
            }
        }

        System.out.println("\nNegative Cycle Check:");

        if (negativeCycle)
            System.out.println("Negative Cycle Detected");
        else
            System.out.println("No Negative Cycle Detected");

        System.out.println("\nProgram Executed Successfully.");
    }

    public static void main(String[] args) {

        String[] hubs = {
                "MJC", //0
                "KEM", //1
                "JAY", //2
                "KOR", //3
                "WHF", //4
                "HBR", //5
                "MRT"  //6
        };

        int V = hubs.length;

        List<Edge> edges = new ArrayList<>();

        // BMTC Route Network

        edges.add(new Edge(0, 1, 8));   // MJC -> KEM
        edges.add(new Edge(0, 2, 5));   // MJC -> JAY

        edges.add(new Edge(2, 3, 4));   // JAY -> KOR
        edges.add(new Edge(3, 4, 6));   // KOR -> WHF

        edges.add(new Edge(2, 5, 10));  // JAY -> HBR
        edges.add(new Edge(1, 5, 7));   // KEM -> HBR

        edges.add(new Edge(4, 6, -3));  // WHF -> MRT
        edges.add(new Edge(5, 6, 4));   // HBR -> MRT

        int source = 0; // MJC

        bellmanFord(edges, V, source, hubs);
    }
}