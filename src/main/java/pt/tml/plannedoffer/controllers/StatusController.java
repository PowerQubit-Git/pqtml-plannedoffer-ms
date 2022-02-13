package pt.tml.plannedoffer.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.tml.plannedoffer.global.ApplicationState;
import pt.tml.plannedoffer.models.ServiceStatus;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@CrossOrigin("*")
public class StatusController
{
    @GetMapping("status-report")
    public ServiceStatus getServiceStatus() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException, URISyntaxException, ClassNotFoundException
    {

        ServiceStatus stats = new ServiceStatus();
        stats.setHeapSize(Runtime.getRuntime().totalMemory());
        stats.setHeapSizeMax(Runtime.getRuntime().maxMemory());
        stats.setHeapFreeSize(Runtime.getRuntime().freeMemory());
        stats.setPersisting(ApplicationState.entityPersistenceBusy);
        stats.setUploading(ApplicationState.uploadBusy);
        stats.setValidating(ApplicationState.validationBusy);
        stats.setExporting(ApplicationState.exportBusy);
        return stats;
    }
}
