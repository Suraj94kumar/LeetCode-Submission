class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;
        int zero = 0;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero = i;
                break;
            }
        }

        int ans = Integer.MAX_VALUE;

        boolean inc = true;
        boolean dec = true;

        for (int i = 1; i < n; i++) {
            int prev = nums[(zero + i - 1) % n];
            int curr = nums[(zero + i) % n];

            if (curr != (prev + 1) % n) {
                inc = false;
            }

            if (curr != (prev - 1 + n) % n) {
                dec = false;
            }
        }

        if (inc) {
            ans = Math.min(ans, zero);
            ans = Math.min(ans, n - zero + 2);
        }

        if (dec) {
            ans = Math.min(ans, zero + 2);
            ans = Math.min(ans, n - zero);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}