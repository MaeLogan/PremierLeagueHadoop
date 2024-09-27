package Hadoop.premierLeague;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;


/*
    * Mapper class to read the input and split the lines into key-value pairs
 */
public class PremierLeagueMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final Text homeTeam = new Text();
    private final IntWritable goals = new IntWritable();


    /*
     * Map function to read the input and split the lines into key-value pairs
     * @Param Object: key
     * @Param Text: value
     * @Param Context: context
     * @throws IOException
     * @throws InterruptedException
     * @return void
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