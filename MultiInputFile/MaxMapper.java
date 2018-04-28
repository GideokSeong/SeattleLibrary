import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * @param This map works for just passing key and value into the reduce to allow it to sort by max value of frequency 
 *
 */

public class MaxMapper extends Mapper<LongWritable, Text, Text, Text> {

@Override
public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

    String lines = value.toString();
    String[] val = lines.split("\t");
    
    // key is Title and value is month and frequency how many books are borrowed.
    
    context.write(new Text(val[0]), new Text(val[1] + "," + val[2]));
    
 }
}