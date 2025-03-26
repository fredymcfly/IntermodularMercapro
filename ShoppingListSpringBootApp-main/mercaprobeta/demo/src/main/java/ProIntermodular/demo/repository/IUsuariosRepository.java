package ProIntermodular.demo.repository;

import ProIntermodular.demo.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuariosRepository extends JpaRepository<Usuarios,Integer> {
    Usuarios findByEmailAndContrasena(String email, String contrasena);
// Metodo para buscar al usuario por correo
    Optional<Usuarios> findByEmail(String email);
}






