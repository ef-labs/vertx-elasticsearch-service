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
!function (factory) {
  if (typeof require === 'function' && typeof module !== 'undefined') {
    factory();
  } else if (typeof define === 'function' && define.amd) {
    // AMD loader
    define('vertx-elasticsearch-service-js/elastic_search_admin_service-proxy', [], factory);
  } else {
    // plain old include
    ElasticSearchAdminService = factory();
  }
}(function () {

  /**
 Admin service

 @class
  */
  var ElasticSearchAdminService = function(eb, address) {

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

     @public
     @param indices {Array.<string>} 
     @param type {string} 
     @param source {Object} 
     @param options {Object} 
     @param resultHandler {function} 
     */
    this.putMapping = function(indices, type, source, options, resultHandler) {
      var __args = arguments;
      if (__args.length === 5 && typeof __args[0] === 'object' && __args[0] instanceof Array && typeof __args[1] === 'string' && typeof __args[2] === 'object' && typeof __args[3] === 'object' && typeof __args[4] === 'function') {
        if (closed) {
          throw new Error('Proxy is closed');
        }
        j_eb.send(j_address, {"indices":__args[0], "type":__args[1], "source":__args[2], "options":__args[3]}, {"action":"putMapping"}, function(err, result) { __args[4](err, result &&result.body); });
        return;
      } else throw new TypeError('function invoked with invalid arguments');
    };

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
      if (closed) {
        throw new Error('Proxy is closed');
      }
      j_eb.send(j_address, {"vertx":__args[0], "address":__args[1]}, {"action":"createEventBusProxy"});
      return;
    } else throw new TypeError('function invoked with invalid arguments');
  };

  if (typeof exports !== 'undefined') {
    if (typeof module !== 'undefined' && module.exports) {
      exports = module.exports = ElasticSearchAdminService;
    } else {
      exports.ElasticSearchAdminService = ElasticSearchAdminService;
    }
  } else {
    return ElasticSearchAdminService;
  }
});