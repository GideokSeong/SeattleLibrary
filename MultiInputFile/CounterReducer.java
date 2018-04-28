import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

/*
 * @param Simple word counting reduce function. Here, key CheckoutDateTime and Title
 * @param Based on the keys, the values of keys matched are to be added.
 * 
 */

public class CounterReducer extends Reducer<Text, IntWritable, Text, IntWritable> 
 {
  
  public void reduce(Text key, Iterable<IntWritable> values, Context context ) 
  throws IOException, InterruptedException
  {
	  int count=0;
	  
	  for(IntWritable value: values){
		  count++;
	  }
	  
	  
   context.write(key, new IntWritable(count));
  }
 }