package org.cis1200;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/** Tests for MarkovChain */
public class MarkovChainTest {

    /*
     * Writing tests for Markov Chain can be a little tricky.
     * We provide a few tests below to help you out, but you still need
     * to write your own.
     */

    /* **** ****** **** **** ADD BIGRAMS TESTS **** **** ****** **** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testAddBigram() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        assertTrue(mc.chain.containsKey("1"));
        ProbabilityDistribution<String> pd = mc.chain.get("1");
        assertTrue(pd.getRecords().containsKey("2"));
        assertEquals(1, pd.count("2"));
    }

    @Test
    public void testAddBigramFirstNull() {
        MarkovChain mc = new MarkovChain();
        assertThrows(IllegalArgumentException.class, () -> mc.addBigram(null, "2"));
    }

    @Test
    public void testAddBigramSecondNull() {
        MarkovChain mc = new MarkovChain();
        assertThrows(IllegalArgumentException.class, () -> mc.addBigram("1", null));
    }

    @Test
    public void testAddBigramBothNull() {
        MarkovChain mc = new MarkovChain();
        assertThrows(IllegalArgumentException.class, () -> mc.addBigram(null, null));
    }

    @Test
    public void testAddBigramSameFirstDifferentSecond() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        mc.addBigram("1", "3");
        assertTrue(mc.chain.containsKey("1"));
        ProbabilityDistribution<String> pd = mc.chain.get("1");
        assertTrue(pd.getRecords().containsKey("2"));
        assertTrue(pd.getRecords().containsKey("3"));
        assertEquals(1, pd.count("2"));
        assertEquals(1, pd.count("3"));
    }

    @Test
    public void testAddBigramDSameSecondDifferentFirst() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        mc.addBigram("3", "2");
        assertTrue(mc.chain.containsKey("1"));
        assertTrue(mc.chain.containsKey("3"));
        ProbabilityDistribution<String> pd1 = mc.chain.get("1");
        ProbabilityDistribution<String> pd2 = mc.chain.get("3");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertTrue(pd2.getRecords().containsKey("2"));
        assertEquals(1, pd1.count("2"));
        assertEquals(1, pd2.count("2"));
    }

    @Test
    public void testAddBigramDifferentFirstDifferentSecond() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        mc.addBigram("3", "4");
        assertTrue(mc.chain.containsKey("1"));
        assertTrue(mc.chain.containsKey("3"));
        ProbabilityDistribution<String> pd1 = mc.chain.get("1");
        ProbabilityDistribution<String> pd2 = mc.chain.get("3");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertTrue(pd2.getRecords().containsKey("4"));
        assertEquals(1, pd1.count("2"));
        assertEquals(1, pd2.count("4"));
    }

    @Test
    public void testAddBigramSameFirstSameSecond() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("1", "2");
        mc.addBigram("1", "2");
        assertTrue(mc.chain.containsKey("1"));
        ProbabilityDistribution<String> pd = mc.chain.get("1");
        assertTrue(pd.getRecords().containsKey("2"));
        assertEquals(2, pd.count("2"));
    }

    /* ***** ****** ***** ***** TRAIN TESTS ***** ***** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testTrain() {
        MarkovChain mc = new MarkovChain();
        String sentence = "1 2 3";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(3, mc.chain.size());
        ProbabilityDistribution<String> pd1 = mc.chain.get("1");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertEquals(1, pd1.count("2"));
        ProbabilityDistribution<String> pd2 = mc.chain.get("2");
        assertTrue(pd2.getRecords().containsKey("3"));
        assertEquals(1, pd2.count("3"));
        ProbabilityDistribution<String> pd3 = mc.chain.get("3");
        assertTrue(pd3.getRecords().containsKey(MarkovChain.END_TOKEN));
        assertEquals(1, pd3.count(MarkovChain.END_TOKEN));
    }

    @Test
    public void testTrainSingleton() {
        MarkovChain mc = new MarkovChain();
        String sentence = "cis";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(1, mc.chain.size());
        ProbabilityDistribution<String> pd = mc.chain.get("cis");
        assertTrue(pd.getRecords().containsKey(MarkovChain.END_TOKEN));
        assertEquals(1, pd.count(MarkovChain.END_TOKEN));
    }

    @Test
    public void testTrainNull() {
        MarkovChain mc = new MarkovChain();
        Iterator<String> sentence = null;
        assertThrows(IllegalArgumentException.class, () -> mc.train(sentence));
    }

    @Test
    public void testTrainTwoSetsOfThree() {
        MarkovChain mc = new MarkovChain();
        String sentence = "1 2 3 4 5 6";
        mc.train(Arrays.stream(sentence.split(" ")).iterator());
        assertEquals(6, mc.chain.size());

        ProbabilityDistribution<String> pd1 = mc.chain.get("1");
        assertTrue(pd1.getRecords().containsKey("2"));
        assertEquals(1, pd1.count("2"));

        ProbabilityDistribution<String> pd2 = mc.chain.get("2");
        assertTrue(pd2.getRecords().containsKey("3"));
        assertEquals(1, pd2.count("3"));

        ProbabilityDistribution<String> pd3 = mc.chain.get("3");
        assertTrue(pd3.getRecords().containsKey("4"));
        assertEquals(1, pd3.count("4"));

        ProbabilityDistribution<String> pd4 = mc.chain.get("4");
        assertTrue(pd4.getRecords().containsKey("5"));
        assertEquals(1, pd4.count("5"));

        ProbabilityDistribution<String> pd5 = mc.chain.get("5");
        assertTrue(pd5.getRecords().containsKey("6"));
        assertEquals(1, pd5.count("6"));

        ProbabilityDistribution<String> pd6 = mc.chain.get("6");
        assertTrue(pd6.getRecords().containsKey(MarkovChain.END_TOKEN));
        assertEquals(1, pd6.count(MarkovChain.END_TOKEN));
    }

    @Test
    public void testRestartNull() {
        MarkovChain mc = new MarkovChain();
        String start = null;
        assertThrows(IllegalArgumentException.class, () -> mc.reset(start));
    }

    @Test
    public void testRestartNotInChain() {
        MarkovChain mc = new MarkovChain();
        mc.addBigram("dog", "cat");
        String start = "bat";
        mc.reset(start);
    }

    /* **** ****** ****** MARKOV CHAIN CLASS TESTS ***** ****** ***** */

