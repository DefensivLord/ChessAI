package com.chess.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.Board;
import com.chess.move.Move;
import com.chess.move.Move.AttackMove;
import com.chess.move.Move.NormalMove;
import com.gfx.Assets;
import com.main.Utils;

public class King extends Piece {
	private static final int[] CANDIDATE_MOVE_COORDINATES = { -9, -8, -7, -1, 1, 7, 8, 9 };

	private final boolean isCastled;
	private final boolean canCastle;

	public King(int position, Team team, boolean canCastle) {
		this(position, team, false, canCastle, false);
	}

	private King(int position, Team team, boolean movedAtLeastOnce, boolean canCastle, boolean isCastled) {
		super(position, team, PieceType.KING);
		this.isCastled = isCastled;
		this.canCastle = canCastle;
		this.movedAtLeastOnce = movedAtLeastOnce;
		this.texture = team == Team.WHITE ? Assets.white_king : Assets.black_king;
	}

	@Override
	public List<Move> getMoves(Board board) {
		List<Move> moves = new ArrayList<Move>();

		for (int currentOffset : CANDIDATE_MOVE_COORDINATES) {
			if ((Utils.getX(position) == 0 && (currentOffset == -9 || currentOffset == -1 || currentOffset == 7))
					|| (Utils.getX(position) == 7
							&& (currentOffset == 9 || currentOffset == 1 || currentOffset == -7))) {
				// if king is in left- or rightmost column; can't move any further
				continue;
			}

			int currentDestination = position + currentOffset;

			if (Utils.inRange(currentDestination, 0, 63)) {
				Piece pieceAtDestination = board.getPiece(currentDestination);

				if (pieceAtDestination == null) {
					moves.add(new NormalMove(board, this, currentDestination));
				} else {
					if (team != pieceAtDestination.getTeam())
						moves.add(new AttackMove(board, this, currentDestination, pieceAtDestination));
				}
			}
		}

		return moves;
	}

	@Override
	public Piece movePiece(Move move) {
		return new King(move.getPieceDestination(), team, true, false, move.isCastlingMove());
	}

	@Override
	public int positionBonus() {
		return team.kingBonus(position);
	}

	public boolean isCastled() {
		return isCastled;
	}

	public boolean canCastle() {
		return canCastle;
	}
}