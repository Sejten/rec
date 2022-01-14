import csv

from data_model.data import Data


class CsvReaders:

    @classmethod
    def read_currencies(cls, file_path):
        with open(file_path, newline='') as csvfile:
            reader = csv.DictReader(csvfile, delimiter=',')
            currencies = dict()
            for row in reader:
                currencies[row['currency']] = float(row['ratio'])
            return currencies

    @classmethod
    def read_data(cls, file_path):
        with open(file_path, newline='') as csvfile:
            reader = csv.DictReader(csvfile, delimiter=',')
            data = []
            for row in reader:
                data.append(Data(id=row['id'], price=float(row['price']), currency=row['currency'],
                                 quantity=int(row['quantity']), matching_id=int(row['matching_id'])))
            return data

    @classmethod
    def read_matchings(cls, file_path):
        with open(file_path, newline='') as csvfile:
            reader = csv.DictReader(csvfile, delimiter=',')
            matchings = dict()
            for row in reader:
                matchings[int(row['matching_id'])] = int(row['top_priced_count'])
            return matchings
