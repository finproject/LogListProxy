package com.example.proxyarraylist;

import com.example.proxyarraylist.annotation.PrintSizeLog;
import com.example.proxyarraylist.service.Cache;
import com.example.proxyarraylist.service.MyService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProxyArrayListApplication {
    @PrintSizeLog
    private List<Cache> listCache = new LinkedList<>();
    @PrintSizeLog
    private List<MyService> listMyService = new ArrayList<>();

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext("com.example.proxyarraylist");
        ProxyArrayListApplication bean =
                context.getBean(ProxyArrayListApplication.class);
        bean.testAddInfoToLists();
    }

    public void testAddInfoToLists() {
        //Intercept method add()
        listMyService.add(new MyService());
        listMyService.add(new MyService());
        listCache.add(new Cache());
        //Intercept method remove
        listMyService.clear();
        listCache.remove(0);
    }
}
