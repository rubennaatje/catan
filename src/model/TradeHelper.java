package model;

import java.util.HashMap;

//handles logic for trade, will be done later
public class TradeHelper {
	private TradeHelper() {
		
	}

	public static void registerTrade(String spelId, PlayerModel player, HashMap<String, Integer>[] tradeSuggestion) {
		//for testing
		for (HashMap<String, Integer> hashMap : tradeSuggestion) {
			System.out.println(hashMap.get("Hout"));
			System.out.println(hashMap.get("Wol"));
			System.out.println(hashMap.get("Graan"));
			System.out.println(hashMap.get("Baksteen"));
			System.out.println(hashMap.get("Erts"));
		}
	}
}
