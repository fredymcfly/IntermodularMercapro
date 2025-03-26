package ProIntermodular.demo.service;

import ProIntermodular.demo.model.ProductModel;
import ProIntermodular.demo.model.ShoppingList;
import ProIntermodular.demo.model.ShoppingListProducts;
import ProIntermodular.demo.repository.IShoppingListProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingListProductService {
    @Autowired
    private IShoppingListProductRepository repository;

    public boolean guardarProductoALista(ShoppingList lista, ProductModel product)
    {
        ShoppingListProducts entidad = new ShoppingListProducts();
        entidad.setName(product.getDisplayName());
        entidad.setShoppingList(lista);
        entidad.setPrice(product.getPrice());
        entidad.setPackaging(product.getPackaging());
        entidad.setMercaId(product.getId());
        entidad.setThumbnail(product.getThumbnail());

        repository.save(entidad);

        return true;
    }

    public List<ShoppingListProducts> getListProducts(ShoppingList shoppingList)
    {
        return repository.findByshoppingList(shoppingList);
    }

}
