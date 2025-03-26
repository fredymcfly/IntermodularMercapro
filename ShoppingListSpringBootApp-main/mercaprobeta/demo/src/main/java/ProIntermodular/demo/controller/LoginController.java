package ProIntermodular.demo.controller;

import ProIntermodular.demo.model.Usuarios;
import jakarta.servlet.http.HttpSession;
import ProIntermodular.demo.service.UsuariosService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "fragments/login";
    }

    //  @RequestParam String email, @RequestParam String password, HttpSession session
    @PostMapping("/dologin")
    public ResponseEntity<?> DoLogin(@RequestBody Usuarios user, HttpSession session){
        var userLogin = this.usuariosService.getByLogin(user.getEmail(), user.getContrasena());
        if(userLogin != null)
        {
            //dejamos pasar
            // Guardamos el ID del usuario en la sesión
            session.setAttribute("usuarioId", userLogin.getId());
            session.setAttribute("usuarioEmail", userLogin.getEmail());
            //ir a la vista de gestion listas de compra
            return ResponseEntity.ok().body("ok");
           // return "index";
        }
        else
        {
            return ResponseEntity.badRequest().body("ko");
        }
    }

    // Creo el apartado de restro de usuario

}