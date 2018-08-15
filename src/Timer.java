import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edn.*;
import util.SampleParser;

public class Timer {
	public static String EDN = "117;f(130,(132;if(27,_);150;150;150;150;150;f(67,f(68,150));f(74,(75;f(76,150)));82;f(83,150);f(89,(90;f(99,150)));f(100, (101;f(105,150))));138;139";

	public static void main(String[] args) {
//		test();
		//rds();
		vpc();
	}
	
	public static void vpc(){
		EDNCond node = new EDNCond(106, new EDNBlock(1061), new EDNBlock(-1));
		EDNFor node2 = new EDNFor(105, node);
		EDNSequence node3 = new EDNSequence(node2, new EDNBlock(107));
		node3 = new EDNSequence(new EDNBlock(102), node3);
		node = new EDNCond(368, new EDNBlock(3681), new EDNBlock(-1));
		EDNSequence node4 = new EDNSequence(node, new EDNBlock(369));
		node2 = new EDNFor(367, node4);
		node3 = new EDNSequence(node3, node2);
		node3 = new EDNSequence(new EDNBlock(366), node3);
		
		SampleParser parser = new SampleParser();
		try {
			parser.parse(".\\outputs\\vpc_output" + 0 + ".data");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<180;i++){
			
			System.out.println(node3.eval(parser.samples, parser.frequencies, null));
		}
	}
	
	public static void rds() {
		EDNBlock n1 = new EDNBlock(117);
		EDNBlock n2 = new EDNBlock(130);
		EDNBlock n3 = new EDNBlock(132);
		EDNBlock n4 = new EDNBlock(27);
		EDNBlock n5 = new EDNBlock(41);
		EDNBlock n6 = new EDNBlock(22);
		EDNBlock n7 = new EDNBlock(150);
		EDNBlock n8 = new EDNBlock(150);
		EDNBlock n9 = new EDNBlock(150);
		EDNBlock n10 = new EDNBlock(150);
		EDNBlock n11 = new EDNBlock(150);
		EDNBlock n12 = new EDNBlock(67);
		EDNBlock n13 = new EDNBlock(68);
		EDNBlock n14 = new EDNBlock(150);
		EDNBlock n15 = new EDNBlock(74);
		EDNBlock n16 = new EDNBlock(75);
		EDNBlock n17 = new EDNBlock(76);
		EDNBlock n18 = new EDNBlock(150);
		EDNBlock n19 = new EDNBlock(83);
		EDNBlock n20 = new EDNBlock(150);
		EDNBlock n21 = new EDNBlock(82);
		EDNBlock n22 = new EDNBlock(89);
		EDNBlock n23 = new EDNBlock(90);
		EDNBlock n24 = new EDNBlock(94);
		EDNBlock n25 = new EDNBlock(150);
		EDNBlock n26 = new EDNBlock(100);
		EDNBlock n27 = new EDNBlock(101);
		EDNBlock n28 = new EDNBlock(105);
		EDNBlock n29 = new EDNBlock(150);
		EDNBlock n30 = new EDNBlock(138);
		EDNBlock n31 = new EDNBlock(139);
		EDNBlock bottom = new EDNBlock(-1);
		
		EDNFor node = new EDNFor(105, n29);
		EDNSequence node2 = new EDNSequence(n27, node);
		node = new EDNFor(100, node2);
		EDNFor node3 = new EDNFor(94, n25);
		node2 = new EDNSequence(n23, node3);
		node3 = new EDNFor(89, node2);
		node2 = new EDNSequence(node3, node);
		node = new EDNFor(83, n20);
		node2 = new EDNSequence(node, node2);
		node2 = new EDNSequence(n21, node2);
		node = new EDNFor(76, n18);
		EDNSequence node4 = new EDNSequence(n16, node);
		node3 = new EDNFor(74, node4);
		node2 = new EDNSequence(node3, node2);
		node = new EDNFor(68, n14);
		node = new EDNFor(67, node);
		node2 = new EDNSequence(node, node2);
		node2 = new EDNSequence(n11, node2);
		node2 = new EDNSequence(n10, node2);
		node2 = new EDNSequence(n9, node2);
		node2 = new EDNSequence(n8, node2);
		node2 = new EDNSequence(n7, node2);
		node2 = new EDNSequence(n6, node2);
		EDNCond node5 = new EDNCond(27, n5, bottom);
		node2 = new EDNSequence(node5, node2);
		node2 = new EDNSequence(node3, node2);
		node = new EDNFor(130, node2);
		node2 = new EDNSequence(n30, n31);
		
		node2 = new EDNSequence(node, node2);
		node2 = new EDNSequence(n2, node2);
		
		SampleParser parser = new SampleParser();
		try {
			parser.parse(".\\outputs\\rds_output" + 0 + ".data");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<30;i++){
			System.out.println(node2.eval(parser.samples, parser.frequencies, null));
		}
	}
	
	public static void test() {
		EDNNode left = new EDNBlock(1);
		EDNNode loopBody = new EDNBlock(3);
		EDNNode loop = new EDNWhile(2, loopBody);
		EDNNode node = new EDNSequence(left, loop);
		
		Map<Integer, List<Double>> samples = new HashMap<>();
		Map<Integer, Map<Integer, Double>> frequencies = new HashMap<>();
		Map<Integer, Double> loopFs = new HashMap<>();
		loopFs.put(1, 0.5);
		loopFs.put(2, 0.5);
		List<Double> sampleTimes = new ArrayList<>();
		sampleTimes.add(0.87);
		samples.put(1,sampleTimes);
		samples.put(2,sampleTimes);
		samples.put(3,sampleTimes);
		frequencies.put(2, loopFs);
		
		double result = node.eval(samples, frequencies, null);
		
		System.out.println(result);//expecting 5.22
	}

	int get(int a, int b) {
		Random r = new Random();
		return r.nextInt((b - a) + 1) + a;
	}

	double time(double a, double b) {
		Random r = new Random();
		return a + (b - a) * r.nextDouble();
	}
}
