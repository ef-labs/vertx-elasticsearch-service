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
var utils = require('vertx-js/util/utils');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JElasticSearchService = com.englishtown.vertx.elasticsearch.ElasticSearchService;
var SearchOptions = com.englishtown.vertx.elasticsearch.SearchOptions;

/**
 ElasticSearch service

 @class
*/
var ElasticSearchService = function(j_val) {

  var j_elasticSearchService = j_val;
  var that = this;

  /**

   @public

   */
  this.start = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_elasticSearchService.start();
    } else utils.invalidArgs();
  };

  /**

   @public

   */
  this.stop = function() {
    var __args = arguments;
    if (__args.length === 0) {
      j_elasticSearchService.stop();
    } else utils.invalidArgs();
  };

  /**

   @public
   @param index {string} 
   @param type {string} 
   @param id {string} 
   @param source {Object} 
   @param resultHandler {function} 
   */
  this.index = function(index, type, id, source, resultHandler) {
    var __args = arguments;
    if (__args.length === 5 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'string' && typeof __args[3] === 'object' && typeof __args[4] === 'function') {
      j_elasticSearchService.index(index, type, id, utils.convParamJsonObject(source), function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**

   @public
   @param index {string} 
   @param type {string} 
   @param id {string} 
   @param resultHandler {function} 
   */
  this.get = function(index, type, id, resultHandler) {
    var __args = arguments;
    if (__args.length === 4 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'string' && typeof __args[3] === 'function') {
      j_elasticSearchService.get(index, type, id, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
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
      j_elasticSearchService.search(indices, options != null ? new SearchOptions(new JsonObject(JSON.stringify(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**

   @public
   @param scrollId {string} 
   @param scroll {string} 
   @param resultHandler {function} 
   */
  this.scroll = function(scrollId, scroll, resultHandler) {
    var __args = arguments;
    if (__args.length === 3 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'function') {
      j_elasticSearchService.scroll(scrollId, scroll, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  /**

   @public
   @param index {string} 
   @param type {string} 
   @param id {string} 
   @param resultHandler {function} 
   */
  this.delete = function(index, type, id, resultHandler) {
    var __args = arguments;
    if (__args.length === 4 && typeof __args[0] === 'string' && typeof __args[1] === 'string' && typeof __args[2] === 'string' && typeof __args[3] === 'function') {
      j_elasticSearchService.delete(index, type, id, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else utils.invalidArgs();
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_elasticSearchService;
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
    return new ElasticSearchService(JElasticSearchService.createEventBusProxy(vertx._jdel, address));
  } else utils.invalidArgs();
};

// We export the Constructor function
module.exports = ElasticSearchService;