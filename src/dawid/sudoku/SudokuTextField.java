package dawid.sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;

public class SudokuTextField extends JFormattedTextField implements ISudokuConstraints {

	static NumberFormatter formatter;
	private Font font;
	static int filledFields;
	boolean filled = false;
	Color prv;

	public SudokuTextField(int size) {
		super(formatter = new NumberFormatter(NumberFormat.getInstance()));
		//NumberFormat format = NumberFormat.getInstance();
		//formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(1);
		formatter.setMaximum(size);
		formatter.setAllowsInvalid(false);
		formatter.setCommitsOnValidEdit(true);
		font = new Font("TimesRoman", Font.PLAIN, FONT_SIZE);
		setFormatter(formatter);
		setPreferredSize(CELL_SIZE);
		setFont(font);
		setHorizontalAlignment(JTextField.CENTER);
		setBackground(Color.YELLOW);
		setSelectionColor(getBackground());
		setCaretColor(Color.red);
		setSelectionColor(Color.red);
		listeners();

	}

	void listeners() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						if (!filled && getValue() != null) {
							filledFields++;
							filled = true;
						} else if (filled && getValue() == null) {
							filledFields--;
							filled = false;
						}
						if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
							setValue(null);
							if (filled) {
								filled = false;
								filledFields--;
							}
						}
						System.out.println(filledFields);
					}
				});
				selectAll();
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				setBackground(prv);
			}

			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						selectAll();
						prv = getBackground();
						setBackground(Color.red);
					}
				});
			}
		});
	}

}
