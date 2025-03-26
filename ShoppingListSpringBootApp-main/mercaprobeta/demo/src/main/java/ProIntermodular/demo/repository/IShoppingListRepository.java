package ProIntermodular.demo.repository;

import ProIntermodular.demo.model.ShoppingList;
import ProIntermodular.demo.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// Define esta interfaz como un repositorio JPA gestionado por Spring
@Repository
// JpaRepository proporciona métodos básicos como save(), findAll(), findById(), etc.
public interface IShoppingListRepository extends JpaRepository<ShoppingList,Long> {
    //List<ShoppingList> findByusuario(int id_usuario);
    List<ShoppingList> findByUsuario_Id(int id_usuario);
}
