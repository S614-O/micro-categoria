package com.example.categoria.service;
import com.example.categoria.dto.CategoriaRequestDTO;
import com.example.categoria.dto.CategoriaResponseDTO;

import java.util.List;
public interface CategoriaService {
  CategoriaResponseDTO crearCategoria(CategoriaRequestDTO requestDTO);

    CategoriaResponseDTO obtenerCategoriaPorId(Long id);

    List<CategoriaResponseDTO> obtenerTodasLasCategorias();

    List<CategoriaResponseDTO> obtenerCategoriasActivas();

    CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO requestDTO);

    void eliminarCategoria(Long id);

    void desactivarCategoria(Long id);

    void activarCategoria(Long id);

}
