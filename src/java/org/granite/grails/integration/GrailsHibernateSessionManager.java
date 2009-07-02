/*
  GRANITE DATA SERVICES
  Copyright (C) 2007-2008 ADEQUATE SYSTEMS SARL

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
  for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.
*/

package org.granite.grails.integration;

import java.io.Serializable;
import java.lang.reflect.Method;

import org.granite.tide.data.JTAPersistenceContextManager;
import org.granite.util.Reflections;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;

/**
 * Responsible for attaching a session with the persistence mangager
 * @author cingram
 *
 */
public class GrailsHibernateSessionManager extends JTAPersistenceContextManager {
	
	private SessionFactory sessionFactory;
	
	
	public GrailsHibernateSessionManager() {
	}

	public GrailsHibernateSessionManager(SessionFactory sf) {
        this.sessionFactory = sf;
	}
    
	
	@Override
    protected Object beginTransaction() {
        return sessionFactory.getCurrentSession().getTransaction(); // We should be in a Spring managed transaction (or another container)
    }
    
	@Override
    protected void commitTransaction(Object tx) throws Exception {
	    // Should not commit: managed by the container
    }
    
	@Override
    protected void rollbackTransaction(Object tx) {
        // Should not rollback: managed by the container
    }

	
	/**
	 * attaches the entity to the JPA context.
	 * @return the attached entity
	 */
	@Override
    public Object mergeEntity(Object entity) {
        return findEntity(entity);
    }
	
    /**
     * Finds the entity with the JPA context.
     * @return the entity with the JPA context.
     */
	@Override
	public Object findEntity(Object entity) {
		if (entity == null)
			return null;
		
		try {
			sessionFactory.getCurrentSession().refresh(entity);
	   	    return entity;
		}
		catch (Exception e) {
			throw new RuntimeException("Could not get id of entity " + entity.getClass(), e);
		}
	}
}
