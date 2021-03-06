package Dziecioly.zkimnabasen.baza.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import Dziecioly.zkimnabasen.baza.DatabaseManager;
import android.content.Entity;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;

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
				// e.printStackTrace();
				Log.d(DatabaseManager.DEBUG_TAG, e.toString());
			}
		}
		return dao;
	}

	// insert
	public E add(E entity) {
		try {
			return getDao().createIfNotExists(entity);
		} catch (SQLException e) {
			// e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;
	}

	// update
	public E update(E entity) {
		remove(entity);
		return add(entity);
	}

	// delete
	public void remove(E entity) {
		try {
			getDao().delete(entity);
		} catch (SQLException e) {
			// e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
	}

	// select po id
	public E find(K key) {
		try {
			return getDao().queryForId(key);
		} catch (SQLException e) {
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;
	}

	// select po jednej kolumnie
	public List<E> findByColumnName(String columnName, Object value) {
		try {
			return getDao().queryBuilder().where().eq(columnName, value)
					.query();
		} catch (SQLException e) {
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;
	}

	// select wszystko
	public List<E> list() {
		try {
			return getDao().queryForAll();
		} catch (SQLException e) {
			// e.printStackTrace();
			Log.d(DatabaseManager.DEBUG_TAG, e.toString());
		}
		return null;
	}

}
