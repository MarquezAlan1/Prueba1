from pydantic import BaseModel
from typing import Optional, List
from uuid import uuid4

class UnitBase(BaseModel):
    name: str
    parentId: Optional[str] = None

class UnitCreate(UnitBase):
    pass

class Unit(UnitBase):
    id: str
    managerId: Optional[str] = None
    children: List['Unit'] = []

    class Config:
        from_attributes = True