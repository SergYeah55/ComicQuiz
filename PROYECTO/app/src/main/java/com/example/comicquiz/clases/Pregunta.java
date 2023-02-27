package com.example.comicquiz.clases;

public class Pregunta {

    private String categoria;
    private int numero;

    private boolean imagenEnunciado;
    private boolean imagenRespuestas;

    private boolean sonidoPregunta;
    private boolean videoPregunta;

    private String enunciado;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta4;
    private int correcta;

    private String codigoImagenEnunciado;
    private String codigoImagenRespuesta1;
    private String codigoImagenRespuesta2;
    private String codigoImagenRespuesta3;
    private String codigoImagenRespuesta4;

    private String codigoSonido;
    private String codigoVideo;


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public boolean isImagenEnunciado() {
        return imagenEnunciado;
    }

    public void setImagenEnunciado(boolean imagenEnunciado) {
        this.imagenEnunciado = imagenEnunciado;
    }

    public boolean isImagenRespuestas() {
        return imagenRespuestas;
    }

    public void setImagenRespuestas(boolean imagenRespuestas) {
        this.imagenRespuestas = imagenRespuestas;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getRespuesta1() {
        return respuesta1;
    }

    public void setRespuesta1(String respuesta1) {
        this.respuesta1 = respuesta1;
    }

    public String getRespuesta2() {
        return respuesta2;
    }

    public void setRespuesta2(String respuesta2) {
        this.respuesta2 = respuesta2;
    }

    public String getRespuesta3() {
        return respuesta3;
    }

    public void setRespuesta3(String respuesta3) {
        this.respuesta3 = respuesta3;
    }

    public String getRespuesta4() {
        return respuesta4;
    }

    public void setRespuesta4(String respuesta4) {
        this.respuesta4 = respuesta4;
    }

    public int getCorrecta() {
        return correcta;
    }

    public void setCorrecta(int correcta) {
        this.correcta = correcta;
    }

    public String getCodigoImagenEnunciado() {
        return codigoImagenEnunciado;
    }

    public void setCodigoImagenEnunciado(String codigoImagenEnunciado) {
        this.codigoImagenEnunciado = codigoImagenEnunciado;
    }

    public String getCodigoImagenRespuesta1() {
        return codigoImagenRespuesta1;
    }

    public void setCodigoImagenRespuesta1(String codigoImagenRespuesta1) {
        this.codigoImagenRespuesta1 = codigoImagenRespuesta1;
    }

    public String getCodigoImagenRespuesta2() {
        return codigoImagenRespuesta2;
    }

    public void setCodigoImagenRespuesta2(String codigoImagenRespuesta2) {
        this.codigoImagenRespuesta2 = codigoImagenRespuesta2;
    }

    public String getCodigoImagenRespuesta3() {
        return codigoImagenRespuesta3;
    }

    public void setCodigoImagenRespuesta3(String codigoImagenRespuesta3) {
        this.codigoImagenRespuesta3 = codigoImagenRespuesta3;
    }

    public String getCodigoImagenRespuesta4() {
        return codigoImagenRespuesta4;
    }

    public void setCodigoImagenRespuesta4(String codigoImagenRespuesta4) {
        this.codigoImagenRespuesta4 = codigoImagenRespuesta4;
    }

    public boolean isSonidoPregunta() {
        return sonidoPregunta;
    }

    public void setSonidoPregunta(boolean sonidoPregunta) {
        this.sonidoPregunta = sonidoPregunta;
    }

    public boolean isVideoPregunta() {
        return videoPregunta;
    }

    public void setVideoPregunta(boolean videoPregunta) {
        this.videoPregunta = videoPregunta;
    }

    public String getCodigoSonido() {
        return codigoSonido;
    }

    public void setCodigoSonido(String codigoSonido) {
        this.codigoSonido = codigoSonido;
    }

    public String getCodigoVideo() {
        return codigoVideo;
    }

    public void setCodigoVideo(String codigoVideo) {
        this.codigoVideo = codigoVideo;
    }
}
