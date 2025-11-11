from typing import Dict
from .models import Unit

store: Dict[str, Unit] = {}

# Seed
root = Unit(id="root-001", name="Company", parentId=None)
store[root.id] = root