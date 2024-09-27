package Hadoop.premierLeague;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
    * Reducer class to combine the values for the same key
 */
public class PremierLeagueCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

    /*
        * Reduce function to combine the values for the same key
        * @Param Text: key
        * @Param Iterable<IntWritable>: values
        * @Param Context: context
        * @throws IOException
        * @throws InterruptedException
        * @return void
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;

        // add all the values for the same key
        for (IntWritable value : values) {
            sum += value.get();
        }

        // write the key and the sum of values
        context.write(key, new IntWritable(sum));
    }
}
