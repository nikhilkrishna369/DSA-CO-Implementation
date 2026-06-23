
class Delivery {

    int over;
    int ball;

    int batsmanId;
    int bowlerId;
    int runs;

    Delivery(int over, int ball) {
        this.over = over;
        this.ball = ball;
    }

    @Override
    public String toString() {
        return "(" + over + "," + ball + ")";
    }
}

public class CricketDeliverySorting {

    // Stable Counting Sort by Ball Number
    static Delivery[] countingSortByBall(Delivery[] in) {

        final int K = 12; // Ball range: 0-12

        int[] count = new int[K + 1];

        // Step 1: Count frequencies
        for (Delivery d : in) {
            count[d.ball]++;
        }

        // Step 2: Prefix sums
        for (int i = 1; i <= K; i++) {
            count[i] += count[i - 1];
        }

        Delivery[] out = new Delivery[in.length];

        // Step 3: Reverse traversal for stability
        for (int i = in.length - 1; i >= 0; i--) {

            Delivery d = in[i];

            out[--count[d.ball]] = d;
        }

        return out;
    }

    // Stable Counting Sort by Over Number
    static Delivery[] countingSortByOver(Delivery[] in) {

        final int K = 50; // Over range: 0-49

        int[] count = new int[K + 1];

        // Step 1: Count frequencies
        for (Delivery d : in) {
            count[d.over]++;
        }

        // Step 2: Prefix sums
        for (int i = 1; i <= K; i++) {
            count[i] += count[i - 1];
        }

        Delivery[] out = new Delivery[in.length];

        // Step 3: Reverse traversal for stability
        for (int i = in.length - 1; i >= 0; i--) {

            Delivery d = in[i];

            out[--count[d.over]] = d;
        }

        return out;
    }

    public static void main(String[] args) {

        Delivery[] deliveries = {

                new Delivery(2, 4),
                new Delivery(1, 1),
                new Delivery(3, 6),
                new Delivery(1, 5),
                new Delivery(2, 2),
                new Delivery(3, 1),
                new Delivery(1, 3),
                new Delivery(2, 6),
                new Delivery(3, 4),
                new Delivery(1, 2)
        };

        System.out.println("===== CRICKET DELIVERY SORTING =====");

        System.out.println("\nInput (Unsorted):");
        for (Delivery d : deliveries) {
            System.out.print(d + " ");
        }

        // Pass 1: Sort by Ball Number
        deliveries = countingSortByBall(deliveries);

        System.out.println("\n\nAfter Pass 1 (Stable Counting Sort by Ball):");
        for (Delivery d : deliveries) {
            System.out.print(d + " ");
        }

        // Pass 2: Sort by Over Number
        deliveries = countingSortByOver(deliveries);

        System.out.println("\n\nAfter Pass 2 (Stable Counting Sort by Over):");
        for (Delivery d : deliveries) {
            System.out.print(d + " ");
        }

        System.out.println("\n\nFinal Chronological Order:");
        for (Delivery d : deliveries) {
            System.out.print(d + " ");
        }

        System.out.println("\n\nComplexity Analysis:");
        System.out.println("Counting Sort Time Complexity  : O(n + K)");
        System.out.println("Counting Sort Space Complexity : O(n + K)");
        System.out.println("Radix Sort Time Complexity     : O(d(n + K))");

        System.out.println("\nSorting Completed Successfully.");
    }
}