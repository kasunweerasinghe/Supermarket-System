package lk.ijse.pos.dao;

import java.sql.*;
import java.util.ArrayList;

public interface CrudDAO<T, ID> extends SuperDAO{

     ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

     boolean save(T dto) throws SQLException, ClassNotFoundException;

     boolean update(T dto) throws SQLException, ClassNotFoundException;

     T search(ID id) throws SQLException, ClassNotFoundException;

     boolean exists(ID id) throws SQLException, ClassNotFoundException;

     boolean delete(ID id) throws SQLException, ClassNotFoundException;

     String generateNewID() throws SQLException, ClassNotFoundException;


}
