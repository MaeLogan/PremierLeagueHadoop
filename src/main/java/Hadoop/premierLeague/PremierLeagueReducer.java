package Hadoop.premierLeague;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
* This class is the Reducer class for the MapReduce job.
*/
public class PremierLeagueReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private final IntWritable totalGoals = new IntWritable();

    /*
     * This method reads the key-value pairs from the Mapper class and sums up the total number of goals scored by each team.
     * It then sets the total number of goals scored by each team in key-value pairs.
     * @Param key: The key of the input data
     * @Param values: The values of the input data
     * @Param context: The context object for the MapReduce job
     * @Throws IOException
     * @Throws InterruptedException
     * @Return void
     */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        // Add up all the goals for the team
        for (IntWritable value : values) {
            sum += value.get();
        }

        // Set the total goals for the team
        totalGoals.set(sum);
        context.write(key, totalGoals);
    }
}
