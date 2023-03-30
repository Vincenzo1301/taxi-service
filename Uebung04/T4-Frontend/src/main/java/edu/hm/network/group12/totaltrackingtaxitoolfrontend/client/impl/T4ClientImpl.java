package edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.T4Client;
import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto.TaxiDriveDto;
import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto.TaxiDto;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Client to communicate with the REST-Api of the T4-backend.
 */
public class T4ClientImpl implements T4Client {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Optional<TaxiDriveDto> postTaxiDrive(TaxiDriveDto taxiDriveDto) {
        final String URL = "http://localhost:8080/api/v1/taxi/create/trip";

        try {
            final HttpURLConnection http = (HttpURLConnection) new URL(URL).openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("taxiNumber", taxiDriveDto.getTaxiNumber());
            jsonObject.put("origin", taxiDriveDto.getOrigin());
            jsonObject.put("destination", taxiDriveDto.getDestination());

            OutputStream os = http.getOutputStream();
            os.write(jsonObject.toString().getBytes());
            os.flush();

            return Optional.of(mapper.readValue(http.getInputStream(), TaxiDriveDto.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<TaxiDto> getAllTaxis() {
        final String URL = "http://localhost:8080/api/v1/taxi/all";

        try {
            final HttpURLConnection http = (HttpURLConnection) new URL(URL).openConnection();
            http.setRequestMethod("GET");
            http.setDoOutput(true);
            http.connect();

            return Arrays.asList(mapper.readValue(http.getInputStream(), TaxiDto[].class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<TaxiDriveDto> putTaxiAvailability(TaxiDriveDto taxiDriveDto) {
        final String URL = "http://localhost:8080/api/v1/taxi/update/trip";

        try {
            final HttpURLConnection http = (HttpURLConnection) new URL(URL).openConnection();
            http.setRequestMethod("PUT");
            http.setDoOutput(true);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.setRequestProperty("Accept", "application/json");

            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("taxiNumber", taxiDriveDto.getTaxiNumber());
            jsonObject.put("origin", taxiDriveDto.getOrigin());
            jsonObject.put("destination", taxiDriveDto.getDestination());

            OutputStream os = http.getOutputStream();
            os.write(jsonObject.toString().getBytes());
            os.flush();

            return Optional.of(mapper.readValue(http.getInputStream(), TaxiDriveDto.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void putTaxiAvailability(long taxiNumber, boolean available) {
        final String URL = "http://localhost:8080/api/v1/taxi/" + taxiNumber + "/" + available;

        try {
            final HttpURLConnection http = (HttpURLConnection) new URL(URL).openConnection();
            http.setRequestMethod("PUT");
            http.setDoOutput(true);
            http.connect();

            int code = http.getResponseCode();
            System.out.println(code);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTaxiDrive(long taxiNumber) {
        final String URL = "http://localhost:8080/api/v1/taxi/trip/" + taxiNumber;

        try {
            final HttpURLConnection http = (HttpURLConnection) new URL(URL).openConnection();
            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.connect();

            int code = http.getResponseCode();
            System.out.println(code);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
