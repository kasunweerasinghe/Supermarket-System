package lk.ijse.pos.dao;
import lk.ijse.pos.dao.custom.impl.*;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        if(daoFactory==null){
            daoFactory= new DAOFactory();
        }
        return daoFactory;
    }

    public enum DAOType{
        CUSTOMER,ITEM,ORDER,ORDERDETAIL,QUERYDAO
    }

    public SuperDAO getDAO(DAOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDERDETAIL:
                return new OrderDetailDAOImpl();
            case QUERYDAO:
                return new QueryDAOImpl();
            default:
                return null;

        }

    }



}
