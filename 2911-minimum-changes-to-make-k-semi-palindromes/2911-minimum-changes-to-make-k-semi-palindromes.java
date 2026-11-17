import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();

        int[][] cost = new int[n][n];
        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, n);
        }

        // Calculate cost of every substring
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {

                int len = j - i + 1;
                int minCost = n;

                for (int d = 1; d < len; d++) {

                    if (len % d != 0) {
                        continue;
                    }

                    int changes = 0;

                    for (int offset = 0; offset < d; offset++) {

                        int l = i + offset;
                        int r = j - d + 1 + offset;

                        while (l < r) {
                            if (s.charAt(l) != s.charAt(r)) {
                                changes++;
                            }

                            l += d;
                            r -= d;
                        }
                    }

                    minCost = Math.min(minCost, changes);
                }

                cost[i][j] = minCost;
            }
        }

        // Dynamic Programming
        dp[n][0] = 0;

        for (int i = n - 1; i >= 0; i--) {

            for (int j = 1; j <= k; j++) {

                for (int l = i + 1; l < n; l++) {

                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[l + 1][j - 1] + cost[i][l]
                    );
                }
            }
        }

        return dp[0][k];
    }
}