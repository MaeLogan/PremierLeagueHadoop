package Hadoop.premierLeague;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.awt.*;
import java.io.IOException;

public class PremierLeagueReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private final IntWritable totalShots = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        // Additionner tous les tirs pour l'équipe à domicile donnée
        for (IntWritable value : values) {
            sum += value.get();
        }

        // Émettre le total des tirs pour cette équipe
        totalShots.set(sum);
        context.write(key, totalShots);
    }
}
