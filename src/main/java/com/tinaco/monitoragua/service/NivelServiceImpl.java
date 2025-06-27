package com.tinaco.monitoragua.service;

import com.tinaco.monitoragua.dto.NivelDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NivelServiceImpl implements INivelService {

    private NivelDTO nivelDTO = new NivelDTO();

    private double alturaAguaActualCm = -1.0;
    private double porcentajeActual = 0;
    private double porcentajeAnterior = -1;

    private String tiempoInicioLlenado = null;
    private String tiempoFinalLlenado = null;

    private boolean estaLleno = false;
    private boolean estaDesbordado = false;

    private int contadorLecturasSubiendo = 0;
    private int contadorLecturaBajando = 0;

    private boolean llenadoEnCurso = false;

    private static final double ALTURA_MAX_AGUA_TINACO_CM = 160.0;
    private static final int MINIMO_LECTURAS_CAMBIO = 5;
    private static final String PATRON_FECHA = "dd/MM/yyyy HH:mm:ss";

    @Override
    public void guardarNivel(NivelDTO nivel) {
        alturaAguaActualCm = nivel.getAlturaAguaCm();
        porcentajeActual = calcularPorcentajeDeLlenado(alturaAguaActualCm);
        System.out.println("Altura del agua: " + alturaAguaActualCm);

        procesarCambioDeNivel();
        verificarFinDeLlenado();
        actualizarEstadoGeneral();
    }

    private void procesarCambioDeNivel() {
        // CASO 1: Subiendo
        if (porcentajeActual > porcentajeAnterior) {
            procesarNivelSubiendo();
        } else {
            // CASO 2: Nivel igual o bajando
            procesarNivelIgualOBajando();
        }
    }

    private void procesarNivelSubiendo() {
        contadorLecturasSubiendo++;

        // Se detecta inicio de llenado solo si se detecta que el agua sube por lo menos 5 veces y el tinaco no tiene un llenado en curso
        if (contadorLecturasSubiendo >= MINIMO_LECTURAS_CAMBIO && !llenadoEnCurso) {
            iniciarLlenado();
        }
    }

    private void procesarNivelIgualOBajando() {
        contadorLecturasSubiendo = 0;

        if (porcentajeAnterior != -1) {
            if (porcentajeActual <= porcentajeAnterior) {
                contadorLecturaBajando++;
                if (contadorLecturaBajando >= MINIMO_LECTURAS_CAMBIO) {
                    llenadoEnCurso = false;
                }
            } else {
                contadorLecturaBajando = 0;
            }
        }
    }

    private void iniciarLlenado() {
        llenadoEnCurso = true;
        tiempoInicioLlenado = LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATRON_FECHA));
        System.out.println("⏱️ Inicio de llenado: " + tiempoInicioLlenado);

        if (tiempoFinalLlenado != null) {
            tiempoFinalLlenado = null;
        }
    }

    private void verificarFinDeLlenado() {
        // CASO 3: Se llena completamente
        if (porcentajeActual == 100 && llenadoEnCurso) {
            tiempoFinalLlenado = LocalDateTime.now().format(DateTimeFormatter.ofPattern(PATRON_FECHA));
            llenadoEnCurso = false;
            System.out.println("✅ Fin de llenado al 100%: " + tiempoFinalLlenado);
        }
    }

    private void actualizarEstadoGeneral() {
        estaLleno = estaLleno();
        estaDesbordado = estaDesbordado();
        porcentajeAnterior = porcentajeActual;
    }

    @Override
    public NivelDTO obtenerNivel() {
        nivelDTO.setAlturaAguaCm(alturaAguaActualCm);
        nivelDTO.setPorcentaje(porcentajeActual);
        nivelDTO.setTiempoInicio(tiempoInicioLlenado);
        nivelDTO.setTiempoFinal(tiempoFinalLlenado);
        nivelDTO.setEstaLleno(estaLleno);
        nivelDTO.setEstaDesbordado(estaDesbordado);
        return nivelDTO;
    }

    @Override
    public double calcularPorcentajeDeLlenado(double alturaAgua) {
        if (alturaAgua <= 0) {
            return 0;
        }

        if (alturaAgua >= ALTURA_MAX_AGUA_TINACO_CM) {
            return 100;
        }

        return (alturaAgua / ALTURA_MAX_AGUA_TINACO_CM) * 100;
    }

    @Override
    public boolean estaLleno() {
        return porcentajeActual == 100;
    }

    @Override
    public boolean estaDesbordado() {
        return alturaAguaActualCm > ALTURA_MAX_AGUA_TINACO_CM;
    }
}
