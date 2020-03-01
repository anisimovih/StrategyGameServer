package atlas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import atlas.map.Tile;
import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.SFSArray;

public final class TrisGameBoard
{
	private static final int BOARD_SIZE = 12;
	private final Tile[][] board;
	private int winner = 0;
	
	public TrisGameBoard()
    {
		board = new Tile[BOARD_SIZE][BOARD_SIZE];
		reset();
	}

	public void reset()
	{
		winner = 0;

		for (int y = 1; y < BOARD_SIZE; y++)
		{
			Tile[] boardRow = board[y];

			for (int x = 1; x < BOARD_SIZE; x++)
			{
				boardRow[x] = Tile.EMPTY;
			}
		}
	}
}
