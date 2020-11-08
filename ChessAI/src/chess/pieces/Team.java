package chess.pieces;

public enum Team {
	white {
		@Override
		public int moveDirection() {
			return -1;
		}
	},
	black {
		@Override
		public int moveDirection() {
			return 1;
		}
	};

	public abstract int moveDirection();
}