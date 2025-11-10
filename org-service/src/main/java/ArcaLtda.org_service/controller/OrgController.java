package ArcaLtda.org_service.controller;

import ArcaLtda.org_service.dto.AssignManagerRequest;
import ArcaLtda.org_service.dto.UnitRequest;
import ArcaLtda.org_service.model.Unit;
import ArcaLtda.org_service.service.OrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/org")
@Validated
public class OrgController {

    private final OrgService orgService;
    private final Logger logger = LoggerFactory.getLogger(OrgController.class);

    public OrgController(OrgService orgService) {
        this.orgService = orgService;
    }

    /**
     * GET /org/units
     * Query param tree=true returns nested tree; default flat list
     */
    @GetMapping("/units")
    public ResponseEntity<?> getUnits(@RequestParam(required = false, defaultValue = "false") boolean tree) {
        if (tree) {
            List<Unit> treeUnits = orgService.listUnitsTree();
            return ResponseEntity.ok(treeUnits);
        } else {
            List<Unit> flat = orgService.listUnitsFlat();
            return ResponseEntity.ok(flat);
        }
    }

    /**
     * POST /org/units
     * header X-User-Role: HR_ADMIN (demo)
     */
    @PostMapping("/units")
    public ResponseEntity<?> createUnit(@RequestHeader(value = "X-User-Role", required = false) String role,
                                        @Valid @RequestBody UnitRequest req) {
        if (role == null || (!role.equalsIgnoreCase("HR_ADMIN") && !role.equalsIgnoreCase("ADMIN"))) {
            return ResponseEntity.status(403).body(Map.of("error", "forbidden - requires HR_ADMIN"));
        }
        try {
            Unit created = orgService.createUnit(req.getName(), req.getParentId());
            logger.info("OrgEvent: UnitCreated id={} name={} parent={}", created.getId(), created.getName(), created.getParentId());
            return ResponseEntity.created(URI.create("/org/units/" + created.getId())).body(created);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    /**
     * POST /org/assign-manager
     * header X-User-Role: HR_ADMIN or MANAGER (demo)
     */
    @PostMapping("/assign-manager")
    public ResponseEntity<?> assignManager(@RequestHeader(value = "X-User-Role", required = false) String role,
                                           @Valid @RequestBody AssignManagerRequest req) {
        if (role == null || (!role.equalsIgnoreCase("HR_ADMIN") && !role.equalsIgnoreCase("MANAGER"))) {
            return ResponseEntity.status(403).body(Map.of("error", "forbidden - requires HR_ADMIN or MANAGER"));
        }
        try {
            Unit updated = orgService.assignManager(req.getUnitId(), req.getManagerId());
            logger.info("OrgEvent: ManagerAssigned unitId={} managerId={}", req.getUnitId(), req.getManagerId());
            // Simulate notification event (console log)
            logger.info("Notify: ManagerAssigned -> to={} subject='Asignado como manager' body='Fue asignado manager de unidad {}'",
                    req.getManagerId(), updated.getName());
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(404).body(Map.of("error", ex.getMessage()));
        }
    }
}
