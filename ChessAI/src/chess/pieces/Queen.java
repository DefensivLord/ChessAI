package chess.pieces;

import java.util.HashSet;
import java.util.Set;

import chess.Board;
import chess.move.Move;
import chess.move.Move.AttackMove;
import chess.move.Move.NormalMove;
import gfx.Assets;

public class Queen extends Piece {
	public Queen(int x, int y, Team team) {
		this(x, y, team, false);
	}

	private Queen(int x, int y, Team team, boolean movedAtLeastOnce) {
		super(x, y, 90, team, PieceType.QUEEN);
		this.movedAtLeastOnce = movedAtLeastOnce;
		this.texture = team == Team.WHITE ? Assets.white_queen : Assets.black_queen;
	}

	@Override
	public Set<Move> getMoves(Board board) {
		Set<Move> moves = new HashSet<Move>();

		Piece currentPiece;

		boolean nDone = false, sDone = false, wDone = false, eDone = false;
		boolean nwDone = false, neDone = false, seDone = false, swDone = false;

		for (int i = 1; i < 8; i++) {
			if (!nDone && this.y - i >= 0) { // up
				currentPiece = board.getPiece(this.x, this.y - i);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x, this.y - i));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x, this.y - i, currentPiece));
					nDone = true;
				} else {
					nDone = true;
				}
			}

			if (!sDone && this.y + i < 8) { // down
				currentPiece = board.getPiece(this.x, this.y + i);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x, this.y + i));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x, this.y + i, currentPiece));
					sDone = true;
				} else {
					sDone = true;
				}
			}

			if (!wDone && this.x - i >= 0) { // left
				currentPiece = board.getPiece(this.x - i, this.y);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x - i, this.y));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x - i, this.y, currentPiece));
					wDone = true;
				} else {
					wDone = true;
				}
			}

			if (!eDone && this.x + i < 8) { // right
				currentPiece = board.getPiece(this.x + i, this.y);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x + i, this.y));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x + i, this.y, currentPiece));
					eDone = true;
				} else {
					eDone = true;
				}
			}

			if (!nwDone && this.x - i >= 0 && this.y - i >= 0) { // northwest / left up
				currentPiece = board.getPiece(this.x - i, this.y - i);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x - i, this.y - i));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x - i, this.y - i, currentPiece));
					nwDone = true;
				} else {
					nwDone = true;
				}
			}

			if (!seDone && this.x + i < 8 && this.y + i < 8) { // southeast / right down
				currentPiece = board.getPiece(this.x + i, this.y + i);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x + i, this.y + i));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x + i, this.y + i, currentPiece));
					seDone = true;
				} else {
					seDone = true;
				}
			}

			if (!swDone && this.x - i >= 0 && this.y + i < 8) { // southwest / left down
				currentPiece = board.getPiece(this.x - i, this.y + i);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x - i, this.y + i));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x - i, this.y + i, currentPiece));
					swDone = true;
				} else {
					swDone = true;
				}
			}

			if (!neDone && this.x + i < 8 && this.y - i >= 0) { // northeast / right up
				currentPiece = board.getPiece(this.x + i, this.y - i);

				if (currentPiece == null) {
					moves.add(new NormalMove(board, this, this.x + i, this.y - i));
				} else if (currentPiece.getTeam() != team) {
					moves.add(new AttackMove(board, this, this.x + i, this.y - i, currentPiece));
					neDone = true;
				} else {
					neDone = true;
				}
			}
		}

		return moves;
	}

	@Override
	public Piece movePiece(Move move) {
		return new Queen(move.getPieceDestinationX(), move.getPieceDestinationY(), team, true);
	}
}