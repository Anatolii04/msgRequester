package ru.ifree.msgoperators.web;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class RootController {
    @GetMapping("/")
    public String root() {
        return "redirect:msgcontacts";
    }

    @GetMapping(value = "/msgcontacts")
    public String root(Model model){
        model.addAttribute("tip","esme");
        return "operators";
    }

    @GetMapping(value = "/custom")
    public String custom(Model model){
        model.addAttribute("tip","custom");
        return "operators";
    }

    @GetMapping(value = "/accidents")
    public String accidents(){
        return "accidents";
    }

}
