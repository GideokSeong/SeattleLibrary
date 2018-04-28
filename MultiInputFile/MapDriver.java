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
 * @param This MapReduce job is second job which allows first job that is unstructured order to be ordered.
 * 
 */
public class MapDriver {

	public static void main(String[] args) throws Exception {
	
		Configuration conf = new Configuration();
		
		
		Job job = Job.getInstance();
	    // set up job name we need when we run jar file
		job.setJobName("MapDriver");
	    job.setJarByClass(MapDriver.class);
	    
	    // data type of key and value
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(IntWritable.class);

	    // set up class name of map and reduce function.
	    job.setMapperClass(CounterMapper.class);
	    job.setReducerClass(CounterReducer.class);

	    // input and output format class.
	    job.setInputFormatClass(TextInputFormat.class);
	    job.setOutputFormatClass(TextOutputFormat.class);

	    // set up location of input and output
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));

	    job.waitForCompletion(true);

	    
	}
}
