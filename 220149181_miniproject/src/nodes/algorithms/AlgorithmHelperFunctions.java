package nodes.algorithms;

import java.util.List;

/**
 * 
 * AlgorithmHelperFunctions a class which includes a few functions to help run
 * my project
 *
 */
public class AlgorithmHelperFunctions {

	/**
	 * a function that returns the distance between any two locations
	 * @param node1Coords the first locations coordinates in the format of [x, y]
	 * @param node2Coords the second locations coordinates in the format of [x, y]
	 * @return distance	the distance between the two nodes
	 */
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

}
