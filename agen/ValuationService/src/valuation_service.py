from data_model.data import Data
from data_model.top_product import TopProduct
from csv_readers import CsvReaders
from csv_writers import CsvWriters


def calculate_top_products(data: list[Data], currencies: dict[str, float], matchings: dict[int, int]):
    # gather all unique matching ids
    matching_ids: set[int] = {x.matching_id for x in data}
    matching_id: int
    top_products = []
    for matching_id in matching_ids:
        # filter data by current processed matching_id
        matching_id_data = [x for x in data if x.matching_id is matching_id]
        # compile list [id,total_price]
        id_total_price_list = [[x.id, x.price * x.quantity * currencies[x.currency]] for x in matching_id_data]
        # sort by total price
        id_total_price_list = sorted(tuple(id_total_price_list), key=lambda x: x[1])
        # limit by top_priced_count
        id_total_price_list = id_total_price_list[-matchings[matching_id]:]
        # calculate avg price for matching_id
        avg_price = 0
        [avg_price := avg_price + x.price for x in matching_id_data]
        avg_price = avg_price / len(matching_id_data)
        ignored_products_count = len(matching_id_data) - len(id_total_price_list)
        top_products.extend(
            [TopProduct(matching_id=matching_id,
                        total_price="%.2f" % x[1],
                        avg_price="%.2f" % avg_price,
                        currency=[r.currency for r in data if r.id is x[0]][0],
                        ignored_products_count=ignored_products_count if ignored_products_count >= 0 else 0) for x
             in id_total_price_list])
    return top_products


'''
This program is a valuation service.
Input
    This program reads 3 files:
    ● data.csv 
    ● currencies.csv
    ● matchings.csv
Output
    ● top_products.csv
'''
if __name__ == "__main__":
    CsvWriters.write_top_products(calculate_top_products(
        CsvReaders.read_data('src/resources/data.csv'),
        CsvReaders.read_currencies('src/resources/currencies.csv'),
        CsvReaders.read_matchings('src/resources/matchings.csv')),
        'src/resources/top_products.csv')
