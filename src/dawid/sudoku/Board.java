package dawid.sudoku;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Board extends JFrame implements ISudokuConstraints {

	private SudokuTextField[][] fields;
	JMenuItem save;
	JMenuItem load;
	JMenuItem newGame;
	JMenuItem reset;
	JMenuItem solve;
	JMenuItem check;

	public Board() {
		super("Sudoku");

		createUI();
		createBoard();

		pack();

	}

	void createBoard() {

		fields = new SudokuTextField[GRID_SIZE][GRID_SIZE];
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {

				fields[i][j] = new SudokuTextField(GRID_SIZE);
				add(fields[i][j]);
				int[] tab = { 3, 4, 5 };
				for (int x : tab) {
					for (int y : tab) {

						if ((i == x) || (j == y)) {
							fields[i][j].setBackground(Color.WHITE);

							for (int z : tab) {
								if ((i == x && j == z) || (i == z && j == y)) {
									fields[i][j].setBackground(Color.yellow);
								}
							}
						}

					}
				}
			}
		}

	}

	void createUI() {

		GridLayout layuot = new GridLayout(GRID_SIZE + 1, GRID_SIZE, 5, 5);
		setLayout(layuot);
		getContentPane().setBackground(Color.orange);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		initMenu();

	}

	void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu file = new JMenu("Plik");
		JMenu game = new JMenu("Game");

		save = new JMenuItem("Save");
		load = new JMenuItem("Load");
		newGame = new JMenuItem("New Game");
		reset = new JMenuItem("Reset");
		solve = new JMenuItem("Solve");
		check = new JMenuItem("Check");
		//file.add(save);
		//file.add(load);
		addListenersToMenu();
		game.add(newGame);
		game.add(reset);
		game.add(solve);
		game.add(check);

		menuBar.add(game);
		//menuBar.add(file);
	}

	void addListenersToMenu() {
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearBoard();

			}
		});
	}

	void clearBoard() {
		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				fields[i][j].setValue(null);
				fields[i][j].setEnabled(true);

			}
		}
		SudokuTextField.filledFields = 0;
	}
	void newBoard(int[][] tab){
		for (int i = 0; i < GRID_SIZE; i++)
			for (int j = 0; j < GRID_SIZE; j++)
				if (tab[i][j] != 0){
					fields[i][j].setValue(tab[i][j]);
					fields[i][j].setEnabled(false);
				}
				else
					fields[i][j].setValue(null);
	}
	void updateBoard(int[][] tab) {
		for (int i = 0; i < GRID_SIZE; i++)
			for (int j = 0; j < GRID_SIZE; j++)
				if (tab[i][j] != 0)
					fields[i][j].setValue(tab[i][j]);
				else
					fields[i][j].setValue(null);
	}
	void setField(int row,int col,Object val){
		fields[row][col].setValue(val);
	}
	int[][] getBoardValues() {
		int[][] x = new int[GRID_SIZE][GRID_SIZE];

		for (int i = 0; i < GRID_SIZE; i++) {
			for (int j = 0; j < GRID_SIZE; j++) {
				if (fields[i][j].getValue() != null) {
					x[i][j]=Integer.parseInt(fields[i][j].getText());
				}
				else
					x[i][j]=0;
			}
		}

		return x;
	}
}