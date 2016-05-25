package co.dilaver.quoter.helper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import co.dilaver.quoter.models.Quote;

import static junit.framework.Assert.assertTrue;

/**
 * Created by omer on 5/25/16.
 */
public class QuoteParserTest {

    private QuoteParser quoteParser;

    private String firstQuoteOriginal = "\"May your trails be crooked, winding, lonesome, dangerous, leading to the most amazing view. May your mountains rise into and above the clouds.\" -Edward Abbey (1927-1989) American Writer";
    private String firstQuoteParsed = "May your trails be crooked, winding, lonesome, dangerous, leading to the most amazing view. May your mountains rise into and above the clouds. -Edward Abbey (1927-1989) American Writer";
    private String firstQuoteAuthor = "Edward Abbey (1927-1989) American Writer";
    private String firstQuoteText = "May your trails be crooked, winding, lonesome, dangerous, leading to the most amazing view. May your mountains rise into and above the clouds. ";

    private String secondQuoteOriginal = "Marriage has no guarantees. If that's what you're looking for, go live with a car battery. - Erma Bombeck";
    private String secondQuoteParse = "Marriage has no guarantees. If that's what you're looking for, go live with a car battery. - Erma Bombeck";
    private String secondQuoteAuthor = " Erma Bombeck";
    private String secondQuoteText = "Marriage has no guarantees. If that's what you're looking for, go live with a car battery. ";

    private String thirdQuoteOriginal = "Though no one can go back and make a brand new start, anyone can start from now and make a brand new ending.- Carl Brand";
    private String thirdQuoteParsed = "Though no one can go back and make a brand new start, anyone can start from now and make a brand new ending.- Carl Brand";
    private String thirdQuoteAuthor = " Carl Brand";
    private String thirdQuoteText = "Though no one can go back and make a brand new start, anyone can start from now and make a brand new ending.";

    @Before
    public void setUp() throws Exception {
        quoteParser = new QuoteParser();
    }

    @Test
    public void firstQuote() throws Exception {
        String quoteString = firstQuoteOriginal;

        quoteString = quoteParser.removeQuotations(quoteString);
        assertTrue(quoteString.equals(firstQuoteParsed));

        Quote popularQuote = quoteParser.getQuoteTextAndAuthorParsingDashes(quoteString);
        assertTrue(firstQuoteAuthor.equals(popularQuote.getQuoteAuthor()));
        assertTrue(firstQuoteText.equals(popularQuote.getQuoteText()));
    }

    @Test
    public void secondQuote() throws Exception {
        String quoteString = secondQuoteOriginal;

        quoteString = quoteParser.removeQuotations(quoteString);
        assertTrue(quoteString.equals(secondQuoteParse));

        Quote popularQuote = quoteParser.getQuoteTextAndAuthorParsingDashes(quoteString);
        assertTrue(secondQuoteAuthor.equals(popularQuote.getQuoteAuthor()));
        assertTrue(secondQuoteText.equals(popularQuote.getQuoteText()));
    }

    @Test
    public void thirdQuote() throws Exception {
        String quoteString = thirdQuoteOriginal;

        assertTrue(quoteString.equals(thirdQuoteOriginal));
        quoteString = quoteParser.removeQuotations(quoteString);

        assertTrue(quoteString.equals(thirdQuoteParsed));
        Quote popularQuote = quoteParser.getQuoteTextAndAuthorParsingDashes(quoteString);
        assertTrue(thirdQuoteAuthor.equals(popularQuote.getQuoteAuthor()));
        assertTrue(thirdQuoteText.equals(popularQuote.getQuoteText()));
    }

    @After
    public void tearDown() throws Exception {

    }

}