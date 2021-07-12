# MongoDBでEventStreamを試すサンプル

## テスト用MongoDB

EventStreamを利用するには、レプリカセットで起動する必要がある。Dockerfileの下記部分

```dockerfile
CMD ["--replSet", "single_node_replica_set"]
```

single nodeでのレプリカセットモードで起動している。

### ビルド・実行

```shell
cd docker
docker build . -t mongo:single_rs
docker run -p 27017:27017 -d --name mongo mongo:single_rs
```

### docker内に入る

```shell
docker exec -it mongo bash
root@ef98e859c6e5:/# mongo # mongoシェル
single_node_replica_set:PRIMARY> rs.status() # ステータス
```

## プログラム起動

```shell
sbt run
```

起動後、MongoDBの test_db > test_collection に変更を加えてあげれば、change streamが受信できていることを確認できる。

```javascript
use test_db
db.createCollection("test_collection")
db.test_collection.insertOne(
    {
        event: "UserSigned",
        user: {
            id: 1,
            name: "tanaka",
        }
    }
)
```

printlnの結果

```log
ChangeStreamDocument{ 
  operationType=OperationType{value='insert'},
  resumeToken = {"_data": "8260EC7417000000012B022C0100296E5A10044B4A1B0E3FA2439BA6F0D61DDD6805FB46645F6964006460EC741724019F022F0786530004"},
  namespace=test_db.test_collection,
  destinationNamespace=null,
  fullDocument=Document{
    {
      _id=60ec741724019f022f078653,
      event=UserSigned,
      user=Document{
        {
          id=1,
          name=tanaka
        }
      }
    }
  }, 
  documentKey={
    "_id": {
      "$oid": "60ec741724019f022f078653"
    }
  },
  clusterTime=Timestamp{
    value=6984084764277866497,
    seconds=1626108951, 
    inc=1
  }, 
  updateDescription=null, 
  txnNumber=null, 
  lsid=null
}
```

## 参考

* [Tracking Changes in MongoDB With Scala and Akka](https://dzone.com/articles/tracking-changes-in-mongodb-with-scala-and-akka)