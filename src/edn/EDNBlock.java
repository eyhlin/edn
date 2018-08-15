package edn;

import java.util.List;
import java.util.Map;

public class EDNBlock implements EDNNode {
	int id;

	public EDNBlock(int id) {
		this.id = id;
	}

	@Override
	public double eval(Map<Integer, List<Double>> samples, Map<Integer, Map<Integer, Double>> f, Map<String, EDNNode> methodTable) {
		return sample(samples, id);
	}
	
	@Override
	public
	String toString(){
		return id+"";
	}
}
