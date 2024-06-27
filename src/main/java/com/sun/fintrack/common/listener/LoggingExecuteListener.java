package com.sun.fintrack.common.listener;

import org.jooq.DSLContext;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.util.Arrays;

public class LoggingExecuteListener implements ExecuteListener {

  @Override
  public void start(ExecuteContext ctx) {
    // Create a new DSLContext for logging rendering purposes
    // This DSLContext doesn't need a connection, only the SQLDialect...
    DSLContext create = DSL.using(ctx.dialect(),
        // ... and the flag for pretty-printing
        new Settings().withRenderFormatted(true));
    // If we're executing a query
    if (ctx.query() != null) {
      System.out.println(create.renderInlined(ctx.query()));
    } else if (ctx.routine() != null) {
      // If we're executing a routine
      System.out.println(create.renderInlined(ctx.routine()));
    } else if (ctx.batchQueries() != null) {
      // If we're executing a batch queries
      Arrays.stream(ctx.batchQueries()).forEach(query -> System.out.println(create.renderInlined(query)));
    }
  }
}