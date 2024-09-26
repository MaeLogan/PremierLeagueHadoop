package Hadoop.premierLeague;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class PremierLeagueReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private final IntWritable totalGoals = new IntWritable();

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
