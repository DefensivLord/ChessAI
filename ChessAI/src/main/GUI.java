package main;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JColorChooser;

import chess.Board;
import chess.move.MoveStatus;
import chess.move.MoveTransition;
import display.Display;
import gfx.Assets;
import ui.listeners.ClickListener;
import ui.listeners.MoveExecutionListener;
import ui.objects.UIBoardPanel;
import ui.objects.UIButton;
import ui.objects.UILabel;
import ui.objects.UIObject;
import ui.objects.UIPanel;
import ui.objects.UISelectionBox;

public class GUI {
	private static Board board;
	private static Display display;

	private static final Font bold_font = new Font("Sans Serif", Font.BOLD, 15);

	public GUI() {
		Assets.init();

		display = new Display("Chess", 1010, 820);
		display.setBackground(Color.white);

		board = Board.create();

		UIBoardPanel boardPanel = new UIBoardPanel();
		boardPanel.setBounds(10, 10, 800, 800);
		boardPanel.setBoard(board);
		boardPanel.setBorder(Color.black);
		boardPanel.setMoveExecutionListener(new MoveExecutionListener() {
			@Override
			public void moveExecuted(MoveTransition e) {
				if (e.getMoveStatus() == MoveStatus.DONE) {
					boardPanel.setBoard(board = e.getNewBoard());
				}
			}
		});
		display.add(boardPanel);

		UIPanel panelSettings = new UIPanel();
		panelSettings.setBounds(820, 10, 180, 230);
		panelSettings.setBorder(Color.black);
		display.add(panelSettings);

		UILabel lblPlayer1 = new UILabel("Player 1 (White)");
		lblPlayer1.setBounds(820, 15, 180, 20);
		lblPlayer1.setHorizontalAlignment(UIObject.CENTER);
		lblPlayer1.setFont(bold_font);
		panelSettings.add(lblPlayer1);

		UISelectionBox<String> boxPlayer1 = new UISelectionBox<String>(new String[] { "Player", "AI" });
		boxPlayer1.setBounds(835, 55, 150, 25);
		boxPlayer1.setTextColor(Color.white);
		boxPlayer1.setBackground(Color.black);
		panelSettings.add(boxPlayer1);

		UILabel lblPlayer2 = new UILabel("Player 2 (Black)");
		lblPlayer2.setBounds(820, 105, 180, 20);
		lblPlayer2.setHorizontalAlignment(UIObject.CENTER);
		lblPlayer2.setFont(bold_font);
		panelSettings.add(lblPlayer2);

		UISelectionBox<String> boxPlayer2 = new UISelectionBox<String>(new String[] { "Player", "AI" });
		boxPlayer2.setBounds(835, 145, 150, 25);
		boxPlayer2.setTextColor(Color.white);
		boxPlayer2.setBackground(Color.black);
		panelSettings.add(boxPlayer2);

		UIButton btnSave = new UIButton("Save Settings");
		btnSave.setBounds(830, 205, 160, 25);
		btnSave.setTextColor(Color.white);
		btnSave.setBackground(Color.black);
		btnSave.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				saveSettings();
			}
		});
		panelSettings.add(btnSave);

		UIPanel panelColor = new UIPanel();
		panelColor.setBounds(820, 250, 180, 80);
		panelColor.setBorder(Color.black);
		display.add(panelColor);

		UIButton btnLightColor = new UIButton("Set Light Color");
		btnLightColor.setBounds(830, 260, 160, 25);
		btnLightColor.setTextColor(Color.white);
		btnLightColor.setBackground(Color.black);
		btnLightColor.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				Color newColor = JColorChooser.showDialog(display.getFrame(), "Choose new light color",
						boardPanel.getLightColor());
				if (newColor != null)
					boardPanel.setLightColor(newColor);
			}
		});
		panelColor.add(btnLightColor);

		UIButton btnDarkColor = new UIButton("Set Dark Color");
		btnDarkColor.setBounds(830, 295, 160, 25);
		btnDarkColor.setTextColor(Color.white);
		btnDarkColor.setBackground(Color.black);
		btnDarkColor.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				Color newColor = JColorChooser.showDialog(display.getFrame(), "Choose new dark color",
						boardPanel.getDarkColor());
				if (newColor != null)
					boardPanel.setDarkColor(newColor);
			}
		});
		panelColor.add(btnDarkColor);

		UIButton btnReset = new UIButton("Reset");
		btnReset.setBounds(820, 780, 180, 30);
		btnReset.setTextColor(Color.white);
		btnReset.setBackground(Color.black);
		btnReset.setClickListener(new ClickListener() {
			@Override
			public void onClick() {
				boardPanel.setBoard(board = Board.create());
			}
		});
		display.add(btnReset);
	}

	private void saveSettings() {
		try {
			Desktop.getDesktop().browse(new URL("https://github.com/DefensivLord/ChessAI").toURI());
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	public void show() {
		display.setVisible(true);
	}
}