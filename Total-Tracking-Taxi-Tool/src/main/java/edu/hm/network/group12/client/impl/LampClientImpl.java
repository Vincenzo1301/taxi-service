package edu.hm.network.group12.client.impl;

import edu.hm.network.group12.api.dto.hue.LightStateDto;
import edu.hm.network.group12.client.LampClient;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class LampClientImpl implements LampClient {

    @Value("${hue.lamps.api.base.url}")
    private String hueLampsBaseUrl;

    @Value("${hue.lamps.api.user}")
    private String hueLampUser;

    @Override
    public void turnLightOnOrOff(long lampId, boolean on) {
        final String URL = hueLampsBaseUrl + "/" + hueLampUser;

        try {
            final HttpURLConnection http = (HttpURLConnection) new URL(URL + "/lights/" + lampId + "/state").openConnection();
            http.setRequestMethod("PUT");
            http.setDoOutput(true);

            final DataOutputStream dataOutputStream = new DataOutputStream(http.getOutputStream());
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("on", on);
            dataOutputStream.write(jsonObject.toString().getBytes(StandardCharsets.UTF_8));
            dataOutputStream.flush();

            final LampClientResponse response = new LampClientResponse(http.getResponseCode(), http.getResponseMessage());

            final String change = on ? "on" : "off";
            if (response.getResponseCode() != 200) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something unexpected went wrong.");
            } else {
                log.info("[" + response.getResponseCode() + "]" + " Light was switched " + change + ".");
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Something unexpected went wrong.", e);
        }
    }

    @Override
    public boolean setLightState(long lampId, LightStateDto lightStateDto) {
        final String URL = hueLampsBaseUrl + "/" + hueLampUser;

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

            final LampClientResponse response = new LampClientResponse(http.getResponseCode(), http.getResponseMessage());
            if (response.getResponseCode() != 200) {
                System.out.println(response);
                return false;
            } else {
                log.info("[" + response.getResponseCode() + "]" + " Light state had been changed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
