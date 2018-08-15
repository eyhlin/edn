package edn;

import java.util.List;
import java.util.Map;

public class EDNWhile implements EDNNode {
	int id;
	EDNNode body;

	public EDNWhile(int id, EDNNode body) {
		this.id = id;
		this.body = body;
	}

	@Override
	public double eval(Map<Integer, List<Double>> samples, Map<Integer, Map<Integer, Double>> frequencies,
			Map<String, EDNNode> methodTable) {
		double eval = 0.0;
		Map<Integer, Double> rft = frequencies.get(id);
		for (Integer loops : rft.keySet()) {
			double f = rft.get(loops);
			for (int i = 0; i < loops; i++) {
				eval += f * (sample(samples, id) + body.eval(samples, frequencies, methodTable));
			}
			eval += sample(samples, id);
		}
		return eval;
	}

	@Override
	public String toString() {
		return "while(" + id + ") " + body;
	}
}
