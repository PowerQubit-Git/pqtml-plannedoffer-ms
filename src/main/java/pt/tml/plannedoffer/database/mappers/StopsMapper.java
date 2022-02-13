package pt.tml.plannedoffer.database.mappers;

import lombok.extern.flogger.Flogger;
import pt.powerqubit.validator.core.table.GtfsStop;
import pt.powerqubit.validator.core.table.GtfsTableContainer;
import pt.tml.plannedoffer.entities.Stop;

import java.util.ArrayList;
import java.util.List;

@Flogger
public class StopsMapper
{

    public static List<Stop> map(String fileName, GtfsTableContainer container, String feedId) throws Exception
    {

        List<GtfsStop> gtfsEntities = container.getEntities();
        List<Stop> entities = new ArrayList<>();

        log.atInfo().log(String.format("Persisting %s : %d entries", fileName, gtfsEntities.size()));

        gtfsEntities.forEach(gtfsEntity -> {
            var out = new Stop();


            out.setFeedId(feedId);

            out.setFeedId(feedId);
            out.setCsvRowNumber(gtfsEntity.csvRowNumber());
            out.setStopId(gtfsEntity.stopId());
            out.setStopIdStepp(gtfsEntity.stopIdStepp());
            out.setStopCode(gtfsEntity.stopCode());
            out.setStopName(gtfsEntity.stopName());
            out.setStopDesc(gtfsEntity.stopDesc());
            out.setStopRemarks(gtfsEntity.stopRemarks());
            out.setStopLat(gtfsEntity.stopLat());
            out.setStopLon(gtfsEntity.stopLon());
            out.setZoneShift(gtfsEntity.zone_shift());
            out.setLocationType(gtfsEntity.locationType());
            out.setParentStation(gtfsEntity.parentStation());
            out.setWheelchairBoarding(gtfsEntity.wheelchairBoarding());
            out.setPlatformCode(gtfsEntity.platformCode());
            out.setEntranceRestriction(gtfsEntity.entranceRestriction());
            out.setExitRestriction(gtfsEntity.exitRestriction());
            out.setSlot(gtfsEntity.slot());
            out.setSignalling(gtfsEntity.signalling());
            out.setShelter(gtfsEntity.shelter());
            out.setBench(gtfsEntity.bench());
            out.setNetworkMap(gtfsEntity.networkMap());
            out.setSchedule(gtfsEntity.schedule());
            out.setRealTimeInformation(gtfsEntity.realTimeInformation());
            out.setTariff(gtfsEntity.tariff());
            out.setPreservationState(gtfsEntity.preservationState());
            out.setEquipment(gtfsEntity.equipment());
            out.setObservations(gtfsEntity.observations());
            //out.setRegion(gtfsEntity.region());
            out.setMunicipality(gtfsEntity.municipality());
            out.setMunicipalityFare1(gtfsEntity.municipalityFare1());
            out.setMunicipalityFare2(gtfsEntity.municipalityFare2());

            entities.add(out);

        });

        return  entities;
    }
}
