package util;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edn.EDNBlock;
import edn.EDNCall;
import edn.EDNCond;
import edn.EDNFor;
import edn.EDNNode;
import edn.EDNSequence;
import edn.EDNWhile;

public class EDNBuilder {
	Map<String, EDNNode> methodTable = new HashMap<>();
	SampleParser parser = new SampleParser();
	
	
	//evaluates a EDN method and returns the inferred value
	public double eval(String method){
		EDNNode node = methodTable.get(method);
		return node.eval(parser.samples, parser.frequencies, methodTable);
	}
	
	public void readOutput(String file){
		try {
			parser.parse(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Parses a EDN representation string and maps the method to the EDNNode generates
	EDNNode parse(Iterator<String> i) {
		String next = i.next();
		if (next.equals(";")) {
			return new EDNSequence(parse(i), parse(i));
		} else if (next.equals("i")) {
			next = i.next();
			return new EDNCond(Integer.parseInt(next), parse(i), parse(i));
		} else if (next.equals("f")) {
			next = i.next();
			return new EDNFor(Integer.parseInt(next), parse(i));
		} else if (next.equals("w")) {
			next = i.next();
			return new EDNWhile(Integer.parseInt(next), parse(i));
		} else if (next.equals("c")) {
			next = i.next();
			return new EDNCall(next);
		} else {
			return new EDNBlock(Integer.parseInt(next));
		}
	}

	//takes a string "<method_name> EDNNode" and adds method => EDNNode to methodTable
	public void build(String str) {
		String[] strs = str.split(" ");
		Iterator<String> iter = Arrays.asList(strs).iterator();
		String method = iter.next();
		methodTable.put(method, parse(iter));
	}
	
	public void print(String method){
		System.out.println(methodTable.get(method));
	}

	public static void test() {
		EDNBuilder b = new EDNBuilder();
		b.build("method ; i 1 f 4 5 3 2");

		System.out.println(b.methodTable.get("method"));
	}

	public static void main(String[] args) {
		test();
	}
}
