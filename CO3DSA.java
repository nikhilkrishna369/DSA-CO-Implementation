import java.util.*;

class Edge {

    int to;
    int weight;

    Edge(int to,int weight) {
        this.to = to;
        this.weight = weight;
    }
}

public class PrimMST {

    static final int V = 8;

    ArrayList<Edge>[] graph =
            new ArrayList[V];

    PrimMST() {

        for(int i=0;i<V;i++)
            graph[i] = new ArrayList<>();
    }

    void addEdge(int u,int v,int w) {

        graph[u].add(new Edge(v,w));
        graph[v].add(new Edge(u,w));
    }

    void prim(int source) {

        boolean[] visited =
                new boolean[V];

        PriorityQueue<int[]> pq =
                new PriorityQueue<>(
                        Comparator.comparingInt(a -> a[1])
                );

        pq.offer(new int[]{source,0,-1});

        int totalCost = 0;

        System.out.println("MST Edges:");

        while(!pq.isEmpty()) {

            int[] cur = pq.poll();

            int u = cur[0];
            int wt = cur[1];
            int parent = cur[2];

            if(visited[u])
                continue;

            visited[u] = true;
            totalCost += wt;

            if(parent != -1)
                System.out.println(
                        parent + " - " + u +
                                " : " + wt);

            for(Edge e : graph[u]) {

                if(!visited[e.to]) {

                    pq.offer(
                            new int[]{
                                    e.to,
                                    e.weight,
                                    u
                            });
                }
            }
        }

        System.out.println(
                "Total MST Cost = "
                        + totalCost);
    }

    public static void main(String[] args) {

        PrimMST g = new PrimMST();

        g.addEdge(0,1,4);
        g.addEdge(0,2,3);
        g.addEdge(1,3,5);
        g.addEdge(2,3,4);
        g.addEdge(2,4,6);
        g.addEdge(3,5,2);
        g.addEdge(4,5,7);
        g.addEdge(5,6,2);
        g.addEdge(6,7,3);
        g.addEdge(4,7,4);

        g.prim(0);
    }
}