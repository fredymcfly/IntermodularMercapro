package ProIntermodular.demo.service;
// Paquete que contiene la lógica del negocio o servicio
import ProIntermodular.demo.model.ShoppingList;
import ProIntermodular.demo.repository.IShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import java.util.List;
// Indica que esta clase es un servicio gestionado por Spring
@Service
public class ShoppingListService {
    // Inyección de dependencias: el repositorio es gestionado automáticamente por Spring
    @Autowired
    private IShoppingListRepository repository;

    // Método que devuelve todas las listas de compras almacenadas en la base de datos
    @Async
    public CompletableFuture<List<ShoppingList>> findAll(int idUsuario){
        //este metodo devuelve todas las listas
        return CompletableFuture.supplyAsync(() -> {
            return repository.findByUsuario_Id(idUsuario);
        });
    }

    public Optional<ShoppingList> getById(Long id)
    {
        return repository.findById(id);
    }

    // Método que guarda una nueva lista de compras en la base de datos
    public ShoppingList guardar(ShoppingList shoppingList){
        return  repository.save(shoppingList);
    }

}
