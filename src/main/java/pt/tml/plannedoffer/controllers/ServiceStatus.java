package pt.tml.plannedoffer.controllers;

import lombok.Data;

@Data
public class ServiceStatus
{
    private long heapSize;
    private long heapSizeMax;
    private long heapFreeSize;
    private boolean isValidating;
    private boolean isUploading;
    private boolean isPersisting;
    private boolean isExporting;
}
