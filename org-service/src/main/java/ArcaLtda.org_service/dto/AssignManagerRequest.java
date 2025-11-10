package ArcaLtda.org_service.dto;

import jakarta.validation.constraints.NotBlank;

public class AssignManagerRequest {
    @NotBlank
    private String unitId;
    @NotBlank
    private String managerId; // employee id

    public AssignManagerRequest() {}
    public String getUnitId() { return unitId; }
    public void setUnitId(String unitId) { this.unitId = unitId; }
    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }
}
