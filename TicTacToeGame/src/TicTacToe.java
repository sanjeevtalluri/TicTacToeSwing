import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {
	private final static int BOARD_SIZE = 3;

	private JButton[][] buttons;

	private boolean crossTurn;
	
	private int cellsFilled;

	public static enum GameState {
		Incomplete, XWins, ZWins, Tie
	}

	public TicTacToe() {
		buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
		cellsFilled=0;
		crossTurn=true;
		super.setTitle("TicTacToe");
		super.setSize(800, 800);
		super.setResizable(false);
		GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(grid);
		Font font = new Font("Comic Sans", 1, 150);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton("");
				buttons[row][col] = button;
				button.setFont(font);
				button.addActionListener(this);
				super.add(button);
			}
		}
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		postButtonClick(clickedButton);
		GameState currGameState = checkGameState();
		if(currGameState!=GameState.Incomplete) {
			declareTheWinner(currGameState);
			int choice=JOptionPane.showConfirmDialog(this, "Do you want to restart the game ?");
			if(choice==JOptionPane.YES_OPTION) {
				for (int row = 0; row < BOARD_SIZE; row++) {
					for (int col = 0; col < BOARD_SIZE; col++) {
						buttons[row][col].setText("");
						cellsFilled=0;
						crossTurn=true;
					}
				}
			}
			else {
				super.dispose();
			}
		}
	}

	private void declareTheWinner(TicTacToe.GameState currGameState) {
		if(currGameState==GameState.XWins) {
			JOptionPane.showMessageDialog(this, "X Wins");
		}
		else if(currGameState==GameState.ZWins) {
			JOptionPane.showMessageDialog(this, "0 Wins");
		}
		else {
			JOptionPane.showMessageDialog(this, "It's a Tie");
		}
	}

	private TicTacToe.GameState checkGameState() {
		String currText = "", nextText = "";
		// for checking rows
		int row = 0;
		while (row < BOARD_SIZE) {
			int col = 0;
			while (col < BOARD_SIZE - 1) {
				currText = buttons[row][col].getText();
				nextText = buttons[row][col + 1].getText();
				if (currText != nextText || currText.length() == 0)
					break;
				col++;
			}
			if (col == BOARD_SIZE - 1) {
				if (currText.equals("X")) {
					return GameState.XWins;
				} else {
					return GameState.ZWins;
				}
			}
			row++;
		}
		// for checking cols
		int col = 0;
		while (col < BOARD_SIZE) {
			row = 0;
			while (row < BOARD_SIZE - 1) {
				currText = buttons[row][col].getText();
				nextText = buttons[row + 1][col].getText();
				if (currText != nextText || currText.length() == 0)
					break;
				row++;
			}
			if (row == BOARD_SIZE - 1) {
				if (currText.equals("X")) {
					return GameState.XWins;
				} else {
					return GameState.ZWins;
				}
			}
			col++;
		}

		// for checking left diagonal

		row = 0;
		col = 0;

		while (row < BOARD_SIZE - 1) {
			currText = buttons[row][col].getText();
			nextText = buttons[row + 1][col+1].getText();
			if (currText != nextText || currText.length() == 0)
				break;
			row++;
			col++;
		}
		if (row == BOARD_SIZE - 1) {
			if (currText.equals("X")) {
				return GameState.XWins;
			} else {
				return GameState.ZWins;
			}
		}
		
		//for checking right diagonal
		
		row = 0;
		col = BOARD_SIZE - 1;

		while (row <BOARD_SIZE-1) {
			currText = buttons[row][col].getText();
			nextText = buttons[row +1][col-1].getText();
			if (currText != nextText || currText.length() == 0)
				break;
			row++;
			col--;
		}
		if (row == BOARD_SIZE-1) {
			System.out.println("huss");
			if (currText.equals("X")) {
				return GameState.XWins;
			} else {
				return GameState.ZWins;
			}
		}
		
		return cellsFilled==BOARD_SIZE*BOARD_SIZE?GameState.Tie:GameState.Incomplete ;
	}

	private void postButtonClick(JButton clickedButton) {
		String buttonText = clickedButton.getText();
		if (buttonText.length() == 0) {
			cellsFilled++;
			if (crossTurn) {
				clickedButton.setText("X");
			} else {
				clickedButton.setText("O");
			}
			crossTurn = !crossTurn;
		} else {
			JOptionPane.showMessageDialog(this, "Invalid move");
		}

	}
}
