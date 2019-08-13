package com.dai.llew.kalah.game;

import com.dai.llew.kalah.exceptions.GameException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(0, null, null);
    }

    @Test(expected = GameException.class)
    public void testIsValidPitChoice_playerNull() {
        boolean result = game.isValidPitChoice(null, 0);
    }

    @Test
    public void testIsValidPitChoice_playerOne() {
        List<TestCase<Integer, Boolean>> testCases = new ArrayList<TestCase<Integer, Boolean>>() {{
            add(new TestCase<Integer, Boolean>(-100, false));
            add(new TestCase<Integer, Boolean>(0, false));
            add(new TestCase<Integer, Boolean>(1, true));
            add(new TestCase<Integer, Boolean>(2, true));
            add(new TestCase<Integer, Boolean>(3, true));
            add(new TestCase<Integer, Boolean>(4, true));
            add(new TestCase<Integer, Boolean>(5, true));
            add(new TestCase<Integer, Boolean>(6, true));
            add(new TestCase<Integer, Boolean>(7, false));
            add(new TestCase<Integer, Boolean>(666, false));
        }};

        testIsValidPitChoice(Player.ONE, testCases);
    }

    @Test
    public void testIsValidPitChoice_playerTwo() {
        List<TestCase<Integer, Boolean>> testCases = new ArrayList<TestCase<Integer, Boolean>>() {{
            add(new TestCase<Integer, Boolean>(7, false));
            add(new TestCase<Integer, Boolean>(8, true));
            add(new TestCase<Integer, Boolean>(9, true));
            add(new TestCase<Integer, Boolean>(10, true));
            add(new TestCase<Integer, Boolean>(11, true));
            add(new TestCase<Integer, Boolean>(12, true));
            add(new TestCase<Integer, Boolean>(13, true));
            add(new TestCase<Integer, Boolean>(14, false));
            add(new TestCase<Integer, Boolean>(15, false));
        }};

        testIsValidPitChoice(Player.TWO, testCases);
    }

    private void testIsValidPitChoice(Player player, List<TestCase<Integer, Boolean>> testCases) {
        String errorFormat = "test isValidPitChoice player {0}, pitID {1} returned unexpected result";

        for (TestCase<Integer, Boolean> testCase : testCases) {
            boolean result = game.isValidPitChoice(player, testCase.input());
            String errorMessage = format(errorFormat, player.name(), testCase.expected());
            assertThat(errorMessage, result, equalTo(testCase.expected()));
        }
    }

    @Test
    public void testGetNextPitID() {
        List<TestCase<Integer, Integer>> cases = new ArrayList<TestCase<Integer, Integer>>() {{
            add(new TestCase<Integer, Integer>(-100, 1));
            add(new TestCase<Integer, Integer>(0, 1));
            add(new TestCase<Integer, Integer>(1, 2));
            add(new TestCase<Integer, Integer>(2, 3));
            add(new TestCase<Integer, Integer>(3, 4));
            add(new TestCase<Integer, Integer>(4, 5));
            add(new TestCase<Integer, Integer>(5, 6));
            add(new TestCase<Integer, Integer>(6, 7));
            add(new TestCase<Integer, Integer>(7, 8));
            add(new TestCase<Integer, Integer>(8, 9));
            add(new TestCase<Integer, Integer>(9, 10));
            add(new TestCase<Integer, Integer>(10, 11));
            add(new TestCase<Integer, Integer>(11, 12));
            add(new TestCase<Integer, Integer>(12, 13));
            add(new TestCase<Integer, Integer>(13, 14));
            add(new TestCase<Integer, Integer>(14, 1));
            add(new TestCase<Integer, Integer>(15, 1));
            add(new TestCase<Integer, Integer>(100, 1));
        }};

        String errorFormat = "test testGetNextPitID returned unexpected result for input: {0}";
        for (TestCase<Integer, Integer> tc : cases) {
            int nextID = game.getNextPitID(tc.input());
            assertThat(format(errorFormat, tc.input()), nextID, equalTo(tc.expected()));
        }
    }
}
