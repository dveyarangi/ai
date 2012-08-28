package yarangi.ai.search.game;

public interface MoveFilter
{

	public static final MoveFilter TRANSPARENT_FILTER = new MoveFilter() {
		public boolean accept(PiecePosition pos, GameState state) 
		{
			return true;
		}
	};
	
	public boolean accept(PiecePosition pos, GameState state); 

}
