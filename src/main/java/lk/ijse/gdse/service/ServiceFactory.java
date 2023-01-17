package lk.ijse.gdse.service;

import lk.ijse.gdse.service.custom.impl.BookServiceImpl;
import lk.ijse.gdse.service.custom.impl.IssueServiceImpl;
import lk.ijse.gdse.service.custom.impl.MemberServiceImpl;

public class ServiceFactory {

    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance(){
        return serviceFactory==null?(serviceFactory=new ServiceFactory()):serviceFactory;
    }

    public <T extends SuperService> T getService(ServiceTypes serviceTypes){
        switch (serviceTypes){
            case MEMBER:
                return (T)new MemberServiceImpl();

            case BOOK:
                return (T)new BookServiceImpl();

            case ISSUE:
                return (T)new IssueServiceImpl();

            default:
                return null;
        }
    }

}
