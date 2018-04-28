import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * @param This is second map function consisting item inventory that includes BibNum, Title, Author,
 * @param ISBN, PublicationYear, Publisher, Subjects, ItemType, ItemCollection, ReportDate, ItemCount.
 * 
 */
public class CounterTwoMapper extends Mapper<LongWritable, Text, Text, Text>
{

	private Text outkey = new Text();

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException
	{
		// Same as first Mapper, this skips first row(such as the columns)
		if(value.toString().startsWith("BibNum"))
		{
			return;
		}
		
		// Splitted each column by comma 
		String data[] = value.toString().split(",");

		
		String BibNum = data[0];
		String title = data[1];
		
		// Data in each column contains " ", so only in case data includes it, write key and value to the reduce function.
		String tes="\"\"";
		if(title.equals(tes)==false)
		{
			context.write(new Text(BibNum), new Text(title));	
		}

	}
}