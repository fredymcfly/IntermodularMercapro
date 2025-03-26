package ProIntermodular.demo.model;
import java.util.List;

import jakarta.persistence.*;
@Entity
@Table(name = "usuarios")

public class Usuarios {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Column
        private String nombre;
        @Column
        private String email;
        @Column
        private String contrasena;

        @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
        private List<ShoppingList> listasDeCompra;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }

