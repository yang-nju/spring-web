package com.jsc.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.hibernate.id.factory.IdentifierGeneratorFactory;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Repository;
import org.hibernate.type.*;

import com.jsc.util.GenericDao;



/**
 * hibernate基础dao操作封装
 * 
 * 问题：
 * 1、query.iterator()如何使用，如何关闭
 * 2、queryName如何使用
 * 3、分页如何使用:仍然需要自己实现分页类，只是利用有选择的查询条件
 * 4、原生sql如何用
 * 5、原生sql 与criteria
 * @author yangyang
 *
 */
@Repository
public class GenericHibernateDao<T extends Serializable, PK extends Serializable> implements GenericDao<T, PK>{

	
	private Class<T> entityClass;
	
	public GenericHibernateDao(){
		
		try{
			this.entityClass = null;
//			Class c = getClass();
//			java.lang.reflect.Type t = c.getGenericSuperclass();
//			if(t instanceof ParameterizedType){
//				java.lang.reflect.Type[] p = ((ParameterizedType)t).getActualTypeArguments();
//				this.entityClass = (Class<T>)p[0];
//			}
//			Method m = getClass().getMethod("findAll", GenericHibernateDao.class);
			
//			
//			java.lang.reflect.Type[] p = ((ParameterizedType)t).getActualTypeArguments();
//			this.entityClass = (Class<T>)p[0];
			
//			Method method  =c.getMethod("saveOrUpdate", Collection.class);
//			java.lang.reflect.Type[] p1 = method.getGenericParameterTypes();
//			this.entityClass = (Class)p1[0];
			
//			this.entityClass = (Class<T>)GenericTypeResolver.resolveTypeArgument(getClass(), GenericHibernateDao.class);
			
			
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		
		
		
	}
	
	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	
	public List<T> findall() {
//		log.debug("finding all Users instances");
		try {
			Criteria criteria = getCurrentSession().createCriteria(entityClass);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
//			prepareCriteria(criteria);
//			criteria.setCacheable(true);
//			criteria.setCacheRegion(null);
//			criteria.setFetchSize(100);
//			criteria.setMaxResults(10);
//			criteria.setTimeout(10000);
			return criteria.list();
		} catch (RuntimeException re) {
//			log.error("find all failed", re);
			throw re;
		}
	}
	
	public T findByID(String id) {
		Object obj = getCurrentSession().get(entityClass, id);
		return (obj != null ? (T)obj : null);
	}

	public List<T> findByProperty(String propName, Object value) {
		String queryString = "from " +  entityClass.getName() + " as model where model."
				+ value + "= ?";
		Query queryObject = getCurrentSession().createQuery(queryString);
		queryObject.setParameter(0, value);
		return queryObject.list();
	}

	public void save(T entity) {
		getCurrentSession().save(entity);
	}

	public void update(T entity) {
		getCurrentSession().update(entity);
	}

	public void saveOrUpdate(T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	public void saveOrUpdate(Collection<T> entities) {
		for(T t : entities){
			getCurrentSession().saveOrUpdate(t);
		}
	}

	public void delete(T entity) {
		getCurrentSession().delete(entity);
	}

	public int bulkUpdate(String queryString) {
		Query query = getCurrentSession().createQuery(queryString);
		return query.executeUpdate();
	}

	public int bulkUpdate(String queryString, Object[] paras, Type[] types) {
		Query query = getCurrentSession().createQuery(queryString);
		query.setParameters(paras, types);
		return query.executeUpdate();
	}

	public List find(String queryString) {
		Query query = getCurrentSession().createQuery(queryString);
		return query.list();
	}

	/**
	 * 根据具体查询再做调整
	 */
	public List find(String queryString, Object[] paras, Type[] types) {
		Query query = getCurrentSession().createQuery(queryString);
		if(types != null && paras != null)
			query.setParameters(paras, types);	
//		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		return query.list();
	}

	/**
	 * 如何使用queryName
	 */
	public List findByNamedQuery(String queryName) {
		
		Query query = getCurrentSession().getNamedQuery(queryName);
		return query.list();
	}

	public Iterator iterate(String queryString) {
		Query query = getCurrentSession().createQuery(queryString);
		
		return query.iterate();
	}

	
	/**
	 * iterator如何关闭？？
	 */
	public void closeIterator(Iterator it){
		
	}

	

	public List findBySql(String queryString, Object[] paras, Type[] types,Class resultClass) {
		SQLQuery query = getCurrentSession().createSQLQuery(queryString);
		if(types != null && paras != null)
			query.setParameters(paras, types);	
		query.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		query.addEntity(resultClass);
		
		//以下两项用于分页操作时取特定数据；现在可考虑redis等内存数据库进行分页
//		query.setFirstResult(1);
//		query.setMaxResults(20);
		
		return query.list();
	}

	public void updateBySql(String queryString, Object[] paras, Type[] types) {
		SQLQuery query = getCurrentSession().createSQLQuery(queryString);
		if(paras != null && types!= null)
			query.setParameters(paras, types);
		query.executeUpdate();
		
	}

	public void batchInsertBySql(String queryString, List<Object[]> parasList,
			Type[] types) {
		
		Session session = sessionFactory.getCurrentSession();
		try{
			final Transaction trans = session.beginTransaction();
			final String sql = queryString;
			final List<Object[]> para = parasList;
			final Type[] localType = types;
			session.doReturningWork(new ReturningWork<String>() {

				public String execute(Connection connection) throws SQLException {
					PreparedStatement stmt=connection.prepareStatement(sql);

					setParas(stmt, para, localType);
					
				    stmt.executeBatch();
				    trans.commit();
				    return "";
				}
			});
		}
		catch(Exception e){
			throw new RuntimeException(e);
		}
		finally{
//			session.close();
		}
		
		
	}

	
	private void setParas(PreparedStatement stmt, List<Object[]> parasList, Type[] types) throws SQLException{
		for(Object[] paras : parasList){
			for(int i = 0; i<types.length; i++){
				setPara(stmt,i+1, paras[i],types[i]);
			}
			stmt.addBatch();
		}
	}
	
	
	private void setPara(PreparedStatement stmt,int index, Object value, Type type) throws SQLException{
		if(type instanceof StringType){
			stmt.setString(index, value.toString());
		}
		else if(type instanceof IntegerType){
			stmt.setInt(index, Integer.parseInt(value.toString()));
		}
//		else if(type instanceof LongType){
//			
//		}
//		else if(type instanceof DoubleType){
//			
//		}
//		else if(type instanceof DateType){
//			
//		}
//		else if(type instanceof BooleanType){
//			
//		}
//		else if(type instanceof BlobType){
//			
//		}
		else {
			throw new RuntimeException("批量插入：传入了不支持的参数类型");
		}
		
		
		
	}
	
	
}
