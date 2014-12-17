package Dziecioly.zkimnabasen.baza.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import Dziecioly.zkimnabasen.baza.DatabaseManager;

import android.util.Log;

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
				dao = DatabaseManager.getInstance().getDao(daoType);
			} catch (java.sql.SQLException e) {
//				e.printStackTrace();
				Log.d(DatabaseManager.DEBUG_TAG, e.toString());
			}
		}
		return dao;
	}

	public int add(E entity) {
		try {
			return getDao().create(entity);
		} catch (SQLException e) {
//			e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return 0;
	}

	public void update(E entity) {
		try {
			getDao().update(entity);
		} catch (SQLException e) {
//			e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
	}

	public void remove(E entity) {
		try {
			getDao().delete(entity);
		} catch (SQLException e) {
//			e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
	}

	public E find(K key) {
		try {
			return getDao().queryForId(key);
		} catch (SQLException e) {
//			e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;
	}

	public List<E> list() {
		try {
			return getDao().queryForAll();
		} catch (SQLException e) {
//			e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;
	}

}
