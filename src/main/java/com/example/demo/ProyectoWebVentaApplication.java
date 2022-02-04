package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.example.demo.model.Producto;
import com.example.demo.model.Usuario;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.repository.UsuarioRepository;

@SpringBootApplication
public class ProyectoWebVentaApplication extends SpringBootServletInitializer{

	
	public static void main(String[] args) {
		SpringApplication.run(ProyectoWebVentaApplication.class, args);
		
		}
	
	 @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(ProyectoWebVentaApplication.class);
	  }
	 
	@Bean
	CommandLineRunner iniProductos (ProductoRepository productoREPO) {
		return (args) -> {
			productoREPO.saveAll(Arrays.asList(
					new Producto(1,"Steam Gift Card 25€","/imagenes/steamgift.jpg"),
					new Producto(2,"Amazon Gift Card 25€","/imagenes/amazongift.png"),
					 new Producto(3,"Google Play Gift Card 25€","/imagenes/googleplaygift.jpg"),
					 new Producto(4,"Xbox  Gift Card 25€", "/imagenes/xboxgift.png"),
					 new Producto(5,"eShop Gift Card 25€", "/imagenes/nintendogift.jpg"),
					 new Producto(6,"PS Store Gift Card 25€", "/imagenes/playgift.jpeg")));
		};
	}
	
	@Bean
	CommandLineRunner iniUsuarios (UsuarioRepository userREPO) {
		return (args) -> {
			userREPO.saveAll(Arrays.asList(
					new Usuario(1,"rafa","contra"),
					new Usuario(2,"admin","admin"),
					new Usuario(3,"pepe","pepe23")));
		};
	}
	

	
}
