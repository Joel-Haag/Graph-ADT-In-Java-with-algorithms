package nodes.algorithms;

import java.util.*;

public class HopcroftKarp {
    private List<Integer>[] graph;
    private int[] match;
    private int[] dist;

    public int maximumMatching(List<Integer>[] graph, int n1, int n2) {
        this.graph = graph;
        int[] partition = new int[n1 + n2];
        Arrays.fill(partition, -1);
        this.match = new int[n1 + n2];
        Arrays.fill(match, -1);
        int matching = 0;
        while (bfs(partition, n1, n2)) {
            for (int u = 0; u < n1; u++) {
                if (partition[u] == 0 && match[u] == -1 && dfs(u)) {
                    matching++;
                }
            }
        }
        return matching;
    }

    private boolean bfs(int[] partition, int n1, int n2) {
        Queue<Integer> queue = new LinkedList<>();
        for (int u = 0; u < n1; u++) {
            if (partition[u] == 0 && match[u] == -1) {
                dist[u] = 0;
                queue.offer(u);
            } else {
                dist[u] = Integer.MAX_VALUE;
            }
        }
        dist[n1 + n2] = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (dist[u] < dist[n1 + n2]) {
                for (int v : graph[u]) {
                    if (dist[match[v]] == Integer.MAX_VALUE) {
                        dist[match[v]] = dist[u] + 1;
                        queue.offer(match[v]);
                    }
                }
            }
        }
        return dist[n1 + n2] != Integer.MAX_VALUE;
    }

    private boolean dfs(int u) {
        if (u != -1) {
            for (int v : graph[u]) {
                if (dist[match[v]] == dist[u] + 1 && dfs(match[v])) {
                    match[u] = v;
                    match[v] = u;
                    return true;
                }
            }
            dist[u] = Integer.MAX_VALUE;
            return false;
        }
        return true;
    }
}

