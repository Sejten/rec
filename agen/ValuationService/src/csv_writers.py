import csv

from data_model.top_product import TopProduct


class CsvWriters:
    @classmethod
    def write_top_products(cls, top_products: list[TopProduct], filepath):
        with open(filepath, 'w', newline='') as f:
            # matching_id, total_price, avg_price, currency, ignored_products_count.
            writer = csv.writer(f, delimiter=',')
            writer.writerow(TopProduct.get_headers())
            for top_product in top_products:
                writer.writerow(top_product.get_row())
