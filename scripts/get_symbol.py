from datetime import datetime
from elasticsearch import Elasticsearch
import pandas as pd
es = Elasticsearch()

res = es.search(index="stock-tushare", body={
        "query": {
            "term": {
                "code": "002450"
            }
        },
        "sort": [
            {
                "date": {
                    "order": "asc"
                }
            }
        ],
        "size": 888
    })

df_list = []

print("Got %d Hits:" % res['hits']['total'])
for hit in res['hits']['hits']:
    # print("%(code)s : %(date)s" % hit["_source"])
    df_list.append(pd.Series(hit["_source"]));

print pd.DataFrame(df_list).reset_index().set_index('date')
