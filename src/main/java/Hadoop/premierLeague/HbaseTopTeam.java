package Hadoop.premierLeague;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HbaseTopTeam {

    public static Map<String, String> getTopTeams() {
        Map<String, String> teamData = new HashMap<>();
        Configuration config = HBaseConfiguration.create();
        try (Connection connection = ConnectionFactory.createConnection(config);
             Table table = connection.getTable(TableName.valueOf("PremierLeagueStats"))) {

            Scan scan = new Scan();
            scan.setLimit(10); // Limiter à 10 résultats
            scan.addFamily(Bytes.toBytes("teamStats"));

            try (ResultScanner scanner = table.getScanner(scan)) {
                for (Result result : scanner) {
                    String team = Bytes.toString(result.getRow());
                    String homeShots = Bytes.toString(result.getValue(Bytes.toBytes("teamStats"), Bytes.toBytes("homeShots")));
                    teamData.put(team, homeShots);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teamData;
    }

    public static void main(String[] args) {
        Map<String, String> topTeams = getTopTeams();
        topTeams.forEach((team, homeShots) ->
                System.out.println("Team: " + team + ", Home Shots: " + homeShots));
    }
}
