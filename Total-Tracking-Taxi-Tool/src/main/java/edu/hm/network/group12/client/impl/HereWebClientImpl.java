package edu.hm.network.group12.client.impl;

import edu.hm.network.group12.api.dto.here.GeoCodeDto;
import edu.hm.network.group12.api.dto.here.PositionDto;
import edu.hm.network.group12.api.dto.here.RouteDto;
import edu.hm.network.group12.client.HereWebClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class HereWebClientImpl implements HereWebClient {

    @Value("${here.platform.geocoding.api.base.url}")
    private String geocodingBaseUrl;

    @Value("${here.platform.routing.api.key}")
    private String geocodingApiKey;

    @Value("${here.platform.routes.api.base.url}")
    private String routesBaseUrl;

    @Autowired
    private ModelMapper modelMapper;

    // https://developer.here.com/documentation/geocoding-search-api/dev_guide/topics-api/code-geocode-address.html
    private PositionDto getPositionData(@NonNull String address) {
        final WebClient client = WebClient.builder()
                .baseUrl(geocodingBaseUrl + "/v1/geocode?q=" + address + "&apiKey=" + geocodingApiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        final GeoCodeDto geoCodeDto = client.get().retrieve().bodyToMono(GeoCodeDto.class).block();
        return geoCodeDto.getGeoCodeItems().get(0).getPositionDto(); // TODO: 18.11.2022 Appropriate error handling.
    }

    @Override
    public int getDurationForRoute(@NonNull String origin, @NonNull String destination) {
        final PositionDto positionDtoOrigin = getPositionData(origin);
        final PositionDto positionDtoDestination = getPositionData(destination);

        final String originLat = positionDtoOrigin.getLat();
        final String originLng = positionDtoOrigin.getLng();
        final String destinationLat = positionDtoDestination.getLat();
        final String destinationLng = positionDtoDestination.getLng();

        final WebClient webClient = WebClient.create();
        final RouteDto routeDto = webClient
                .get()
                .uri(routesBaseUrl + "?transportMode=taxi&origin=" + originLat + "," + originLng + "&destination=" + destinationLat + "," + destinationLng + "&&return=summary,typicalDuration&apiKey=" + geocodingApiKey)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RouteDto.class)
                .block();

        return routeDto.getSectionsDtos().get(0).getSectionEntryDtos().get(0).getSummaryDto().getDuration(); // TODO: 18.11.2022 Appropriate error handling.
    }
}
