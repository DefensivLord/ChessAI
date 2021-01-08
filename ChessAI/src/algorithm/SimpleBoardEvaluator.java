package algorithm;

import chess.Board;
import chess.player.Player;

public class SimpleBoardEvaluator extends BoardEvaluator {
	protected static final int MOBILITY_BONUS = 10;
	protected static final int ATTACK_BONUS = 5;
	protected static final int KING_ESCAPE_BONUS = 1;

	@Override
	public int evaluate(Board board, int depth) {
		return score(board.getWhitePlayer(), board, depth) + score(board.getBlackPlayer(), board, depth);
	}

	protected static int score(Player player, Board board, int depth) {
		return pieceScore(player) + mobilityScore(player, MOBILITY_BONUS) + attackScore(player, ATTACK_BONUS)
				+ kingEscapeScore(player, board, KING_ESCAPE_BONUS);
	}
}