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
        topPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mainPanel.add(topPanel, BorderLayout.NORTH);

        createGridPanel();
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(gridPanel, BorderLayout.CENTER);

        createControlPanel();
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
                buttons[row][col].setFont(new Font("Serif", Font.PLAIN, 60));
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setBackground(Color.PINK);
                buttons[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

                buttons[row][col].addActionListener((ActionEvent ae) -> {
                    JButton buttonClicked = (JButton) ae.getSource();
                    if (buttonClicked.getText().equals(" ")) {
                        String currentPlayerSymbol = currentPlayer ? "X" : "O";
                        buttonClicked.setText(currentPlayerSymbol);
                        moveCnt++;

                        if (isWin(currentPlayerSymbol)) {
                            JOptionPane.showMessageDialog(gridPanel, "Player " + currentPlayerSymbol + " Won!");
                            disableAllButtons();
                            if (getYNConfirm("Do you want to play again?")) {
                                resetGame();
                            } else {
                                System.exit(0);
                            }

                        }
                        else if (isTie()) {
                            JOptionPane.showMessageDialog(gridPanel, "It's a Tie!");
                            disableAllButtons();
                            if (getYNConfirm("Do you want to play again?")) {
                                resetGame();
                            }
                            else {
                                System.exit(0);
                            }
                        }


                        currentPlayer = !currentPlayer;
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

        for (int col = 0; col < COL; col++) {
            if (buttons[0][col].getText().equals(player) && buttons[1][col].getText().equals(player) && buttons[2][col].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isRowWin(String player) {

        for (int row = 0; row < ROW; row++) {
            if (buttons[row][0].getText().equals(player) && buttons[row][1].getText().equals(player) && buttons[row][2].getText().equals(player)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDiagonalWin(String player) {

        if (buttons[0][0].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][2].getText().equals(player)) {
            return true;
        }
        if (buttons[0][2].getText().equals(player) && buttons[1][1].getText().equals(player) && buttons[2][0].getText().equals(player)) {
            return true;
        }
        return false;
    }

    private boolean isTie()
    {
        if (moveCnt < 7) {
            return false;
        }
        boolean xFlag = false;
        boolean oFlag = false;

        for(int row=0; row < ROW; row++)
        {
            if(buttons[row][0].getText().equals("X") || buttons[row][1].getText().equals("X") || buttons[row][2].getText().equals("X"))
            {
                xFlag = true;
            }
            if(buttons[row][0].getText().equals("O") || buttons[row][1].getText().equals("O") || buttons[row][2].getText().equals("O"))
            {
                oFlag = true;
            }

            if(! (xFlag && oFlag) )
            {
                return false;
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            if(buttons[0][col].getText().equals("X") || buttons[1][col].getText().equals("X") || buttons[2][col].getText().equals("X"))
            {
                xFlag = true;
            }
            if(buttons[0][col].getText().equals("O") || buttons[1][col].getText().equals("O") || buttons[2][col].getText().equals("O"))
            {
                oFlag = true;
            }

            if(! (xFlag && oFlag) )
            {
                return false;
            }
        }

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
            return false;
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
            return false;
        }

        return true;
    }

    private boolean getYNConfirm(String message) {
        int result = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    private void resetGame() {
        moveCnt = 0;
        currentPlayer = true;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                buttons[row][col].setText(" ");
                buttons[row][col].setEnabled(true);
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