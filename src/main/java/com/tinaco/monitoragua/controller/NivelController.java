package com.tinaco.monitoragua.controller;

import com.tinaco.monitoragua.dto.NivelDTO;
import com.tinaco.monitoragua.service.INivelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nivel")
public class NivelController {

    private final INivelService nivelService;

    @Autowired
    public NivelController(INivelService nivelService) {
        this.nivelService = nivelService;
    }

    @PostMapping
    public void guardarNivel(@RequestBody NivelDTO nivel) {
        nivelService.guardarNivel(nivel);
    }

    @GetMapping
    public NivelDTO obtenerNivel() {
        return nivelService.obtenerNivel();
    }
}
//package com.tinaco.monitoragua.controller;
//
//import com.tinaco.monitoragua.dto.NivelDTO;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/nivel")
//public class NivelController {
//
//    private NivelDTO nivelDTO = new NivelDTO();
//
//    private double alturaAguaActualCm = -1.0;
//
//    private double porcentajeActual = 0;
//    private double porcentajeAnterior = -1;
//
//    private String tiempoInicioLlenado = null;
//    private String tiempoFinalLlenado = null;
//
//    private boolean estaLleno = false;
//    private boolean estaDesbordado = false;
//
//    private int contadorLecturasSubiendo = 0;
//    private int contadorLecturaBajando = 0;
//
//    private boolean llenadoEnCurso = false;
//
//    private final double ALTURA_MAX_AGUA_TINACO_CM = 160.0;
//    
//    @PostMapping
//    public void guardarNivel(@RequestBody NivelDTO nivel) {
//        alturaAguaActualCm = nivel.getAlturaAguaCm();
//        porcentajeActual = calcularPorcentajeDeLlenado(alturaAguaActualCm);
//        System.out.println("Altura del agua: " + alturaAguaActualCm);
//
//        // CASO 1: Subiendo
//        if (porcentajeActual > porcentajeAnterior) {
//            contadorLecturasSubiendo++;
//
//            // Se detecta inicio de llenado solo si no estaba ya en llenado
//            if (contadorLecturasSubiendo >= 5 && !llenadoEnCurso) {
//                llenadoEnCurso = true;
//                tiempoInicioLlenado = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
//                System.out.println("⏱️ Inicio de llenado: " + tiempoInicioLlenado);
//
//                if (tiempoFinalLlenado != null) {
//                    tiempoFinalLlenado = null;
//                }
//            }
//
//            // CASO 2: Nivel igual (sin cambio o bajando)
//        } else {
//            contadorLecturasSubiendo = 0;
//            if (porcentajeAnterior != -1) {
//
//                if (porcentajeActual <= porcentajeAnterior) {
//                    contadorLecturaBajando++;
//                    if (contadorLecturaBajando >= 5) {
//                        llenadoEnCurso = false;
//                    }
//                }else {
//                    contadorLecturaBajando = 0;
//                }
//            }
//        }
//
//        // CASO ESPECIAL: Se llena completamente
//        if (porcentajeActual == 100 && llenadoEnCurso) {
//            tiempoFinalLlenado = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
//            llenadoEnCurso = false;
//            System.out.println("✅ Fin de llenado al 100%: " + tiempoFinalLlenado);
//        }
//
//        // Estado general
//        estaLleno = estaLleno();
//        estaDesbordado = estaDesbordado();
//
//        porcentajeAnterior = porcentajeActual;
//        //alturaAguaAnteriorCm = alturaAguaActualCm;
//    }
//
//    @GetMapping
//    public NivelDTO obtenerNivel() {
//        nivelDTO.setAlturaAguaCm(alturaAguaActualCm);
//        nivelDTO.setPorcentaje(porcentajeActual);
//        nivelDTO.setTiempoInicio(tiempoInicioLlenado);
//        nivelDTO.setTiempoFinal(tiempoFinalLlenado);
//        nivelDTO.setEstaLleno(estaLleno);
//        nivelDTO.setEstaDesbordado(estaDesbordado);
//        return nivelDTO;
//    }
//
//    public double calcularPorcentajeDeLlenado(double alturaAgua) {
//
//        if (alturaAgua <= 0) {
//            return 0;
//        }
//
//        if (alturaAgua >= ALTURA_MAX_AGUA_TINACO_CM) {
//            return 100;
//        }
//
//        double porcentajeAgua = (alturaAgua / ALTURA_MAX_AGUA_TINACO_CM) * 100;
//
//        return  porcentajeAgua;
//    }
//
//    //Metodo para saber si esta lleno
//    public boolean estaLleno() {
//        if (porcentajeActual == 100) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    //Metodo para saber si se desbordó el agua
//    public boolean estaDesbordado() {
//        if (alturaAguaActualCm > ALTURA_MAX_AGUA_TINACO_CM) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    //Metodo para saber si se esta llenando
////    public boolean seEstaLlenando() {
////        int maximoLecturas = 3;
////        int contadorLectura = 0;
////        boolean llenando = false;
////        if (porcentajeAnterior != -1) {
////            if (porcentajeActual > porcentajeAnterior) {
////                contadorLectura++;
////                if (contadorLectura >= maximoLecturas) {
////                    llenando = true;
////                }
////            } else {
////                contadorLectura = 0;
////            }
////        }
////
////        return llenando;
////    }
//}
