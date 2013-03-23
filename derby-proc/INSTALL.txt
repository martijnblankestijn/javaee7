export DERBY_HOME=~/workspaces/javaee7/glassfish4/javadb
# Om te bouwen
mvn clean install

# Uploaden van de stored procedure in Java naar de Database
$DERBY_HOME/bin/ij ij_java_proc.sql

# Definieren van de stored procedure in de Database (koppeling Database - Java code)
$DERBY_HOME/bin/ij ij_create_proc.sql

