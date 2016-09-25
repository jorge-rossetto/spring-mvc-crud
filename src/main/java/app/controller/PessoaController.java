package app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.domain.Cargo;
import app.domain.CorCabelo;
import app.domain.Pessoa;
import app.domain.PessoaRepository;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	private PessoaRepository repository;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String list(@ModelAttribute("pessoaFilter") Pessoa pessoaFilter, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "pessoa/pessoa_list";
		}

		model.addAttribute("pessoas", repository.findByFilter(pessoaFilter));
		return "pessoa/pessoa_list";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String newPessoa(Model model) {
		model.addAttribute("pessoa", new Pessoa());
		model.addAttribute("todasCoresCabelo", todasCoresCabelo());
		model.addAttribute("todosCargos", todosCargos());
		return "pessoa/pessoa_edit";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable long id, Model model) {
		Pessoa pessoa = repository.findOne(id);
		model.addAttribute("pessoa", pessoa);
		model.addAttribute("todasCoresCabelo", todasCoresCabelo());
		model.addAttribute("todosCargos", todosCargos());
		model.addAttribute("updateMode", true);
		return "pessoa/pessoa_edit";
	}

	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String insertOrUpdate(@ModelAttribute("pessoa") Pessoa pessoa, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "pessoa/pessoa_edit";
		}

		repository.save(pessoa);

		return "redirect:/pessoa";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable long id) {
		repository.delete(id);
		return new ModelAndView("redirect:/pessoa");
	}

	private List<CorCabelo> todasCoresCabelo() {
		return Arrays.asList(CorCabelo.values());
	}

	private List<Cargo> todosCargos() {
		return Arrays.asList(Cargo.values());
	}

	// @RequestMapping(value = "/create", method = RequestMethod.POST)
	// public String create(@ModelAttribute("pessoa") Pessoa pessoa,
	// BindingResult bindingResult, Model model) {
	//
	// if (bindingResult.hasErrors()) {
	// return "pessoa/pessoa_edit";
	// }
	//
	// repository.save(pessoa);
	//
	// return "redirect:/pessoa";
	// }

	// @RequestMapping(value = "/update", method = RequestMethod.POST)
	// public String update(@ModelAttribute("pessoa") Pessoa pessoaAlterada,
	// BindingResult bindingResult, Model model) {
	//
	// if (bindingResult.hasErrors()) {
	// return "pessoa/pessoa_edit";
	// }
	//
	// repository.save(pessoaAlterada);
	//
	// return "redirect:/pessoa";
	// }
}
