package org.mcbx.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mcbx.entity.Authors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mcbx.entityinterface.AuthorsInterface;

@Service
public class AuthorsService implements AuthorsInterface {

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction trx;

    public void testCallMethod() {
        System.out.println("IM CALLED BY CONTROLLER CLASS");
        System.out.println();

        Authors a1 = new Authors();
        Authors a2 = new Authors();
        Authors a3 = new Authors();
        Authors a4 = new Authors();
        Authors a5 = new Authors();
//------------------------------------------------------------------------------ CREATE    
        beginTransaction();
        session.persist(a1);
        session.persist(a2);
        session.persist(a3);
        session.persist(a4);
        session.persist(a5);
        trx.commit();
//------------------------------------------------------------------------------ UPDATE  
        beginTransaction();
        a1.setFirstname("ID: " + a1.getIdauthors() + " is UPDATED");
        session.update(a1);
        trx.commit();
//------------------------------------------------------------------------------ DELETE
        beginTransaction();
        session.delete(a5);
        trx.commit();
//------------------------------------------------------------------------------ READ
    }

    private void beginTransaction() {
        session = sessionFactory.openSession();
        trx = session.beginTransaction();
    }

//------------------------------------------------------------------------------ AuthorsInterface Interface    
    @Override
    public void create() {
        System.out.println("create Implementation");
    }

    @Override
    public void delete() {
        System.out.println("delete Implementation");
    }

    @Override
    public void update() {
        System.out.println("update Implementation");
    }

}
