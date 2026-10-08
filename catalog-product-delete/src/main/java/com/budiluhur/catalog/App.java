package com.budiluhur.catalog;

import com.budiluhur.catalog.servlet.ProductServlet;
import com.budiluhur.catalog.servlet.CategoryServlet; 

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class App {

    public static void main(String[] args) throws Exception {

        System.out.println(
                "Menjalankan Embedded Tomcat Server..."
        );

        Tomcat tomcat = new Tomcat();

        tomcat.setPort(8080);

        tomcat.getConnector();

        Context ctx = tomcat.addContext(
                "",
                new File(".").getAbsolutePath()
        );

        Tomcat.addServlet(
                ctx,
                "ProductServlet",
                new ProductServlet()
        );

        ctx.addServletMappingDecoded(
                "/products",
                "ProductServlet"
        );

        // TAMBAHAN UNTUK CATEGORY
        Tomcat.addServlet(
                ctx,
                "CategoryServlet",
                new CategoryServlet()
        );

        ctx.addServletMappingDecoded(
                "/categories",
                "CategoryServlet"
        );

        System.out.println(
                "Server berhasil berjalan di: "
                + "http://localhost:8080/products"
        );

        tomcat.start();

        tomcat.getServer().await();
    }
}