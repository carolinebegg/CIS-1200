package org.cis1200;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for TweetParser */
public class TweetParserTest {

    // A helper function to create a singleton list from a word
    private static List<String> singleton(String word) {
        List<String> l = new LinkedList<>();
        l.add(word);
        return l;
    }

    // A helper function for creating lists of strings
    private static List<String> listOfArray(String[] words) {
        List<String> l = new LinkedList<>();
        for (String s : words) {
            l.add(s);
        }
        return l;
    }

    // Cleaning and filtering tests -------------------------------------------
    @Test
    public void removeURLsTest() {
        assertEquals("abc . def.", TweetParser.removeURLs("abc http://www.cis.upenn.edu. def."));
        assertEquals("abc", TweetParser.removeURLs("abc"));
        assertEquals("abc ", TweetParser.removeURLs("abc http://www.cis.upenn.edu"));
        assertEquals("abc .", TweetParser.removeURLs("abc http://www.cis.upenn.edu."));
        assertEquals(" abc ", TweetParser.removeURLs("http:// abc http:ala34?#?"));
        assertEquals(" abc  def", TweetParser.removeURLs("http:// abc http:ala34?#? def"));
        assertEquals(" abc  def", TweetParser.removeURLs("https:// abc https``\":ala34?#? def"));
        assertEquals("abchttp", TweetParser.removeURLs("abchttp"));
    }

    @Test
    public void testCleanWord() {
        assertEquals("abc", TweetParser.cleanWord("abc"));
        assertEquals("abc", TweetParser.cleanWord("ABC"));
        assertNull(TweetParser.cleanWord("@abc"));
        assertEquals("ab'c", TweetParser.cleanWord("ab'c"));
    }

    /* **** ****** **** WRITE YOUR TESTS BELOW THIS LINE **** ****** **** */

    /* **** ****** ***** **** EXTRACT COLUMN TESTS **** **** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testExtractColumnGetsCorrectColumn() {
        assertEquals(
                " This is a tweet.",
                TweetParser.extractColumn(
                        "wrongColumn, wrong column, wrong column!, This is a tweet.", 3
                )
        );
    }

    @Test
    public void testExtractColumnGetsCorrectColumnZero() {
        assertEquals(
                "This is a tweet",
                TweetParser.extractColumn(
                        "This is a tweet, wrongColumn, wrong column, wrong column!", 0
                )
        );
    }

    @Test
    public void testExtractColumnGetsCorrectColumnTwo() {
        assertEquals(
                " This is a tweet",
                TweetParser.extractColumn(
                        "WRONG, still wrong, This is a tweet, wrong again!", 2
                )
        );
    }

    @Test
    public void testExtractColumnNoCvsColumnEntry() {
        assertNull(
                TweetParser.extractColumn(
                        "Hi, my name, is, Caroline!", 4
                )
        );
    }

    @Test
    public void testExtractColumnNullCsvLine() {
        assertEquals(
                null,
                TweetParser.extractColumn(
                        null, 3
                )
        );
    }

    @Test
    public void testExtractColumnEmpty() {
        assertEquals(
                null,
                TweetParser.extractColumn(
                        "", 0
                )
        );
    }

    /* **** ****** ***** ***** CSV DATA TO TWEETS ***** **** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testCsvDataToTweetsSimpleCSV() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 1);
        List<String> expected = new LinkedList<>();
        expected.add(" The end should come here.");
        expected.add(" This comes from data with no duplicate words!");
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTweetsOutOfBoundsColumn() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 2);
        List<String> expected = new LinkedList<>();
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTweetsNoTweets() {
        StringReader sr = new StringReader("");
        BufferedReader br = new BufferedReader(sr);
        List<String> tweets = TweetParser.csvDataToTweets(br, 0);
        List<String> expected = new LinkedList<>();
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTweetsNull() {
        assertThrows(
                IllegalArgumentException.class, () -> new FileLineIterator((BufferedReader) null)
        );
    }

    /* **** ****** ***** ** PARSE AND CLEAN SENTENCE ** ***** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void parseAndCleanSentenceNonEmptyFiltered() {
        List<String> sentence = TweetParser.parseAndCleanSentence("abc #@#F");
        List<String> expected = new LinkedList<>();
        expected.add("abc");
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceCleanOneWord() {
        List<String> sentence = TweetParser.parseAndCleanSentence("hi");
        List<String> expected = new LinkedList<>();
        expected.add("hi");
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceOneBadWord() {
        List<String> sentence = TweetParser.parseAndCleanSentence("c@r$o!wD");
        List<String> expected = new LinkedList<>();
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceCleanSentence() {
        List<String> sentence = TweetParser.parseAndCleanSentence("hi my name is caroline");
        List<String> expected = new LinkedList<>();
        expected.add("hi");
        expected.add("my");
        expected.add("name");
        expected.add("is");
        expected.add("caroline");
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceBadSentence() {
        List<String> sentence = TweetParser.parseAndCleanSentence("cis is n@t my favorite class");
        List<String> expected = new LinkedList<>();
        expected.add("cis");
        expected.add("is");
        expected.add("my");
        expected.add("favorite");
        expected.add("class");
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceEmpty() {
        List<String> sentence = TweetParser.parseAndCleanSentence("");
        List<String> expected = new LinkedList<>();
        assertEquals(expected, sentence);
    }

    @Test
    public void parseAndCleanSentenceNoValidWords() {
        List<String> sentence = TweetParser.parseAndCleanSentence("3#X12 !F2@9");
        List<String> expected = new LinkedList<>();
        assertEquals(expected, sentence);
    }

    /* **** ****** ***** **** PARSE AND CLEAN TWEET *** ***** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testParseAndCleanTweetRemovesURLS1() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testParseAndCleanTweetRemovesURLS2() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("http://www.thewalkmag.org abc http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testParseAndCleanTweetRemovesURLSNone() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("abc");
        List<List<String>> expected = new LinkedList<>();
        expected.add(singleton("abc"));
        assertEquals(expected, sentences);
    }

    @Test
    public void testParseAndCleanTweetRemovesURLNoValidTweets() {
        List<List<String>> sentences = TweetParser
                .parseAndCleanTweet("http://www.cis.upenn.edu");
        List<List<String>> expected = new LinkedList<>();
        assertEquals(expected, sentences);
    }

    /* **** ****** ***** ** CSV DATA TO TRAINING DATA ** ***** ****** **** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testCsvDataToTrainingDataSimpleCSV() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" +
                        "1, This comes from data with no duplicate words!"
        );
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTrainingDataEmpty() {
        StringReader sr = new StringReader("");
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<>();
        assertEquals(expected, tweets);
    }

    @Test
    public void testCsvDataToTrainingDataEmptyTweet() {
        StringReader sr = new StringReader(
                "0, The end should come here.\n" + "" +
                        "2, This comes from data with no duplicate words!\n" + "@ t#e e^d"
        );
        BufferedReader br = new BufferedReader(sr);
        List<List<String>> tweets = TweetParser.csvDataToTrainingData(br, 1);
        List<List<String>> expected = new LinkedList<>();
        expected.add(listOfArray("the end should come here".split(" ")));
        expected.add(listOfArray("this comes from data with no duplicate words".split(" ")));
        assertEquals(expected, tweets);
    }

}
