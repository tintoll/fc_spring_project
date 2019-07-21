package com.study.web;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

import javax.servlet.ServletException;
import java.io.File;

public class Main {
    public static void main(String[] args) throws ServletException, LifecycleException {
        String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        StandardContext ctx = (StandardContext)tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

        // Servlet 3.0 annotation will work
        File additionalWebInfClass = new File("target/classes");
        WebResourceRoot resouces = new StandardRoot(ctx);
        resouces.addPreResources(new DirResourceSet(resouces,"/WEB-INF/classes", additionalWebInfClass.getAbsolutePath(), "/"));
        ctx.setResources(resouces);

        tomcat.start();
        tomcat.getServer().await();

    }
}
