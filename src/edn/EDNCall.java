package edn;

import java.util.List;
import java.util.Map;

public class EDNCall implements EDNNode{
	String calls="";
	
	public EDNCall(String calls){
		this.calls = calls;
	}
	
	@Override
	public double eval(Map<Integer, List<Double>> samples, Map<Integer, Map<Integer, Double>> frequencies, Map<String, EDNNode> methodTable) {
		return methodTable.get(calls).eval(samples, frequencies, methodTable);
	}

	@Override
	public
	String toString(){
		return "c "+calls;
	}
}
