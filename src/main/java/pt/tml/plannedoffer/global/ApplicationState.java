package pt.tml.plannedoffer.global;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ApplicationState
{
    public static boolean validationBusy = false;
    public static boolean uploadBusy = false;
    public static boolean entityPersistenceBusy = false;
    public static boolean exportBusy = false;

}
