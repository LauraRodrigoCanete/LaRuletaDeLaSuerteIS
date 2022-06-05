package model.record;

import org.json.JSONObject;

public class Record {
	private String _playerName;
	private int _score;

	public Record(String first, int second) {
		_playerName = first;
		_score = second;
	}

	public String getPlayerName() {
		return _playerName;
	}

	public int getScore() {
		return _score;
	}
	
	public JSONObject report(){
		JSONObject obj = new JSONObject();
		obj.put("type", "RECORD");
		JSONObject data = new JSONObject();
		data.put("PLAYER", _playerName);
		data.put("SCORE", _score);
		obj.put("data", data);
		return obj;
	}

	public void setFirst(String name) {
		_playerName = name;
	}

	public void setSecond(int points) {
		_score = points;
	}
}
