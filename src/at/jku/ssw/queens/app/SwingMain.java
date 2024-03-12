package at.jku.ssw.queens.app;

import at.jku.ssw.queens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

public class SwingMain {
    private static final Queens queens = Queens.create(8);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingMain::initField);
    }

    private static void initField() {
        final JFrame frame = new JFrame("Queens");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // TODO create QueensPanel and add it to BorderLayout.CENTER
        frame.add(new QueensPanel(queens), BorderLayout.CENTER);

        // TODO create JLabel for messages and add it to BorderLayout.SOUTH
        //      add listener to queens model to show state as message 
        JLabel label = new JLabel("");
        queens.addQueensModelListener(e -> {
            label.setText("Change position " + e.getRow() + "/" + e.getCol() + ", new State: " + e.getState());
        });
        frame.add(label, BorderLayout.SOUTH);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        
        // TODO add menu items Clear and Exit; implement action events
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(a -> frame.dispose());
        menu.add(exit);
        JMenuItem clear = new JMenuItem("Clear");
        clear.addActionListener(a -> queens.clear());
        menu.add(clear);
        
        frame.pack(); 
        frame.setVisible(true);
    }

}
