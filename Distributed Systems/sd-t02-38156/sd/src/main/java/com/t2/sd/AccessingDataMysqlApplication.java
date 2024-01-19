package com.t2.sd;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.URI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class AccessingDataMysqlApplication {
    

  public static void main(String[] args) throws IOException {
      
    // context permite para a aplicação quando for pedido
    ConfigurableApplicationContext context = SpringApplication.run(AccessingDataMysqlApplication.class, args);
    System.out.println("\n\tEnter para terminar a aplicação");
    System.in.read();
    context.close();
             
  }

}
