package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.domain.Pessoa;
import app.domain.PessoaRepository;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaRepository repository;
    
    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public String list(@ModelAttribute("pessoaFilter")Pessoa pessoaFilter, Model model) {
    	
    	System.out.println(pessoaFilter);
    	
    	if (!"".equals(pessoaFilter.getNome())) {
    		model.addAttribute("pessoas", repository.findByFilter(pessoaFilter));
    	} else {
    		model.addAttribute("pessoas", repository.findAll());
    	}
        return "pessoa/pessoa_list";
    }

//    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
//    public ModelAndView delete(@PathVariable long id) {
//        repository.delete(id);
//        return new ModelAndView("redirect:/posts");
//    }
//
//    @RequestMapping(value="/new", method = RequestMethod.GET)
//    public String newProject() {
//        return "posts/new";
//    }
//
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public ModelAndView create(@RequestParam("message") String comment) {
//        repository.save(new Post(comment));
//        return new ModelAndView("redirect:/posts");
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public ModelAndView update(@RequestParam("post_id") long id,
//                               @RequestParam("message") String message) {
//        Post post = repository.findOne(id);
//        post.setMessage(message);
//        repository.save(post);
//        return new ModelAndView("redirect:/posts");
//    }
//
//    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
//    public String edit(@PathVariable long id,
//                       Model model) {
//        Post post = repository.findOne(id);
//        model.addAttribute("post", post);
//        return "posts/edit";
//    }


}
