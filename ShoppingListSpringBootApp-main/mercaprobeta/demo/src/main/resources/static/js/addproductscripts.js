
function cargarCategorias() {
        fetch("/api/merca/subcategorias")
            .then(response => response.json())
            .then(data => {
                let combo = document.getElementById("subcategorias");
                combo.innerHTML = '<option value="">Seleccione una subcategoría</option>';
                data.forEach(subcategoria => {
                    let option = document.createElement("option");
                    option.value = subcategoria.id;
                    option.textContent = subcategoria.name;
                    combo.appendChild(option);
                });

                 document.getElementById("subcategorias").addEventListener("change", function () {
                    let categoriaId = this.value;
                    if (categoriaId) {
                        cargarProductos(categoriaId);
                    } else {
                        limpiarProductos();
                    }
                });
            })
            .catch(error => console.error("Error cargando subcategorías:", error));
    }

    function cargarProductos(categoriaId) {
        fetch(`/api/merca/productos/${categoriaId}`)
            .then(response => response.json())
            .then(data => {
                let combo = document.getElementById("productos");
                combo.innerHTML = '<option value="">Seleccione un producto</option>';
                data.forEach(producto => {
                    let option = document.createElement("option");
                    option.value = producto.id;
                    option.textContent = producto.display_name + " - " + producto.unit_price + " €";
                    combo.appendChild(option);
                });
            })
            .catch(error => console.error("Error cargando productos:", error));
    }

    function limpiarProductos() {
        let combo = document.getElementById("productos");
        combo.innerHTML = '<option value="">Seleccione un producto</option>';
    }