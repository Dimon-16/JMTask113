package DAO;

import model.User;

import java.sql.*;
import java.util.List;

public interface UserDAO {
    public List<User> getAllUsers();

    public User getUserById(long id);

    public boolean addUser(User user);

    public boolean deleteUser(long id);

    public boolean updateUser(long id, User user);

    public void dropTable() throws SQLException;

    public void createTable() throws SQLException;
}
