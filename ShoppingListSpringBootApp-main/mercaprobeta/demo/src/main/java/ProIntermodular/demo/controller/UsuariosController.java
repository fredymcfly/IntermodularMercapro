package ProIntermodular.demo.controller;

import ProIntermodular.demo.model.Usuarios;
import ProIntermodular.demo.service.UsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private UsuariosService usuariosService;

    @GetMapping
    public ArrayList<Usuarios> getUsuarios(){
        return this.usuariosService.getUsuarios();
    }

    @PostMapping
    public  Usuarios guardar (@RequestBody  Usuarios usuarios ){
        return this.usuariosService.guardar(usuarios);
    }

    @GetMapping(path = "/{id}")
    public Optional<Usuarios> getUsuarioById(@PathVariable Integer id){
        return this.usuariosService.getById(id);
    }

    @PutMapping (path = "/{id}")
    public Usuarios updateUsuariosById(@RequestBody Usuarios usuarios , @PathVariable Integer id ){
        return this.usuariosService.updateById(usuarios , id);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable ("id"  ) Integer id ){
        boolean ok = this.usuariosService.deleteUsuarios(id);
        if (ok ){
            return "Usuario de id es " + id + "eliminado";
        }else {
            return  "Error tenemos un problema al eliminar el id ";
        }
    }

    @PostMapping("/login")
    public Usuarios login(@RequestParam String email, @RequestParam String contrasena) {
        return this.usuariosService.getByLogin(email, contrasena);
    }


    @GetMapping("/registro")
    public String showRGPage() {
        System.out.println(" Cargando la pagina de registro...");
        return "registro";
    }



    // Procesar el formulario de registro
    @PostMapping("doregistro")
    public String registrarUsuario(@ModelAttribute Usuarios usuarios, Model model){
        Usuarios usuarioGuardado = usuariosService.registrarUsuario(usuarios);
        return "login"; // Redirigir a la página de login (crear login.html en templates)
    }

    // Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<Usuarios> registrarUsuario( @RequestBody Usuarios usuarios) {
        Usuarios usuarioGuardado = usuariosService.registrarUsuario(usuarios);
        return new ResponseEntity<>(usuarioGuardado, HttpStatus.CREATED); // Retorna 201 Created
    }
}