package pt.tml.plannedoffer.models;

public class UploadValErrReport
{
    private String validationReport;

    private String errorsReport;

    public UploadValErrReport()
    {
    }

    public UploadValErrReport(String validationReport, String errorsReport)
    {
        this.validationReport = validationReport;
        this.errorsReport = errorsReport;
    }

    public String getValidationReport()
    {
        return validationReport;
    }

    public void setValidationReport(String validationReport)
    {
        this.validationReport = validationReport;
    }

    public String getErrorsReport()
    {
        return errorsReport;
    }

    public void setErrorsReport(String errorsReport)
    {
        this.errorsReport = errorsReport;
    }
}
