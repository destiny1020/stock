from sqlalchemy import create_engine
import tushare as ts

engine = create_engine('mysql+pymysql://root:adobe@127.0.0.1/tushare?charset=utf8')

# industry
df = ts.get_industry_classified()
df.to_sql('industry_data', engine, if_exists='append')

# concept
df = ts.get_concept_classified()
df.to_sql('concept_data', engine, if_exists='append')

# area
# df = ts.get_area_classified()
# df.to_sql('area_data', engine)
