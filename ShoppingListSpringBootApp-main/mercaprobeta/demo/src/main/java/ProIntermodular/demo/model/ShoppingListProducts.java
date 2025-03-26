package ProIntermodular.demo.model;

import jakarta.persistence.*;

@Entity
public class ShoppingListProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long mercaId;
    private String name;
    private String packaging;
    private String thumbnail;
    private double price;
    //private int ShoppingListId;

    // Relación con la entidad ShoppingList (clave foránea)
    @ManyToOne
    @JoinColumn(name = "id_lista", nullable = false)  // Especifica la columna en la BD
    private ShoppingList shoppingList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMercaId() {
        return mercaId;
    }

    public void setMercaId(Long mercaId) {
        this.mercaId = mercaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }
}
