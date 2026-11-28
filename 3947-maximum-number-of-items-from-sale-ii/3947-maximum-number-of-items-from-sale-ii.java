import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {

        int n = items.length;

        int[] freq = new int[n + 1];

        int minPrice = Integer.MAX_VALUE;

        // Count factors and find minimum price
        for (int[] item : items) {
            freq[item[0]]++;
            minPrice = Math.min(minPrice, item[1]);
        }

        // Calculate number of divisible factors
        int[] gain = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                gain[i] += freq[j];
            }
            gain[i]--;
        }

        // Sort items by price
        Arrays.sort(items, (a, b) -> Integer.compare(a[1], b[1]));

        long ans = 0;
        long remaining = budget;

        // Greedily purchase items that give a free item
        for (int[] item : items) {

            int factor = item[0];
            int price = item[1];

            if (gain[factor] <= 0) {
                continue;
            }

            if (price > 2L * minPrice) {
                break;
            }

            long count = Math.min(gain[factor], remaining / price);

            ans += count * 2;
            remaining -= count * price;
        }

        // Spend remaining budget on cheapest item
        ans += remaining / minPrice;

        return (int) ans;
    }
}