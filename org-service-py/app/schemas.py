from typing import Optional, List
from pydantic import BaseModel

class UnitResponse(BaseModel):
    id: str
    name: str
    parentId: Optional[str]
    managerId: Optional[str]