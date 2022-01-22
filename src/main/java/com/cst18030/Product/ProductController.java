package com.cst18030.Product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cst18030.Category.Category;
import com.cst18030.Category.CategoryRepository;


@Controller
public class ProductController {
	
	@Autowired
	private ProductRepository ProductRepo;
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@GetMapping("/products/new")
	public String showProductNewForm(Model model) {
		List<Category> listCategories = categoryRepo.findAll();
		
		model.addAttribute("product", new Product());
		model.addAttribute("listCategories", listCategories);
		
		return "product_form";
	}
	
	@PostMapping("/products/save")
	public String saveProduct(Product product, HttpServletRequest request) {
		String[] detailIDs= request.getParameterValues("detailID");
		String[] detailNames= request.getParameterValues("detailName");
		String[] detailValues= request.getParameterValues("detailValue");
		
		for(int i=0; i < detailNames.length; i++) {
			
			if(detailIDs != null && detailIDs.length > 0) {

				product.setDetails(Integer.valueOf(detailIDs[i]),detailNames[i], detailValues[i]);
			} else {
				
				product.addDetail(detailNames[i], detailValues[i]);
			}
			
			
			
		}
		ProductRepo.save(product);
		return "redirect:/products";
	}
	
	@GetMapping("/products")
	public String listCategories(Model model) {
		List<Product> listProducts = ProductRepo.findAll();
		model.addAttribute("listProducts", listProducts);
		return "products";
	}
	
	@GetMapping("/products/edit/{id}")
	public String showEditProductForm(@PathVariable("id") Integer id, Model model) {
		Product product = ProductRepo.findById(id).get();
		model.addAttribute("product", product);
		
		List<Category> listCategories = categoryRepo.findAll();

		model.addAttribute("listCategories", listCategories);
		
		return "product_form";
	}
	
	@GetMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id, Model model) {
		ProductRepo.deleteById(id);
		return "redirect:/products";
		
	}

}
