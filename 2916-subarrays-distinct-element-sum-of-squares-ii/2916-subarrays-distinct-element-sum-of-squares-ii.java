import java.util.Arrays;

class Solution {
    static final int MOD = 1_000_000_007;

    int n;
    long[] sum, squareSum, lazy;

    public int sumCounts(int[] nums) {
        n = nums.length;

        sum = new long[4 * n];
        squareSum = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];

            update(1, 0, n - 1, last[x] + 1, i);

            
            ans = (ans + squareSum[1]) % MOD;

            last[x] = i;
        }

        return (int) ans;
    }

    private void update(int node, int start, int end,
                        int left, int right) {
        if (left > end || right < start) {
            return;
        }

        if (left <= start && end <= right) {
            apply(node, start, end);
            return;
        }

        pushDown(node, start, end);

        int mid = start + (end - start) / 2;

        update(node * 2, start, mid, left, right);
        update(node * 2 + 1, mid + 1, end, left, right);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;

        squareSum[node] =
            (squareSum[node * 2] + squareSum[node * 2 + 1]) % MOD;
    }

    private void apply(int node, int start, int end) {
        long len = end - start + 1L;
        long oldSum = sum[node];

        squareSum[node] =
            (squareSum[node] + 2L * oldSum + len) % MOD;

        sum[node] = (sum[node] + len) % MOD;

        lazy[node]++;
        lazy[node] %= MOD;
    }

    private void pushDown(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) {
            return;
        }

        int mid = start + (end - start) / 2;

        applyLazy(node * 2, start, mid, lazy[node]);
        applyLazy(node * 2 + 1, mid + 1, end, lazy[node]);

        lazy[node] = 0;
    }

    private void applyLazy(int node, int start, int end, long val) {
        long len = end - start + 1L;
        long oldSum = sum[node];

        squareSum[node] =
            (squareSum[node] + 2L * val % MOD * oldSum % MOD
             + len % MOD * val % MOD * val % MOD) % MOD;

        sum[node] = (sum[node] + len % MOD * val) % MOD;

        lazy[node] = (lazy[node] + val) % MOD;
    }
}