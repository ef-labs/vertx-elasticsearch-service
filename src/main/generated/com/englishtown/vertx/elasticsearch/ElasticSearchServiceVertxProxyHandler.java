/*
* Copyright 2014 Red Hat, Inc.
*
* Red Hat licenses this file to you under the Apache License, version 2.0
* (the "License"); you may not use this file except in compliance with the
* License. You may obtain a copy of the License at:
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
* License for the specific language governing permissions and limitations
* under the License.
*/

package com.englishtown.vertx.elasticsearch;

import com.englishtown.vertx.elasticsearch.ElasticSearchService;
import io.vertx.core.Vertx;
import io.vertx.core.Handler;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.ReplyException;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import java.util.Collection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import io.vertx.serviceproxy.ProxyHelper;
import io.vertx.serviceproxy.ProxyHandler;
import io.vertx.serviceproxy.ServiceException;
import io.vertx.serviceproxy.ServiceExceptionMessageCodec;
import com.englishtown.vertx.elasticsearch.DeleteOptions;
import io.vertx.core.Vertx;
import com.englishtown.vertx.elasticsearch.GetOptions;
import java.util.List;
import com.englishtown.vertx.elasticsearch.ElasticSearchService;
import com.englishtown.vertx.elasticsearch.UpdateOptions;
import com.englishtown.vertx.elasticsearch.SearchOptions;
import com.englishtown.vertx.elasticsearch.IndexOptions;
import com.englishtown.vertx.elasticsearch.SuggestOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import com.englishtown.vertx.elasticsearch.SearchScrollOptions;

/*
  Generated Proxy code - DO NOT EDIT
  @author Roger the Robot
*/
public class ElasticSearchServiceVertxProxyHandler extends ProxyHandler {

  public static final long DEFAULT_CONNECTION_TIMEOUT = 5 * 60; // 5 minutes 

  private final Vertx vertx;
  private final ElasticSearchService service;
  private final long timerID;
  private long lastAccessed;
  private final long timeoutSeconds;

  public ElasticSearchServiceVertxProxyHandler(Vertx vertx, ElasticSearchService service) {
    this(vertx, service, DEFAULT_CONNECTION_TIMEOUT);
  }

  public ElasticSearchServiceVertxProxyHandler(Vertx vertx, ElasticSearchService service, long timeoutInSecond) {
    this(vertx, service, true, timeoutInSecond);
  }

  public ElasticSearchServiceVertxProxyHandler(Vertx vertx, ElasticSearchService service, boolean topLevel, long timeoutSeconds) {
    this.vertx = vertx;
    this.service = service;
    this.timeoutSeconds = timeoutSeconds;
    try {
      this.vertx.eventBus().registerDefaultCodec(ServiceException.class,
          new ServiceExceptionMessageCodec());
    } catch (IllegalStateException ex) {}
    if (timeoutSeconds != -1 && !topLevel) {
      long period = timeoutSeconds * 1000 / 2;
      if (period > 10000) {
        period = 10000;
      }
      this.timerID = vertx.setPeriodic(period, this::checkTimedOut);
    } else {
      this.timerID = -1;
    }
    accessed();
  }

  public MessageConsumer<JsonObject> registerHandler(String address) {
    MessageConsumer<JsonObject> consumer = vertx.eventBus().<JsonObject>consumer(address).handler(this);
    this.setConsumer(consumer);
    return consumer;
  }

  private void checkTimedOut(long id) {
    long now = System.nanoTime();
    if (now - lastAccessed > timeoutSeconds * 1000000000) {
      close();
    }
  }

  @Override
  public void close() {
    if (timerID != -1) {
      vertx.cancelTimer(timerID);
    }
    super.close();
  }

  private void accessed() {
    this.lastAccessed = System.nanoTime();
  }

