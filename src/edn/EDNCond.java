package edn;

import java.util.List;
import java.util.Map;

public class EDNCond implements EDNNode {
	EDNBlock id;
	EDNNode body;
	EDNNode els;

	public EDNCond(int id, EDNNode body, EDNNode els) {
		this.id = new EDNBlock(id);
		this.body = body;
		this.els = els;
	}
	public EDNCond(EDNBlock id, EDNNode body, EDNNode els) {
		this.id = id;
		this.body = body;
		this.els = els;
	}

	@Override
	public double eval(Map<Integer, List<Double>> samples, Map<Integer, Map<Integer, Double>> frequencies, Map<String, EDNNode> methodTable) {
		double t = frequencies.get(id.id).containsKey(1) ? frequencies.get(id.id).get(1) : 0.0;
		double f = frequencies.get(id.id).containsKey(0) ? frequencies.get(id.id).get(0) : 0.0;

		double headTime = sample(samples, id.id);
		double trueTime = t * body.eval(samples, frequencies, methodTable);
		double falseTime = f * els.eval(samples, frequencies, methodTable);

		return headTime + trueTime + falseTime;
	}
	
	@Override
	public
	String toString(){
		return "if("+id.id+") "+body+" "+els;
	}
}
