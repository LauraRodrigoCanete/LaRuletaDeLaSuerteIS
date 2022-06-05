package model;


//Clase envolvente de modelStatus para que el modelo y el game tengan la 
// misma instancia y que cuando se actualice uno el otro también tenga esa actualización
public class Status {
	private ModelStatus _status;
	
	public Status(ModelStatus status) {
		_status = status;
	}
	
	public ModelStatus getStatus() {
		return _status;
	}
	
	public void setStatus(ModelStatus s) {
		_status = s;
	}
}
