package ArcaLtda.org_service.dto;

import jakarta.validation.constraints.NotBlank;

public class UnitRequest {
    @NotBlank
    private String name;
    // optional parent
    private String parentId;

    public UnitRequest() {}
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
}
