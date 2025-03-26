async function EnviarApi(url, datos, headers = {}) {
    try {
        const respuesta = await fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json", headers },
            body: JSON.stringify(datos)
        });

        if (!respuesta.ok)
        {
            console.log(respuesta);
        }

        return await respuesta;
    } catch (error) {
        console.error("Error en EnviarApi:", error);
        return null;
    }
}
