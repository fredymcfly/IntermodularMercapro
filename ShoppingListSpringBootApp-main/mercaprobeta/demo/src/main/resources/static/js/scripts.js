//
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    const contenedor = document.getElementById('contenido');

     contenedor.addEventListener('click', function(event) {
            event.preventDefault();
            if (event.target && event.target.matches('#nuevaLista')) {
                (async () => {
                    const url = "/api/lists/nuevaLista";
                    cargarVistaAContenido(url);
                })();
            }
            else if(event.target && event.target.matches('#guardarLista'))
            {
                onSaveLista();
            }
            else if(event.target && event.target.matches(".verLista"))
            {
                console.log(event.target);
                let enlace = event.target.closest("a"); // Encuentra el enlace contenedor
                if (enlace) {
                    let href = enlace.getAttribute("href");
                    onVerLista(href);
                }
            }
            else if(event.target && event.target.matches("#btnEntrar"))
            {
                onDoLogin();
            }
            else if(event.target && event.target.matches("#addProductButton"))
            {
                const idLista = document.getElementById("hdIdLista");
                (async () => {
                    const url = "/api/lists/agregarProducto?idLista=" + idLista.value;
                    cargarVistaAContenido(url);
                    inicializarScriptVistaParcial();
                })();
            }
            else if(event.target && event.target.matches("#btnAgregarProd"))
            {
                onAddProduct();
            }
        });


    //controlar el menu
     const home = document.body.querySelector('#home');
        home.addEventListener('click', event => {
            event.preventDefault();
            const url = "/home";
            cargarVistaAContenido(url);
        });

    const login = document.body.querySelector('#verlogin');
        login.addEventListener('click', event => {
            event.preventDefault();
            const url = "/login";
            cargarVistaAContenido(url);
        });

    const menuListas = document.body.querySelector('#mislistsas');
    menuListas.addEventListener('click', event => {
        event.preventDefault();
        abrirListas();
    });

    //
    async function onAddProduct()
    {
        let idLista = document.getElementById("hdIdLista");
        let producto = document.getElementById("productos");
        let prodSeleccionado = producto.value;

        const formData = new FormData();
        formData.append("idLista", idLista.value);
        formData.append("mercaId", prodSeleccionado);

        const url = "/api/lists/grabarProducto"
        const response = await fetch(url, {
            method: "POST",
            body: formData
        });

       if (response.ok)
       {
           const url = "/api/lists/detallesLista?id=" + idLista.value;
           cargarVistaAContenido(url);
       } else {
           alert('error');
       }
    }

    async function inicializarScriptVistaParcial()
    {
        cargarCategorias();
    }

    async function onVerLogin()
    {
        const url = "/login";
        cargarVistaAContenido(url);
    }

    async function onDoLogin()
    {
        const email = document.getElementById('email').value;
        const pass = document.getElementById('password').value;
        if (email.trim() && pass.trim()) {
                const data = {
                    email: email,
                    contrasena: pass
                };

                const url = "/dologin";
               const response = await EnviarApi(url, data);
               if (response.ok)
               {
                   const url = "/api/lists";
                   cargarVistaAContenido(url);
               } else {
                   alert('Login incorrecto');
               }
            } else {
                alert('Por favor, completa todos los campos.');
            }
    }

    async function onVerLista(e)
    {
        console.log(e);
        cargarVistaAContenido(e);
    }
    async function abrirListas()
    {
        const url = "/api/lists";
        cargarVistaAContenido(url);
    }

    async function onSaveLista(){
            const listName = document.getElementById('listName').value;
            const listDate = document.getElementById('listDate').value;

            if (listName.trim() && listDate.trim()) {
                const data = {
                    nombre: listName,
                    date: listDate
                };

                const url = "/api/lists/save";
                const response = await EnviarApi(url, data);
                if (response.ok)
                {
                    alert('Lista guardada correctamente');
                    document.getElementById('newListForm').reset();
                } else {
                    alert('Error al guardar la lista');
                }
            } else {
                alert('Por favor, completa todos los campos.');
            }
        }

    function cargarVistaAContenido(url) {
        fetch(url)
            .then(response => response.text())
            .then(html => {
                document.getElementById("contenido").innerHTML = html;
            })
            .catch(error => console.error("Error cargando la vista:", error));
    }

    //esto se ejecuta nada mas cargarse el sript una vez que se ha terminado de generar el dom
    const url = "/home";
    cargarVistaAContenido(url);

});


