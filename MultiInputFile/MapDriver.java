import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/*
 * @param This job does finding max value for combination of key and value of second job. 
 * 
 */
public class MapDriver {
	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

		Configuration conf = new Configuration();
	    conf.set("mapreduce.job.queuename", "default");
	    Job job = new Job(conf,"MaxValue");
	    
	    // Driver name when running in command
	    job.setJarByClass(MapDriver.class);
	    
	    // Name of Mapper and Reducer class
	    job.setMapperClass(MaxMapper.class);
	    job.setReducerClass(MaxReducer.class);
	    
	    // set up output format for key and value.
	    job.setMapOutputKeyClass(Text.class);
	    job.setMapOutputValueClass(Text.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);

	    // set up location of input and output
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    
	    job.waitForCompletion(true);
	}
}
