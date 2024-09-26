**<h1>Description</h1>**

Ce projet utilise Hadoop MapReduce pour analyser des données de matchs de la Premier League anglaise de 1995 à 2021. Il traite plusieurs fichiers CSV et extrait le nombre total de but réalisés par chaque équipe lorsqu'elle joue à domicile.

<h2>Prérequis</h2>
**<h3>Logiciels requis :</h3>**

- Java Development Kit (JDK) version 8 ou plus récente.
- Apache Hadoop (3.x) configuré pour être exécuté sur un cluster distribué.
- Apache Maven pour créer le package.
- GIT (optionnel) pour cloner le dépôt si nécessaire.

<h2>1. Exécuter le programme en local</h2>

<h3>Clonez le dépôt ou téléchargez les fichiers du projet :</h3>


```
git clone <URL-DU-DÉPÔT>
cd <NOM_DU_PROJET>
```

<h3>Compilez et créez le package avec Maven :</h3>

```
mvn clean package
```

Cela génère un fichier JAR dans le répertoire target/. Le fichier JAR aura un nom similaire à premierleague-1.0-SNAPSHOT.jar.

<h3>Exécutez le programme:</h3>

```
java -jar target/premierleague-1.0-SNAPSHOT-with-dependencies.jar <input> <output>
```

- ***input*** : Le répertoire local contenant les fichiers CSV à analyser. (ex: src/main/resources/archive)
- ***output*** : Le répertoire local où les résultats seront générés. (ex: src/main/resources/output)

<h3>1.2. Visualisation des résultats</h3>

Après l'exécution, ouvrez le fichier de sortie généré dans le répertoire output/ :

```
cat output/results.txt
```
Chaque ligne contiendra le nom de l'équipe et le nombre total de but réalisés a domicile.


<h2>2. Exécuter le programme avec Hadoop MapReduce</h2>

**prérequis :**

- Avoir réaliser le TP docker (https://insatunisia.github.io/TP-BigData/tp1/)
- Avoir un cluster Hadoop fonctionnel
- Avoir réaliser la partie 1 du Readme

<h3> Envoyer vos données dans votre docker master</h3>

```
docker cp src/main/resources/archive/ hadoop-master:/root/<NOM_DU_PROJET>/archive
```
<h3>mettre nos données dans hdfs</h3>

```
hdfs dfs -mkdir -p /user/root/<NOM_DU_PROJET>
hdfs dfs -put /root/<NOM_DU_PROJET>/archive /user/root/<NOM_DU_PROJET>
```

<h3> Exécutez le programme avec Hadoop :</h3>

dans un permier temps on va copier le fichier jar dans le docker master
```
docker cp target/premierleague-1.0-SNAPSHOT-with-dependencies.jar hadoop-master:/root/<NOM_DU_PROJET>
```

Ensuite on va exécuter le programme hadoop

```
hadoop jar premierleague-1.0-SNAPSHOT-with-dependencies.jar <input> <output>
```

- ***input*** : Le répertoire HDFS contenant les fichiers CSV à analyser. (ex: <NOM_DU_PROJET>/archive)
- ***output*** : Le répertoire HDFS où les résultats seront générés. (ex: <NOM_DU_PROJET>/output)

<h3>2.2. Visualisation des résultats</h3>

Après l'exécution, ouvrez le fichier de sortie généré dans le répertoire output/ :

```
hdfs dfs -cat /user/root/<NOM_DU_PROJET>/output/results.txt
```

Chaque ligne contiendra le nom de l'équipe et le nombre total de but réalisés a domicile.




