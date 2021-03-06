package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.move.Move;
import com.chess.move.Move.AttackMove;
import com.chess.move.Move.NormalMove;
import com.gfx.Assets;
import com.main.Utils;

public class Queen extends Piece {
	private static final int[] CANDIDATE_MOVE_COORDIANTES = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public Queen(int position, Team team) {
		this(position, team, false);
	}

	public Queen(int position, Team team, boolean movedAtLeastOnce) {
		super(position, team, PieceType.QUEEN, movedAtLeastOnce);
		this.texture = team == Team.WHITE ? Assets.white_queen : Assets.black_queen;
	}

	@Override
	public List<Move> getMoves(Board board) {
		final List<Move> moves = new ArrayList<Move>();

		for (int currentOffset : CANDIDATE_MOVE_COORDIANTES) {
			int currentDestination = position;

			while (Utils.inRange(currentDestination, 0, 63)) {
				if ((Utils.getX(currentDestination) == 0
						&& (currentOffset == -9 || currentOffset == -1 || currentOffset == 7))
						|| (Utils.getX(currentDestination) == 7
								&& (currentOffset == -7 || currentOffset == 1 || currentOffset == 9)))
					break; // if queen is in left- or rightmost column; can't move any further

				currentDestination += currentOffset;

				if (!Utils.inRange(currentDestination, 0, 63)) // if queen would move off the board
					break;

				final Piece pieceAtDestination = board.getPiece(currentDestination);

				if (pieceAtDestination != null) { // if a piece is obstructing the path
					if (team != pieceAtDestination.getTeam())
						moves.add(new AttackMove(board, this, currentDestination, pieceAtDestination));

					break;
				}

				moves.add(new NormalMove(board, this, currentDestination));
			}
		}

		return moves;
	}

	@Override
	public Piece movePiece(Move move) {
		return Utils.getMovedQueen(team, move.getPieceDestination());
	}

	@Override
	public int positionBonus() {
		return team.queenBonus(position);
	}
}