package model.record;

import java.io.*;
import java.util.*;

import model.*;

public class Records {
	private List<Record> _records;
	private int _size;
	private Status _status;
	
	private List<RDLSObserver> _observers;
	
	public Records(List<Record> records, List<RDLSObserver> observers, Status status) {
		_records = records;
		_size = records.size();
		_observers = observers;
		_status = status;
	}
	
	public List<Record> getRecords(){
		return Collections.unmodifiableList(_records);
	}

	public void update(Player player) {
		boolean recordInsertado = false;
		if(player.getPoints() > _records.get(0).getScore()) {
			for(RDLSObserver o : _observers) {
				o.onNewRecord();
			}
		}
		
		for(int i = 0; i < _size; ++i) {
			if(player.getPoints() > _records.get(i).getScore()) {
				recordInsertado = true;
				for(int j = _size - 1; j > i; --j) {
					_records.set(j, _records.get(j - 1));
				}
				_records.set(i, new Record(player.getName(), player.getPoints()));
				break;
			}
		}
		
		if(recordInsertado) {
			OutputStream out = null;
			try {
				out = new FileOutputStream(new File("examples/records.json"));
			} catch (FileNotFoundException e) {
				for(RDLSObserver o : _observers) {
					o.notify(_status.getStatus(), "[ERROR] : No se ha podido guardar el record");
				}
			}
			PrintStream p = new PrintStream(out);
			p.println("{\"RECORDS\" : [");
			for(int j = 0; j < _size; ++j) {
				p.print(_records.get(j).report());
				p.println(",");
			}
			p.println("]}");
		}
	}
}
