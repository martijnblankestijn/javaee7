connect 'jdbc:derby://localhost:1527/sun-appserv-samples';
create SCHEMA JAVAEE7;
CALL SQLJ.install_jar('/home/martijn/workspaces/javaee7/projects/derby-proc/target/derby-proc-1.0-SNAPSHOT.jar', 'JAVAEE7.procsJar', 0);
CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.database.classpath', 'JAVAEE7.procsJar');