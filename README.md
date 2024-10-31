# Premier League Shot Analysis with Hadoop and HBase

## Description

This project uses **Hadoop MapReduce** to analyze Premier League football match data (CSV files). The results include the total number of shots taken by each team when playing at home. The combined data is then inserted into **HBase** for storage and further analysis.

## Prerequisites

### Required Software

- **Java Development Kit (JDK)** version 8 or later.
- **Apache Hadoop** (version 3.x) configured to run in standalone mode or on a distributed cluster.
- **Apache HBase** (version 2.x) installed and running.
- **Apache Maven** to compile and build the project.
- **Docker** (optional) to run Hadoop and HBase in containers.

### Data Files

The input files must be in CSV format, containing the football match data.

## Installation and Setup

### 1. Clone the Repository

```bash
git clone <repo-url>
cd <project-directory>
```

### 2. Build the Project

```bash
mvn clean package
```


### 2.2 Optional: Run Hadoop and HBase in Docker

   Start the Hadoop and HBase containers in your docker

```bash
  docker exec -it hadoop-master bash
  start-hbase.sh
```

copy the input files to the container

```bash
  docker cp /path/to/local/files/*.csv hadoop-master:/input/
```

copy the jar file to the container

```bash
  docker cp /path/to/local/files/*.jar hadoop-master:/input/
```

copy the lib folder to the container next to the jar file

```bash
  docker cp /path/to/local/files/lib hadoop-master:/input/lib
```

```bash
  docker cp /path/to/local/files/lib hadoop-master:/input/
```

### 3. Prepare the Data

   Place the CSV files in the local filesystem or HDFS.
   If using HDFS, load the files into HDFS:

```bash
  hdfs dfs -mkdir -p /user/root/input/archive
  hdfs dfs -put /path/to/local/files/*.csv /user/root/input/archive/
```

### 4. Run the MapReduce Hbase Job

To run the MapReduce program, use the following command:

```bash
  java -cp "PremierLeagueHadoop-1.0-SNAPSHOT.jar:lib/*" Hadoop.premierLeague.PremierLeague hdfs://hadoop-master:9000/user/root/input/archive hdfs://hadoop-master:9000/user/root/output
```
### 5. Check the Results in HBase

Once the job has finished, you can check the data inserted into HBase.

#### Start the HBase shell:

```bash
  hbase shell
```

#### Scan the PremierLeagueStats table to verify the data:

```bash
  scan 'PremierLeagueStats'
```

You should see the results with row keys corresponding to teams and match dates, along with the number of shots recorded for each team.


