package com.GrpId.gauravArtId;

import com.GrpId.gauravArtId.Pojo.Result;
import com.GrpId.gauravArtId.Pojo.Student;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class StudentHandler {

  Services services;
  public StudentHandler(Services services) {
    this.services = services;
  }

  public Future<Student> get(RoutingContext rc) {
    String rn = "rollno";
    String RollNo = rc.queryParams().get(rn);
    return services.get(RollNo)
      .onSuccess(promise -> {
        System.out.println(promise.getAddress()+" "+rc.response(). end(promise.getAddress()));
      })
      .onFailure(throwable -> { throwable.getMessage();});
  }
  public  Future<Result> delete(RoutingContext rc){
    String rollNo = rc.pathParam("rollno");
    return services.delete(rollNo)
      .onSuccess(rs ->{
        System.out.println(rs.getCode() + " " + rs.getMessage());
        rc.response().end(rs.getMessage());
      })
      .onFailure(a ->{
        System.out.println(a.getMessage());
        rc.response().end(a.getMessage());});
  }
  public Future<Result> insert(RoutingContext rc){
    JsonObject json = rc.body().asJsonObject();
    Student student = json.mapTo(Student.class);
     return services.insert(student)
       .onSuccess(result -> {
         System.out.println(result.getCode()+" "+result.getMessage());
         rc.response().end(result.getMessage());
       })
       .onFailure(throwable -> {throwable.getMessage();});
  }
  public Future<Result> update(RoutingContext rc){
    JsonObject json = rc.body().asJsonObject();
    Student student = json.mapTo(Student.class);
    return services.update(student)
      .onSuccess(result -> {
        System.out.println(result.getCode()+" "+result.getMessage());
        rc.response().end(result.getMessage());
      })
      .onFailure(throwable -> {throwable.getMessage();});
  }

}
