package co.dilaver.quoter.helper;

import co.dilaver.quoter.models.Quote;

/**
 * Created by argeusmac on 23.05.2016.
 */
public class QuoteParser {

    public String removeQuotations(String inputString){

        if (inputString.contains("\"")) {
            inputString = inputString.replace("\"", "");
        }

        if (inputString.contains("“")) {
            inputString = inputString.replace("“", "");
        }

        if (inputString.contains("”")) {
            inputString = inputString.replace("”", "");
        }

        return inputString;
    }

    public Quote getQuoteTextAndAuthorParsingDashes(String quoteString){

        String quoteText = "";
        String quoteAuthor = "";

        if (quoteString.contains("-")) {
            quoteText = quoteString.split("\\-")[0];
            quoteAuthor = quoteString.substring(quoteString.indexOf("-") + 1);
        } else if (quoteString.contains("\u2015")) {
            quoteText = quoteString.split("\\\u2015")[0];
            quoteAuthor = quoteString.substring(quoteString.indexOf("\u2015") + 1);
        } else if (quoteString.contains("\u2014")) {
            quoteText = quoteString.split("\\\u2014")[0];
            quoteAuthor = quoteString.substring(quoteString.indexOf("\u2014") + 1);
        } else if (quoteString.contains("\u2013")) {
            quoteText = quoteString.split("\\\u2013")[0];
            quoteAuthor = quoteString.substring(quoteString.indexOf("\u2013") + 1);
        } else if (quoteString.contains("\u2012")) {
            quoteText = quoteString.split("\\\u2012")[0];
            quoteAuthor = quoteString.substring(quoteString.indexOf("\u2012") + 1);
        } else if (quoteString.contains("\u2011")) {
            quoteText = quoteString.split("\\\u2011")[0];
            quoteAuthor = quoteString.substring(quoteString.indexOf("\u2011") + 1);
        } else if (quoteString.contains("\u2010")) {
            quoteText = quoteString.split("\\\u2010")[0];
            quoteAuthor = quoteString.substring(quoteString.indexOf("\u2010") + 1);
        }

        return new Quote(quoteText,quoteAuthor);
    }
}
