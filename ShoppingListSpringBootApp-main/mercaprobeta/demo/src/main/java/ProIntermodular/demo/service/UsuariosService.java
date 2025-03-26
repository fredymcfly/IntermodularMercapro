package ProIntermodular.demo.service;

import ProIntermodular.demo.model.Usuarios;
import ProIntermodular.demo.repository.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UsuariosService {
    @Autowired
    IUsuariosRepository usuariosRepositories;

    public ArrayList<Usuarios> getUsuarios() {
        return (ArrayList<Usuarios>) usuariosRepositories.findAll();
    }

    public Usuarios guardar(Usuarios usuarios) {
        return usuariosRepositories.save(usuarios);
    }

//    @Async
//    public CompletableFuture<Optional<Usuarios>> getById(Integer id) {
//        return CompletableFuture.supplyAsync(() -> {
//            return usuariosRepositories.findById(id);
//        });
//    }

    public Optional<Usuarios> getById(Integer id) {
            return usuariosRepositories.findById(id);
    }

    public Usuarios getByLogin(String email,String pass ) {
        return usuariosRepositories.findByEmailAndContrasena(email, pass);
    }

    public Usuarios updateById(Usuarios request, Integer id) {
        Usuarios usuarios = usuariosRepositories.findById(id).get();
        usuarios.setNombre(request.getNombre());
        usuarios.setEmail(request.getEmail());
        usuarios.setContrasena(request.getContrasena());
        return usuarios;
    }


    public Boolean deleteUsuarios(Integer id) {

        try {
            usuariosRepositories.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // método para registrar un usuario
    public Usuarios registrarUsuario(Usuarios usuario) {
        // Validar si el correo ya está registrado
        if (usuariosRepositories.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalStateException("El correo ya está registrado");
        }
    // Guarda el usuario en la base de datos
        return usuariosRepositories.save(usuario);
    }

}