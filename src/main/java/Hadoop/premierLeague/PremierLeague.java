package Hadoop.premierLeague;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/*
    * This class is the driver class for the MapReduce job.
 */
public class PremierLeague {

    /*
     * This method creates and configures a new Job, sets the Mapper and Reducer classes, and defines the output key and value types.
     * It then defines the input and output paths and executes the Job.
     * @Param args: The input and output paths for the MapReduce job
     * @Throws Exception
     * @Return void
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: PremierLeagueDriver <input path> <output path>");
            System.exit(-1);
        }
        System.out.println("Hello world!");

        // Create and configure a new Job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Premier League Shot Count");

        job.setJarByClass(PremierLeague.class);
        job.setMapperClass(PremierLeagueMapper.class);
        job.setCombinerClass(PremierLeagueReducer.class);
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
