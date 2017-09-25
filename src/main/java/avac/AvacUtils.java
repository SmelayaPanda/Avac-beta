package avac;

import java.util.Set;

class AvacUtils {

    static String toInClause( Set<String> wordSet ) {
        StringBuilder rString = new StringBuilder( "''" );

        String sep = ", ";
        String quote = "'";
        for( String each : wordSet ) {
            each = each.replace( "'", "''" );
            rString.append( sep ).append( quote ).append( each ).append( quote );
        }
        return rString.toString();
    }

    static String getFilePath( String... args ) {
        StringBuilder filePath = new StringBuilder( AvacConst.USER_DIR );
        for( String a : args ) {
            filePath.append( AvacConst.LS ).append( a );
        }
        return filePath.toString();
    }
}
