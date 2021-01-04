package algorithm;

import chess.Board;
import chess.move.Move;
import chess.pieces.Piece;
import chess.pieces.Team;
import chess.player.Player;

public abstract class BoardEvaluator {
	public abstract int evaluate(Board board, int depth);

	protected static int pieceScore(Player player) {
		int score = 0;

		for (Piece p : player.getActivePieces()) {
			score += p.getValue();
		}

		if (player.getTeam() == Team.BLACK)
			score *= -1;

		return score;
	}

	protected static int mobilityScore(Player player, float mobilityBonus) {
		float score = player.getLegalMoves().size() * mobilityBonus;

		if (player.getTeam() == Team.BLACK)
			score *= -1;

		return Math.round(score);
	}

	protected static int attackScore(Player player, float attackBonus) {
		float score = 0;

		for (Move m : player.getLegalMoves()) {
			if (m.isAttackMove())
				score += attackBonus;
		}

		if (player.getTeam() == Team.BLACK)
			score *= -1;

		return Math.round(score);
	}

	protected static int kingEscapeScore(Player player, Board board, float kingEscapeBonus) {
		float score = player.getKing().getMoves(board).size() * kingEscapeBonus;

		if (player.getTeam() == Team.BLACK)
			score *= -1;

		return Math.round(score);
	}
}