package service;

import DAO.UserHibernateDAO;
import DAO.UserJdbcDAO;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import util.DBHelper;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class UserService {

    public static UserService userService;

    private UserHibernateDAO dao = getUserDAO();
    private SessionFactory sessionFactory;

    private UserService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService(DBHelper.getSessionFactory());
        }
        return userService;
    }



    public boolean updateTable(long id, User user) {
        User oldUser = dao.getUserById(id);
        if (oldUser != null) {
            dao.updateUser(id, user);
        } else {
            return false;
        }
        return true;
    }

    public boolean deleteUser(long id) {
        User oldUser = dao.getUserById(id);
        if (oldUser != null) {
            dao.deleteUser(id);
        } else {
            return false;
        }
        return true;
    }

    public List<User> getAllUsers(){
        return dao.getAllUsers();
    }

    public boolean addUser(User user) {
        return dao.addUser(user);
    }

   /* private static Connection getConnection() {
        try {
            DriverManager.registerDriver((Driver) Class.forName("com.mysql.jdbc.Driver").newInstance());

            StringBuilder url = new StringBuilder();
            url.append("jdbc:mysql://").
                append("localhost:").
                append("3306/").
                append("bankclient?").
                append("user=root&").
                append("password=root");

            Connection connection = DriverManager.getConnection(url.toString());
            return connection;
        } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException exc) {
            exc.printStackTrace();
            throw new IllegalStateException();
        }
    }*/

    private UserHibernateDAO getUserDAO() {
        return new UserHibernateDAO(sessionFactory);
    }
}
