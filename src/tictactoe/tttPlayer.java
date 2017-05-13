

package tictactoe;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Meysam
 */
public class tttPlayer implements Serializable {

    private Pieces piece;
    private String name;
    private ArrayList<Byte> tiles;
    private int wins;
    
    public tttPlayer(){
        tiles = new ArrayList<Byte>();
        wins = 0;
    }
    
    public tttPlayer(Pieces p, String n) {
        tiles = new ArrayList<Byte>();
        piece = p;
        name = n;
        wins = 0;
    }
    
     public void assign(tttPlayer p){
        this.name = p.name;
        this.piece = p.piece;
        this.tiles = p.tiles;
        this.wins = p.wins;
    }
    
    public void setPiece(Pieces p){
        piece = p;
    }
    
    public Pieces getPiece(){
        return piece;
    }
    
    public void setName(String str){
        name = str;
    }
    
    public String getName(){
        return name;
    }
    
    public boolean addTile(byte t){
        if(!tiles.contains(t))
        {
            tiles.add(t);
            return true;
        }
        else 
            return false;
    }
    
    public ArrayList<Byte> getTile(){
        return tiles;
    }
    
    public void clearTiles(){
        tiles.clear();
    }
    
    public void increaseWin(){
        wins++;
    }
    
    public int getWins(){
        return wins;
    }

}
