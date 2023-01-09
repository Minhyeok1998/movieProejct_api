from pymongo import MongoClient
mongoDb_url = "mongodb://localhost:27017/"
# python으로 mongodb MyMovieDb 접근하여 Kofic OpenApi 를 사용하여 데이터를 저장한다.

def accessMongo():
    client = MongoClient(mongoDb_url);
    movieDb = client['MyMovieDb'];

    return client, movieDb;


    
