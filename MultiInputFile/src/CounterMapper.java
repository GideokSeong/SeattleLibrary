import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * 
 * @param This map function gets only one CSV file(yearly record) and put together with other map function to join each dataset.
 * @param First CSV file includes BibNumber, ItemBarcode, ItemType, ItemCollection, CallNumber and CheckoutDateTime.
 * 
 */
public class CounterMapper extends Mapper<LongWritable, Text, Text, Text>
{
	private Text outkey = new Text();

	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException
	{
		try {

			// this does for ignoring first row such that it includes several columns information
			if(value.toString().startsWith("BibNumber"))  
			{
				return;
			}

			// Getting data from dataset file as a commma unit
			String data[] = value.toString().split(",");

			// BibNum is book number and DateTime is check-out date time.
			String BibNum = data[0];
			String DateTime = data[5];

			// This play a role as a simple date format so that we would like to sort date by this format.
			SimpleDateFormat frmt = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");

			Date creationDate = frmt.parse(DateTime);

			// This convert simple date format to only Month format.
			frmt.applyPattern("MM");
			String dateTime = frmt.format(creationDate);

			//Pass key and value as a format of text into reduce function.
			context.write(new Text(BibNum),new Text(dateTime));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
