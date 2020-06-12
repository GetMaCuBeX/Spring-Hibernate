package org.mcbx.database.relationship.test;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private static final Logger LOG = Logger.getLogger(EmployeeService.class.getName());

    @Autowired
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction trx;

    public void createEmployee() {
        try {
//------------------------------------------------------------------------------ 1
//            batchProcessing_UPDATE();
//------------------------------------------------------------------------------ 2 CREATE   [HIBERNATE QUERY LANGUAGE]
//            Address a1 = new Address("Davao City");
//            Employee e1 = new Employee("Kenneth Mabandos", a1);
//
//            trxBeginTransaction();
//            session.save(a1);
//            session.save(e1);
//            trx.commit();
//            session.close();
//
//            Address a2 = new Address("Mati City");
//            Employee e2 = new Employee("Reysian Pan", a2);
//
//            trxBeginTransaction();
//            session.save(a2);
//            session.save(e2);
//            trx.commit();
//            session.close();
//------------------------------------------------------------------------------ 3  READ     [HIBERNATE QUERY LANGUAGE]
//            getRecordsWithPagination();
//------------------------------------------------------------------------------ 4  UPDATE   [HIBERNATE QUERY LANGUAGE]
//            updateQuery();
//------------------------------------------------------------------------------ 5  DELETE   [HIBERNATE QUERY LANGUAGE]
//            deleteQuery();
//------------------------------------------------------------------------------ 6  QUERY    [HIBERNATE QUERY LANGUAGE]
//            getValueFromQuery();
//------------------------------------------------------------------------------ 7  READ     [HIBERNATE CRITERIA QUERY LANGUAGE] Criteria.Class ;https://www.javatpoint.com/hcql
//            getRecordsAsClassArgument();
//------------------------------------------------------------------------------ 8  READ     [HIBERNATE CRITERIA QUERY LANGUAGE] Criteria.Class
//            getRecordsAsClassArgumentWithPagination();
//------------------------------------------------------------------------------ 9  READ     [HIBERNATE CRITERIA QUERY LANGUAGE] With Order.Class
//            getRecordsAsClassArgumentSortOrder();
//------------------------------------------------------------------------------ 10 READ     [HIBERNATE CRITERIA QUERY LANGUAGE] With Projection.Class, PRINT DATA name, COLUMN Property Only
//            getRecordsAsClassArgumentWithProjection();
//------------------------------------------------------------------------------ 10 READ     [HIBERNATE CRITERIA QUERY LANGUAGE] With Restrictions.Class
//            getRecordsAsClassArgumentWithRestrictions();
        } catch (HibernateException e) {
            System.err.println(e.getMessage());
        }
    }

    private void trxBeginTransaction() {
        session = sessionFactory.openSession();
        trx = session.beginTransaction();
    }

