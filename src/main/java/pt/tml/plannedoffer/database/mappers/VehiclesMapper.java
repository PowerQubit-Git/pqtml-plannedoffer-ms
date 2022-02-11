package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.powerqubit.validator.core.table.GtfsVehicles;
import pt.tml.plannedoffer.entities.Vehicles;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class VehiclesMapper
{

    public static List<Vehicles> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsVehicles> gtfsEntities = container.getEntities();
        List<Vehicles> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Vehicles();

            out.setFeedId(feedId);
            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setAgencyId(gtfsEntity.agencyId());
            out.setAvailableSeats(gtfsEntity.availableSeats());
            out.setEmission(gtfsEntity.emission());
            out.setMake(gtfsEntity.make());
            out.setPropulsion(gtfsEntity.propulsion());
            out.setVehicleId(gtfsEntity.vehicleId());
            out.setLicensePlate(gtfsEntity.licensePlate());
            out.setClasses(gtfsEntity.classes());
            out.setStartDate(gtfsEntity.startDate().toYYYYMMDD());
            out.setEndDate(gtfsEntity.endDate().toYYYYMMDD());
            out.setModel(gtfsEntity.model());
            out.setAvailableStandings(gtfsEntity.availableStandings());
            out.setRegistrationDate(gtfsEntity.registrationDate().toYYYYMMDD());
            out.setTypology(gtfsEntity.typology());

            entities.add(out);

        });

        return  entities;
    }
}
