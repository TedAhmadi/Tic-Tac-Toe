

package tictactoe;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Meysam
 */
public class ChatCommunication extends Thread {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private JTextComponent outputContainer;
    
    public ChatCommunication(Socket socket, JTextComponent outputContainer) {
               
        this.socket = socket;
        this.outputContainer = outputContainer;
        
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }  catch (IOException e) {
                System.err.println("Chat Error:Could not get I/O from server: "+socket.getPort());
                JOptionPane.showMessageDialog(null, "Chat Error:Could not get I/O from server: "+socket.getPort(), null, 0);
                System.exit(1);
        }
        
    }
    
    public void run() {
            String line;
            try {
                while(true){
                    line = in.readLine();
                    line = "@Other Player: " + line + "\n";
                    outputContainer.setText(outputContainer.getText()+line);
                    outputContainer.setCaretPosition(outputContainer.getDocument().getLength());
                }
            } catch (IOException ex) {
                System.err.println("Chat Error:Could not read from chat input stream!");
                JOptionPane.showMessageDialog(null, "Chat Error:Could not read from chat input stream!", null, 0);
            }
    }

    public void ChatWrite(String str) {
        
        out.println(str);
        
        str = "@Me: " + str + "\n";
        outputContainer.setText(outputContainer.getText()+str);
    }
    
    protected void finalize(){
        try{
            in.close();
            out.close();
        } catch (IOException e) {
            System.out.println("Chat Error:Could not close socket");
            JOptionPane.showMessageDialog(null, "Chat Error:Could not close socket", null, 0);
            System.exit(1);
        }
    }
    
}
