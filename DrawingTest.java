import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DrawingTest {
    Drawing drawing;
    Bet bet;
    @BeforeEach
    void setup(){
        drawing = new Drawing();
        bet = new Bet();
        bet.setNumSpots(10);
        bet.setBetAmount(5);
        bet.setNumDraws(3);
    }

    @Test
    void addTest(){
        drawing.add(2);
        drawing.add(10);
        drawing.add(40);
        drawing.add(30);
//        drawing.printUserDrawing();
        assertEquals(4, drawing.getUserSize(), "Add method failed: wrong value");
    }

    @Test
    void addTest2(){
        drawing.randomize(bet);
        for(int i = 0; i<40; i++){
            if(drawing.userDrawing.contains(i)){
                drawing.remove(i);
            }
        }
        drawing.add(75);
        drawing.add(34);
        drawing.add(98);
//        drawing.printUserDrawing();
        assertEquals(drawing.userDrawing.size(), drawing.getUserSize(), "Add method failed: wrong value");
    }

    @Test
    void removeTest(){
        drawing.add(2);
        drawing.add(10);
        drawing.add(40);
        drawing.add(30);
        drawing.remove(10);
        drawing.remove(40);
        assertEquals(2, drawing.getUserSize(), "Remove method failed: wrong value");
    }

    @Test
    void removeTest2(){
        drawing.randomize(bet);
        for(int i = 1; i<81; i++){
            if(drawing.userDrawing.contains(i)){
                drawing.remove(i);
            }
        }
        assertEquals(0, drawing.getUserSize(), "Remove method failed: wrong value");
    }

    @Test
    void userRandomizeTest(){
        drawing.randomize(bet);
        drawing.printUserDrawing();
        assertEquals(10, drawing.getUserSize(), "userRandomize method failed: wrong value");
    }

    @Test
    void userRandomizeTest2(){
        drawing.add(10);
        drawing.add(9);
        drawing.randomize(bet);
        assertEquals(bet.getNumSpots(), drawing.getUserSize(), "userRandomizeTest2 failed");

    }

    @Test
    void userCheckDupTest(){
        drawing.randomize(bet);
        ArrayList<Integer> array = new ArrayList<Integer>();
        boolean check = true;
        for(int i = 0; i<drawing.getUserSize(); i++){
            if(array.contains(drawing.userDrawing.get(i))){
                check = false;
            }
        }
        assertTrue(check, "userCheckDupTest failed");
    }

    @Test
    void selectedCheckDupTest(){
        drawing.selectGameDraw();
        ArrayList<Integer> array = new ArrayList<Integer>();
        boolean check = true;
        for(int i = 0; i<drawing.getSelectedSize(); i++){
            if(array.contains(drawing.selectedDrawing.get(i))){
                check = false;
            }
        }
        assertTrue(check, "selectedCheckDupTest failed");
    }

    @Test
    void selectedDrawingTest(){
        drawing.selectGameDraw();
        drawing.printSelectedDrawing();
        assertEquals(20, drawing.getSelectedSize(), "selectedDrawing function failed: wrong value");
    }

    @Test
    void selectedDrawingTest2(){
        drawing.addSelected(10);
        drawing.addSelected(2);
        drawing.addSelected(4);
        drawing.addSelected(3);

        drawing.selectGameDraw();

        assertEquals(20, drawing.getSelectedSize(), "selectedDrawingTest2 failed");
    }

    @Test
    void matchTest() {
        drawing.selectGameDraw();
        drawing.randomize(bet);

        drawing.matching();
        int count = 0;
        for (int i = 0; i < drawing.selectedSize; i++) {
            if (drawing.userDrawing.contains(drawing.selectedDrawing.get(i))) {
                count++;
            }
        }
        assertEquals(count, drawing.numMatches, "matchTest failed");
    }

    @Test
    void matchTest2(){
        drawing.selectGameDraw();
        drawing.add(1);
        drawing.add(2);
        drawing.add(3);
        drawing.add(4);
        drawing.add(5);
        drawing.add(6);
        drawing.add(7);
        drawing.add(8);
        drawing.add(9);
        drawing.add(10);

        drawing.matching();
        int count = 0;
        for(int i = 0; i<drawing.selectedSize; i++){
            if(drawing.userDrawing.contains(drawing.selectedDrawing.get(i))){
                count++;
            }
        }
        assertEquals(count, drawing.numMatches, "matchTest2 failed");
    }

    @Test
    void matchTest3(){
        drawing.add(1);
        drawing.add(2);
        drawing.add(3);
        drawing.remove(2);

        drawing.add(4);
        drawing.add(5);
        drawing.remove(5);

        drawing.add(6);
        drawing.add(7);

        drawing.addSelected(1);
        drawing.addSelected(10);
        drawing.addSelected(13);

        drawing.matching();

        assertEquals(1, drawing.getNumMatches(), "matchTest3 failed");

    }

    @Test
    void canAddTest(){
        drawing.randomize(bet);

        // Trying to add 5
        assertFalse(drawing.canAdd(5, bet.getNumSpots()), "canAddTest failed");
    }

    @Test
    void canAddTest2(){
        drawing.add(10);
        drawing.add(3);
        drawing.add(2);
        drawing.add(1);

        // trying to add 5
        assertTrue(drawing.canAdd(5, bet.getNumSpots()), "canAddTest2 failed");
    }

    @Test
    void canAddTest3(){
        drawing.add(10);
        drawing.add(3);
        drawing.add(2);
        drawing.add(1);

        // Trying to add 10
        assertFalse(drawing.canAdd(10, bet.getNumSpots()), "canAddTest3 failed");
    }

    @Test
    void clearTest(){
        drawing.add(10);
        drawing.add(3);
        drawing.add(2);
        drawing.add(1);
        drawing.clear();
        assertEquals(0, drawing.getUserSize(),"clearTest failed");
        assertEquals(0, drawing.getSelectedSize(), "clearTest failed");
        assertEquals(0, drawing.getNumMatches(), "clearTest failed");
    }

    @Test
    void clearTest2(){
        drawing.randomize(bet);
        drawing.selectGameDraw();
        drawing.clear();
        assertEquals(0, drawing.getUserSize(), "clearTest2 failed");
        assertEquals(0, drawing.getSelectedSize(), "clearTest2 failed");
        assertEquals(0, drawing.getNumMatches(), "clearTest2 failed");
    }

    @Test
    void nextGameClearTest(){
        drawing.randomize(bet);
        drawing.selectGameDraw();

        drawing.matching();
        int value = drawing.getNumMatches();

        drawing.nextGameClear();

        assertEquals(value, drawing.getNumMatches(), "nextGameClearTest failed");
        assertEquals(0, drawing.getUserSize(), "nextGameClearTest failed");
        assertEquals(0, drawing.getSelectedSize(), "nextGameClearTest failed");
    }

    @Test
    void setSelectedTest(){
        drawing.addSelected(5);
        drawing.addSelected(10);
        drawing.addSelected(8);
        drawing.addSelected(1);
        assertEquals(4, drawing.selectedSize, "setSelectedTest failed");
    }

//24

}
