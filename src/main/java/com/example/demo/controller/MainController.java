package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Cantidades;
import com.example.demo.model.LineaPedido;
import com.example.demo.model.Pedido;
import com.example.demo.model.Usuario;
import com.example.demo.service.PedidoService;
import com.example.demo.service.UsuarioService;

@Controller
public class MainController {

	@Autowired
	 private HttpSession session;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private PedidoService pedidoService;
	
	
	@GetMapping("/")
	public String Base(@Valid @ModelAttribute("usuario") Usuario user,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "login";
		}
		else if(1==usuarioService.autenticar(user.getNickname(),user.getPassword())){
		
			System.out.println(user.getNickname());
			session.setAttribute("usuario", user);
			model.addAttribute("usuario", user);
			
			return "redirect:/seleccion";
		}
		else {
			return "login";
		}
	}
	
	@PostMapping("/login")
	public String paginaLogin(@Valid @ModelAttribute("usuario") Usuario user,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "login";
		}
		else if(1==usuarioService.autenticar(user.getNickname(),user.getPassword())){
		
			session.setAttribute("usuario", user);
			model.addAttribute("usuario", user);
			return "redirect:/seleccion";
		}
		else {
			return "login";
		}
	}
	
	@GetMapping("/login")
	public String Login(@Valid @ModelAttribute("usuario") Usuario user,
			BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "login";
		}
		else if(1==usuarioService.autenticar(user.getNickname(),user.getPassword())){
		
		
			session.setAttribute("usuario", user);
			model.addAttribute("usuario", user);
			return "redirect:/seleccion";
		}
		else {
			return "login";
		}
	}
	
	@GetMapping({"/invalidate"})
	public String invalidate() {
		
		this.session.invalidate();
		return "redirect:/login";
			
	}
	@GetMapping("/seleccion") public String seleccion() {
		 Usuario user =(Usuario)session.getAttribute("usuario");
			if(user != null) {
				
				return "seleccion";
			}
			else {
				 return "redirect:/error";	
			}
	}
	
	
	@GetMapping("/catalogo")
	public String seleccionFormulario(Model model) {
		
		 Usuario user =(Usuario)session.getAttribute("usuario");
		if(user != null) {
			model.addAttribute("cantidades", new Cantidades());
			return "/catalogo";
		}
		else {
			 return "redirect:/error";	
		}
		
	}
	
	
	  @PostMapping("/catalogo/nuevoPedido") public String
	  nuevoPedido(@ModelAttribute("cantidades") Cantidades cant,
			  BindingResult bindingResult) { 
		  
		  
		  if(session.getAttribute("usuario") == null) {
				
			  return "redirect:/error";				  
			}
			else {
				Pedido nuevo = pedidoService.creaPedido(cant);
				Usuario user =(Usuario)session.getAttribute("usuario");
				session.setAttribute("usuario", user);
				if (nuevo!=null) {
					usuarioService.guardaPedido(nuevo, user.getNickname());	
				}
				
				


				  return "redirect:/mispedidos";
			}
		 
	  }
	  
	  
	  @SuppressWarnings("unchecked")
	@GetMapping("/mispedidos") public String mispedidos(Model model) { 
		  
		  Usuario user = (Usuario) session.getAttribute("usuario");
		  
		  if(session.getAttribute("usuario") == null) {
				
			  return "redirect:/error";			  
			}
			else {
				List<Pedido> pedidos = usuarioService.getPedidosByNick(user.getNickname());
				if (pedidos.isEmpty()) {
					return"/sinPedidos";
				}
				else {
					Collections.sort(pedidos);
					model.addAttribute("pedidos", pedidos);
					
					
					return "/mispedidos";
				}
				
			}
		  
	  }
	  
	  @GetMapping("/editarpedido") public String editarPedido(Model model,
			  @RequestParam int id) {
		  
		
		  Usuario user = (Usuario) session.getAttribute("usuario");
		  if(user == null) {
				
			  return "redirect:/error";				  
			}
			else {
				Usuario usuario = usuarioService.getByNick(user.getNickname());
				List<Pedido> pedidos = usuario.getPedidos();
				if (pedidos.isEmpty()) {
					return"/sinPedidos";
				}
				else {
					
				
						ArrayList<Integer> cantidadesAux = new ArrayList<Integer>();
						for (LineaPedido linea : usuario.getPedido(id).getlineas()) {
							cantidadesAux.add(linea.getCantidad());
						}
						model.addAttribute("cantidades", new Cantidades());
						model.addAttribute("idpedido",id);
						session.setAttribute("idpedido",id);
						model.addAttribute("lineas", usuario.getPedido(id).getlineas());
						return "editarpedido";
					
				
				}
			}
		
	  }
	  
	  
	  @PostMapping("/modificarPedido")
	  public String	  modificaPedido(Model model, @RequestParam(name="cantidad") int[]cantidades,
			  @RequestParam int id) { 
		  
		  
		  Usuario user = (Usuario) session.getAttribute("usuario");
		  if(user == null) {
				
			  return "redirect:/error";				  
			}
			else {
				session.removeAttribute("idpedido");
				Usuario usuario = usuarioService.getByNick(user.getNickname());
				List<LineaPedido> lineas= usuario.getPedido(id).getlineas();
				
				for (int i = 0; i < lineas.size(); i++) {
						lineas.get(i).cantidad=cantidades[i];			
						
				}				
					usuario.getPedido(id).setlineas(lineas);
					usuario.getPedido(id).setFecha(new Date(System.currentTimeMillis()));
					
					usuarioService.guardaUsuario(usuario);				
				session.setAttribute("usuario", usuario);
				  return "redirect:/mispedidos";
			}
		 
	  }
	  
	  
	  @GetMapping("/pagarpedido")
	  public String pagarPedido(Model model,@RequestParam int id) {
		  
		  Usuario user = (Usuario) session.getAttribute("usuario");
		  if(user == null) {
				
			  return "redirect:/error";				  
			}
		  else {
			  List<LineaPedido> lineas = usuarioService.getByNick(user.getNickname()).getPedido(id).getlineas();
			  ArrayList<Integer> cantidadesAux = new ArrayList<Integer>();
			  int total = 0;
				for (LineaPedido linea : lineas) {
					cantidadesAux.add(linea.getCantidad());
					total+=linea.getCantidad();
				}			 
				
					 total = total*25;
				model.addAttribute("total", total);
				model.addAttribute("cantidades", new Cantidades());
				model.addAttribute("idpedido",id);
				session.setAttribute("idpedido",id);
				model.addAttribute("lineas", lineas);
				return "pagarpedido";
		  }
		  
	  }
	  
	  @PostMapping("/pedidopagado")
	  public String pedidoPagado(Model model, RedirectAttributes redirectAttrs) {
		  
		  Usuario user = (Usuario) session.getAttribute("usuario");
		  if(user == null) {
				
			  return "redirect:/error";				  
			}
		  else {
			  //int id=(int) session.getAttribute("idpedido");
			  //Pedido p=usuarioService.getByNick(user.getNickname()).getPedido(id);
			  //pedidoService.borraPedido(p);
			
				
				redirectAttrs
	            .addFlashAttribute("mensaje", "Pedido Pagado");
			

			  return "redirect:/mispedidos";
			  }
		  
	  }
		  



}

