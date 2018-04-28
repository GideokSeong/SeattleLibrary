import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/*
 * @param This map function acts as sorting key and value getting from first job.
 * 
 */
public class CounterMapper extends Mapper<LongWritable, Text, Text, IntWritable>
{
	private Text outkey = new Text();

	public void map(LongWritable key, Text values, Context context)
			throws IOException, InterruptedException
	{

		try {

			String date ="";
			String title="";
			
			// Splitted data by tap delimiter
			String []tmo = values.toString().split("\t");

			boolean condition=false;
			
			// Each row contains ' " ', so only in case it is included in each row, added into title.
			// 
			for (int ii=1;ii<tmo.length;ii++){

				if(tmo[ii].contains("\"")==true)
				{
					title = tmo[ii];
					condition=true;
					tmo[ii]=null;	
				}

			}
			
			// In case condtion is true, date is gotten from tmo[] array
			for(int ii=1;ii<tmo.length;ii++){

				if(condition==true)							
				{
					if(tmo[ii]!=null){

						date=tmo[ii];
						if(!date.isEmpty()){
							
							// Outkey works for putting two keys together.
							outkey.set(title + "\t" + date);

							context.write(outkey, new IntWritable(1));
						}
					}
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
