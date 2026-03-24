package com.example.categoria.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.categoria.dto.CategoriaRequestDTO;
import com.example.categoria.dto.CategoriaResponseDTO;
import com.example.categoria.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Slf4j
public class CategoriaController {
private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> crearCategoria(
            @RequestBody CategoriaRequestDTO requestDTO) {

        log.info("POST /api/categorias - Crear categoría: {}", requestDTO.getNombre());
        CategoriaResponseDTO categoriaCreada = categoriaService.crearCategoria(requestDTO);
        return new ResponseEntity<>(categoriaCreada, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> obtenerCategoriaPorId(
            @PathVariable Long id) {

        log.info("GET /api/categorias/{} - Obtener categoría", id);
        CategoriaResponseDTO categoria = categoriaService.obtenerCategoriaPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerTodasLasCategorias() {
        log.info("GET /api/categorias - Obtener todas las categorías");
        List<CategoriaResponseDTO> categorias = categoriaService.obtenerTodasLasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CategoriaResponseDTO>> obtenerCategoriasActivas() {
        log.info("GET /api/categorias/activas - Obtener categorías activas");
        List<CategoriaResponseDTO> categorias = categoriaService.obtenerCategoriasActivas();
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO requestDTO) {

        log.info("PUT /api/categorias/{} - Actualizar categoría", id);
        CategoriaResponseDTO categoriaActualizada =
                categoriaService.actualizarCategoria(id, requestDTO);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id) {
        log.info("DELETE /api/categorias/{} - Eliminar categoría", id);
        categoriaService.eliminarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarCategoria(@PathVariable Long id) {
        log.info("PATCH /api/categorias/{}/desactivar - Desactivar categoría", id);
        categoriaService.desactivarCategoria(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<Void> activarCategoria(@PathVariable Long id) {
        log.info("PATCH /api/categorias/{}/activar - Activar categoría", id);
        categoriaService.activarCategoria(id);
        return ResponseEntity.ok().build();
    }
}
