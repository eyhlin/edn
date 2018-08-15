import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import util.EDNBuilder;

public class VPC {
	
	public static void main(String args[]){
		String LAMBDA_HANDLER = 
				"lambda_handler ; 321 ; c compute_node_ip ; f 336 ; i 337 340 -1 ; 342 c process_message ; i 345 ; 346 c Stats.increment -1 c Stats.flush";
		String COMPUTE_NODE_IP = 
				"compute_node_ip ; 58 ; f 59 i 61 62 -1 ; i 65 i 66 -1 -1 -1 68";
		String PROCESS_MESSAGE = 
				"process_message ; i 35 43 -1 ; i 44 45 -1 ; c process_log_status ; i 48 -1 -1 ; c process_action ; c process_duration ; c process_packets c process_bytes";
		String PROTOCOL_ID_TO_NAME =
				"protocol_id_to_name ; i 72 -1 -1 74";
		String PROCESS_LOG_STATUS = 
				"process_log_status c Stats.increment";
		String PROCESS_ACTION = 
				"process_action c Stats.increment";
		String PROCESS_DURATION = 
				"process_duration c Stats.histogram";
		String PROCESS_PACKETS = 
				"process_packets ; c Stats.histogram c Stats.increment";
		String PROCESS_BYTES = 
				"process_bytes ; c Stats.histogram c Stats.increment";
		String STATS_INITIALIZE = 
				"Stats._initialize 243";
		String STATS_INIT = "Stats._init 247";
		String STATS_INCREMENT = "Stats.increment 251";
		String STATS_HISTOGRAM = "Stats.histogram 257";
		String STATS_FLUSH = 
				"Stats.flush ; 263 ; f 265 f 266 267 ; f 277 f 278 ; 279 ; f 280 ; 281 f 283 284 f 286 ; i 288 289 -1 ; i 288 289 -1 ; i 290 291 -1 ; i 292 293 -1 294 ; c Stats._initialize 305";
		
		EDNBuilder builder = new EDNBuilder();
		builder.build(LAMBDA_HANDLER);
		builder.build(COMPUTE_NODE_IP);
		builder.build(PROCESS_MESSAGE);
		builder.build(PROTOCOL_ID_TO_NAME);
		builder.build(PROCESS_LOG_STATUS);
		builder.build(PROCESS_ACTION);
		builder.build(PROCESS_DURATION);
		builder.build(PROCESS_PACKETS);
		builder.build(PROCESS_BYTES);
		builder.build(STATS_INITIALIZE);
		builder.build(STATS_INIT);
		builder.build(STATS_INCREMENT);
		builder.build(STATS_HISTOGRAM);
		builder.build(STATS_FLUSH);
		
		/**
		builder.print("lambda_handler");
		builder.print("compute_node_ip");
		builder.print("process_message");
		builder.print("protocol_id_to_name");
		builder.print("process_log_status");
		builder.print("process_action");
		builder.print("process_duration");
		builder.print("process_packets");
		builder.print("process_bytes");
		builder.print("Stats._initialize");
		builder.print("Stats._init");
		builder.print("Stats.increment");
		builder.print("Stats.histogram");
		builder.print("Stats.flush");**/
		
		builder.readOutput(".\\outputs\\vpc_output.data");
		for(int i=0;i<1000;i++){
			System.out.println(builder.eval("lambda_handler"));
		}
		List<Double> results = new ArrayList<>();
		for(int i=0;i<300;i++){
			results.add(builder.eval("lambda_handler"));
		}
		results.sort(
				new Comparator<Double>() {
					@Override
					public int compare(Double o1, Double o2) {
						return Double.compare(o1, o2);
					}
				}
				);
		double min = results.get(0);
		double max = results.get(results.size()-1);
		
		int BINS = 20;
		double binSize = (max-min)/BINS;
		
		int[] hist = new int[BINS+1];
		
		for(Double d : results){
			int bin = (int) ((d-min)/binSize);
			//System.out.println(d+" "+min+" "+binSize+" "+bin+" "+ max);
			hist[bin] ++;
		}
		//for(int i=0;i<BINS;i++){
		//	System.out.println(hist[i]);
		//}
	}

}
