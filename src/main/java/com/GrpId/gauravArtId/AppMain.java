package com.GrpId.gauravArtId;

import io.vertx.core.Vertx;

public class AppMain {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new ApiVerticle(vertx));
  }
}
