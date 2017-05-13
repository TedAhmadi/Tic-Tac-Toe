
package tictactoe;


/**
 *
 * @author Meysam
 */
public class Game {
   
    private tttPlayer player1, player2;
    private tttPlayer activePlayer;
    private tttPlayer thisPlayer;

    
    private static Byte[][] winConditions = {
        {1,2,3}, {4,5,6}, {7,8,9},
        {1,4,7}, {2,5,8}, {3,6,9},
        {1,5,9}, {3,5,7}
    };

    public Game() {
        player1 = new tttPlayer();
        player2 = new tttPlayer();
        activePlayer = player1;
        thisPlayer = null;
    }
    
    public Game(tttPlayer p1, tttPlayer p2) {
        player1 = p1;
        player2 = p2;
        activePlayer = player1;
        thisPlayer = null;
    }
    
    private tttPlayer getWinner(){
        int p1, p2;
        for(int i=0; i<8; i++){
            p1 = p2 = 0;
            for(int j=0; j<3; j++){
                if(player1.getTile().contains(winConditions[i][j]))
                    p1++;
                else if(player2.getTile().contains(winConditions[i][j]))
                    p2++;
            }
            
            if(p1==3)
            {
                player1.increaseWin();
                reset();
                activePlayer = player1;
                return player1;
            }
            else if(p2==3)
            {
                player2.increaseWin();
                reset();
                activePlayer = player2;
                return player2;
            }
        }
        
        return null;
    }
    
    public tttPlayer getPlayer1(){
        return player1;
    }
    
    public tttPlayer getPlayer2(){
        return player2;
    }

    public tttPlayer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(tttPlayer activePlayer) {
        this.activePlayer = activePlayer;
    }
    
    public void changeActivePlayer() {
        if(activePlayer == player1)
            activePlayer = player2;
        else
            activePlayer = player1;
    }
    
    public tttPlayer assignTile(Byte tile){
        activePlayer.addTile(tile);
        changeActivePlayer();
        return getWinner();
    }
    
    public void reset(){
        player1.clearTiles();
        player2.clearTiles();
    }
    
    public tttPlayer getThisPlayer() {
        return thisPlayer;
    }

    public void setThisPlayer(tttPlayer thisPlayer) {
        this.thisPlayer = thisPlayer;
    }
    
    public boolean thisPlayerIsServer(){
        if(thisPlayer==player1)
            return true;
        else 
            return false;
    }
}
