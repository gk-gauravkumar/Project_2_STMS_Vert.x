//package com.GrpId.gauravArtId;
//
//import io.vertx.core.Future;
//
//public class dbTest {
//  public static void main(String[] args) {
//
//    insert();
//  }
//
//  private static void insert() {
//    Student stud = new Student("ram","101","ayodhya",100);
//    Services dbOperation = new Services();
//    Future<Result> res=dbOperation.insert(stud);
//    res.onSuccess(result ->{
//      System.out.println(res);
//    });
//  }
//}
