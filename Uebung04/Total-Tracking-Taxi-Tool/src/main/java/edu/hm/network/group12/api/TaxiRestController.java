package edu.hm.network.group12.api;

import edu.hm.network.group12.api.dto.taxi.TaxiDriveDto;
import edu.hm.network.group12.api.dto.taxi.TaxiDto;
import edu.hm.network.group12.client.HereWebClient;
import edu.hm.network.group12.service.TaxiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/taxi")
public class TaxiRestController {

    @Autowired
    private TaxiService taxiService;

    @Autowired
    private HereWebClient hereService;

    @PostMapping
    public ResponseEntity<?> createTaxi(@NonNull @RequestBody TaxiDto taxiDto) {
        final TaxiDto createdTaxiDto = taxiService.createTaxi(taxiDto);

        return new ResponseEntity<>(createdTaxiDto, HttpStatus.CREATED);
    }

    @PostMapping("/create/trip")
    public ResponseEntity<?> createTaxiDrive(@NonNull @RequestBody TaxiDriveDto stateDto) {
        final TaxiDriveDto taxiDriveDto = taxiService.createTaxiDrive(stateDto);

        return new ResponseEntity<>(taxiDriveDto, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<?> readAllTaxis() {
        final List<TaxiDto> taxiDtos = taxiService.readAllTaxis();

        return ResponseEntity.ok(taxiDtos);
    }

    @PutMapping("/{taxiNumber}/{available}")
    public ResponseEntity<?> updateDriverAvailability(@NonNull @PathVariable long taxiNumber, @NonNull @PathVariable boolean available) {
        taxiService.updateTaxiAvailability(taxiNumber, available);

        final String statusOfTaxi = available ? "Taxi " + taxiNumber + " is now available." : "Taxi " + taxiNumber + " is now not available.";
        return ResponseEntity.ok(statusOfTaxi);
    }

    @PutMapping("/update/trip")
    public ResponseEntity<?> updateTaciDrive(@NonNull @RequestBody TaxiDriveDto stateDto) {
        taxiService.updateTaxiDrive(stateDto);

        return ResponseEntity.ok("Updated");
    }

    @DeleteMapping("/trip/{taxiNumber}")
    public ResponseEntity<?> deleteTaxiDrive(@NonNull @PathVariable long taxiNumber) {
        taxiService.deleteTaxiDrive(taxiNumber);

        return ResponseEntity.ok(taxiNumber);
    }
}
