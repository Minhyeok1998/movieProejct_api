import SecretInfo;
from mongoDB_access import accessMongo;
import json
import requests
import pandas as pd;
import time
import csv;

client , movieDB = accessMongo();

#1. 모든 영화 데이터를 저장할것이당.
#collection name = "movie"
movie_collection = movieDB['Movie'];

# data = pd.DataFrame(movieData);
# data.columns= ['movieCd','movieNm','typeNm','nationAlt','genreAlt','directors','companys']

for page in range(1,10000,1):
            
    requestUrl = '{0}?key={1}&itemPerPage={2}'.format(SecretInfo.requestUrl, SecretInfo.accessKey, 100);
    res = requests.get(requestUrl);
    resTxt = res.text;
    resJson = json.loads(resTxt);
    print(resJson)
    movieData =[];
    # print(movieData.__sizeof__)
    print("현재 페이지 : {0}".format(page))
    for m in resJson['movieListResult']['movieList']:
        movieData.append(
            {
                "movieId" :m['movieCd'],
                "movieName": m['movieNm'],
                "movieType" :m['typeNm'],
                "movieNation": m['nationAlt'],
                "genre" :m['genreAlt'],
                "movieDirector" :m['directors'][0]['peopleNm'] if len(m['directors'])!=0 else "",
                "company" :m['companys'][0]['companyNm'] if len(m['companys'])!=0 else ""
            }
        )
    #movieData 를 mongodb에 저장한다. 어케할까용
    movie_collection.insert_many(movieData);
    time.sleep(4);    

    