package yarangi.ai.search.game;

import java.util.Map;


public abstract class Game
{
	public abstract GameState getCurrentState();

	public abstract Map<PlayerMark, PiecePosition> getMoves(MoveFilter transparentFilter);

	public abstract PlayerMark getCurrentPlayer();

	public abstract void move(PiecePosition pick);
}
