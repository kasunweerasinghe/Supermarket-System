package bo;

import bo.custom.impl.CustomerBOImpl;
import bo.custom.impl.ItemBOImpl;
import bo.custom.impl.PurchaseOrderBOImpl;

public class BOFactory {
    public static BOFactory boFactory;


    private BOFactory(){

    }



    public static BOFactory getBoFactory(){
        if(boFactory==null){
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOType{
        CUSTOMER,ITEM,PURCHASE_ORDER
    }

    public SuperBO getBO(BOType type){
        switch (type){
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PURCHASE_ORDER:
                return new PurchaseOrderBOImpl();
            default:return null;
        }
    }


}
