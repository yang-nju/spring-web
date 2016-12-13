package com.jsc.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.type.Type;

/**
 * 基础dao接口
 * 数据库操作CRUD
 * 查询：语句（join，parameter）；返回（对象、map、 ）；分页、fetchsize、cache、config
 * 更新：语句（参数）；批量、参数；索引、性能
 * 
 * @author yangyang
 *
 * @param <T>
 * @param <PK>
 */
public interface GenericDao<T extends Serializable, PK extends Serializable> {

	//--------------------------基础操作-------------------------------------
	
	//根据ID查询
	public T findByID(String id);
	
	//根据属性查询
	public List<T> findByProperty(String propName, Object value);
	
	// 获取全部实体
	public List<T> findall();
	
	//增加实体
	public void save(T entity);
	
	//更新实体
	public void update(T entity);
	
	//增加或更新实体
	public void saveOrUpdate(T entity);
	
	
	//增加或更新集合中的全部实体
	public void saveOrUpdate(Collection<T> entities);
	
	//删除指定的实体
	public void delete(T entity);
	
	
	//----------------------------HQL----------------------------
	
	//使用hql语句检索数据
	public List find(String queryString);
	
	//使用命名的hql语句检索数据
		public List findByNamedQuery(String queryName);
	
	//使用带参hql语句检索数据
	public List find(String queryString, Object[] paras, Type[] types);
	
	//其它带参查询
	
	//使用hql语句检索，返回Iterator
	public Iterator iterate(String queryString);	
	
	
	
	//使用hql语句增加、更新、删除实体
	public int bulkUpdate(String queryString);
	
	//使用带参hql语句增加、更新、删除实体
	public int bulkUpdate(String queryString, Object[] paras, Type[] types);
	
	//其它带参更新
	
	
	
	//关闭Iterator
	public void closeIterator(Iterator it);
	
	
	//--------------------------------------原生sql---------------------------------
	
	//使用原生sql查询
	public List findBySql(String queryString, Object[] paras, Type[] types,Class entityClass);
	
	//使用原生sql更新
	public void updateBySql(String queryString, Object[] paras, Type[] types);
	
	//使用原生sql批量入库
	public void batchInsertBySql(String queryString, List<Object[]> parasList, Type[] types);
	
	
	
}
