package com.tinaco.monitoragua.dto;

public class NivelDTO {

    private double alturaAguaCm;
    private double porcentaje;
    private String tiempoInicio;
    private String tiempoFinal;
    private boolean estaLleno;
    private boolean estaDesbordado;

    public NivelDTO() {
    }

    public NivelDTO(double alturaAguaCm, int porcentaje, String tiempoInicio, String tiempoFinal, boolean estaLleno, boolean seDesbordo) {
        this.alturaAguaCm = alturaAguaCm;
        this.porcentaje = porcentaje;
        this.tiempoInicio = tiempoInicio;
        this.tiempoFinal = tiempoFinal;
        this.estaLleno = estaLleno;
        this.estaDesbordado = seDesbordo;
    }

    public double getAlturaAguaCm() {
        return alturaAguaCm;
    }

    public void setAlturaAguaCm(double alturaAguaCm) {
        this.alturaAguaCm = alturaAguaCm;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getTiempoInicio() {
        return tiempoInicio;
    }

    public void setTiempoInicio(String tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public String getTiempoFinal() {
        return tiempoFinal;
    }

    public void setTiempoFinal(String tiempoFinal) {
        this.tiempoFinal = tiempoFinal;
    }

    public boolean isEstaLleno() {
        return estaLleno;
    }

    public void setEstaLleno(boolean estaLleno) {
        this.estaLleno = estaLleno;
    }

    public boolean isEstaDesbordado() {
        return estaDesbordado;
    }

    public void setEstaDesbordado(boolean estaDesbordado) {
        this.estaDesbordado = estaDesbordado;
    }

}
