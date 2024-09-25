package Hadoop.premierLeague;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PremierLeague {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: PremierLeagueDriver <input path> <output path>");
            System.exit(-1);
        }
        System.out.println("Hello world!");

        // Configuration du job
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Premier League Shot Count");

        job.setJarByClass(PremierLeague.class);
        job.setMapperClass(PremierLeagueMapper.class);
        job.setCombinerClass(PremierLeagueReducer.class);
        job.setReducerClass(PremierLeagueReducer.class);
        // Définir le type de sortie du Mapper et du Reducer
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);


        // Chemins d'entrée et de sortie
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        // Exécuter le job
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
