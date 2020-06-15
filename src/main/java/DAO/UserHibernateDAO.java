package DAO;

import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserHibernateDAO implements UserDAO {
    private Session session;
    private SessionFactory sessionFactory;

    public UserHibernateDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        session = sessionFactory.openSession();
        try {
            Query query = session.createQuery("FROM User");
            list = query.list();
        } catch (Exception exc) {
            return list;
        } finally {
            session.close();
        }
        return list;
    }

    public User getUserById(long id) {
        session = sessionFactory.openSession();
        User user = null;
        try {
            Query query = session.createQuery("FROM User WHERE id = : idUser");
            query.setParameter("idUser", id);
            for(User userById : (List<User>) query.list()) {
                user = userById;
            }
        } catch (Exception exc) {
            return user;
        } finally {
            session.close();
        }
        return user;
    }

    public boolean addUser(User user) {
        session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.save(user);
            transaction.commit();
        } catch (Exception exc) {
            transaction.rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public boolean deleteUser(long id) {
        session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Query query = session.createQuery("DELETE FROM User WHERE id = :idUser");
            query.setParameter("idUser", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception exc) {
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public boolean updateUser(long id, User user) {
        session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            Query query = session.createQuery("UPDATE User Set name = :newName, lastName = :newLastName," +
                    "age = :newAge WHERE id = :id");
            query.setParameter("newName", user.getName());
            query.setParameter("newLastName", user.getLastName());
            query.setParameter("newAge", user.getAge());
            query.setParameter("id", id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception exc) {
            return false;
        }
        return false;
    }

    public void dropTable() throws SQLException {

    }

    public void createTable() throws SQLException {

    }
}
