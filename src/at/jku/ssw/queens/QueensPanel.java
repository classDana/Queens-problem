package at.jku.ssw.queens;

import inout.Out;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.floorDiv;

public class QueensPanel extends JPanel {
    private static final int CELL_SIZE = 60;
    private static final int GAP = 20;

    private final Queens queens;
    private BufferedImage queenImage;
    private final Dimension size;

    public QueensPanel(Queens queens) {
        this.queens = queens;
        queenImage = null;
        try {
            queenImage = ImageIO.read(new File("images/queen.png"));
        } catch (IOException e) {
            Out.println("Queen image source not found");
        }
        this.size = new Dimension(queens.getSize() * CELL_SIZE + 2 * GAP,
                queens.getSize() * CELL_SIZE + 2 * GAP);
        
        // TODO: add mouse listener and model listener
        queens.addQueensModelListener(modelListener);
        addMouseListener(mouseListener);
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }
    
    @Override
    protected void paintComponent(Graphics g){
    	Graphics2D g2d = (Graphics2D) g;
    	Color background = null;  
    	
    	// TODO set background based on game state
        if(queens.getGameState().equals(State.INVALID)){
            g2d.setBackground(Color.RED);
        }else if(queens.getGameState().equals(State.COMPLETE)){
            g2d.setBackground(Color.GREEN);
        }else{
            //queens.getGameState().equals(State.INCOMPLETE)
            g2d.setBackground(Color.YELLOW);
        }
    	
        g2d.clearRect(getX(), getY(), getWidth(), getHeight());
        
        drawBoard(g2d);
        try {
            drawQueens(g2d);
        } catch (InvalidPositionException e) {
            e.printStackTrace();
        }

    }


	private void drawBoard(Graphics2D g2D) {
		boolean white = true; 
		for (int r = 0; r < queens.getSize(); r++) {
			for (int c = 0; c < queens.getSize(); c++) {
				g2D.setColor(white ? Color.WHITE : Color.BLACK);
				g2D.fillRect(GAP + c * CELL_SIZE, GAP + r * CELL_SIZE, CELL_SIZE, CELL_SIZE);
				white = ! white; 
			}
			white = ! white; 
		}
	}

	private void drawQueens(Graphics2D g2D) throws InvalidPositionException{
		// TODO: draw queens on positions
        for(int x= 0; x < queens.getSize(); x++){
            for(int y = 0; y < queens.getSize(); y++){
                if(queens.hasQueen(x+1,(char) (y+'A'))){
                    g2D.drawImage(queenImage, GAP+y*CELL_SIZE, GAP+x*CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                }
            }
        }
	}

	private MouseListener mouseListener = new MouseAdapter() {

        @Override
        public void mouseReleased(MouseEvent e) {
            int row = 1 + floorDiv((e.getY() - GAP), CELL_SIZE);
            char col = (char) ('A' + floorDiv((e.getX() - GAP), CELL_SIZE));

            // TODO: react to mouse released events
            //    - if e.isControlDown remove otherwise set queen
            //    - catch to exceptions and show message pane as follows
            if (e.isControlDown()) {
                try {
                    if (queens.hasQueen(row, col)) {
                        try {
                            queens.removeQueen(row, col);
                        } catch (QueensException queensException) {
                            JOptionPane.showMessageDialog(QueensPanel.this, "Could not remove queen", "title", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (InvalidPositionException invalidPositionException) {
                    JOptionPane.showMessageDialog(QueensPanel.this, "Invalid position for method hasQueen", "title", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                try {
                    queens.setQueen(row, col);
                } catch (QueensException queensException) {
                    JOptionPane.showMessageDialog(QueensPanel.this, "Could not set queen", "title", JOptionPane.ERROR_MESSAGE);
                }

            }
        }};

        private final QueensListener modelListener = event -> repaint();
    }
