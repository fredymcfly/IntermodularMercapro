package ProIntermodular.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;
// Indica que esta clase es una entidad JPA (se vincula con una tabla de la base de datos)
@Entity
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private LocalDate date;

    // Relación con la entidad Usuario (clave foránea)
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)  // Especifica la columna en la BD
    private Usuarios usuario;  // Usa la entidad Usuario en lugar de un Integer

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }
}
