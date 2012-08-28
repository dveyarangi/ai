package yarangi.ai.search.game;

import java.util.List;

public abstract class GameState
{
	public abstract boolean isGoal();

	public abstract List <Victory> getVictories();
}
