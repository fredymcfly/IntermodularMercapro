package ProIntermodular.demo.model;

public class ProductModel {
    private Long id;
    private String packaging;
    private String thumbnail;
    private String displayName;
    private Double price;

    // Constructor vacío
    public ProductModel() {}

    // Constructor con parámetros
    public ProductModel(Long id, String packaging, String thumbnail, String displayName, Double price) {
        this.id = id;
        this.packaging = packaging;
        this.thumbnail = thumbnail;
        this.displayName = displayName;
        this.price = price;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPackaging() { return packaging; }
    public void setPackaging(String packaging) { this.packaging = packaging; }

    public String getThumbnail() { return thumbnail; }
    public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }

    public String getDisplayName() { return displayName; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}

