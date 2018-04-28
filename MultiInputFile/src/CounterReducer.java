import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

/*
 *   @param This Reduce function gets data from two different Map functions such as CounterMapper and CounterTwoMapper
 *
 */

public class CounterReducer extends Reducer<Text, Text, Text, Text> 
{
	public void reduce(Text key, Iterable<Text> values, Context context ) 
			throws IOException, InterruptedException
	{
		
		int count=0;
		
		String tmp="";
		
		// This for loop summates values getting from first map function and second map function.
		// So, it plays a role as join function.
		for(Text value:values){
			tmp+="\t"+value;
			count+=1;
		}

		// Only in case value is over with " symbol, then write key and value for the last output in first MapReduce job.
		// Here, key is BibNum and value is CheckoutDateTime and Title.
		String k="\"";
		if(tmp.contains(k)==true){

			context.write(key, new Text(tmp));
		}
	}
}
