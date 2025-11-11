from typing import List
from ..models import Unit
from ..database import store
from uuid import uuid4
import logging

logger = logging.getLogger("uvicorn")

def create_unit(name: str, parentId: Optional[str]) -> Unit:
    if parentId and parentId not in store:
        raise ValueError("Parent unit not found")
    
    unit = Unit(id=str(uuid4()), name=name, parentId=parentId)
    store[unit.id] = unit
    logger.info(f"OrgEvent: UnitCreated id={unit.id} name={name}")
    return unit

def assign_manager(unitId: str, managerId: str) -> Unit:
    if unitId not in store:
        raise ValueError("Unit not found")
    store[unitId].managerId = managerId
    logger.info(f"OrgEvent: ManagerAssigned unitId={unitId} managerId={managerId}")
    logger.info(f"Notify: ManagerAssigned -> to={managerId}")
    return store[unitId]

def get_units_flat() -> List[Unit]:
    return list(store.values())

def get_units_tree() -> List[Unit]:
    units = {k: Unit(**v.dict()) for k, v in store.items()}
    roots = []
    for unit in units.values():
        if unit.parentId is None:
            roots.append(unit)
        else:
            parent = units.get(unit.parentId)
            if parent:
                parent.children.append(unit)
    return roots