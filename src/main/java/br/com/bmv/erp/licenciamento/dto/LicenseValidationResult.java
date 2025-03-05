package br.com.bmv.erp.licenciamento.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LicenseValidationResult {

    private boolean valid;
    private String reason;
    private Long licenseId;
    private Integer currentActiveCount;
    private Integer maxAllowed;
}
