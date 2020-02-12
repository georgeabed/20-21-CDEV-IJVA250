package com.example.demo.controller;

import com.example.demo.entity.Article;
import com.example.demo.entity.Client;
import com.example.demo.entity.Facture;
import com.example.demo.service.impl.ClientServiceImpl;
import com.example.demo.service.ArticleService;
import com.example.demo.service.FactureService;

import org.apache.poi.ss.usermodel.DataConsolidateFunction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.time.LocalDate;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller principale pour affichage des clients / factures sur la page d'acceuil.
 */
@Controller
public class HomeController {

	private ArticleService articleService;
	private ClientServiceImpl clientServiceImpl;
	private FactureService factureService;

	public HomeController(ArticleService articleService, ClientServiceImpl clientService, FactureService factureService) {
		this.articleService = articleService;
		this.clientServiceImpl = clientService;
		this.factureService = factureService;
	}

	@GetMapping("/")
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView("home");

		List<Article> articles = articleService.findAll();
		modelAndView.addObject("articles", articles);

		List<Client> toto = clientServiceImpl.findAllClients();
		modelAndView.addObject("clients", toto);

		List<Facture> factures = factureService.findAllFactures();
		modelAndView.addObject("factures", factures);

		return modelAndView;
	}

	/*
    @GetMapping("/articles/csv")
    public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	response.setContentType("text/csv");
    	response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");
    			PrintWriter writer = response.getWriter();

    			writer.println("LIGNE1COLONNE1;LIGNE1COLONNE2");
    			writer.println("LIGNE2COLONNE1;LIGNE2COLONNE2");
    		}
	 */

	@GetMapping("/articles/csv")
	public void articlesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"export-articles.csv\"");
		PrintWriter writer = response.getWriter();

		List<Article> articles = articleService.findAll();

		for(int i=0;i<articles.size();i++) {

			Article article = articles.get(i);
			writer.println(article.getLibelle() + ";" + article.getPrix());

		}


	}

	@GetMapping("/clients/csv")
	public void clientesCSV(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.csv\"");
		PrintWriter writer = response.getWriter();

		List<Client> clients = clientServiceImpl.findAllClients();

		for(int i=0;i<clients.size();i++) {

			Client c = clients.get(i);

			LocalDate DateNow = LocalDate.now(); 
			Integer anneeDateNaissance = c.getDateNaissance().getYear();
			Integer anneeNow = DateNow.getYear(); 	

			Integer age = anneeNow - anneeDateNaissance;

			writer.println(c.getNom() + ";" + c.getPrenom() + ";" + age);

		}
	}

	@GetMapping("/clients/xlsx")
	public void clientsXLSX(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=\"export-clients.xlsx\"");
		PrintWriter writer = response.getWriter();

		List<Client> clients = clientServiceImpl.findAllClients();

		for(int i=0;i<clients.size();i++) {

			Client c = clients.get(i);

			LocalDate DateNow = LocalDate.now(); 
			Integer anneeDateNaissance = c.getDateNaissance().getYear();
			Integer anneeNow = DateNow.getYear(); 	

			Integer age = anneeNow - anneeDateNaissance;

			writer.println(c.getNom() + c.getPrenom() + age);

		}
	}


}



