import SecretInfo;
from mongoDB_access import accessMongo;
import json
import requests
import pandas as pd;
import time
import csv;
import datetime
# now = datetime.datetime.now()
# # print(now)
 
# nowDate = now.strftime('%Y%m%d')
# print(nowDate)
nowDate="20230101"
url = "	http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json?"

now = time
req = "{0}key={1}&targetDt={2}&weekGb=0&itemPerPage=8".format(url,SecretInfo.accessKey,nowDate)
res = requests.get(req);

movieList=[];
resText = res.text;
resJson = json.loads(resText);
for movie in resJson['boxOfficeResult']['weeklyBoxOfficeList']:
    movieList.append([
        {
            "rank" :movie['rank'],
            "rankInten" : movie['rankInten'],
            "name" :movie["movieNm"],
            "audiAcc":movie["audiAcc"]
            
        }
    ])

Data = pd.DataFrame(movieList);
print(Data);
