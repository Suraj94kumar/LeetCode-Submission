import java.util.*;

class Solution {

    TreeMap<Integer, Integer> left = new TreeMap<>();
    TreeMap<Integer, Integer> right = new TreeMap<>();

    long sumLeft = 0, sumRight = 0;
    int sizeLeft = 0, sizeRight = 0;

    void add(TreeMap<Integer, Integer> map, int val) {
        map.put(val, map.getOrDefault(val, 0) + 1);
    }

    void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);

        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }

    void balance() {
        int total = sizeLeft + sizeRight;
        int target = (total + 1) / 2;

        while (sizeLeft > target) {
            int val = left.lastKey();

            remove(left, val);
            sizeLeft--;
            sumLeft -= val;

            add(right, val);
            sizeRight++;
            sumRight += val;
        }

        while (sizeLeft < target) {
            int val = right.firstKey();

            remove(right, val);
            sizeRight--;
            sumRight -= val;

            add(left, val);
            sizeLeft++;
            sumLeft += val;
        }
    }

    void insert(int val) {
        if (left.isEmpty() || val <= left.lastKey()) {
            add(left, val);
            sizeLeft++;
            sumLeft += val;
        } else {
            add(right, val);
            sizeRight++;
            sumRight += val;
        }

        balance();
    }

    void erase(int val) {
        if (left.containsKey(val)) {
            remove(left, val);
            sizeLeft--;
            sumLeft -= val;
        } else {
            remove(right, val);
            sizeRight--;
            sumRight -= val;
        }

        balance();
    }

    long getCost() {
        long median = left.lastKey();

        return median * sizeLeft - sumLeft
                + sumRight - median * sizeRight;
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        long[] cost = new long[n - x + 1];

        left.clear();
        right.clear();

        sumLeft = 0;
        sumRight = 0;
        sizeLeft = 0;
        sizeRight = 0;

        for (int i = 0; i < x; i++) {
            insert(nums[i]);
        }

        cost[0] = getCost();

        for (int i = x; i < n; i++) {
            erase(nums[i - x]);
            insert(nums[i]);

            cost[i - x + 1] = getCost();
        }

        long INF = Long.MAX_VALUE / 4;

        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }

        for (int j = 1; j <= k; j++) {

            for (int i = n - 1; i >= 0; i--) {

                // Skip current index
                dp[i][j] = dp[i + 1][j];

                // Select subarray of length x
                if (i + x <= n &&
                    dp[i + x][j - 1] != INF) {

                    dp[i][j] = Math.min(
                            dp[i][j],
                            cost[i] + dp[i + x][j - 1]
                    );
                }
            }
        }

        return dp[0][k];
    }
}