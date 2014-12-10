package Dziecioly.zkimnabasen.baza.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import Dziecioly.zkimnabasen.baza.DatabaseManager;

import com.j256.ormlite.dao.Dao;

public class GenericDao<E, K> {

	private E entity;
	private Dao<E, K> dao;
	protected Class<? extends E> daoType;

	@SuppressWarnings("unchecked")
	public GenericDao() {
		daoType = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public Dao<E, K> getDao() {
		if (null == entity) {
			try {
				dao = DatabaseManager.getInstance().getHelper().getDao(daoType);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		}
		return dao;
	}

	public int add(E entity) {
		try {
			return getDao().create(entity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void update(E entity) {
	}

	public void remove(E entity) {
	}

	public E find(K key) {
		return null;
	}

	public List<E> list() {
		return null;
	}

}
