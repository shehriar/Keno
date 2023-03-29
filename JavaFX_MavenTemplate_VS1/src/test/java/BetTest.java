import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BetTest {

    Bet bet;
    @BeforeEach
    void setup(){
        bet = new Bet();
    }

    @Test
    void setSpotsTest(){
        bet.setNumSpots(5);
        assertEquals(5, bet.getNumSpots(), "setSpotsTest failed");
    }

    @Test
    void setBetTest(){
        bet.setBetAmount(10);
        assertEquals(10, bet.getBetAmount(), "setBetTest failed");
    }

    @Test
    void setDrawsTest(){
        bet.setNumDraws(10);
        assertEquals(10, bet.getNumDraws(), "setDrawsTest failed");
    }

    @Test
    void totalWinningsTest(){
        bet.setBetAmount(10);
        bet.setNumSpots(8);
        bet.setNumDraws(3);
        int value = 15;
        assertEquals(31.25, bet.winnings(value), "totalWinningsTest failed");

    }

    @Test
    void totalWinningsTest2(){
        bet.setBetAmount(4);
        bet.setNumSpots(4);
        bet.setNumDraws(2);
        int value = 3;
        assertEquals(5, bet.winnings(value), "totalWinningsTest2 failed");

    }
}
