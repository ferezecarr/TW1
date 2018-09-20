package ar.edu.unlam.tallerweb1.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCaracteres {

	@RequestMapping("/{operacion}/{valor}")
	public ModelAndView operacion(@PathVariable String operacion , @PathVariable String valor , String resultado) {
		ModelMap modelo = new ModelMap();
		
		switch(operacion) {
		
			case "pasarAMayuscula":
				resultado = valor.toUpperCase();
				break;
			
			case "pasarAMinuscula":
				resultado = valor.toLowerCase();
				break;
			
			case "invertirOrden":
				StringBuilder builder = new StringBuilder(valor);
				resultado = builder.reverse().toString();
				break;
			
			case "cantidadDeCaracteres":
				int result = valor.length();
				resultado = Integer.toString(result);
				break;
			default:
				return new ModelAndView("redirect:/error");
		}
		
		modelo.put("operacion", operacion);
		modelo.put("valor", valor);
		modelo.put("resultado", resultado);
		
		
		return new ModelAndView("resultado",modelo);
	}
	
	@RequestMapping("/error")
	public ModelAndView operacionDeCaracteresErronea() {
		ModelMap modelo = new ModelMap();
		return new ModelAndView("error",modelo);
	}
}
