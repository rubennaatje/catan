package model;

public enum PlayerType {
	ROOD,WIT,BLAUW,ORANJE;
	
	public String getCSSClass() {
		switch(this) {
		case ROOD:
	        return "player_red";
	    case WIT:
	        return "player_white";
	    case BLAUW:
	        return "player_blue";
	    case ORANJE:
	        return "player_orange";
	    default:
	    	return "";
		}
    }
	public int getFollowNR() {
		switch(this) {
		case ROOD:
	        return 1;
	    case WIT:
	        return 2;
	    case BLAUW:
	        return 3;
	    case ORANJE:
	        return 4;
	    default:
	    	return 0;
		}
	}
	public String getColorString() {
		switch(this) {
			case ROOD:
		        return "rood";
		    case WIT:
		        return "wit";
		    case BLAUW:
		        return "blauw";
		    case ORANJE:
		        return "oranje";
		    default:
		    	return "";
		}
	}
}