    /* Here's an example test case. Be sure to add your own as well */
    @Test
    public void testWalk() {
        /*
         * Using the sentences "CIS 1200 rocks" and "CIS 1200 beats CIS 1600",
         * we're going to put some bigrams into the Markov Chain.
         *
         * While in the real world, we want the sentence we output to be random,
         * we don't want this in testing. For testing, we want to modify our
         * ProbabilityDistribution such that it will output a predictable chain
         * of words.
         *
         * Luckily, we've provided a `fixDistribution` method that will do this
         * for you! By calling `fixDistribution` with a list of words that you
         * expect to be output, the ProbabilityDistributions will be modified to
         * output your words in that order.
         *
         * See our below test for an example of how to use this.
         */

        String[] expectedWords = { "CIS", "1200", "beats", "CIS", "1200", "rocks" };
        MarkovChain mc = new MarkovChain();

        String sentence1 = "CIS 1200 rocks";
        String sentence2 = "CIS 1200 beats CIS 1600";
        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());

        mc.reset("CIS"); // we start with "CIS" since that's the word our desired walk starts with
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords)));

        for (int i = 0; i < expectedWords.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords[i], mc.next());
        }

    }

    @Test
    public void testWalkTwo() {

        String[] expectedWords = { "red", "orange", "yellow", "green", "blue", "purple", "pink",
            "red", "orange", "yellow" };
        MarkovChain mc = new MarkovChain();

        String sentence1 = "red orange yellow";
        String sentence2 = "yellow green blue";
        String sentence3 = "blue purple pink";
        String sentence4 = "pink red orange";

        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());
        mc.train(Arrays.stream(sentence3.split(" ")).iterator());
        mc.train(Arrays.stream(sentence4.split(" ")).iterator());

        mc.reset("red");
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords)));

        for (int i = 0; i < expectedWords.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords[i], mc.next());
        }

    }

    @Test
    public void testWalkThree() {
        String[] expectedWords = { "I", "love", "brownies" };
        MarkovChain mc = new MarkovChain();

        String sentence1 = "I love cookies";
        String sentence2 = "I don't love brownies";

        mc.train(Arrays.stream(sentence1.split(" ")).iterator());
        mc.train(Arrays.stream(sentence2.split(" ")).iterator());

        mc.reset("I"); // we start with "CIS" since that's the word our desired walk starts with
        mc.fixDistribution(new ArrayList<>(Arrays.asList(expectedWords)));

        for (int i = 0; i < expectedWords.length; i++) {
            assertTrue(mc.hasNext());
            assertEquals(expectedWords[i], mc.next());
        }
    }
}
