package app.controller;

import java.util.Arrays;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import app.domain.CorCabelo;
import app.domain.Pessoa;
import app.domain.PessoaRepository;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

	// TODO campo de data

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
	public ModelAndView newPessoa() {
		ModelAndView modelAndView = new ModelAndView("pessoa/pessoa_edit");
		modelAndView.addObject("pessoa", new Pessoa());
		
		modelAndView.addObject("todasCoresCabelo", Arrays.asList(CorCabelo.values()));
		
		return modelAndView;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@ModelAttribute("pessoa") Pessoa pessoa, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "pessoa/pessoa_edit";
		}

		repository.save(pessoa);

		return "redirect:/pessoa";
	}

	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable long id, Model model) {
		Pessoa pessoa = repository.findOne(id);
		model.addAttribute("pessoa", pessoa);
		model.addAttribute("updateMode", true);
		return "pessoa/pessoa_edit";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@ModelAttribute("pessoa") Pessoa pessoaAlterada, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return "pessoa/pessoa_edit";
		}

		Pessoa pessoaPersistida = repository.findOne(pessoaAlterada.getId());
		BeanUtils.copyProperties(pessoaAlterada, pessoaPersistida);
		repository.save(pessoaPersistida);

		return "redirect:/pessoa";
	}

	@RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable long id) {
		repository.delete(id);
		return new ModelAndView("redirect:/pessoa");
	}

}
