from dataclasses import dataclass


@dataclass
class TopProduct:
    matching_id: int
    total_price: str
    avg_price: str
    currency: str
    ignored_products_count: int

    @classmethod
    def get_headers(cls):
        return ["matching_id", "total_price", "avg_price", "currency", "ignored_products_count"]

    def get_row(self):
        return self.matching_id, self.total_price, self.avg_price, self.currency, self.ignored_products_count
