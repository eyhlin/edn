import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

public class Tracer {
	static int ENTRY = 6;

	public static void main(String[] args) {
		Map<Integer, String> nodes = new HashMap<>();
		Map<Integer, Set<Integer>> edges = new HashMap<>();
		// Set<String> edges = new HashSet<>();
		
		Set<Integer> leaders = new HashSet<>();
		Set<Integer> exits = new HashSet<>();
		try {
			File f = new File("test.extracted");

			FileReader fileReader = new FileReader(f);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuffer stringBuffer = new StringBuffer();
			String line;

			int previous = -1;
			Set<Integer> visited = new HashSet<>();
			while ((line = bufferedReader.readLine()) != null) {
				int start = line.indexOf('(');
				int end = line.indexOf(')');
				int id = Integer.parseInt(line.substring(start + 1, end));
				String str = line.substring(end + 2);
				nodes.put(id, str);
				if (previous == -1) {

				} else {
					// String edge = previous+" -> "+id+";";
					// if(visited.contains(id)&&!edges.contains(edge)){
					// exits.add(id);
					// }
					if (edges.containsKey(previous)) {
						if (edges.get(previous).add(id)) {
							if (!visited.add(id))
								leaders.add(id); // more than two ins
							exits.add(previous); // more than two outs
						}
					} else {
						Set<Integer> ch = new HashSet<>();
						ch.add(id);
						edges.put(previous, ch);
						visited.add(id);
					}

					// edges.add(edge);
					// //////////function returns as ends
				}
				visited.add(id);
				previous = id;
			}
			fileReader.close();

			for (Integer node : nodes.keySet()) {
				System.out.print(node + " ");
				System.out.println(nodes.get(node));
			}

			for (Integer e : edges.keySet()) {
				for (Integer i : edges.get(e))
					System.out.println(e + " -> " + i);
			}

			// from ASTs
			//for test.extracted
			leaders.add(9);
			exits.add(15);
			
			leaders.add(18);
			exits.add(19);
			
			leaders.add(21);
			exits.add(21);
			
//			for extracted.data
//			leaders.add(27);
//			exits.add(108);
//
//			leaders.add(117);
//			exits.add(139);
//			edges.put(139, new HashSet<Integer>());// /#exit
//
//			leaders.add(147);
//			exits.add(147);
//
//			leaders.add(150);
//			exits.add(158);
//
//			leaders.add(161);
//			exits.add(171);

			for (int node : exits) {
				System.out.println(node + " [color=red]");
				
				if(edges.get(node)!=null)//null if void method TODO
				for (int next : edges.get(node)) {
					leaders.add(next);
				}
			}
			for (int leader : leaders)
				System.out
						.println(leader + " [style=filled, fillcolor=yellow]");
		} catch (IOException e) {

		}

		System.out.println("\n------Basic block inference------");

		// edges, leaders, exits, START

		Stack<Integer> stack = new Stack<>();
//		stack.push(117);///start
		stack.push(18);///start
		List<Integer> block = new ArrayList<>();
		List<List<Integer>> blocks = new ArrayList<>();
		Set<Integer> visited = new HashSet<>();
		Map<Integer, Integer> headIndex = new HashMap<>();
		while (!stack.empty()) {
			int current = stack.pop();
//			System.out.println(current);
			if (current == 0)
				continue;
			if(visited.contains(current)){
				if(block.size()>0){
					blocks.add(block);
					block=new ArrayList<>();
				}
				continue;
			}
			visited.add(current);
			if (leaders.contains(current)){
				if(block.size()>0){
					blocks.add(block);
					block = new ArrayList<>();
				}else{
					block = new ArrayList<>();
				}
				headIndex.put(current, blocks.size());
			}
			block.add(current);
			if (exits.contains(current)){
				blocks.add(block);
				block = new ArrayList<>();
			}
			
			if(edges.get(current)!=null)//TODO if void
			for (int children : edges.get(current))
				stack.push(children);
		}
		
		
//		blocks.remove(0);//get rid of the empty list
		Map<Integer, Set<Integer>> bEdges = new HashMap<>();
		int c=0;
		for(List<Integer> b : blocks){
			Set<Integer> e = new HashSet<>();
			if(edges.get(b.get(b.size()-1))!=null)//TODO if void
			e.addAll(edges.get(b.get(b.size()-1)));
			bEdges.put(c++,e);
		}
		System.out.println("digraph bbgraph{\n"
				+ "{");
		for(int i=0;i<blocks.size();i++){
			System.out.println("\t"+i+" [label=\""+blocks.get(i)+"\"]");
		}
		System.out.println("}");
		for(int i=0;i<blocks.size();i++){
			List<Integer> b =blocks.get(i);
			Set<Integer> e = new HashSet<>();
			int head = b.get(b.size()-1);
			if(edges.get(head)!=null)//TODO if void
			for(int tail: edges.get(head)){
				System.out.println(i+" -> "+headIndex.get(tail)+";");
			}
			bEdges.put(c++,e);
		}
		System.out.println("}");
		
		Gson gson = new Gson();
		
		Trace[] traces = new Trace[4500];
		Tracer tracer = new Tracer();
		for(int i=0;i<4500;i++){
			Trace t = tracer.new Trace();
			t.times = new Time[blocks.size()];
			t.trace=i;
			for(int j=0;j<blocks.size();j++){
				Time time = tracer.new Time();
				time.id=j;
				time.trace=i;
				Random r = new Random();
				time.sec=r.nextDouble()*0.00000000023;
				t.times[j] = time;
			}
			traces[i]=t;
		}
		try {
			gson.toJson(traces, new FileWriter("measure.json"));
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	class Trace{
		int trace;
		Time[] times; 
	}
	class Time{
		int id;
		int trace;
		double sec;
	}
	
}
