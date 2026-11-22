import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            adj[parent[i]].add(i);
        }

        int[] newParent = new int[n];
        int[] last = new int[26];
        int[] prev = new int[n];

        Arrays.fill(last, -1);
        Arrays.fill(newParent, -1);

        // DFS using stack
        Stack<Integer> stack = new Stack<>();
        stack.push(0);

        List<Integer> order = new ArrayList<>();

        while (!stack.isEmpty()) {
            int u = stack.pop();

            if (u >= 0) {
                int ch = s.charAt(u) - 'a';

                prev[u] = last[ch];

                if (last[ch] != -1) {
                    newParent[u] = last[ch];
                } else {
                    newParent[u] = parent[u];
                }

                last[ch] = u;
                order.add(u);

                stack.push(~u);

                for (int v : adj[u]) {
                    stack.push(v);
                }

            } else {
                u = ~u;
                int ch = s.charAt(u) - 'a';
                last[ch] = prev[u];
            }
        }

        // Calculate subtree sizes
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[newParent[i]].add(i);
        }

        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        for (int i = n - 1; i >= 0; i--) {
            int u = order.get(i);

            if (newParent[u] != -1) {
                ans[newParent[u]] += ans[u];
            }
        }

        return ans;
    }
}