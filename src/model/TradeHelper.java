package model;

import java.util.HashMap;

//handles logic for trade, will be done later
public class TradeHelper {
	private TradeHelper() {

	}

	public static void registerTrade(String spelId, PlayerModel player, HashMap<TileType, Integer>[] tradeSuggestion) {
		// for testing
		for (HashMap<TileType, Integer> hashMap : tradeSuggestion) {
			System.out.println(hashMap.get(TileType.H));
			System.out.println(hashMap.get(TileType.W));
			System.out.println(hashMap.get(TileType.G));
			System.out.println(hashMap.get(TileType.B));
			System.out.println(hashMap.get(TileType.E));
		}
	}
}
