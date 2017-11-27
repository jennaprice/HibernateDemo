package com.gc.controller;

import java.util.ArrayList;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.jboss.logging.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gc.dto.ProductDto;
@Controller
public class HomeController {

	@RequestMapping("/welcome")
	public ModelAndView helloWorld(Model model) {
		
		ArrayList<ProductDto> list = getAllProducts();

		String message = "<br><div style='text-align:center;'>"
				+ "<h3>This message is coming from HomeController.java</h3>";
		return new ModelAndView("welcome", "message", list);
	}
	
	@RequestMapping("/getnewprod")
	public String prodForm() {
		return "addprodform";
	
	}
	// this mapping is needed to pass the parameter as a hidden field to the update
	// form
	@RequestMapping("/update")
	public ModelAndView updateForm(@RequestParam("id") int id) {

		return new ModelAndView("update", "productID", id);
	}
	
	@RequestMapping(value="addnewproduct", method = RequestMethod.POST)
	public String addNewProduct(@RequestParam("code") String code, @RequestParam("description") String desc,
			@RequestParam("listPrice") double price, Model model) {
		Configuration config = new Configuration().configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();

		Transaction tx = session.beginTransaction();
		ProductDto newProduct = new ProductDto(code, desc, price);
		session.save(newProduct);
		tx.commit();
		session.close();
		
		model.addAttribute("newProduct", newProduct);
		
		
		return "addProdSuccess";
	}
	
	@RequestMapping(value="searchbyproduct", method = RequestMethod.GET)
	public ModelAndView searchProduct(@RequestParam("searchproduct") String searchProduct) {
		Configuration config = new Configuration().configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(ProductDto.class);
		crit.add(Restrictions.like("code", "%"+ searchProduct + "%" ));
		ArrayList<ProductDto> list = (ArrayList<ProductDto>)crit.list();
		tx.commit();
		session.close();
		
		
		
		return new ModelAndView("welcome", "message", list);
	}
	
	@RequestMapping("/delete")
	public ModelAndView deleteCustomer(@RequestParam("id") int id) {
		ProductDto objtoDelete= new ProductDto();
		objtoDelete.setProductID(id);
		Configuration config = new Configuration().configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		session.delete(objtoDelete);
		session.getTransaction().commit();
		
		//Updates the list to reflect that an item was deleted
		ArrayList<ProductDto> prodList = getAllProducts();
		
		
		return new ModelAndView("welcome", "message", prodList);
	}
	
	@RequestMapping("/updateproduct")
	public ModelAndView updateProduct(@RequestParam("id") int id, @RequestParam("code") String code,
			@RequestParam("description") String desc, @RequestParam("listPrice") double price) {

		// temp Object will store info for the object we want to update
		ProductDto temp = new ProductDto();
		temp.setProductID(id);
		temp.setCode(code);
		temp.setDescription(desc);
		temp.setListPrice(price);

		Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

		SessionFactory sessionFact = cfg.buildSessionFactory();

		Session codes = sessionFact.openSession();

		codes.beginTransaction();

		codes.update(temp); // update the object from the list

		codes.getTransaction().commit(); // update the row from the database table

		ArrayList<ProductDto> prodList = getAllProducts();

		return new ModelAndView("welcome", "message", prodList);
	}

	private ArrayList<ProductDto> getAllProducts() {
		Configuration config = new Configuration().configure("hibernate.cfg.xml");
		SessionFactory sessionFactory = config.buildSessionFactory();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Criteria crit = session.createCriteria(ProductDto.class);
		ArrayList<ProductDto> list = (ArrayList<ProductDto>) crit.list();
		tx.commit();
		session.close();
		return list;
	}
}
