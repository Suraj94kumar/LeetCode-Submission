class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;

        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            int free = 0;

            for (int j = 0; j < n; j++) {
                if (j != i && items[j][0] % factor == 0) {
                    free++;
                }
            }

            int[] buy = new int[budget + 1];
            java.util.Arrays.fill(buy, -1000000);

            for (int b = price; b <= budget; b++) {
                buy[b] = Math.max(
                    dp[b - price] + free + 1,
                    buy[b - price] + 1
                );
            }

            for (int b = 0; b <= budget; b++) {
                dp[b] = Math.max(dp[b], buy[b]);
            }
        }

        int ans = 0;

        for (int x : dp) {
            ans = Math.max(ans, x);
        }

        return ans;
    }
}