package Hadoop.premierLeague;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class PremierLeagueChart {
    public static void generateChart() {

        Map<String, String> teamData = HbaseTopTeam.getTopTeams();

        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, String> entry : teamData.entrySet()) {
            dataset.addValue(Double.parseDouble(entry.getValue()), entry.getKey(), "Home Shots");
        }

        // Créer un graphique en barres
        JFreeChart barChart = ChartFactory.createBarChart(
                "Goals Scored by Team",  // Titre du graphique
                "Team",                  // Label de l'axe X
                "Goals",                 // Label de l'axe Y
                dataset);

        // Sauvegarder le graphique sous forme d'image PNG
        File outputFile = new File("goals_chart.png");
        try {
            ChartUtils.saveChartAsPNG(outputFile, barChart, 800, 600);
            System.out.println("PNG file created: " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
