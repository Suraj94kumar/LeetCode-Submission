import java.util.*;

class Solution {
    private int[] head, to, next;
    private int[] first, last, depth;
    private int[][] lift;
    private int time = 0;
    private int maxPower;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                         String s, String[] queries) {
        first = new int[n];
        last = new int[n];
        depth = new int[n];
        maxPower = 31 - Integer.numberOfLeadingZeros(n);
        lift = new int[maxPower + 1][n];

        int m = edges.length;
        head = new int[n];
        to = new int[2 * m];
        next = new int[2 * m];
        Arrays.fill(head, -1);

        for (int i = 0; i < m; i++) {
            int a = edges[i][0];
            int b = edges[i][1];

            to[2 * i] = b;
            next[2 * i] = head[a];
            head[a] = 2 * i;

            to[2 * i + 1] = a;
            next[2 * i + 1] = head[b];
            head[b] = 2 * i + 1;
        }

        dfs(0, -1);

        BIT bit = new BIT(n + 1);
        char[] arr = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (arr[i] - 'a');
            bit.update(first[i], mask);
            bit.update(last[i] + 1, mask);
        }

        List<Boolean> ans = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int node = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                if (arr[node] == c) {
                    continue;
                }

                int mask = (1 << (arr[node] - 'a'))
                         ^ (1 << (c - 'a'));

                bit.update(first[node], mask);
                bit.update(last[node] + 1, mask);

                arr[node] = c;

            } else {
                int a = Integer.parseInt(parts[1]);
                int b = Integer.parseInt(parts[2]);

                int ancestor = lca(a, b);

                int mask = bit.query(first[a])
                         ^ bit.query(first[b])
                         ^ (1 << (arr[ancestor] - 'a'));

                ans.add((mask & (mask - 1)) == 0);
            }
        }

        return ans;
    }

    private void dfs(int node, int parent) {
        first[node] = ++time;

        for (int i = head[node]; i != -1; i = next[i]) {
            int child = to[i];

            if (child == parent) {
                continue;
            }

            depth[child] = depth[node] + 1;
            lift[0][child] = node;

            for (int j = 1; j <= maxPower; j++) {
                lift[j][child] =
                    lift[j - 1][lift[j - 1][child]];
            }

            dfs(child, node);
        }

        last[node] = time;
    }

    private int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int diff = depth[a] - depth[b];

        for (int i = maxPower; i >= 0; i--) {
            if ((diff & (1 << i)) != 0) {
                a = lift[i][a];
            }
        }

        if (a == b) {
            return a;
        }

        for (int i = maxPower; i >= 0; i--) {
            if (lift[i][a] != lift[i][b]) {
                a = lift[i][a];
                b = lift[i][b];
            }
        }

        return lift[0][a];
    }

    private static class BIT {
        int[] tree;
        int n;

        BIT(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int val) {
            for (int i = index + 1; i <= n; i += i & -i) {
                tree[i] ^= val;
            }
        }

        int query(int index) {
            int ans = 0;

            for (int i = index + 1; i > 0; i -= i & -i) {
                ans ^= tree[i];
            }

            return ans;
        }
    }
}