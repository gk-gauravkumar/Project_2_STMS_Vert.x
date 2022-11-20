package com.GrpId.gauravArtId;

import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;

public class DbConnection {
  Pool pool;
  SqlClient client;
  PgConnectOptions connectOptions;
  //Vertx vertx;

    public DbConnection(PgConnectOptions connectOptions){
    this.connectOptions = connectOptions;
    PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
    client = PgPool.client(Vertx.vertx(),connectOptions,poolOptions);
    pool = Pool.pool(Vertx.vertx(), connectOptions, poolOptions);
  }
}


