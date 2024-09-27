<h1>Description</h1>

This project uses Hadoop MapReduce to analyze English Premier League match data from 1995 to 2021. It processes multiple CSV files and extracts the total number of goals scored by each team when playing at home.
<h2>Prerequisites</h2> <h3>Required Software:</h3>

    Java Development Kit (JDK) version 8 or higher.
    Apache Hadoop (3.x) configured to run on a distributed cluster.
    Apache Maven to build the package.
    GIT (optional) to clone the repository if needed.

<h2>1. Running the program locally</h2> <h3>Clone the repository or download the project files:</h3>

```bash
git clone <REPO-URL>
cd <PROJECT-NAME>
```
<h3>Compile and build the package with Maven:</h3>


```bash
mvn clean package
```

This generates a JAR file in the target/ directory. The JAR file will have a name similar to premierleague-1.0-SNAPSHOT.jar.
<h3>Run the program:</h3>

```bash
java -jar target/premierleague-1.0-SNAPSHOT-with-dependencies.jar < input > < output >
```
    input: The local directory containing the CSV files to analyze (e.g., src/main/resources/archive).
    output: The local directory where the results will be generated (e.g., src/main/resources/output).

<h3>1.2. Viewing the results</h3>

After execution, open the output file generated in the output/ directory:

```bash
cat output/results.txt
```

Each line will contain the team name and the total number of goals scored at home.
<h2>2. Running the program with Hadoop MapReduce</h2>

Prerequisites:

- Complete the Docker tutorial (https://insatunisia.github.io/TP-BigData/tp1/)
- Have a working Hadoop cluster
- Complete part 1 of the Readme

<h3>Upload your data to the Docker master node</h3>


```bash
docker cp src/main/resources/archive/ hadoop-master:/root/<PROJECT-NAME>/archive
```

<h3>Put your data into HDFS</h3>

```bash
hdfs dfs -mkdir -p /user/root/<PROJECT-NAME>
hdfs dfs -put /root/<PROJECT-NAME>/archive /user/root/<PROJECT-NAME>
```

<h3>Run the program with Hadoop:</h3>

First, copy the JAR file into the Docker master node:

```bash
docker cp target/premierleague-1.0-SNAPSHOT-with-dependencies.jar hadoop-master:/root/<PROJECT-NAME>
```

Next, run the Hadoop job:

```bash
hadoop jar premierleague-1.0-SNAPSHOT-with-dependencies.jar <input> <output>
```
- input: The HDFS directory containing the CSV files to analyze (e.g., <PROJECT-NAME>/archive).
- output: The HDFS directory where the results will be generated (e.g., <PROJECT-NAME>/output).

<h3>2.2. Viewing the results</h3>

After execution, open the output file generated in the output/ directory:


```bash
hdfs dfs -cat /user/root/<PROJECT-NAME>/output/results.txt
```
Each line will contain the team name and the total number of goals scored at home.



