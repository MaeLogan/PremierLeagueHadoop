package Hadoop.premierLeague;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
/*
    *Main class to run the MapReduce job
    *This class configures the job and sets the input and output paths

 */
public class PremierLeague {

    /*
     * Main function to create and configure a new Job
     * @Param args: input path and output path
     * @throws Exception
     * @return void
     * Author: Mael Dabard
     * Date: 2024-09-27
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: PremierLeagueDriver <input path> <output path>");
            System.exit(-1);
        }

        // Create and configure a new Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Premier League Shot Count");

        job.setJarByClass(PremierLeague.class);
        job.setMapperClass(PremierLeagueMapper.class);
        job.setCombinerClass(PremierLeagueCombiner.class);
        job.setReducerClass(PremierLeagueReducer.class);
        // Define the output key and value types
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // Define Path of input and output
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Execute Job
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
