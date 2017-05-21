package dawid.sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Game extends JFrame {

	int[][] gameArray;
	Board board;

	public Game() {
		board = new Board();
		gameArray = new int[9][9];
		initMenu();
	}

	public static void main(String[] args) {

		Game game = new Game();

	}

	protected boolean checkRow(int row, int num) {
		for (int col = 0; col < 9; col++)
			if (gameArray[row][col] == num)
				return false;

		return true;
	}

	protected boolean checkCol(int col, int num) {
		for (int row = 0; row < 9; row++)
			if (gameArray[row][col] == num)
				return false;

		return true;
	}

	protected boolean checkBox(int row, int col, int num) {
		row = (row / 3) * 3;
		col = (col / 3) * 3;

		for (int r = 0; r < 3; r++)
			for (int c = 0; c < 3; c++)
				if (gameArray[row + r][col + c] == num)
					return false;

		return true;
	}

	private boolean containedIn3x3Box(int row, int col, int value) {
		int startRow = row / 3 * 3;
		int startCol = col / 3 * 3;

		for (int i = startRow; i < startRow + 3; i++)
			for (int j = startCol; j < startCol + 3; j++) {
				if (!(i == row && j == col)) {
					if (gameArray[i][j] == value) {
						return true;
					}
				}
			}

		return false;
	}

	private boolean containedInRowCol(int row, int col, int value) {
		for (int i = 0; i < 9; i++) {
			if (i != col)
				if (gameArray[row][i] == value)
					return true;
			if (i != row)
				if (gameArray[i][col] == value)
					return true;
		}

		return false;
	}

	private Integer[] generateRandomNumbers() {
		ArrayList<Integer> randoms = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++)
			randoms.add(i + 1);
		Collections.shuffle(randoms);

		return randoms.toArray(new Integer[9]);
	}

	private boolean solve(int row, int col) {
		if (row == 9)
			return true;

		if (gameArray[row][col] != 0) {
			if (solve(col == 8 ? (row + 1) : row, (col + 1) % 9))

				return true;
		} else {

			Integer[] randoms = generateRandomNumbers();
			for (int i = 0; i < 9; i++) {

				if (!containedInRowCol(row, col, randoms[i]) && !containedIn3x3Box(row, col, randoms[i])) {
					gameArray[row][col] = randoms[i];

					if (solve(col == 8 ? (row + 1) : row, (col + 1) % 9))
						return true;
					else {
						gameArray[row][col] = 0;
					}
				}
			}
		}

		return false;
	}

	private void eraseCells() {
		Random random = new Random();
		int rand = random.nextInt(30) + 20;
		System.out.println(rand);
		for (int i = 0; i < rand; i++) {
			int randomRow = random.nextInt(9);
			int randomColumn = random.nextInt(9);

			if (gameArray[randomRow][randomColumn] >= 1 && gameArray[randomRow][randomColumn] <= 9) {
				gameArray[randomRow][randomColumn] = 0;
			} else {
				i--;
			}
		}
	}

	void clearGameArray() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				gameArray[i][j] = 0;
			}
		}
	}

	boolean isBoardValid() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				int tmp = gameArray[i][j];
				gameArray[i][j] = 1000;
				if (tmp<= 9 && tmp >= 1) {
					if (containedInRowCol(i, j, tmp)) {
						gameArray[i][j] = tmp;
						return false;
					}
					if (containedIn3x3Box(i, j, tmp)) {
						gameArray[i][j] = tmp;
						return false;
					}

				}
				gameArray[i][j] = tmp;
			}
		}

		return true;
	}

	void initMenu() {
		board.solve.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gameArray = board.getBoardValues();

				if (isBoardValid()) {
					solve(0, 0);
					board.updateBoard(gameArray);

				} else
					JOptionPane.showMessageDialog(board, "Nie ma rozwi¹zania");
			}
		});
		board.newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				board.clearBoard();
				clearGameArray();
				gameArray = board.getBoardValues();

				solve(0, 0);
				eraseCells();
				board.newBoard(gameArray);
			}
		});
		board.check.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isBoardValid() && SudokuTextField.filledFields==80){
					JOptionPane.showMessageDialog(board, "Dobrze");
				}
				else
					JOptionPane.showMessageDialog(board, "Zle");
			}
		});
	}

}
