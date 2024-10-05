import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TicTacToeFrame extends JFrame {

    JPanel mainPanel;
    JPanel topPanel;
    JPanel gridPanel;
    JPanel controlPanel;
    JLabel title;
    JButton quit;

    private static final int ROW = 3;
    private static final int COL = 3;
    private JButton[][] buttons = new JButton[ROW][COL];
    boolean currentPlayer = true;
    int moveCnt;

    public TicTacToeFrame() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = (int) (screenSize.width/3.5);
        int height = (int) (screenSize.height*0.10);

        setLocation(width, height);

        createTopPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); // Add padding
        mainPanel.add(topPanel, BorderLayout.NORTH);

        createGridPanel();
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        createControlPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setSize(600, 630);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createTopPanel() {
        topPanel = new JPanel();
        title = new JLabel("Play Tic Tac Toe", JLabel.CENTER);
        title.setFont(new Font("Monospaced", Font.BOLD, 36));
        title.setVerticalTextPosition(SwingConstants.BOTTOM);
        title.setHorizontalTextPosition(SwingConstants.CENTER);

        topPanel.add(title);
    }

    private void createGridPanel() {
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(ROW, COL));
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {

                buttons[row][col] = new JButton(" ");
                buttons[row][col].setFont(new Font("Serif", Font.PLAIN, 60)); // Set font size for buttons
                buttons[row][col].setFocusPainted(false); // Remove focus border
                buttons[row][col].setBackground(Color.PINK); // Set button background color
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

                buttons[row][col].addActionListener((ActionEvent ae) -> {
                    JButton buttonClicked = (JButton) ae.getSource(); // Get the button that was clicked
                    if (buttonClicked.getText().equals(" ")) {
                        String currentPlayerSymbol = currentPlayer ? "X" : "O";
                        buttonClicked.setText(currentPlayerSymbol); // Set text to "X" for Player 1 or "O" for Player 2
                        moveCnt++; // Increment move count

                        // Check if there's a win
                        if (isWin(currentPlayerSymbol)) {
                            JOptionPane.showMessageDialog(gridPanel, "Player " + currentPlayerSymbol + " Won!");
                            disableAllButtons(); // Disable further moves
                            if (getYNConfirm("Do you want to play again?")) {
                                resetGame(); // Reset the game if yes
                            } else {
                                System.exit(0); // End the game if no
                            }

                        }
                        else if (isTie()) {
                            JOptionPane.showMessageDialog(gridPanel, "It's a Tie!");
                            disableAllButtons(); // Disable further moves
                            if (getYNConfirm("Do you want to play again?")) {
                                resetGame(); // Reset the game if yes
                            }
                            else {
                                System.exit(0);
                            }
                        }


                        currentPlayer = !currentPlayer; // Switch turns
                    }
                });
                gridPanel.add(buttons[row][col]);
            }
        }
    }

    private boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagonalWin(player))
        {
            return true;
        }

        return false;
    }
    private boolean isColWin(String player) {
        // checks for a col win for specified player
        for (int col = 0; col < COL; col++) {
            if (buttons[0][col].getText().equals(player) && buttons[1][col].getText().equals(player) && buttons[2][col].getText().equals(player)) {
                return true;
            }
        }
        return false; // no col win
    }

    private boolean isRowWin(String player) {
        // checks for a row win for the specified player
        for (int row = 0; row < ROW; row++) {
            if (buttons[row][0].getText().equals(player) && buttons[row][1].getText().equals(player) && buttons[row][2].getText().equals(player)) {
                return true;
            }
        }
        return false; // no row win
    }

    private boolean isDiagonalWin(String player) {
        // checks for a diagonal win for the specified player
        if (buttons[0][0].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][2].getText().equals(player)) {
            return true;
        }
        if (buttons[0][2].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][0].getText().equals(player)) {
            return true;
        }
        return false;
    }
    // Method to check if the game is a tie
    private boolean isTie()
    {
        if (moveCnt < 7) {
            return false;
        }
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            if(buttons[row][0].getText().equals("X") || buttons[row][1].getText().equals("X") || buttons[row][2].getText().equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(buttons[row][0].getText().equals("O") || buttons[row][1].getText().equals("O") || buttons[row][2].getText().equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(buttons[0][col].getText().equals("X") || buttons[1][col].getText().equals("X") || buttons[2][col].getText().equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(buttons[0][col].getText().equals("O") || buttons[1][col].getText().equals("O") || buttons[2][col].getText().equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if(buttons[0][0].getText().equals("X") || buttons[1][1].getText().equals("X") || buttons[2][2].getText().equals("X") )
        {
            xFlag = true;
        }
        if(buttons[0][0].getText().equals("O") || buttons[1][1].getText().equals("O") || buttons[2][2].getText().equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if(buttons[0][2].getText().equals("X") || buttons[1][1].getText().equals("X") || buttons[2][0].getText().equals("X") )
        {
            xFlag =  true;
        }
        if(buttons[0][2].getText().equals("O") || buttons[1][1].getText().equals("O") || buttons[2][0].getText().equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }

    private boolean getYNConfirm(String message) {
        int result = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION; // Return true if they selected "Yes"
    }

    private void resetGame() {
        moveCnt = 0; // Reset move count
        currentPlayer = true; // Reset current player to the first player (X)
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col].setText(" "); // Clear button text
                buttons[row][col].setEnabled(true); // Re-enable buttons
            }
        }
    }

    private void createControlPanel(){
        controlPanel = new JPanel();
        quit = new JButton("Quit");
        quit.addActionListener((ActionEvent ae) -> System.exit(0));
        quit.setFont(new Font ("Arial",Font.BOLD,14));
        controlPanel.add(quit);
    }

    private void disableAllButtons() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }
}