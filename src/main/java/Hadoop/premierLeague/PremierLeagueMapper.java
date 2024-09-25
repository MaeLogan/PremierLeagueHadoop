package Hadoop.premierLeague;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

public class PremierLeagueMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final Text homeTeam = new Text();
    private final IntWritable goals = new IntWritable();


    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        // Récupérer la ligne
        String line = value.toString();

        // Vérifier si la ligne est vide
        if (line.isEmpty()) {
            return; // Ignorer les lignes vides
        }


        // Séparer les colonnes du fichier CSV (en supposant que le séparateur est une virgule)
        String[] matchData = line.split(",");

        // Vérifier si c'est la première ligne du fichier CSV
        if (matchData[0].equals("Div")) {
            return;
        }


        // Récupérer l'équipe à domicile et les tirs à domicile (adapter les indices selon votre fichier CSV)
        String homeTeamName = matchData[2]; // Par exemple : 2e colonne = équipe à domicile
        int homeShots = Integer.parseInt(matchData[4]); // Par exemple : 9e colonne = tirs à domicile
        // Émettre le nom de l'équipe et le nombre de tirs comme paire clé-valeur
        homeTeam.set(homeTeamName);
        goals.set(homeShots);
        context.write(homeTeam, goals);
    }
}