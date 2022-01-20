package dao;

import java.util.List;

import javax.ejb.Local;


@Local
public interface IDaoLocal<T> {
	
	boolean create(T u);
	boolean delete (T u);
	boolean update (T u);
	T getById(int id);
	List<T> getAll();

}
