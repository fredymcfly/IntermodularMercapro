package ProIntermodular.demo.controller;

import ProIntermodular.demo.model.ProductModel;
import ProIntermodular.demo.model.ShoppingListProducts;
import ProIntermodular.demo.model.Usuarios;
import ProIntermodular.demo.service.ShoppingListProductService;
import ProIntermodular.demo.service.UsuariosService;
import jakarta.servlet.http.HttpSession;
import ProIntermodular.demo.model.ShoppingList;
import ProIntermodular.demo.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// Indica que esta clase es un controlador en Spring MVC
@Controller
// Define la ruta base para todas las solicitudes a este controlador
@RequestMapping("/api/lists")
public class ShoppingListController {
    // Inyección de dependencias: Spring gestionará el servicio automáticamente
    @Autowired
    private ShoppingListService service;
    @Autowired
    private UsuariosService userService;
    @Autowired
    private MercadonaController mercaController;
    @Autowired
    private ShoppingListProductService listaProductService;

    @GetMapping()
    public CompletableFuture<String> getAllLists(Model model, HttpSession session){
        //obtenemos el id usuario logueado para grabar la lista de compra
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        //antes de grabarlo en base de datos modificamos el modelo de lista de compra y le añadimos el idusuario
        if(usuarioId != null && usuarioId > 0) {
            // Obtiene todas las listas de compras desde el servicio de forma asincrona
            return service.findAll(usuarioId).thenApply(datos -> {
                model.addAttribute("listasCompra", datos);
                return "fragments/listasCompra";
            });
        }
        else
        {
            return CompletableFuture.completedFuture("fragments/login");
        }
    }

    @GetMapping("agregarProducto")
    public String agregarProducto(@RequestParam String idLista,Model model)
    {
        model.addAttribute("idLista", idLista);
        return "fragments/agregarProducto";
    }

    @GetMapping("nuevaLista")
    public String showNuevaLista()
    {
        return "fragments/NuevaLista";
    }

    @PostMapping("grabarProducto")
    public ResponseEntity<?> agregarProductoALista(@RequestParam String idLista, @RequestParam String mercaId)
    {
        ProductModel product = mercaController.obtenerProducto(mercaId);
        Optional<ShoppingList> lista = service.getById(Long.valueOf(idLista));
        if(product != null && lista.isPresent())
        {
            //grabar el producto a la lista
            listaProductService.guardarProductoALista(lista.get(),product);
            return ResponseEntity.ok().body("ok");
        }
        else
        {
            return ResponseEntity.badRequest().body("ko");
        }
    }

    @GetMapping("detallesLista")
    public String showDetalleLista(@RequestParam Long id,Model model)
    {
        //2 consultar a BD la lista que quiero consultar
        var objetoLista = service.getById(id);
        var productosLista = listaProductService.getListProducts(objetoLista.get());

        // Calcular la suma total de los precios
        double total = productosLista.stream().mapToDouble(ShoppingListProducts::getPrice).sum();

        model.addAttribute("productos", productosLista);
        model.addAttribute("idLista", id);
        model.addAttribute("total", total);
        return "fragments/detallesLista";

    }

    // Maneja las solicitudes POST para crear una nueva lista de compras
    @PostMapping("/save")
    public ResponseEntity<?> createList(@RequestBody ShoppingList shoppingList, HttpSession session){
        //obtenemos el id usuario logueado para grabar la lista de compra
        Integer usuarioId = (Integer) session.getAttribute("usuarioId");
        //antes de grabarlo en base de datos modificamos el modelo de lista de compra y le añadimos el idusuario
        if(usuarioId != null && usuarioId > 0)
        {
            var user = userService.getById(usuarioId);
            if(user.isPresent())
            {
                shoppingList.setUsuario(user.get());
                // Llama al servicio para guardar la nueva lista de compras
                var shoppingListGuardada = service.guardar(shoppingList);

                if(shoppingListGuardada != null)
                {
                    return ResponseEntity.ok().body("ok");
                }
            }
        }
        return ResponseEntity.badRequest().body("ko");
    }

}
