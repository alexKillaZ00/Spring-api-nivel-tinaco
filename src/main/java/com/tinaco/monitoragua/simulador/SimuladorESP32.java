package com.tinaco.monitoragua.simulador;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.util.Random;

public class SimuladorESP32 {

    public static void main(String[] args) throws Exception {
        Random random = new Random();
        double altura = 0.0;

        while (true) {
            
            altura += random.nextDouble() * 8; 

            if (altura >= 200.0) {
                altura = 200; 
            }

            String json = String.format("{\"alturaAguaCm\": %.2f}", altura);
            System.out.println("Enviando: " + json);

            //URL url = new URL("http://localhost:8080/nivel");
            URL url = new URL("https://monitoragua.onrender.com/nivel");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(json.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            System.out.println("Respuesta: " + responseCode);
            conn.disconnect();

            Thread.sleep(2000);
        }
    }
}
