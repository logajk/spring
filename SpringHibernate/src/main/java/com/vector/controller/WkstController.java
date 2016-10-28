package com.vector.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vector.model.WkstWorkstation;
import com.vector.service.WorkstationService;

@Controller
public class WkstController {

	@Autowired
	private WorkstationService workstationService;
	
	@RequestMapping(value="/wkst/add")
	public ModelAndView addWkstPage(){
		ModelAndView model = new ModelAndView("addWkstForm");
		model.addObject("wkst", new WkstWorkstation());
		return model;
	}
	
	@RequestMapping("/wkst/add/process")
	public ModelAndView addingWkst(@ModelAttribute WkstWorkstation wkst){
		ModelAndView model = new ModelAndView("home");
		workstationService.addWkst(wkst);
		
		String mensaje = "WorkStation se ha añadido correctamente";
		
		model.addObject(mensaje);
		
		return model;
	}
	
	@RequestMapping("/wkst/list")
	public ModelAndView wkstList(){
		ModelAndView model = new ModelAndView("wkstList");
		
		List<WkstWorkstation> listado = workstationService.getAll();
		
		for(WkstWorkstation wkst : listado){
			System.out.println(wkst);
		}
		
		model.addObject("listado", listado);
		
		return model;
	}
	
	@RequestMapping(value="/wkst/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editWkstPage(@PathVariable String id){
		ModelAndView model = new ModelAndView("editWkstForm");
		
		WkstWorkstation workstation = workstationService.findByID(id);
		model.addObject("wkst",workstation);
		
		return model;
	}
	
	@RequestMapping(value="/wkst/edit/{id}", method=RequestMethod.POST)
	public ModelAndView eddittingWkst(@ModelAttribute WkstWorkstation wkst, @PathVariable String id){
		ModelAndView model = new ModelAndView("home");
		
		workstationService.updateWkst(wkst);
		
		String mensaje="WorkStation se ha modificado correctamente";
		model.addObject("mensjae", mensaje);
		
		return model;
	}
	
	@RequestMapping(value="/wkst/delete/{id}", method=RequestMethod.POST)
	public ModelAndView edditingWkst(@ModelAttribute WkstWorkstation wkst, @PathVariable String id){
		ModelAndView model = new ModelAndView("home");
		
		workstationService.updateWkst(wkst);
		String mensaje = "WorkStation actualizado correctamente.";
		
		model.addObject("mensaje", mensaje);
		
		return model;
	}
	
	public ModelAndView deleteWkst(@PathVariable String id){
		ModelAndView model = new ModelAndView("home");
		
		workstationService.deleteWkst(id);
		
		String mensaje = "WorkStation borrado correctamente";
		
		model.addObject("mensaje", mensaje);
		
		return model;
	}
}
