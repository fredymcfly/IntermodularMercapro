package ProIntermodular.demo.controller;

import ProIntermodular.demo.model.ProductModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/merca")
public class MercadonaController {
    private final RestTemplate restTemplate;

    public MercadonaController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @GetMapping("/subcategorias")
    public List<Map<String, Object>> obtenerSubcategorias() {
        String url = "https://tienda.mercadona.es/api/categories/";

        // Hacer la petición GET
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Parsear la respuesta JSON
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> subcategorias = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            for (JsonNode categoria : rootNode.get("results")) {
                for (JsonNode subcategoria : categoria.get("categories")) {
                    Map<String, Object> subcatMap = new HashMap<>();
                    subcatMap.put("id", subcategoria.get("id").asInt());
                    subcatMap.put("name", subcategoria.get("name").asText());
                    subcategorias.add(subcatMap);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la respuesta JSON", e);
        }

        return subcategorias;
    }

    @GetMapping("/productos/{categoriaId}")
    public List<Map<String, Object>> obtenerProductosPorCategoria(@PathVariable int categoriaId) {
        String url = "https://tienda.mercadona.es/api/categories/" + categoriaId;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> productos = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            for (JsonNode subcategoria : rootNode.get("categories")) {
                for (JsonNode producto : subcategoria.get("products")) {
                    Map<String, Object> productoMap = new HashMap<>();
                    productoMap.put("id", producto.get("id").asText());
                    productoMap.put("packaging", producto.get("packaging").asText());
                    productoMap.put("thumbnail", producto.get("thumbnail").asText());
                    productoMap.put("display_name", producto.get("display_name").asText());
                    productoMap.put("unit_price", producto.get("price_instructions").get("unit_price").asText());
                    productos.add(productoMap);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la respuesta JSON", e);
        }

        return productos;
    }

    @GetMapping("/producto/{productId}")
    public ProductModel obtenerProducto(@PathVariable String productId) {
        String url = "https://tienda.mercadona.es/api/products/" + productId;

        // Hacer la petición HTTP a la API de Mercadona
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Convertir la respuesta JSON en un objeto
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(response.getBody());

            // Extraer los valores requeridos
            Long id = rootNode.get("id").asLong();
            String packaging = rootNode.get("packaging").asText();
            String thumbnail = rootNode.get("thumbnail").asText();
            String displayName = rootNode.get("display_name").asText();
            double price = rootNode.get("price_instructions").get("unit_price").asDouble(); // Precio

            // Devolver el objeto con los datos requeridos
            return new ProductModel(id, packaging, thumbnail, displayName, price);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la respuesta JSON", e);
        }
    }
}
