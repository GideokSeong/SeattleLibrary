import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/*
 * @param This reducer practically sorts title based on the month and the frequency of how many books are borrowed
 * 
 */
public class MaxReducer extends Reducer<Text,Text,Text,Text>{
	
public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
	
	int max = 0;
    String val = null;
    Iterator it = (Iterator) values.iterator();

    //values are month and frequency of how many books are borrowed. 
    for(Text txt : values){

        String st[] = txt.toString().split(",");
        int data = new Integer(st[1]);
        
        //This finds which month has the most frequency books are borrowed and add it to variable called max.
        if(data > max){
            max = data;
            val = st[0];
        }else if (data == max){
            val  = val +"," + st[0];
        }
    }
    Text output = new Text(val+"  "+max);
    
    context.write(key, output);
 }
}


