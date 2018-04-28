import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/*
 * 
 *@param This is first MapReduce job works for joining two different datasets 
 *@param such as library yearly record and item inventory. And we get dataset from Kaggle.
 *@return Kaggle URL : https://www.kaggle.com/seattle-public-library/seattle-library-checkout-records
 *
 */

public class MultiInputFile extends Configured implements Tool 
{
	
 public int run(String[] args) throws Exception {
 Configuration conf = new Configuration();
 Job job = new Job(conf, "aggprog");
 
 //When we run in command interface, we need to put this class name(MultiInputFile.class) we declared.
 job.setJarByClass(MultiInputFile.class);
 
 //We are using two input files(Map functions) so that we join two datasets. 
 MultipleInputs.addInputPath(job,new Path(args[0]),TextInputFormat.class,CounterMapper.class);
 MultipleInputs.addInputPath(job,new Path(args[1]),TextInputFormat.class,CounterTwoMapper.class);

 //We need to specify the location of output file
 FileOutputFormat.setOutputPath(job, new Path(args[2]));
 
 //specified the name of reduce class.
 job.setReducerClass(CounterReducer.class);
 job.setNumReduceTasks(1);
 
 //For output, will get text format both key and value.
 job.setOutputKeyClass(Text.class);
 job.setOutputValueClass(Text.class);
 
 return (job.waitForCompletion(true) ? 0 : 1);

 }

 public static void main(String[] args) throws Exception {

  
  int ecode = ToolRunner.run(new MultiInputFile(), args);
  System.exit(ecode);

  
 }

}