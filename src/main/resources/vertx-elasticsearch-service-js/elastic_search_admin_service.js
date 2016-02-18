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

/** @module vertx-elasticsearch-service-js/elastic_search_admin_service */
var utils = require('vertx-js/util/utils');
var Vertx = require('vertx-js/vertx');

var io = Packages.io;
var JsonObject = io.vertx.core.json.JsonObject;
var JElasticSearchAdminService = com.englishtown.vertx.elasticsearch.ElasticSearchAdminService;
var MappingOptions = com.englishtown.vertx.elasticsearch.MappingOptions;

/**
 Admin service

 @class
*/
var ElasticSearchAdminService = function(j_val) {

  var j_elasticSearchAdminService = j_val;
  var that = this;

  /**

   @public
   @param indices {Array.<string>} 
   @param type {string} 
   @param source {Object} 
   @param options {Object} 
   @param resultHandler {function} 
   */
  this.putMapping = function(indices, type, source, options, resultHandler) {
    var __args = arguments;
    if (__args.length === 5 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'string' && (typeof __args[2] === 'object' && __args[2] != null) && (typeof __args[3] === 'object' && __args[3] != null) && typeof __args[4] === 'function') {
      j_elasticSearchAdminService["putMapping(java.util.List,java.lang.String,io.vertx.core.json.JsonObject,com.englishtown.vertx.elasticsearch.MappingOptions,io.vertx.core.Handler)"](indices, type, utils.convParamJsonObject(source), options != null ? new MappingOptions(new JsonObject(JSON.stringify(options))) : null, function(ar) {
      if (ar.succeeded()) {
        resultHandler(utils.convReturnJson(ar.result()), null);
      } else {
        resultHandler(null, ar.cause());
      }
    });
    } else throw new TypeError('function invoked with invalid arguments');
  };

  // A reference to the underlying Java delegate
  // NOTE! This is an internal API and must not be used in user code.
  // If you rely on this property your code is likely to break if we change it / remove it without warning.
  this._jdel = j_elasticSearchAdminService;
};

/**

 @memberof module:vertx-elasticsearch-service-js/elastic_search_admin_service
 @param vertx {Vertx} 
 @param address {string} 
 @return {ElasticSearchAdminService}
 */
ElasticSearchAdminService.createEventBusProxy = function(vertx, address) {
  var __args = arguments;
  if (__args.length === 2 && typeof __args[0] === 'object' && __args[0]._jdel && typeof __args[1] === 'string') {
    return utils.convReturnVertxGen(JElasticSearchAdminService["createEventBusProxy(io.vertx.core.Vertx,java.lang.String)"](vertx._jdel, address), ElasticSearchAdminService);
  } else throw new TypeError('function invoked with invalid arguments');
};

// We export the Constructor function
module.exports = ElasticSearchAdminService;