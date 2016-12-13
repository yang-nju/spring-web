package com.jsc.util;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.AbstractUUIDGenerator;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.tuple.PropertyFactory;

public class CustomerUUIDGenerator extends AbstractUUIDGenerator{

	
	public void t(){
		IdentifierGenerator id = PropertyFactory.buildIdentifierProperty(null, null).getIdentifierGenerator();
		id.generate(null, null);
	}

	public Serializable generate(SessionImplementor session, Object object)
			throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
