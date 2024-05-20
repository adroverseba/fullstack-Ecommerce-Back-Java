package com.sebadev.ecommerce;

import java.util.ArrayList;
import java.util.List;

public class helperFunction {
    public static void main(String[] args) {
        System.out.println("aca estoy");
        List<String> entityClasses = new ArrayList<>();

        entityClasses.add("Customer");
        entityClasses.add("Product");
        entityClasses.add("Order");


        String[] domainTypes = entityClasses.toArray(new String[0]);

        System.out.println(domainTypes.length);
        for (String tempDomainTypes: domainTypes){
            System.out.println(tempDomainTypes);
        }
    }
}
