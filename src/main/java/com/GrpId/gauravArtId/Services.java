package com.GrpId.gauravArtId;

import com.GrpId.gauravArtId.Pojo.Result;
import com.GrpId.gauravArtId.Pojo.Student;
import io.vertx.core.Future;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.sqlclient.*;

public class Services  {
  private static final Logger LOGGER = LoggerFactory.getLogger(Services.class);
  PgConnectOptions connectOptions = new PgConnectOptions()
    .setPort(5432)
    .setHost("localhost")
    .setDatabase("demo")
    .setUser("postgres")
    .setPassword("8979704800")
    .setReconnectAttempts(2)
    .setReconnectInterval(1000);
  PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
  DbConnection dbConnection = new DbConnection(connectOptions);
  public Future<Result> insert(Student student) {
    Result result=new Result();
    if(student.getRollNo().equals(""))
      return Future.future(promise ->{
        result.setCode(1);
        result.setMessage("Roll number cannot be null");
        promise.complete(result);
      });
    return Future.future(promise -> {
      dbConnection.client
        .preparedQuery("INSERT INTO student (name, rollNo, address,marks) VALUES ($1, $2, $3, $4)")
        .execute(Tuple.of(student.getName(), student.getRollNo(), student.getAddress(), student.getMarks()))
        .onComplete(ar ->{
          if(ar.succeeded()){
            result.setCode(200);
            result.setMessage("Student details insertion successfull");

            promise.complete(result);
          }
          else {
            result.setCode(1);
            result.setMessage(ar.cause().getMessage());
            promise.complete(result);
          }
        });
    });
  }
  public Future<Result> update(Student student) {
    Result result=new Result();
    if(student.getRollNo().equals(""))
      return Future.future(promise ->{
        result.setCode(1);
        result.setMessage("Roll number cannot be null");
        promise.complete(result);
      });
    return Future.future(promise -> {
      dbConnection.client
        .preparedQuery("UPDATE student SET name = $1 ,address = $2 ,marks = $3 WHERE rollNo = $4")
        .execute(Tuple.of(student.getName(), student.getAddress(), student.getMarks(), student.getRollNo()))
        .onComplete(ar ->{
          if(ar.succeeded()){
            result.setCode(200);
            result.setMessage("Student details updatation successfull");
            promise.complete(result);
          }
          else {
            result.setCode(1);
            result.setMessage(ar.cause().getMessage());
            promise.complete(result);
          }
        });
    });
  }
  public Future<Student> get(String rollNo) {
    if(rollNo.equals(""))
      return Future.future(promise ->{
        promise.fail("Roll Number cannot be null");
      });

    return Future.future(promise -> {
      dbConnection.client
        .preparedQuery("SELECT * FROM student WHERE rollNo = $1")
        .execute(Tuple.of(rollNo))
        .onComplete(ar -> {
          if (ar.succeeded()) {
            RowSet<Row> rows = ar.result();
            Student student = new Student();
            for (Row row : rows) {

              student.setName(row.getString("name"));
              student.setRollNo(row.getString("rollno"));
              student.setAddress(row.getString("address"));
              student.setMarks(row.getInteger("marks"));
            }
            promise.complete(student);
          } else {
            promise.fail(ar.cause().getMessage());
          }
        });
    });
  }

  public Future<Result> delete(String rollNo) {
    Result result = new Result();
    if (rollNo.equals(""))
      return Future.future(promise -> {
        result.setCode(1);
        result.setMessage("Roll number cannot be null");
        promise.complete(result);
      });
    return Future.future(promise -> {
      dbConnection.client
        .preparedQuery("DELETE from student WHERE rollNo = $1")
        .execute(Tuple.of(rollNo))
        .onComplete(ar -> {
          if (ar.result().rowCount()>0) {
            result.setCode(200);
            result.setMessage("Student details deletion successfull");
            promise.complete(result);
          } else {
            result.setCode(1);
            result.setMessage("record not found");
            promise.fail("Record not found");
          }
        });
    });
  }
//  public int NullRecordValidation(String rollno){
//    return dbConnection.client
//      .preparedQuery("select * from student where rollNo = $1")
//      .execute(Tuple.tuple(rollno))
//      .onComplete(rowSet -> {
//        if(rowSet.result().rowCount()>0)
//      })
//  }
}
