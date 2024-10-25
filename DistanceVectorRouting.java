import java.util.*;
public class DistanceVectorRouting {
    private final Map<String, Map<String, Integer>> network = new HashMap<>();
    private static final int INFINITY = 9999;
    public void addNeighbor(String router, String neighbor, int cost) {
        network.putIfAbsent(router, new HashMap<>());
        network.putIfAbsent(neighbor, new HashMap<>());
        network.get(router).put(neighbor, cost);
        network.get(neighbor).put(router, cost); 
    }
    public Map<String, Map<String, Integer>> computeRoutingTables() {
        Map<String, Map<String, Integer>> routingTables = new HashMap<>();
        for (String router : network.keySet()) {
            Map<String, Integer> table = new HashMap<>();
            for (String dest : network.keySet()) {
                table.put(dest, router.equals(dest) ? 0 : network.get(router).getOrDefault(dest, INFINITY));
            }
            routingTables.put(router, table);
        }
        boolean updated;
        do {
            updated = false;
            for (String src : network.keySet()) {
                for (String neighbor : network.get(src).keySet()) {
                    for (String dest : routingTables.get(neighbor).keySet()) {
                        int newCost = routingTables.get(src).get(neighbor) + routingTables.get(neighbor).get(dest);
                        if (newCost < routingTables.get(src).get(dest)) {
                            routingTables.get(src).put(dest, newCost);
                            updated = true;
                        }
                    }
                }
            }
        } while (updated);

        return routingTables;
    }
    public static void main(String[] args) {
        DistanceVectorRouting dvr = new DistanceVectorRouting();
        dvr.addNeighbor("A", "B", 1);
        dvr.addNeighbor("B", "C", 1);
        dvr.addNeighbor("C", "D", 1);
        dvr.addNeighbor("D", "E", 1);
        dvr.addNeighbor("A", "C", 2);
        dvr.addNeighbor("B", "D", 2);
        Map<String, Map<String, Integer>> tables = dvr.computeRoutingTables();
        for (String router : tables.keySet()) {
            System.out.println("Router " + router + "'s Routing Table: " + tables.get(router));
        }
    }
}
