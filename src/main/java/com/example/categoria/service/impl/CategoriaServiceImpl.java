package com.example.categoria.service.impl;
import com.example.categoria.dto.CategoriaRequestDTO;
import com.example.categoria.dto.CategoriaResponseDTO;
import com.example.categoria.model.Categoria;
import com.example.categoria.repository.CategoriaRepository;
import com.example.categoria.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.categoria.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional

public class CategoriaServiceImpl implements CategoriaService{

private final CategoriaRepository categoriaRepository;

    @Override
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO requestDTO) {
        log.info("Creando nueva categoría: {}", requestDTO.getNombre());

        validarDatosCategoria(requestDTO);
        verificarNombreUnico(requestDTO.getNombre());

        Categoria categoria = convertirDTOAEntidad(requestDTO);
        Categoria categoriaGuardada = categoriaRepository.save(categoria);

        log.info("Categoría creada con ID: {}", categoriaGuardada.getId());
        return convertirEntidadADTO(categoriaGuardada);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerCategoriaPorId(Long id) {
        log.info("Buscando categoría con ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con ID: " + id));

        return convertirEntidadADTO(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> obtenerTodasLasCategorias() {
        log.info("Obteniendo todas las categorías");

        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> obtenerCategoriasActivas() {
        log.info("Obteniendo categorías activas");

        return categoriaRepository.findByActivoTrue()
                .stream()
                .map(this::convertirEntidadADTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO requestDTO) {
        log.info("Actualizando categoría con ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con ID: " + id));

        validarDatosCategoria(requestDTO);

        // Verificar nombre único solo si cambió
        if (!categoria.getNombre().equalsIgnoreCase(requestDTO.getNombre())) {
            verificarNombreUnico(requestDTO.getNombre());
        }

        categoria.setNombre(requestDTO.getNombre());
        categoria.setDescripcion(requestDTO.getDescripcion());
        if (requestDTO.getActivo() != null) {
            categoria.setActivo(requestDTO.getActivo());
        }

        Categoria categoriaActualizada = categoriaRepository.save(categoria);

        log.info("Categoría actualizada: {}", id);
        return convertirEntidadADTO(categoriaActualizada);
    }

    @Override
    public void eliminarCategoria(Long id) {
        log.info("Eliminando categoría con ID: {}", id);

        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con ID: " + id);
        }

        categoriaRepository.deleteById(id);
        log.info("Categoría eliminada: {}", id);
    }

    @Override
    public void desactivarCategoria(Long id) {
        log.info("Desactivando categoría con ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con ID: " + id));

        categoria.setActivo(false);
        categoriaRepository.save(categoria);

        log.info("Categoría desactivada: {}", id);
    }

    @Override
    public void activarCategoria(Long id) {
        log.info("Activando categoría con ID: {}", id);

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Categoría no encontrada con ID: " + id));

        categoria.setActivo(true);
        categoriaRepository.save(categoria);

        log.info("Categoría activada: {}", id);
    }

    private void validarDatosCategoria(CategoriaRequestDTO requestDTO) {
        if (requestDTO.getNombre() == null || requestDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio");
        }

        if (requestDTO.getNombre().length() < 3) {
            throw new IllegalArgumentException(
                    "El nombre debe tener al menos 3 caracteres");
        }

        if (requestDTO.getNombre().length() > 50) {
            throw new IllegalArgumentException(
                    "El nombre no puede exceder 50 caracteres");
        }
    }

    private void verificarNombreUnico(String nombre) {
        if (categoriaRepository.existsByNombreIgnoreCase(nombre)) {
            throw new IllegalArgumentException(
                    "Ya existe una categoría con el nombre: " + nombre);
        }
    }

    private Categoria convertirDTOAEntidad(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        categoria.setActivo(dto.getActivo() != null ? dto.getActivo() : true);
        return categoria;
    }

    private CategoriaResponseDTO convertirEntidadADTO(Categoria categoria) {
        return CategoriaResponseDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .activo(categoria.getActivo())
                .fechaCreacion(categoria.getFechaCreacion())
                .fechaActualizacion(categoria.getFechaActualizacion())
                .build();
    }
}
