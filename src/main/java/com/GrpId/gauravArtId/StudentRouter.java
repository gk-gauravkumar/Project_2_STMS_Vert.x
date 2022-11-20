package com.GrpId.gauravArtId;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;


public class StudentRouter //extends AbstractVerticle {

{

  Vertx vertx;
  StudentHandler studentHandler;
  Router router;
  public StudentRouter(Vertx vertx, StudentHandler studentHandler) {
    this.vertx = vertx;
    this.studentHandler = studentHandler;
  }

  public void setRouter(Router router) {
    this.router = router;
    router.route("/student*").handler(BodyHandler.create());
    router.route(HttpMethod.GET,"/student/get").handler(studentHandler::get);
    router.route(HttpMethod.DELETE,"/student/delete/:rollno").handler(studentHandler::delete);
    router.route(HttpMethod.POST,"/student/insert").handler(studentHandler::insert);
    router.route(HttpMethod.PUT,"/student/update").handler(studentHandler::update);

  }
}
