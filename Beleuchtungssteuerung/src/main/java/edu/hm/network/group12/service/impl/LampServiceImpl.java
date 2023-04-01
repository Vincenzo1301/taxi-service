package edu.hm.network.group12.service.impl;

import edu.hm.network.group12.dto.LightStateDto;
import edu.hm.network.group12.service.LampService;
import edu.hm.network.group12.util.Response;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class LampServiceImpl implements LampService {

    private static final String URL = "http://localhost/api/newdeveloper";

    @Override
    public boolean turnLightOnOrOff(int lampId, boolean on) {
        try {
            final HttpURLConnection http = (HttpURLConnection) new URL(URL + "/lights/" + lampId + "/state").openConnection();
            http.setRequestMethod("PUT");
            http.setDoOutput(true);

            final DataOutputStream dataOutputStream = new DataOutputStream(http.getOutputStream());
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("on", on);
            dataOutputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();

            final Response response = new Response(http.getResponseCode(), http.getResponseMessage());
            if (response.getResponseCode() != 200) {
                System.out.println(response);
                return false;
            } else {
                final String change = on ? "on" : "off";
                System.out.println("[" + response.getResponseCode() + "]" + " Light was switched " + change + ".");
            }
        } catch (IOException e) {
            System.out.println("Something went wrong. " + e.getMessage() + ".");
            return false;
        }
        return true;
    }

    @Override
    public boolean setLightState(int lampId, LightStateDto lightStateDto) {
        try {
            final URL url = new URL(URL + "/lights/" + lampId + "/state");
            final URLConnection con = url.openConnection();
            final HttpURLConnection http = (HttpURLConnection) con;
            http.setRequestMethod("PUT");
            http.setDoOutput(true);

            final DataOutputStream dataOutputStream = new DataOutputStream(http.getOutputStream());
            final String requestBodyAsString = new JSONObject(lightStateDto).toString();
            dataOutputStream.write(requestBodyAsString.getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();

            final Response response = new Response(http.getResponseCode(), http.getResponseMessage());
            if (response.getResponseCode() != 200) {
                System.out.println(response);
                return false;
            } else {
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
