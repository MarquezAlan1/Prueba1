package ArcaLtda.org_service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Unit {
    private String id;
    private String name;
    private String parentId; // can be null
    private String managerId; // employeeId or null
    private List<Unit> children = new ArrayList<>();

    public Unit() {}

    public Unit(String name, String parentId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.parentId = parentId;
    }

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }
    public String getManagerId() { return managerId; }
    public void setManagerId(String managerId) { this.managerId = managerId; }
    public List<Unit> getChildren() { return children; }
    public void setChildren(List<Unit> children) { this.children = children; }
}
