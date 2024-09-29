package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
public class HttpGetRequest
{
    public static void main(String[] args) {
        try {
            // URL для запроса
            URL url = new URL("https://httpbin.org/headers");

            // Открытие соединения
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Чтение ответа от сервера
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            // Закрытие потоков
            in.close();
            connection.disconnect();

            // Парсинг JSON-ответа
            JSONObject jsonResponse = new JSONObject(content.toString());
            JSONObject headers = jsonResponse.getJSONObject("headers");

            // Извлечение значений заголовков
            headers.keys().forEachRemaining(key -> {
                System.out.print(headers.getString(key) + ", ");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

