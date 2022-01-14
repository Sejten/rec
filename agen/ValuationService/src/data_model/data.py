from dataclasses import dataclass


@dataclass
class Data:
    id: int
    price: float
    currency: str
    quantity: int
    matching_id: int
