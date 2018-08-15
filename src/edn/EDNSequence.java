package edn;

import java.util.List;
import java.util.Map;

public class EDNSequence implements EDNNode {
	public EDNNode left;
	public EDNNode right;

	public EDNSequence(EDNNode left, EDNNode right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public double eval(Map<Integer, List<Double>> samples, Map<Integer, Map<Integer, Double>> frequencies,
			Map<String, EDNNode> methodTable) {
		return left.eval(samples, frequencies, methodTable) + right.eval(samples, frequencies, methodTable);
	}

	@Override
	public String toString() {
		return left + " ; " + right;
	}
}
