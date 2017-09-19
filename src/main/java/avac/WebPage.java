package avac;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class WebPage
{

    static Set<String> getPageWords( String url )
    {
        Set<String> wordList = new HashSet<>();
        String[] line;
        Document doc = null;
        try
        {
            doc = Jsoup.connect( url ).get();
        }
        catch( IOException e )
        {
            e.printStackTrace();
        }
        Elements paragraphs = null;
        if( doc != null )
        {
            paragraphs = doc.select( "p" );
        }
        if( paragraphs != null )
        {
            for( Element p : paragraphs )
            {
                line = p
                        .text()
                        .replaceAll( "[^\\p{L}\\p{Nd}]+", " " )
                        .toLowerCase()
                        .split( " " );
                Collections.addAll( wordList, line );
            }
        }
        return wordList;
    }

    static Map<String, Integer> rangePageWords( String... urls )
    {
        Map<String, Integer> map = new HashMap<>();
        String[] line;
        Document doc = null;


        for( String url : urls )
        {
            try
            {
                doc = Jsoup.connect( url ).get();
            }
            catch( Exception ignored )
            {
                //
            }
            Elements paragraphs = null;
            if( doc != null )
            {
                paragraphs = doc.select( "p" );
            }
            if( paragraphs != null )
            {
                for( Element p : paragraphs )
                {
                    line = p
                            .text()
                            .replaceAll( "[^\\p{L}\\p{Nd}]+", " " )
                            .toLowerCase()
                            .split( " " );

                    for( String w : line )
                    {
                        w = w.trim();
                        if( map.containsKey( w ) )
                        {
                            map.put( w, map.get( w ) + 1 );
                        }
                        else
                        {
                            map.put( w, 1 );
                        }
                    }
                }
            }
        }

        return map;
    }

}