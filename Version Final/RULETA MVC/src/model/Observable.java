package model;

//Interfaz del modelo en el patrón MVC 
public interface Observable<T> {
	void addObserver(T o);
	void removeObserver(T o);
}
