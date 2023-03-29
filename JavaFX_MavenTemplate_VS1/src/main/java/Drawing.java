/*
    Class to that will help drawing the grid on the window. This is because this class will store all of the information regarding the player and game's drawings.
    It can then be used to match the two drawings and see the matching numbers.
 */

import java.util.ArrayList;
import java.util.Random;
public class Drawing {
    ArrayList<Integer> userDrawing;
    int userSize;
    ArrayList<Integer> selectedDrawing;
    int selectedSize;
    ArrayList<Integer> matchedDrawing = new ArrayList<Integer>();
    int numMatches;

    Drawing(){
        userDrawing = new ArrayList<Integer>();
        selectedDrawing = new ArrayList<Integer>();
        matchedDrawing = new ArrayList<Integer>();
        userSize = 0;
        selectedSize = 0;
        numMatches = 0;
    }
    // Method to see if you can add more values or not.
    public boolean canAdd(int val, int numSpots){
        int size = userDrawing.size();
        if(size == numSpots){
            return false;
        }
        for(int i = 0; i< size; i++){
            if(userDrawing.get(i) == val){
                return false;
            }
        }
        return true;
    }

    // Method to add more values to the userDrawing
    public void add(int val){
        userDrawing.add(val);
        userSize++;
    }

    public void addSelected(int val){
        selectedDrawing.add(val);
        selectedSize++;
    }

    // Method to remove value from the userDrawing
    public void remove(int val){
        userDrawing.removeIf(s -> s.equals(val));
        userSize--;
    }
    public void randomize(Bet bet){
        userDrawing.clear();
        userSize = 0;
        int upperBound = 80;
        for(int i = 0; i < bet.getNumSpots(); i++){
            boolean found = false;
            Random rand = new Random();
            int randomNum = rand.nextInt(upperBound);
            randomNum++;

            for(int j = 0; j < userDrawing.size(); j++){
                if(userDrawing.get(j) == randomNum){
                    found = true;
                    i--;
                    break;
                }
            }

            if(!found){
                add(randomNum);
            }
        }
    }
    public void selectGameDraw(){
        selectedDrawing.clear();
        selectedSize = 0;
        int upperBound = 80;
        for(int i = 0; i < 20; i++){
            boolean found = false;
            Random rand = new Random();
            int randomNum = rand.nextInt(upperBound);
            randomNum++;

            for(int j = 0; j < selectedDrawing.size(); j++){
                if(selectedDrawing.get(j) == randomNum){
                    found = true;
                    i--;
                    break;
                }
            }

            if(!found){
                addSelected(randomNum);
            }
        }
    }

    // printing for testing
    public void printUserDrawing(){
        System.out.println("Printing User Drawing...");
        for(int i = 0; i<userSize; i++){
            System.out.println(userDrawing.get(i));
        }
    }

    // printing for testing
    public void printSelectedDrawing(){
        System.out.println("Printing Selected Drawing...");
        for(int i = 0; i<selectedSize; i++){
            System.out.println(selectedDrawing.get(i));
        }
    }

    public void matching(){
        for(int i = 0; i<selectedSize; i++){
            for(int j = 0; j<userSize; j++){
                if(selectedDrawing.get(i) == userDrawing.get(j)){
                    matchedDrawing.add(selectedDrawing.get(i));
                    numMatches++;
                    break;
                }
            }
        }
    }

    public int getNumMatches(){
        return numMatches;
    }

    public void clear(){
        userDrawing.clear();
        userSize = 0;
        selectedDrawing.clear();
        selectedSize = 0;
        matchedDrawing.clear();
        numMatches = 0;
    }

    public void nextGameClear(){
        userDrawing.clear();
        userSize = 0;
        selectedDrawing.clear();
        selectedSize = 0;
        matchedDrawing.clear();
    }

    public int getUserSize(){
        return userSize;
    }
    public int getSelectedSize(){
        return selectedSize;
    }
}
