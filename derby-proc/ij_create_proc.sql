connect 'jdbc:derby://localhost:1527/sun-appserv-samples';
CREATE PROCEDURE APP.PLUS(IN eerste INTEGER,
                          IN tweede INTEGER,
                          OUT resultaat INTEGER)
DYNAMIC RESULT SETS 0
PARAMETER STYLE JAVA
EXTERNAL NAME 'nl.ordina.derby.ProcedureHolder.add'
LANGUAGE JAVA NO SQL;
