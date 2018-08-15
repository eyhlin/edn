import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import util.EDNBuilder;

public class Logzio {
	public static void main(String args[]){
		String LAMBDA_HANDLER = 
				"lambda_handler ; 65 ; c _extract_aws_logs_data ; c _enrich_logs_data ; c Shipper.__init__ f 77 ; c _parse_cloudwatch_Log c Shipper.add";
		String _ENRICH_LOGS_DATA = 
				"_enrich_logs_data 54";
		String _PARSE_CLOUDWATCH_LOG = 
				"_parse_cloudwatch_Log 29";
		String _EXTRACT_AWS_LOGS_DATA =
				"_extract_aws_logs_data 17";
		String SHIPPER__INIT__ = 
				"Shipper.__init__ 120";
		String SHIPPER_ADD = 
				"Shipper.add 131";
		
		EDNBuilder builder = new EDNBuilder();
		builder.build(LAMBDA_HANDLER);
		builder.build(_ENRICH_LOGS_DATA);
		builder.build(_PARSE_CLOUDWATCH_LOG);
		builder.build(_EXTRACT_AWS_LOGS_DATA);
		builder.build(SHIPPER__INIT__);
		builder.build(SHIPPER_ADD);
		
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
		
		builder.readOutput(".\\outputs\\logzio_output.data");
		for(int i=0;i<1000000;i++){
			System.out.println(builder.eval("lambda_handler"));
		}
	}

}
