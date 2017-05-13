    
package tictactoe;

import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;


/**
 *
 * @author Meysam
 */
public class GameCommunication extends Thread{
    
    Game game;
    TicTacToeView tttView;
    private ServerSocket sSocket = null;
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private final int gamePort = 1983;

    public GameCommunication(Game game, TicTacToeView tttView){
        this.game = game;
        this.tttView = tttView;
        if(game.thisPlayerIsServer()){
            try{
                sSocket = new ServerSocket(gamePort);
            }catch(IOException e){
                System.out.println("Game Error:Could not listen on port: "+ gamePort);
                JOptionPane.showMessageDialog(null, "Game Error:Could not listen on port: "+ gamePort, null, 0);
                System.exit(1);
            }
            try {
                JOptionPane.showMessageDialog(tttView.getFrame(), "Waiting for Player2 to connect...");
                socket = sSocket.accept();
            } catch (IOException e) {
                System.out.println("Game Error:Accept failed on port: "+ gamePort);
                JOptionPane.showMessageDialog(null, "Game Error:Accept failed on port: "+ gamePort, null, 0);
                System.exit(1);
            }
        }
        else{
            try {
                socket = new Socket(TicTacToeView.getPlayer1IpAddress(), gamePort);
            } catch (UnknownHostException e) {
                System.err.println("Game Error:Unknown host: "+TicTacToeView.getPlayer1IpAddress());
                JOptionPane.showMessageDialog(null, "Game Error:Unknown host: "+TicTacToeView.getPlayer1IpAddress(), null, 0);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Game Error:Could not get I/O from server: "+TicTacToeView.getPlayer1IpAddress());
                JOptionPane.showMessageDialog(null, "Game Error:Could not get I/O from server: "+TicTacToeView.getPlayer1IpAddress(), null, 0);
                System.exit(1);
            }
        }
        
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            if(game.thisPlayerIsServer()){
                out.println(game.getPlayer1().getPiece());
            }
            else{
                String str = in.readLine();
                if(str.equals("x")){
                    game.getPlayer1().setPiece(Pieces.x);
                    game.getPlayer2().setPiece(Pieces.o);
                }
                else{
                    game.getPlayer1().setPiece(Pieces.o);
                    game.getPlayer2().setPiece(Pieces.x);
                }
            }
        }  catch (IOException e) {
                System.err.println("Game Error:Could not get I/O from server: "+socket.getPort());
                JOptionPane.showMessageDialog(null, "Game Error:Could not get I/O from server: "+socket.getPort(), null, 0);
                System.exit(1);
        }
    }

    public void run(){
        Byte tile;
        try {
            while(true){
                tile = Byte.parseByte(in.readLine());
                tttView.nextMove(tile);
            }
        } catch (IOException ex) {
            System.err.println("Game Error:Could not read from game input stream!");
            JOptionPane.showMessageDialog(null, "Game Error:Could not read from game input stream!", null, 0);
        }
    }
    
    public void sendNextMove(Byte tile){
        out.println(tile);
    }
    
    protected void finalize(){
        try{
            socket.close();
            sSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Game Error:Could not close socket");
            JOptionPane.showMessageDialog(null, "Game Error:Could not close socket", null, 0);
            System.exit(1);
        }
    }   

}
