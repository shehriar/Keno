/*
    Class that is used to store all information regarding the bet. This is the information that will change the earnings from the bet.
    This class can also be used to calculate the earnings.
 */
public class Bet {
    int betAmount;
    int numSpots;
    int numDraws;

    public Bet(){
        betAmount = 0;
        numSpots = 0;
        numDraws = 0;
    }

    public void clear(){
        betAmount = 0;
        numSpots = 0;
        numDraws = 0;
    }

    public double winnings(int value){
//        return betAmount * (value/(numSpots+numDraws));
        double avgMatches = value/numDraws;
        return ((betAmount*avgMatches)*(betAmount*avgMatches))+2/numSpots;
    }

    public void setBetAmount(int value){
        betAmount = value;
    }
    public int getBetAmount(){
        return betAmount;
    }

    public void setNumSpots(int value){
        numSpots = value;
    }
    public int getNumSpots(){
        return numSpots;
    }
    public void setNumDraws(int value){
        numDraws = value;
    }
    public int getNumDraws(){
        return numDraws;
    }

}
