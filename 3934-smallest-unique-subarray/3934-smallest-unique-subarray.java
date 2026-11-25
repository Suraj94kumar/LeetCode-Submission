import java.util.*;

class Solution {

    static final long MOD1 = 1000000007;
    static final long MOD2 = 998244353;
    static final long BASE = 233;

    long[] h1, h2;
    long[] p1, p2;

    public int smallestUniqueSubarray(int[] nums) {

        int n = nums.length;

        h1 = new long[n + 1];
        h2 = new long[n + 1];

        p1 = new long[n + 1];
        p2 = new long[n + 1];

        p1[0] = 1;
        p2[0] = 1;

        // Precompute prefix hashes
        for (int i = 1; i <= n; i++) {

            p1[i] = (p1[i - 1] * BASE) % MOD1;
            p2[i] = (p2[i - 1] * BASE) % MOD2;

            h1[i] = (h1[i - 1] * BASE + nums[i - 1]) % MOD1;
            h2[i] = (h2[i - 1] * BASE + nums[i - 1]) % MOD2;
        }

        int low = 1;
        int high = n;
        int ans = n;

        // Binary Search
        while (low <= high) {

            int mid = low + (high - low) / 2;

            if (check(n, mid)) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }

    boolean check(int n, int len) {

        HashMap<Long, Integer> map = new HashMap<>();

        for (int i = len; i <= n; i++) {

            long hash1 = (h1[i] - h1[i - len] * p1[len] % MOD1 + MOD1) % MOD1;

            long hash2 = (h2[i] - h2[i - len] * p2[len] % MOD2 + MOD2) % MOD2;

            long hash = (hash1 << 30) | hash2;

            map.put(hash, map.getOrDefault(hash, 0) + 1);
        }

        for (int count : map.values()) {

            if (count == 1) {
                return true;
            }
        }

        return false;
    }
}