//------------------------------------------------------------------------------ BATCH PROCESSING
    /*https://www.tutorialspoint.com/hibernate/hibernate_batch_processing.htm*/
    private void batchProcessing_INSERT() {
        trxBeginTransaction();
        try {
            for (int i = 0; i < 100000; i++) {
                Address a1 = new Address("Davao City");
                session.save(a1);
                if (i % 50 == 0) { // Same as the JDBC batch size properties
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
            trx.commit();
        } catch (HibernateException e) {
            if (trx != null) {
                trx.rollback();
            }
            LOG.warning("HibernateException");
        } finally {
            session.close();
        }
    }

    private void batchProcessing_UPDATE() {
        trxBeginTransaction();
        ScrollableResults employeeCursor = session.createQuery("SELECT e FROM Employee e").scroll();
        int count = 0;
        try {
            while (employeeCursor.next()) {
                Employee employee = (Employee) employeeCursor.get(0);
                //employee.updateEmployee();
                employee.setName("It works lagi jud");
                session.update(employee);
                if (++count % 50 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            trx.commit();
        } catch (HibernateException e) {
            if (trx != null) {
                trx.rollback();
            }
            LOG.warning("HibernateException");
        } finally {
            session.close();
        }

    }

    private void batchProcessing_READ() {
        try {
            trxBeginTransaction();
            List employees = session.createQuery("SELECT e FROM Employee e").list();
            for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
                Employee employee = (Employee) iterator.next();
                System.out.println(employee.toString());
            }
            trx.commit();
        } catch (HibernateException e) {
            if (trx != null) {
                trx.rollback();
            }
            LOG.warning("HibernateException");
        } finally {
            session.close();
        }
    }

    private void batchProcessing_DELETE() {
        trxBeginTransaction();
        try {
            Employee employee = (Employee) session.get(Employee.class, 1);
            session.delete(employee);
            trx.commit();
        } catch (HibernateException e) {
            if (trx != null) {
                trx.rollback();
            }
            LOG.warning("HibernateException");
        } finally {
            session.close();
        }
    }

//------------------------------------------------------------------------------ READ
    private void getRecordsWithPagination() {
        trxBeginTransaction();
        Query query = session.createQuery("SELECT e FROM Employee e");
        query.setFirstResult(2); // Get records starts at this number
        query.setMaxResults(3); // Get records with maximum number
        List employees = query.list();
        for (Iterator iterator = employees.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            System.out.println(employee.toString());
        }
        session.close();
    }

//------------------------------------------------------------------------------ UPDATE
    private void updateQuery() {
        trxBeginTransaction();
        String query = "UPDATE Employee set name=:n WHERE id=:i";
        Query q = session.createQuery(query);
        q.setParameter("n", "Change Name");
        q.setParameter("i", 31);

        int status = q.executeUpdate(); // if return value is, [0] update is not possible. [1] possible update.
        System.out.println(status);
        trx.commit();
        session.close();
    }

//------------------------------------------------------------------------------ DELETE
    private void deleteQuery() {
        trxBeginTransaction();
        String query = "DELETE FROM Employee WHERE id=:i";
        Query q = session.createQuery(query);
        q.setParameter("i", 42);
        int status = q.executeUpdate(); // if return value is, [0] update is not possible. [1] possible update.
        System.out.println(status);
        trx.commit();
        session.close();
    }

//------------------------------------------------------------------------------ GET VALUE FROM QUERY  Aggregate functions  You may call avg(), min(), max(), count() etc.
    private void getValueFromQuery() {
        trxBeginTransaction();
        String query1 = "SELECT COUNT(e) FROM Employee e";
        String query2 = "SELECT COUNT(name) FROM Employee";
        Query q = session.createQuery(query2);
        List<Integer> list = q.list();
        System.out.println(list.get(0));
        session.close();
    }

//------------------------------------------------------------------------------ GET RECORDS FROM Class Argument
    private void getRecordsAsClassArgument() {
        trxBeginTransaction();
        Criteria c = session.createCriteria(Employee.class); //passing Class class argument  
        List list = c.list();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            System.out.println(employee.toString());
        }
        session.close();
    }

    private void getRecordsAsClassArgumentWithPagination() {
        trxBeginTransaction();
        Criteria c = session.createCriteria(Employee.class); //passing Class class argument  
        c.setFirstResult(2); // Get records starts at this number
        c.setMaxResults(3); // Get records with maximum number
        List list = c.list();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            System.out.println(employee.toString());
        }
        session.close();
    }

//------------------------------------------------------------------------------ GET RECORDS FROM Class Argument Ascending, Descending ORDER
    private void getRecordsAsClassArgumentSortOrder() {
        trxBeginTransaction();
        Criteria c = session.createCriteria(Employee.class); //passing Class class argument  
        c.addOrder(Order.asc("name"));
        //c.addOrder(Order.desc("name"));
        List list = c.list();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            System.out.println(employee.toString());
        }
        session.close();
    }

//------------------------------------------------------------------------------ GET RECORDS FROM Class Argument WITH Projection
    private void getRecordsAsClassArgumentWithProjection() {
        trxBeginTransaction();
        Criteria c = session.createCriteria(Employee.class); //passing Class class argument  
        c.setProjection(Projections.property("name"));
        List list = c.list();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            //String propertyValue = (String) iterator.next();
            System.out.println((String) iterator.next());
        }
        session.close();
    }

//------------------------------------------------------------------------------ GET RECORDS FROM Class Argument WITH Restrictions
    private void getRecordsAsClassArgumentWithRestrictions() {
        /*
 lt         (String propertyName,   Object value) sets the less than constraint to the given property.
 le         (String propertyName,   Object value) sets the less than or equal constraint to the given property.
 gt         (String propertyName,   Object value) sets the greater than constraint to the given property.
 ge         (String propertyName,   Object value) sets the greater than or equal than constraint to the given property.
 ne         (String propertyName,   Object value) sets the not equal constraint to the given property.
 eq         (String propertyName,   Object value) sets the equal constraint to the given property.
 like       (String propertyName,   Object value) sets the like constraint to the given property.
 between    (String propertyName,   Object low, Object high) sets the between constraint.
         */
        trxBeginTransaction();
        Criteria c = session.createCriteria(Employee.class);
        c.add(Restrictions.like("name", "Kenneth Mabandos")); // where property name like given value
        List list = c.list();
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            Employee employee = (Employee) iterator.next();
            System.out.println(employee.toString());
        }
        session.close();
    }

}
