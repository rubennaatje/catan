package model;
//enum, stores tiletypes
public enum TileType {
	B,E,G,H,W,X;
	public String  getCssClass() {
		switch(this) {
		case B:
	        return "baksteen";
	    case H:
	        return "hout";
	    case G:
	        return "graan";
	    case W:
	        return "wol";
	    case E:
	        return"erts";
	    case X:
	        return "woestijn";
	    default:
	    	return "";
		}
    }

}
