import unittest
import sys
import os

sys.path.append(os.path.dirname(os.path.realpath(__file__)) + "/../src")
from data_model.data import Data
from data_model.top_product import TopProduct
from valuation_service import calculate_top_products


class TestValuationService(unittest.TestCase):

    def test_calc(self):
        data: list[Data] = [Data(1, 500, 'USD', 3, 1),
                            Data(2, 2000, 'USD', 2, 1),
                            Data(3, 3000, 'USD', 1, 1),
                            Data(4, 100, 'GBP', 3, 2),
                            Data(5, 5000, 'GBP', 2, 2),
                            Data(6, 1000, 'MNX', 1, 3)
                            ]
        currencies: dict[str, float] = {'USD': 2.4, 'GBP': 1.5, 'MNX': 1.0}
        matchings: dict[int, int] = {1: 2, 2: 1, 3: 1}
        top_products: list[TopProduct] = calculate_top_products(data, currencies, matchings)
        expected_top_products = [TopProduct(1, '7200.00', '1833.33', 'USD', 1),
                                 TopProduct(1, '9600.00', '1833.33', 'USD', 1),
                                 TopProduct(2, '15000.00', '2550.00', 'GBP', 1),
                                 TopProduct(3, '1000.00', '1000.00', 'MNX', 0)
                                 ]
        self.assertEqual(top_products, expected_top_products)


if __name__ == '__main__':
    unittest.main()
