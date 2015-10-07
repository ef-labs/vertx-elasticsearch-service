/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

/** @module vertx-elasticsearch-service-js/elastic_search_service */
!function (factory) {
  if (typeof require === 'function' && typeof module !== 'undefined') {
    factory();
  } else if (typeof define === 'function' && define.amd) {
    // AMD loader
    define('vertx-elasticsearch-service-js/elastic_search_service-proxy', [], factory);
  } else {
    // plain old include
    ElasticSearchService = factory();
  }
}(function () {

  /**
 ElasticSearch service

 @class
  */
  var ElasticSearchService = function(eb, address) {

    var j_eb = eb;
    var j_address = address;
    var closed = false;
    var that = this;
    var convCharCollection = function(coll) {
      var ret = [];
      for (var i = 0;i < coll.length;i++) {
        ret.push(String.fromCharCode(coll[i]));
      }
      return ret;
    };

    /**
     http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/index_.html

     @public
     @param index {string} the index name 
     @param type {string} the type name 
     @param source {Object} the source to be indexed 
     @param options {Object} optional index options (id, timeout, ttl, etc.) 
     @param resultHandler {function} result handler callback 
     */
    this.index = function(index, type, source, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 5 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'object' && typeof __args[3] === 'object' && typeof __args[4] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"index":__args[0], "type":__args[1], "source":__args[2], "options":__args[3]}, {"action":"index"}, function(err, result) { __args[4](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**
     http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/java-update-api.html

     @public
     @param index {string} the index name 
     @param type {string} the type name 
     @param id {string} the source id to update 
     @param options {Object} the update options (doc, script, etc.) 
     @param resultHandler {function} result handler callback 
     */
    this.update = function(index, type, id, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 5 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'string' && typeof __args[3] === 'object' && typeof __args[4] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"index":__args[0], "type":__args[1], "id":__args[2], "options":__args[3]}, {"action":"update"}, function(err, result) { __args[4](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**
     http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/get.html

     @public
     @param index {string} the index name 
     @param type {string} the type name 
     @param id {string} the source id to update 
     @param options {Object} the update options 
     @param resultHandler {function} result handler callback 
     */
    this.get = function(index, type, id, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 5 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'string' && typeof __args[3] === 'object' && typeof __args[4] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"index":__args[0], "type":__args[1], "id":__args[2], "options":__args[3]}, {"action":"get"}, function(err, result) { __args[4](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**

     @public
     @param indices {Array.<string>} 
     @param options {Object} 
     @param resultHandler {function} 
     */
    this.search = function(indices, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 3 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'object' && typeof __args[2] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"indices":__args[0], "options":__args[1]}, {"action":"search"}, function(err, result) { __args[2](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**
     http://www.elastic.co/guide/en/elasticsearch/reference/1.4/search-request-scroll.html

     @public
     @param scrollId {string} 
     @param options {Object} 
     @param resultHandler {function} 
     */
    this.searchScroll = function(scrollId, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 3 && typeof __args[0] === 'string' && typeof __args[1] === 'object' && typeof __args[2] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"scrollId":__args[0], "options":__args[1]}, {"action":"searchScroll"}, function(err, result) { __args[2](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**
     http://www.elastic.co/guide/en/elasticsearch/client/java-api/1.4/delete.html

     @public
     @param index {string} the index name 
     @param type {string} the type name 
     @param id {string} the source id to delete 
     @param options {Object} optional delete options (timeout, etc.) 
     @param resultHandler {function} result handler callback 
     */
    this.delete = function(index, type, id, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 5 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'string' && typeof __args[3] === 'object' && typeof __args[4] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"index":__args[0], "type":__args[1], "id":__args[2], "options":__args[3]}, {"action":"delete"}, function(err, result) { __args[4](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

    /**
     https://www.elastic.co/guide/en/elasticsearch/reference/current/search-suggesters.html

     @public
     @param index {string} the index name 
     @param options {Object} optional suggest options 
     @param resultHandler {function} result handler callback 
     */
    this.suggest = function(index, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 3 && typeof __args[0] === 'string' && typeof __args[1] === 'object' && typeof __args[2] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"index":__args[0], "options":__args[1]}, {"action":"suggest"}, function(err, result) { __args[2](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

  };

  /**

   @memberof module:vertx-elasticsearch-service-js/elastic_search_service
   @param vertx {Vertx} 
   @param address {string} 
   @return {ElasticSearchService}
   */
  ElasticSearchService.createEventBusProxy = function(vertx, address) {
    var __args = arguments;
    if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
      if (closed) {
        throw new Error('Proxy is closed');
      }
      j_eb.send(j_address, {"vertx":__args[0], "address":__args[1]}, {"action":"createEventBusProxy"});
      return;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  if (typeof exports !== 'undefined') {
    if (typeof module !== 'undefined' && module.exports) {
      exports = module.exports = ElasticSearchService;
    } else {
      exports.ElasticSearchService = ElasticSearchService;
    }
  } else {
    return ElasticSearchService;
  }
});