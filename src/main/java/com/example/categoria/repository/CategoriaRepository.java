package com.example.categoria.repository;

import com.example.categoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    // Buscar por nombre exacto
    Optional<Categoria> findByNombre(String nombre);

    // Buscar por nombre (ignorar mayúsculas)
    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    // Verificar si existe por nombre
    boolean existsByNombreIgnoreCase(String nombre);

    // Buscar solo categorías activas
    List<Categoria> findByActivoTrue();

    // Buscar categorías inactivas
    List<Categoria> findByActivoFalse();

    // Buscar por nombre que contenga
    List<Categoria> findByNombreContainingIgnoreCase(String nombre);
    
}
