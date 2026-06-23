import java.util.*;

class Cargo {
    String id;
    int weight;
    int value;
    double ratio;

    Cargo(String id, int weight, int value) {
        this.id = id;
        this.weight = weight;
        this.value = value;
        this.ratio = (double) value / weight;
    }
}

public class LogisticsKnapsack {

    public static void main(String[] args) {

        // Consignments
        String[] ids = {"A", "B", "C", "D", "E", "F", "G", "H"};

        int[] weights = {4, 7, 5, 3, 6, 8, 9, 6};
        int[] values  = {30, 40, 35, 25, 45, 50, 60, 40};

        int capacity = 24;

        System.out.println("=========== 0/1 KNAPSACK ANALYSIS ===========");
        System.out.println("\nTruck Capacity : " + capacity + " tons");

        // ---------------- GREEDY APPROACH ----------------
        ArrayList<Cargo> cargos = new ArrayList<>();

        for (int i = 0; i < ids.length; i++) {
            cargos.add(new Cargo(ids[i], weights[i], values[i]));
        }

        cargos.sort((a, b) -> Double.compare(b.ratio, a.ratio));

        int currentWeight = 0;
        int greedyValue = 0;

        ArrayList<String> greedyItems = new ArrayList<>();

        for (Cargo c : cargos) {
            if (currentWeight + c.weight <= capacity) {
                greedyItems.add(c.id);
                currentWeight += c.weight;
                greedyValue += c.value;
            }
        }

        System.out.println("\nGreedy Solution:");
        System.out.print("Selected Items : ");
        for (String item : greedyItems)
            System.out.print(item + " ");

        System.out.println("\nTotal Weight   : " + currentWeight);
        System.out.println("Total Value    : " + greedyValue);

        // ---------------- DYNAMIC PROGRAMMING ----------------
        System.out.println("\nRunning Dynamic Programming...\n");

        int n = weights.length;
        int[][] dp = new int[n + 1][capacity + 1];

        for (int i = 1; i <= n; i++) {

            for (int w = 0; w <= capacity; w++) {

                if (weights[i - 1] <= w) {

                    dp[i][w] = Math.max(
                            dp[i - 1][w],
                            values[i - 1]
                                    + dp[i - 1][w - weights[i - 1]]
                    );

                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("DP Table Constructed Successfully\n");

        int optimalRevenue = dp[n][capacity];

        System.out.println("Optimal Revenue : " + optimalRevenue);

        // ---------------- BACKTRACKING ----------------
        ArrayList<String> selectedItems = new ArrayList<>();

        int w = capacity;

        for (int i = n; i > 0; i--) {

            if (dp[i][w] != dp[i - 1][w]) {

                selectedItems.add(ids[i - 1]);
                w -= weights[i - 1];
            }
        }

        Collections.reverse(selectedItems);

        int totalWeight = 0;
        int totalValue = 0;

        System.out.println("\nRecovered Items:");

        for (String item : selectedItems) {
            System.out.print(item + " ");

            int index = Arrays.asList(ids).indexOf(item);

            totalWeight += weights[index];
            totalValue += values[index];
        }

        System.out.println("\n");
        System.out.println("Total Weight : " + totalWeight);
        System.out.println("Total Value  : " + totalValue);

        System.out.println("\nProgram Executed Successfully.");
    }
}