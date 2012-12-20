package com.web.action.report.htreport.util;

import org.springframework.beans.BeansException;   
import org.springframework.beans.factory.BeanFactory;   
import org.springframework.beans.factory.BeanFactoryAware;   
  
public class SpringBeanUtil implements BeanFactoryAware {   
    private static BeanFactory beanFactory = null;    
    private static SpringBeanUtil beanUtil = null;   
  
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {   
        this.beanFactory=beanFactory;   
    }   
   
    public  BeanFactory getBeanFactory() {   
        return beanFactory;   
    }   
       
    public static SpringBeanUtil getInstance() {   
        if (beanUtil == null)   
            beanUtil = (SpringBeanUtil) beanFactory.getBean("beanUtil");   
        return beanUtil;   
    }   
       
    public static Object getBean(String beanName) {   
        return beanFactory.getBean(beanName);   
    }   
   
    public static Object getBean(String beanName, Class clazz) {   
        return beanFactory.getBean(beanName, clazz);   
    }   
  
}  