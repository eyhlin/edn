package edn;

import java.util.List;
import java.util.Map;
import java.util.Random;

public interface EDNNode {
	double eval(Map<Integer, List<Double>> samples,
			Map<Integer, Map<Integer, Double>> frequencies, Map<String, EDNNode> methodTable);

	default double sample(Map<Integer, List<Double>> samples, int id) {
		Random r = new Random();
		List<Double> samplesList = samples.get(id);
		if(samplesList==null) return 0;
		return samplesList.get(r.nextInt(samplesList.size()));
	}
}
