package Hadoop.premierLeague;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


/*
 * This class is the Mapper class for the MapReduce job.
 */
public class PremierLeagueMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final Text homeTeam = new Text();
    private final IntWritable goals = new IntWritable();


    /*
     * This method reads the input line by line and extracts the home team name and the number of goals scored.
     * It then sets the home team name and the number of goals scored in key-value pairs.
     * @Param key: The key of the input data
     * @Param value: The value of the input data
     * @Param context: The context object for the MapReduce job
     * @Throws IOException
     * @Throws InterruptedException
     * @Return void
     */
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        // Get the line
        String line = value.toString();

        // Check if the line is empty
        if (line.isEmpty()) {
            return; // if empty, skip this line
        }


        // split the line into an array of strings
        String[] matchData = line.split(",");

        // Check if the line is a header
        if (matchData[0].equals("Div")) {
            return;
        }


        // Get the home team name and the number of goals scored
        String homeTeamName = matchData[2];
        int homeShots = Integer.parseInt(matchData[4]);
        // Set the home team name and the number of goals scored in key-value
        homeTeam.set(homeTeamName);

        goals.set(homeShots);

        context.write(homeTeam, goals);
    }
}