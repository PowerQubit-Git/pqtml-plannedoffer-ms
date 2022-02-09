package pt.tml.plannedoffer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult
{
    private boolean validated = false;
    private int errors = 0;
    private int warnings = 0;
    private int infos = 0;
    private String message = "";
}
