package com.chess.move;

public class Position {
	public int x, y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position() {
		this(0, 0);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;

		if (!(other instanceof Position))
			return false;

		Position otherPosition = (Position) other;

		return this.x == otherPosition.x && this.y == otherPosition.y;
	}

	@Override
	public int hashCode() {
		return x * 10 + y;
	}
}