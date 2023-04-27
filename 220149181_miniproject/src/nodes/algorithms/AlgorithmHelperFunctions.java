package nodes.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AlgorithmHelperFunctions {
	private List<Integer>[] adj;

	public static double getDistance(Double[] node1Coords, Double[] node2Coords) {
		double x1 = node1Coords[0];
		double y1 = node1Coords[1];

		double x2 = node2Coords[0];
		double y2 = node2Coords[1];

		double distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
		return distance;

	}

	public static Integer getEdgeWeight(int severity, double distance) {
		double edgeWeight = ((severity * 4) + distance);
		return (int) edgeWeight;

	}

	public boolean recommendNewSecuritySystem(int severity, double distance) {
		if (severity > 3 && distance > (severity * 3)) {
			return true;
		} else {
			return false;
		}

	}

	public void bfs(int s, int numberOfNodes) {
		boolean[] visited = new boolean[numberOfNodes];
		Queue<Integer> queue = new LinkedList<>();
		visited[s] = true;
		queue.offer(s);
		while (!queue.isEmpty()) {
			int u = queue.poll();
			for (int v : adj[u]) {
				if (!visited[v]) {
					visited[v] = true;
					queue.offer(v);
				}
			}
		}
	}

}