  public void handle(Message<JsonObject> msg) {
    try {
      JsonObject json = msg.body();
      String action = msg.headers().get("action");
      if (action == null) {
        throw new IllegalStateException("action not specified");
      }
      accessed();
      switch (action) {

        case "start": {
          service.start();
          break;
        }
        case "stop": {
          service.stop();
          break;
        }
        case "index": {
          service.index((java.lang.String)json.getValue("index"), (java.lang.String)json.getValue("type"), (io.vertx.core.json.JsonObject)json.getValue("source"), json.getJsonObject("options") == null ? null : new com.englishtown.vertx.elasticsearch.IndexOptions(json.getJsonObject("options")), createHandler(msg));
          break;
        }
        case "update": {
          service.update((java.lang.String)json.getValue("index"), (java.lang.String)json.getValue("type"), (java.lang.String)json.getValue("id"), json.getJsonObject("options") == null ? null : new com.englishtown.vertx.elasticsearch.UpdateOptions(json.getJsonObject("options")), createHandler(msg));
          break;
        }
        case "get": {
          service.get((java.lang.String)json.getValue("index"), (java.lang.String)json.getValue("type"), (java.lang.String)json.getValue("id"), json.getJsonObject("options") == null ? null : new com.englishtown.vertx.elasticsearch.GetOptions(json.getJsonObject("options")), createHandler(msg));
          break;
        }
        case "search": {
          service.search(convertList(json.getJsonArray("indices").getList()), json.getJsonObject("options") == null ? null : new com.englishtown.vertx.elasticsearch.SearchOptions(json.getJsonObject("options")), createHandler(msg));
          break;
        }
        case "searchScroll": {
          service.searchScroll((java.lang.String)json.getValue("scrollId"), json.getJsonObject("options") == null ? null : new com.englishtown.vertx.elasticsearch.SearchScrollOptions(json.getJsonObject("options")), createHandler(msg));
          break;
        }
        case "delete": {
          service.delete((java.lang.String)json.getValue("index"), (java.lang.String)json.getValue("type"), (java.lang.String)json.getValue("id"), json.getJsonObject("options") == null ? null : new com.englishtown.vertx.elasticsearch.DeleteOptions(json.getJsonObject("options")), createHandler(msg));
          break;
        }
        case "suggest": {
          service.suggest((java.lang.String)json.getValue("index"), json.getJsonObject("options") == null ? null : new com.englishtown.vertx.elasticsearch.SuggestOptions(json.getJsonObject("options")), createHandler(msg));
          break;
        }
        default: {
          throw new IllegalStateException("Invalid action: " + action);
        }
      }
    } catch (Throwable t) {
      msg.reply(new ServiceException(500, t.getMessage()));
      throw t;
    }
  }

  private <T> Handler<AsyncResult<T>> createHandler(Message msg) {
    return res -> {
      if (res.failed()) {
        if (res.cause() instanceof ServiceException) {
          msg.reply(res.cause());
        } else {
          msg.reply(new ServiceException(-1, res.cause().getMessage()));
        }
      } else {
        if (res.result() != null  && res.result().getClass().isEnum()) {
          msg.reply(((Enum) res.result()).name());
        } else {
          msg.reply(res.result());
        }
      }
    };
  }

  private <T> Handler<AsyncResult<List<T>>> createListHandler(Message msg) {
    return res -> {
      if (res.failed()) {
        if (res.cause() instanceof ServiceException) {
          msg.reply(res.cause());
        } else {
          msg.reply(new ServiceException(-1, res.cause().getMessage()));
        }
      } else {
        msg.reply(new JsonArray(res.result()));
      }
    };
  }

  private <T> Handler<AsyncResult<Set<T>>> createSetHandler(Message msg) {
    return res -> {
      if (res.failed()) {
        if (res.cause() instanceof ServiceException) {
          msg.reply(res.cause());
        } else {
          msg.reply(new ServiceException(-1, res.cause().getMessage()));
        }
      } else {
        msg.reply(new JsonArray(new ArrayList<>(res.result())));
      }
    };
  }

  private Handler<AsyncResult<List<Character>>> createListCharHandler(Message msg) {
    return res -> {
      if (res.failed()) {
        if (res.cause() instanceof ServiceException) {
          msg.reply(res.cause());
        } else {
          msg.reply(new ServiceException(-1, res.cause().getMessage()));
        }
      } else {
        JsonArray arr = new JsonArray();
        for (Character chr: res.result()) {
          arr.add((int) chr);
        }
        msg.reply(arr);
      }
    };
  }

  private Handler<AsyncResult<Set<Character>>> createSetCharHandler(Message msg) {
    return res -> {
      if (res.failed()) {
        if (res.cause() instanceof ServiceException) {
          msg.reply(res.cause());
        } else {
          msg.reply(new ServiceException(-1, res.cause().getMessage()));
        }
      } else {
        JsonArray arr = new JsonArray();
        for (Character chr: res.result()) {
          arr.add((int) chr);
        }
        msg.reply(arr);
      }
    };
  }

  private <T> Map<String, T> convertMap(Map map) {
    return (Map<String, T>)map;
  }

  private <T> List<T> convertList(List list) {
    return (List<T>)list;
  }

  private <T> Set<T> convertSet(List list) {
    return new HashSet<T>((List<T>)list);
  }
}