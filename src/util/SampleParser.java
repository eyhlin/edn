package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SampleParser {

	public Map<Integer, List<Double>> samples = new HashMap<>();
	public Map<Integer, Map<Integer, Double>> frequencies = new HashMap<>();
	public Map<Integer, String> calls = new HashMap<>();

	public void write() {
		// TODO write maps to file
	}

	//this parses a file and populates the maps of this instance
	public void parse(String filePath) throws FileNotFoundException {
		//.\\outputs\\vpc_output" + 0 + ".data
		BufferedReader buffer = new BufferedReader(
				new FileReader(new File(filePath)));
		try {
			String line = buffer.readLine();
			while (line != null) {
				String[] measure = line.split(":");
				if (measure.length < 2) {
					line = buffer.readLine();
					continue;
				}
				String id = measure[0];
				String m = measure[1];
				if (id.endsWith("f")) {
					id = id.substring(0, id.length() - 1);
					int times = Integer.parseInt(m.trim());
					if (!frequencies.containsKey(Integer.parseInt(id))) {
						Map<Integer, Double> f = new HashMap<>();
						f.put(times, 1.0);
						frequencies.put(Integer.parseInt(id), f);
					} else {
						Map<Integer, Double> f = frequencies.get(Integer.parseInt(id));
						if (f.containsKey(times))
							f.put(times, f.get(times) + 1.0);
						else {
							// System.out.println(f);
							// System.out.println(times);
							Map<Integer, Double> temp = new HashMap<>();
							temp.put(times, 1.0);
							f.put(times, 1.0);
						}
					}

				} else if (id.endsWith("c")) {
					id = id.substring(0, id.length() - 1);
					calls.put(Integer.parseInt(id), m);
				} else {
					if (!samples.containsKey(Integer.parseInt(id))) {
						List<Double> s = new ArrayList<>();
						double k = Double.parseDouble(m);
						s.add(k);
						samples.put(Integer.parseInt(id), s);
					} else {
						samples.get(Integer.parseInt(id)).add(Double.parseDouble(m));
					}
				}
				line = buffer.readLine();
			}

			// 117: 0.014622456860844223
			// 27f: 1

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		for (Integer id : frequencies.keySet()) {
			// System.out.println(id+":");
			Map<Integer, Double> temp = frequencies.get(id);

			int sum = 0;
			for (Integer ids : temp.keySet()) {
				sum += temp.get(ids);
			}
			for (Integer ids : temp.keySet()) {
				temp.put(ids, temp.get(ids) / sum);
			}

			// System.out.println(temp);
			// for(Double d: frequencies.get(id)){
			//// System.out.println(d);
			// }
		}

		// return null;
	}

	public static void main(String[] args) {
		SampleParser p = new SampleParser();
		try {
			p.parse(".\\outputs\\vpc_output" + 0 + ".data");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
