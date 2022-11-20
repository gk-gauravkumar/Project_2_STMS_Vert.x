package com.GrpId.gauravArtId;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.impl.logging.Logger;
import io.vertx.core.impl.logging.LoggerFactory;
import io.vertx.ext.web.Router;



public class ApiVerticle extends AbstractVerticle {
  private final static Logger log = LoggerFactory.getLogger(ApiVerticle.class);
  Vertx vertx;
  public ApiVerticle(Vertx vertx){
    this.vertx = vertx;
  }
  @Override
  public void start(Promise<Void> promise){

    final Router router = Router.router(vertx);

    Services services = new Services();
    StudentHandler studentHandler = new StudentHandler(services);
    StudentRouter studentRouter = new StudentRouter(vertx,studentHandler);
    studentRouter.setRouter(router);
    BuildServer(promise,router);
  }
  public void BuildServer(Promise<Void> startPromise, Router router) {
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        log.info("HTTP server started on port 8080");
      } else {
        startPromise.fail(http.cause());
        log.info("Fail hogya he");
      }
    });



  }
}
