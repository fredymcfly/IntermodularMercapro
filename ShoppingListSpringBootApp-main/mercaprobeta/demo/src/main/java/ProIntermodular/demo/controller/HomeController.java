package ProIntermodular.demo.controller;

import ProIntermodular.demo.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String showAppPage() {
        return "fragments/home";
    }
}