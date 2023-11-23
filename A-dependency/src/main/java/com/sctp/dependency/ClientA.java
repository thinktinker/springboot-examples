package com.sctp.dependency;

public class ClientA implements Client {
    Service service;

    public ClientA(Service svc){   // constructor injection
        this.service = svc;
    }
    public void doSomething(){
        String info = service.getInfo();
        System.out.println(info);
    }
}
