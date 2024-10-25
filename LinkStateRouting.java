import java.util.*;
public class LinkStateRouting {
    private final Map<String, Map<String, Integer>> network = new HashMap<>();
    public void addEdge(String src, String dest, int cost) {
        network.putIfAbsent(src, new HashMap<>());
        network.putIfAbsent(dest, new HashMap<>());
        network.get(src).put(dest, cost);
        network.get(dest).put(src, cost); // Bidirectional
    }
    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<String> pq = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<String> visited = new HashSet<>();
        for (String router : network.keySet()) {
            distances.put(router, router.equals(start) ? 0 : Integer.MAX_VALUE);
        }
        pq.add(start);
        while (!pq.isEmpty()) {
            String current = pq.poll();
            if (!visited.add(current)) continue;
            for (var neighbor : network.get(current).entrySet()) {
                int newDist = distances.get(current) + neighbor.getValue();
                if (newDist < distances.get(neighbor.getKey())) {
                    distances.put(neighbor.getKey(), newDist);
                    pq.add(neighbor.getKey());
                }
            }
        }
        return distances;
    }
    public static void main(String[] args) {
        LinkStateRouting lsr = new LinkStateRouting();
        lsr.addEdge("A", "B", 1);
        lsr.addEdge("B", "C", 1);
        lsr.addEdge("C", "D", 1);
        lsr.addEdge("D", "E", 1);
        lsr.addEdge("A", "C", 3);
        lsr.addEdge("B", "D", 2);
        System.out.println("Link State Routing Tables:");
        for (String router : lsr.network.keySet()) {
            System.out.println("Shortest paths from " + router + ": " + lsr.dijkstra(router));
        }
    }
}
