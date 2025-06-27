package com.tinaco.monitoragua.service;

import com.tinaco.monitoragua.dto.NivelDTO;

public interface INivelService {

    /**
     * Guarda y procesa el nivel de agua recibido
     *
     * @param nivel DTO con la información del nivel de agua
     */
    void guardarNivel(NivelDTO nivel);

    /**
     * Obtiene el estado actual del nivel de agua
     *
     * @return NivelDTO con toda la información del estado actual
     */
    NivelDTO obtenerNivel();

    /**
     * Calcula el porcentaje de llenado basado en la altura del agua
     *
     * @param alturaAgua altura del agua en centímetros
     * @return porcentaje de llenado (0-100)
     */
    double calcularPorcentajeDeLlenado(double alturaAgua);

    /**
     * Verifica si el tinaco está lleno
     *
     * @return true si está lleno, false en caso contrario
     */
    boolean estaLleno();

    /**
     * Verifica si el tinaco está desbordado
     *
     * @return true si está desbordado, false en caso contrario
     */
    boolean estaDesbordado();
}
