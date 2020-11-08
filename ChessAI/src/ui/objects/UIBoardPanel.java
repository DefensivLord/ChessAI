package ui.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import chess.Board;
import chess.Board.Position;
import chess.move.Move;
import chess.move.MoveStatus;
import chess.move.MoveTransition;
import chess.pieces.Piece;
import ui.interfaces.Clickable;
import ui.listeners.MoveExecutionListener;

public class UIBoardPanel extends UIObject implements Clickable {
	private Board board;
	private int pieceWidth, pieceHeight;
	private Piece selectedPiece;
	private List<Position> selectedPiece_movePositions;
	private Set<Move> selectedPiece_moves;
	private MoveExecutionListener meListener;

	public UIBoardPanel() {
		selectedPiece_movePositions = new ArrayList<Position>();
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.lightGray);

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if ((x + y + 1) % 2 == 0)
					g.fillRect(this.x + x * pieceWidth, this.y + y * pieceHeight, pieceWidth, pieceHeight);

				if (board != null) {
					Piece p = board.getPiece(x, y);
					if (p != null) {
						p.render(g, this.x + x * pieceWidth, this.y + y * pieceHeight, pieceWidth, pieceHeight);
					}
				}
			}
		}

		g.setColor(Color.black);
		for (Position p : selectedPiece_movePositions) {
			g.drawRect(this.x + p.x * pieceWidth, this.y + p.y * pieceHeight, pieceWidth, pieceHeight);
		}
	}

	@Override
	public void onMouseMove(MouseEvent e) {

	}

	@Override
	public void onMouseRelease(MouseEvent e) {
		if (board == null)
			return;

		int clickedX = e.getX();
		int clickedY = e.getY();

		int pieceX = (clickedX - this.x) / pieceWidth;
		int pieceY = (clickedY - this.y) / pieceHeight;

		Piece clickedPiece = board.getPiece(pieceX, pieceY);

		if (clickedPiece != null && clickedPiece.getTeam() == board.getCurrentPlayer().getTeam()) {
			selectedPiece = clickedPiece;
			selectedPiece_movePositions.clear();
			selectedPiece_moves = selectedPiece.getMoves(board);
			for (Move m : selectedPiece_moves) {
				selectedPiece_movePositions.add(new Position(m.getPieceDestinationX(), m.getPieceDestinationY()));
			}
		} else if (selectedPiece_movePositions.contains(new Position(pieceX, pieceY))) {
			for (Move m : selectedPiece_moves) {
				if (m.getPieceDestinationX() == pieceX && m.getPieceDestinationY() == pieceY) {
					MoveTransition mt = board.getCurrentPlayer().makeMove(m);
					
					if (meListener != null)
						meListener.moveExecuted(mt);
					
					if (mt.getMoveStatus() == MoveStatus.DONE) {
						this.board = mt.getNewBoard();

						selectedPiece = null;
						selectedPiece_movePositions.clear();
						selectedPiece_moves.clear();
						return;
					}
				}
			}
		} else {
			selectedPiece = null;
			selectedPiece_movePositions.clear();
			selectedPiece_moves.clear();
		}
	}

	// ===== Getter ===== \\
	public Board getBoard() {
		return board;
	}

	public MoveExecutionListener getMoveExecutionListener() {
		return meListener;
	}

	// ===== Setter ===== \\
	@Override
	public void setBounds(int x, int y, int width, int height) {
		pieceWidth = width / 8;
		pieceHeight = height / 8;

		super.setBounds(x, y, width, height);
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setMoveExecutionListener(MoveExecutionListener meListener) {
		this.meListener = meListener;
	}
}