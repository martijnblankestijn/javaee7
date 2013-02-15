package nl.ordina.derby;

/**
 * Create Procedure: http://db.apache.org/derby/docs//10.9/ref/rrefcreateprocedurestatement.html
 * Call Procedure http://db.apache.org/derby/docs//10.9/ref/rrefcallprocedure.html
 */
public class ProcedureHolder {
    public static void add(int first, int second, int[] result) {
        result[0] = first + second;

    }
}
