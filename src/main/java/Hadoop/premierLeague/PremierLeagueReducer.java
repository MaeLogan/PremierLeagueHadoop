package Hadoop.premierLeague;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.conf.Configuration;


import java.io.IOException;

/*
    * Reducer class to combine the values for the same key
 */
public class PremierLeagueReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private Connection connection;
    private Table table;

    /*
        * Setup function to create a connection to HBase and create the table if it does not exist
        * @Param Context: context
        * @throws IOException
        * @throws InterruptedException
        * @return void
     */
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        if (connection == null || connection.isClosed()) {
            // Configuration Hbase
            Configuration config = HBaseConfiguration.create();
            System.out.println("Connecting to HBase");

            try {
                // Init HBase connection
                connection = ConnectionFactory.createConnection(config);
                Admin admin = connection.getAdmin();

                TableName tableName = TableName.valueOf("PremierLeagueStats");

                if (!admin.tableExists(tableName)) {
                    System.out.println("Table 'PremierLeagueStats' does not exist. Creating table...");

                    TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(tableName)
                            .setColumnFamily(ColumnFamilyDescriptorBuilder.newBuilder(Bytes.toBytes("teamStats")).build())
                            .build();

                    admin.createTable(tableDescriptor);
                    System.out.println("Table 'PremierLeagueStats' created successfully.");
                } else {
                    System.out.println("Table 'PremierLeagueStats' already exists.");
                }

                table = connection.getTable(tableName);
                admin.close();

            } catch (IOException e) {
                throw new IOException("Erreur lors de la connexion à HBase", e);
            }
        } else {
            System.out.println("HBase connection already established.");
        }
    }



/*
    * Reduce function to combine the values for the same key
    * @Param Text: key
    * @Param Iterable<IntWritable>: values
    * @Param Context: context
    * @throws IOException
    * @return void
 */
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException {
        try {
            Integer sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            Put put = new Put(Bytes.toBytes(key.toString())); // Clé de ligne = nom de l'équipe
            put.addColumn(Bytes.toBytes("teamStats"), Bytes.toBytes("homeShots"), Bytes.toBytes(sum.toString()));
            table.put(put);
        }
        catch (IOException e) {
            throw new IOException("Erreur lors de l'écriture dans HBase", e);
        }
    }

    /*
        * Cleanup function to close the connection to HBase
        * @throws IOException
        * @return void
     */
    @Override
    protected void cleanup(Context context) throws IOException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}
