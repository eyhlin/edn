package edn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class EDNFor implements EDNNode {
	public int id;
	EDNNode body;

	public EDNFor(int id, EDNNode body) {
		this.id = id;
		this.body = body;
	}

	@Override
	public double eval(Map<Integer, List<Double>> samples, Map<Integer, Map<Integer, Double>> frequencies,
			Map<String, EDNNode> methodTable) {
		double eval = 0.0;
		Map<Integer, Double> rft = frequencies.get(id);
		Random r = new Random();

		double straw = r.nextDouble();
		Set<Entry<Integer, Double>> entries = rft.entrySet();
		List<Entry<Integer, Double>> l = new ArrayList<>();
		for (Entry<Integer, Double> entry : entries) {
			l.add(entry);
		}

		l.sort(new Comparator<Entry<Integer, Double>>() {
			@Override
			public int compare(Entry<Integer, Double> arg0, Entry<Integer, Double> arg1) {
				return Double.compare(arg0.getValue(), arg1.getValue());
			}
		}
		);
		
		int i=0;
		int loop = l.get(i).getKey();
		while(i+1<l.size()&&l.get(i+1).getValue()<=straw){
			i++;
			loop = l.get(i).getKey();
		}
		

		for (int loops = 0;loops<loop;loops++) {
			eval += body.eval(samples, frequencies, methodTable);
		}
		eval += sample(samples, id);
		return eval;
	}

	public double eval_bak(Map<Integer, List<Double>> samples, Map<Integer, Map<Integer, Double>> frequencies,
			Map<String, EDNNode> methodTable) {
		double eval = 0.0;
		Map<Integer, Double> rft = frequencies.get(id);
		for (Integer loops : rft.keySet()) {
			double f = rft.get(loops);
			for (int i = 0; i < loops; i++) {
				eval += f * body.eval(samples, frequencies, methodTable);
			}
			eval += sample(samples, id);
		}
		return eval;
	}

	@Override
	public String toString() {
		return "for(" + id + ") " + body;
	}

}
